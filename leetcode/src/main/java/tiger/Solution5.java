package tiger;

/**
 * @Author Zenghu
 * @Date 2021/9/10 22:30
 * @Description
 * @Version: 1.0
 **/
public class Solution5 {


    /**
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s.length() <= 1) return s;
        if (s.length() == 2) return s.charAt(0) == s.charAt(1) ? s : s.substring(0, 1);
        // 当长度大于3


        return null;
    }
}
