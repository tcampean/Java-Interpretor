package Mechanism.Lock;


import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class LockStatement implements IStatement
{
    private String var;
    public LockStatement(String name)
    {
        var = name;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        IDict<String, Value> symTable = state.getSymTable();
        ILock<Integer,Integer> lt = state.getLockTable();

        if (!symTable.IsDefined(var))
            throw new RuntimeException("Runtime exception !");

        Value poz = symTable.Find(var);
        IntValue intValue = (IntValue) poz;
        Integer intPos = intValue.getValue();

        synchronized(lt)
        {
            if (!lt.contains(intPos))
                throw new RuntimeException("Runtime exception !");

            if (lt.get(intPos) == -1)
                lt.add(intPos, state.getId());
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
        return "Lock(" + var + ")";
    }
}