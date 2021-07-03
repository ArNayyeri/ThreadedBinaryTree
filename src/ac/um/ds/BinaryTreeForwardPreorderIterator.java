package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeForwardPreorderIterator<T, IBTN extends InternalBinaryTreeNode<T>> implements Iterator<T> {
    protected IBTN mCurrentNode;
    protected Stack<IBTN> mParentsStack;
    protected BinaryTree<T, IBTN> mBinaryTree;

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
            Stack<IBTN> stack = new Stack<IBTN>();
            do {
                if (mCurrentNode.mLeftChild != null) {
                    mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
                    mParentsStack.push(mCurrentNode);
                    stack.push(mCurrentNode);
                } else if (mCurrentNode.mRightChild != null) {
                    mCurrentNode = (IBTN) mCurrentNode.mRightChild;
                    mParentsStack.pop();
                    mParentsStack.push(mCurrentNode);
                    stack.push(mCurrentNode);
                } else {
                    while (mParentsStack.peek().mRightChild == null || mParentsStack.peek().mRightChild == mCurrentNode)
                        mCurrentNode = mParentsStack.pop();
                    mCurrentNode = (IBTN) mParentsStack.pop().mRightChild;
                    mParentsStack.push(mCurrentNode);
                    stack.push(mCurrentNode);
                }
            } while (mCurrentNode != mBinaryTree.mPreorderEnd);
            stack.pop();
            mParentsStack.pop();
            mParentsStack.pop();
            while (!stack.empty())
                mParentsStack.push(stack.pop());
        }
        mCurrentNode = mParentsStack.pop();
        return mCurrentNode.mData;
    }

    @Override
    public boolean hasNext() {
        return !mParentsStack.empty();
    }
}
