package RPIS51.Filippov.wdad.learn.xml;

import java.util.Date;
import java.util.HashMap;

public class Note {
    private User owner;
    private String title;
    private StringBuilder text;
    private Date cdate;
    private HashMap<User,Integer> privelegesMap;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StringBuilder getText() {
        return text;
    }

    public void setText(StringBuilder text) {
        this.text = text;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public HashMap<User, Integer> getPrivelegesMap() {
        return privelegesMap;
    }

    public void setPrivelegesMap(HashMap<User, Integer> privelegesMap) {
        this.privelegesMap = privelegesMap;
    }
}
