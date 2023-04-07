package ro.cristian.accesaquest.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player implements JSON{
    private String username;
    private final String email;
    private final String password;
    private final Integer tokens;
    private final List<String> badges_id;
    private final Integer rank;
    private final String id;
    private final List<String> questsCreated_id;
    private final List<String> questsAccepted_id;

    public Player(String username, String email, String password) {
        this.id = UUID.randomUUID().toString();

        this.username = username;
        this.email = email;
        this.password = password;

        this.tokens = 0;
        this.rank = 1;
        this.badges_id = new ArrayList<>();

        this.questsCreated_id = new ArrayList<>();
        this.questsAccepted_id = new ArrayList<>();
    }

    /**
     * @return return the JSON object for the model
     */
    @Override
    public JSONObject createJSON() {
        JSONObject json = new JSONObject();

        json.put("id", id);

        json.put("email", email);
        json.put("username", username);
        json.put("password", password);

        json.put("tokens", tokens);
        json.put("rank", rank);
        JSONArray badgesJSON = new JSONArray();
        badgesJSON.addAll(badges_id);
        json.put("badges_id", badgesJSON);

        JSONArray questsCreatedJSON = new JSONArray();
        questsCreatedJSON.addAll(questsCreated_id);
        json.put("questsCreated_id", questsCreatedJSON);
        JSONArray questsAcceptedJSON = new JSONArray();
        questsAcceptedJSON.addAll(questsAccepted_id);
        json.put("questsAccepted_id", questsAcceptedJSON);

        return json;
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

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
