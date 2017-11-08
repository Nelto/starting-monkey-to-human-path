package RPIS51.Filippov.wdad.data.managers;

/**
 * Created by Nelto on 16.10.2017.
 */
public class PreferTest {
    public static void main(String[] args) {
        try {
            PreferencesManager preferencesManager = PreferencesManager.getInstance();
            /*System.out.println(preferencesManager.getProperty("appconfig/rmi/server/registry/createregistry"));
            preferencesManager.setProperty("appconfig/rmi/server/registry/createregistry","yes");
            preferencesManager.updateDocument();
            System.out.println(preferencesManager.getProperty("appconfig/rmi/server/registry/createregistry"));*/
          /* preferencesManager.addBindedObject("asd","asd");
           preferencesManager.updateDocument();*/
            preferencesManager.removeBindedObject("asd");
            preferencesManager.updateDocument();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
