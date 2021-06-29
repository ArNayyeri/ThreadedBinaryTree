package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeForwardPreorderIterator<T, IBTN extends InternalBinaryTreeNode<T>> implements Iterator<T> {
    private IBTN mCurrentNode;
    private Stack<IBTN> mParentsStack;
    private BinaryTree<T, IBTN> mBinaryTree;

    //constructors:
    public BinaryTreeForwardPreorderIterator() {
    }

    // The binaryTree parameter is used to initialize the mCurrentNode to
    // the appropriate dummy root. In addition, it is used to initialize mParentsStack
    public BinaryTreeForwardPreorderIterator(BinaryTree<T, IBTN> binaryTree) {
        mBinaryTree = binaryTree;
        mParentsStack = new Stack<IBTN>();
        mCurrentNode = binaryTree.mPreorderPreBegin;
        mParentsStack.push(binaryTree.mPreorderEnd);
        mParentsStack.push(binaryTree.mPreorderPreBegin);
    }

    //overloading operators:
    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPreorderPreBegin) {
            Stack<IBTN> temp = new Stack<IBTN>();
            do {
                if (mCurrentNode.mLeftChild != null) {
                    mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else if (mCurrentNode.mRightChild != null) {
                    mCurrentNode = (IBTN) mCurrentNode.mRightChild;
                    mParentsStack.pop();
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else {
                    while (mParentsStack.peek().mRightChild == null || mParentsStack.peek().mRightChild == mCurrentNode) {
                        mCurrentNode = mParentsStack.pop();
                    }
                    mCurrentNode = (IBTN) mParentsStack.pop().mRightChild;
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                }
            } while (mCurrentNode != mBinaryTree.mPreorderEnd);
            temp.pop();
            mParentsStack.pop();
            mParentsStack.pop();
            while (!temp.empty()) {
                mParentsStack.push(temp.pop());
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
