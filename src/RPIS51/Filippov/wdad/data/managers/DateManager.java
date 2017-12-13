package RPIS51.Filippov.wdad.data.managers;

import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;

import javax.xml.xpath.XPathExpressionException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface DateManager extends Remote {
    String getNoteText(User owner, String title) throws RemoteException, SQLException;
    void updateNote(User owner, String title, String newText) throws RemoteException, SQLException;
    void setPrivileges(String noteTitle, User user, int nerRights) throws RemoteException, SQLException;
    List<Note> getNotes(User owner) throws RemoteException, XPathExpressionException, ParseException, SQLException;
}
