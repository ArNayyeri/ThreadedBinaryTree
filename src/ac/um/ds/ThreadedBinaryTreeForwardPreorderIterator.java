package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class ThreadedBinaryTreeForwardPreorderIterator <T, IBTN extends InternalThreadedBinaryTreeNode<T>>
        extends BinaryTreeForwardPreorderIterator<T,IBTN> implements Iterator<T> {

    public ThreadedBinaryTreeForwardPreorderIterator(BinaryTree<T, IBTN> binaryTree) {
        super(binaryTree);
    }

    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPreorderPreBegin) {
            Stack<IBTN> temp = new Stack<IBTN>();
            do {
                if (mCurrentNode.mLeftChild != null && !mCurrentNode.leftThread) {
                    mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else if (mCurrentNode.mRightChild != null && !mCurrentNode.rightThread) {
                    mCurrentNode = (IBTN) mCurrentNode.mRightChild;
                    mParentsStack.pop();
                    mParentsStack.push(mCurrentNode);
                    temp.push(mCurrentNode);
                } else {
                    while (mParentsStack.peek().mRightChild == null ||
                            mParentsStack.peek().rightThread || mParentsStack.peek().mRightChild == mCurrentNode) {
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
}
