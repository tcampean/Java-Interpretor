package Mechanism.Latch;


import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class CountDown implements IStatement {
    private String var;
    public CountDown(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if (state.getSymTable().Find(this.var) == null)
            return null;
        Value index = state.getSymTable().Find(this.var);
        IntValue intVal = (IntValue) index;
        Integer intIndex = intVal.getValue();
        synchronized (state.getLatchTable()) {
            if (state.getLatchTable().get(intIndex) == null)
                return null;
            Value count = state.getLatchTable().get(intIndex);
            IntValue intCount = (IntValue) count;
            IntValue intC = new IntValue(intCount.getValue()-1);

            if (intCount.getValue() > 0) {
                state.getLatchTable().put(intIndex,intC );
                IntValue id = new IntValue(state.getId());
                state.getOut().Add(id);
            }
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return types;
    }

    @Override
    public String toString() {
        return "countDown(" + this.var + ")";
    }
}