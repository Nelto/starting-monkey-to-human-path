package RPIS51.Filippov.wdad.data.managers;

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
 * Created by Nelto on 16.10.2017.
 */
public class PreferencesManager {
    private static volatile PreferencesManager instance;
    private Document document;
    private final File appconfig = new File("src/RPIS51/Filippov/wdad/resources/configuration/appconfig.xml");

    private PreferencesManager() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(appconfig);
    }

    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            instance = new PreferencesManager();
        return instance;
    }

    private Element getElement(String tagName) {
        NodeList elements = document.getElementsByTagName(tagName);
        return (Element) elements.item(0);
    }

    public String getCreateregistry() {
        return getElement("createregistry").getTextContent();
    }

    public void setCreateregistry(String value) {
        getElement("createregistry").setTextContent(value);
        saveProperty();
    }

    public String getRegistryaddress() {
        return getElement("registryaddress").getTextContent();
    }

    public void setRegistryaddress(String value) {
        getElement("registryaddress").setTextContent(value);
        saveProperty();
    }

    public int getRegistryport() {
        return Integer.parseInt(getElement("registryport").getTextContent());
    }

    public void setRegistryport(int value) {
        getElement("registryport").setTextContent(String.valueOf(value));
        saveProperty();
    }

    public String getPolicypath() {
        return getElement("policypath").getTextContent();
    }

    public void setPolicypath(String value) {
        getElement("policypath").setTextContent(value);
        saveProperty();
    }

    public String getUsecodebaseonly() {
        return getElement("usecodebaseonly").getTextContent();
    }

    public void setUsecodebaseonly(String value) {
        getElement("usecodebaseonly").setTextContent(value);
        saveProperty();
    }


    public String getClassprovider() {
        return getElement("classprovider").getTextContent();
    }

    public void setClassprovider(String value) {
        getElement("classprovider").setTextContent(value);
        saveProperty();
    }

    private void saveProperty() {
        try {
            DOMSource dom_source = new DOMSource(document);
            StreamResult out_stream = new StreamResult(appconfig);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DocumentType docType = document.getDoctype();
            String systemID = docType.getSystemId();
            String res = systemID;
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, res);
            transformer.transform(dom_source, out_stream);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex.getMessage());
        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
