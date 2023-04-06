package ro.cristian.accesaquest.database;

import ro.cristian.accesaquest.models.Player;

import java.util.List;

public interface PlayerDBI {
    /**
     * Add a player to the database
     * @param player the player data that needs to be added
     * @return whether operation was successful
     * @throws Exception if the operation was unsuccessful
     */
    boolean createPlayer(Player player) throws Exception;

    /**
     * @param email email of the player
     * @param password password of the player
     * @return if the account exists or not
     */
    boolean login(String email, String password);

}
