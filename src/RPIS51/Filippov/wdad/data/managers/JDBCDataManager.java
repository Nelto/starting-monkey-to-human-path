package RPIS51.Filippov.wdad.data.managers;

import RPIS51.Filippov.wdad.data.storage.DataSourceFactory;
import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;

import javax.sql.DataSource;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCDataManager implements DataManager {
    private DataSource source;

    public JDBCDataManager() throws Exception {
        source = DataSourceFactory.createDataSource();
    }

    @Override
    public String getNoteText(User owner, String title) throws RemoteException, SQLException {
        Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT text FROM notes JOIN users WHERE title = ? AND author_id = users.id AND email = ?");
        statement.setString(1, title);
        statement.setString(2, owner.getMail());
        ResultSet resultSet = statement.executeQuery();
        String s = null;
        if (resultSet.next()) s =  resultSet.getString(1);
        connection.close();
        return s;
    }

    @Override
    public void updateNote(User owner, String title, String newText) throws RemoteException, SQLException {
        Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE notes JOIN users SET text = ?  WHERE title = ? AND author_id = users.id AND email = ?");
        statement.setString(1, newText);
        statement.setString(2, title);
        statement.setString(3, owner.getMail());
        statement.executeUpdate();
        connection.close();
    }

    @Override
    public void setPrivileges(String noteTitle, User user, int newRights) throws RemoteException, SQLException {
        Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT users_privileges.id, privilege FROM users_privileges JOIN notes,users WHERE notes_id =  notes.id AND title = ?" +
                " AND user_id =  users.id AND email = ?");
        statement.setString(1, noteTitle);
        statement.setString(2, user.getMail());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            statement.executeUpdate(changeRight(resultSet,newRights));
        }else {
            statement = connection.prepareStatement("SELECT notes.id, users.id FROM notes,users WHERE email = ? AND title = ?");
            statement.setString(1,user.getMail());
            statement.setString(2,noteTitle);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            statement.executeUpdate("INSERT INTO users_privileges (privilege, notes_id, user_id)VALUES " +
                    "("+newRights+", "+resultSet.getInt(1)+", "+resultSet.getInt(2)+")");
        }
        connection.close();
    }

    private String changeRight(ResultSet resultSet,int newRights) throws SQLException {
        if (resultSet.getInt(2)==0) return "DELETE FROM users_privileges WHERE id = "+resultSet.getInt(1);
        else return ("UPDATE users_privileges SET privilege = "+newRights+"  WHERE id = "+resultSet.getInt(1));
    }

    @Override
    public List<Note> getNotes(User owner) throws RemoteException, SQLException {
        Connection connection = source.getConnection();
        List<Note> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM notes JOIN users WHERE author_id = users.id  AND  email = ?");
        statement.setString(1,owner.getMail());
        ResultSet notes = statement.executeQuery();
        Note note = new Note(owner);
        while (notes.next()){
            note.setTitle(notes.getString(2));
            note.setCdate(notes.getDate(3));
            note.setText(new StringBuilder(notes.getString(4)));
            note.setPrivelegesMap(getUsers(notes.getInt(1)));
            result.add(note);
        }
        connection.close();
        return result;
    }

    private HashMap<User,Integer> getUsers(int noteId) throws SQLException {
        Connection connection = source.getConnection();
        HashMap<User,Integer> users = new HashMap<>();
        Statement statement = connection.createStatement();
        ResultSet priv = statement.executeQuery("SELECT privilege, email, name FROM users_privileges, users WHERE notes_id = "+noteId+" AND user_id = users.id");
        while (priv.next()){
           users.put(new User(priv.getString(3),priv.getString(2)),priv.getInt(1));
        }
        connection.close();
        return users;
    }
}