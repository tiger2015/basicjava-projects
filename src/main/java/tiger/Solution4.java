package tiger;

import java.util.Arrays;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/10/11 16:26
 * @Description:
 * @Version: 1.0
 **/
public class Solution4 {


    public static void main(String[] args) {
        ListNode head = new ListNode(1);
         head.next = new ListNode(2);
         head.next.next = new ListNode(3);
        //head.next.next.next = new ListNode(4);

        ListNode pairs = swapPairs(head);


    }


    public static int searchInsert(int[] nums, int target) {
        int search = Arrays.binarySearch(nums, target);
        if (search < 0) {
            for (int i = 0; i < nums.length; i++) {
                if (target <= nums[i]) {
                    return i;
                }
            }
            return nums.length;
        } else {
            return search;
        }
    }

    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);

    }

    public static ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode first = head;
        ListNode second = head.next;
        ListNode newHead = head;
        ListNode newTail = null;
        ListNode next;
        while (first != null && second != null) {
            next = second.next;
            if (newHead == head) {
                newHead = second;
                newHead.next = first;
                newTail = first;
                newTail.next = null;
            } else {
                newTail.next = second;
                second.next = first;
                newTail = first;
                newTail.next = null;
            }
            first = next;
            if (next != null) {
                second = next.next;
            }
        }
        if (second == null && newTail != null) {
            newTail.next = first;
        }
        return newHead;
    }

}

class ListNode {
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
