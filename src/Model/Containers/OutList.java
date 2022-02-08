package Model.Containers;

import java.util.Vector;
public class OutList<Elem> implements IList<Elem>{
    Vector<Elem> list;
    public OutList()
    {
        list = new Vector<Elem>();
    }

    @Override
    public void Add(Elem e)
    {
        list.add(e);
    }
    @Override
    public Elem Get(int index)
    {
        return list.get(index);
    }

    @Override
    public String toString()
    {
        String result = "";
        for(Elem key : list)
        {
            result = result + key.toString() +"\n";
        }
        return result;
    }

    @Override
    public int size()
    {
        return this.list.size();
    }


}
