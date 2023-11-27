package tiger;

import java.util.*;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/10/11 17:08
 * @Description:
 * @Version: 1.0
 **/
public class Solution5 {
    public static void main(String[] args) {
        char[][] board = {{'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}};

        new Solution5().solveSudoku(board);
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }

        boolean validSudoku = new Solution5().isValidSudoku(board);
        System.out.println(validSudoku);


    }


    public boolean isUnique(String astr) {
        for (int i = 0; i < astr.length(); i++) {
            for (int j = i + 1; j < astr.length(); j++) {
                if (astr.charAt(i) == astr.charAt(j)) return false;
            }
        }
        return true;
    }


    public boolean CheckPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            if (map1.containsKey(s1.charAt(i))) {
                map1.put(s1.charAt(i), map1.get(s1.charAt(i)) + 1);
            } else {
                map1.put(s1.charAt(i), 1);
            }
            if (map2.containsKey(s2.charAt(i))) {
                map2.put(s2.charAt(i), map2.get(s2.charAt(i)) + 1);
            } else {
                map2.put(s2.charAt(i), 1);
            }
        }
        for (Character ch : map1.keySet()) {
            if (!map2.containsKey(ch)) return false;
            if (map2.get(ch) != map1.get(ch)) return false;
            map2.remove(ch);
        }
        if (map2.size() > 0) return false;
        return true;
    }

    public String replaceSpaces(String S, int length) {
        char[] array = new char[length * 3];
        int index = 0;
        for (int i = 0; i < length; i++) {
            if (S.charAt(i) == 0x20) {
                array[index++] = '%';
                array[index++] = '2';
                array[index++] = '0';
            } else {
                array[index++] = S.charAt(i);
            }
        }
        return new String(Arrays.copyOf(array, index));
    }

    public boolean canPermutePalindrome(String s) {
        int[] array = new int[255];
        // 判断是否可以构成回文
        for (int i = 0; i < s.length(); i++) {
            array[s.charAt(i) - 1]++;
        }
        int single = 0; // 出现次数为奇数的字符数
        int duplicate = 0; // 出现次数为偶数的字符数
        int odd = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 1) {
                single++;
                odd += array[i];
            }
            if (array[i] == s.length()) return true;
            if (array[i] % 2 == 0) duplicate += array[i];
        }
        if (single > 1) return false;
        return s.length() - odd == duplicate;
    }

    public boolean isValidSudoku(char[][] board) {
        int rowNum = board.length;
        char[] counts;
        for (int i = 0; i < rowNum; i++) {
            counts = new char[rowNum + 1];
            // 检测每一行
            for (int j = 0; j < rowNum; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                int num = board[i][j] - 0x30;
                counts[num]++;
                if (counts[num] > 1) {
                    return false;
                }
            }
            // 每一列
            counts = new char[rowNum + 1];
            for (int j = 0; j < rowNum; j++) {
                if (board[j][i] == '.') {
                    continue;
                }
                int num = board[j][i] - 0x30;
                counts[num]++;
                if (counts[num] > 1) {
                    return false;
                }

            }
        }
        // 检测每个3*3单元格
        for (int i = 0; i < rowNum / 3; i++) {
            for (int j = 0; j < rowNum / 3; j++) {
                // 检测的位置：i*3 (i+1)*3; j*3 (j+1)*3
                counts = new char[rowNum + 1];
                for (int k = i * 3; k < (i + 1) * 3; k++) {
                    for (int m = j * 3; m < (j + 1) * 3; m++) {
                        if (board[k][m] == '.') {
                            continue;
                        }
                        int num = board[k][m] - 0x30;
                        counts[num]++;
                        if (counts[num] > 1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void solveSudoku(char[][] board) {
        int totalRow = board.length;
        int totalCol = board[0].length;
        List<Solve> unsolvedList = new ArrayList<>();
        for (int i = 0; i < totalRow; i++) {
            for (int k = 0; k < totalCol; k++) {
                if (board[i][k] == '.') {
                    Solve solve = new Solve();
                    solve.rowIndex = i;
                    solve.colIndex = k;
                    unsolvedList.add(solve);
                }
            }
        }
        Stack<Solve> solvedStack = new Stack<>();
        LinkedList<Solve> unsolveQueue = new LinkedList<>();
        unsolveQueue.addAll(unsolvedList);
        Solve solve = unsolveQueue.pop();
        while (solvedStack.size() != unsolvedList.size()) {
            getAvailableSolve(solve, board);
            board[solve.rowIndex][solve.colIndex] = '.';
            solve.possibleCharSet.removeAll(solve.accessCharSet);
            for (int k = 0; k < solve.possibleCharSet.size(); k++) {
                if (isOk(board, solve.rowIndex, solve.colIndex, solve.possibleCharSet.get(k))) {
                    solve.ch = solve.possibleCharSet.get(k);
                    System.out.println(solve.rowIndex + "," + solve.colIndex + ":" + solve.ch);
                    break;
                }
            }
            if (solve.ch != '.') { // 有解
                solve.accessCharSet.add(solve.ch);
                solvedStack.push(solve);
                board[solve.rowIndex][solve.colIndex] = solve.ch;
                if (!unsolveQueue.isEmpty())
                    solve = unsolveQueue.pop();
            } else { //无解
                solve.accessCharSet.clear();
                unsolveQueue.addFirst(solve);
                if (solvedStack.isEmpty()) {
                    solve = unsolveQueue.pop();
                    solve.accessCharSet.clear();
                } else {
                    solve = solvedStack.pop();
                }
                solve.ch = '.';
            }
        }

    }


    private void getAvailableSolve(Solve solve, char[][] board) {
        int size = board.length;
        Set<Character> optionCharSet = new HashSet<>();
        solve.possibleCharSet.clear();
        for (int i = 0; i < size; i++) {
            optionCharSet.add((char) (49 + i));
            if (board[solve.rowIndex][i] != '.') {
                solve.possibleCharSet.add(board[solve.rowIndex][i]);
            }
            if (board[i][solve.colIndex] != '.') {
                solve.possibleCharSet.add(board[i][solve.colIndex]);
            }
        }
        for (int i = (solve.rowIndex / 3) * 3; i < (solve.rowIndex / 3 + 1) * 3; i++) {
            for (int k = (solve.colIndex / 3) * 3; k < (solve.colIndex / 3 + 1) * 3; k++) {
                if (board[i][k] != '.') {
                    solve.possibleCharSet.add(board[i][k]);
                }
            }
        }
        Set<Character> allCharacter = new HashSet<>(optionCharSet);
        allCharacter.removeAll(solve.possibleCharSet);
        solve.possibleCharSet.clear();

        solve.possibleCharSet.addAll(allCharacter);

    }


    // 判断(rowNum, colNum)可以填入ch
    private static boolean isOk(char[][] board, int rowNum, int colNum, char ch) {
        int size = board.length;
        // 检测rowNum行
        for (int i = 0; i < size; i++) {
            if (board[rowNum][i] == ch) {
                return false;
            }
        }
        // 检测colNum列
        for (int i = 0; i < size; i++) {
            if (board[i][colNum] == ch) {
                return false;
            }
        }
        // 检测所在的3*3;
        for (int i = (rowNum / 3) * 3; i < (rowNum / 3 + 1) * 3; i++) {
            for (int k = (colNum / 3) * 3; k < (colNum / 3 + 1) * 3; k++) {
                if (board[i][k] == ch) {
                    return false;
                }
            }
        }
        return true;
    }

    class Solve {
        int colIndex;
        int rowIndex;
        char ch = '.';
        List<Character> possibleCharSet = new ArrayList<>();
        Set<Character> accessCharSet = new HashSet<>();

    }

}
