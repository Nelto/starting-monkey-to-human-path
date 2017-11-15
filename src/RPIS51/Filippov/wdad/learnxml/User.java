package RPIS51.Filippov.wdad.learnxml;

import javax.xml.soap.Name;

/**
 * Created by Nelto on 01.10.2017.
 */
public class User {
    private String name;
    private String mail;
    static final User ALL = new User("ALL", null);

    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
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

}
