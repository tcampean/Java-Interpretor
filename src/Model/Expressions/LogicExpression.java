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

public class LogicExpression implements Expression {
    Expression e1;
    Expression e2;
    int operation;

    public LogicExpression(String op, Expression exp1, Expression exp2) throws ExpressionException {
        if (op.equals("and") || op.equals("&&"))
            operation = 1;
        else if (op.equals("or") || op.equals("||"))
            operation = 2;
        else
            throw new ExpressionException("Invalid operator!\n");
        e1 = exp1;
        e2 = exp2;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, IHeap<Integer,Value> heap) throws ExpressionException, EvaluationException {
        Value v1, v2;
        v1 = e1.eval(symTable,heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(symTable,heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean nr1, nr2;
                nr1 = i1.getValue();
                nr2 = i2.getValue();
                if (operation == 1)
                    return new BoolValue(nr1 && nr2);
                else if (operation == 2)
                    return new BoolValue(nr1 || nr2);
                else throw new ExpressionException("Invalid boolean operators!");
            } else throw new ExpressionException("Second operand is not boolean");

        } else throw new ExpressionException("First operand is not boolean");
    }

    @Override
    public Type typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type type1,type2;
        type1 = e1.typeCheck(types);
        type2 = e2.typeCheck(types);

        if(type1.equals(new BoolType()))
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new EvaluationException("Second operand is not a boolean!\n");
        else
            throw new EvaluationException("First operand is not a boolean!\n");
    }
    @Override
    public Expression deepCopy() throws ExpressionException {
        String op = "";
        if(operation == 1)
            op = "and";
        if(operation == 2)
            op = "or";

        return new LogicExpression(op, e1, e2);
    }
}
