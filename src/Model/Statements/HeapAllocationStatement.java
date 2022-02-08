package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.sql.Ref;

public class HeapAllocationStatement implements IStatement{
    StringValue varName;
    Expression exp;

    public HeapAllocationStatement(StringValue varName, Expression exp)
    {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString()
    {
        return "new("+varName.toString()+", "+exp.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException
    {
        IHeap<Integer, Value> heap = state.getHeap();
        IDict<String,Value> symTable = state.getSymTable();
        if(!symTable.IsDefined(varName.getVal()))
        {
            throw new EvaluationException("There is no such variable!\n");
        }
        else
        {
            Value val = symTable.Find(varName.getVal());
            Type t = new IntType();
            RefType typ = new RefType(t);
            if(!val.getType().equals(typ))
            {
                throw new EvaluationException("This is not a ref type!\n");
            }
            else
            {
                Value newVal = exp.eval(symTable,heap);
                if(!newVal.getType().equals(((RefValue) val).getLocationType()))
                {
                    throw new EvaluationException("Types are not equal!\n");
                }
                else
                {
                    int addr = heap.generateNext();
                    heap.put(addr,newVal);
                    Type newRefType = ((RefValue) val).getLocationType();
                    Value newRefValue = new RefValue(addr,newRefType);
                    symTable.Update(varName.getVal(), newRefValue);

                }
            }
        }
        state.setHeap(heap);
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IDict<String,Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeVar = types.Find(varName.getVal());
        Type typeExp = exp.typeCheck(types);
        if(typeVar.equals(new RefType(typeExp)))
            return types;
        else throw new EvaluationException("Types do not match!\n");
    }

}
