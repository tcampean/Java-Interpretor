package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Containers.IStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.Expressions.NegationExpression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class RepeatUntil implements IStatement{

    IStatement s;
    Expression e;

    public RepeatUntil(IStatement s1, Expression e1)
    {
        s = s1;
        e = e1;
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException
    {
        IStack<IStatement> stack = state.getExeStack();
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        Value val = e.eval(symTable,heap);
        if(!val.getType().equals(new BoolType()))
        {
            throw new EvaluationException("Invalid type!\n");
        }else
        {
            BoolValue newVal = (BoolValue) val;
            if(!newVal.getValue())
            {
                Expression negatedExpression = new NegationExpression(e);
                WhileStatement newWhile = new WhileStatement(negatedExpression,s);
                stack.Push(newWhile);
            }
            stack.Push(s);
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {

        Type typeExp = e.typeCheck(types);
        if(typeExp.equals(new BoolType())) {
            s.typeCheck(types);
            return types;
        }
        else throw new EvaluationException("Types do not match!\n");
    }

    @Override
    public String toString()
    {
        return "repeat(" + s.toString() +") until(" + e.toString()+")";
    }

}
