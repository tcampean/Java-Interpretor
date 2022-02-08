package Repository;

import Model.Containers.*;
import Model.ProgramState.ProgramState;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Repository implements IRepository {
    List<ProgramState> states = new ArrayList<ProgramState>();
    Path filePath;
    PrintWriter logFile;

    public Repository(ProgramState prgState, String fileName) {
        states.add(prgState);
        filePath = Paths.get(fileName).toAbsolutePath();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return states;
    }

    @Override
    public void setProgramList(List<ProgramState> newStates) {
        this.states = newStates;
    }

    @Override
    public void logPrgStateExec(ProgramState state) {
        try {
            this.logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath.toString(), true)));
            this.logFile.println(state.toString());
            this.logFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public IList<Value> getOutputState() {
        return this.states.get(0).getOut();
    }
    @Override
    public Set<StringValue> getFileTableKeys() {
        return this.states.get(0).getfTable().getKeys();
    }
    @Override
    public IHeap<Integer, Value> getHeap()
    {
        return this.states.get(0).getHeap();
    }
    @Override
    public int getCount()
    {
        return states.size();
    }

    @Override
    public IDict<Integer, Pair<Integer, List<Integer>>> getSemaphore() { return this.states.get(0).getSemaphore().getSemaphore();}





}
