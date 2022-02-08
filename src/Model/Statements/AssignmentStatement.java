package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Containers.IStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignmentStatement implements IStatement{
    String key;
    Expression e;

    public AssignmentStatement(String k,Expression exp)
    {
        key = k;
        e = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, EvaluationException {
        IStack<IStatement> stack = state.getExeStack();
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        if(symTable.IsDefined(key))
        {
            Value val = e.eval(symTable,heap);
            Type keyType = (symTable.Find(key)).getType();
            if(val.getType().equals(keyType))
                symTable.Update(key,val);
            else throw new ExpressionException("Type of variable and expression do not match!\n");

        }else throw new EvaluationException("Undeclared variable!\n");
        state.setSymTable(symTable);
        return null;
    }
    @Override
    public IDict<String,Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeVar = types.Find(key);
        Type typeExp = e.typeCheck(types);
        if(typeVar.equals(typeExp))
            return types;
        else throw new EvaluationException("Types do not match!\n");
    }

    @Override
    public String toString()
    {
        return key +" = "+ e.toString();
    }
}
