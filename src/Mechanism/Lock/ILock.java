package Mechanism.Lock;

import java.util.Collection;
import java.util.Map;

public interface ILock<K,V>
{
    void add(K key, V value);

    void update(K key, V value);

    boolean contains(K key);

    V get(K key);

    Iterable<K> getAll();

    Collection<V> getValues();

    Iterable<K> getKeys();

    ILock<K, V> makeCopy();

    Map<K, V> getContent();
}