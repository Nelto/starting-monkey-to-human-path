package RPIS51.Filippov.wdad.learn.rmi.Server;

import RPIS51.Filippov.wdad.learn.rmi.Client.XmlDateManager;
import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;
import RPIS51.Filippov.wdad.learn.xml.XmlTask;

import java.rmi.RemoteException;
import java.util.List;

public class XmlDateManagerImpl extends XmlTask implements XmlDateManager {
    public XmlDateManagerImpl(String filepath) {
        super(filepath);
    }

    @Override
    public List<Note> getNotes(User owner) throws RemoteException {
        return null;
    }
}
