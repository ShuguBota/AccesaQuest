package ro.cristian.accesaquest.database;

import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.Quest;

import java.util.List;

public interface QuestDBI {
    /**
     * Add a quest to the database
     *
     * @param quest the quest data that needs to be added
     * @return whether operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean createQuest(Quest quest) throws Exception;

    /**
     * Get quests to display that are up for taking
     *
     * @param amount the max amount of quests to get back
     * @return List of JSONObjects containing the quests
     */
    List<JSONObject> getIncompleteQuests(String name, int amount) throws Exception;

    /**
     * Get the quests that the player created
     *
     * @param playerId   id of the player
     * @return           the quests that the player created
     * @throws Exception if the operation was unsuccessful
     */
    List<JSONObject> getCreatedQuests(String playerId) throws Exception;

    /**
     * Get the quests that the player accepted
     *
     * @param playerId   id of the player
     * @return           the quests that the player accepted
     * @throws Exception if the operation was unsuccessful
     */
    List<JSONObject> getAcceptedQuests(String playerId) throws Exception;

    /**
     * Delete a quest from the db
     *
     * @param questId    id of the quest
     * @return           if the operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean deleteQuest(String questId) throws Exception;

    /**
     * Take a quest that is not started by anyone
     *
     * @param playerId   id of the player that wants to take the quest
     * @param questId    id of the quest
     * @return           if the operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean takeQuest(String playerId, String questId) throws Exception;

    /**
     * Update that the quest is completed by either one of the players (taker/creator)
     *
     * @param playerId   id of the player
     * @param questId    id of the quest
     * @return           true if operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean completeQuest(String playerId, String questId) throws Exception;

    /**
     * Cancel a quest by the one who was created
     *
     * @param playerId   id of the player
     * @param questId    id of the quest
     * @return           true if operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean cancelQuest(String playerId, String questId) throws Exception;
}
