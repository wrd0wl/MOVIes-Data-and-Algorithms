
public interface IDictionary <K extends Comparable<K>, V> {
    void insert (K key, V value);
    V search (K key);
    void delete(K key);
}
