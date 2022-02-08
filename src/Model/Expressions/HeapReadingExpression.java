package Model.Expressions;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class HeapReadingExpression implements Expression{
    Expression exp;

    public HeapReadingExpression(Expression e)
    {
        exp = e;
    }

    @Override
    public String toString()
    {
        return "rH("+exp.toString()+")";
    }

    @Override
    public Value eval(IDict<String,Value> symTable, IHeap<Integer,Value> heap) throws ExpressionException, EvaluationException {
        Value val = exp.eval(symTable,heap);
        Type valType = val.getType();
        Type newType = new IntType();
        RefValue newVal = new RefValue(1,newType);

        if(!valType.equals(newVal.getType()))
        {
            throw new ExpressionException("Invalid type\n");

        }
        else
        {

            int addr = ((RefValue) val).getAddress();
            if(!heap.containsKey(addr))
            {
                throw new EvaluationException("Key is not in the heap!\n");
            }
            else
            {
                return heap.getValue(addr);
            }
        }
    }

    @Override
    public Type typeCheck(IDict<String,Type> types) throws EvaluationException {
        Type type1 = exp.typeCheck(types);

        if(type1 instanceof RefType)
        {
            RefType refType1 = (RefType) type1;
            return refType1.getInner();
        }
        else throw new EvaluationException("This is not a ref type!\n");
    }

    @Override
    public Expression deepCopy()
    {
        return new HeapReadingExpression(exp);
    }
}
