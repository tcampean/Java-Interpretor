package Model.Containers;

import java.util.HashMap;

public interface IDict<Key,Value> {
    boolean IsDefined(Key key);
    Value Find(Key key);
    void Update(Key key, Value value);
    void Remove(Key key);
    HashMap<Key,Value> getContent();
}
