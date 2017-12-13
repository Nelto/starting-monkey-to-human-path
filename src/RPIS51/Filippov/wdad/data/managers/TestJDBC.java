package RPIS51.Filippov.wdad.data.managers;

import RPIS51.Filippov.wdad.learn.xml.User;

public class TestJDBC {
    public static void main(String[] args) {
        try {
            JDBCDataManager manager = new JDBCDataManager();
            manager.setConnection();
            User user = new User("Sauron","greatEvil@mail.ru");
            System.out.println(manager.getNoteText(user,"EVIIL"));
            manager.closeConnection();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
