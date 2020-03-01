package tiger;

import org.junit.Test;
import tiger.com.tiger.netty.util.ByteUtil;

import java.io.UnsupportedEncodingException;

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


}
