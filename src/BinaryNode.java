public class BinaryNode<K extends Comparable,T>{

    private K key;
    private T element;
    private BinaryNode left, right, parent;
    private int height;

    public BinaryNode(T element, K key, BinaryNode parent) {
        this.key = key;
        this.element = element;
        this.parent = parent;
    }


    /**
     * Metodo auxiliar para conocer la altura de un nodo
     * @param node nodo del que se desea conocer la altura
     * @return int - altura del nodo
     */
    public int getHeightNode(BinaryNode node) {
        if(!node.isLeaf()){//Cuando es hoja
            return 0;
        }else if(node.hasLeft() && node.hasRight()){//Cuando tiene dos hijos
            int auxMax = Math.max(getHeightNode(node.getLeft()), getHeightNode(node.getRight()));
            return auxMax+1;
        }else{//Cuando tiene un hijo
            boolean hasLeft = node.hasLeft();
            return 1 + (hasLeft ? getHeightNode(node.getLeft()) : getHeightNode(node.getRight()));
        }
    }


    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
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
