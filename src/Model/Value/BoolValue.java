package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value {
    boolean value;
    public BoolValue()
    {
        value = true;
    }
    public BoolValue(boolean v)
    {
        value = v;
    }
    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object another)
    {
        if(another instanceof BoolValue)
        {
            if(value == ((BoolValue) another).getValue())
                return true;
            else
                return false;
        }
        else return false;
    }
    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString()
    {
        if(value)
            return "True";
        return "False";
    }
}
