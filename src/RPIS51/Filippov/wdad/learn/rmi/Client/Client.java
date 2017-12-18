package RPIS51.Filippov.wdad.learn.rmi.Client;


import RPIS51.Filippov.wdad.data.managers.PreferencesManager;
import RPIS51.Filippov.wdad.data.managers.DataManager;
import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;
import RPIS51.Filippov.wdad.utils.PreferencesConstantManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            PreferencesManager manager = PreferencesManager.getInstance();
            Registry registry = LocateRegistry.getRegistry(Integer.parseInt(manager.getProperty(PreferencesConstantManager.REGISTRYPORT)));
            DataManager obj = (DataManager) registry.lookup("Note");
            User owner = new User("Frodo","steelHobbit@gmail.com");
            System.out.println(obj.getNoteText(owner,"Спасение мира"));
            User user = new User("Aragon","ReunitedKingdomKing@gmail.com");
            List<Note> notes = obj.getNotes(owner);
            /*for (Map.Entry<User,Integer> map: notes.get(0).getPrivelegesMap().entrySet()) {
                System.out.print(map.getKey().getName()+": ");
                System.out.println(map.getValue());
            }*/
           // System.out.println(notes.get(0).getCdate());
            System.out.println(notes.get(0).getCdate());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
