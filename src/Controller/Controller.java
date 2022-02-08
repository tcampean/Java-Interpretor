package Controller;

import Model.Containers.*;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StackException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import javafx.util.Pair;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    ExecutorService executor;

    public Controller(IRepository repo)
    {
        repository = repo;
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {
        /*prgList.forEach(prg -> {System.out.println(prg.toString());
                                repository.logPrgStateExec(prg);
                                });
        */
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> { return p.oneStep();}))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(p -> p!=null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        repository.setProgramList(prgList);


    }

    public void allStep() throws ExpressionException,EvaluationException, StackException
    {
        IDict<String, Type> types1 = new SymTable<String,Type>();
        repository.getProgramList().get(0).getInitialProgram().typeCheck(types1);
        executor = Executors.newFixedThreadPool(8);

        List<ProgramState> prgList=removeCompletedPrograms(repository.getProgramList());
        while(prgList.size() > 0){
            System.out.println("Nr. of active threads: "+Integer.toString(prgList.size()));
            try {
                oneStepForAllPrg(prgList);

                List<Integer> allSymTableAddrs = new ArrayList<Integer>();
                HashMap<Integer, Value> heapAddrs = prgList.get(0).getHeap().getContent();
                prgList.forEach((prg) -> allSymTableAddrs.addAll(prg.getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap().getContent().values())));
                prgList.get(0).getHeap().setContent(prgList.get(0).unsafeGarbageCollector(
                        allSymTableAddrs,
                        heapAddrs));

                prgList.forEach(prg ->{
                        System.out.println(prg.toString());
                        repository.logPrgStateExec(prg);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prgList=removeCompletedPrograms(repository.getProgramList());
        }
        executor.shutdownNow();

        prgList.forEach(prg ->{
                System.out.println(prg.toString());
                repository.logPrgStateExec(prg);
        });

        repository.setProgramList(prgList);
    }

    public IRepository getRepository()
    {
        return this.repository;
    }

    public int getRepoCount()
    {
        return this.repository.getCount();
    }

    public IList<Value> getOut()
    {
        return this.repository.getOutputState();
    }

    public Set<StringValue> getFileTableKeys() {
        return this.repository.getFileTableKeys();
    }

    public IHeap<Integer, Value> getHeap()
    {
        return this.repository.getHeap();
    }

    public IDict<Integer, Pair<Integer, List<Integer>>> getSemaphore() { return this.repository.getSemaphore();}

    public List<String> getPrgStateIDs()
    {
        List<String> aux = new ArrayList<String>();
        List<ProgramState> aux2 = this.repository.getProgramList();
        for (ProgramState i : aux2)
        {
            aux.add(Integer.toString(i.getId()));
        }
        return aux;
    }
}
