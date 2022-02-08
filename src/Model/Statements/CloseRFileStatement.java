package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IFile;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    Expression exp;

    public CloseRFileStatement(Expression e)
    {
        this.exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, EvaluationException
    {
        IFile<StringValue, BufferedReader> fileTable = state.getfTable();
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        try{
            StringValue strValue = (StringValue) this.exp.eval(symTable,heap);
            if(fileTable.IsDefined(strValue))
            {
                BufferedReader buffR = fileTable.Find(strValue);
                buffR.close();
                fileTable.Remove(strValue);
            }
            else {
                throw new EvaluationException("There is no such file name!\n");
            }


        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        state.setfTable(fileTable);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {

        Type typeExp = exp.typeCheck(types);
        if(typeExp.equals(new StringType()))
            return types;
        else throw new EvaluationException("Types do not match!\n");
    }

    @Override
    public String toString()
    {
        return "CloseRFile( " + exp.toString() +" )";
    }
}
