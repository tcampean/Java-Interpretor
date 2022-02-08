package Mechanism.CyclicBarrier;

import java.util.HashMap;
import java.util.Map;

public class Barrier<K,V> implements IBarrier<K,V>
{
    private Map<K,V> dict;

    public Barrier()
    {
        dict = new HashMap<>();
    }

    @Override
    public void add(K key,V value) {
        dict.put(key,value);
    }

    @Override
    public V get(K key) {
        return dict.get(key);
    }

    @Override
    public void update(K key, V value) {
        dict.put(key,value);
    }

    @Override
    public boolean contains(K key) {
        return dict.containsKey(key);
    }


    @Override
    public Iterable<K> getAll() {
        return dict.keySet();
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("\n\nBarrierTable:");
        for (Map.Entry<K,V> e : dict.entrySet())
        {
            s.append('\n');
            s.append(e.getKey());
            s.append("-->");
            s.append(e.getValue());
        }
        return s.toString();
    }
}