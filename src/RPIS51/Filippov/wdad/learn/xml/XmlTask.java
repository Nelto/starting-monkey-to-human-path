package RPIS51.Filippov.wdad.learn.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by Nelto on 01.10.2017.
 */
public class XmlTask {
    private File xmlfile;
    protected Document document;

    public XmlTask(String filepath) {
        try {
            xmlfile = new File(filepath);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(xmlfile);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }

    private boolean checkNote(User owner, String title, Node verifNote) {
        try {
            Element note = (Element) verifNote;
            if (titleCheck(title, note)) {
                NodeList owners = note.getElementsByTagName("owner");
                NamedNodeMap attrib = owners.item(0).getAttributes();
                return  (attrib.item(0).getTextContent().equals(owner.getMail()) && attrib.item(1).getTextContent().equals(owner.getName()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getNoteText(User owner, String title) {
        NodeList notes = document.getElementsByTagName("note");
        for (int i = 0; i < notes.getLength(); i++) {
            if (checkNote(owner, title, notes.item(i))) {
                Element note = (Element) notes.item(i);
                NodeList texts = note.getElementsByTagName("text");
                return texts.item(0).getTextContent();
            }
        }
        return null;
    }


    public void updateNote(User owner, String title, String newText) {
        NodeList notes = document.getElementsByTagName("note");
        for (int i = 0; i < notes.getLength(); i++) {
            if (checkNote(owner, title, notes.item(i))) {
                Element note = (Element) notes.item(i);
                NodeList texts = note.getElementsByTagName("text");
                texts.item(0).setTextContent(newText);
                updateDocument();
            }
        }
    }

    private boolean titleCheck(String title, Element note) {
        NodeList titles = note.getElementsByTagName("title");
        if (titles.item(0).getTextContent().equals(title))
            return true;
        else return false;
    }


    public void setPrivileges(String noteTitle, User user, int newRights) {
        try {
            NodeList notes = document.getElementsByTagName("note");
            Element note;
            for (int i = 0; i < notes.getLength(); i++) {
                note = (Element) notes.item(i);
                if (note.getElementsByTagName("title").item(0).getTextContent().equals(noteTitle)) {
                    note = (Element) note.getElementsByTagName("privileges").item(0);
                    if (!changeRights(note, user, newRights) && newRights != 0) {
                        note.appendChild(createUser(user, newRights));
                    }
                }
            }
            updateDocument();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean changeRights(Element privileges, User wantedUser, int newRight) {
        NodeList users = privileges.getElementsByTagName("user");
        NamedNodeMap attrib;
        Element user;
        for (int i = 0; i < users.getLength(); i++) {
            user = (Element) users.item(i);
            attrib = user.getAttributes();
            if (attrib.item(0).getTextContent().equals(wantedUser.getMail()) && attrib.item(1).getTextContent().equals(wantedUser.getName())) {
                switch (newRight) {
                    case 0:
                        privileges.removeChild(user);
                        return true;
                    case 1:
                        user.setAttribute("rights", "R");
                        return true;
                    case 3:
                        user.setAttribute("rights", "RW");
                        return true;
                }
            }
        }
        return false;
    }

    private Element createUser(User user, int newRights) {
        Element newUser = document.createElement("user");
        newUser.setAttribute("name", user.getName());
        newUser.setAttribute("mail", user.getMail());
        switch (newRights) {
            case 1:
                newUser.setAttribute("rights", "R");
                break;
            case 3:
                newUser.setAttribute("rights", "RW");
                break;
        }
        return newUser;
    }

    private void updateDocument() throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlfile);
            tr.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex.getMessage());
        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
