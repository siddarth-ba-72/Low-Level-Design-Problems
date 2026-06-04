package lru_cache.models;

public class LRUNode<K, V> {

    K key;
    V value;
    LRUNode<K, V> prev;
    LRUNode<K, V> next;

    public LRUNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
