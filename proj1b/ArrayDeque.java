public class ArrayDeque<T> implements Deque<T>{
    private T[] items;
    private int size;
    private int nextFirst, nextLast;
    private static final int R_FACTOR = 2;
    private static final double MIN_USAGE_FACTOR = 2;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }
    public ArrayDeque(ArrayDeque other) {
        for (int i = 0; i < other.size; i++) {
            items[i] = (T) other.items[i];
        }
        size = other.size;
        nextLast = other.nextLast;
        nextFirst = other.nextFirst;
    }
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int current = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newArray[i] = items[current];
            current = plusOne(current);
        }
        items = newArray;
        nextFirst = items.length - 1;
        nextLast = size;
    }
    private int minusOne(int index) {
        if (index - 1 < 0) {
            return items.length - 1;
        }
        return index - 1;
    }
    private int plusOne(int index) {
        if (index + 1 > items.length - 1) {
            return 0;
        }
        return index + 1;
    }
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(R_FACTOR * size);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = minusOne(nextFirst);
    }
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(R_FACTOR * size);
        }
        items[nextLast] = item;
        size++;
        nextFirst = plusOne(nextFirst);
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        int i = plusOne(nextFirst);
        while (i != nextLast) {
            System.out.print(items[i] + " ");
            i = plusOne(i);
        }
    }
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int currentFirst = plusOne(nextFirst);
        T removed = items[currentFirst];
        items[currentFirst] = null;
        nextFirst = currentFirst;
        size--;
        if (items.length >= 16 && size < (int) items.length * MIN_USAGE_FACTOR) {
            resize(items.length / R_FACTOR);
        }
        return removed;
    }
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int currentLast = minusOne(nextLast);
        T removed = items[currentLast];
        items[currentLast] = null;
        nextFirst = currentLast;
        size--;
        if (items.length >= 16 && size < (int) items.length * MIN_USAGE_FACTOR) {
            resize(items.length / R_FACTOR);
        }
        return removed;
    }
    @Override
    public T get(int index) {
        if (size == 0 || index > size) {
            return null;
        }
        int current = plusOne(nextFirst);
        for (int i = 0; i < index; i++) {
            current = plusOne(current);
        }
        return items[current];
    }
}

