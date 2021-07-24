package com.wq.algorithm.lesson4thief3p;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/house-robber-iii/
 */
public class RobberTreeNode {

    private static Stack<TreeNode> s = new Stack<>();
    private static Map<TreeNode, Integer> f = new HashMap<>();
    private static Map<TreeNode, Integer> g = new HashMap<>();
    public static int robberTreeNode(TreeNode root){

        f.put(null, 0);
        g.put(null, 0);

        TreeNode p = root;
        TreeNode pre = null;
        while(p!=null || !s.empty()){
            if(p!=null){
                s.push(p);
                p = p.getLeft();
            }else{
                p = s.peek();
                if(p.getRight()!=null && p.getRight()!=pre){
                    p = p.getRight();
                }else{
                    s.pop();
                    f.put(p, g.get(p.getLeft()) + g.get(p.getRight()) + p.getVal());
                    // 重要的一行 左子树的最大 加上 右子树的最大
                    g.put(p, Math.max(f.get(p.getLeft()), g.get(p.getLeft())) + Math.max(f.get(p.getRight()), g.get(p.getRight())));
                    pre = p;
                    p = null;
                }
            }
        }
        return Math.max(f.get(root), g.get(root));
    }
}
