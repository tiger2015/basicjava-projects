package tiger;

import javax.swing.*;
import java.util.Arrays;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/9/26 19:51
 * @Description:
 * @Version: 1.0
 **/
public class Solution2 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    public static void main(String[] args) {
        ListNode[] lists = new ListNode[4];
        lists[1] = null;
        ListNode list1 = new ListNode(-1);
        list1.next = new ListNode(5);
        list1.next.next = new ListNode(11);
        lists[1] = list1;

        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);
        // lists[1] = list2;
        lists[2] = null;
        ListNode list3 = new ListNode(6);
        list3.next = new ListNode(10);
        lists[3] = list3;

        // ListNode node = mergeKLists(lists);


        int[] nums = {3,4,5,6,1,2};
        System.out.println(search(nums, 2));


    }


    public static ListNode mergeKLists1(ListNode[] lists) {
        if (lists.length == 0) return null;
        ListNode[] next = new ListNode[lists.length];
        for (int i = 0; i < lists.length; i++) {
            next[i] = lists[i];
        }
        ListNode head = null;
        ListNode combineNext = head;
        while (true) {
            // 所有链表中最小的节点
            int minIndex = 0;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < lists.length; i++) {
                if (next[i] == null) continue;
                if (next[i] != null && min > next[i].val) {
                    min = next[i].val;
                    minIndex = i;
                }
            }
            if (next[minIndex] == null) break;
            if (head == null) {
                head = next[minIndex];
                combineNext = head;
            } else {
                combineNext.next = next[minIndex];
                combineNext = combineNext.next;
            }
            next[minIndex] = next[minIndex].next;
        }
        for (int i = 0; i < lists.length; i++) {
            if (next[i] == null) continue;
            while (next[i] != null) {
                if (head == null) {
                    head = next[i];
                    combineNext = head;
                } else {
                    combineNext.next = next[i];
                }
                next[i] = next[i].next;
                combineNext = combineNext.next;
            }
        }
        return head;
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;
        if (lists.length == 1) return lists[0];
        if (lists.length == 2) {
            return merge(lists[0], lists[1]);
        } else {
            // 合并后n个
            ListNode kLists = mergeKLists(Arrays.copyOfRange(lists, 1, lists.length));
            return merge(lists[0], kLists);
        }
    }

    private static ListNode merge(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode next1 = list1;
        ListNode next2 = list2;
        ListNode head = null, next = null;
        while (next1 != null && next2 != null) {
            ListNode temp;
            if (next1.val < next2.val) {
                temp = next1;
                next1 = next1.next;
            } else {
                temp = next2;
                next2 = next2.next;
            }
            if (head == null) {
                head = temp;
                next = head;
            } else {
                next.next = temp;
                next = temp;
            }
        }
        if (next1 == null) {
            next.next = next2;
        } else {
            next.next = next1;
        }
        return head;
    }


    public static int search(int[] nums, int target) {
        return binarySearch(nums, target, 0, nums.length - 1);
    }


    static int binarySearch(int[] nums, int target, int start, int end) {
        if (start > end) return -1;
        int middle = (start + end) / 2;
        if (nums[middle] == target) {
            return middle;
        }
        if (middle == start) {
            return binarySearch(nums, target, middle + 1, end);
        } else if (middle == end) {
            return binarySearch(nums, target, start, middle - 1);
        }
        if (nums[start] < nums[middle] && nums[middle] < nums[end]) { // 升序
            if (target > nums[middle]) return binarySearch(nums, target, middle + 1, end);
            else return binarySearch(nums, target, start, middle - 1);
        } else if(nums[start] < nums[middle]){  // 左边有序
            if(target >= nums[start] && target <= nums[middle]){
                return binarySearch(nums, target, start, middle - 1);
            }else {
                return  binarySearch(nums, target, middle + 1, end);
            }
        }else {   // 右边有序
            if(target >= nums[middle+1] && target <= nums[end]){
                return binarySearch(nums, target, middle+1, end);
            }else {
                return  binarySearch(nums, target, start, middle-1);
            }
        }
    }
}
