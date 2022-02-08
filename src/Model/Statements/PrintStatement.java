package Model.Statements;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Containers.IList;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStatement implements IStatement{
    Expression e;
    public PrintStatement(Expression exp)
    {
        e = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, EvaluationException
    {
        IList<Value> out = state.getOut();
        IDict<String,Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heap = state.getHeap();
        out.Add(e.eval(symTable,heap));
        state.setOut(out);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        e.typeCheck(types);
        return types;
    }

    @Override
    public String toString()
    {
        return "print("+e.toString()+")";
    }
}
