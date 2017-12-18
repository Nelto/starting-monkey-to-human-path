package RPIS51.Filippov.wdad.learn.rmi.Server;

import RPIS51.Filippov.wdad.data.managers.DataManager;
import RPIS51.Filippov.wdad.learn.xml.Note;
import RPIS51.Filippov.wdad.learn.xml.User;
import RPIS51.Filippov.wdad.learn.xml.XmlTask;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class XmlDataManagerImpl extends XmlTask implements DataManager {
   private XPath xPath;
    public XmlDataManagerImpl(String filepath) {
        super(filepath);
        xPath = XPathFactory.newInstance().newXPath();
    }

    @Override
    public List<Note> getNotes(User owner) throws RemoteException {
        try {
            XPathExpression xPathExpression;
            List<Node> notes = getCheckNote(owner);
            Note newNote = new Note(owner);
            Node note = null;
            List<Note> notesList = new ArrayList<>();
            for (int i = 0; i < notes.size(); i++) {
                note = notes.get(i);
                newNote.setTitle(getTitle(note));
                newNote.setText(getText(note));
                newNote.setCdate(getCDate(note));
                newNote.setPrivelegesMap(getPrivileges(note));
                notesList.add(newNote);
            }
            return notesList;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    private List<Node> getCheckNote(User owner){
        try {
           XPathExpression xPathExpression = xPath.compile("notes/note/owner[@name ='"+owner.getName()+"'][@mail ='"+owner.getMail()+"']");
            NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
            List<Node> notes = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                notes.add(nodeList.item(i).getParentNode());
            }
            return notes;
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String getTitle(Node note) throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("title/text()");
        return (String) xPathExpression.evaluate(note,XPathConstants.STRING);
    }

    private StringBuilder getText(Node note) throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("text/text()");
        StringBuilder builder = new StringBuilder();
        return builder.append(xPathExpression.evaluate(note,XPathConstants.STRING));
    }

    public Date getCDate(Node note) throws XPathExpressionException, ParseException {
        XPathExpression xPathExpression = xPath.compile("cdate/text()");
        String date = (String) xPathExpression.evaluate(note,XPathConstants.STRING);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd-MM-yyyy");
        return format.parse(date);
    }

    private HashMap<User,Integer> getPrivileges(Node note) throws XPathExpressionException {
        HashMap<User,Integer> users = new HashMap<>();
        XPathExpression xPathExpression = xPath.compile("privileges/user");
        NodeList nodes =(NodeList) xPathExpression.evaluate(note,XPathConstants.NODESET);
        if (nodes.getLength() ==0) {
            xPathExpression = xPath.compile("privileges/ALL/@rights/text()");
            users.put(User.getALL(),getRight((String) xPathExpression.evaluate(note,XPathConstants.STRING)));
            return users;
        }

        NamedNodeMap attr = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            attr = nodes.item(i).getAttributes();
            users.put(new User(attr.item(1).getTextContent(),attr.item(0).getTextContent()),getRight(attr.item(2).getTextContent()));
        }
        return users;
    }

    private Integer getRight(String s){
        switch (s){
            case ("RW"): return 3;
            case ("R"): return 1;
        }
            return 0;
    }
    

    private boolean checkOwner(Element note,User owner){
        NodeList owners = note.getElementsByTagName("owner");
        NamedNodeMap attrib = owners.item(0).getAttributes();
        return  (attrib.item(0).getTextContent().equals(owner.getMail()) && attrib.item(1).getTextContent().equals(owner.getName()));
    }
}
