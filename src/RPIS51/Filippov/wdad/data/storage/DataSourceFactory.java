package RPIS51.Filippov.wdad.data.storage;

import javax.sql.DataSource;

import RPIS51.Filippov.wdad.data.managers.PreferencesManager;
import RPIS51.Filippov.wdad.utils.PreferencesConstantManager;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.util.Formatter;

public class DataSourceFactory {
    public static DataSource createDataSource(String className, String driverType, String host, int port, String dbName, String user, String password){
        MysqlDataSource source = new MysqlDataSource();
        Formatter formatter = new Formatter();
        formatter.format("%s://%s:%d/%s",driverType,host,port,dbName);
        source.setURL(formatter.toString());
        source.setUser(user);
        source.setPassword(password);
        return source;
    }

    public static DataSource createDataSource() throws Exception {
        PreferencesManager manager = PreferencesManager.getInstance();
        return createDataSource(
                manager.getProperty(PreferencesConstantManager.CLASSNAME),
                manager.getProperty(PreferencesConstantManager.DRIVERTYPE),
                manager.getProperty(PreferencesConstantManager.HOSTNAME),
                Integer.valueOf(manager.getProperty(PreferencesConstantManager.PORT)),
                manager.getProperty(PreferencesConstantManager.DBNAME),
                manager.getProperty(PreferencesConstantManager.USER),
                manager.getProperty(PreferencesConstantManager.PASS));
    }
}
