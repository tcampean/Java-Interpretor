package Model.Containers;

import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;

public class Heap<Key,Value> implements IHeap<Key,Value>{
    HashMap<Integer,Value> heap = new HashMap<Integer,Value>();
    int addr = 0;

    @Override
    public void clear()
    {
        heap.clear();
    }

    @Override
    public int generateNext()
    {
        addr+=1;
        return addr;
    }

    @Override
    public void setContent(Map<Integer,Value> m )
    {
        heap = (HashMap<Integer, Value>) m;
    }

    @Override
    public HashMap<Integer,Value> getContent()
    {
        return heap;
    }

    @Override
    public String toString()
    {
        return heap.toString();
    }

    @Override
    public boolean containsKey(Key key)
    {
        return heap.containsKey(key);
    }

    @Override
    public boolean containsValue(Value value)
    {
        return heap.containsValue(value);
    }

    @Override
    public Value getValue(Key key)
    {
        return heap.get(key);
    }

    @Override
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    @Override
    public Value put(Integer key, Value value)
    {
        return heap.put(key,value);
    }

    @Override
    public Value remove(Key key)
    {
        return heap.remove(key);
    }

    @Override
    public int size()
    {
        return heap.size();
    }

    @Override
    public Value lookUp(Key key)
    {
        return this.getValue(key);
    }

    @Override
    public void update(Key key, Value value)
    {
        if(key instanceof Integer)
            this.heap.replace((Integer) key,value);
    }



}
