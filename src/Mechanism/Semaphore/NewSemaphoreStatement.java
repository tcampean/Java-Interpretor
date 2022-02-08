package Mechanism.Semaphore;

import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStatement implements IStatement {
    private String var;
    private Expression e;
    private static Lock lock = new ReentrantLock();

    public NewSemaphoreStatement(String var, Expression expression){
        this.var = var;
        this.e = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException {
        lock.lock();
        IDict<String, Value> symTable = state.getSymTable();
        IDict<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphore().getSemaphore();

        Value value = e.eval(symTable, state.getHeap());
        if(!value.getType().equals(new IntType())) {
            throw new EvaluationException("Not an int type");
        }
        IntValue intVal = (IntValue) value;
        Integer intValue = intVal.getValue();
        IntValue location = new IntValue(state.getSemaphore().getSemaphoreAddress());
        if(!symTable.Find(var).getType().equals(new IntType()))
            throw new EvaluationException("Not an int type");

        semaphoreTable.Update(location.getValue(), new Pair<>(intValue, new ArrayList<>()));
        symTable.Update(var, location);

        state.setSemaphore(semaphoreTable);
        state.setSymTable(symTable);
        lock.unlock();
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeVar = types.Find(var);
        Type typeExp = e.typeCheck(types);
        if(typeVar.equals(typeExp) && typeVar.equals(new IntType()))
            return types;
        else throw new EvaluationException("Types do not match!\n");
    }

    @Override
    public String toString() {
        return "newSemaphore( " + var + ", " + e.toString() + ")";
    }
}