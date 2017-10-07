package RPIS51.Filippov.wdad.learnxml;

import org.w3c.dom.*;

import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Nelto on 01.10.2017.
 */
public class XmlTask {
    private File file;
    private Document document;

    public XmlTask(String filepath) {
        try {
            file = new File(filepath);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }

   /* public String getNoteText(User owner, String title) {
        NodeList nodeList = document.getElementsByTagName("owner");
        int noteNumber = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            NamedNodeMap map = nodeList.item(i).getAttributes();
            if (map.item(0).getTextContent().equals(owner.getMail()) && map.item(1).getTextContent().equals(owner.getName())) {
                noteNumber = i;
            }
        }
        nodeList = document.getElementsByTagName("text");
        return nodeList.item(noteNumber).getTextContent();
    }*/

    private boolean checkNote(User owner, String title, Node note) {
        try {
            Element element = (Element) note;
            if (searchbyTitle(title, element)) {
                NodeList nodeList = element.getElementsByTagName("owner");
                NamedNodeMap attrib = nodeList.item(0).getAttributes();
                if (attrib.item(0).getTextContent().equals(owner.getMail()) && attrib.item(1).getTextContent().equals(owner.getName()))
                    return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getNoteText(User owner, String title) {
        NodeList nodeList = document.getElementsByTagName("note");
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (checkNote(owner, title, nodeList.item(i))) {
                Element element = (Element) nodeList.item(i);
                nodeList = element.getElementsByTagName("text");
                return nodeList.item(0).getTextContent();
            }
        }
        return null;
    }


    public void UpdateNote(User owner, String title, String newText) {
        NodeList nodeList = document.getElementsByTagName("note");
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (checkNote(owner, title, nodeList.item(i))) {
                Element element = (Element) nodeList.item(i);
                nodeList = element.getElementsByTagName("text");
                nodeList.item(0).setTextContent(newText);
                writeDocument();
            }
        }
    }

    private boolean searchbyTitle(String title, Element element) {
        NodeList nodeList = element.getElementsByTagName("title");
        if (nodeList.item(0).getTextContent().equals(title))
            return true;
        else return false;
    }


    public void setPrivileges(String noteTitle, User user, int newRights) {
        try {
            NodeList nodeList = document.getElementsByTagName("note");
            Element element;
            for (int i = 0; i < nodeList.getLength(); i++) {
                element = (Element) nodeList.item(i);
                if (element.getElementsByTagName("title").item(0).getTextContent().equals(noteTitle)) {
                    element = (Element) element.getElementsByTagName("privileges").item(0);
                    if (!changeRights(element, user, newRights) && newRights != 0) {
                        element.appendChild(createUser(user, newRights));
                    }
                }
            }
            writeDocument();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean changeRights(Element privileges, User wantedUser, int newRight) {
        NodeList nodeList = privileges.getElementsByTagName("user");
        NamedNodeMap attrib;
        Element user;
        for (int i = 0; i < nodeList.getLength(); i++) {
            user = (Element) nodeList.item(i);
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

    private void writeDocument() throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(file);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
            System.out.println("XML успешно изменен");
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
