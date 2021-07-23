package com.wq.algorithm.lesson2tree;

import java.util.Stack;

public class InOrder {
    private static Stack<Node> s = new Stack<>();
    public static void inOrder(Node root){
        Node p = root;
        while(p != null || !s.empty()){
            if(p != null){
                s.push(p);
                p = p.getLeft();
            }else{
                p = s.pop();
                System.out.print(p.getVal() + " ");
                p = p.getRight();
            }
        }
        System.out.println();
    }

    public static void inOrderRe(Node cur){
        if(cur == null)
            return;
        inOrderRe(cur.getLeft());
        System.out.print(cur.getVal() + " ");
        inOrderRe(cur.getRight());
    }
}
