package Model.Expressions;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExpression implements Expression{

    Value val;

    public ValueExpression(Value v)
    {
        val = v;
    }

    @Override
    public Value eval(IDict<String,Value> symTable, IHeap<Integer,Value> heap)
    {
        return val;
    }

    @Override
    public Type typeCheck(IDict<String,Type> types) throws EvaluationException {
        return val.getType();
    }

    @Override
    public Expression deepCopy()
    {
        return new ValueExpression(val);
    }

    @Override
    public String toString()
    {
        return val.toString();
    }
}
