package tiger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Zeng Hu
 * @Date: 2020/11/26 20:57
 * @Description:
 * @Version: 1.0
 **/
public class Solution40 {

    public static void main(String[] args) {

        int[] condidates = {4, 1, 1, 4, 4, 4, 4, 2, 3, 5};
        int sum = 10;
        List<List<Integer>> lists = new Solution40().combinationSum2(condidates, sum);
        System.out.println(lists);
    }


    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        for (int i = 0; i < candidates.length; i++) {
            List<List<Integer>> lists = subCombinations(Arrays.copyOfRange(candidates, i, candidates.length), target);
            for (List<Integer> list : lists) {
                boolean add = true;
                for (List<Integer> combination : combinations) {
                    if (list.containsAll(combination) && combination.containsAll(list) && list.size() == combination.size()) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    combinations.add(list);
                }
            }
        }
        return combinations;
    }


    public static List<List<Integer>> subCombinations(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> combinations = new LinkedList<>();
        LinkedList<Integer> indexes = new LinkedList<>();
        int sum = 0;
        int i = 0;
        do {
            for (; i < candidates.length; i++) {
                if (sum + candidates[i] < target) {
                    combinations.add(candidates[i]);
                    indexes.add(i);
                    sum += candidates[i];
                } else if (sum + candidates[i] == target) {
                    combinations.add(candidates[i]);
                    result.add(new ArrayList<>(combinations));
                    combinations.removeLast();
                }
            }
            if (combinations.size() > 0) {
                Integer last = combinations.removeLast();
                sum -= last;
                Integer lastIndex = indexes.removeLast();
                i = lastIndex + 1;
            }
        } while (combinations.size() > 0);
        return result;
    }


}
