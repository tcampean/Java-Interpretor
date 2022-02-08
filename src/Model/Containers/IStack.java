package Model.Containers;

import Model.Exceptions.StackException;

import java.util.Stack;

public interface IStack<Elem> {
    Elem Pop() throws StackException;
    void Push(Elem e);
    boolean Empty();
    public Stack<Elem> getContents();


}
