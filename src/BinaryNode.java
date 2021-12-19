public class BinaryNode<K extends Comparable,T>{

    private K key;
    private T element;
    private BinaryNode left, right, parent;

    public BinaryNode(T element, K key, BinaryNode parent) {
        this.key = key;
        this.element = element;
        this.parent = parent;
    }

    public boolean isLeaf(){
        return !(this.hasRight() || this.hasLeft());
    }

    public K getKey() {
        return key;
    }

    public T getElement() {
        return element;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public BinaryNode getParent() {
        return parent;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public void setParent(BinaryNode parent) {
        this.parent = parent;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Boolean hasLeft(){
        return this.getLeft() != null;
    }

    public Boolean hasRight(){
        return this.getRight() != null;
    }

    public String toString(){
        return (String)getElement();
    }
}
