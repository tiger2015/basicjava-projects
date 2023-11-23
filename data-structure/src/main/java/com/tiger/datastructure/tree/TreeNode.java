package com.tiger.datastructure.tree;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/7/13 22:30
 * @Description:
 * @Version: 1.0
 **/
public class TreeNode<T extends Comparable> {
    private T value;
    private TreeNode parent;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(T value) {
        this.value = value;
    }

    public TreeNode left() {
        return left;
    }

    public TreeNode right() {
        return right;
    }

    public TreeNode parent() {
        return parent;
    }

    ;

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public T value() {
        return value;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
