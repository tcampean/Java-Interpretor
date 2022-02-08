package Model.GUI;

import Controller.Controller;

import Mechanism.CyclicBarrier.TableModelBarrier;
import Mechanism.Semaphore.TableModelSemaphore;
import Model.Containers.IDict;
import Model.Containers.IList;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class ProgramWindowController implements Initializable {

    @FXML
    public TableColumn<TableModel,String> addressCol;
    @FXML
    public TableColumn<TableModel,String> valueCol;
    @FXML
    public TableColumn<TableModel,String> varName;
    @FXML
    public TableColumn<TableModel,String> value;
    @FXML
    public TableColumn<TableModelBarrier,String> list;
    @FXML
    public TableColumn<TableModelBarrier,Integer> semaphoreValue;
    @FXML
    public TableColumn<TableModelBarrier,Integer> index;

    @FXML
    public TableView<TableModelBarrier> semaphoreTable;
    @FXML
    private Button exitButton;
    @FXML
    private Button runStep;
    @FXML
    private TextField prgCount;
    @FXML
    private ListView<String> outputList;
    @FXML
    private ListView<String> fileTableList;
    @FXML
    private ListView<String> exeStackList;
    @FXML
    private ListView<String> programStatesList;
    @FXML
    private TableView<TableModel> heapTable;
    @FXML
    private TableView<TableModel> symTable;

    private Controller ctrl;

    private int selectedID;

    private IRepository repo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Platform.runLater(() -> {
            addressCol.setCellValueFactory(new PropertyValueFactory<TableModel,String>("firstCol"));
            valueCol.setCellValueFactory(new PropertyValueFactory<TableModel,String>("secondCol"));

            programStatesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            programStatesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal)
                {
                    try {
                        selectedID=Integer.parseInt(newVal);
                    }
                    catch (Exception e)
                    {
                        selectedID=-1;
                    }
                    setDependencies();
                }
            });

            varName.setCellValueFactory(new PropertyValueFactory<TableModel,String>("firstCol"));
            value.setCellValueFactory(new PropertyValueFactory<TableModel,String>("secondCol"));
            semaphoreValue.setCellValueFactory(new PropertyValueFactory<TableModelBarrier,Integer>("value"));
            list.setCellValueFactory(new PropertyValueFactory<TableModelBarrier,String>("list"));
            index.setCellValueFactory(new PropertyValueFactory<TableModelBarrier,Integer>("index"));

            try {
                this.repo = this.ctrl.getRepository();
            }catch (NullPointerException e)
            {
                return;
            }

            this.updateWindow();
        });
    }

    public void setController(Controller newCont) {
        ctrl=newCont;
    }

    private void setPrgStateCount()
    {
        int count=this.ctrl.getRepoCount();
        prgCount.setText(Integer.toString(count));
    }

    private void setOutputList()
    {
        ObservableList<String> outList = FXCollections.observableArrayList();
        IList<Value> auxList = this.ctrl.getOut();
        for(int i=auxList.size()-1; i>=0; i--)
        {
            outList.add(auxList.Get(i).toString());
        }
        outputList.setItems(outList);
    }

    private void setFileTableList()
    {
        ObservableList<String> fileList = FXCollections.observableArrayList();
        Set<StringValue> auxSet = this.ctrl.getFileTableKeys();
        for(StringValue s :auxSet)
        {
            fileList.add(s.getVal());
        }
        fileTableList.setItems(fileList);
    }

    private void setHeapTable()
    {
        HashMap<Integer, Value> aux = this.ctrl.getHeap().getContent();
        ObservableList<TableModel> tbl = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Value> cursor : aux.entrySet())
        {
            String first=cursor.getKey().toString();
            String second=cursor.getValue().toString();
            tbl.add(new TableModel(first, second));
        }
        heapTable.setItems(tbl);
    }

    private void setSemaphoreTable()
    {
        HashMap<Integer, Pair<Integer, List<Integer>>> semTable = this.ctrl.getSemaphore().getContent();
        ObservableList<TableModelBarrier> table = FXCollections.observableArrayList();
        for(Map.Entry<Integer,Pair<Integer, List<Integer>>> cursor : semTable.entrySet())
        {
            Integer ind = cursor.getKey();
            Integer val = cursor.getValue().getKey();
            String list = cursor.getValue().getValue().toString();
            table.add(new TableModelBarrier(ind,val,list));
        }
        semaphoreTable.setItems(table);
    }

    private void setPrgStateIDList()
    {
        ObservableList<String> prgStateList = FXCollections.observableArrayList();
        List<String> aux = this.ctrl.getPrgStateIDs();
        for (String i: aux)
        {
            prgStateList.add(i);
        }
        programStatesList.setItems(prgStateList);
    }

    private void setDependencies()
    {
        if(selectedID!=-1)
        {
            List<ProgramState> list = this.ctrl.getRepository().getProgramList();
            for(ProgramState i : list)
            {
                if (i.getId()==selectedID)
                {
                    HashMap<String, Value> symTbl = i.getSymTable().getContent();
                    ObservableList<TableModel> tbl = FXCollections.observableArrayList();
                    for (Map.Entry<String, Value> cursor : symTbl.entrySet())
                    {
                        String first=cursor.getKey();
                        String second=cursor.getValue().toString();
                        tbl.add(new TableModel(first, second));
                    }
                    symTable.setItems(tbl);

                    Stack<IStatement> stack = i.getExeStack().getContents();
                    ObservableList<String> stk = FXCollections.observableArrayList();
                    ListIterator<IStatement> iterator = stack.listIterator(stack.size());
                    while(iterator.hasPrevious())
                    {
                        stk.add(iterator.previous().toString());
                    }
                    exeStackList.setItems(stk);
                }
            }
        }
        else {
            symTable.setItems(null);
            exeStackList.setItems(null);
        }
    }

    private void updateWindow()
    {
        this.setPrgStateCount();
        this.setOutputList();
        this.setFileTableList();
        this.setHeapTable();
        this.setPrgStateIDList();
        this.setSemaphoreTable();
        this.setDependencies();
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException
    {

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList). stream()
                . map(future -> { try {
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

        repo.setProgramList(prgList);

    }
    ExecutorService executor;

    public void runStepClicked(ActionEvent actionEvent) {

        executor = Executors.newFixedThreadPool(8);

        List<ProgramState> prgList=removeCompletedPrg(repo.getProgramList());

        if(prgList.size() > 0)
        {
            try {
                oneStepForAllPrg(prgList);

                HashMap<Integer,Value> lastHeap=new HashMap<Integer,Value>(prgList.get(0).getHeap().getContent());
                List<Integer> allSymTableAddrs = new ArrayList<Integer>();
                HashMap<Integer, Value> heapAddrs = prgList.get(0).getHeap().getContent();
                prgList.forEach((prg) -> allSymTableAddrs.addAll(prg.getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap().getContent().values())));
                prgList.get(0).getHeap().setContent(prgList.get(0).unsafeGarbageCollector(
                        allSymTableAddrs,
                        heapAddrs));

                while(!lastHeap.equals(prgList.get(0).getHeap().getContent()))
                {
                    lastHeap=new HashMap<Integer,Value>(prgList.get(0).getHeap().getContent());
                    List<Integer> newSymTableAddrs = new ArrayList<Integer>();
                    prgList.forEach((prg) -> newSymTableAddrs.addAll(prg.getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap().getContent().values())));
                    prgList.get(0).getHeap().setContent(prgList.get(0).unsafeGarbageCollector(
                            newSymTableAddrs,
                            lastHeap));
                }

                prgList.forEach(prg ->{
                        repo.logPrgStateExec(prg);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setOutputList();
            this.setFileTableList();
            this.setHeapTable();
            prgList=removeCompletedPrg(repo.getProgramList());
            this.setPrgStateCount();
            this.setPrgStateIDList();
            this.setSemaphoreTable();
            this.setDependencies();
        }
        executor.shutdownNow();
        prgList=removeCompletedPrg(repo.getProgramList());
        repo.setProgramList(prgList);

        if(prgList.size()<=0)
        {
            prgCount.setText("0");
        }
    }

    public void exitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
}
