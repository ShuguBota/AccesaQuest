package ro.cristian.accesaquest.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String username;
    private final String email;
    private String password;

    private Integer tokens;

    private List<Badge> badges;

    private Integer rank;

    public Player(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

        this.tokens = 0;
        this.badges = new ArrayList<>();
        this.rank = 1;
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
}
