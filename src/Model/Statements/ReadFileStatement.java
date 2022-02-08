package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IFile;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{
    Expression exp;
    String varName;
    public ReadFileStatement(Expression e, String var)
    {
        this.exp = e;
        this.varName = var;

    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, EvaluationException {
        IFile<StringValue, BufferedReader> fileTable = state.getfTable();
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        IntType intType = new IntType();
        if (symTable.IsDefined(this.varName) && symTable.Find(this.varName).getType().getClass().equals(intType.getClass())) {
            try {
                StringValue expValue = (StringValue) this.exp.eval(symTable,heap);
                if (fileTable.IsDefined(expValue)) {
                    BufferedReader buffR = fileTable.Find(expValue);
                    String line;
                    IntValue intVal = new IntValue();
                    if ((line = buffR.readLine()) != null) {
                        intVal = new IntValue(Integer.parseInt(line));

                    } else {
                        intVal = new IntValue(0);
                    }
                    symTable.Update(this.varName, intVal);


                } else throw new EvaluationException("There is no such entry!\n");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else throw new ExpressionException("The variable is not an int\n");
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeVar = types.Find(varName);
        Type typeExp = exp.typeCheck(types);
        if(typeVar.equals(new IntType()))
            if(typeExp.equals(new StringType()))
                return types;
            else throw new EvaluationException("Expression is not a string!\n");
        else throw new EvaluationException("Variable is not an int!\n");
    }

    @Override
    public String toString()
    {
        return "ReadFile( " + exp.toString() + " )";
    }
}
