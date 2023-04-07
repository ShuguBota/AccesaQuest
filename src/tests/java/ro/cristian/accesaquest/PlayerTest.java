package ro.cristian.accesaquest;

import org.json.simple.JSONObject;
import ro.cristian.accesaquest.database.PlayerDB;
import ro.cristian.accesaquest.models.Player;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    PlayerDB playerDB = new PlayerDB();

    @Test
    public void createClassTest(){
        System.out.println("Beginning test");
        String name = "ShuguBota";
        String email = "cristiancsete06@gmail.com";
        String password = "1234Cc";
        Player player = new Player(name, email, password);
        assertEquals(name, player.getUsername());
        assertEquals(email, player.getEmail());
        assertEquals(password, player.getPassword());
    }

    @Test
    public void registerTest() {
        Player player = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");

        try {
            Assertions.assertTrue(playerDB.register(player));
            playerDB.deleteById(player.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest() {
        try{
            Assertions.assertTrue(playerDB.login("p", "p"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidLogin() {
        try{
            Assertions.assertThrows(Exception.class, () -> playerDB.login("p", "poop"), "Incorrect credentials");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidLogin2() {
        try{
            Assertions.assertThrows(Exception.class, () -> playerDB.login("poop", "p"), "Email not found in the db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidUsername() {
        try{
            Player player = new Player("p", "cristiancsete06@gmail.com", "1234Cc");
            Assertions.assertThrows(Exception.class, () -> playerDB.register(player), "Username already in use");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void replaceTest() {
        Player player = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
        try{
            Assertions.assertTrue(playerDB.register(player));
            player.setUsername("Bob");
            playerDB.replace(player);

            JSONObject json = playerDB.findPlayerById(player.getId());
            assertEquals(json.get("username"), player.getUsername());
            playerDB.deleteById(player.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidEmail() {
        Player player = new Player("ShuguBota", "abc", "1234Cc");
        try{
            Assertions.assertThrows(Exception.class, () -> playerDB.register(player), "Invalid email");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidPassword() {
        Player player = new Player("ShuguBota", "cristiancsete06@gmai.com", "1");
        try{
            Assertions.assertThrows(Exception.class, () -> playerDB.register(player), "Invalid password 4 to 8 character both lower and upper case and number required");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void alreadyRegistered() {
        Player player = new Player("ShuguBota", "cristiancsete06@gmail.com", "1234Cc");
        try{
            Assertions.assertTrue(playerDB.register(player));
            Assertions.assertThrows(Exception.class, () -> playerDB.register(player), "Email already in use");
            playerDB.deleteById(player.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
