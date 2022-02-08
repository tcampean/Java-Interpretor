package com.company;

import Controller.Controller;
import Model.GUI.FX;
import Model.Containers.*;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StackException;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;
import View.Commands.ExitCommand;
import View.Commands.RunExample;
import View.TextMenu;
import javafx.application.Application;

public class Interpreter {

    public static void main(String[] args) throws ExpressionException, EvaluationException, StackException {

        IStatement ex1 = new CompoundStatement(new VariableDecStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        IStatement ex2 = new CompoundStatement(new VariableDecStatement("a", new IntType()), new CompoundStatement(new VariableDecStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IStatement ex3 = new CompoundStatement(new VariableDecStatement("a", new BoolType()), new CompoundStatement(new VariableDecStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        IStatement ex4 = new CompoundStatement(new VariableDecStatement("varf",new StringType()),
                new CompoundStatement(new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDecStatement("varc",new IntType()),
                                        new CompoundStatement(new AssignmentStatement("varc",new ValueExpression(new IntValue())),
                                                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"),"varc"),
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CloseRFileStatement(new VariableExpression("varf")))))))))));

        IStatement ex51 = new VariableDecStatement("v",new RefType(new IntType()));
        IStatement ex52 = new HeapAllocationStatement(new StringValue("v"),new ValueExpression(new IntValue(20)));
        IStatement ex53 = new VariableDecStatement("a",new RefType(new RefType(new IntType())));
        IStatement ex54 = new HeapAllocationStatement(new StringValue("a"),new VariableExpression("v"));
        IStatement ex55 = new PrintStatement(new VariableExpression("v"));
        IStatement ex56 = new PrintStatement(new VariableExpression("a"));
        IStatement ex5 = new CompoundStatement(ex51,new CompoundStatement(ex52,new CompoundStatement(ex53,new CompoundStatement(ex54,new CompoundStatement(ex55,ex56)))));

        IStatement ex61 = new VariableDecStatement("v",new RefType(new IntType()));
        IStatement ex62 = new HeapAllocationStatement(new StringValue("v"),new ValueExpression(new IntValue(20)));
        IStatement ex63 = new VariableDecStatement("a",new RefType(new RefType(new IntType())));
        IStatement ex64 = new HeapAllocationStatement(new StringValue("a"),new VariableExpression("v"));
        IStatement ex65 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v")));
        IStatement ex66 = new PrintStatement(new ArithmeticExpression('+',new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),new ValueExpression(new IntValue(5))));
        IStatement ex6 = new CompoundStatement(ex61,new CompoundStatement(ex62,new CompoundStatement(ex63,new CompoundStatement(ex64,new CompoundStatement(ex65,ex66)))));


        IStatement ex71 = new VariableDecStatement("v",new RefType(new IntType()));
        IStatement ex72 = new HeapAllocationStatement(new StringValue("v"),new ValueExpression(new IntValue(20)));
        IStatement ex73 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v")));
        IStatement ex74 = new HeapWritingStatement("v",new ValueExpression(new IntValue(30)));
        IStatement ex75 = new PrintStatement(new ArithmeticExpression('+',new HeapReadingExpression(new VariableExpression("v")),new ValueExpression(new IntValue(5))));
        IStatement ex7 = new CompoundStatement(ex71,new CompoundStatement(ex72,new CompoundStatement(ex73,new CompoundStatement(ex74,ex75))));

        IStatement ex81 = new VariableDecStatement("v",new IntType());
        IStatement ex82 = new AssignmentStatement("v",new ValueExpression(new IntValue(4)));
        IStatement ex83 = new WhileStatement(new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignmentStatement("v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))));
        IStatement ex84 = new PrintStatement(new VariableExpression("v"));
        IStatement ex8 = new CompoundStatement(ex81,new CompoundStatement(ex82,new CompoundStatement(ex83,ex84)));

        IStatement ex91 = new VariableDecStatement("v",new RefType(new IntType()));
        IStatement ex92 = new HeapAllocationStatement(new StringValue("v"),new ValueExpression(new IntValue(20)));
        IStatement ex93 = new VariableDecStatement("a",new RefType(new RefType(new IntType())));
        IStatement ex94 = new HeapAllocationStatement(new StringValue("a"),new VariableExpression("v"));
        IStatement ex95 = new HeapAllocationStatement(new StringValue("v"),new ValueExpression(new IntValue(30)));
        IStatement ex96 = new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))));
        IStatement ex9 = new CompoundStatement(ex91,new CompoundStatement(ex92,new CompoundStatement(ex93,new CompoundStatement(ex94,new CompoundStatement(ex95,ex96)))));

        IStatement ex101 = new VariableDecStatement("v",new IntType());
        IStatement ex102 = new VariableDecStatement("a",new RefType(new IntType()));
        IStatement ex103 = new AssignmentStatement("v",new ValueExpression(new IntValue(10)));
        IStatement ex104 = new HeapAllocationStatement(new StringValue("a"),new ValueExpression(new IntValue(22)));
        IStatement ex106 = new HeapWritingStatement("a",new ValueExpression(new IntValue(30)));
        IStatement ex107 = new AssignmentStatement("v",new ValueExpression(new IntValue(32)));
        IStatement ex108 = new PrintStatement(new VariableExpression("v"));
        IStatement ex109 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
        IStatement ex105 = new ForkStatement(new CompoundStatement(ex106,new CompoundStatement(ex107,new CompoundStatement(ex108,ex109))));
        IStatement ex1010 = new PrintStatement(new VariableExpression("v"));
        IStatement ex1011 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
        IStatement ex10 = new CompoundStatement(ex101,new CompoundStatement(ex102, new CompoundStatement(ex103,new CompoundStatement(ex104,new CompoundStatement(ex105,new CompoundStatement(ex1010,ex1011))))));

        IStatement ex111 = new VariableDecStatement("v",new IntType());
        IStatement ex112 = new AssignmentStatement("v",new ValueExpression(new IntValue(20)));
        IStatement ex113 = new ForkStatement(new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),new PrintStatement(new VariableExpression("v"))));
        IStatement ex114 = new PrintStatement(new VariableExpression("v"));
        IStatement ex115 = new PrintStatement(new VariableExpression("v"));
        IStatement ex11 = new CompoundStatement(ex111,new CompoundStatement(ex112,new CompoundStatement(ex113,new CompoundStatement(ex114,ex115))));

        IStatement ex121 = new VariableDecStatement("v",new IntType());
        IStatement ex122 = new AssignmentStatement("v",new ValueExpression(new IntValue(20)));
        IStatement ex123 = new ConditionalAssignment("v",new RelationalExpression(new ValueExpression(new IntValue(10)),new ValueExpression(new IntValue(20)),">"),new ValueExpression(new IntValue(56)),new ValueExpression(new IntValue(67)));
        IStatement ex12 = new CompoundStatement(ex121,new CompoundStatement(ex122,ex123));


        IDict<String, Type> types1 = new SymTable<String,Type>();
        IDict<String, Type> types2 = new SymTable<String,Type>();
        IDict<String, Type> types3 = new SymTable<String,Type>();
        IDict<String, Type> types4 = new SymTable<String,Type>();
        IDict<String, Type> types5 = new SymTable<String,Type>();
        IDict<String, Type> types6 = new SymTable<String,Type>();
        IDict<String, Type> types7 = new SymTable<String,Type>();
        IDict<String, Type> types8 = new SymTable<String,Type>();
        IDict<String, Type> types9 = new SymTable<String,Type>();
        IDict<String, Type> types10 = new SymTable<String,Type>();
        IDict<String, Type> types11 = new SymTable<String,Type>();
        IDict<String, Type> types12 = new SymTable<String,Type>();

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

        Repository repo1 = new Repository(state1,"logFile1.out");
        Repository repo2 = new Repository(state2,"logFile2.out");
        Repository repo3 = new Repository(state3,"logFile3.out");
        Repository repo4 = new Repository(state4,"logFile4.out");
        Repository repo5 = new Repository(state5,"logFile5.out");
        Repository repo6 = new Repository(state6,"logFile6.out");
        Repository repo7 = new Repository(state7,"logFile7.out");
        Repository repo8 = new Repository(state8,"logFile8.out");
        Repository repo9 = new Repository(state9,"logfile9.out");
        Repository repo10 = new Repository(state10,"logfile10.out");
        Repository repo11 = new Repository(state11,"logfile11.out");
        Repository repo12 = new Repository(state12,"logfile12.out");

        Controller ctrl1 = new Controller(repo1);
        Controller ctrl2 = new Controller(repo2);
        Controller ctrl3 = new Controller(repo3);
        Controller ctrl4 = new Controller(repo4);
        Controller ctrl5 = new Controller(repo5);
        Controller ctrl6 = new Controller(repo6);
        Controller ctrl7 = new Controller(repo7);
        Controller ctrl8 = new Controller(repo8);
        Controller ctrl9 = new Controller(repo9);
        Controller ctrl10 = new Controller(repo10);
        Controller ctrl11 = new Controller(repo11);
        Controller ctrl12 = new Controller(repo12);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctrl1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctrl2));
        menu.addCommand(new RunExample("3",ex3.toString(),ctrl3));
        menu.addCommand(new RunExample("4",ex4.toString(),ctrl4));
        menu.addCommand(new RunExample("5",ex5.toString(),ctrl5));
        menu.addCommand(new RunExample("6",ex6.toString(),ctrl6));
        menu.addCommand(new RunExample("7",ex7.toString(),ctrl7));
        menu.addCommand(new RunExample("8",ex8.toString(),ctrl8));
        menu.addCommand(new RunExample("9",ex9.toString(),ctrl9));
        menu.addCommand(new RunExample("10",ex10.toString(),ctrl10));
        menu.addCommand(new RunExample("11",ex11.toString(),ctrl11));
        menu.addCommand(new RunExample("12",ex12.toString(),ctrl12));
        //menu.show();
        Application.launch(FX.class);



    }
}
