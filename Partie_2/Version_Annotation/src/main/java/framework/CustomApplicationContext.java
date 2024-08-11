package framework;

import dao.DaoImpl1;
import dao.DaoImpl2;
import framework.annotations.Autowired;
import framework.annotations.Qualified;
import metier.MetierImpl;

import java.lang.reflect.Field;
import java.util.*;



public class CustomApplicationContext {
    private Map<String, Object> beans = new HashMap<>();
    private String[] packageNames;

    public CustomApplicationContext(String... packageNames) {
        this.packageNames = packageNames;
        // Scan packageNames and instantiate beans
        for (String packageName : packageNames) {
            instantiateBeans(packageName);
        }
        // Autowire dependencies
        autowireDependencies();
    }


    public Object getBean(String beanName) {
        return beans.get(beanName);
    }

    private void instantiateBeans(String packageName) {
        beans.put("metier", new MetierImpl());
        beans.put("daoImplV1", new DaoImpl1());
        beans.put("daoImplV2", new DaoImpl2());
    }

    private void autowireDependencies() {
        for (Object bean : beans.values()) {
            // Use reflection to find fields annotated with @Autowired
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    // Find corresponding beans from beans map and inject them
                    try {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Qualified.class)) {
                            String qualifier = field.getAnnotation(Qualified.class).value();
                            field.set(bean, beans.get(qualifier));
                        } else {
                            field.set(bean, getBean(field.getName()));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
