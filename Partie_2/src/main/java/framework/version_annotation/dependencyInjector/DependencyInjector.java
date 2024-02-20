package framework.version_annotation.dependencyInjector;

import dao.DaoImpl;
import framework.version_annotation.annotations.Autowired;
import framework.version_annotation.annotations.Component;
import metier.MetierImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DependencyInjector {
    private final Map<Class<?>, Object> components = new HashMap<>();

    public DependencyInjector(String... basePackages) {
        // Scan base packages for classes marked with @Component
        for (String basePackage : basePackages) {
            List<Class<?>> classes = scanPackage(basePackage);
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(Component.class)) {
                    try {
                        Object instance = createInstance(clazz);
                        components.put(clazz, instance);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Wire dependencies
        for (Object component : components.values()) {
            wireDependencies(component);
        }
    }

    private List<Class<?>> scanPackage(String basePackage) {
        // Implement package scanning logic here
        // This can be done using libraries like Reflections or by manual scanning
        // For simplicity, let's assume we already have a list of classes in the base package
        return Arrays.asList(DaoImpl.class, MetierImpl.class);
    }

    private Object createInstance(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameters[i] = components.get(parameterTypes[i]);
                }
                return constructor.newInstance(parameters);
            }
        }
        return clazz.getDeclaredConstructor().newInstance();
    }

    private void wireDependencies(Object component) {
        Class<?> clazz = component.getClass();

        // Setter injection
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Autowired.class) && method.getName().startsWith("set") && method.getParameterCount() == 1) {
                Class<?> parameterType = method.getParameterTypes()[0];
                Object dependency = components.get(parameterType);
                if (dependency != null) {
                    try {
                        method.invoke(component, dependency);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public <T> T getComponent(Class<T> type) {
        Object component = components.get(type);
        if (component != null) {
            return type.cast(component);
        }
        return null;
    }
}
