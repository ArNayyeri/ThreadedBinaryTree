package ac.um.ds;

public class InternalThreadedBinaryTreeNode<T> extends InternalBinaryTreeNode<T> {
    protected boolean leftThread;
    protected boolean rightThread;

    public InternalThreadedBinaryTreeNode() {
        super();
        leftThread = true;
        rightThread = true;
    }

    public boolean isLeftThread() {
        return leftThread;
    }

    public boolean isRightThread() {
        return rightThread;
    }
}
