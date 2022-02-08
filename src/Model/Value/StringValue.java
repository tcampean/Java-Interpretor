package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{
    String val;
    public StringValue()
    {
        val = "";
    }

    public StringValue(String s)
    {
        val = s;
    }

    public String getVal()
    {
        return this.val;
    }

    @Override
    public boolean equals(Object another)
    {
        if(another instanceof StringValue)
        {
            if(val.equals(((StringValue) another).getVal()))
                return true;
            else
                return false;
        }
        else return false;
    }

    @Override
    public Type getType()
    {
        return new StringType();
    }

    @Override
    public String toString()
    {
        return val.toString();
    }
}
