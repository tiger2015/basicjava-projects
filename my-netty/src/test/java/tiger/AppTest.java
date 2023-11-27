package tiger;

import org.junit.Test;
import com.tiger.chatroom.util.ByteUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void test01() throws UnsupportedEncodingException {
        byte low = (byte) 0xff;
        byte high = 0x02;

        int len = (high << 8) + (low & 0xff);
        System.out.println(len);
        System.out.println(0x02 << 8);
        System.out.println(0xff & 0xff);
        String s = new String(new byte[]{0x20}, "utf-8");
        System.out.println(s);

    }


    @Test
    public void test02(){
        int len = 1754;
        byte [] buffer = new byte[2];
        buffer[0] = (byte) (len >> 8);
        buffer[1] = (byte) (len &0xff);
        String s = ByteUtil.byte2HexStr(buffer);
        System.out.println(s);
    }



    @Test
    public void test03(){

        Class<?> declaredClass = Integer.class.getDeclaredClasses()[0];

        Field cache = null;
        try {
            cache = declaredClass.getDeclaredField("cache");
            cache.setAccessible(true);
            Integer[] integers = (Integer[]) cache.get(declaredClass);
            integers[130] = integers[129];
            integers[131] = integers[129];
            Integer a = 1;
            if(a==(Integer)1 && a == (Integer)2 && a == (Integer) 3){
                System.out.println("success");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
