public class LinkedListDeque<T> {

    public class Node {
        private Node prior;
        private T data;
        private Node next;

        public Node(T data0) {
            prior = null;
            data = data0;
            next = null;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prior = sentinel;
    }
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = other.sentinel;
        size = other.size;
    }
    public void addFirst(T item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node first = new Node(item);
        first.prior = sentinel;
        first.next = sentinel.next;
        sentinel.next.prior = first;
        sentinel.next = first;
        size++;
    }
    public void addLast(T item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node last = new Node(item);
        last.next = sentinel;
        last.prior = sentinel.prior;
        sentinel.prior.next = last;
        sentinel.prior = last;
        size++;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        Node one = sentinel.next;
        for (int i = 0; i < this.size; i++) {
            System.out.print(one.data + " ");
            one = one.next;
        }
    }
    public T removeFirst() {
        Node oldfirst = sentinel.next;
        T olddata = oldfirst.data;
        sentinel.next = sentinel.next.next;
        sentinel.next.prior = sentinel;
        size--;
        oldfirst = null;
        return olddata;
    }
    public T removeLast() {
        Node oldlast = sentinel.prior;
        T olddata = oldlast.data;
        sentinel.prior = oldlast.prior;
        oldlast.prior.next = sentinel;
        size--;
        oldlast = null;
        return olddata;
    }
    public T get(int index) {
        if (size == 0 || index > size) {
            return null;
        }
        Node one = sentinel.next;
        for (int i = 0; i < index; i++) {
            one = one.next;
        }
        return one.data;
    }
    public T getRecursive(int index) {
        if (size == 0 || index > size) {
            return null;
        }
        return getRecursivehelper(sentinel, index);
    }
    private T getRecursivehelper(Node one, int index) {
        while (index == 0) {
            return one.data;
        }
        return getRecursivehelper(one.next, index--);
    }
}
