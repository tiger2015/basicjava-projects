package tiger;

import java.util.Stack;

/**
 * @Author: ZengHu
 * @Date: 2020/12/9 20:24
 * @Description:
 * @Version: 1.0
 **/
public class Solution44 {
    public static void main(String[] args) {
        //"acdcb"
        //"a*c?b"
        // aab c*a*b*

        //"abcabczzzde"
        //"*abc???de*"

        //"ab"
        //"*?*?*"
        String s = "ab";
        String p = "*?*?*";
        System.out.println(new Solution44().isMatch(s, p));


    }

    public boolean isMatch(String s, String p) {
        int multiSum = 0;
        int singleSum = 0;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*') {
                multiSum++;
            }
            if(p.charAt(i) == '?'){
                singleSum++;
            }
        }
        if (multiSum ==0 && singleSum == 0) { // 不含通配符
            if (p.length() != s.length()) return false;
            for (int i = 0; i < p.length(); i++) {
                if (p.charAt(i) != s.charAt(i)) return false;
            }
            return true;
        }
        if (s.length() == 0 && multiSum + singleSum == 0) return true;

        if (s.length() == 0 && singleSum > 0) return false;

        //以通配符进行分隔
        Stack<Character> indexes = new Stack<>();
        Stack<Character> patternIndexes = new Stack<>();
        int index;
        int patternIndex;
        while (indexes.size() < s.length() && patternIndexes.size() < p.length()) {
            index = indexes.size();
            patternIndex = patternIndexes.size();
            if (p.charAt(patternIndex) == s.charAt(index) || p.charAt(patternIndex) == '?') {
                indexes.push(s.charAt(index));
                patternIndexes.push(p.charAt(patternIndex));
            } else if (p.charAt(patternIndex) == '*') {
                // 尝试加入剩余所有的
                while (indexes.size() < s.length()) indexes.push(s.charAt(indexes.size()));
                patternIndexes.push(p.charAt(patternIndex));
            } else {
                if (patternIndexes.size() > 0 && p.charAt(patternIndexes.size() - 1) == '*') { // 弹出
                    indexes.pop();
                } else {
                    return false;
                }
            }
            if (s.length() == indexes.size() && patternIndexes.size() < p.length()) {
                if (patternIndexes.size() > 0 && p.charAt(patternIndexes.size() - 1) == '*')
                    indexes.pop();
            }
            System.out.println("index:" + index + ", pattern index:" + patternIndex);
        }
        if (indexes.size() != s.length()) return false;
        if (patternIndexes.size() == p.length()) {
            return true;
        }
        for (int i = patternIndexes.size(); i < p.length(); i++) {
            if (p.charAt(i) != '*') return false;
        }
        return true;

    }

}
