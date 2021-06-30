package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class ThreadedBinaryTreeBackwardPreorderIterator<T, IBTN extends InternalThreadedBinaryTreeNode<T>>
        extends BinaryTreeBackwardPreorderIterator<T, IBTN> implements Iterator<T> {

    public ThreadedBinaryTreeBackwardPreorderIterator(BinaryTree<T, IBTN> binaryTree) {
        super(binaryTree);
    }

    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPreorderReversePreBegin) {
            Stack stack = new Stack<IBTN>();
            stack.push(mParentsStack.pop().mLeftChild);
            while (!stack.empty()) {
                mCurrentNode = (IBTN) stack.pop();
                mParentsStack.push(mCurrentNode);
                if (mCurrentNode.mRightChild != null && !mCurrentNode.rightThread)
                    stack.push(mCurrentNode.mRightChild);
                if (mCurrentNode.mLeftChild != null && !mCurrentNode.leftThread)
                    stack.push(mCurrentNode.mLeftChild);
            }
        }
        mCurrentNode = mParentsStack.pop();
        return mCurrentNode.mData;
    }
}
