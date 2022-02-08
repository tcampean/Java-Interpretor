package Mechanism.Latch;

import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;


public class AwaitStatement implements IStatement {
    private String var;

    public AwaitStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState p) {
        if (!p.getSymTable().getContent().containsKey(var))
            return null;
        Value foundIndex=p.getSymTable().Find(var);
        IntValue intValue = (IntValue) foundIndex;
        Integer intIndex = intValue.getValue();

        if(!p.getLatchTable().contains(intIndex))
            return null;
        else {
            Value remaining = p.getLatchTable().get(intIndex);
            IntValue remain = (IntValue) remaining;
            if(remain.getValue()!=0)
                p.getExeStack().Push(this);
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return types;
    }

    @Override
    public String toString(){
        return "await(" + var + ")";
    }
}
