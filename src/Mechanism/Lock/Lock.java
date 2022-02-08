package Mechanism.Lock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Lock<K,V> implements ILock<K,V>
{
    private Map<K,V> dict;
    public Map<K, V> getContent() { return dict; }
    public Lock()
    {
        dict = new HashMap<K, V>();
    }
    public void add(K key, V value)
    {
        dict.put(key, value);
    }
    public void update(K key, V value)
    {
        dict.put(key, value);
    }
    public V get(K key)
    {
        return dict.get(key);
    }
    public boolean contains(K key)
    {
        return dict.containsKey(key);
    }
    public Iterable<K> getAll()
    {
        return dict.keySet();
    }
    private Lock(Map<K,V> d){
        dict = d;
    }

    @Override
    public Collection<V> getValues()
    {
        return dict.values();
    }

    @Override
    public Iterable<K> getKeys()
    {
        return dict.keySet();
    }

    @Override
    public ILock<K, V> makeCopy()
    {
        return new Lock<K,V>(new HashMap<K,V>(dict));
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        if(dict.isEmpty())
            buff.append("LockTable: EMPTY");
        else
        {
            buff.append("\nLockTable: \n");
            for (Map.Entry<K, V> dc : dict.entrySet())
            {
                buff.append(dc.getKey());
                buff.append(" = ");
                buff.append(dc.getValue());
                buff.append('\n');
            }
        }
        return buff.toString();
    }
}