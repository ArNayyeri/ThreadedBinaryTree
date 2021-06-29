package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeForwardPostorderIterator<T, IBTN extends InternalBinaryTreeNode<T>> implements Iterator<T> {
    private IBTN mCurrentNode;
    private Stack<IBTN> mParentsStack;
    private BinaryTree<T, IBTN> mBinaryTree;

    //constructors:
    public BinaryTreeForwardPostorderIterator() {
    }

    // The binaryTree parameter is used to initialize the mCurrentNode to
    // the appropriate dummy root. In addition, it is used to initialize mParentsStack
    public BinaryTreeForwardPostorderIterator(BinaryTree<T, IBTN> binaryTree) {
        mParentsStack = new Stack<IBTN>();
        mBinaryTree = binaryTree;
        mCurrentNode = binaryTree.mPostorderPreBegin;
    }

    //overloading operators:
    @Override
    public T next() {
        if (mCurrentNode == mBinaryTree.mPostorderPreBegin) {
            mCurrentNode = mBinaryTree.getRootNode().mActualNode;
            Stack stack = new Stack<IBTN>();
            stack.push(mCurrentNode);
            while (!stack.empty()) {
                mCurrentNode = (IBTN) stack.pop();
                mParentsStack.push(mCurrentNode);
                if (mCurrentNode.mLeftChild != null)
                    stack.push(mCurrentNode.mLeftChild);
                if (mCurrentNode.mRightChild != null)
                    stack.push(mCurrentNode.mRightChild);
            }
        }
        mCurrentNode = mParentsStack.peek();
        return mParentsStack.pop().mData;
    }

    @Override
    public boolean hasNext() {
        return mCurrentNode != mBinaryTree.mInorderEnd.mLeftChild || !mParentsStack.empty();
    }
}
