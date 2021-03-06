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
        if(node == root) {
            AVLNode aux = node.getRight();
            node.setRight(aux.getLeft());
            aux.setLeft(node);
            aux.setParent(node.getParent());
            root = aux;
            node.setParent(aux);
            aux.setHeight();
            node.setHeight();
            return aux;
        }else{
            AVLNode aux = node.getRight();
            node.setRight(aux.getLeft());
            aux.setLeft(node);
            aux.setParent(node.getParent());
            node.getParent().setRight(aux);
            node.setParent(aux);
            aux.setHeight();
            node.setHeight();
            return aux;
        }
    }

    /**
     * Metodo para rotar a la izquierda
     * @param node nodo raiz del subarbol que se va a rotar
     * @return AVLNode - nodo raiz del subarbol despues de la rotacion
     */
    public  AVLNode rotateRight(AVLNode node) {
        if (node == root) {
            AVLNode aux = node.getLeft();
            node.setLeft(aux.getRight());
            aux.setRight(node);
            aux.setParent(node.getParent());
            root = aux;
            node.setParent(aux);
            aux.setHeight();
            node.setHeight();
            return aux;
        }else {
            AVLNode aux = node.getLeft();
            node.setLeft(aux.getRight());
            aux.setRight(node);
            aux.setParent(node.getParent());
            node.getParent().setLeft(aux);
            node.setParent(aux);
            aux.setHeight();
            node.setHeight();
            return aux;
        }
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
     * M??todo auxiliar para retrieve y obtener un nodo con tener la clave
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
     * Inserta un nuevo elemento al ??rbol.
     *
     * @param e el elemento a ingresar.
     * @param k la clave del elemento a ingresar.
     */
    @Override
    public void insert(T e, K k) {
        if(isEmpty()){ //Si el ??rbol es vac??o.
            root = new AVLNode<>(e, k, null);
            return;
        }

        AVLNode v = insert(e, k, root);
        updateHeights(v);
        rebalance(v, v.getParent());

    }

    /**
     * M??todo auxiliar de insert para saber en que lugar ir?? el nodo que insertaremos.
     * @param e - elemento que ingresaremos.
     * @param key - llave del nodo.
     * @param node - nodo con el que compararemos.
     * @return AVLNode - nodo que se acaba de insertar
     */

    private AVLNode insert(T e, K key, AVLNode node){
        if(key.compareTo(node.getKey())<0){ // Verificamos sobre el izquierdo
            if(!node.hasLeft()){ // Insertamos en esa posici??n
                node.setLeft(new AVLNode(e, key, node));
                return node.getLeft();
            } else { // Recursi??n sobre el izquierdo
                return insert(e, key, node.getLeft());
            }
        }else{ // Verificamos sobre la derecha
            if(!node.hasRight()){ // Insertamos en esa posici??n
                node.setRight(new AVLNode(e, key, node));
                return node.getRight();
            } else { // Recursi??n sobre el derecho
                return insert(e, key, node.getRight());
            }
        }
    }

    /**
     * Metodo privado para hacer un swap entre nodos de un arbol
     * @param v - primer nodo
     * @param w - segundo nodo
     */
    private void swap(AVLNode v, AVLNode w){
        T element = (T) v.getElement();
        K key = (K) v.getKey();
        v.setElement(w.getElement());
        v.setKey(w.getKey());
        w.setElement(element);
        w.setKey(key);
    }

    /**
     * Elimina el nodo con clave k del ??rbol.
     *
     * @param k la clave perteneciente al nodo a eliminar.
     * @return el elemento almacenado en el nodo a eliminar.
     * null si el nodo con clave k no existe.
     */
    @Override
    public T delete(K k){
        AVLNode v = retrieve(root, k);

        // El elemento que queremos eliminar no est?? en el ??rbol
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
     * M??todo auxiliar para delete.
     * @param node nodo que eliminaremos.
     * @return elemento del nodo que se borr??.
     */
    private AVLNode delete(AVLNode node){
        if(node.hasLeft() && node.hasRight()){ // Tiene dos hijos
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
        }else{ // S??lo tiene un hijo
            if(node.hasLeft()){
                swap(node, node.getLeft());
                return delete(node.getLeft());
            }else{
                swap(node, node.getRight());
                return delete(node.getRight());
            }
        }
    }

    /**
     * Metodo que ayuda a rebalancear un arbol AVL
     * @param v - nodo que se acaba de insertar el arbol, a partir de cual se va a rebalancear
     * @param auxRoot - padre de nodo que se inserto para hacer recursion
     */
    public void rebalance(AVLNode v, AVLNode auxRoot){
        if(auxRoot == null){
            return;
        }else{
            AVLNode auxParent = auxRoot;

            int alturaIzquierdo = auxParent.hasLeft() ? auxParent.getLeft().getAltura() : -1;
            int alturaDerecho = auxParent.hasRight() ? auxParent.getRight().getAltura() : -1;

            if(Math.abs(alturaIzquierdo-alturaDerecho) == 2){

                if(v.getKey().compareTo(root.getKey()) > 0){

                    if(v.getKey().compareTo(auxRoot.getRight().getKey()) > 0){
                        auxParent = rotateLeft(auxRoot);
                    }else{
                        auxParent = rotateLeft(auxRoot);
                        auxParent = rotateRight(auxParent);
                    }
                }else{
                    if(v.getKey().compareTo(auxRoot.getLeft().getKey()) <= 0){
                        auxParent = rotateRight(auxRoot);
                    }else{
                        auxParent = rotateRight(auxRoot);
                        auxParent = rotateLeft(auxParent);
                    }
                }
            }
            updateHeights(v);
            rebalance(v, auxRoot.getParent());
        }
    }

    /**
     * Metodo que actualiza las alturas de todos los nodos de un arbol
     * @param v - nodo a partir del cual se va a actualizar hasta la raiz
     */
    public void updateHeights(AVLNode v){
        if(v == null){
            return;
        }else{
            v.setHeight();
            updateHeights(v.getParent());
        }
    }

    /**
     * M??todo que encuentra el elemento m??nimo del ??rbol.
     * @return El elemento con la llave de peso m??nimo.
     */
    @Override
    public T findMin(){
       if(this.isEmpty())
           return null;
       else
           return (T) findMin(root).getElement();
    }

    /**
     * M??todo auxiliar de findMin para saber el nodo del elemento m??nimo.
     * @param node - Nodo base del que encontraremos el m??nimo.
     * @return Nodo del elemento m??nimo.
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
     * M??todo que encuentra la clave k con valor o peso m??ximo del ??rbol.
     *
     * @return el elemento con llave de peso m??ximo
     */
    @Override
    public T findMax(){
        if(this.isEmpty())
            return null;
        else
            return (T) findMax(root).getElement();
    }


    /**
     * M??todo auxiliar de findMax para encontrar la clave con mayor peso.
     * @param node - nodo del que tomaremos como base para encontrar el m??ximo.
     * @return nodo del elemento m??ximo.
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
     * Recorre el ??rbol en preorden.
     */
    @Override
    public void preorden() {
        preorden(root);
    }

    /**
     * M??todo auxiliar de preorden para hacer el recorrido del ??rbol.
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
     * Recorre el ??rbol en inorden.
     */
    @Override
    public void inorden() {
        inorden(root);
    }

    /**
     * M??todo auxiliar de inorden para recorrer el ??rbol.
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
     * Recorre el ??rbol en postorden.
     */
    @Override
    public void postorden() {
        postorden(root);
    }

    /**
     * M??todo auxiliar de postorden para hacer recorrido del ??rbol.
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
     * Verifica si el ??rbol es vac??o.
     *
     * @return true si el ??rbol es vac??o, false en otro caso.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Metodo que deja el ??rbol vacio
     */
    public void clear(){
        root = null;
    }

    /**
     * Metodo que regresa la altura de un arbol
     * @return int - entero con la altura
     */
    public int treeHeight(){
        return treeHeight(root);
    }

    /**
     * Metodo auxiliar para ayudar a saber la altura de un arbol
     * @param node - nodo a partir del cual se va a calcular la altura
     * @return int - entero con la altura del nodo
     */
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
                System.out.println("6. Verificar si el ??rbol es vac??o");
                System.out.println("7. Obtener elemento");
                System.out.println("8. Encontrar M??ximo");
                System.out.println("9. Encontrar M??nimo");
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
                            try {
                                tupla = on.nextLine().trim();
                                a = (tupla.split(",")[0]).trim();
                                b = Integer.parseInt(tupla.split(",")[1]);
                                binarySearchTree.insert(a, b);
                                repe = false;
                            } catch (Exception e) {
                                System.out.println(yellow + "\t Intentalo de nuevo. Sigue el ejemplo :)" + reset);
                            }
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
                                    System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
                                }
                                repe = false;
                            } catch (InputMismatchException e) {
                                System.out.println(yellow + "\t Debes ingresar un n??mero" + reset);
                                in.next();
                            } catch (NullPointerException e) {
                                System.out.println(red + "No hay ning??n elemento contenido en el ??rbol con clave " + clave + reset);
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
                            System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
                        }
                        break;
                    case 4:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.print(green + "El inorden es: ");
                            binarySearchTree.inorden();
                            System.out.println(reset);
                        } else {
                            System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
                        }
                        break;
                    case 5:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.print(green + "El postorden es: ");
                            binarySearchTree.postorden();
                            System.out.println(reset);
                        } else {
                            System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
                        }
                        break;
                    case 6:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.println(green + "\tEl ??rbol no es vac??o" + reset);
                        } else {
                            System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
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
                                        System.out.println(red + "No hay ning??n elemento contenido en el ??rbol con clave " + clave + reset);
                                    }
                                } else {
                                    System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
                                }
                                repe = false;
                            } catch (Exception e) {
                                System.out.println(yellow + "\t Debes ingresar un n??mero" + reset);
                                in.next();
                            }
                        }
                        break;
                    case 8:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.println(green + "El elemento m??ximo del ??rbol es: " + binarySearchTree.findMax() + reset);
                        } else {
                            System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
                        }
                        break;
                    case 9:
                        if (!binarySearchTree.isEmpty()) {
                            System.out.println(green + "El elemento m??nimo del ??rbol es: " + binarySearchTree.findMin() + reset);
                        } else {
                            System.out.println(yellow + "\tEl ??rbol es vac??o" + reset);
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
                System.out.println(yellow + "\tDebes ingresar un n??mero\tIntentalo de nuevo" + reset);
                in.next();
                excep = true;
            }

        }
    }
}
