package ro.cristian.accesaquest.database;

import ro.cristian.accesaquest.models.Player;

import java.util.List;

public interface PlayerDBI {

    /**
     * @return all the players from the db
     */
    public List<Player> getAll();

    /**
     * Add a player to the database
     * @param player the player data that needs to be added
     * @return whether operation was successful
     */
    public boolean createPlayer(Player player);

    /**
     * Remove a player from the database
     * @param email email of the player
     * @param password password of the player
     * @return whether operation was successful
     */
    public boolean removePlayer(String email, String password);

    /**
     * Update a player from the database
     * @param idPlayer the id of the player needed to change
     * @param newData the player data
     * @return whether operation was successful
     */
    public boolean updatePlayer(String idPlayer, Player newData);


    /**
     * @param email email of the player
     * @param password password of the player
     * @return if the account exists or not
     */
    public boolean login(String email, String password);

}
