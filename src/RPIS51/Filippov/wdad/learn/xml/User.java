package RPIS51.Filippov.wdad.learn.xml;

import org.w3c.dom.NamedNodeMap;

import javax.xml.soap.Node;
import java.io.Serializable;

/**
 * Created by Nelto on 01.10.2017.
 */
public class User implements Serializable {
    private int id;
    private String name;
    private String mail;
    static final User ALL = new User("ALL", null);

    public User() {
    }

    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public User(int id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

    public static User getALL() {
        return ALL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
