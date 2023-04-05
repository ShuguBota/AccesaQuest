package ro.cristian.accesaquest.database;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;

import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.Config;
import ro.cristian.accesaquest.models.Player;

import java.util.List;
import java.util.logging.Logger;

public class PlayerDB implements PlayerDBI {
    private static final Logger logger = Logger.getLogger("| PlayerDB | ");
    private static final CosmosClient client = DataAccess.getClient();
    private static final CosmosDatabase db = client.getDatabase(Config.databaseID);
    private static final CosmosContainer container = db.getContainer(Config.playersContainer);

    /**
     * @return all the players from the db
     */
    @Override
    public List<Player> getAll() {
        return null;
    }

    /**
     * Add a player to the database
     *
     * @param player the player data that needs to be added
     * @return whether operation was successful
     */
    @Override
    public boolean createPlayer(Player player) {
        JSONObject json = player.createJSON();

        try{
            container.createItem(json);
        } catch(CosmosException e){
            logger.info("Error creating the player");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Remove a player from the database
     *
     * @param email    email of the player
     * @param password password of the player
     * @return whether operation was successful
     */
    @Override
    public boolean removePlayer(String email, String password) {
        return false;
    }

    /**
     * Update a player from the database
     *
     * @param idPlayer the id of the player needed to change
     * @param newData  the player data
     * @return whether operation was successful
     */
    @Override
    public boolean updatePlayer(String idPlayer, Player newData) {
        return false;
    }

    /**
     * @param email    email of the player
     * @param password password of the player
     * @return if the account exists or not
     */
    @Override
    public boolean login(String email, String password) {
        String sqlQuery = "SELECT * FROM c WHERE c.email = '" + email + "' AND c.password = '" + password + "'";

        JSONObject res = DataAccess.queryDB("players", sqlQuery);
        return res != null;
    }
}
