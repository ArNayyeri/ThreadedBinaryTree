package ac.um.ds;

import java.util.Iterator;

public class ThreadedBinaryTree<T, IBTN extends InternalThreadedBinaryTreeNode<T>> extends BinaryTree<T, IBTN> {

    public ThreadedBinaryTree() {
        super();
    }

    @Override
    protected IBTN createInternalBinaryTreeNodeInstance() {
        return (IBTN) new InternalThreadedBinaryTreeNode<T>();
    }

    @Override
    public void insertLeftChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        //try catch
        ++mSize;
        IBTN node = createInternalBinaryTreeNodeInstance();
        node.mData = data;
        node.mLeftChild = parentNode.mActualNode.mLeftChild;
        node.mRightChild = parentNode.mActualNode;
        parentNode.mActualNode.mLeftChild = node;
        parentNode.mActualNode.leftThread = false;
    }

    @Override
    public void insertRightChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        //try catch
        ++mSize;
        IBTN node = createInternalBinaryTreeNodeInstance();
        node.mData = data;
        node.mLeftChild = parentNode.mActualNode;
        node.mRightChild = parentNode.mActualNode.mLeftChild;
        parentNode.mActualNode.mRightChild = node;
        parentNode.mActualNode.rightThread = false;
    }

    @Override
    protected void deleteNode(IBTN parentNode, IBTN theNode) {
        //try catch
        --mSize;
        if (parentNode.mRightChild == theNode) {
            parentNode.mRightChild = theNode.mRightChild;
            parentNode.rightThread = true;
        } else if (parentNode.mLeftChild == theNode) {
            parentNode.mLeftChild = theNode.mLeftChild;
            parentNode.leftThread = true;
        }
    }

}
