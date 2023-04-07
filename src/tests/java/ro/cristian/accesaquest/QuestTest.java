package ro.cristian.accesaquest;

import org.json.simple.JSONObject;
import ro.cristian.accesaquest.database.DataAccess;
import ro.cristian.accesaquest.database.PlayerDB;
import ro.cristian.accesaquest.database.QuestDB;
import ro.cristian.accesaquest.models.JSON;
import ro.cristian.accesaquest.models.Player;
import ro.cristian.accesaquest.models.Quest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestTest {
    @Test
    public void createClassTest(){
        Player p = new Player("a", "a","a");
        JSONObject player = p.createJSON();
        String name = "quest";
        String description = "a description";
        Quest quest = new Quest(name, description, (String) player.get("id"), 0);
        assertEquals(name, quest.getName());
        assertEquals(description, quest.getDescription());
        assertEquals(player.get("id"), quest.getCreatedBy());
    }

    @Test
    public void createQuestTest(){
        try {
            PlayerDB playerDB = new PlayerDB();
            QuestDB questDB = new QuestDB();
            Player player = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
            Quest quest = new Quest("A name", "Abcd", player.getId(), 0);
            Assertions.assertTrue(playerDB.register(player));
            Assertions.assertTrue(questDB.createQuest(quest));
            questDB.deleteQuest(quest.getId());
            playerDB.deleteById(player.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidQuestTest(){
        try {
            PlayerDB playerDB = new PlayerDB();
            QuestDB questDB = new QuestDB();
            Player player = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
            Quest quest = new Quest("A name", "Abcd", player.getId(), 0);
            Assertions.assertTrue(playerDB.register(player));
            Assertions.assertTrue(questDB.createQuest(quest));
            Assertions.assertThrows(Exception.class, () -> questDB.createQuest(quest), "A quest with this name has already been created");
            questDB.deleteQuest(quest.getId());
            playerDB.deleteById(player.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createdQuestsTest() {
        try {
            PlayerDB playerDB = new PlayerDB();
            QuestDB questDB = new QuestDB();
            Player player = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
            Quest quest = new Quest("A name", "Abcd", player.getId(), 0);
            Assertions.assertTrue(playerDB.register(player));
            Assertions.assertTrue(questDB.createQuest(quest));
            assertEquals(questDB.getCreatedQuests(player.getId()).size(), 1);
            questDB.deleteQuest(quest.getId());
            playerDB.deleteById(player.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createQuestPlayerListTest() {
        try {
            PlayerDB playerDB = new PlayerDB();
            QuestDB questDB = new QuestDB();
            Player player = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
            Quest quest = new Quest("A name", "Abcd", player.getId(), 0);
            Assertions.assertTrue(playerDB.register(player));
            Assertions.assertTrue(questDB.createQuest(quest));
            assertEquals(questDB.getCreatedQuests(player.getId()).size(), 1);
            var json = playerDB.findPlayerById(player.getId());
            var playerNew = JSON.deserializeJSONPlayer(json);
            assertEquals(playerNew.getQuestsCreated_id().size(), 1);

            questDB.deleteQuest(quest.getId());
            playerDB.deleteById(player.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void acceptQuestTest() {
        try{
            PlayerDB playerDB = new PlayerDB();
            QuestDB questDB = new QuestDB();
            Player playerCreator = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
            Quest quest = new Quest("A name", "Abcd", playerCreator.getId(), 0);
            Assertions.assertTrue(playerDB.register(playerCreator));
            Assertions.assertTrue(questDB.createQuest(quest));

            Player playerTaker = new Player("Shugy", "cristian.csete06@gmail.com", "1234Cc");
            Assertions.assertTrue(playerDB.register(playerTaker));
            Assertions.assertTrue(questDB.takeQuest(playerTaker.getId(), quest.getId()));

            questDB.deleteQuest(quest.getId());
            playerDB.deleteById(playerCreator.getId());
            playerDB.deleteById(playerTaker.getId());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void completeQuestTest(){
        try{
            PlayerDB playerDB = new PlayerDB();
            QuestDB questDB = new QuestDB();
            Player playerCreator = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
            Quest quest = new Quest("A name", "Abcd", playerCreator.getId(), 0);
            Assertions.assertTrue(playerDB.register(playerCreator));
            Assertions.assertTrue(questDB.createQuest(quest));

            Player playerTaker = new Player("Shugy", "cristian.csete06@gmail.com", "1234Cc");
            Assertions.assertTrue(playerDB.register(playerTaker));
            Assertions.assertTrue(questDB.takeQuest(playerTaker.getId(), quest.getId()));

            Assertions.assertTrue(questDB.completeQuest(playerTaker.getId(), quest.getId()));
            Assertions.assertTrue(questDB.completeQuest(playerCreator.getId(), quest.getId()));

            //Assertions.assertThrows(Exception.class, () -> questDB.deleteQuest(quest.getId()), "Quest is not in the db");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
