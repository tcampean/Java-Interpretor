package Model.ProgramState;

import Mechanism.CyclicBarrier.Barrier;
import Mechanism.CyclicBarrier.IBarrier;
import Mechanism.Latch.ILatchTable;
import Mechanism.Latch.LatchTable;
import Mechanism.Lock.ILock;
import Mechanism.Lock.Lock;
import Mechanism.Semaphore.Semaphore;
import Model.Containers.*;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StackException;
import Model.Statements.IStatement;
import Model.Value.BoolValue;
import Model.Value.RefValue;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramState {
    IStack<IStatement> exeStack;
    IDict<String, Value> symTable;
    IList<Value> out;
    IStatement initialProgram;
    IFile<StringValue, BufferedReader> fTable;
    IHeap<Integer, Value> heap;
    Semaphore semaphore;
    LatchTable<Integer,Value> latchTable;
    int id = 1;
    static int stateID = 1;
    ILock<Integer,Integer> lockTable;
    IBarrier<Integer, Mechanism.CyclicBarrier.Pair<Integer, ArrayList<Integer>>> barrier;

    public ProgramState(IStatement program) {
        exeStack = new ExeStack<IStatement>();
        symTable = new SymTable<String, Value>();
        out = new OutList<Value>();
        fTable = new FileTable<StringValue, BufferedReader>();
        heap = new Heap<Integer, Value>();
        initialProgram = program;
        exeStack.Push(program);
        semaphore = new Semaphore();
        latchTable = new LatchTable<>();
        lockTable = new Lock<>();
        barrier = new Barrier<>();


    }

    public ProgramState(IStack<IStatement> stack, IDict<String, Value> table, IList<Value> list, IStatement program, IFile<StringValue, BufferedReader> fileTable, IHeap<Integer, Value> heap, Semaphore sem, LatchTable<Integer,Value> l, ILock<Integer,Integer> lock,IBarrier<Integer, Mechanism.CyclicBarrier.Pair<Integer,ArrayList<Integer>>> barrier) {
        exeStack = stack;
        symTable = table;
        out = list;
        initialProgram = program;
        exeStack.Push(program);
        fTable = fileTable;
        this.heap = heap;
        id = ProgramState.increaseID();
        semaphore = sem;
        latchTable = l;
        lockTable = lock;
        this.barrier = barrier;


    }

    public IStack<IStatement> getExeStack() {
        return exeStack;
    }

    public Semaphore getSemaphore() { return semaphore; }

    public IDict<String, Value> getSymTable() {
        return symTable;
    }

    public IList<Value> getOut() {
        return out;
    }

    public IFile<StringValue, BufferedReader> getfTable() {
        return fTable;
    }

    public IStatement getInitialProgram(){
        return  initialProgram;
    }

    public Set<StringValue> getFileTableKeys()
    {
        return this.fTable.getKeys();
    }

    public IHeap<Integer,Value> getHeap() { return heap;}

    public LatchTable<Integer,Value> getLatchTable() { return latchTable; }

    public ILock<Integer, Integer> getLockTable(){ return lockTable; }

    public IBarrier<Integer, Mechanism.CyclicBarrier.Pair<Integer,ArrayList<Integer>>> getBarrier() { return barrier; }


    public void setExeStack(IStack<IStatement> s)
    {
        exeStack = s;
    }

    public void setSymTable(IDict<String,Value> dict)
    {
        symTable = dict;
    }

    public void setOut(IList<Value> list)
    {
        out = list;
    }

    public void setInitialProgram(IStatement program)
    {
        initialProgram = program;
    }

    public void setHeap(IHeap<Integer,Value> heap) {this.heap = heap;}

    public void setfTable(IFile<StringValue, BufferedReader> filet)
    {
        this.fTable = filet;
    }

    public void setSemaphore(IDict<Integer, Pair<Integer, List<Integer>>> s) { semaphore.setSemaphore(s); }

    public Boolean isNotCompleted()
    {
        return !exeStack.Empty();
    }

    public void setLatchTable(LatchTable<Integer,Value> l ) { latchTable = l; }

    public static synchronized int increaseID()
    {
        stateID += 1;
        return stateID;
    }

    public int getId()
    {
        return id;
    }

    public ProgramState oneStep() throws ExpressionException, EvaluationException, StackException {

        IStatement currentStatement = exeStack.Pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString()
    {
        return "ID =" + Integer.toString(id)+"\nProgram State " +"\nExecution Stack = \n" + exeStack +"\n\nSymbol Table = \n" + symTable +
                "\n\nOutput = \n" +out+"\n\nInitial program = \n"+ initialProgram + "\n\n"
                + "File table = \n" + fTable.toString() + "\n\n" +"Heap = \n" + heap.toString() + "\n" ;
    }

    public Map<Integer,Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet()
                .stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }


    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues,Collection<Value> heapValues){
        List<Integer> symTableList = symTableValues.stream()
            .filter(v-> v instanceof RefValue)
            .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
            .collect(Collectors.toList());

        List<Integer> heapList = heapValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v ->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());

        symTableList.addAll(heapList);
        return symTableList;
    }

}
