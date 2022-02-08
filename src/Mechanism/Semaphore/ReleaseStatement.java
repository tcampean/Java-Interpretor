package Mechanism.Semaphore;


import Model.Containers.IDict;
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

public class ReleaseStatement implements IStatement {
    private String var;
    private static Lock lock = new ReentrantLock();

    public ReleaseStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try{
            IDict<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphore().getSemaphore();


            if(state.getSymTable().Find(var) == null)
                throw new EvaluationException("No such variable in symbolTable");
            Value foundIndex = state.getSymTable().Find(var);
            IntValue intVal = (IntValue) foundIndex;
            int intIndex = intVal.getValue();

            if(!semaphoreTable.IsDefined(intIndex))
                throw new EvaluationException("There is no such index");
            Pair<Integer, List<Integer>> semaphoreValue = semaphoreTable.Find(intIndex);
            List<Integer> threads = semaphoreValue.getValue();
            Integer nMax = semaphoreValue.getKey();
            if(threads.contains(state.getId()))
            {
                int i = 0;
                while(i<threads.size())
                {
                    if(threads.get(i) == state.getId() )
                        threads.remove(i);
                    i++;
                }
            }
            state.getSemaphore().put(intIndex, new Pair<>(nMax, threads));
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
        return "release( " + var + " )";
    }
}