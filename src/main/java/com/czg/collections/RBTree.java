package com.czg.collections;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class RBTree<K extends Comparable<K>,V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private RBNode root;



    /**
     * 围绕p左旋  每个节点都是双向的
     *         pf                   pf
     *       /                     /
     *      p                     pr(r)
     *    /   \                  /  \
     *   pl   pr(r)      ====>  p   rr
     *       /  \              / \
     *      rl  rr            pl  rl
     * */
    private void leftRotate(RBNode p) {
        if (p != null) {
            RBNode r = p.right;
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }
            r.parent = p.parent;
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            r.left=p;
            p.parent=r;
        }

    }
    /**
     * 围绕p左旋  每个节点都是双向的
     *         pf                   pf
     *       /                     /
     *      p                     pl(l)
     *    /   \                  /  \
     *   pl(l)   pr     ====>  ll   p
     *  /  \                       / \
     * ll  lr                     lr  pr
     * */
    private void rightRotate(RBNode p) {
        if (p != null) {
            RBNode l = p.left;
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            } else if (p.parent.right == p) {
                p.parent.right = l;
            } else {
                p.parent.left = l;
            }
            l.right = p;
            p.parent = l;
        }

    }

    private boolean colorOf(RBNode node) {
        return node == null ? BLACK : node.color;
    }

    private RBNode parentOf(RBNode node) {
        return node != null ? node.parent : null;
    }

    private RBNode leftOf(RBNode node) {
        return node != null ? node.left : null;
    }

    private RBNode rightOf(RBNode node) {
        return node != null ? node.right : null;
    }

    /**
     * 设置颜色
     * */
    private void setColor(RBNode node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }
    /**
     * 三种情况
     * 1.2-3-4树：新增元素+2节点合并（节点中只有一个元素）=3节点（节点中有2个元素）
     *   红黑树：新增一个红色节点+黑色父亲节点=上黑下红（2节点）----------------不要调整
     *
     * 2.2-3-4树：新增元素+3节点合并（节点中有两个元素）=4节点（节点中有3个元素）
     *   有四种小情况（左3，右3，还有2个左中右不需要调整）---------左3，右3需要调整，其余不需要调整
     *   红黑树：新增红色节点+上黑下红=排序后中间节点是黑色，两边都是红色节点（3节点）
     *
     * 3.2-3-4树：新增一个元素+4节点合并=原来的4节点分裂，中间的元素升级为父节点，新增一个元素与剩下的其中一个合并
     *   红黑树:新增红色节点+爷爷节点黑，父亲节点和叔叔节点都是红色=爷爷节点变红，父亲和叔叔节点变黑，如果爷爷节点是根节点，则再次变为黑色
     * */
    private void fixAfterPut(RBNode x) {
        x.color = RED;
        //本质上就是父节点是黑色的就不需要调整
        while (x != null && x != root && x.parent.color == RED) {
            //x的父节点是爷爷的左孩子*（左三）
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                //叔叔节点
                RBNode y = rightOf(parentOf(parentOf(x)));
                //第三种情况
                if (colorOf(y)==RED){//四节点合并
                    setColor(parentOf(x),BLACK);//父亲节点变黑
                    setColor(y,BLACK);//叔叔节点变黑
                    setColor(parentOf(parentOf(x)),RED);//爷爷节点变红
                    x= parentOf(parentOf(x));
                }
                //第二种情况
                else {
                    if(x==rightOf(parentOf(x))){
                        x=parentOf(x);//x指向x的父亲地址
                        leftRotate(x);//x变为父亲后再左旋
                    }
                    //三节点合并
                    setColor(parentOf(x),BLACK);//父亲变黑
                    setColor(parentOf(parentOf(x)),RED);//爷爷变红
                    rightRotate(parentOf(parentOf(x)));//爷爷右旋
                }
            }else {//右三 相反
                //叔叔节点
                RBNode y = leftOf(parentOf(parentOf(x)));
                //第三种情况
                if (colorOf(y)==RED){//四节点合并
                    setColor(parentOf(x),BLACK);//父亲节点变黑
                    setColor(y,BLACK);//叔叔节点变黑
                    setColor(parentOf(parentOf(x)),RED);//爷爷节点变红
                    //重要 ，循环向上递归，解决一条链路上不能有两个重复的红色节点
                    x= parentOf(parentOf(x));
                }
                //第二种情况
                else {
                    if(x==leftOf(parentOf(x))){
                        x=parentOf(x);//x指向x的父亲地址
                        rightRotate(x);//x变为父亲后再右旋
                    }
                    //三节点合并
                    setColor(parentOf(x),BLACK);//父亲变黑
                    setColor(parentOf(parentOf(x)),RED);//爷爷变红
                    leftRotate(parentOf(parentOf(x)));//爷爷左旋
                }
            }
        }
        //最后根节点需要染黑
        root.color=BLACK;
    }
    public void put(K key,V value) {
        //先把根节点赋值给t
        RBNode t = this.root;
        //如果是根节点，直接新增一个根节点
        if (t == null) {
            root = new RBNode<>(null, key, value == null ? key : value);
            return;
        }
        int cmp;
        //寻找插入位置
        //定义一个双亲指针
        RBNode parent;
        if (key == null) {
            throw new NullPointerException();
        }
        //沿着根节点寻找插入位置
        do {
            parent = t;
            cmp = key.compareTo((K) t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                //如果put进来的key和它一样，直接覆盖
                t.setValue(value == null ? key : value);
                return;
            }

        } while (t != null);
        //如果循环能走完就说明走到最后的叶子节点了
        RBNode<K, Object> e = new RBNode<>(parent, key, value == null ? key : value);
        //如果比较最终落在左子树，则直接将左子树的指针指向e
        if (cmp < 0) {
            parent.left = e;
        }
        //如果比较最终落在右子树，则直接将右子树的指针指向e
        else {
            parent.right = e;
        }
        //todo 调整变色
        fixAfterPut(e);
    }


    /**
     * 找到指定节点的前驱结点，即小于node节点的最大值
     * */
    private RBNode predecessor(RBNode node) {
        if (null == node) {
            return null;
        } else if (node.left != null) {
            RBNode p = node.left;
            while (p.right != null) {
                p = p.right;
            }
            return p;
        } else {
            RBNode p = node.parent;
            RBNode ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * 找到指定节点的后继结点，即小于node节点的最小值
     * */
    private RBNode successor(RBNode node) {
        if (null == node) {
            return null;
        } else if (node.right != null) {
            RBNode p = node.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        } else {
            RBNode p = node.parent;
            RBNode ch = node;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    public V remove(K key){
            RBNode node = getNode(key);
        if(node==null){
            return null;
        }
        V oldValue = (V) node.value;
        deleteNode(node);
        return oldValue;
    }

    /**
     * 删除操作
     * 1.删除叶子节点，直接删除
     * 2.删除的节点有一个子节点，那么用叶子节点来代替
     * 3。如果删除的节点有2个子节点，此时需要找到该节点的前驱节点或者后继节点来替代
     * */
    private void deleteNode(RBNode node){
        //3.node节点有两个孩子
        if(node.left!=null&&node.right!=null){
            RBNode successor=successor(node);
            node.key = successor.key;
            node.value = successor.value;
            node=successor;
        }

        RBNode replacement = node.left!=null?node.left:node.right;
        //2.替代节点不为空
        if(replacement!=null){
            //替代者的父指针指向原来node的父亲
           replacement.parent = node.parent;
           //node是根节点
           if(node.parent==null){
               root = replacement;
           }
           //node是左孩子，替代者依然是左孩子
           else if(node==node.parent.left){
               node.parent.left=replacement;
           }
           //node是右孩子，替代着还是右孩子
           else {
               node.parent.right = replacement;
           }
           //将node 的左右孩子指针和父指针都指向null,此时node 处于游离状态，等待垃圾回收
           node.left = node.right = node.parent = null;

           //替换完之后需要调整平衡
            if(node.color==BLACK){
                //需要调整
                //fixAfterRemove(replacement)
            }
        }
        //删除节点就是根节点
        else if(node.parent==null){
            root = null;
        }
        //1.叶子节点，replacement为null
        else {
            //先调整
            if(node.color==BLACK){
                //这种情况替代节点一定是红色，此时只要变色
                //fixAfterRemove(node)
            }

            //再删除
            if(node.parent!=null){
                if(node==node.parent.left){
                    node.parent.left = null;
                }
                else if(node==node.parent.right){
                    node.parent.right=null;
                }
                node.parent=null;
            }
        }

    }

    //Black 代表空的意思
    private void fixAfterRemove(RBNode x){
        // 情况二，情况三
        while (x!=root&&colorOf(x)==BLACK){
            //x是左孩子的情况
            if(x == leftOf(parentOf(x))){
                //兄弟节点
                RBNode rnode = rightOf(parentOf(x));

                //判断此时兄弟节点是否是真正的兄弟节点
                if(colorOf(rnode) == RED){
                    setColor(rnode,BLACK);
                    setColor(parentOf(x),RED);
                    leftRotate(parentOf(x));
                    //找到真正的兄弟节点
                    rnode = rightOf(parentOf(x));
                }
                //情况三 找兄弟借，兄弟没的借
                if(colorOf(leftOf(rnode))==BLACK && colorOf(rightOf(rnode))==BLACK){
                    //todo
                    setColor(rnode,RED);
                    x= parentOf(x);
                }
                //情况二 找兄弟借，兄弟有的借
                else {
                    //分两种情况，兄弟节点本来是三节点或者是四节点的情况
                    //右孩子为空
                    if(colorOf(rightOf(rnode))==BLACK){
                        setColor(leftOf(rnode),BLACK);
                        setColor(rnode,RED);
                        rightRotate(rnode);
                        rnode=rightOf(parentOf(x));
                    }
                    setColor(rnode,colorOf(parentOf(x)));
                    setColor(x,BLACK);
                    setColor(rightOf(rnode),BLACK);
                    leftRotate(parentOf(x));
                    x=root;
                }
            }
            //x是右孩子的情况
            else {

            }

        }
        //情况一 替代节点是红色，则直接染黑，补偿删除的黑色节点，这样红黑树依然保持平衡
        setColor(x,BLACK);
    }
    private RBNode getNode(K key) {
        RBNode node = this.root;
        while (node != null) {
            int cmp = key.compareTo((K) node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else
                return node;
        }
        return null;
    }
    static class RBNode<K extends Comparable<K>,V>{
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K key;
        private V value;

        public RBNode() {
        }

        public RBNode(RBNode parent, K key, V value) {
            this.parent = parent;
            this.color = BLACK;
            this.key = key;
            this.value = value;
        }

        public RBNode(RBNode parent, boolean color, K key, V value) {
            this.parent = parent;
            this.color = color;
            this.key = key;
            this.value = value;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }






    public static boolean isRED() {
        return RED;
    }

    public static boolean isBLACK() {
        return BLACK;
    }

    public RBNode getRoot() {
        return root;
    }

    public void setRoot(RBNode root) {
        this.root = root;
    }
}
