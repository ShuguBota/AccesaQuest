package ro.cristian.accesaquest.database;

import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.App;
import ro.cristian.accesaquest.models.Player;
import ro.cristian.accesaquest.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PlayerDB implements PlayerDBI {
    private final String container = Config.playersContainer;

    /**
     * Add a player to the database
     * @param player the player data that needs to be added
     * @return whether operation was successful
     */
    @Override
    public boolean register(Player player) throws Exception{
        if(!validEmail(player.getEmail())) throw new Exception("Invalid email");
        if(!validPassword(player.getPassword())) throw new Exception("Invalid password 4 to 8 character both lower and upper case and number required");

        //Check for email
        JSONObject resEmail = DataAccess.findObject(container, queryOnEmail(player.getEmail()));
        if(resEmail != null) throw new Exception("Email already in use");

        //Check for username
        JSONObject resUsername = DataAccess.findObject(container, queryOnUsername(player.getUsername()));
        if(resUsername != null) throw new Exception("Username already in use");

        JSONObject json = player.createJSON();
        DataAccess.createObject(container, json);

        //Set the myPlayer
        if(App.getInstance() != null) App.getInstance().setMyPlayer(json);
        return true;
    }

    /**
     * @param email      email of the player
     * @param password   password of the player
     * @return           if the account exists or not
     * @throws Exception something went wrong with the request to the db
     */
    @Override
    public boolean login(String email, String password) throws Exception {
        JSONObject res = DataAccess.findObject(container, queryOnEmail(email));

        //Email not found in the db
        if(res == null) throw new Exception("Email not found in the db");
        //Check if password match
        if(!res.get("password").equals(password)) throw new Exception("Incorrect credentials");

        //Set the myPlayer
        if(App.getInstance() != null) App.getInstance().setMyPlayer(res);
        return true;
    }

    /**
     * Find a player by ID
     *
     * @param id id of the player
     * @return the JSONObject of the player in the db
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public JSONObject findPlayerById(String id) throws Exception {
        JSONObject res = DataAccess.findObject(container, queryOnId(id));

        if(res == null) throw new Exception("Id not found in the db");

        return res;
    }

    /**
     * Delete a player by ID
     *
     * @param id         id of the player
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean deleteById(String id) throws Exception {
        int res = DataAccess.deleteObject(container, id);
        if(res >= 300) throw new Exception("Db error, Status code: " + res);
        return true;
    }

    /**
     * Replace a player info
     *
     * @param player     player object
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public boolean replace(Player player) throws Exception {
        int res = DataAccess.replaceObject(container, player.createJSON());
        if(res >= 300) throw new Exception("Db error, Status code: " + res);
        return true;
    }

    /**
     * Find the top ranked players
     *
     * @param amount amount of players to get back
     * @return the list of top ranked players
     * @throws Exception if the operation was unsuccessful
     */
    @Override
    public List<JSONObject> findTop(int amount) throws Exception {
        String sqlQuery = "SELECT TOP @amount * FROM c ORDER BY c.rank DESC";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@amount", amount));
        SqlQuerySpec sqlQuerySpec = new SqlQuerySpec(sqlQuery, paramList);

        System.out.println("Hel");

        var response = DataAccess.findListObjects(container, sqlQuerySpec);

        if(response == null) throw new Exception("Something went wrong with the db");

        System.out.println(response.size());
        return response;
    }

    /**
     * Generate query based on email
     *
     * @param email email of the player
     * @return      the query related to it
     */
    private SqlQuerySpec queryOnEmail(String email){
        String sqlQuery = "SELECT * FROM c WHERE c.email = @email";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@email", email));
        return new SqlQuerySpec(sqlQuery, paramList);
    }

    /**
     * Generate query based on username
     *
     * @param username username of the player
     * @return         the query related to it
     */
    private SqlQuerySpec queryOnUsername(String username) {
        String sqlQuery = "SELECT * FROM c WHERE c.username = @username";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@username", username));
        return new SqlQuerySpec(sqlQuery, paramList);
    }

    /**
     * Generate query based on id
     *
     * @param id id of the player
     * @return   the query related to it
     */
    private SqlQuerySpec queryOnId(String id) {
        String sqlQuery = "SELECT * FROM c WHERE c.id = @id";
        var paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@id", id));
        return new SqlQuerySpec(sqlQuery, paramList);
    }

    /**
     * OWASP email validation
     *
     * @param email email to validate
     * @return true if the validation passes
     */
    private boolean validEmail(String email){
        String regexPattern = "^[a-zA-Z\\d_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z\\d-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regexPattern);

        return pattern.matcher(email).matches();
    }

    /**
     * OWASP simple password validation
     *
     * @param password password to validate
     * @return true if the validation passes
     */
    private boolean validPassword(String password){
        String regexPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
        Pattern pattern = Pattern.compile(regexPattern);

        return pattern.matcher(password).matches();
    }
}
