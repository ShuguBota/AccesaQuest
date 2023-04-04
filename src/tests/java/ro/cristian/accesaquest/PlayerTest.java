package ro.cristian.accesaquest;

import ro.cristian.accesaquest.models.Player;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void createClassTest(){
        System.out.println("Beginning test");
        String name = "me";
        String email = "me@me.com";
        String password = "123";

        Player player = new Player(name, email, password);
        assertEquals(name, player.getUsername());
        assertEquals(email, player.getEmail());
        assertEquals(password, player.getPassword());
    }
}
