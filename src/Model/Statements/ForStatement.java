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
import Model.Value.Value;

public class ForStatement implements IStatement
{
    IStatement declaration;
    Expression finishCondition;
    IStatement steps;
    IStatement toBeExecuted;

    public ForStatement(IStatement d, Expression f, IStatement s, IStatement t)
    {
        declaration = d;
        finishCondition = f;
        steps = s;
        toBeExecuted = t;
    }

    @Override
    public ProgramState execute(ProgramState state) throws EvaluationException, ExpressionException
    {
        IStack<IStatement> stack = state.getExeStack();
        AssignmentStatement assignation = (AssignmentStatement) declaration;
        WhileStatement whileLoop = new WhileStatement(finishCondition,new CompoundStatement(toBeExecuted,steps));
        stack.Push(new CompoundStatement(assignation,whileLoop));
        state.setExeStack(stack);
        return null;

    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {

        declaration.typeCheck(types);
        steps.typeCheck(types);
        toBeExecuted.typeCheck(types);
        Type typeExp = finishCondition.typeCheck(types);
        if(typeExp.equals(new BoolType())) {
            return types;
        }
        else throw new EvaluationException("Invalid statement!");

    }

    @Override
    public String toString()
    {
        return "(for(" + declaration.toString()+";"+finishCondition.toString()+";"+steps.toString()+")" + toBeExecuted.toString()+")";
    }






}
