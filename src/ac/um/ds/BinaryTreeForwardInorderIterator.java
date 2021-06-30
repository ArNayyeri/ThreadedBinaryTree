package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeForwardInorderIterator<T, IBTN extends InternalBinaryTreeNode<T>> implements Iterator<T> {
    protected IBTN mCurrentNode;
    protected Stack<IBTN> mParentsStack;
    protected BinaryTree<T, IBTN> mBinaryTree;

    //constructors:
    public BinaryTreeForwardInorderIterator() {
    }

    // The binaryTree parameter is used to initialize the mCurrentNode to
    // the appropriate dummy root. In addition, it is used to initialize mParentsStack
    public BinaryTreeForwardInorderIterator(BinaryTree<T, IBTN> binaryTree) {
        mParentsStack = new Stack<IBTN>();
        mCurrentNode = binaryTree.mInorderPreBegin;
        mBinaryTree = binaryTree;
    }

    //overloading operators:
    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mInorderPreBegin)
            mCurrentNode = mBinaryTree.getRootNode().mActualNode;
        while (mCurrentNode != null) {
            mParentsStack.push(mCurrentNode);
            mCurrentNode = (IBTN) mCurrentNode.getLeftChild();
        }
        mCurrentNode = (IBTN) mParentsStack.peek().getRightChild();
        return mParentsStack.pop().mData;
    }

    @Override
    public boolean hasNext() {
        return mCurrentNode != null || !mParentsStack.empty();
    }
}
