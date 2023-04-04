package ro.cristian.accesaquest.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Badge {
    private static final Logger logger = LogManager.getLogger(Badge.class);
    private String name;
    private String description;
    private String imagePath;

    public Badge(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;

        logger.info("Badge object created");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }
}
