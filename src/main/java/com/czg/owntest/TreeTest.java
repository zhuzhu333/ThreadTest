package com.czg.owntest;

import com.czg.utils.TreeNode;
import com.czg.utils.TreeNodeBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeTest {
    public static void main(String[] args) {
        int preorder[] = new int[]{3,5,6,2,7,4,1,0,8};
        int inorder[] = new int[]{6,5,7,2,4,3,0,1,8};
        TreeNodeBuilder treeNodeBuilder = new TreeNodeBuilder();
        TreeNode treeNode = treeNodeBuilder.buildTree(preorder,inorder);
        Solution863 solution863 = new Solution863();
        solution863.distanceK(treeNode,5,2);
        System.out.println();
    }
}
