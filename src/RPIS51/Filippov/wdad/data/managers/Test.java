package RPIS51.Filippov.wdad.data.managers;

/**
 * Created by Nelto on 16.10.2017.
 */
public class Test {
    public static void main(String[] args) {
        try {
            PreferencesManager preferencesManager = PreferencesManager.getInstance();
            System.out.println(preferencesManager.getClassprovider());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
