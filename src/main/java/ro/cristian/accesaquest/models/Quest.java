package ro.cristian.accesaquest.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Quest {
    private static final Logger logger = LogManager.getLogger(Quest.class);

    private String name;
    private String description;
    private Player createdBy;
    private Boolean taken;

    public Quest(String name, String description, Player createdBy){
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.taken = false;

        logger.info("Quest object created");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Player getCreatedBy() {
        return createdBy;
    }

    public Boolean getTaken() {
        return taken;
    }
}
