public class AVLNode<K extends Comparable,T>{

    public int height;
    private K key;
    private T element;
    private AVLNode left, right, parent;

    /**
     * Crea un nuevo nodo AVL
     * @param element el elemento a almacenar.
     * @param key la clave del nodo.
     * @param parent el padre del nodo
     */
    public AVLNode(T element, K key, AVLNode parent){
        this.element = element;
        this.key = key;
        this.parent = parent;
        height = this.getHeight();
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

    public AVLNode getLeft() {
        return left;
    }

    public AVLNode getRight() {
        return right;
    }

    public AVLNode getParent() {
        return parent;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public void setParent(AVLNode parent) {
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

    /**
     * Calcula la altura del nodo.
     */
    public int getHeight(){
        // Si este nodo es hoja
        if(left == null && right==null){
            return 0;
        } else if(left != null && right != null){ // Dos hijos
            int max = left.getHeight() > right.getHeight() ? left.getHeight() : right.getHeight();
            return 1 + max;
        } else{ // Tiene solo un hijo
            return 1 + (hasLeft() ? left.getHeight() : right.getHeight());
        }
    }

    /**
     * Actualiza la altura del nodo.
     */
    public void setHeight(){
        this.height = this.getHeight();
    }
}