package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class ThreadedBinaryTreeForwardPostorderIterator<T, IBTN extends InternalThreadedBinaryTreeNode<T>>
        extends BinaryTreeForwardPostorderIterator<T, IBTN> implements Iterator<T> {

    public ThreadedBinaryTreeForwardPostorderIterator(BinaryTree<T, IBTN> binaryTree) {
        super(binaryTree);
    }

    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPostorderPreBegin) {
            mCurrentNode = mBinaryTree.getRootNode().mActualNode;
            Stack stack = new Stack<IBTN>();
            stack.push(mCurrentNode);
            while (!stack.empty()) {
                mCurrentNode = (IBTN) stack.pop();
                mParentsStack.push(mCurrentNode);
                if (mCurrentNode.mLeftChild != null && !mCurrentNode.leftThread)
                    stack.push(mCurrentNode.mLeftChild);
                if (mCurrentNode.mRightChild != null && !mCurrentNode.rightThread)
                    stack.push(mCurrentNode.mRightChild);
            }
        }
        mCurrentNode = mParentsStack.peek();
        return mParentsStack.pop().mData;
    }
}
