package tiger;

import java.util.Arrays;

/**
 * @Author: Zeng Hu
 * @Date: 2020/11/27 22:58
 * @Description:
 * @Version: 1.0
 **/
public class Solution41 {
    public static void main(String[] args) {
        int[] nums = {7, 8, 9, 11, 12};

        int i = firstMissingPositive(nums);
        System.out.println(i);


    }


    public static int firstMissingPositive(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] != 1 ? 1 : nums[0] + 1;
        }
        /**
         Arrays.sort(nums);
         int minPositive = nums[nums.length - 1];
         for (int i = 0; i < nums.length; i++) {
         if (nums[i] <= 0) continue;
         if (nums[i] < minPositive) {
         minPositive = nums[i];
         }
         }
         if (minPositive > 1 || nums[nums.length - 1] <= 0) {
         return 1;
         }
         for (int i = 0; i < nums.length - 1; i++) {
         if (nums[i] <= 0) continue;
         if (nums[i + 1] - nums[i] > 1) {
         return nums[i] + 1;
         }
         }

         return nums[nums.length - 1] + 1;
         **/

        int[] flags = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0 && nums[i] <= nums.length) {
                flags[nums[i] - 1] = nums[i];
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != (i + 1)) {
                return i + 1;
            }
        }
        return 1;


        /**

         int maxPositive = Integer.MIN_VALUE;
         int minPositive = Integer.MAX_VALUE;
         int positiveNum = 0;
         for (int i = 0; i < nums.length; i++) {
         if (nums[i] <= 0) continue;
         positiveNum++;
         if (maxPositive < nums[i]) maxPositive = nums[i];
         if (minPositive > nums[i]) minPositive = nums[i];
         }
         int totalPositive = Math.max(positiveNum, maxPositive - minPositive + 1);
         int subNums[] = new int[totalPositive];
         for (int i = 0; i < nums.length; i++) {
         if (nums[i] <= 0) continue;
         if (subNums[nums[i] - minPositive] == 0) {
         subNums[nums[i] - minPositive] = 1;
         } else {
         subNums[nums[i] - minPositive]++;
         }
         }
         int miss = -1;
         for (int i = 0; i < totalPositive && i < maxPositive; i++) {
         if (subNums[i] == 0) {
         miss = i;
         break;
         }
         }
         System.out.println(miss);
         if (minPositive > 1) {
         return 1;
         }
         if (miss == -1) { // 数据连续
         return maxPositive + 1;
         } else {  // 中间缺失
         return minPositive + miss;
         }
         **/
    }
}
