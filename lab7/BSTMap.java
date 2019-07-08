import java.util.Iterator;
import java.util.Set;
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;             // root of BST
    private class Node {
        private K key;           // sorted by key
        private V val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
    public BSTMap() {
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }
    private V get(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    @Override
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left  = put(x.left,  key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val   = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        printInOrder(root);
    }
    private void printInOrder(Node x) {
        if (x.left == null && x.right == null) {
            printNode(x);
        } else if (x.right == null) {
            printInOrder(x.left);
            printNode(x);
        } else if (x.left == null) {
            printNode(x.right);
            printNode(x);
        } else {
            printInOrder(x.left);
            printNode(x);
            printInOrder(x.right);
        }
    }
    private void printNode(Node x) {
        System.out.print(x.key);
        System.out.println(" " + x.val);
    }
}
