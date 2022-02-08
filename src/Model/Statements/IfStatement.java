package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Containers.IList;
import Model.Containers.IStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStatement implements IStatement{
    Expression e;
    IStatement thenS;
    IStatement elseS;

    public IfStatement(Expression exp, IStatement then,IStatement el)
    {
        e = exp;
        thenS = then;
        elseS = el;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, EvaluationException
    {
        IStack<IStatement> stack = state.getExeStack();
        IDict<String,Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        if(!e.eval(symTable,heap).getType().equals(new BoolType()))
            throw new EvaluationException("Invalid condition!\n");
        else{
            BoolValue v = (BoolValue) e.eval(symTable,heap);
            if(v.getValue())
                stack.Push(thenS);
            else
                stack.Push(elseS);
            state.setExeStack(stack);


        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeExp = e.typeCheck(types);
        if(typeExp.equals(new BoolType()))
        {
            thenS.typeCheck(types);
            elseS.typeCheck(types);
            return types;
        }
        else throw new EvaluationException("Types do not match!\n");
    }

    @Override
    public String toString()
    {
        return "(IF("+ e.toString()+") THEN(" +thenS.toString()  +")ELSE("+elseS.toString()+"))";
    }
}
