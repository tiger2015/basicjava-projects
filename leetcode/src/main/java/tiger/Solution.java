package tiger;

import java.util.*;

public class Solution {


    public static void main(String[] args) {
        // int[] datas = {4, 0, 5, 29, 14, 16, 9, 2, 12, 14, 7, 27, 15, 4, 8, 11, 2, 18, 29, 3, 16, 8, 10, 22, 11,
        // 10, 15, 1, 1, 0, 28, 5, 0, 26, 4, 6, 12, 5, 8, 16, 12, 8, 14, 27, 12, 14, 0, 6, 2, 29, 9, 10, 8, 11, 3};
        // nextPermutation(datas);
        // System.out.println(Arrays.toString(datas));

        String[] words = {"word","good","best","good"};
        String s = "wordgoodgoodgoodbestword";
        List<Integer> list = findSubstring2(s, words);
        System.out.println(list);

    }


    public static void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int len = nums.length;
        int index = 2;
        while (index <= len) {
            // 对后几位做降序排列，即可得到大于当前的数值
            int[] copy = Arrays.copyOf(nums, len);
            int[] range = Arrays.copyOfRange(copy, len - index, len);
            Arrays.sort(range);
            for (int i = 0; i < index; i++) {
                copy[len - index + i] = range[range.length - i - 1];
            }
            int compare = compare(nums, copy);
            if (compare >= 0) {
                index++;
            } else {
                // 对从 len - index 到 len的部分求下一个最大
                int[] sub = Arrays.copyOfRange(nums, len - index, len);
                getNextMax(sub);
                System.arraycopy(sub, 0, nums, len - index, index);
                break;
            }
        }
        if (index > len) {
            Arrays.sort(nums);
        }
    }


    private static void getNextMax(int[] nums) {
        int len = nums.length;
        // 第一位需要变动
        // 找到比第一位大的数据
        int[] copy = Arrays.copyOfRange(nums, 1, len);
        Arrays.sort(copy);
        for (int i = 0; i < copy.length; i++) {
            if (nums[0] < copy[i]) {
                // 交换
                int tmp = nums[0];
                nums[0] = copy[i];
                copy[i] = tmp;
                break;
            }
        }
        Arrays.sort(copy);
        System.arraycopy(copy, 0, nums, 1, copy.length);
    }

    private static String getValue(int[] nums) {
        int len = nums.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    private static int compare(int[] nums, int[] other) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > other[i]) {
                return 1;
            } else if (nums[i] < other[i]) {
                return -1;
            }
        }
        return 0;
    }

    public static int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        LinkedList<Character> stack = new LinkedList<>();
        stack.push(s.charAt(0));
        int count = 0;
        int max = 0;
        for (int i = 1; i < s.length(); i++) {
            if (stack.isEmpty()) {
                stack.push(s.charAt(i));
                continue;
            }
            char top = stack.peek();
            if (top == '(' && s.charAt(i) == ')') {
                stack.pop();
                count += 2;
                if (max < count) {
                    max = count;
                }
            } else {
                stack.push(s.charAt(i));
            }
        }
        return max < count ? count : max;
    }

    public int threeSumClosest(int[] nums, int target) {
        int len = nums.length;
        int[] temp = new int[len];
        for (int i = 0; i < len; i++) {
            temp[i] = target - nums[i];
        }
        // 找两个数最近的
        for (int i = 0; i < len; i++) {
            for (int k = i + 1; k < len; k++) {


            }

        }

        return 0;

    }


    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        int totalLen = 0;
        for (int i = 0; i < words.length; i++) {
            int index = 0;
            while (index < s.length()) {
                String temp = s.substring(index);
                int firstIndex = temp.indexOf(words[i].charAt(0)); // 找到匹配的第一个字符
                if (firstIndex < 0 || firstIndex + words[i].length() > temp.length()) break;
                String compareWord = temp.substring(firstIndex, firstIndex + words[i].length());
                if (compareWord.equals(words[i])) {
                    if (!indexes.contains(firstIndex + index)) indexes.add(firstIndex + index);
                }
                index++;
            }
            totalLen += words[i].length();
        }
        System.out.println(indexes);
        int wordLen = totalLen / words.length;
        int start;
        for (int i = 0; i < indexes.size(); i++) {
            start = indexes.get(i);
            if (start + totalLen > s.length()) continue; // 剩余字符不足所有字符的长度
            String temp = s.substring(start, start + totalLen);  // 查看子串是否包含所有单词
            System.out.println("===" + temp);

            List<String> wordList = new ArrayList<>();
            for (int k = 0; k < words.length; k++) {


                wordList.add(temp.substring(k * wordLen, (k + 1) * wordLen));
            }
            boolean flag = true;
            for (int k = 0; k < words.length; k++) {
                if (!wordList.contains(words[k])) {
                    flag = false;
                    break;
                } else { // 移除已存在的
                    wordList.remove(words[k]);
                }
            }
            if (flag) result.add(start);
        }
        return result;
    }

    public static List<Integer> findSubstring2(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        int wordLen = words[0].length();
        int totalLen = words.length * wordLen;
        Map<String,Integer> wordsMap = new HashMap<>();
        for (int k = 0; k < words.length; k++) {
           if (wordsMap.containsKey(words[k])) wordsMap.put(words[k], wordsMap.get(words[k])+1);
           else  wordsMap.put(words[k], 1);
        }
        for (int i = 0; i < s.length() - totalLen+1; i++) {
            String subStr = s.substring(i, i + totalLen);
            Map<String,Integer> tempWordsMap = new HashMap<>();
            for (int k = 0; k < words.length ; k++) {
                String tempWord = subStr.substring(k * wordLen, (k + 1) * wordLen);
                if(tempWordsMap.containsKey(tempWord)) tempWordsMap.put(tempWord, tempWordsMap.get(tempWord)+1);
                else tempWordsMap.put(tempWord, 1);
            }
            for(String word: wordsMap.keySet()){
                if(tempWordsMap.containsKey(word) && tempWordsMap.get(word).equals(wordsMap.get(word))){
                    tempWordsMap.remove(word);
                }
            }
            if(tempWordsMap.size() == 0){
                result.add(i);
            }
        }
        return result;
    }

}