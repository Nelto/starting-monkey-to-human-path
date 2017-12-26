package RPIS51.Filippov.wdad.learn.dao;

import RPIS51.Filippov.wdad.data.storage.DataSourceFactory;
import RPIS51.Filippov.wdad.learn.xml.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Users implements UsersDAO {
    private DataSource source;

    public Users() throws Exception {
        source = DataSourceFactory.createDataSource();
    }

    @Override
    public int insertUser(User user) throws SQLException {
        Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO  users (id, name, email) VALUES (?,?,?)");
        statement.setInt(1, user.getId());
        statement.setString(2,user.getName());
        statement.setString(3,user.getMail());
        int res = statement.executeUpdate();
        connection.close();
        return res;
    }

    @Override
    public boolean deleteUser(User user)  {
        try {
            Connection connection = source.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            statement.setInt(1, user.getId());
            statement.executeUpdate();
            connection.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User findUser(int id) throws SQLException {
        Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name,email FROM users WHERE id=?");
        statement.setInt(1,id);
        ResultSet set = statement.executeQuery();
        User user = new User(id,set.getString(1),set.getString(2));
        connection.close();
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            Connection connection = source.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET name = ?,email=? WHERE id=?");
            statement.setString(1,user.getName());
            statement.setString(2,user.getMail());
            statement.setInt(3,user.getId());
            statement.executeUpdate();
            connection.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean saveOrUpdateUser(User user) throws SQLException {
        Connection connection  = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
        statement.setInt(1,user.getId());
        ResultSet set = statement.executeQuery();
        connection.close();
        if (set.next()) return updateUser(user);
        else return insertUser(user)>0;
    }

    @Override
    public Collection<User> findUsersByName(String name) throws SQLException {
        Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id,email FROM users WHERE name = ?");
        statement.setString(1,name);
        ResultSet set = statement.executeQuery();
        connection.close();
        Collection<User> users =  new ArrayList<>();
        while (set.next()){
         users.add(new User(set.getInt(1),name,set.getString(2)));
        }
        return users;
    }

    @Override
    public Collection<User> findUsersByEMail(String eMail) throws SQLException {
        Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id,name FROM users WHERE email = ?");
        statement.setString(1,eMail);
        ResultSet set = statement.executeQuery();
        connection.close();
        Collection<User> users =  new ArrayList<>();
        while (set.next()){
            users.add(new User(set.getInt(1),set.getString(2),eMail));
        }
        return users;
    }
}
