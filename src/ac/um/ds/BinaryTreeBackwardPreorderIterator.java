package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeBackwardPreorderIterator<T, IBTN extends InternalBinaryTreeNode<T>> implements Iterator<T> {
    private IBTN mCurrentNode;
    private Stack<IBTN> mParentsStack;
    private BinaryTree<T, IBTN> mBinaryTree;

    //constructors:
    public BinaryTreeBackwardPreorderIterator() {
    }

    // The binaryTree parameter is used to initialize the mCurrentNode to
    // the appropriate dummy root. In addition, it is used to initialize mParentsStack
    public BinaryTreeBackwardPreorderIterator(BinaryTree<T, IBTN> binaryTree) {
        mCurrentNode = binaryTree.mPreorderReversePreBegin;
        mBinaryTree = binaryTree;
        mParentsStack = new Stack<IBTN>();
        mParentsStack.push(binaryTree.mRevPreorderEnd);
    }

    //overloading operators:
    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPreorderReversePreBegin) {
            Stack stack = new Stack<IBTN>();
            stack.push(mParentsStack.pop().mLeftChild);
            while (!stack.empty()) {
                mCurrentNode = (IBTN) stack.pop();
                mParentsStack.push(mCurrentNode);
                if (mCurrentNode.mRightChild != null)
                    stack.push(mCurrentNode.mRightChild);
                if (mCurrentNode.mLeftChild != null)
                    stack.push(mCurrentNode.mLeftChild);
            }
        }
        mCurrentNode = mParentsStack.pop();
        return mCurrentNode.mData;
    }

    @Override
    public boolean hasNext() {
        return !mParentsStack.empty();
    }
}
