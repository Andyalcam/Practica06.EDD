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

    /**
     * Metodo que ayuda a saber si un nodo es hoja o no
     * @return boolean - true si es hoja, false si no es hoja
     */
    public boolean isLeaf(){
        return !(this.hasRight() || this.hasLeft());
    }

    /**
     * Metodo que obtiene la clave de un nodo
     * @return K - llave del nodo con la que fue creado
     */
    public K getKey() {
        return key;
    }

    /**
     * Metodo que obtiene el elemento de un nodo
     * @return T - elemento almacenado en el nodo
     */
    public T getElement() {
        return element;
    }

    /**
     * Metodo que obtiene el hijo izquierdo del nodo
     * @return AVLNode - hijo izquierdo del nodo
     */
    public AVLNode getLeft() {
        return left;
    }

    /**
     * Metodo que obtiene el hijo derecho del nodo
     * @return AVLNode - hijo derecho del nodo
     */
    public AVLNode getRight() {
        return right;
    }

    /**
     * Metodo que obtiene el padre del nodo
     * @return AVLNode - padre del nodo
     */
    public AVLNode getParent() {
        return parent;
    }

    /**
     * Metodo que asigna un nuevo hijo izquierdo del nodo
     * @param left - objeto de tipo AVLNode que se le asignara
     */
    public void setLeft(AVLNode left) {
        this.left = left;
    }

    /**
     * Metodo que asigna un nuevo hijo derecho del nodo
     * @param right - objeto de tipo AVLNode que se le asignara
     */
    public void setRight(AVLNode right) {
        this.right = right;
    }

    /**
     * Metodo que asigna un nuevo padre del nodo
     * @param parent - objeto de tipo AVLNode que se le asignara
     */
    public void setParent(AVLNode parent) {
        this.parent = parent;
    }

    /**
     * Metodo que asigna una nueva clave al nodo
     * @param key - objeto de tipo K con la nueva clave
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Metodo que asigna un nuevo elemento al nodo
     * @param element - objeto de tipo T con el nuevo elemento
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Metodo que ayuda a saber si un nodo tiene hijo izquierdo
     * @return boolean - true si tiene hijo izquierdo, false en caso contrario
     */
    public boolean hasLeft(){
        return this.getLeft() != null;
    }

    /**
     * Metodo que ayuda a saber si un nodo tiene hijo derecho
     * @return boolean - true si tiene hijo derecho, false en caso contrario
     */
    public boolean hasRight(){
        return this.getRight() != null;
    }

    /**
     * Metodo para imprimir en termimal un objeto de tipo nodo
     * @return String - cadena con el elemento que almacena el nodo
     */
    public String toString(){
        return (String)getElement();
    }

    /**
     * Calcula la altura del nodo.
     * @return int - entero con la altura calculada de un nodo
     */
    public int getHeight(){
        // Si este nodo es hoja
        if(left == null && right == null){
            return 0;
        }else if(left != null && right!=null){ // Dos hijos
            int max = left.getHeight() > right.getHeight() ? left.getHeight() : right.getHeight();
            return 1 + max;
        }else{ // Tiene solo un hijo
            return 1 + (left != null ? left.getHeight() : right.getHeight());
        }
    }

    /**
     * Actualiza la altura del nodo.
     */
    public void setHeight(){
        this.height = this.getHeight();
    }

    /**
     * Metodo que regresa la altura que tiene un nodo ya asignada sin volver a calcularla
     * @return int - entero con la altura de un nodo
     */
    public int getAltura(){
        return height;
    }

}