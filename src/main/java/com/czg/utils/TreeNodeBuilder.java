package com.czg.utils;

public class TreeNodeBuilder {
    //写法仿照 剑指offer 面试题7 重建二叉树

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null || preorder.length==0){
            return null;
        }
        return buildCore(preorder,0,preorder.length-1,inorder,0,inorder.length-1);
    }
    private TreeNode buildCore(int[] preorder,int preSt,int preEnd,int[] inorder,int inSt,int inEnd){
        //前序遍历第一个节点是根节点
        int rootValue = preorder[preSt];
        TreeNode root = new TreeNode(rootValue);

        //前序序列只有根节点
        if(preSt == preEnd){
            return root;
        }
        //遍历中序序列，找到根节点的位置
        int rootInorder = inSt;
        while(inorder[rootInorder]!=rootValue&&rootInorder<=inEnd){
            rootInorder++;
        }

        //左子树的长度
        int leftLength = rootInorder - inSt;
        //前序序列中左子树的最后一个节点
        int leftPreEnd = preSt + leftLength;

        //左子树非空
        if(leftLength>0){
            //重建左子树
            root.left = buildCore(preorder,preSt+1,leftPreEnd,inorder,inSt,inEnd);
        }
        //右子树非空
        if(leftLength < preEnd - preSt){
            //重建右子树
            root.right = buildCore(preorder,leftPreEnd +1,preEnd,inorder,rootInorder+1,inEnd);
        }
        return root;
    }
}
