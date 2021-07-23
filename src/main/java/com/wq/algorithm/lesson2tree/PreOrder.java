package com.wq.algorithm.lesson2tree;

import java.util.Stack;

public class PreOrder {

    private static Stack<Node> s = new Stack<>();
    public static void preOrder(Node root){
        Node p = root;
        while(p != null || !s.empty()){
            if(p != null){
                System.out.print(p.getVal() + " ");
                s.push(p);
                p = p.getLeft();
            }else{
                p = s.pop();
                p = p.getRight();
            }
        }
        System.out.println();
    }

    public static void preOrderRe(Node cur){
        if(cur == null)
            return;
        System.out.print(cur.getVal() + " ");
        preOrderRe(cur.getLeft());
        preOrderRe(cur.getRight());
    }

}
