package RPIS51.Filippov.wdad.data.managers;

import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;

import java.util.List;
import java.util.Map;

public class TestJDBC {
    public static void main(String[] args) {
        try {
            JDBCDataManager manager = new JDBCDataManager();
            User user = new User("Sauron","greatEvil@mail.ru");
            User user1 = new User("Saruman","EvilHelper@mail.ru");
            System.out.println(manager.getNoteText(user,"EVIIL"));
            //manager.updateNote(user,"EVIIL","Seize the middle of the earth!!!");
            manager.setPrivileges("EVIIL",user,3);
            List<Note> notes = manager.getNotes(user);
            /*for (Note note:notes) {
                System.out.println("Title: "+note.getTitle());
                System.out.println("Text: "+note.getText());
                System.out.println("Date: "+note.getCdate());
                System.out.println("Users:");
                for (Map.Entry<User, Integer> us : note.getPrivelegesMap().entrySet()) {
                    System.out.printf("%s  %d ",us.getKey().getName(),us.getValue());
                }
            }*/
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
