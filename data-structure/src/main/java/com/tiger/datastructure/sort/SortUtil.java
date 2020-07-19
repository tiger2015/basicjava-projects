package com.tiger.datastructure.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/16 21:15
 * @Description:
 * @Version: 1.0
 **/
public class SortUtil {

    /**
     * 冒泡排序 稳定排序
     * 每次循环找到一个元素的正确位置
     *
     * @param values
     * @param <T>
     */
    public static <T extends Comparable> void bubbleSort(T[] values) {
        int len = values.length;
        for (int i = 0; i < len; i++) {
            for (int k = i + 1; k < len; k++) {
                if (values[i].compareTo(values[k]) > 0) { // 交换
                    T temp = values[k];
                    values[k] = values[i];
                    values[i] = temp;
                }
            }
        }
    }

    /**
     * 选择排序，每次选择一个最小的插入到适当的位置
     *
     * @param values
     * @param <T>
     */
    public static <T extends Comparable> void selectSort(T[] values) {
        int len = values.length;
        for (int i = 0; i < len; i++) {
            int index = i; //第i个最小的位置
            for (int k = i + 1; k < len; k++) {
                if (values[index].compareTo(values[k]) > 0) {
                    index = k;
                }
            }
            T temp = values[i];
            values[i] = values[index];
            values[index] = temp;
        }
    }


    /**
     * 插入排序 稳定排序
     *
     * @param values
     * @param <T>
     */
    public static <T extends Comparable> void insertSort(T[] values) {
        int len = values.length;
        for (int i = 1; i < len; i++) {
            T temp = values[i];
            int index = i - 1;
            for (; index >= 0; index--) {
                if (values[index].compareTo(temp) > 0) {
                    values[index + 1] = values[index];
                } else {
                    break;
                }
            }
            values[index + 1] = temp;
        }
    }

    /**
     * 快速排序
     * 将问题分解为若干个规模更小的子问题，递归解决
     *
     * @param values
     * @param low
     * @param high
     * @param <T>
     */
    public static <T extends Comparable> void quickSort(T[] values, int low, int high) {

        T temp = values[low];
        int start = low;
        int end = high;
        while (start < end) {
            // 从右边开始查找
            while (values[end].compareTo(temp) >= 0 && end > start)
                end--;
            // 找到小于基准的值，然后交换
            if (values[end].compareTo(temp) <= 0) {
                T t = values[end];
                values[end] = values[start];
                values[start] = t;
            }

            // 从左边开始查找
            while (values[start].compareTo(temp) <= 0 && start < end)
                start++;

            // 找到大于基准的值，然后交换
            if (values[start].compareTo(temp) >= 0) {
                T t = values[start];
                values[start] = values[end];
                values[end] = t;
            }

        }
        if (start > low) quickSort(values, low, start - 1);// 递归处理左边
        if (end < high) quickSort(values, end + 1, high); // 递归处理右边
    }


    /**
     * 希尔排序
     * 随着增量变化，分组变少，组内元素变得有序，所以速度快
     * 组内采用插入排序
     *
     * @param values
     * @param <T>
     */
    public static <T extends Comparable> void shellSort(T[] values) {
        int len = values.length;
        int step = len / 2;

        for (; step > 0; step /= 2) {
            // 步长为step
            for (int i = step; i < len; i++) {
                // 每组采用插入排序
                int k = i - step;
                T tmp = values[i];
                while (k >= 0 && tmp.compareTo(values[k]) <= 0) {
                    values[k + step] = values[k];
                    k -= step;
                }
                values[k + step] = tmp;
            }
        }
    }


    /**
     * 归并排序
     *
     * @param values
     * @param start
     * @param end
     * @param <T>
     */
    public static <T extends Comparable> void mergeSort(Class<T> tClass, T[] values, int start, int end) {
        if (start >= end) return;
        int center = (start + end) / 2;
        mergeSort(tClass, values, start, center);
        mergeSort(tClass, values, center + 1, end);
    }


    /**
     *  将两个有序分组合并
     * @param tClass
     * @param values
     * @param start
     * @param center
     * @param end
     * @param <T>
     */
    private static <T extends Comparable> void merge(Class<T> tClass, T[] values, int start, int center, int end) {
        int left = start;
        int right = center;
        int index = 0;
        T[] tempArray = (T[]) Array.newInstance(tClass, values.length);
        while (left < center && right < end) {
            if (values[left].compareTo(values[right]) < 0) {
                // 取left的值
                tempArray[index++] = values[left++];
            } else {
                // 取right的值
                tempArray[index++] = values[right++];
            }
        }

        if (left == center) { // 将右边剩余的
            for (; right < end; ) {
                tempArray[index++] = values[right++];
            }
        }

        if (right == end) { // 将左边的剩余
            for (; left < center; ) {
                tempArray[index++] = values[left++];
            }
        }
        for (int i = start; i < end; i++) {
            values[i] = tempArray[i - start];
        }
    }


    public static void main(String[] args) {
        Double[] values = {5D, 1D, 12D, -5D, 16D, 2D, 12D, 14D};
        selectSort(values);
        //quickSort(values, 0, values.length - 1);
        //shellSort(values);
        mergeSort(Double.class, values, 0, values.length);
        System.out.println(Arrays.toString(values));

    }

}
