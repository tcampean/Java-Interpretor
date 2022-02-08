package Model.GUI;

import Controller.Controller;
import Mechanism.CyclicBarrier.BarrierAwait;
import Mechanism.CyclicBarrier.NewBarrier;
import Mechanism.Latch.AwaitStatement;
import Mechanism.Latch.CountDown;
import Mechanism.Latch.NewLatch;
import Mechanism.Lock.LockStatement;
import Mechanism.Lock.NewLockStatement;
import Mechanism.Lock.UnlockStatement;
import Mechanism.Semaphore.AcquireStatement;
import Mechanism.Semaphore.NewSemaphoreStatement;
import Mechanism.Semaphore.ReleaseStatement;
import Model.Containers.IDict;
import Model.Containers.SymTable;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private ListView<String> programList;

    @FXML
    private Button executeButton;

    @FXML
    private Button exitButton;

    Controller ctrl1 ;
    Controller ctrl2;
    Controller ctrl3;
    Controller ctrl4 ;
    Controller ctrl5 ;
    Controller ctrl6 ;
    Controller ctrl7 ;
    Controller ctrl8 ;
    Controller ctrl9;
    Controller ctrl10 ;
    Controller ctrl11 ;
    Controller ctrl12 ;
    Controller ctrl13;
    Controller ctrl14;
    Controller ctrl15;
    Controller ctrl16;
    Controller ctrl17;
    Controller ctrl18;

    IStatement ex1;
    IStatement ex2;
    IStatement ex3;
    IStatement ex4;
    IStatement ex5;
    IStatement ex6;
    IStatement ex7;
    IStatement ex8;
    IStatement ex9;
    IStatement ex10;
    IStatement ex11;
    IStatement ex12;
    IStatement ex13;
    IStatement ex14;
    IStatement ex15;
    IStatement ex16;
    IStatement ex17;
    IStatement ex18;

    public void executeClicked(ActionEvent actionEvent) {
        String selection = programList.getSelectionModel().getSelectedItem();
        if(selection == null)
            return;
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("programstate.fxml"));
            try {
                VBox newWindow = (VBox) loader.load();
                Stage stage = new Stage();
                Controller cont = null;
                selection = programList.getSelectionModel().getSelectedItem();
                if (selection.equals(ex1.toString()))
                    cont = ctrl1;
                else if (selection.equals(ex2.toString()))
                    cont = ctrl2;
                else if (selection.equals(ex3.toString()))
                    cont = ctrl3;
                else if (selection.equals(ex4.toString()))
                    cont = ctrl4;
                else if (selection.equals(ex5.toString()))
                    cont = ctrl5;
                else if (selection.equals(ex6.toString()))
                    cont = ctrl6;
                else if (selection.equals(ex7.toString()))
                    cont = ctrl7;
                else if (selection.equals(ex8.toString()))
                    cont = ctrl8;
                else if (selection.equals(ex9.toString()))
                    cont = ctrl9;
                else if (selection.equals(ex10.toString())) {
                    cont = ctrl10;
                }
                else if (selection.equals(ex11.toString())) {
                    cont = ctrl11;
                } else if (selection.equals(ex12.toString())) {
                    cont = ctrl12;
                }else if (selection.equals(ex13.toString())) {
                    cont = ctrl13;
                }else if (selection.equals(ex14.toString())) {
                    cont = ctrl14;
                }else if (selection.equals(ex15.toString())) {
                        cont = ctrl15;

                }else if (selection.equals(ex16.toString())) {
                    cont = ctrl16;
                }else if (selection.equals(ex17.toString())) {
                    cont = ctrl17;
                }else if (selection.equals(ex18.toString())) {
                        cont = ctrl18;

                }else {
                    cont = null;
                    Platform.exit();
                }
                IDict<String, Type> types1 = new SymTable<String,Type>();
                boolean valid = true;
                try
                {
                    cont.getRepository().getProgramList().get(0).getInitialProgram().typeCheck(types1);
                }catch (EvaluationException e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.show();
                    valid = false;

                }
                if(valid) {
                    ProgramWindowController newCont = loader.<ProgramWindowController>getController();
                    newCont.setController(cont);
                    stage.setScene(new Scene(newWindow));
                    stage.setTitle("Program execution");
                    stage.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        try {

             ex1 = new CompoundStatement(new VariableDecStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
            ex2 = new CompoundStatement(new VariableDecStatement("a", new IntType()), new CompoundStatement(new VariableDecStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
             ex3 = new CompoundStatement(new VariableDecStatement("a", new BoolType()), new CompoundStatement(new VariableDecStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
             ex4 = new CompoundStatement(new VariableDecStatement("varf", new StringType()),
                    new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                            new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                    new CompoundStatement(new VariableDecStatement("varc", new IntType()),
                                            new CompoundStatement(new AssignmentStatement("varc", new ValueExpression(new IntValue())),
                                                    new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                            new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                    new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                            new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CloseRFileStatement(new VariableExpression("varf")))))))))));

            IStatement ex51 = new VariableDecStatement("v", new RefType(new IntType()));
            IStatement ex52 = new HeapAllocationStatement(new StringValue("v"), new ValueExpression(new IntValue(20)));
            IStatement ex53 = new VariableDecStatement("a", new RefType(new RefType(new IntType())));
            IStatement ex54 = new HeapAllocationStatement(new StringValue("a"), new VariableExpression("v"));
            IStatement ex55 = new PrintStatement(new VariableExpression("v"));
            IStatement ex56 = new PrintStatement(new VariableExpression("a"));
             ex5 = new CompoundStatement(ex51, new CompoundStatement(ex52, new CompoundStatement(ex53, new CompoundStatement(ex54, new CompoundStatement(ex55, ex56)))));

            IStatement ex61 = new VariableDecStatement("v", new RefType(new IntType()));
            IStatement ex62 = new HeapAllocationStatement(new StringValue("v"), new ValueExpression(new IntValue(20)));
            IStatement ex63 = new VariableDecStatement("a", new RefType(new RefType(new IntType())));
            IStatement ex64 = new HeapAllocationStatement(new StringValue("a"), new VariableExpression("v"));
            IStatement ex65 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v")));
            IStatement ex66 = new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5))));
             ex6 = new CompoundStatement(ex61, new CompoundStatement(ex62, new CompoundStatement(ex63, new CompoundStatement(ex64, new CompoundStatement(ex65, ex66)))));


            IStatement ex71 = new VariableDecStatement("v", new RefType(new IntType()));
            IStatement ex72 = new HeapAllocationStatement(new StringValue("v"), new ValueExpression(new IntValue(20)));
            IStatement ex73 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v")));
            IStatement ex74 = new HeapWritingStatement("v", new ValueExpression(new IntValue(30)));
            IStatement ex75 = new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))));
             ex7 = new CompoundStatement(ex71, new CompoundStatement(ex72, new CompoundStatement(ex73, new CompoundStatement(ex74, ex75))));

            IStatement ex81 = new VariableDecStatement("v", new IntType());
            IStatement ex82 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
            IStatement ex83 = new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"), new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))));
            IStatement ex84 = new PrintStatement(new VariableExpression("v"));
            ex8 = new CompoundStatement(ex81, new CompoundStatement(ex82, new CompoundStatement(ex83, ex84)));

            IStatement ex91 = new VariableDecStatement("v", new RefType(new IntType()));
            IStatement ex92 = new HeapAllocationStatement(new StringValue("v"), new ValueExpression(new IntValue(20)));
            IStatement ex93 = new VariableDecStatement("a", new RefType(new RefType(new IntType())));
            IStatement ex94 = new HeapAllocationStatement(new StringValue("a"), new VariableExpression("v"));
            IStatement ex95 = new HeapAllocationStatement(new StringValue("v"), new ValueExpression(new IntValue(30)));
            IStatement ex96 = new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))));
             ex9 = new CompoundStatement(ex91, new CompoundStatement(ex92, new CompoundStatement(ex93, new CompoundStatement(ex94, new CompoundStatement(ex95, ex96)))));

            IStatement ex101 = new VariableDecStatement("v", new IntType());
            IStatement ex102 = new VariableDecStatement("a", new RefType(new IntType()));
            IStatement ex103 = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
            IStatement ex104 = new HeapAllocationStatement(new StringValue("a"), new ValueExpression(new IntValue(22)));
            IStatement ex106 = new HeapWritingStatement("a", new ValueExpression(new IntValue(30)));
            IStatement ex107 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
            IStatement ex108 = new PrintStatement(new VariableExpression("v"));
            IStatement ex109 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
            IStatement ex105 = new ForkStatement(new CompoundStatement(ex106, new CompoundStatement(ex107, new CompoundStatement(ex108, ex109))));
            IStatement ex1010 = new PrintStatement(new VariableExpression("v"));
            IStatement ex1011 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
            ex10 = new CompoundStatement(ex101, new CompoundStatement(ex102, new CompoundStatement(ex103, new CompoundStatement(ex104, new CompoundStatement(ex105, new CompoundStatement(ex1010, ex1011))))));

            IStatement ex111 = new VariableDecStatement("v", new IntType());
            IStatement ex112 = new AssignmentStatement("v", new ValueExpression(new IntValue(20)));
            IStatement ex117 = new CompoundStatement(new NoOperationStatement(),new CompoundStatement(new NoOperationStatement(),new CompoundStatement(new NoOperationStatement(),new NoOperationStatement())));
            IStatement ex113 = new ForkStatement(new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))), new CompoundStatement(new PrintStatement(new VariableExpression("v")),ex117)));
            IStatement ex114 = new PrintStatement(new VariableExpression("v"));
            IStatement ex115 = new PrintStatement(new VariableExpression("v"));
            ex11 = new CompoundStatement(ex111, new CompoundStatement(ex112, new CompoundStatement(ex113, new CompoundStatement(ex113, new CompoundStatement(ex114,ex115)))));

            IStatement ex121 = new VariableDecStatement("v",new IntType());
            IStatement ex122 = new AssignmentStatement("v",new ValueExpression(new IntValue(20)));
            IStatement ex123 = new ConditionalAssignment("v",new RelationalExpression(new ValueExpression(new IntValue(10)),new ValueExpression(new IntValue(20)),"<"),new ValueExpression(new IntValue(56)),new ValueExpression(new IntValue(67)));
            ex12 = new CompoundStatement(ex121,new CompoundStatement(ex122,ex123));


            IStatement ex131 = new VariableDecStatement("v", new IntType());
            IStatement ex132 = new VariableDecStatement("x", new IntType());
            IStatement ex133 = new VariableDecStatement("y", new IntType());
            IStatement ex134 = new AssignmentStatement("v", new ValueExpression(new IntValue(0)));
            IStatement ex136 = new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignmentStatement("v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))));
            IStatement ex137 = new AssignmentStatement("v",new ArithmeticExpression('+',new VariableExpression("v"),new ValueExpression(new IntValue(1))));
            IStatement ex135 = new RepeatUntil(new CompoundStatement(ex136,ex137),new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(3)),"=="));
            IStatement ex138 = new AssignmentStatement("x",new ValueExpression(new IntValue(1)));
            IStatement ex139 = new NoOperationStatement();
            IStatement ex1310 = new AssignmentStatement("y", new ValueExpression(new IntValue(3)));
            IStatement ex1311 = new PrintStatement(new ArithmeticExpression('*',new VariableExpression("v"),new ValueExpression(new IntValue(10))));
            ex13 = new CompoundStatement(ex131,new CompoundStatement(ex132,new CompoundStatement(ex133,new CompoundStatement(ex134,new CompoundStatement(ex135,new CompoundStatement(ex138,new CompoundStatement(ex139,new CompoundStatement(ex1310,new CompoundStatement(ex139,ex1311)))))))));


            IStatement ex141 = new VariableDecStatement("v",new IntType());
            IStatement ex142 = new ForStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(0))),new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(5)),"<="),new AssignmentStatement("v",new ArithmeticExpression('+', new VariableExpression("v"),new ValueExpression(new IntValue(1)))),new PrintStatement(new VariableExpression("v")));
            ex14 = new CompoundStatement(ex141,ex142);

            IStatement ex156 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v1")));
            IStatement ex157 = new ReleaseStatement("cnt");
            IStatement ex151 = new VariableDecStatement("v1",new RefType(new IntType()));
            IStatement ex152 = new VariableDecStatement("cnt",new IntType());
            IStatement ex153 = new HeapAllocationStatement(new StringValue("v1"),new ValueExpression(new IntValue(1)));
            IStatement ex154 = new NewSemaphoreStatement("cnt",new HeapReadingExpression(new VariableExpression("v1")));
            IStatement ex155 = new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"),new CompoundStatement(new HeapWritingStatement
                    ("v1",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),new CompoundStatement(ex156,ex157))));
            IStatement ex159 = new HeapWritingStatement("v1",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(2))));
            IStatement ex1510 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v1")));
            IStatement ex1511 = new ReleaseStatement("cnt");

            IStatement ex158 = new ForkStatement(new CompoundStatement(new AcquireStatement("cnt"),new CompoundStatement(new HeapWritingStatement("v1",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),new CompoundStatement(ex159,new CompoundStatement(ex1510,ex1511)))));
            IStatement ex1512 = new CompoundStatement(new AcquireStatement("cnt"),new CompoundStatement(new PrintStatement(new ArithmeticExpression('-',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1)))),new ReleaseStatement("cnt")));
            ex15 = new CompoundStatement(ex151,new CompoundStatement(ex152,new CompoundStatement(ex153,new CompoundStatement(ex154,new CompoundStatement(ex155,new CompoundStatement(ex158,ex1512))))));

            IStatement ex161 = new HeapAllocationStatement(new StringValue("v1"),new ValueExpression(new IntValue(2)));
            IStatement ex162 = new HeapAllocationStatement(new StringValue("v2"),new ValueExpression(new IntValue(3)));
            IStatement ex163 = new HeapAllocationStatement(new StringValue("v3"),new ValueExpression(new IntValue(4)));
            IStatement ex164 = new NewLatch("cnt",new HeapReadingExpression(new VariableExpression("v2")));
            IStatement ex165 = new ForkStatement(new CompoundStatement(new HeapWritingStatement("v3",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v3")),new ValueExpression(new IntValue(10)))),new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v3"))),new CountDown("cnt"))));
            IStatement ex166 = new ForkStatement(new CompoundStatement(new HeapWritingStatement("v2",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(10)))),new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v2"))),new CompoundStatement(new CountDown("cnt"),ex165))));
            IStatement ex167 = new ForkStatement(new CompoundStatement(new HeapWritingStatement("v1",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v1"))),new CompoundStatement(new CountDown("cnt"),ex166))));
            IStatement ex168 = new CompoundStatement(new AwaitStatement("cnt"),new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),new CompoundStatement(new CountDown("cnt"),new PrintStatement(new ValueExpression(new IntValue(100))))));
            ex16 = new CompoundStatement(new VariableDecStatement("v1",new RefType(new IntType())),new CompoundStatement(new VariableDecStatement("v2",new RefType(new IntType())),new CompoundStatement(new VariableDecStatement("v3",new RefType(new IntType())),new CompoundStatement( ex161, new CompoundStatement(ex162,new CompoundStatement(ex163,new CompoundStatement(ex164,new CompoundStatement(ex167,ex168))))))));


            IStatement ex171 = new CompoundStatement(new VariableDecStatement("v1", new RefType(new IntType())),new CompoundStatement(new VariableDecStatement("v2", new RefType(new IntType())),new CompoundStatement(new VariableDecStatement("x",new IntType()),new VariableDecStatement("q",new IntType()))));
            IStatement ex172 = new CompoundStatement(new HeapAllocationStatement(new StringValue("v1"),new ValueExpression(new IntValue(20))),new CompoundStatement(new HeapAllocationStatement(new StringValue("v2"),new ValueExpression(new IntValue(30))),new NewLockStatement("x")));
            IStatement ex173 = new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("x"),new CompoundStatement(new HeapWritingStatement("v1",new ArithmeticExpression('-',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1)))),new UnlockStatement("x")))),new CompoundStatement(new LockStatement("x"),new CompoundStatement(new HeapWritingStatement("v1",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),new UnlockStatement("x")))));
            IStatement ex174 = new ForkStatement(new CompoundStatement(new LockStatement("q"),new CompoundStatement(new HeapWritingStatement("v2",new ArithmeticExpression('+',new HeapReadingExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(5)))),new UnlockStatement("q"))));
            IStatement ex175 = new ForkStatement(new CompoundStatement(ex174,new CompoundStatement(new LockStatement("q"),new CompoundStatement(new HeapWritingStatement("v2",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(10)))),new UnlockStatement("q")))));
            IStatement ex176 = new CompoundStatement(new NoOperationStatement(),new CompoundStatement(new NoOperationStatement(),new CompoundStatement(new NoOperationStatement(),new NoOperationStatement())));
            IStatement ex177 = new CompoundStatement(new LockStatement("x"),new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v1"))),new UnlockStatement("x")));
            IStatement ex178 = new CompoundStatement(new LockStatement("q"),new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v2"))),new UnlockStatement("q")));
            ex17 = new CompoundStatement(ex171,new CompoundStatement(ex172,new CompoundStatement(ex173,new CompoundStatement(new NewLockStatement("q"),new CompoundStatement(ex175,new CompoundStatement(ex176,new CompoundStatement(ex177,ex178)))))));

            IStatement ex181 = new CompoundStatement(new VariableDecStatement("v1", new RefType(new IntType())),new CompoundStatement(new VariableDecStatement("v2", new RefType(new IntType())),new VariableDecStatement("v3",new RefType(new IntType()))));
            IStatement ex182 = new CompoundStatement(new HeapAllocationStatement(new StringValue("v1"),new ValueExpression(new IntValue(2))),new CompoundStatement(new HeapAllocationStatement(new StringValue("v2"),new ValueExpression(new IntValue(3))),new CompoundStatement(new HeapAllocationStatement(new StringValue("v3"),new ValueExpression(new IntValue(4))),new NewBarrier("cnt",new HeapReadingExpression(new VariableExpression("v2"))))));
            IStatement ex183 = new ForkStatement(new CompoundStatement(new BarrierAwait("cnt"),new CompoundStatement(new HeapWritingStatement("v1",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),new PrintStatement(new HeapReadingExpression(new VariableExpression("v1"))))));
            IStatement ex184 = new ForkStatement(new CompoundStatement(new BarrierAwait("cnt"),new CompoundStatement(new HeapWritingStatement("v2",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(10)))),new CompoundStatement(new HeapWritingStatement("v2",new ArithmeticExpression('*',new HeapReadingExpression(new VariableExpression("v2")),new ValueExpression(new IntValue(10)))),new PrintStatement(new HeapReadingExpression(new VariableExpression("v2")))))));
            IStatement ex185 = new CompoundStatement(new BarrierAwait("cnt"),new PrintStatement(new HeapReadingExpression(new VariableExpression("v3"))));
            ex18 = new CompoundStatement(ex181,new CompoundStatement(ex182,new CompoundStatement(ex183,new CompoundStatement(ex184,ex185))));







            IDict<String, Type> types1 = new SymTable<String, Type>();
            IDict<String, Type> types2 = new SymTable<String, Type>();
            IDict<String, Type> types3 = new SymTable<String, Type>();
            IDict<String, Type> types4 = new SymTable<String, Type>();
            IDict<String, Type> types5 = new SymTable<String, Type>();
            IDict<String, Type> types6 = new SymTable<String, Type>();
            IDict<String, Type> types7 = new SymTable<String, Type>();
            IDict<String, Type> types8 = new SymTable<String, Type>();
            IDict<String, Type> types9 = new SymTable<String, Type>();
            IDict<String, Type> types10 = new SymTable<String, Type>();
            IDict<String, Type> types11 = new SymTable<String, Type>();
            IDict<String, Type> types12 = new SymTable<String, Type>();

        /*ex1.typeCheck(types1);
        ex2.typeCheck(types2);
        ex3.typeCheck(types3);
        ex4.typeCheck(types4);
        ex5.typeCheck(types5);
        ex6.typeCheck(types6);
        ex7.typeCheck(types7);
        ex8.typeCheck(types8);
        ex9.typeCheck(types9);
        ex10.typeCheck(types10);
        ex11.typeCheck(types11);
        ex12.typeCheck(types12);
    */


            ProgramState state1 = new ProgramState(ex1);
            ProgramState state2 = new ProgramState(ex2);
            ProgramState state3 = new ProgramState(ex3);
            ProgramState state4 = new ProgramState(ex4);
            ProgramState state5 = new ProgramState(ex5);
            ProgramState state6 = new ProgramState(ex6);
            ProgramState state7 = new ProgramState(ex7);
            ProgramState state8 = new ProgramState(ex8);
            ProgramState state9 = new ProgramState(ex9);
            ProgramState state10 = new ProgramState(ex10);
            ProgramState state11 = new ProgramState(ex11);
            ProgramState state12 = new ProgramState(ex12);
            ProgramState state13 = new ProgramState(ex13);
            ProgramState state14 = new ProgramState(ex14);
            ProgramState state15 = new ProgramState(ex15);
            ProgramState state16 = new ProgramState(ex16);
            ProgramState state17 = new ProgramState(ex17);
            ProgramState state18 = new ProgramState(ex18);

            Repository repo1 = new Repository(state1, "logFile1.out");
            Repository repo2 = new Repository(state2, "logFile2.out");
            Repository repo3 = new Repository(state3, "logFile3.out");
            Repository repo4 = new Repository(state4, "logFile4.out");
            Repository repo5 = new Repository(state5, "logFile5.out");
            Repository repo6 = new Repository(state6, "logFile6.out");
            Repository repo7 = new Repository(state7, "logFile7.out");
            Repository repo8 = new Repository(state8, "logFile8.out");
            Repository repo9 = new Repository(state9, "logfile9.out");
            Repository repo10 = new Repository(state10, "logfile10.out");
            Repository repo11 = new Repository(state11, "logfile11.out");
            Repository repo12 = new Repository(state12, "logfile12.out");
            Repository repo13 = new Repository(state13,"logFile13.out");
            Repository repo14 = new Repository(state14, "logFile14.out");
            Repository repo15 = new Repository(state15,"logfile15.out");
            Repository repo16 = new Repository(state16,"logfile16.out");
            Repository repo17 = new Repository(state17, "logFile17.out");
            Repository repo18 = new Repository(state18, "logFile18.out");


            ctrl1 = new Controller(repo1);
             ctrl2 = new Controller(repo2);
             ctrl3 = new Controller(repo3);
            ctrl4 = new Controller(repo4);
            ctrl5 = new Controller(repo5);
             ctrl6 = new Controller(repo6);
            ctrl7 = new Controller(repo7);
             ctrl8 = new Controller(repo8);
            ctrl9 = new Controller(repo9);
             ctrl10 = new Controller(repo10);
             ctrl11 = new Controller(repo11);
             ctrl12 = new Controller(repo12);
             ctrl13 = new Controller(repo13);
             ctrl14 = new Controller(repo14);
             ctrl15 = new Controller(repo15);
             ctrl16 = new Controller(repo16);
             ctrl17 = new Controller(repo17);
             ctrl18 = new Controller(repo18);

            ObservableList<String> str_list = FXCollections.observableArrayList();

            str_list.add(ex1.toString());
            str_list.add(ex2.toString());
            str_list.add(ex3.toString());
            str_list.add(ex4.toString());
            str_list.add(ex5.toString());
            str_list.add(ex6.toString());
            str_list.add(ex7.toString());
            str_list.add(ex8.toString());
            str_list.add(ex9.toString());
            str_list.add(ex10.toString());
            str_list.add(ex11.toString());
            str_list.add(ex12.toString());
            str_list.add(ex13.toString());
            str_list.add(ex14.toString());
            str_list.add(ex15.toString());
            str_list.add(ex16.toString());
            str_list.add(ex17.toString()) ;
            str_list.add(ex18.toString());
            programList.setItems(str_list);
            programList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        }catch (ExpressionException e) {
            e.printStackTrace();
        }

    }



}
