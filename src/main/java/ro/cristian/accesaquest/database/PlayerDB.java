package ro.cristian.accesaquest.database;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;

import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.util.Config;
import ro.cristian.accesaquest.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class PlayerDB implements PlayerDBI {
    private static final Logger logger = Logger.getLogger("| PlayerDB | ");
    private final String container = "players";

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
    public boolean createPlayer(Player player) throws Exception{
        if(!validEmail(player.getEmail())) throw new Exception("Invalid email");
        if(!validPassword(player.getPassword())) throw new Exception("Invalid password 4 to 8 character both lower and upper case and number required");

        //Check for email
        JSONObject resEmail = DataAccess.queryDBObject(container, queryOnEmail(player.getEmail()));
        if(resEmail != null) throw new Exception("Email already in use");

        //Check for username
        JSONObject resUsername = DataAccess.queryDBObject(container, queryOnUsername(player.getUsername()));
        if(resUsername != null) throw new Exception("Username already in use");

        JSONObject json = player.createJSON();
        DataAccess.createDBObject(container, json);

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
        JSONObject res = DataAccess.queryDBObject(container, queryOnEmail(email));

        //Email not found in the db
        if(res == null) return false;
        //Check if password match
        return res.get("password").equals(password);
    }

    private SqlQuerySpec queryOnEmail(String email){
        String sqlQuery = "SELECT * FROM c WHERE c.email = @email";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@email", email));
        return new SqlQuerySpec(sqlQuery, paramList);
    }

    private SqlQuerySpec queryOnUsername(String username){
        String sqlQuery = "SELECT * FROM c WHERE c.username = @username";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@username", username));
        return new SqlQuerySpec(sqlQuery, paramList);
    }


    /**
     * OWASP email validation
     * @param email email to validate
     * @return true if the validation passes
     */
    private boolean validEmail(String email){
        String regexPattern = "^[a-zA-Z\\d_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z\\d-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regexPattern);

        System.out.println(email);
        return pattern.matcher(email).matches();
    }

    /**
     * OWASP simple password validation
     * @param password password to validate
     * @return true if the validation passes
     */
    private boolean validPassword(String password){
        String regexPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
        Pattern pattern = Pattern.compile(regexPattern);

        return pattern.matcher(password).matches();
    }
}
