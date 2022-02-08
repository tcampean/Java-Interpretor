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
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{
    Expression exp;

    public OpenRFileStatement(Expression e)
    {
        this.exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, EvaluationException
    {
        IFile<StringValue, BufferedReader> fileTable = state.getfTable();
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        Value expValue = this.exp.eval(symTable,heap);
        StringValue stringExpValue = (StringValue) expValue;

        StringType strType = new StringType();
        if(expValue.getType().getClass().equals(strType.getClass()))
        {
            if(!fileTable.IsDefined(stringExpValue))
            {
                try{
                    FileReader fileR = new FileReader(stringExpValue.toString());
                    BufferedReader bufferR = new BufferedReader(fileR);
                    fileTable.Update(stringExpValue,bufferR);
                }catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                throw new EvaluationException("The file already exists!\n");
            }
        }
        else {
            throw new ExpressionException("The expression is not a string type!\n");
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
        return "OpenRFile( " + this.exp.toString() + " )";
    }
}
