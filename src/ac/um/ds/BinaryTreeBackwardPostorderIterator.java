package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeBackwardPostorderIterator<T, IBTN extends InternalBinaryTreeNode<T>> implements Iterator<T> {
    private IBTN mCurrentNode;
    private Stack<IBTN> mParentsStack;
    private BinaryTree<T, IBTN> mBinaryTree;

    //constructors:
    public BinaryTreeBackwardPostorderIterator() {
    }

    // The binaryTree parameter is used to initialize the mCurrentNode to
    // the appropriate dummy root. In addition, it is used to initialize mParentsStack
    public BinaryTreeBackwardPostorderIterator(BinaryTree<T, IBTN> binaryTree) {
        mBinaryTree = binaryTree;
        mParentsStack = new Stack<IBTN>();
        mCurrentNode = binaryTree.mPostorderReversePreBegin;
        mParentsStack.push(binaryTree.mRevPostorderEnd);
        mParentsStack.push(binaryTree.mRevInorderEnd);
        mParentsStack.push(binaryTree.mInorderEnd);
        mParentsStack.push(binaryTree.mPostorderEnd);
    }

    //overloading operators:
    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPostorderReversePreBegin) {
            Stack<IBTN> temp = new Stack<IBTN>();
            do {
                if (mCurrentNode.mRightChild != null) {
                    mCurrentNode = (IBTN) mCurrentNode.mRightChild;
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else if (mCurrentNode.mLeftChild != null) {
                    mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
                    mParentsStack.pop();
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else {
                    while (mParentsStack.peek().mLeftChild == null || mParentsStack.peek().mLeftChild == mCurrentNode) {
                        mCurrentNode = mParentsStack.pop();
                    }
                    mCurrentNode = (IBTN) mParentsStack.pop().mLeftChild;
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                }
            } while (mCurrentNode != mBinaryTree.mRevPostorderEnd);
            temp.pop();
            mParentsStack.pop();
            mParentsStack.pop();
            while (!temp.empty()) {
                mParentsStack.push(temp.pop());
            }
            mCurrentNode = mParentsStack.peek();
        }
        mCurrentNode = mParentsStack.peek();
        return mParentsStack.pop().mData;
    }

    @Override
    public boolean hasNext() {
        return !mParentsStack.empty();
    }
}
