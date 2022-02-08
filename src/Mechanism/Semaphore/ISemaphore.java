package Mechanism.Semaphore;

import Model.Containers.IDict;
import javafx.util.Pair;

import java.util.List;

public interface ISemaphore {
    void setSemaphore(IDict<Integer, Pair<Integer, List<Integer>>> semaphore);
    IDict<Integer, Pair<Integer, List<Integer>>> getSemaphore();
    Integer getSemaphoreAddress();

    void put(Integer foundIndex, Pair<Integer, List<Integer>> integerListPair);
}