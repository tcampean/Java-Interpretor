package Model.Containers;


import java.util.HashMap;


public class SymTable<Key,Value> implements IDict<Key,Value> {
    HashMap<Key, Value> dict;

    public SymTable() {
        dict = new HashMap<Key, Value>();
    }

    @Override
    public boolean IsDefined(Key key) {
        return dict.containsKey(key);
    }

    @Override
    public Value Find(Key key) {
        return dict.get(key);
    }

    @Override
    public void Update(Key key, Value value) {
        dict.put(key, value);
    }

    @Override
    public String toString() {
        String result = "";
        for (HashMap.Entry<Key, Value> entry : dict.entrySet()) {
            Key key = entry.getKey();
            Value value = entry.getValue();
            result += key.toString() + "=" + value.toString() + "\n";
        }
        return result;
    }

    @Override
    public void Remove(Key key) {
        dict.remove(key);
    }

    @Override
    public HashMap<Key, Value> getContent()
    {
        return dict;
    }

}


