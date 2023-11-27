package tiger;

/**
 * @Author: ZengHu
 * @Date: 2020/11/29 9:46
 * @Description:
 * @Version: 1.0
 **/
public class Solution43 {

    public static void main(String[] args) {
        String num1 = "56";
        String num2 = "499";
        String multiply = multiply(num1, num2);
        System.out.println(multiply);

    }


    public static String multiply(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        for (int i = num1.length() - 1; i >= 0; i--) {
            StringBuilder singleMultiply = singleMultiply(num2, num1.charAt(i));
            // 后面补充0的个数
            for (int k = i + 1; k < num1.length(); k++) {
                singleMultiply.append('0');
            }
            if (result.length() == 0) {
                result.append(singleMultiply);
            } else {
                result = add(result.toString(), singleMultiply.toString());
            }
        }
        while (result.length() > 0 && result.charAt(0) == 0) result.deleteCharAt(0);
        return result.toString();
    }


    public static StringBuilder singleMultiply(String num, char ch) {
        StringBuilder result = new StringBuilder();
        int flag = 0;
        int opt1 = ch - 0x30;
        for (int i = num.length() - 1; i >= 0; i--) {
            int opt2 = num.charAt(i) - 0x30;
            int val = opt1 * opt2;
            val += flag;
            if (val >= 10) {
                flag = val / 10;
                result.insert(0, (char) (val % 10 + 0x30));
            } else {
                flag = 0;
                result.insert(0, (char) (val + 0x30));
            }
        }
        if (flag > 0) result.insert(0, (char) (flag + 0x30));
        return result;
    }


    public static StringBuilder add(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int len1 = num1.length();
        int len2 = num2.length();
        int index = 0;
        boolean flag = false;
        for (; index < len1 && index < len2; index++) {
            //
            char opt1 = num1.charAt(len1 - index - 1);
            char opt2 = num2.charAt(len2 - index - 1);
            char val = (char) (opt1 + opt2);
            if (flag) val += 1;
            if (val > 0x69) {
                flag = true;
                result.insert(0, (char) (val - 0x3A));
            } else {
                flag = false;
                result.insert(0, (char) (val - 0x30));
            }
        }

        for (; index < len1; index++) {
            char opt = num1.charAt(len1 - index - 1);
            if (flag) opt += 1;
            if (opt > 0x39) {
                flag = true;
                result.insert(0, (char) (opt - 10));
            } else {
                flag = false;
                result.insert(0, opt);
            }
        }

        for (; index < len2; index++) {
            char opt = num2.charAt(len2 - index - 1);
            if (flag) opt += 1;
            if (opt > 0x39) {
                flag = true;
                result.insert(0, (char) (opt - 10));
            } else {
                flag = false;
                result.insert(0, opt);
            }
        }
        if (flag) {
            result.insert(0, '1');
        }
        return result;
    }


}
