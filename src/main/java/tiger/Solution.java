package tiger;

import java.util.Arrays;
import java.util.LinkedList;

public class Solution {


    public static void main(String[] args) {
        // int[] datas = {4, 0, 5, 29, 14, 16, 9, 2, 12, 14, 7, 27, 15, 4, 8, 11, 2, 18, 29, 3, 16, 8, 10, 22, 11, 10, 15, 1, 1, 0, 28, 5, 0, 26, 4, 6, 12, 5, 8, 16, 12, 8, 14, 27, 12, 14, 0, 6, 2, 29, 9, 10, 8, 11, 3};
        // nextPermutation(datas);
        // System.out.println(Arrays.toString(datas));
        String str = "()(()"; // 解决这种
        System.out.println(longestValidParentheses(str));
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
}