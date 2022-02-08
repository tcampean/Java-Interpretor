package Mechanism.CyclicBarrier;

import java.util.Collection;

public interface IBarrier<K, V>
{
    void add(K key, V value);

    V get(K key);

    void update(K key, V value);

    boolean contains(K key);

    Iterable<K> getAll();
}