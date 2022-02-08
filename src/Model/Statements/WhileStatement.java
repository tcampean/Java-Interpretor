package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Containers.IStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStatement implements IStatement{
    Expression exp;
    IStatement statement;

    public WhileStatement(Expression exp,IStatement statement)
    {
        this.exp = exp;
        this.statement = statement;

    }

    @Override
    public String toString()
    {
        return "(while(" +exp.toString()+") " + statement.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException
    {
        IStack<IStatement> stack = state.getExeStack();
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        Value val = exp.eval(symTable,heap);
        if(!val.getType().equals(new BoolType()))
        {
            throw new EvaluationException("Invalid type!\n");
        }
        else
        {
            BoolValue newVal = (BoolValue) val;
            if(newVal.getValue())
            {
                WhileStatement newWhile = new WhileStatement(exp,statement);
                stack.Push(newWhile);
                stack.Push(statement);
            }
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {

        Type typeExp = exp.typeCheck(types);
        if(typeExp.equals(new BoolType())) {
            statement.typeCheck(types);
            return types;
        }
        else throw new EvaluationException("Types do not match!\n");
    }
}
