package Mechanism.Latch;


import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatch implements IStatement {
    private static int newFreeLocation = -1;
    private String var;
    private Expression exp;
    private static Lock lock = new ReentrantLock();

    public NewLatch(String var,Expression exp) {
        this.var=var;
        this.exp=exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException {
            lock.lock();
            Value number = this.exp.eval(state.getSymTable(), state.getHeap());
            synchronized (state.getLatchTable()) {
                ++newFreeLocation;
                IntValue fl = new IntValue(newFreeLocation);
                state.getLatchTable().put(newFreeLocation,number);
                state.getSymTable().Update(this.var, fl);
                lock.unlock();
                return null;
            }
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        return types;
    }

    @Override
    public String toString() {
        return "newLatch(" + this.var + ", " + this.exp.toString() + ")";
    }
}