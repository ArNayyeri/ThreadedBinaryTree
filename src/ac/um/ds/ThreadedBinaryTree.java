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
        //try catch
        IBTN root = createInternalBinaryTreeNodeInstance();
        root.mData = data;
        root.leftThread = true;
        root.rightThread = true;
        root.mRightChild = threadedRoot;
        root.mLeftChild = threadedRoot;
        mRevPreorderEnd.mLeftChild = root;
        ++mSize;
    }

    @Override
    public void insertLeftChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        //try catch
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

    @Override
    public void insertRightChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        //try catch
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

    @Override
    protected void deleteNode(IBTN parentNode, IBTN theNode) {
        //try catch
        --mSize;
        if (parentNode.mRightChild == theNode) {
            parentNode.mRightChild = theNode.mRightChild;
            parentNode.rightThread = true;
        } else if (parentNode.mLeftChild == theNode) {
            parentNode.mLeftChild = theNode.mLeftChild;
            parentNode.leftThread = true;
        }
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
}
