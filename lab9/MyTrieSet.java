import java.util.*;

public class MyTrieSet implements TrieSet61B{
    private Node root;      // root of trie
    private int n;          // number of keys in trie
    // R-way trie node
    private static class Node {
        char info;
        boolean isKey;
        Map<Character,Node> map;
        Node(char ch, boolean ik) {
            info = ch;
            isKey = ik;
            map = new TreeMap<Character,Node>();
        }
        Node(char ch, Node p) {
            info = ch;
            isKey = false;
            map = new TreeMap<Character,Node>();
        }
    }

    public MyTrieSet() {
        root = new Node(' ', null);
        n = 0;
    }

    @Override
    public void clear() {
        root = new Node(' ', null);
        n = 0;
    }

    @Override
    public boolean contains(String key) {
        Node t = find(key);
        if (t == null) {
            return false;
        } else {
            return t.isKey;
        }
    }


    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }


    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new ArrayList<>();
        Node n = find(prefix);
        collect(prefix, keys, n);
        return keys;
    }
    private Node find(String key) {
        Node t = root;
        for (int k = 0; k < key.length(); k++) {
            char c = key.charAt(k);
            t = t.map.get(c);
            if (t == null)
                return null;
        }
        return t;
    }
    private void collect(String key, List<String> s, Node n) {
        if (n == null) {
            return;
        }
        if (n.isKey) {
            s.add(key);
        }
        for (char c : n.map.keySet()) {
            collect(key + c, s, n.map.get(c));
        }
    }
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

}
