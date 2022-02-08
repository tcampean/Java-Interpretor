package Repository;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Containers.IList;
import Model.ProgramState.ProgramState;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

public interface IRepository {
    List<ProgramState> getProgramList();
    void logPrgStateExec(ProgramState state);
    public void setProgramList(List<ProgramState> newStates);
    public int getCount();
    public IList<Value> getOutputState();
    public Set<StringValue> getFileTableKeys();
    public IHeap<Integer, Value> getHeap();
    public IDict<Integer, Pair<Integer, List<Integer>>> getSemaphore();
}
