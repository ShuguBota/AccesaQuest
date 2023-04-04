package ro.cristian.accesaquest.models;

public class Quest {
    /*
    + name: String
    + descrption: String (maybe more than String)
    + createdBy: String
    + taken: Boolean
     */

    private String name;
    private String description;
    private Player createdBy;
    private Boolean taken;

    public Quest(String name, String description, Player createdBy){
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.taken = false;
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
