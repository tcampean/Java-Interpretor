package Model.Statements;

import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.ProgramState.ProgramState;
import Model.Type.*;
import Model.Value.*;

public class VariableDecStatement implements IStatement{
    String name;
    Type type;

    public VariableDecStatement(String n, Type t)
    {
        name = n;
        type = t;
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException
    {
        IDict<String , Value> symTable = state.getSymTable();
        if(symTable.IsDefined(name))
            throw new ExpressionException("Symbol is already defined!\n");
        else
        {
            Value v;
            if(type.equals(new BoolType()))
                v = new BoolValue();
            else if(type.equals(new IntType()))
                v = new IntValue();
            else if(type.equals(new StringType()))
                v = new StringValue();
            else if(type.equals(new RefType(new IntType())))
            {
                RefType refType = (RefType) type;
                v = refType.defaultValue();
            }
            else throw new ExpressionException("Invalid type\n");
            symTable.Update(name,v);
            state.setSymTable(symTable);
        }
        return null;
    }

    @Override
    public IDict<String,Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
       types.Update(name,type);
       return types;
    }

    @Override
    public String toString()
    {
        return type+ " "+ name;
    }

}
