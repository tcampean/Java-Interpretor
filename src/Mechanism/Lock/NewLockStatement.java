package Mechanism.Lock;


import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class NewLockStatement implements IStatement
{
    private String var;
    private Integer nextFree = 0; // to fix free pos

    public NewLockStatement(String v)
    {
        var = v;
    }

    public ProgramState execute(ProgramState state)
    {
        ILock<Integer, Integer> lockTable =state.getLockTable();
        IDict<String, Value> symTable = state.getSymTable();
        synchronized (lockTable)
        {
            lockTable.add(nextFree, -1);
        }

        IntValue intFree = new IntValue(nextFree);
        symTable.Update(var, intFree);

        nextFree++;
        return null;
    }


    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return types;
    }
    @Override
    public String toString()
    {
        return "NewLock(" + var +")";
    }
}
