package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value{
    Integer value;
    public IntValue()
    {
        value = 0;
    }
    public IntValue(int v)
    {
        value = v;
    }
    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object another)
    {
        if(another instanceof IntValue)
        {
            if(value == ((IntValue) another).getValue())
                return true;
            else
                return false;
        }
        else
            return false;
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString()
    {
        return value.toString();
    }

}
