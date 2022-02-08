package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class BoolType implements Type{
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof BoolType)
            return true;
        return false;
    }

    @Override
    public Value defaultValue()
    {
        return new BoolValue(true);
    }

    @Override
    public String toString()
    {
        return "boolean";
    }
}
