package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type{
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof IntType)
            return true;
        return false;
    }

    @Override
    public String toString()
    {
        return "integer";
    }

    @Override
    public Value defaultValue()
    {
       return new IntValue(0);
    }



}
