package tiger;

import java.util.Arrays;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/10/5 20:20
 * @Description:
 * @Version: 1.0
 **/
public class Solution3 {
    public static void main(String[] args) {
        int[] nums = {3,3,3,3};
        int[] ints = searchRange(nums, 3);
        System.out.println(Arrays.toString(ints));
    }


    public static int[] searchRange(int[] nums, int target) {
        int[] indexes = new int[]{-1, -1};
        binarySearch(nums, target, 0, nums.length - 1, indexes);
        if (indexes[0] >= 0 && indexes[1] == -1) {
            indexes[1] = indexes[0];
        }
        return indexes;
    }


    public static void binarySearch(int[] nums, int target, int start, int end, int[] indexes) {
        if (start > end) return;
        int middle = (start + end) / 2;
        if (nums[middle] == target) { // 找到目标元素
            binarySearch(nums, target, middle + 1, end, indexes); // 查找左半部分
            binarySearch(nums, target, start, middle - 1, indexes); // 查找右半部分
           // System.out.println("===="+middle);
            if(indexes[0] > middle || indexes[0] == -1){
               indexes[0] = middle;
            }
            if(indexes[1] < middle || indexes[1] == -1){
                indexes[1] = middle;
            }
        } else if (nums[middle] < target) {
            binarySearch(nums, target, middle + 1, end, indexes);
        } else {
            binarySearch(nums, target, start, middle - 1, indexes);
        }
    }

}
