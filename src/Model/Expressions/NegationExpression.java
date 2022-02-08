package Model.Expressions;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class NegationExpression implements Expression{

    Expression e;
    public NegationExpression(Expression e)
    {
        this.e = e;
    }

    @Override
    public Value eval(IDict<String,Value> symTable, IHeap<Integer,Value> heap) throws ExpressionException, EvaluationException
    {
        Value v1;
        v1 = e.eval(symTable,heap);
        if (v1.getType().equals(new BoolType()))
        {
            BoolValue i1 = (BoolValue) v1;
            boolean nr1;
            nr1 = i1.getValue();
            if(nr1)
                return new BoolValue(false);
            else
                return new BoolValue(true);
        }
        else throw new ExpressionException("Invalid boolean operator!");
    }

    @Override
    public Expression deepCopy() throws ExpressionException
    {
        return new NegationExpression(e);
    }

    @Override
    public Type typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type type1;
        type1 = e.typeCheck(types);
        if(type1.equals(new BoolType()))
                return new BoolType();
            else
                throw new EvaluationException("Operand is not a boolean!\n");


    }

    @Override
    public String toString()
    {
        return "!"+e.toString();
    }


}
