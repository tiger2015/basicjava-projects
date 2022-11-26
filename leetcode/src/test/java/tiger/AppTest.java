package tiger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public static void main(String[] args) {

        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 200;
        Integer i4 = 200;

        System.out.println(i1 == i2); // true
        System.out.println(i3 == i4); // false

        System.out.println(i1.equals(i2)); // true
        System.out.println(i3.equals(i4)); // true

        int d0 = 10;
        long d1 = 10;
        double d2 = 10.0;

        System.out.println(d0 == d1); // true
        System.out.println(d1 == d2); // true

        // List
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new LinkedList<>();
        System.out.println(0.1 * 3 == 0.3);
        System.out.println("=================");
        System.out.println(cal());

    }


    public static int cal() {
        int a = 1;
        try {


            return a += 2;
        } catch (Exception e) {
            return 6;
        } finally {
            return a += 4;
        }


    }


}
