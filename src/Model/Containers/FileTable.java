package Model.Containers;

import java.util.HashMap;
import java.util.Set;

public class FileTable<Key,Value> implements IFile<Key,Value> {

    HashMap<Key,Value> dict;
    public FileTable()
    {
        dict = new HashMap<Key,Value>();
    }

    @Override
    public boolean IsDefined(Key key)
    {
        return dict.containsKey(key);
    }

    @Override
    public Value Find(Key key)
    {
        return dict.get(key);
    }
    @Override
    public void Update(Key key, Value value)
    {
        dict.put(key,value);
    }

    @Override
    public void Remove(Key key)
    {
        dict.remove(key);
    }

    @Override
    public String toString()
    {
        String result = "";
        for(Key key : dict.keySet())
        {
            result = result + key.toString() +"\n";
        }
        return result;
    }

    @Override
    public Set<Key> getKeys()
    {
        return this.dict.keySet();
    }
}
