package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

import java.sql.Ref;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }


    public boolean equals(Object another)
    {
        if(another instanceof RefValue)
        {
            if(address==((RefValue)another).getAddress())
                return true;
            else
                return false;

        }
        else
            return false;
    }
    public int getAddress()
    {
        return address;
    }

    public Type getLocationType()
    {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString()
    {
        return "(" + address +", " + locationType.toString() +")";
    }

}
