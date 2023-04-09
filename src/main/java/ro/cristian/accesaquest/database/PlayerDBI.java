package ro.cristian.accesaquest.database;

import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.Player;

import java.util.List;

public interface PlayerDBI {
    /**
     * Add a player to the database
     *
     * @param  player    the player data that needs to be added
     * @return           whether operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean register(Player player) throws Exception;

    /**
     * Login a player
     *
     * @param email      email of the player
     * @param password   password of the player
     * @return           if the account exists or not
     * @throws Exception if the operation was unsuccessful
     */
    boolean login(String email, String password) throws Exception;

    /**
     * Find a player by ID
     *
     * @param id         id of the player
     * @return           the JSONObject of the player in the db
     * @throws Exception if the operation was unsuccessful
     */
    JSONObject findPlayerById(String id) throws Exception;

    /**
     * Delete a player by ID
     *
     * @param id         id of the player
     * @return           if the operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean deleteById(String id) throws Exception;

    /**
     * Replace a player info
     *
     * @param player     player object
     * @return           if the operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean replace(Player player) throws Exception;


    /**
     * Find the top ranked players
     *
     * @param amount     amount of players to get back
     * @return           the list of top ranked players
     * @throws Exception if the operation was unsuccessful
     */
    List<JSONObject> findTop(int amount) throws Exception;
}
