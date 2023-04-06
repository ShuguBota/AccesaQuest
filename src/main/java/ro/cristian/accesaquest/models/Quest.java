package ro.cristian.accesaquest.models;

import org.json.simple.JSONObject;

import java.util.UUID;
import java.util.logging.Logger;

public class Quest implements JSON{
    private static final Logger logger = Logger.getLogger("| Quest | ");

    private String name;
    private String description;
    private JSONObject createdBy;
    private Boolean taken;
    private String uuid;

    public Quest(String name, String description, JSONObject createdBy){
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.taken = false;
        this.uuid = UUID.randomUUID().toString();

        logger.info("Quest object created");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public JSONObject getCreatedBy() {
        return createdBy;
    }

    public Boolean getTaken() {
        return taken;
    }

    /**
     * @return return the JSON object for the model
     */
    @Override
    public JSONObject createJSON() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("description", description);
        json.put("createdBy", createdBy);
        json.put("taken", taken);
        json.put("id", uuid);

        return json;
    }
}
