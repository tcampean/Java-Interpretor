package Model.Expressions;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;


public class ArithmeticExpression implements Expression{
    Expression e1;
    Expression e2;
    int operation;

    public ArithmeticExpression(Character operand, Expression first, Expression second) throws ExpressionException
    {
        if(operand.equals('+'))
            operation = 1;
        else if(operand.equals('-'))
            operation = 2;
        else if(operand.equals('*'))
            operation = 3;
        else if(operand.equals('/'))
            operation = 4;
        else throw new ExpressionException("Invalid operation!");
        e1 = first;
        e2 = second;
    }

    @Override
    public Value eval(IDict<String,Value> symTable, IHeap<Integer,Value> heap) throws ExpressionException,EvaluationException
    {
        Value v1,v2;
        v1 = e1.eval(symTable,heap);


        if(v1.getType().equals(new IntType()))
        {
            v2 = e2.eval(symTable,heap);
            if(v2.getType().equals(new IntType()))
            {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int nr1, nr2;
                nr1 = i1.getValue();
                nr2 = i2.getValue();
                if(operation == 1)
                    return new IntValue(nr1 + nr2);
                if(operation == 2)
                    return new IntValue(nr1-nr2);
                if(operation == 3)
                    return new IntValue(nr1*nr2);
                if(operation == 4)
                {   if(nr2 == 0)
                    throw new EvaluationException("Division by zero\n");
                    return new IntValue(nr1/nr2);
                }
                throw new ExpressionException("Invalid arithmetic operators!\n");

            }else throw new ExpressionException("Second operand is not an integer!\n");
        }else throw new ExpressionException("First operand is not an integer\n");
    }

    @Override
    public Type typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type type1,type2;
        type1 = e1.typeCheck(types);
        type2 = e2.typeCheck(types);

        if(type1.equals(new IntType()))
            if(type2.equals(new IntType()))
                return new IntType();
            else
                throw new EvaluationException("Second operand is not an integer!\n");
        else
            throw new EvaluationException("First operand is not an integer!\n");
    }

    @Override
    public String toString()
    {
        String op = "";
        if(operation == 1)
            op = "+";
        if(operation == 2)
            op = "-";
        if(operation == 3)
            op = "*";
        if(operation == 4)
            op = "/";
        return e1 + op + e2;
    }

    public Expression deepCopy() throws ExpressionException {
        Character op = '+';
        if(operation == 1)
            op = '+';
        if(operation == 2)
            op = '-';
        if(operation == 3)
            op = '*';
        if(operation == 4)
            op = '/';

        return new ArithmeticExpression(op, e1, e2);
    }

}
