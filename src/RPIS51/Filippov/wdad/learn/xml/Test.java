package RPIS51.Filippov.wdad.learn.xml;

/**
 * Created by Nelto on 04.10.2017.
 */
public class Test {
    public static void main(String[] args) {
        User owner = new User("Frodo","steelHobbit@gmail.com");
        User user = new User("Aragon","ReunitedKingdomKing@gmail.com");
        XmlTask xmlTask = new XmlTask("src/RPIS51/Filippov/wdad/learnxml/CorrectXml.xml");
        System.out.println(xmlTask.getNoteText(owner,"Спасение мира"));
        xmlTask.updateNote(owner,"Спасение мира","Отнести кольцо в Мордер");
        xmlTask.setPrivileges("Спасение мира",user,3);
    }
}
