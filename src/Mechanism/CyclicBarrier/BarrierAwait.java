package Mechanism.CyclicBarrier;


import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class BarrierAwait implements IStatement
{
    private String var;

    public BarrierAwait(String v)
    {
        var = v;
    }

    @Override
    public ProgramState execute(ProgramState state)
    {
        if(!state.getSymTable().IsDefined(var))
             return null;

        Value index = state.getSymTable().Find(var);
        IntValue intVal = (IntValue) index;
        Integer intValue = intVal.getValue();
        if (!state.getBarrier().contains(intVal.getValue())) return null;

        synchronized (state.getBarrier())
        {
            if (state.getBarrier().get(intValue).getFirst() == state.getBarrier().get(intValue).getSecond().size())
                return null;
            else
            {
                state.getExeStack().Push(new BarrierAwait(var));
                state.getBarrier().get(intValue).getSecond().add(state.getId());
                return null;
            }
        }
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return types;
    }

    @Override
    public String toString()
    {
        return "Await("+var+")";
    }
}