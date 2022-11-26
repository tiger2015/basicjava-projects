package tiger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/11/16 20:59
 * @Description:
 * @Version: 1.0
 **/
public class Solution6 {
    public static void main(String[] args) {
        int[] cadidates = {2, 3, 6, 7};
        int target = 7;
        combinationSum(cadidates, target);

        double x1 = 70;
        double y1 = -45;
        double z1 = 13;


        System.out.println(Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1));


    }


    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        } else {
            String prevS = countAndSay(n - 1);
            if (prevS.length() == 1) {
                return "11";
            }
            int count = 1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < prevS.length() - 1; i++) {
                if (prevS.charAt(i) == prevS.charAt(i + 1)) {
                    count++;
                } else {
                    builder.append(count + "");
                    builder.append(prevS.charAt(i));
                    count = 1;
                }
            }
            builder.append(count + "");
            builder.append(prevS.charAt(prevS.length() - 1));
            return builder.toString();
        }
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> intList = new ArrayList<>();
        int min = candidates[0];
        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] <= target) {
                List<Integer> list = new ArrayList<>();
                list.add(candidates[i]);
                intList.add(list);
            }
            if (min > candidates[i]) {
                min = candidates[i];
            }
        }
        for (int i = 0; i < target / min + 1; i++) {
            List<List<Integer>> subCombination = subCombination(intList, candidates, target);
            intList.clear();
            intList.addAll(subCombination);
        }
        return intList;
    }


    private static List<List<Integer>> subCombination(List<List<Integer>> list, int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Integer> item = list.get(i);
            for (int k = 0; k < candidates.length; k++) {
                List<Integer> subList = new ArrayList<>(item);
                int sum = 0;
                for (Integer data : subList) {
                    sum += data;
                }
                if (sum + candidates[k] <= target) {
                    // 插入最小的
                    int pos = subList.size() - 1;
                    for (; pos >= 0; pos--) {
                        if (subList.get(pos) < candidates[i]) {
                            break;
                        }
                    }
                    subList.add(pos + 1, candidates[i]);
                    if (!containsList(result, subList)) {
                        result.add(subList);
                    }
                }
                if (sum == target) {
                    if (!containsList(result, subList)) {
                        result.add(subList);
                    }
                }
            }
        }
        // 去重


        return result;
    }


    private static boolean containsList(List<List<Integer>> lists, List<Integer> list) {
        for (List<Integer> target : lists) {
            if (target.equals(list)) {
                return true;
            }
        }
        return false;
    }

}
