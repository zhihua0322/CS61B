import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INIT_CAPACITY = 16;
    private static final double INIT_LOADFACTOR = .75;
    private int n;                          // number of key-value pairs
    private int size;                       // hash table size
    private double loadFactor;
    private LinkedList<Entry<K, V>>[] bins;


    public MyHashMap() {
        this(INIT_CAPACITY, INIT_LOADFACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, INIT_LOADFACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        size = initialSize;
        this.loadFactor = loadFactor;
        bins = (LinkedList<Entry<K, V>>[]) new LinkedList[size];
    }

    private class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue (V x) {
            V old = value;
            this.value = x;
            return old;
        }
        @Override
        public String toString() {
            return "<" + key + "," + value + ">";
        }
    }

    private int hash(K key, int size) {
        return (key.hashCode() & 0x7fffffff) % size;
    }


    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        n = 0;
        bins = (LinkedList<Entry<K, V>>[]) new LinkedList[size];
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("argument to get() is null");
        }
        int i = hash(key, size);
        Entry<K, V> e = find(key, bins[i]);
        return (e == null) ? null : e.value;
    }

    /* The Entry in the list BIN whose key is KEY, or null if none. */
    private Entry<K, V> find(K key, LinkedList<Entry<K, V>> bin) {
        if (bin == null) {
            return null;
        }
        for (Entry<K, V> e : bin) {
            if (key.equals(e.key)) {
                return e;
            }
        }
        return null;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();

        for (int i = 0; i < size; i++) {
            if (bins[i] != null) {
                LinkedList<Entry<K, V>> bin = bins[i];
                for (Entry<K, V> entry : bin) {
                    set.add(entry);
                }
            }
        }
        return set;
    }

    private void resize() {
        int newsize = size * 2;
        MyHashMap<K, V> newMap =  new MyHashMap<>(newsize, loadFactor);
        for (Entry<K, V> e : entrySet()) {
            newMap.put(e.key, e.value);
        }
        this.bins = newMap.bins;
        this.n = newMap.n;
        this.size = newMap.size;
    }

    private LinkedList<Entry<K, V>> getEntryList(K key) {
        int i = hash(key, size);
        LinkedList<Entry<K, V>> entryList = bins[i];
        if (entryList == null) {
            entryList = new LinkedList<>();
            bins[i] = entryList;
        }
        return entryList;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return n;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("argument to put() is null");
        }
        if(get(key) == null) {
            int i = hash(key, size);
            LinkedList<Entry<K, V>> bin = getEntryList(key);
            for (Entry<K, V> entry : bin) {
                if (key.equals(entry.key)) {
                    entry.value = value;
                }
            }
            Entry<K, V> e = new Entry<>(key, value);
            bin.add(e);
            n += 1;
            bins[i] = bin;
            if ((double) n / size > loadFactor) {
                resize();
            }
        } else {
            int i = hash(key, size);
            Entry<K, V> e = find(key, bins[i]);
            e.setValue((V)value);
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (bins[i] != null) {
                LinkedList<Entry<K, V>> bin = bins[i];
                for (Entry<K, V> entry : bin) {
                    keys.add(entry.key);
                }
            }
        }
        return keys.size() == 0 ? null : keys;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("argument to remove() is null");
        }
        int i = hash(key, size);
        Entry<K, V> e = find(key, bins[i]);
        V oldValue = e.value;
        e.value = null;
        return oldValue;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new NullPointerException("argument to remove() is null");
        }
        int i = hash(key, size);
        Entry<K, V> e = find(key, bins[i]);
        if (e.value.equals(value)) {
            V oldValue = e.value;
            LinkedList<Entry<K, V>> bin = bins[i];
            bin.remove(e);
            n -= 1;
            bins[i] = bin;
            return oldValue;
        }
        return null;
    }

    public Iterator iterator() {
        Set keys = keySet();
        return keys.iterator();
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < bins.length; i += 1) {
            String contents = bins[i] == null ? "null" : ((LinkedList) bins[i]).toString();
            output += "[" + i + "] = " + contents + "\n";
        }
        return output;
    }
}
