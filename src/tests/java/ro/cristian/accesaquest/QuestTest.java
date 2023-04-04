package ro.cristian.accesaquest;

import ro.cristian.accesaquest.models.Player;
import ro.cristian.accesaquest.models.Quest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestTest {
    private static Player player;

    @BeforeAll
    public static void beforeTest(){
        String name = "me";
        String email = "me@me.com";
        String password = "123";
        player = new Player(name, email, password);
    }

    @AfterAll
    public static void afterTest(){
        //TODO: remove it from the database
        //removePlayer(player);
    }

    @Test
    public void createClassTest(){
        String name = "quest";
        String description = "a description";
        Quest quest = new Quest(name, description, player);
        assertEquals(name, quest.getName());
        assertEquals(description, quest.getDescription());
        assertEquals(player, quest.getCreatedBy());
    }
}
