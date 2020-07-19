package com.tiger.datastructure.tree;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 二叉排序树
 *
 * @Auther: Zeng Hu
 * @Date: 2020/7/14 21:03
 * @Description:
 * @Version: 1.0
 **/
public class BinarySortTree<T extends Comparable> {
    private TreeNode root = null;
    private int size = 0;

    public void insert(T value) {
        TreeNode node = new TreeNode(value);
        if (root == null) {
            root = node;
        } else {
            TreeNode next = root;
            while (next != null) {
                int compare = value.compareTo(next.value());
                if (compare > 0) {
                    if (next.right() == null) {
                        next.setRight(node);
                        node.setParent(next);
                        size++;
                        break;
                    }
                    next = next.right(); // 大于则往右
                } else if (compare < 0) {
                    if (next.left() == null) {
                        next.setLeft(node);
                        node.setParent(next);
                        size++;
                        break;
                    }
                    next = next.left(); // 小于则往左
                }
            }
        }
    }

    public boolean search(T value) {
        if (root == null) return false;
        TreeNode next = root;
        while (next != null) {
            int compare = value.compareTo(next.value());
            if (compare > 0) {
                next = next.right();
            } else if (compare == 0) {
                return true;
            } else {
                next = next.left();
            }
        }
        return false;
    }


    public T delete(T value) {

        return value;
    }


    /**
     * 按照升序输出节点
     *
     * @return
     */
    public T[] ascend(Class<T> tClass, TreeNode root) {
        if (root == null) {
            return (T[]) Array.newInstance(tClass, 0);
        }
        T[] ascend = ascend(tClass, root.left());
        T[] result = (T[]) Array.newInstance(tClass, ascend.length + 1);
        for(int i=0; i< ascend.length; i++){
            result[i] = ascend[i];
        }
        result[ascend.length] = (T) root.value();
        T[] ascend1 = ascend(tClass, root.right());
        T[] result1 = (T[]) Array.newInstance(tClass, ascend1.length + result.length);
        for (int i = 0; i < result.length; i++) {
            result1[i] = result[i];
        }
        for (int i = 0; i < ascend1.length; i++) {
            result1[i+result.length] = ascend1[i];
        }

        return result1;
    }


    public TreeNode root() {
        return root;
    }

    public static String rootFirstSearch(TreeNode root) {
        if (root == null) {
            return "";
        }
        String s = root.value().toString() + ",";
        s += rootFirstSearch(root.left());
        s += rootFirstSearch(root.right());
        return s;
    }


    public static String leftFirstSearch(TreeNode root) {
        if (root == null) {
            return "";
        }
        String s = leftFirstSearch(root.left());
        s += root.value().toString() + ",";
        s += leftFirstSearch(root.right());
        return s;
    }

    public static String rightFirstSearch(TreeNode root) {
        if (root == null) {
            return "";
        }
        String s = rightFirstSearch(root.right());
        s += root.value().toString() + ",";
        s += rightFirstSearch(root.left());
        return s;
    }


    public static void main(String[] args) {
        BinarySortTree<Integer> tree = new BinarySortTree<>();
        tree.insert(3);
        tree.insert(-2);
        tree.insert(5);
        tree.insert(10);
        tree.insert(0);
        System.out.println(rootFirstSearch(tree.root));
        System.out.println(leftFirstSearch(tree.root));
        System.out.println(rightFirstSearch(tree.root));

        System.out.println(tree.search(-2));
        System.out.println(tree.search(-199));

        Integer[] ascend = tree.ascend(Integer.class, tree.root);
        System.out.println(Arrays.toString(ascend));

    }
}
