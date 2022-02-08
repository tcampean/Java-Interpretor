package Mechanism.Semaphore;

import Model.Containers.IDict;
import Model.Containers.SymTable;
import javafx.util.Pair;

import java.util.List;

public class Semaphore implements ISemaphore {
    IDict<Integer, Pair<Integer, List<Integer>>> semaphore;
    private int semaphoreAddress = 0;

    public Semaphore(){
        semaphore = new SymTable<>();
    }

    public IDict<Integer, Pair<Integer, List<Integer>>> getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(IDict<Integer, Pair<Integer, List<Integer>>> semaphore) {
        this.semaphore = semaphore;
    }

    public Integer getSemaphoreAddress(){
        return semaphoreAddress++;
    }

    @Override
    public void put(Integer foundIndex, Pair<Integer, List<Integer>> integerListPair) {
        semaphore.Update(foundIndex, integerListPair);
    }
}