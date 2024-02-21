import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
    private Map<String, Object> beans = new HashMap<>();

    public DependencyInjector(String configFile) throws Exception {
        File file = new File(configFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("bean");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String id = element.getAttribute("id");
            String className = element.getAttribute("class");
            Object bean = Class.forName(className).newInstance();

            NodeList propertyNodes = element.getElementsByTagName("property");
            for (int j = 0; j < propertyNodes.getLength(); j++) {
                Element propertyElement = (Element) propertyNodes.item(j);
                String propertyName = propertyElement.getAttribute("name");
                String refName = propertyElement.getAttribute("ref");
                Object refBean = beans.get(refName);
                if (refBean == null) {
                    throw new IllegalArgumentException("Dépendance non trouvée : " + refName);
                }
                bean.getClass().getMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), refBean.getClass().getInterfaces()[0]).invoke(bean, refBean);
            }

            beans.put(id, bean);
        }
    }

    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
}
