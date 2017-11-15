package RPIS51.Filippov.wdad.data.managers;

import RPIS51.Filippov.wdad.utils.PreferencesConstantManager;
import org.w3c.dom.*;

import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Nelto on 16.10.2017.
 */
public class PreferencesManager {
    private static volatile PreferencesManager instance;
    private Document document;
    private final File appconfig = new File("src/RPIS51/Filippov/wdad/resources/configuration/appconfig.xml");
    private XPath xPath;


    private PreferencesManager() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(appconfig);
        xPath = XPathFactory.newInstance().newXPath();
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

    @Deprecated
    public String getCreateregistry() {
        return getElement("createregistry").getTextContent();
    }

    @Deprecated
    public void setCreateregistry(String value) {
        getElement("createregistry").setTextContent(value);
        updateDocument();
    }

    @Deprecated
    public String getRegistryaddress() {
        return getElement("registryaddress").getTextContent();
    }

    @Deprecated
    public void setRegistryaddress(String value) {
        getElement("registryaddress").setTextContent(value);
        updateDocument();
    }

    @Deprecated
    public int getRegistryport() {
        return Integer.parseInt(getElement("registryport").getTextContent());
    }

    @Deprecated
    public void setRegistryport(int value) {
        getElement("registryport").setTextContent(String.valueOf(value));
        updateDocument();
    }

    @Deprecated
    public String getPolicypath() {
        return getElement("policypath").getTextContent();
    }

    @Deprecated
    public void setPolicypath(String value) {
        getElement("policypath").setTextContent(value);
        updateDocument();
    }

    @Deprecated
    public String getUsecodebaseonly() {
        return getElement("usecodebaseonly").getTextContent();
    }

    @Deprecated
    public void setUsecodebaseonly(String value) {
        getElement("usecodebaseonly").setTextContent(value);
        updateDocument();
    }

    @Deprecated
    public String getClassprovider() {
        return getElement("classprovider").getTextContent();
    }

    @Deprecated
    public void setClassprovider(String value) {
        getElement("classprovider").setTextContent(value);
        updateDocument();
    }

    public String getProperty(String key) {
        try {
            XPathExpression xPathExpression = xPath.compile(key + "/text()");
            String property = (String) xPathExpression.evaluate(document, XPathConstants.STRING);
            return property;
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void setProperty(String key, String value) {
        try {
            XPathExpression xPathExpression = xPath.compile(key + "/text()");
            Node property = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
            property.setTextContent(value);
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
        }
    }


    public Properties getProperties()  {
        Properties properties = new Properties();
        String[] keys = {
                PreferencesConstantManager.CREATEREGISTRY,
                PreferencesConstantManager.REGISTRYADDRESS,
                PreferencesConstantManager.REGISTRYPORT,
                PreferencesConstantManager.POLICYPATH,
                PreferencesConstantManager.USECODEBASEONLY,
                PreferencesConstantManager.CLASSPROVIDER};
        for (String key : keys) {
            properties.setProperty(key, getProperty(key));
        }
        return properties;
    }

    public void setProperties(Properties prop) {
        for (Map.Entry<Object,Object> proper:prop.entrySet()) {
            setProperty((String) proper.getKey(),(String) proper.getValue());
        }
    }

    public void addBindedObject(String name, String className) throws XPathExpressionException {
            Element bindedobject = document.createElement("bindedobject");
            bindedobject.setAttribute("class", className);
            bindedobject.setAttribute("name", name);
            getElement("server").appendChild(bindedobject);
    }

    public void removeBindedObject(String name){
        try {
            XPathExpression xPathExpression = xPath.compile("appconfig/rmi/server/bindedobject[@name='"+name+"']");
            Node bindedobject = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
            getElement("server").removeChild(bindedobject);
        } catch (XPathExpressionException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateDocument() {
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
