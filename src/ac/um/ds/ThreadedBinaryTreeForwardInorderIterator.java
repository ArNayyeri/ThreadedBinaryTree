package ac.um.ds;

import java.util.Iterator;

public class ThreadedBinaryTreeForwardInorderIterator<T, IBTN extends InternalThreadedBinaryTreeNode<T>>
        extends BinaryTreeForwardInorderIterator<T, IBTN> implements Iterator<T> {

    public ThreadedBinaryTreeForwardInorderIterator(ThreadedBinaryTree<T, IBTN> binaryTree) {
        super(binaryTree);
    }

    private IBTN rightMost(IBTN i) {
        if (i == null)
            return null;
        while (!i.isRightThread())
            i = (IBTN) i.mRightChild;
        return i;
    }

    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mInorderPreBegin) {
            mCurrentNode = mBinaryTree.getRootNode().mActualNode;
            while (true) {
                mCurrentNode = rightMost(mCurrentNode);
                mParentsStack.push(mCurrentNode);
                if (mCurrentNode.mLeftChild == (IBTN) ((ThreadedBinaryTree) mBinaryTree).getThreadedRoot())
                    break;
                boolean b = false;
                while (mCurrentNode.isLeftThread()) {
                    mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
                    mParentsStack.push(mCurrentNode);
                    if (mCurrentNode.mLeftChild == (IBTN) ((ThreadedBinaryTree) mBinaryTree).getThreadedRoot()) {
                        b = true;
                        break;
                    }
                }
                if (b)
                    break;
                mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
            }
        }
        mCurrentNode = mParentsStack.peek();
        return mParentsStack.pop().mData;
    }

    @Override
    public boolean hasNext() {
        return mCurrentNode == mBinaryTree.mInorderPreBegin || !mParentsStack.empty();
    }
}
