package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.ProgramState.ProgramState;
import Model.Type.Type;

public class CompoundStatement implements IStatement{
    IStatement s1;
    IStatement s2;

    public CompoundStatement(IStatement first, IStatement second)
    {
        s1 = first;
        s2 = second;
    }

    public IStatement getS1()
    {
        return s1;
    }

    public IStatement getS2()
    {
        return s2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException
    {
        IStack<IStatement> stack = state.getExeStack();
        stack.Push(s2);
        stack.Push(s1);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return s2.typeCheck(s1.typeCheck(types));
    }

    @Override
    public String toString()
    {
        return s1.toString() + " ; " + s2.toString();
    }
}
