package com.wq.algorithm.lesson4thief3p;

public class RobberTest {
    public static void main(String[] args) {
        int[] houses = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println("==== 小偷问题 ====");  // 18
        System.out.println(Robber.robberByArray(houses));
        System.out.println(Robber.robber(houses));
        System.out.println("==== 小偷问题 环形 ===="); // 18
        System.out.println(RobberRing.robberRing(houses));
        System.out.println("==== 小偷问题 二叉树形 ===="); // 7
        TreeNode tn1 = new TreeNode(3, null, null);
        TreeNode tn2 = new TreeNode(1, null, null);
        TreeNode tn3 = new TreeNode(2, null, tn1);
        TreeNode tn4 = new TreeNode(3, null, tn2);
        TreeNode root = new TreeNode(3, tn3, tn4);
        System.out.println(RobberTreeNode.robberTreeNode(root));

    }
}
