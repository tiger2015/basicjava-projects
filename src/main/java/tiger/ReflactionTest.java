package tiger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public class ReflactionTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IntrospectionException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = Class.forName("tiger.User");
        Object instance = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
            Class<?> type = field.getType();
            field.setAccessible(true);
            if(type == Integer.class || type == int.class){
                field.set(instance, 1);
            }else if(type == String.class){
                field.set(instance, "test");
            }else if (type == Date.class){
                field.set(instance, Calendar.getInstance().getTime());
            }
        }
        System.out.println(instance);

        Method[] methods = clazz.getDeclaredMethods();
        for(Method method: methods){
            System.out.println(method);
        }

        // java bean
        BeanInfo bean = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] descriptors = bean.getPropertyDescriptors();
        for(PropertyDescriptor descriptor: descriptors){
            System.out.println(descriptor);
        }

        // 创建对象
        // 1. Class getInstance();
        // 2. new
        // 3. 反射调用构造器
        Constructor<?> constructor = clazz.getConstructor(null);
        User newInstance = (User) constructor.newInstance();
        System.out.println(newInstance);
    }

}
