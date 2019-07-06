package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        rb = (T[]) new Object[capacity];
    }
    // return size of the buffer
    public int capacity() {
        return rb.length;
    }
    // return number of items currently in the buffer
    public int fillCount() {
        return  fillCount;
    }
    private int plusOne(int index) {
        if (index + 1 > rb.length - 1) {
            return 0;
        }
        return index + 1;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (isFull()) {
            throw new IllegalArgumentException("Ring Buffer overflow");
        } else {
            rb[last] = x;
            last = plusOne(last);
            fillCount++;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new IllegalArgumentException("Ring Buffer underflow");
        } else {
            T tmp = rb[first];
            first = plusOne(first);
            fillCount--;
            return tmp;
        }
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return rb[first];
        }
    }
    /** returns an iterator (a.k.a. seer) into ME */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int wizPos;

        public ArrayRingBufferIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < rb.length;
        }

        public T next() {
            T returnItem = rb[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    private boolean contains(T i) {
        for (T item : this) {
            if (item == i) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if (o.capacity() != this.capacity()) {
            return false;
        }
        for (T item : this) {
            if (!o.contains(item)) {
                return false;
            }
        }
        return true;
    }
    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
