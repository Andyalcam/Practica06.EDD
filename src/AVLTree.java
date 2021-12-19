import java.util.Scanner;
import java.lang.NullPointerException;
import java.util.InputMismatchException;
public class AVLTree <K extends Comparable,T> implements TDABinarySearchTree<K,T>{

    private BinaryNode<K, T> root;

    /**
     * Metodo auxiliar para conocer la altura de un nodo
     * @param node nodo del que se desea conocer la altura
     * @return int - altura del nodo
     */
    private int height (BinaryNode node) {
        return (node == null) ? -1 : //node.height();
    }

    /**
     * Metodo para rotar a la izquierda
     * @param node - nodo raiz del subarbol que se va a rotar
     * @return BinaryNode - Nodo raiz del subarbol despues de la rotacion
     */
    private BinaryNode rotateLeft(BinaryNode node) {
        BinaryNode aux = node.getLeft();
        node.getLeft() = aux.getRight();
        aux.getRight() = node;
        node.setHeight() = max(height(node.getLeft()), height(node.getRight())) + 1;
        aux.setHeight() = max(height(aux.getLeft()), node.getHeight()) + 1;
        return aux;
    }

    /**
     * Metodo para rotar a la izquierda
     * @param node nodo raiz del subarbol que se va a rotar
     * @return BinaryNode - nodo raiz del subarbol despues de la rotacion
     */
    private BinaryNode rotateRight(BinaryNode node) {
        BinaryNode aux = node.getRight();
        node.getRight() = aux.getLeft();
        aux.getLeft() = node;
        node.setHeight() = max(height(node.getLeft()), height(node.getRight())) + 1;
        aux.setHeight() = max(altura(aux.getRight()), node.getHeight()) + 1;
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
        BinaryNode node = retrieve(root,k);
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
    private BinaryNode retrieve(BinaryNode node, K key){
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
            root = new BinaryNode<>(e, k, null);
            return;
        }
        insert(e, k, root);
    }

    /**
     * Método auxiliar de insert para saber en que lugar irá el nodo que insertaremos.
     * @param e - elemento que ingresaremos.
     * @param key - llave del nodo.
     * @param node - nodo con el que compararemos.
     */

    private void insert(T e, K key, BinaryNode node){
        if(node.getKey().compareTo(key) >= 0){//Si la clave del nodo a insertar es menor o igual al nodo existente
            if(node.hasLeft()){
                insert(e, key, node.getLeft());
            }else{
                node.setLeft(new BinaryNode<>(e, key, node));
            }
        }else{//Si la clave del nodo a insertar es mayor al nodo existente.
            if(node.hasRight()){
                insert(e, key, node.getRight());
            }else{
                node.setRight(new BinaryNode<>(e, key, node));
            }
        }
    }

    /**
     * Elimina el nodo con clave k del árbol.
     *
     * @param k la clave perteneciente al nodo a eliminar.
     * @return el elemento almacenado en el nodo a eliminar.
     * null si el nodo con clave k no existe.
     */
    @Override
    public T delete(K k) {
        if(this.isEmpty()) //Si el árbol es vacío regresa null
            return null;
        else
            return delete(retrieve(root,k));
    }

    /**
     * Método auxiliar para delete.
     * @param node nodo que eliminaremos.
     * @return elemento del nodo que se borró.
     */

    private T delete(BinaryNode node){
        if(node == null){ //Si no existe el elemento regresará null.
            throw new NullPointerException();
        }

        BinaryNode aux;

        T element = (T) node.getElement();

        if(node.hasLeft()){//Si el elemento a borrar tiene hijo izquierdo.
            aux = findMaxAux(node.getLeft());
            if(aux.isLeaf()){//Verificaremos si el elemento más grande es una hoja.
                if(aux.getParent().equals(node))//Si el nodo máximo es hijo del elemento a borrar.
                    node.setLeft(null);
                else//Por si hay más nodos entre el nodo a borrar y el máximo.
                    aux.getParent().setRight(null);
            }else{//Si el elemento máximo no es una hoja.
                aux.getLeft().setParent(aux.getParent());
                aux.getParent().setRight(aux.getLeft());
            }
        }else{//Verificaremos si tiene hijo derecho o no es una hoja.
            if(node.hasRight()){//Si hay hijo derecho.
                aux = findMaxAux(node.getRight());
                if(aux.isLeaf()){//Verificaremos si el elemento más grande es una hoja.
                    if(aux.getParent().equals(node)) //Si el nodo máximo es hijo del elemento a borrar.
                        node.setRight(null);
                    else //Por si hay más nodos entre el nodo a borrar y el máximo.
                        aux.getParent().setRight(null);
                }else{//Si el elemento máximo no es una hoja.
                    aux.getLeft().setParent(aux.getParent());
                    aux.getParent().setRight(aux.getLeft());
                }
            }else{//Si no tiene hijos
                if(node.getKey().compareTo(node.getParent().getKey()) < 0) //Si es hijo izquierdo.
                    node.getParent().setLeft(null);
                else //Si es hijo derecho.
                    node.getParent().setRight(null);
                return element;
            }
        }
        node.setElement(aux.getElement());
        node.setKey(aux.getKey());
        return element;
    }


    /**
     * Encuentra la clave k con valor o peso mínimo del árbol.
     * @param node - nodo base del que encontraremos el mínimo.
     * @return el elemento con llave de peso mínimo.
     */
    @Override
    public T findMin(BinaryNode node) {
        if(this.isEmpty())
            return null;
        else{
            return (T) findMinAux(node).getElement();
        }
    }

    /**
     * Método que encuentra el elemento mínimo del árbol.
     * @return El elemento con la llave de peso mínimo.
     */

    public T findMin(){
       if(this.isEmpty())
           return null;
       else
           return (T) findMinAux(root).getElement();
    }

    /**
     * Método auxiliar de findMin para saber el nodo del elemento mínimo.
     * @param node - Nodo base del que encontraremos el mínimo.
     * @return Nodo del elemento mínimo.
     */

    private BinaryNode findMinAux(BinaryNode node){
        if(node.hasLeft())
            return findMinAux(node.getLeft());
        else
            return node;
    }


    /**
     * Encuentra la clave k con valor o peso máximo del árbol.
     *
     * @return el elemento con llave de peso máximo.
     */
    @Override
    public T findMax(BinaryNode node) {
        if(this.isEmpty())
            return null;
        else
            return (T) findMaxAux(node).getElement();
    }

    /**
     * Método que encuentra la clave k con valor o peso máximo del árbol.
     *
     * @return el elemento con llave de peso máximo
     */
    public T findMax(){
        if(this.isEmpty())
            return null;
        else
            return (T) findMaxAux(root).getElement();
    }


    /**
     * Método auxiliar de findMax para encontrar la clave con mayor peso.
     * @param node - nodo del que tomaremos como base para encontrar el máximo.
     * @return nodo del elemento máximo.
     */
    private BinaryNode findMaxAux(BinaryNode node){
        if(node.hasRight())
            return findMaxAux(node.getRight());
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
    public void preorden(BinaryNode node){
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
    public void inorden(BinaryNode node){
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
     public void postorden(BinaryNode node){
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
                System.out.println("10. Salir");
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
                        System.out.println(purple + "\tHasta luego :)" + reset + "\n");
                        excep = false;
                        break;
                    default:
                        System.out.println(yellow + "\tElige una opcion de menu plis :c" + reset);
                        break;
                }

            } catch (Exception e) {
                System.out.println(yellow + "\tDebes ingresar un número\tIntentalo de nuevo" + reset);
                in.next();
                excep = true;
            }

        }
    }
}
