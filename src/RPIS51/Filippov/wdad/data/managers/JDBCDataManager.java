package RPIS51.Filippov.wdad.data.managers;

import RPIS51.Filippov.wdad.data.storage.DataSourceFactory;
import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;

import javax.sql.DataSource;
import javax.xml.xpath.XPathExpressionException;
import java.rmi.RemoteException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDataManager implements DateManager {
    private DataSource source;
    private Connection connection;

    public JDBCDataManager() throws Exception {
        source = DataSourceFactory.createDataSource();
    }

    public void setConnection() throws SQLException {
        connection = source.getConnection();
    }

    @Override
    public String getNoteText(User owner, String title) throws RemoteException, SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT text FROM notes WHERE title = ? AND author_id = (SELECT id FROM users WHERE name = ?)");
        statement.setString(1, title);
        statement.setString(2, owner.getName());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) return resultSet.getString(1);
        else return null;
    }

    @Override
    public void updateNote(User owner, String title, String newText) throws RemoteException, SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE notes SET text = ? WHERE title = ? AND author_id = (SELECT id FROM users WHERE name = ?)");
        statement.setString(1, newText);
        statement.setString(2, title);
        statement.setString(3, owner.getName());
        statement.executeUpdate();
    }

    @Override
    public void setPrivileges(String noteTitle, User user, int nerRights) throws RemoteException, SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users_privileges SET privilege = ? WHERE usert_id = (SELECT id FROM users WHERE name = ?) " +
                "AND notes_id = (SELECT id FROM notes WHERE title = ?)");
        /*statement.setString(1, nerRights);
        statement.setString(2, title);
        statement.setString(3, owner.getName());*/
    }

    @Override
    public List<Note> getNotes(User owner) throws RemoteException, SQLException {
        List<Note> notes = new ArrayList<>();
        PreparedStatement note = connection.prepareStatement("SELECT * FROM notes WHERE author_id = (SELECT id FROM users WHERE name = ?)");
        return null;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}