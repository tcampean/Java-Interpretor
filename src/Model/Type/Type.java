package Model.Type;

import Model.Value.Value;

public interface Type {
    public boolean equals(Object o);
    Value defaultValue();

}
