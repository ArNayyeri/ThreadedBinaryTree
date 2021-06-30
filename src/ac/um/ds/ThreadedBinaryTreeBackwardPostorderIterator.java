package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class ThreadedBinaryTreeBackwardPostorderIterator<T, IBTN extends InternalThreadedBinaryTreeNode<T>>
        extends BinaryTreeBackwardPostorderIterator<T, IBTN> implements Iterator<T> {

    public ThreadedBinaryTreeBackwardPostorderIterator(BinaryTree<T, IBTN> binaryTree) {
        super(binaryTree);
    }

    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPostorderReversePreBegin) {
            Stack<IBTN> temp = new Stack<IBTN>();
            do {
                if (mCurrentNode.mRightChild != null && !mCurrentNode.isRightThread()) {
                    mCurrentNode = (IBTN) mCurrentNode.mRightChild;
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else if (mCurrentNode.mLeftChild != null && !mCurrentNode.isLeftThread()) {
                    mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
                    mParentsStack.pop();
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else {
                    while (mParentsStack.peek().mLeftChild == null ||
                            mParentsStack.peek().isLeftThread() || mParentsStack.peek().mLeftChild == mCurrentNode) {
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
}
