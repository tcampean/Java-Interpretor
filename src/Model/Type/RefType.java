package Model.Type;

import Model.Value.RefValue;
import Model.Value.Value;

import java.sql.Ref;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner)
    {
        this.inner = inner;
    }

    public Type getInner()
    {
        return this.inner;
    }

    @Override
    public boolean equals(Object another)
    {
        if(another instanceof RefType)
            return true;
        else
            return false;
    }

    @Override
    public String toString()
    {
        return "Ref("+inner.toString()+")";
    }

    @Override
    public Value defaultValue()
    {
        return new RefValue(0,inner);
    }
}
