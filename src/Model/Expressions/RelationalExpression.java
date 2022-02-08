package Model.Expressions;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelationalExpression implements Expression{
    Expression exp1, exp2;
    String operator;

    public RelationalExpression(Expression exp1, Expression exp2,String op )
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = op;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, IHeap<Integer,Value> heap) throws ExpressionException, EvaluationException
    {
        Value val1,val2;
        val1 = this.exp1.eval(symTable,heap);
        if(val1.getType().equals(new IntType()))
        {
            val2 = this.exp2.eval(symTable,heap);
            if(val2.getType().equals(new IntType()))
            {
                IntValue int1 = (IntValue) val1;
                IntValue int2 = (IntValue) val2;
                int a1,a2;
                a1 = int1.getValue();
                a2 = int2.getValue();
                if(operator.equals("<"))
                    return new BoolValue(a1 < a2);
                if(operator.equals("<="))
                    return new BoolValue(a1 <= a2);
                if(operator.equals("=="))
                    return new BoolValue(a1 == a2);
                if(operator.equals(">"))
                    return new BoolValue(a1 > a2);
                if(operator.equals(">="))
                    return new BoolValue(a1 >= a2);
                if(operator.equals("!="))
                    return new BoolValue(a1 != a2);

            }
            else throw new EvaluationException("Second operand is not an int!\n");
        }
        else throw new EvaluationException("First operand is not an int!\n");
        return null;

    }

    @Override
    public Type typeCheck(IDict<String,Type> types) throws EvaluationException {
        Type type1, type2;
        type1 = exp1.typeCheck(types);
        type2 = exp2.typeCheck(types);

        if (type1.equals(new IntType()))
            if (type2.equals(new IntType()))
                return new BoolType();
            else
                throw new EvaluationException("Second operand is not an integer!\n");
        else
            throw new EvaluationException("First operand is not an integer!\n");
    }

    @Override
    public Expression deepCopy()
    {
        return new RelationalExpression(exp1,exp2,operator);
    }
    @Override
    public String toString()
    {
        return this.exp1.toString() + " " + operator.toString() + " " +this.exp2.toString();
    }
}
