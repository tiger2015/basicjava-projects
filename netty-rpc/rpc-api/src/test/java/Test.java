import com.tiger.rpc.common.KryoUtil;
import com.tiger.rpc.service.EmployeeService;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ClassName: Test
 * @Author: Zeng.h
 * @Date: 2023/11/24
 * @Description:
 * @Version: 1.0
 **/
public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
        String name = int.class.getName();
        System.out.println(name);
        Class<?> aClass = Class.forName(EmployeeService.class.getName());
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method: declaredMethods){
            System.out.println(method.getName());
            System.out.println(Arrays.toString(method.getParameterTypes()));
        }

        byte[] serialize = KryoUtil.serialize(1);
        System.out.println(Arrays.toString(serialize));
        int deserialize = KryoUtil.deserialize(serialize);
        System.out.println(deserialize);

    }
}
