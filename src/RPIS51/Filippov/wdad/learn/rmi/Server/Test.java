package RPIS51.Filippov.wdad.learn.rmi.Server;

import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;


import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            XmlDataManagerImpl impl = new XmlDataManagerImpl("src/RPIS51/Filippov/wdad/learn/xml/CorrectXml.xml");
            User owner = new User("Frodo","steelHobbit@gmail.com");
            List<Note> nodes = impl.getNotes(owner);
           /* System.out.println(nodes.get(0).getTitle());
            System.out.println(nodes.get(0).getText());
            System.out.println(nodes.get(0).getCdate());*/
            /*HashMap<User,Integer> map = nodes.get(0).getPrivelegesMap();
            System.out.println(map.size());
            for (Map.Entry<User,Integer> us: map.entrySet()) {
                System.out.println(us.getKey().getName());
                System.out.println(us.getValue());
            }*/
            System.out.println(nodes.get(0).getCdate());
        }
        catch (Exception e){
            System.out.println("Косяяяк");
        }
    }
}
