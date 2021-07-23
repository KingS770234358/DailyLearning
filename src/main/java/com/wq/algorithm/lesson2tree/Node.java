package com.wq.algorithm.lesson2tree;

public class Node {
    private int val;
    private Node left;
    private Node right;
    public static Node createTree(){
        Node node1 = new Node(1, null, null);
        Node node2 = new Node(-1, null, null);
        Node node3 = new Node(0, null, null);
        Node node4 = new Node(5, null, null);
        Node node5 = new Node(3, null, node1);
        Node node6 = new Node(-5, node5, node2);
        Node node7 = new Node(11, node3, null);
        Node node8 = new Node(99, node4, node7);
        Node root = new Node(88, node6, node8);
        return root;
    }
    public Node(){
    }
    public Node(int val, Node left, Node right){
        this.val = val;
        this.left = left;
        this.right = right;
    }
    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
