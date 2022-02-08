package Model.Expressions;

import Model.Containers.IDict;
import Model.Containers.IHeap;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Type.Type;
import Model.Value.Value;

public interface Expression {
    Value eval(IDict<String,Value> symTable, IHeap<Integer,Value> heap) throws ExpressionException, EvaluationException;
    Expression deepCopy() throws ExpressionException;
    Type typeCheck(IDict<String,Type> types) throws EvaluationException;
}
