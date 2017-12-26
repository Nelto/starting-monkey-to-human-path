package RPIS51.Filippov.wdad.learn.dao;

import RPIS51.Filippov.wdad.learn.xml.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UsersDAO {
    public int insertUser (User user) throws SQLException;
    public boolean deleteUser (User user);
    public User findUser (int id) throws SQLException;
    public boolean updateUser (User user);
    public boolean saveOrUpdateUser (User user) throws SQLException;
    public Collection<User> findUsersByName (String name) throws SQLException;
    public Collection<User> findUsersByEMail (String eMail) throws SQLException;
}
