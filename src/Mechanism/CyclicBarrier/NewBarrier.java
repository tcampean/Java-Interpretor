package Mechanism.CyclicBarrier;



import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.ArrayList;

public class NewBarrier implements IStatement
{
    private String var;
    private Expression e;
    private Integer nextFree = 0;

    public NewBarrier(String v, Expression e)
    {
        var = v;
        this.e = e;
    }

    @Override
    public ProgramState execute(ProgramState p) throws EvaluationException, ExpressionException
    {

        Value v = e.eval(p.getSymTable(), p.getHeap());
        IntValue intVal = (IntValue) v;
        if (intVal.getValue() != 0)
            {
                synchronized (p.getBarrier())
                {
                    Value ev = e.eval(p.getSymTable(), p.getHeap());
                    IntValue s = (IntValue) ev;
                    Integer evInt = s.getValue();
                    p.getBarrier().add(nextFree,new Pair(evInt, new ArrayList<Integer>()));
                }
                    IntValue intFree = new IntValue(nextFree);
                    p.getSymTable().Update(var,intFree);

            }
            nextFree++;
            return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return types;
    }

    @Override
    public String toString() {
        return "NewBarrierStmt(" + var + ", "+ e + ")";
    }
}