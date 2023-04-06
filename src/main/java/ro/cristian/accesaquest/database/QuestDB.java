package ro.cristian.accesaquest.database;

import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.JSON;
import ro.cristian.accesaquest.models.Quest;

import java.util.*;

public class QuestDB implements QuestDBI{
    private final String container = "quests";

    /**
     * Add a quest to the database
     *
     * @param quest the quest data that needs to be added
     * @return whether operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean createQuest(Quest quest) throws Exception {
        //Check for name to be unique
        JSONObject resName = DataAccess.queryDBObject(container, queryOnName(quest.getName()));

        if(resName != null) throw new Exception("A quest with this name has already been created");

        JSONObject json = quest.createJSON();
        DataAccess.createDBObject(container, json);

        return true;
    }

    /**
     * Get random quests to display that are up for taking
     * @param name the name of the player so we don't show his quests
     * @param amount the max amount of quests to get back
     * @return List of JSONObjects containing the quests
     */
    @Override
    public List<JSONObject> getIncompleteQuests(String name, int amount) {
        var response = DataAccess.queryDBList(container, queryIncompleteQuests(name));

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

    private SqlQuerySpec queryIncompleteQuests(String name){
        String sqlQuery = "SELECT * FROM c WHERE c.createdBy.username != @username AND c.taken = @taken";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@username", name));
        paramList.add(new SqlParameter("@taken", false));
        return new SqlQuerySpec(sqlQuery, paramList);
    }

    private SqlQuerySpec queryOnName(String name){
        String sqlQuery = "SELECT * FROM c WHERE c.name = @name";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@name", name));
        return new SqlQuerySpec(sqlQuery, paramList);
    }
}
