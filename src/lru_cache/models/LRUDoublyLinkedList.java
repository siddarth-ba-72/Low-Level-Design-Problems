package lru_cache.models;

public class LRUDoublyLinkedList<K, V> {

    private final LRUNode<K, V> head;
    private final LRUNode<K, V> tail;

    public LRUDoublyLinkedList() {
        head = new LRUNode<>(null, null);
        tail = new LRUNode<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public void addFirst(LRUNode<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public void remove(LRUNode<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void moveToFront(LRUNode<K, V> node) {
        remove(node);
        addFirst(node);
    }

    public LRUNode<K, V> removeLast() {
        if (tail.prev == head) {
            return null; // List is empty
        }
        LRUNode<K, V> lastNode = tail.prev;
        remove(lastNode);
        return lastNode;
    }

}
