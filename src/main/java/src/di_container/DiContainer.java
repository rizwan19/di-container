package src.di_container;

import src.di_container.annotations.Autowired;
import src.di_container.annotations.Component;
import src.di_container.annotations.Primary;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DiContainer {
    private static final Map<Class<?>, Object> componentInstances = new HashMap<>();
    private static final Map<Class<?>, Class<?>> interfaceMappings = new HashMap<>();

    public static void initializeContainer(Class<?>... serviceClasses) throws Exception {
        for (Class<?> clazz : serviceClasses) {
            if (clazz.isAnnotationPresent(Component.class)) {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                Object instance = constructor.newInstance();
                componentInstances.put(clazz, instance);

                for (Class<?> iface : clazz.getInterfaces()) {
                    if (!interfaceMappings.containsKey(iface) || clazz.isAnnotationPresent(Primary.class)) {
                        interfaceMappings.put(iface, clazz);
                    }
                }
            }
        }

        for (Object instance : componentInstances.values()) {
            Field[] fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object dependency = getDependency(field.getType());
                    if (dependency != null) {
                        field.setAccessible(true);
                        field.set(instance, dependency);
                    } else {
                        throw new RuntimeException("No available instance for " + field.getType().getName());
                    }
                }
            }
        }
    }

    private static Object getDependency(Class<?> type) {
        if (componentInstances.containsKey(type)) {
            return componentInstances.get(type);
        }

        if (interfaceMappings.containsKey(type)) {
            Class<?> implClass = interfaceMappings.get(type);
            return componentInstances.get(implClass);
        }
        return null;
    }

    public static <T> T get(Class<T> type) {
        return (T) getDependency(type);
    }
}


