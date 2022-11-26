package tiger;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author Zenghu
 * @Date 2022年05月29日 15:03
 * @Description
 * @Version: 1.0
 **/
public class Base64Test {

    public static void main(String[] args) {

        byte[] bytes = Base64.getEncoder().encode("".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        byte[] decodeBase64 = Base64.getDecoder().decode("".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(decodeBase64, StandardCharsets.UTF_8));

    }
}
