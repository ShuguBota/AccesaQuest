package ro.cristian.accesaquest.models;

public class Badge {
    private String name;
    private String description;
    private String imagePath;

    public Badge(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
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
