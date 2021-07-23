package com.wq.algorithm.lesson2tree;

import java.util.Stack;

public class PostOrder {
    private static Stack<Node> s = new Stack<>();
    public static void postOrder(Node root){
        Node p = root;
        Node pre = null;
        while(p != null || !s.empty()){
            if(p != null){
                s.push(p);
                p = p.getLeft();
            }else{
                p = s.peek();
                if(p.getRight() != null && p.getRight() != pre){ // 开始访问右子树
                    p = p.getRight();
                }else{ // 左子树访问完毕 且 右子树访问完毕 访问当前节点
                    System.out.print(p.getVal() + " ");
                    s.pop(); // 记得pop出去
                    pre = p;
                    p = null; // 下次循环进入第15行
                }
            }
        }
    }

    public static void postOrderRe(Node cur){
        if(cur == null)
            return;
        postOrderRe(cur.getLeft());
        postOrderRe(cur.getRight());
        System.out.print(cur.getVal() + " ");
    }
}
