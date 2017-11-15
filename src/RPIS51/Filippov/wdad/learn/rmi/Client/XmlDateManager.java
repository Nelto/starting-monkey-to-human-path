package RPIS51.Filippov.wdad.learn.rmi.Client;

import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface XmlDateManager extends Remote {
    String getNoteText(User owner, String title) throws RemoteException;
    void updateNote(User owner, String title, String newText)  throws RemoteException;
    void setPrivileges(String noteTitle, User user, int nerRights) throws RemoteException;
    List<Note> getNotes(User owner) throws RemoteException;
}
