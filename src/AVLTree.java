import java.util.Scanner;
import java.lang.NullPointerException;
import java.util.InputMismatchException;
public class AVLTree <K extends Comparable,T> implements TDABinarySearchTree<K,T>{

    private AVLNode<K, T> root;

    /**
     * Metodo para rotar a la izquierda
     * @param node - nodo raiz del subarbol que se va a rotar
     * @return AVLNode - Nodo raiz del subarbol despues de la rotacion
     */
    public AVLNode rotateLeft(AVLNode node) {
        if (node == null) {
            return null;
        }
        AVLNode aux = node.getLeft();
        node.setLeft(aux.getRight());  
        aux.setRight(node); 
        aux.setHeight();
        node.setHeight();
        return aux;      
    }

    /**
     * Metodo para rotar a la izquierda
     * @param node nodo raiz del subarbol que se va a rotar
     * @return AVLNode - nodo raiz del subarbol despues de la rotacion
     */
    public  AVLNode rotateRight(AVLNode node) {
        if (node == null) {
            return null;
        }
        AVLNode aux = node.getRight();
        node.setRight(aux.getLeft());
        aux.setLeft(node);
        aux.setHeight();
        node.setHeight();
        return aux;
    }

    public AVLNode doubleRotateLeft(AVLNode node){
        if (node == null) {
            return null;
        }
        AVLNode aux;
        node.setLeft(rotateRight(node.getLeft()));
        aux = rotateLeft(node);
        return aux;
    }

    public AVLNode doubleRotateRight(AVLNode node){
        if (node == null) {
            return null;
        }
        AVLNode aux;
        node.setRight(rotateLeft(node.getRight()));
        aux = rotateRight(node);
        return aux;
    }

    /**
     * Recupera el objeto con clave k.
     *
     * @param k la clave a buscar.
     * @return el elemento con clave k o null si no existe.
     */
    @Override
    public T retrieve(K k) {
        AVLNode node = retrieve(root,k);
        if(node == null) //Si no existe el elemento.
            return null;
        return (T) node.getElement();
    }

    /**
     * Método auxiliar para retrieve y obtener un nodo con tener la clave
     *
     * @param node nodo por el que empezaremos
     * @param key llave del nodo que buscamos
     * @return El nodo con la clave que buscamos
     */
    private AVLNode retrieve(AVLNode node, K key){
        //Si no existe el nodo
        if(node == null)
            return null;

        // Si encontramos el elemento
        if(node.getKey().compareTo(key) == 0)
            return node;

        // Comparamos los elementos
        if(key.compareTo(node.getKey()) < 0){ // Verificamos en la izquierda
            return retrieve(node.getLeft(), key);
        } else { // Verificar en la derecha
            return retrieve(node.getRight(), key);
        }
    }

    /**
     * Inserta un nuevo elemento al árbol.
     *
     * @param e el elemento a ingresar.
     * @param k la clave del elemento a ingresar.
     */
    @Override
    public void insert(T e, K k) {
        if(isEmpty()){ //Si el árbol es vacío.
            root = new AVLNode<>(e, k, null);
            return;
        }
        AVLNode v = insert(e, k, root);
        //System.out.println(v);
        rebalance(v);
    }

    /**
     * Método auxiliar de insert para saber en que lugar irá el nodo que insertaremos.
     * @param e - elemento que ingresaremos.
     * @param key - llave del nodo.
     * @param node - nodo con el que compararemos.
     */

    private AVLNode insert(T e, K key, AVLNode node){
        if(key.compareTo(node.getKey())<0){ // Verificamos sobre el izquierdo
            if(!node.hasLeft()){ // Insertamos en esa posición
                node.setLeft(new AVLNode(e, key, node));
                return node.getLeft();
            } else { // Recursión sobre el izquierdo
                return insert(e, key, node.getLeft());
            }
        } else{ // Verificamos sobre la derecha
            if(!node.hasRight()){ // Insertamos en esa posición
                node.setRight(new AVLNode(e, key, node));
                return node.getRight();
            } else { // Recursión sobre el derecho
                return insert(e, key, node.getRight());
            }
        }
    }

    private void swap(AVLNode v, AVLNode w){
        T element = (T) v.getElement();
        K key = (K) v.getKey();
        v.setElement(w.getElement());
        v.setKey(w.getKey());
        w.setElement(element);
        w.setKey(key);
    }

    /**
     * Elimina el nodo con clave k del árbol.
     *
     * @param k la clave perteneciente al nodo a eliminar.
     * @return el elemento almacenado en el nodo a eliminar.
     * null si el nodo con clave k no existe.
     */
    @Override
    public T delete(K k){
        AVLNode v = retrieve(root, k);

        // El elemento que queremos eliminar no está en el árbol
        if(v == null){
            return null;
        }

        T deleted = (T) v.getElement();

        // Eliminar con auxiliar
        AVLNode w = delete(v);

        //rebalance(w);

        return deleted;
    }

    /**
     * Método auxiliar para delete.
     * @param node nodo que eliminaremos.
     * @return elemento del nodo que se borró.
     */
    private AVLNode delete(AVLNode node){
        if(node.getLeft()!=null && node.getRight()!=null){ // Tiene dos hijos
            AVLNode min = findMin(node.getRight());
            swap(min, node);
            return delete(min);
        } else if(node.isLeaf()){ // No tiene hijos
            boolean isLeft = node.getParent().getLeft() == node;
            if(isLeft){
                node.getParent().setLeft(null);
            }else{
                node.getParent().setRight(null);
            }
            return node.getParent();
        }else{ // Sólo tiene un hijo
            if(node.hasLeft()){
                swap(node, node.getLeft());
                return delete(node.getLeft());
            }else{
                swap(node, node.getRight());
                return delete(node.getRight());
            }
        }
    }

    public void rebalance(AVLNode node){
        updateHeights();
        AVLNode auxParent = node.getParent();
        if (auxParent.getLeft().getHeight() - auxParent.getRight().getHeight() == 2) {
            if (node.getKey().compareTo(auxParent.getLeft().getKey()) < 0) {
                System.out.println("Caso 1 rotacion simple izq");
                auxParent = rotateLeft(auxParent);
            }else {
                System.out.println("Caso 2 rotacion doble izq");
                auxParent = doubleRotateLeft(auxParent);
            }
        }
        if (auxParent.getRight().getHeight() - auxParent.getLeft().getHeight() == 2) {
            if (node.getKey().compareTo(auxParent.getRight().getKey()) > 0) {
                System.out.println("Caso 3 rotacion simple der");
                auxParent = rotateRight(auxParent);
            }else {
                System.out.println("Caso 4 rotacion doble der");
                auxParent = doubleRotateRight(auxParent);
            }
        }
        auxParent.setHeight();
        /*boolean isLeft = node.getParent().getLeft() == node;
        if (!isLeft) {
            if(node.getHeight() == (node.getParent().getHeight() - 1)){
                System.out.println("caso1");
                rotateLeft(node.getParent().getParent());
                updateHeights();
            }else if (node.getHeight() == (node.getParent().getHeight() - 2)) {
                System.out.println("caso2");
                doubleRotateLeft(node.getParent());
                updateHeights();
            }
        }else{
            if(node.getHeight() == (node.getParent().getHeight() - 1)){
                System.out.println("caso3");
                rotateRight(node.getParent().getParent());
                updateHeights();
            }else if (node.getHeight() == (node.getParent().getHeight() - 2)) {
                System.out.println("caso4");
                doubleRotateRight(node.getParent());
                updateHeights();
            }
        }*/
    }

    public void updateHeights(){
        root.setHeight();
    }

    /**
     * Método que encuentra el elemento mínimo del árbol.
     * @return El elemento con la llave de peso mínimo.
     */
    @Override
    public T findMin(){
       if(this.isEmpty())
           return null;
       else
           return (T) findMin(root).getElement();
    }

    /**
     * Método auxiliar de findMin para saber el nodo del elemento mínimo.
     * @param node - Nodo base del que encontraremos el mínimo.
     * @return Nodo del elemento mínimo.
     */
    private AVLNode findMin(AVLNode node){
        if(node == null)
            return null;
        if(node.hasLeft())
            return findMin(node.getLeft());
        else
            return node;
    }

    /**
     * Método que encuentra la clave k con valor o peso máximo del árbol.
     *
     * @return el elemento con llave de peso máximo
     */
    @Override
    public T findMax(){
        if(this.isEmpty())
            return null;
        else
            return (T) findMax(root).getElement();
    }


    /**
     * Método auxiliar de findMax para encontrar la clave con mayor peso.
     * @param node - nodo del que tomaremos como base para encontrar el máximo.
     * @return nodo del elemento máximo.
     */
    private AVLNode findMax(AVLNode node){
        if(node == null)
            return null;
        if(node.hasRight())
            return findMax(node.getRight());
        else
            return node;
    }

    /**
     * Recorre el árbol en preorden.
     */
    @Override
    public void preorden() {
        preorden(root);
    }

    /**
     * Método auxiliar de preorden para hacer el recorrido del árbol.
     * @param node - nodo del que empezaremos el recorrido.
     */
    public void preorden(AVLNode node){
         if(node == null)
            return;
        
        System.out.print(node.getElement() + " ");   
        preorden(node.getLeft());  
        preorden(node.getRight());
    }

    /**
     * Recorre el árbol en inorden.
     */
    @Override
    public void inorden() {
        inorden(root);
    }

    /**
     * Método auxiliar de inorden para recorrer el árbol.
     * @param node - nodo del que empezaremos.
     */
    public void inorden(AVLNode node){
        if(node == null)
            return;
        
        inorden(node.getLeft());
        System.out.print(node.getElement() + " ");
        inorden(node.getRight());
    }


    /**
     * Recorre el árbol en postorden.
     */
    @Override
    public void postorden() {
        postorden(root);
    }

    /**
     * Método auxiliar de postorden para hacer recorrido del árbol.
     * @param node - nodo del que empezaremos.
     */
     public void postorden(AVLNode node){
        if( node == null )
            return;
        postorden(node.getLeft());
        postorden(node.getRight());
        System.out.print(node.getElement() + " ");
    }

    /**
     * Verifica si el árbol es vacío.
     *
     * @return true si el árbol es vacío, false en otro caso.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Metodo que deja el árbol vacio
     */
    public void clear(){
        root = null;
    }

    public int treeHeight(){
        return treeHeight(root);
    }

    public int treeHeight(AVLNode node){
        return node.getHeight();
    }

    public static void main(String[] args) {

        // Colores de letra
        String red = "\033[31m";
        String green = "\033[32m";
        String yellow = "\033[33m";
        String purple = "\033[35m";
        String cyan = "\033[36m";
        // Reset
        String reset = "\u001B[0m";

        AVLTree<Integer, String> binarySearchTree = new AVLTree<Integer, String>();
        boolean excep = true, repe;
        Scanner in = new Scanner(System.in);
        Scanner on = new Scanner(System.in);
        int opc, b;
        int clave = 0;
        String tupla = "", a;

        System.out.println("*** BIENVENIDO ***");

        while (excep) {
            try {
                System.out.println("\n\t\t*** Menu ***");
                System.out.println("--------------------------------------------");
                System.out.println("1. Insertar");
                System.out.println("2. Borrar");
                System.out.println("3. Mostrar Preorden");
                System.out.println("4. Mostrar Inorden");
                System.out.println("5. Mostrar Postorden");
                System.out.println("6. Verificar si el árbol es vacío");
                System.out.println("7. Obtener elemento");
                System.out.println("8. Encontrar Máximo");
                System.out.println("9. Encontrar Mínimo");
                System.out.println("10. Mostrar altura del arbol");
                System.out.println("11. Borrar el arbol");
                System.out.println("12. Salir");
                System.out.println("--------------------------------------------");
                System.out.println("Ingresa una opcion del menu: ");
                opc = in.nextInt();

                switch (opc) {
                    case 1:
                        System.out.println("Ingresa el elemento que quieres insertar y su clave. Ej: 2,3");
                        repe = true;
                        while (repe) {
                            //try {
                                tupla = on.nextLine().trim();
                                a = (tupla.split(",")[0]).trim();
                                b = Integer.parseInt(tupla.split(",")[1]);
                                binarySearchTree.insert(a, b);
                                repe = false;
                            //} catch (Exception e) {
                              //  System.out.println(yellow +e+ "\t Intentalo de nuevo. Sigue el ejemplo :)" + reset);
                            //1}
                        }
                        break;
                    case 2:
                        System.out.println("Ingresa la clave del elemento que deseas borrar");
                        repe = true;
                        while (repe) {
                            try {
                                clave = in.nextInt();
                                if (!binarySearchTree.isEmpty()) {
                                    System.out.println(green + "El elemento que se borro es: " + binarySearchTree.delete(clave) + reset);
                                } else {
                                    System.out.println(yellow + "\tEl árbol es vacío" + reset);
                                }
                                repe = false;
                            } catch (InputMismatchException e) {
                                System.out.println(yellow + "\t Debes ingresar un número" + reset);
                                in.next();
                            } catch (NullPointerException e) {
                                System.out.println(red + "No hay ningún elemento contenido en el árbol con clave " + clave + reset);
                                repe = false;
                            }
                        }
                        break;
                    case 3:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.print(green + "El preorden es: ");
                            binarySearchTree.preorden();
                            System.out.println(reset);
                        } else {
                            System.out.println(yellow + "\tEl árbol es vacío" + reset);
                        }
                        break;
                    case 4:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.print(green + "El inorden es: ");
                            binarySearchTree.inorden();
                            System.out.println(reset);
                        } else {
                            System.out.println(yellow + "\tEl árbol es vacío" + reset);
                        }
                        break;
                    case 5:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.print(green + "El postorden es: ");
                            binarySearchTree.postorden();
                            System.out.println(reset);
                        } else {
                            System.out.println(yellow + "\tEl árbol es vacío" + reset);
                        }
                        break;
                    case 6:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.println(green + "\tEl árbol no es vacío" + reset);
                        } else {
                            System.out.println(yellow + "\tEl árbol es vacío" + reset);
                        }
                        break;
                    case 7:
                        System.out.println("Ingresa la clave del elemento que deseas obtener");
                        repe = true;
                        while (repe) {
                            try {
                                clave = in.nextInt();
                                if (!binarySearchTree.isEmpty()) {
                                    if (binarySearchTree.retrieve(clave) != null) {
                                        System.out.println(green + "El elemento con clave " + clave + " es: " + binarySearchTree.retrieve(clave) + reset);
                                    } else {
                                        System.out.println(red + "No hay ningún elemento contenido en el árbol con clave " + clave + reset);
                                    }
                                } else {
                                    System.out.println(yellow + "\tEl árbol es vacío" + reset);
                                }
                                repe = false;
                            } catch (Exception e) {
                                System.out.println(yellow + "\t Debes ingresar un número" + reset + "\n");
                                in.next();
                            }
                        }
                        break;
                    case 8:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.println(green + "El elemento máximo del árbol es: " + binarySearchTree.findMax() + reset);
                        } else {
                            System.out.println(yellow + "\tEl árbol es vacío" + reset);
                        }
                        break;
                    case 9:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.println(green + "El elemento mínimo del árbol es: " + binarySearchTree.findMin() + reset);
                        } else {
                            System.out.println(yellow + "\tEl árbol es vacío" + reset);
                        }
                        break;
                    case 10:
                        System.out.println(binarySearchTree.treeHeight());
                        break;
                    case 11:
                        binarySearchTree.clear();
                        break;
                    case 12:
                        System.out.println(purple + "\tHasta luego :)" + reset + "\n");
                        excep = false;
                        break;
                    default:
                        System.out.println(yellow + "\tElige una opcion de menu plis :c" + reset);
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println(yellow +e+ "\tDebes ingresar un número\tIntentalo de nuevo" + reset);
                in.next();
                excep = true;
            }

        }
    }
}
