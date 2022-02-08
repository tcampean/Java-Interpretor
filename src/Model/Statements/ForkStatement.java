package Model.Statements;

import Model.Containers.*;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.StackException;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

import javax.sound.sampled.Port;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ForkStatement implements IStatement{

    IStatement st;

    public ForkStatement(IStatement statement)
    {
        st=statement;
    }


    @Override
    public ProgramState execute(ProgramState state)  {
        IStack<IStatement> forkedStack = new ExeStack<IStatement>();

        IDict<String, Value> symTableOriginal = state.getSymTable();
        HashMap<String, Value> symTableOriginalValues=symTableOriginal.getContent();
        IDict<String, Value> symTableCopy = new SymTable<String, Value>();
        for (Map.Entry<String, Value> i : symTableOriginalValues.entrySet())
        {
            Map.Entry<String, Value> new_entry = i;
            symTableCopy.Update(new_entry.getKey(), new_entry.getValue());
        }
        ProgramState forked = new ProgramState(forkedStack, symTableCopy, state.getOut(),st, state.getfTable(), state.getHeap(),state.getSemaphore(),state.getLatchTable(),state.getLockTable(),state.getBarrier());
        return forked;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return st.typeCheck(types);
    }

    @Override
    public String toString()
    {
        return "fork(" + st.toString()+")";
    }
}
