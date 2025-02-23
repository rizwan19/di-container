package src.di_container;

import src.di_container.annotations.Autowired;
import src.di_container.annotations.Component;
import src.di_container.annotations.Primary;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<Class<?>> scan(String... basePackages) throws ClassNotFoundException{
        List<Class<?>> components = new ArrayList<>();

        for(String basePackage : basePackages){
            String path = basePackage.replace('.', '/');
            URL packageUrl = this.getClass().getClassLoader().getResource(path);
            if(packageUrl != null){
                File
            }
        }
    }

    public static String getMainClassPackage(Class<?> mainClass) {
        Package pkg = mainClass.getPackage();
        return pkg != null ? pkg.getName() : "";
    }
}


