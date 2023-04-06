package ro.cristian.accesaquest.database;

import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.Quest;

import java.util.List;

public interface QuestDBI {
    /**
     * Add a quest to the database
     * @param quest the quest data that needs to be added
     * @return whether operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean createQuest(Quest quest) throws Exception;

    /**
     * Get quests to display that are up for taking
     * @param amount the max amount of quests to get back
     * @return List of JSONObjects containing the quests
     */
    List<JSONObject> getIncompleteQuests(String name, int amount);
}
