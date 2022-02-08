package Mechanism.Semaphore;

import Model.Containers.IDict;
import Model.Containers.IStack;
import Model.Exceptions.EvaluationException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import javafx.util.Pair;


import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements IStatement {
    private String var;
    private static Lock lock = new ReentrantLock();

    public AcquireStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try{
            IDict<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphore().getSemaphore();
            IStack<IStatement> stack = state.getExeStack();


            if(state.getSymTable().Find(var) == null)
                throw new Exception("No such variable in symbolTable");
            Value foundIndex = state.getSymTable().Find(var);
            IntValue intInd = (IntValue) foundIndex;
            int intIndex = intInd.getValue();

            Pair<Integer, List<Integer>> semaphoreValue = semaphoreTable.Find(intIndex);
            List<Integer> threads = semaphoreValue.getValue();
            Integer nMax = semaphoreValue.getKey();
            if(nMax != threads.size())
            {
                if(threads.contains(state.getId()))
                    throw new EvaluationException("Already in process");
                threads.add(state.getId());
                state.getSemaphore().put(intIndex, new Pair<>(nMax, threads));
            }else
            {
                stack.Push(this);
                state.setExeStack(stack);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeVar = types.Find(var);
        if(typeVar.equals(new IntType()))
            return types;
        else throw new EvaluationException("Var is not int!\n");
    }
    
    @Override
    public String toString() {
        return "acquire( " + var + " )";
    }
}
