package RPIS51.Filippov.wdad.learn.rmi.Server;

import RPIS51.Filippov.wdad.data.managers.PreferencesManager;
import RPIS51.Filippov.wdad.utils.PreferencesConstantManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            XmlDateManagerImpl obj = new XmlDateManagerImpl("src/RPIS51/Filippov/wdad/learn/xml/CorrectXml.xml");
            Registry registry = null;
            PreferencesManager manager = PreferencesManager.getInstance();
            int registryport = Integer.parseInt(manager.getProperty(PreferencesConstantManager.REGISTRYPORT));

            if (manager.getProperty(PreferencesConstantManager.CREATEREGISTRY).equals("yes")) {
                registry = LocateRegistry.createRegistry(registryport);
            } else registry = LocateRegistry.getRegistry(registryport);

            UnicastRemoteObject.exportObject(obj, 0);
            registry.rebind("Note", obj);
            //manager.addBindedObject(manager.getName("Note"),DateManager.class.getCanonicalName());
            //manager.updateDocument();
            System.out.println("Server ready");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
