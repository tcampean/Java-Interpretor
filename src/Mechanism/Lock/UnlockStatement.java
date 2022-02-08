package Mechanism.Lock;


import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class UnlockStatement implements IStatement
{
    private String var;
    public UnlockStatement(String name)
    {
        var = name;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        IDict<String, Value> symTable = state.getSymTable();
        ILock<Integer,Integer> lockTable = state.getLockTable();

        if (!symTable.IsDefined(var))
            throw new RuntimeException("Runtime exception !");

        Value poz = symTable.Find(var);
        IntValue intPos = (IntValue) poz;

        synchronized(lockTable)
        {
            if (!lockTable.contains(intPos.getValue()))
                throw new RuntimeException("Runtime exception !");
            if (lockTable.get(intPos.getValue()) == state.getId())
                lockTable.add(intPos.getValue(), -1);
        }
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
        return "Unlock(" + var + ")";
    }
}