package lru_cache.models;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private final int capacity;
    private final Map<K, LRUNode<K, V>> map;
    private final LRUDoublyLinkedList<K, V> list;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.list = new LRUDoublyLinkedList<>();
    }

    public synchronized V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        LRUNode<K, V> node = map.get(key);
        list.moveToFront(node);
        return node.value;
    }

    public synchronized void put(K key, V value) {
        if (map.containsKey(key)) {
            LRUNode<K, V> node = map.get(key);
            node.value = value;
            list.moveToFront(node);
        } else {
            if (map.size() >= capacity) {
                LRUNode<K, V> lastNode = list.removeLast();
                if (lastNode != null) {
                    map.remove(lastNode.key);
                }
            }
            LRUNode<K, V> newNode = new LRUNode<>(key, value);
            list.addFirst(newNode);
            map.put(key, newNode);
        }
    }

}
