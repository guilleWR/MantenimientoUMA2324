package es.practica2;

public class DoubleLinkedList<T> implements DoubleLinkedQueue<T> {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private int size;

    public DoubleLinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    //insertar u3n nuevo nodo antes del primer nodo existente en la lista
        @Override
        public void prepend(T value) {
            if (this.size == 0) { //no hay nodo
                LinkedNode<T> elem = new LinkedNode<T>(value, null, null);
                this.first = elem;
                this.last = elem;
                this.size = 1;
            }
            else {
                LinkedNode<T> elem = new LinkedNode<T>(value, null, this.first);
                this.first.setPrevious(elem);
                this.first = elem;
                this.size++;
            }

        }

    @Override
    public void append(T value) {
        if (this.size == 0) { //no hay nodo
            LinkedNode<T> elem = new LinkedNode<T>(value, null, null);
            this.first = elem;
            this.last = elem;
            this.size = 1;
        }
        else {
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
        }
        else {
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
        if (size == 1){
            this.first = null;
            this.last = null;
            size = 0;
        }else {
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
}
