package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeBackwardInorderIterator<T, IBTN extends InternalBinaryTreeNode<T>> implements Iterator<T> {
    protected IBTN mCurrentNode;
    protected Stack<IBTN> mParentsStack;
    protected BinaryTree<T, IBTN> mBinaryTree;

    //constructors:
    public BinaryTreeBackwardInorderIterator() {
    }

    // The binaryTree parameter is used to initialize the mCurrentNode to
    // the appropriate dummy root. In addition, it is used to initialize mParentsStack
    public BinaryTreeBackwardInorderIterator(BinaryTree<T, IBTN> binaryTree) {
        mBinaryTree = binaryTree;
        mCurrentNode = binaryTree.mInorderReversePreBegin;
        mParentsStack = new Stack<IBTN>();
    }

    //overloading operators:
    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mInorderReversePreBegin) {
            mCurrentNode = mBinaryTree.getRootNode().mActualNode;
        }
        while (mCurrentNode != null) {
            mParentsStack.push(mCurrentNode);
            mCurrentNode = (IBTN) mCurrentNode.getRightChild();
        }
        mCurrentNode = (IBTN) mParentsStack.peek().getLeftChild();
        return mParentsStack.pop().mData;
    }

    @Override
    public boolean hasNext() {
        return mCurrentNode != null || !mParentsStack.empty();
    }
}
