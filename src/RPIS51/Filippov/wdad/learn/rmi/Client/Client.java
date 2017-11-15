package RPIS51.Filippov.wdad.learn.rmi.Client;


import RPIS51.Filippov.wdad.data.managers.PreferencesManager;
import RPIS51.Filippov.wdad.learn.xml.User;
import RPIS51.Filippov.wdad.utils.PreferencesConstantManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            PreferencesManager manager = PreferencesManager.getInstance();
            Registry registry = LocateRegistry.getRegistry(manager.getProperty(PreferencesConstantManager.REGISTRYPORT));
            XmlDateManager obj = (XmlDateManager) registry.lookup("Note");
            User owner = new User("Frodo","steelHobbit@gmail.com");
            System.out.println(obj.getNoteText(owner,"Спасение мира"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
