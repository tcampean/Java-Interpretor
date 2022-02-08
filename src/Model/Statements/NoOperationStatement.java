package Model.Statements;

import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Type.Type;

public class NoOperationStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state)
    {
        return null;
    }

    @Override
    public String toString()
    {
        return " ";
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return types;
    }
}
