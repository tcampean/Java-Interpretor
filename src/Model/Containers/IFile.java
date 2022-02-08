package Model.Containers;

import java.util.Set;

public interface IFile<Key,Value>
{
    boolean IsDefined(Key key);
    Value Find(Key key);
    void Update(Key key, Value value);
    void Remove(Key key);
    Set<Key> getKeys();
}
