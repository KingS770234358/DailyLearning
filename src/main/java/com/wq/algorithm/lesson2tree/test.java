package com.wq.algorithm.lesson2tree;

public class test {
    public static void main(String[] args) {
        Node treeRoot = Node.createTree();
        System.out.println("====两种前序遍历结果====");
        PreOrder.preOrderRe(treeRoot);
        System.out.println();
        PreOrder.preOrder(treeRoot);
        System.out.println("====两种中序遍历结果====");
        InOrder.inOrderRe(treeRoot);
        System.out.println();
        InOrder.inOrder(treeRoot);
        System.out.println("====两种后序遍历结果====");
        PostOrder.postOrderRe(treeRoot);
        System.out.println();
        PostOrder.postOrder(treeRoot);
    }
}
