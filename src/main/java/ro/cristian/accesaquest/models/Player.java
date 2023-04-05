package ro.cristian.accesaquest.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.logging.Logger;

public class Player implements JSON{
    private static final Logger logger = Logger.getLogger("| Player | ");
    private final String username;
    private final String email;
    private String password;

    private Integer tokens;

    private List<Badge> badges;

    private Integer rank;

    private String uuid;

    public Player(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

        this.tokens = 0;
        this.badges = new ArrayList<>();
        this.rank = 1;
        this.uuid = UUID.randomUUID().toString();

        logger.info("Player object created");
    }

    public void addBadge(Badge badge){
        badges.add(badge);
    }

    public void updateTokens(Boolean toAdd, Integer tokens){
        if(toAdd)
            this.tokens += tokens;
        else
            this.tokens -= tokens;
    }

    public void rankUp(){
        rank++;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUUID(){
        return uuid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTokens() {
        return tokens;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public Integer getRank() {
        return rank;
    }

    /**
     * @return return the JSON object for the model
     */
    @Override
    public JSONObject createJSON() {
        JSONObject json = new JSONObject();

        json.put("email", email);
        json.put("username", username);
        json.put("password", password);
        json.put("tokens", tokens);
        json.put("rank", rank);

        JSONArray badgesJSON = new JSONArray();
        badgesJSON.addAll(badges);
        json.put("badges", badgesJSON);
        json.put("id", uuid);

        return json;
    }
}
