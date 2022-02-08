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
import Model.Value.Value;

public class HeapWritingStatement implements IStatement{
    String varName;
    Expression exp;

    public HeapWritingStatement(String varName,Expression e)
    {
        this.varName = varName;
        this.exp = e;
    }

    @Override
    public String toString()
    {
        return "wH(" +varName+ ", "+exp.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException
    {
        IHeap<Integer, Value> heap = state.getHeap();
        IDict<String, Value> symTable = state.getSymTable();

        if(!symTable.IsDefined(varName))
        {
            throw new EvaluationException("Key is not in symTable!\n");
        }
        else
        {
            Value val = symTable.Find(varName);
            Type newType = new RefType(new IntType());
            if(!val.getType().equals(newType))
            {
                throw new EvaluationException("Invalid type!\n");

            }
            else {
                RefValue refVal = (RefValue) val;
                int addr = refVal.getAddress();
                if(!heap.containsKey(addr))
                {
                    throw new EvaluationException("Address not in heap!\n");
                }
                else
                {
                    Value newVal = exp.eval(symTable,heap);
                    if(!newVal.getType().equals(refVal.getLocationType()))
                    {
                        throw new EvaluationException("Types do not match!\n");
                    }
                    else
                    {
                        heap.update(addr,newVal);
                    }
                }
            }
        }
        state.setSymTable(symTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IDict<String,Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeVar = types.Find(varName);
        Type typeExp = exp.typeCheck(types);
        if(typeVar.equals(new RefType(typeExp)))
            return types;
        else throw new EvaluationException("Types do not match!\n");
    }
}
