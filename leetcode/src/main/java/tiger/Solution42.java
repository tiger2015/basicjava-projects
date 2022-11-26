package tiger;

/**
 * @Author: Zeng Hu
 * @Date: 2020/11/28 16:56
 * @Description:
 * @Version: 1.0
 **/
public class Solution42 {

    public static void main(String[] args) {
        int[] height = {4, 2, 3};
        System.out.println(trap(height));
    }


    public static int trap(int[] height) {
        if (height.length < 3) {
            return 0;
        }
        int sum = 0;
        int start = 0;
        int end = height.length - 1;
        do {
            int nextEnd = getNextMax(height, start, end);
            int nextStart = getNextMaxInverse(height, start, end);
            if (start < nextEnd) {
                for (int i = start; i < nextEnd - 1; i++) {
                    sum += (height[start] - height[i + 1]);
                }
                start = nextEnd;
            }
            if (nextStart < end) {
                for (int i = end; i > nextStart + 1; i--) {
                    sum += (height[end] - height[i - 1]);
                }
                end = nextStart;
            }
        } while (start < end);
        return sum;
    }


    private static int getNextMax(int[] height, int start, int end) {
        int max = height[start];
        for (int i = start + 1; i <= end; i++) {
            if (height[i] >= max) {
                return i;
            }
        }
        return start;
    }

    private static int getNextMaxInverse(int[] height, int start, int end) {
        int max = height[end];
        for (int i = end - 1; i >= start; i--) {
            if (height[i] > max) {
                return i;
            }
        }
        return end;
    }
}
