/*  Westerhof Rodríguez Guillermo Alejandro
    Rueda Cabrera Pedro 
*/

package es.practica2;

import java.util.Comparator;

public class DoubleLinkedList<T> implements DoubleLinkedQueue<T> {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private int size;

    public DoubleLinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    // insertar u3n nuevo nodo antes del primer nodo existente en la lista
    @Override
    public void prepend(T value) {
        if (this.size == 0) { // no hay nodo
            LinkedNode<T> elem = new LinkedNode<T>(value, null, null);
            this.first = elem;
            this.last = elem;
            this.size = 1;
        } else {
            LinkedNode<T> elem = new LinkedNode<T>(value, null, this.first);
            this.first.setPrevious(elem);
            this.first = elem;
            this.size++;
        }

    }

    @Override
    public void append(T value) {
        if (this.size == 0) { // no hay nodo
            LinkedNode<T> elem = new LinkedNode<T>(value, null, null);
            this.first = elem;
            this.last = elem;
            this.size = 1;
        } else {
            LinkedNode<T> elem = new LinkedNode<T>(value, this.last, null);
            this.last.setNext(elem);
            this.last = elem;
            this.size++;
        }
    }

    @Override
    public void deleteFirst() {
        if (size == 0) {
            throw new DoubleLinkedQueueException("Error no se puede borrar en una lista vacia");
        }
        if (this.size == 1) {
            this.first = null;
            this.last = null;
            this.size = 0;
        } else {
            LinkedNode<T> aux = this.first.getNext();
            aux.setPrevious(null);
            this.first = aux;
            this.size--;
        }
    }

    @Override
    public void deleteLast() {
        if (size == 0) {
            throw new DoubleLinkedQueueException("Error no se puede borrar en una lista vacia");
        }
        if (size == 1) {
            this.first = null;
            this.last = null;
            size = 0;
        } else {
            this.last = last.getPrevious();
            last.setNext(null);
            size--;
        }
    }

    @Override
    public T first() {
        return first != null ? first.getItem() : null;
    }

    @Override
    public T last() {
        return last != null ? last.getItem() : null;
    }

    @Override
    public int size() {
        return size;
    }

    // ---------------------------------------------------------------------------------------
    // segunda sesion practica 2
    // ---------------------------------------------------------------------------------------

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("indice fuera de rango");

        LinkedNode<T> aux = first;
        for (int cont = 0; cont < index; cont++) {
            aux = aux.getNext();
        }
        return aux.getItem();


        /*  VERSION OPTMIZADA 

            LinkedNode<T> aux;
            int half = size / 2;
            if (index <= half) {
                aux = first;
                for (int cont = 0; cont < index; cont++) {
                    aux = aux.getNext();
                }
            }
            else {
                aux = last;
                for (int cont = size-1; cont > index; cont--) {
                    aux = aux.getPrevious();
                }
            }
         */
    }

    @Override
    public boolean contains(T value) {
        if (value == null) 
            throw new DoubleLinkedQueueException("Null no esta permitidio");
        
        boolean contains = false;
        LinkedNode<T> aux = first;

        while (aux != null && !contains) {
            contains = value.equals(aux.getItem());
            if (!contains)
                aux = aux.getNext();
        }        
        return contains;
    }

    @Override
    public void remove(T value) {
        if (size == 0) 
            throw new DoubleLinkedQueueException("Error no se puede borrar en una lista vacia");
        
        if (size == 1) {  //solo hay 1 elemento en la lista
            this.first = null;
            this.last = null;
            size = 0;
        }
        else if (first.getItem().equals(value)) {   //el elemento a borrar es el primero y hay mas de 1 elemento en la lista
            first = first.getNext();
            first.setPrevious(null);
            size--;
        }
        else if (last.getItem().equals(value)) {    //el elemento a borrar es el ultimo de la lista
            last = last.getPrevious();
            last.setNext(null);
            size--;
        }
        else {  //el elemento a borrar no es ni el primero ni el ultimo de la lista
            LinkedNode<T> aux = first.getNext(); //empezamos justo en el siguiente elemento porque el elemento no es ni first ni last
            while (aux != null && !aux.getItem().equals(value)) {
                aux = aux.getNext();
            }
            //cuando salimos del bucle si aux no es nulo, entonces aux es el elemento a borrar
            if (aux != null) {
                aux.getPrevious().setNext(aux.getNext());
                aux.getNext().setPrevious(aux.getPrevious());
                size--;
            }
            
        }

    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        if (size <= 1) return; // La lista no necesita ordenación si tiene 0 o 1 elemento.

        boolean wasSwapped;
        do {
            LinkedNode<T> p = first;
            wasSwapped = false; // Se reinicia la bandera para esta pasada.

            while (p != null && p.getNext() != null) { // Asegura que p y su siguiente existan.

                LinkedNode<T> q = p.getNext(); // q siempre comienza como el siguiente de p.
                while (q != null) { // Mientras q no haya alcanzado el final de la lista.
                    if (comparator.compare(p.getItem(), q.getItem()) > 0) {
                        // Intercambiar valores si están en el orden incorrecto.
                        T temp = p.getItem();
                        p.setItem(q.getItem());
                        q.setItem(temp);

                        wasSwapped = true; // Se realizó un intercambio.
                    }
                    q = q.getNext(); // Mover q al siguiente nodo para continuar la comparación.
                }

                p = p.getNext(); // Después de que q recorre toda la lista, mover p al siguiente nodo.
            }


        } while (wasSwapped); // Repetir mientras se sigan haciendo intercambios.
    }

}
