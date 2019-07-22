package bearmaps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> heap;
    private HashMap<T, Integer> items;
    private class Node {
        T item;
        double priority;

        Node(T i, double p) {
            this.item = i;
            this.priority = p;
        }

    }

    public ArrayHeapMinPQ() {
        this.heap = new ArrayList<Node>();
        this.items = new HashMap<>();
    }

    private static int parent(int i) {
        return (i - 1) / 2;
    }

    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    private static int rightChild(int i) {
        return 2 * i + 2;
    }

    @Override
    public void add(T item, double priority) {
        Node tmp = new Node(item, priority);
        if (contains(item)) {
            throw new IllegalArgumentException();
        } else {
            heap.add(tmp);
            items.put(item, size() - 1);
            swim(size() - 1);
            //assert isMinHeap();
        }
    }

    private void swim (int k) {
        while (k > 1 && greater(parent(k), k)) {
            exch(k, parent(k));
            k = parent(k);
        }
    }
    private void sink (int k) {
        while (rightChild(k) <= size()) {
            int j = leftChild(k);

            if (rightChild(k) < size() && less(rightChild(k), j))
                j = rightChild(k);

            if (!less(j, k))
                break;

            exch(k, j);
            k = j;
        }
    }
    private boolean greater (int i, int j) {
        return heap.get(i).priority < heap.get(j).priority;
    }
    private boolean less (int i, int j) {
        return heap.get(i).priority > heap.get(j).priority;
    }

    private void exch (int i, int j) {
        Node tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
        items.put(heap.get(i).item, i);
        items.put(heap.get(j).item, j);
    }
    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }
    @Override
    public T getSmallest() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(1).item;
    }
    @Override
    public T removeSmallest() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        T min = heap.get(1).item;
        exch(1, size() - 1);
        sink(1);
        heap.remove(size() - 1);// to avoid loiterig and help with garbage collection
        sink(0);
        items.remove(min);
        return min;
        //assert isMinHeap();
    }
    @Override
    public int size() {
        return items.size();
    }
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int i = items.get(item);
        double oldPriority = heap.get(i).priority;
        heap.get(i).priority = priority;
        if (oldPriority < priority) {
            sink(i);
        } else {
            swim(i);
        }
    }

}
