package Model.Expressions;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.Value;


public class VariableExpression implements Expression {
    String key;
    public VariableExpression(String k)
    {
        key = k;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, IHeap<Integer,Value> heap) throws ExpressionException
    {
        if(symTable.Find(key) == null)
            throw new ExpressionException("Undefined variable!\n");
        return symTable.Find(key);
    }

    @Override
    public Type typeCheck(IDict<String,Type> types) throws EvaluationException {
        return types.Find(key);
    }

    @Override
    public Expression deepCopy()
    {
        return new VariableExpression(key);
    }

    @Override
    public String toString()
    {
        return key;
    }


}
