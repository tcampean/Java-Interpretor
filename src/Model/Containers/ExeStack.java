package Model.Containers;

import Model.Exceptions.StackException;
import Model.Statements.CompoundStatement;
import Model.Statements.IStatement;

import java.util.Stack;
public class ExeStack<Elem> implements IStack<Elem> {
    Stack<Elem> stack;
    public ExeStack()
    {
        stack = new Stack<Elem>();
    }

    @Override
    public Elem Pop() throws StackException
    {
        if(stack.isEmpty())
            throw new StackException("Execution stack is empty");
        return stack.pop();
    }

    @Override
    public void Push(Elem e)
    {
        stack.push(e);
    }

    @Override
    public boolean Empty()
    {
        return stack.isEmpty();
    }

    public String Traversal(IStatement statement)
    {
        if(statement instanceof CompoundStatement)
        {
            String result = "";
            CompoundStatement newst = (CompoundStatement) statement;
            IStatement left =  newst.getS1();
            IStatement right = newst.getS2();
            if(left instanceof CompoundStatement)
            {
                result += Traversal(left);

            }
            else
            {
                result += left.toString() + "\n";
            }

            if(right instanceof CompoundStatement)
            {
                result += Traversal(right);
            }
            else
            {
                result += right.toString() + "\n";
            }
            return result;
        }
        else
            return statement.toString();

    }

    @Override
    public String toString()
    {
        StringBuilder resultString = new StringBuilder();
        Stack<Elem> printStack = new Stack<>();
        printStack.addAll(this.stack);

        for(Elem e:printStack)
        {
            if(e instanceof IStatement)
                resultString.append(Traversal((IStatement) e ));
            else
                resultString.append(e.toString());
        }
    return resultString.toString();
    }

    @Override
    public Stack<Elem> getContents() {
        return this.stack;
    }


}
