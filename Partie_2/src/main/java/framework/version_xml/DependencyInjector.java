package framework.version_xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
    private Map<String, Object> components = new HashMap<>();

    public void loadConfig(String configFile) {
        try {
            File file = new File(configFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList componentNodes = doc.getElementsByTagName("component");
            for (int i = 0; i < componentNodes.getLength(); i++) {
                Element componentElement = (Element) componentNodes.item(i);
                String componentName = componentElement.getAttribute("name");
                String componentClass = componentElement.getAttribute("class");
                // Get constructor with no parameters
                Object componentInstance = Class.forName(componentClass).getDeclaredConstructor().newInstance();
                components.put(componentName, componentInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T getComponent(String name, Class<T> type) {
        return type.cast(components.get(name));
    }

    // Add a method to register components with constructor parameters
    public void registerComponent(String name, Object componentInstance) {
        components.put(name, componentInstance);
    }
}
