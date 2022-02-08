package Model.Containers;

import java.util.HashMap;
import java.util.Map;

public interface IHeap<Key,Value>{
    void clear();
    boolean containsKey(Key key);
    public int generateNext();
    boolean containsValue(Value value);
    Value getValue(Key key);
    boolean isEmpty();
    Value put(Integer key,Value Value);
    Value remove(Key key);
    int size();
    Value lookUp(Key key);
    void update(Key key, Value value);
    HashMap<Integer,Value> getContent();
    void setContent(Map<Integer,Value> map);
}
