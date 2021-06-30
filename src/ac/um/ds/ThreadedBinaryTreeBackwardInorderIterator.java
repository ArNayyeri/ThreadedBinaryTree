package ac.um.ds;

import java.util.Iterator;

public class ThreadedBinaryTreeBackwardInorderIterator<T, IBTN extends InternalThreadedBinaryTreeNode<T>>
        extends BinaryTreeBackwardInorderIterator<T, IBTN> implements Iterator<T> {

    public ThreadedBinaryTreeBackwardInorderIterator(BinaryTree<T, IBTN> binaryTree) {
        super(binaryTree);
    }

    private IBTN leftMost(IBTN i) {
        if (i == null)
            return null;
        while (!i.isLeftThread())
            i = (IBTN) i.mLeftChild;
        return i;
    }

    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mInorderReversePreBegin) {
            mCurrentNode = mBinaryTree.getRootNode().mActualNode;
            while (true) {
                mCurrentNode = leftMost(mCurrentNode);
                mParentsStack.push(mCurrentNode);
                if (mCurrentNode.mRightChild == (IBTN) ((ThreadedBinaryTree) mBinaryTree).getThreadedRoot())
                    break;
                boolean b = false;
                while (mCurrentNode.isRightThread()) {
                    mCurrentNode = (IBTN) mCurrentNode.mRightChild;
                    mParentsStack.push(mCurrentNode);
                    if (mCurrentNode.mRightChild == (IBTN) ((ThreadedBinaryTree) mBinaryTree).getThreadedRoot()) {
                        b = true;
                        break;
                    }
                }
                if (b)
                    break;
                mCurrentNode = (IBTN) mCurrentNode.mRightChild;
            }
        }
        mCurrentNode = mParentsStack.peek();
        return mParentsStack.pop().mData;
    }

    @Override
    public boolean hasNext() {
        return mCurrentNode == mBinaryTree.mInorderReversePreBegin|| !mParentsStack.empty();
    }
}
