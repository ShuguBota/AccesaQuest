package ro.cristian.accesaquest.models;

import org.json.simple.JSONObject;

import java.util.UUID;

public class Quest implements JSON {
    private final String name;
    private final String description;
    private final String createdBy_id;
    private final String takenBy_id;
    private final boolean completedTaker; //The one who took it needs to accept it
    private final boolean acceptedCreator; //The one accepted need to accept the quest is done
    private final String id;
    private final int tokens;

    public Quest(String name, String description, String createdBy_id, int tokens){
        this.id = UUID.randomUUID().toString();

        this.name = name;
        this.description = description;
        this.createdBy_id = createdBy_id;
        this.tokens = tokens;

        this.takenBy_id = null;

        this.acceptedCreator = false;
        this.completedTaker = false;
    }

    /**
     * @return return the JSON object for the model
     */
    @Override
    public JSONObject createJSON() {
        JSONObject json = new JSONObject();

        json.put("id", id);

        json.put("name", name);
        json.put("description", description);
        json.put("createdBy_id", createdBy_id);
        json.put("tokens", tokens);

        json.put("takenBy_id", takenBy_id);

        json.put("acceptedCreator", acceptedCreator);
        json.put("completedTaker", completedTaker);

        return json;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedBy() {
        return createdBy_id;
    }
}
