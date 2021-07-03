package ac.um.ds;

import java.util.Iterator;

public class ThreadedBinaryTree<T, IBTN extends InternalThreadedBinaryTreeNode<T>> extends BinaryTree<T, IBTN> {

    private IBTN threadedRoot;

    public ThreadedBinaryTree() {
        super();
        threadedRoot = createInternalBinaryTreeNodeInstance();
        threadedRoot.mRightChild = threadedRoot;
    }

    public IBTN getThreadedRoot() {
        return threadedRoot;
    }

    @Override
    protected IBTN createInternalBinaryTreeNodeInstance() {
        return (IBTN) new InternalThreadedBinaryTreeNode<T>();
    }

    @Override
    public void insertRootNode(T data) {
        if (mRevPreorderEnd.mLeftChild != null)
            throw new RuntimeException("root is exist!");
        else {
            IBTN root = createInternalBinaryTreeNodeInstance();
            root.mData = data;
            root.leftThread = true;
            root.rightThread = true;
            root.mRightChild = threadedRoot;
            root.mLeftChild = threadedRoot;
            mRevPreorderEnd.mLeftChild = root;
            ++mSize;
        }
    }

    @Override
    public void insertLeftChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        if (!parentNode.mActualNode.isLeftThread())
            throw new RuntimeException("left child is exist!");
        else {
            ++mSize;
            IBTN node = createInternalBinaryTreeNodeInstance();
            node.mData = data;
            node.mLeftChild = parentNode.mActualNode.mLeftChild;
            node.leftThread = true;
            node.rightThread = true;
            node.mRightChild = parentNode.mActualNode;
            parentNode.mActualNode.mLeftChild = node;
            parentNode.mActualNode.leftThread = false;
        }
    }

    @Override
    public void insertRightChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        if (!parentNode.mActualNode.isRightThread()) {
            throw new RuntimeException("right child is exist!");
        } else {
            ++mSize;
            IBTN node = createInternalBinaryTreeNodeInstance();
            node.mData = data;
            node.mLeftChild = parentNode.mActualNode;
            node.mRightChild = parentNode.mActualNode.mRightChild;
            node.leftThread = true;
            node.rightThread = true;
            parentNode.mActualNode.mRightChild = node;
            parentNode.mActualNode.rightThread = false;
        }
    }

    @Override
    protected void deleteNode(IBTN parentNode, IBTN theNode) {
        if (!theNode.isRightThread() || !theNode.isLeftThread())
            throw new RuntimeException("node isn't leaf!");
        if (parentNode.mRightChild == theNode) {
            parentNode.mRightChild = theNode.mRightChild;
            parentNode.rightThread = true;
            --mSize;
        } else if (parentNode.mLeftChild == theNode) {
            parentNode.mLeftChild = theNode.mLeftChild;
            parentNode.leftThread = true;
            --mSize;
        } else
            throw new RuntimeException("node isn't exist!");
    }

    @Override
    public Iterator<T> forwardInorderIterator() {
        return new ThreadedBinaryTreeForwardInorderIterator<T, IBTN>(this);
    }

    @Override
    public Iterator<T> backwardInorderIterator() {
        return new ThreadedBinaryTreeBackwardInorderIterator<T, IBTN>(this);
    }

    @Override
    public Iterator<T> forwardPostorderIterator() {
        return new ThreadedBinaryTreeForwardPostorderIterator<T, IBTN>(this);
    }

    @Override
    public Iterator<T> backwardPostorderIterator() {
        return new ThreadedBinaryTreeBackwardPostorderIterator<T, IBTN>(this);
    }

    @Override
    public Iterator<T> forwardPreorderIterator() {
        return new ThreadedBinaryTreeForwardPreorderIterator<T, IBTN>(this);
    }

    @Override
    public Iterator<T> backwardPreorderIterator() {
        return new ThreadedBinaryTreeBackwardPreorderIterator<T, IBTN>(this);
    }

    @Override
    public int depthCalc(IBTN root, int depth) {
        int res = depth;
        if (!root.isRightThread()) {
            int rightDepth = depthCalc((IBTN) root.getRightChild(), depth + 1);
            res = (res > rightDepth) ? res : rightDepth;
        }
        if (!root.isLeftThread()) {
            int leftDepth = depthCalc((IBTN) root.getLeftChild(), depth + 1);
            res = (res > leftDepth) ? res : leftDepth;
        }
        return res;
    }

    @Override
    public void recursiveDraw(IBTN root, String[][] lines, int x, int y) {
        int des = 1;
        for (int i = 0; i < y / 2 + 2; i++)
            des *= 2;
        des = 160 / des;
        //root:

        lines[y][x] = root.getData().toString();
        //left child:
        if (!root.isLeftThread()) {
            for (int i = 0; i < des; i++)
                lines[y + 1][x - i] = "-";
            lines[y + 1][x] = "|";
            recursiveDraw((IBTN) root.getLeftChild(), lines, x - des, y + 2);
        }
        //right child:
        if (!root.isRightThread()) {
            for (int i = 0; i < des; i++)
                lines[y + 1][x + i] = "-";
            lines[y + 1][x] = "|";
            recursiveDraw((IBTN) root.getRightChild(), lines, x + des, y + 2);
        }

    }
}
