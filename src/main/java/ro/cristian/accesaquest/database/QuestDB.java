package ro.cristian.accesaquest.database;

import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.JSON;
import ro.cristian.accesaquest.models.Quest;
import ro.cristian.accesaquest.util.Config;

import java.util.*;

public class QuestDB implements QuestDBI{
    private final String container = Config.questsContainer;
    private final String playersContainer = Config.playersContainer;

    /**
     * Add a quest to the database
     *
     * @param quest      the quest data that needs to be added
     * @return           whether operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean createQuest(Quest quest) throws Exception {
        if(quest.getTokens() < 0) throw new Exception("You can't create a quest with negative tokens");

        //Check for name to be unique
        String sqlQuery = "SELECT * FROM c WHERE c.name = @name";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@name", quest.getName()));
        var querySpec = new SqlQuerySpec(sqlQuery, paramList);
        JSONObject resName = DataAccess.findObject(container, querySpec);

        if(resName != null) throw new Exception("A quest with this name has already been created");

        //Update the players token balance
        var responsePlayer = DataAccess.findObject(playersContainer, queryPlayerById(quest.getCreatedBy()));

        if(responsePlayer == null) throw new Exception("The player doesn't exist");

        var playerCreated = JSON.deserializeJSONPlayer(responsePlayer);

        if(playerCreated.getTokens() < quest.getTokens()) throw new Exception("You do not have enough tokens to create this quest");

        playerCreated.updateTokens(-quest.getTokens());

        //Add the quest to the quest db
        JSONObject json = quest.createJSON();
        DataAccess.createObject(container, json);

        //Add it to the player quest created list
        playerCreated.addQuestsCreatedId(quest.getId());
        DataAccess.replaceObject(playersContainer, playerCreated.createJSON());

        return true;
    }

    /**
     * Get random quests to display that are up for taking
     * @param playerId   the name of the player so we don't show his quests
     * @param amount     the max amount of quests to get back
     * @return           List of JSONObjects containing the quests
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public List<JSONObject> getIncompleteQuests(String playerId, int amount) throws Exception {
        String sqlQuery = "SELECT * FROM c WHERE c.createdBy_id != @playerId AND c.taken = @taken";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@playerId", playerId));
        paramList.add(new SqlParameter("@taken", false));
        var querySpec = new SqlQuerySpec(sqlQuery, paramList);
        var response = DataAccess.findListObjects(container, querySpec);

        if(response == null) return null;

        int size = response.size();

        //Make a random set of values to get random quests from the response
        Set<Integer> randomSet = new HashSet<>();
        Random random = new Random();
        while(randomSet.size() < amount || randomSet.size() == size)
            randomSet.add(random.nextInt(size - 1));

        List<JSONObject> listDisplay = new ArrayList<>();
        for(var pos : randomSet)
            listDisplay.add(response.get(pos));

        return listDisplay;
    }

    /**
     * Get the quests that the player created
     *
     * @param playerId   id of the player
     * @return           the quests that the player created
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public List<JSONObject> getCreatedQuests(String playerId) throws Exception {
        String sqlQuery = "SELECT * FROM c WHERE c.createdBy_id = @playerId";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@playerId", playerId));
        var querySpec = new SqlQuerySpec(sqlQuery, paramList);

        return DataAccess.findListObjects(container, querySpec);
    }

    /**
     * Get the quests that the player accepted
     *
     * @param playerId   id of the player
     * @return           the quests that the player accepted
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public List<JSONObject> getAcceptedQuests(String playerId) throws Exception {
        String sqlQuery = "SELECT * FROM c WHERE c.takenBy_id = @playerId";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@playerId", playerId));
        var querySpec = new SqlQuerySpec(sqlQuery, paramList);

        return DataAccess.findListObjects(container, querySpec);
    }

    /**
     * Delete a quest from the db
     *
     * @param questId id of the quest
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean deleteQuest(String questId) throws Exception {
        var resultQuest = DataAccess.findObject(container, queryQuestById(questId));
        if(resultQuest == null) throw new Exception("Quest is not in the db");
        var playerIdCreated = (String) resultQuest.get("createdBy_id");
        var playerIdTaken = (String) resultQuest.get("takenBy_id");

        //Delete the quest from the players database
        var resultPlayerCreated = DataAccess.findObject(playersContainer, queryPlayerById(playerIdCreated));
        var resultPlayerTaken = DataAccess.findObject(playersContainer, queryPlayerById(playerIdTaken));

        if(resultPlayerCreated == null) throw new Exception("Player who created the quest is not in the db");
        if(resultPlayerTaken == null) throw new Exception("Player who took the quest is not in the db");

        var playerCreated = JSON.deserializeJSONPlayer(resultPlayerCreated);
        var playerTaken = JSON.deserializeJSONPlayer(resultPlayerTaken);

        playerCreated.removeQuestsCreated(questId);
        playerTaken.removeQuestsAccepted(questId);

        var updateres1=  DataAccess.replaceObject(playersContainer, playerCreated.createJSON());
        var updateres2 = DataAccess.replaceObject(playersContainer, playerTaken.createJSON());

        if(updateres1 >= 300 || updateres2 >= 300) throw new Exception("There was a problem with the db");

        //Delete the quest from the quest db
        int res = DataAccess.deleteObject(container, questId);
        if(res >= 300) throw new Exception("Db error, Status code: " + res);

        return true;
    }

    /**
     * Take a quest that is not started by anyone
     *
     * @param playerId id of the player that wants to take the quest
     * @param questId  id of the quest
     * @return if the operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean takeQuest(String playerId, String questId) throws Exception {
        var responseQuest = DataAccess.findObject(container, queryQuestById(questId));

        if(responseQuest == null) throw new Exception("The quest doesn't exist");
        if(responseQuest.get("takenBy_id") != null) throw new Exception("This quest is already taken");

        //Make it taken
        var quest = JSON.deserializeJSONQuest(responseQuest);
        quest.setTakenBy_id(playerId);
        var status = DataAccess.replaceObject(container, quest.createJSON());

        //Add it to the player accepted quests list
        var responsePlayer = DataAccess.findObject(playersContainer, queryPlayerById(playerId));

        if(responsePlayer == null) throw new Exception("The player doesn't exist");

        var player = JSON.deserializeJSONPlayer(responsePlayer);
        player.addQuestsAcceptedId(questId);
        DataAccess.replaceObject(playersContainer, player.createJSON());

        if(status >= 300) throw new Exception("Something went wrong in the db");
        return true;
    }

    /**
     * Update that the quest is completed by either one of the players (taker/creator)
     *
     * @param playerId id of the player
     * @param questId  id of the quest
     * @return id of the player
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean completeQuest(String playerId, String questId) throws Exception {
        var resultQuest = DataAccess.findObject(container, queryQuestById(questId));

        if(resultQuest == null) throw new Exception("Quest is not in the db");
        var quest = JSON.deserializeJSONQuest(resultQuest);

        //The one who created the quest marked it as done
        if(quest.getCreatedBy_id().equals(playerId))
            quest.setAcceptedCreator(true);

        //The one who taken the quest marked it as done
        if(quest.getTakenBy_id().equals(playerId))
            quest.setCompletedTaker(true);

        var resultUpdate = DataAccess.replaceObject(container, quest.createJSON());

        if(resultUpdate >= 300) throw new Exception("There was a problem with the db");

        //Check if the quest is complete (marked by both)
        if(quest.isCompletedTaker() && quest.isAcceptedCreator()){
            //Give the taker the tokens and update both players quests lists
            var resultTaker = DataAccess.findObject(playersContainer, queryPlayerById(quest.getTakenBy_id()));

            if(resultTaker == null) throw new Exception("Taker of the quest not found in the db");

            var taker = JSON.deserializeJSONPlayer(resultTaker);
            taker.updateTokens(quest.getTokens());
            //taker.removeQuestsAccepted(quest.getId());

            var updateTaker = DataAccess.replaceObject(playersContainer, taker.createJSON());

            if(updateTaker >= 300) throw new Exception("Something went wrong with the player db");

            //Delete quest
            deleteQuest(quest.getId());
        }

        return true;
    }

    /**
     * Cancel a quest by the one who was created
     *
     * @param playerId id of the player
     * @param questId  id of the quest
     * @return true if operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean cancelQuest(String playerId, String questId) throws Exception {
        var resultQuest = DataAccess.findObject(container, queryQuestById(questId));

        if(resultQuest == null) throw new Exception("The quest is not in the db");

        var quest = JSON.deserializeJSONQuest(resultQuest);

        //Don't allow cancel if the quest is taken
        if(quest.getTakenBy_id() != null) throw new Exception("This quest is already taken by someone you can't cancel it anymore");

        //Allow only the creator to cancel it
        if(!quest.getCreatedBy_id().equals(questId)) throw new Exception("You are not the creator of the quest");

        //If all the checks pass then delete the quest
        return deleteQuest(questId);
    }

    private SqlQuerySpec queryQuestById(String questId){
        String sqlQuery = "SELECT * FROM c WHERE c.id = @questId";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@questId", questId));
        return new SqlQuerySpec(sqlQuery, paramList);
    }

    private SqlQuerySpec queryPlayerById(String playerId){
        String sqlQuery = "SELECT * FROM c WHERE c.id = @playerId";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@playerId", playerId));
        return new SqlQuerySpec(sqlQuery, paramList);
    }
}
