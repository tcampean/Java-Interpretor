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
import Model.Value.BoolValue;
import Model.Value.Value;

public class ConditionalAssignment implements IStatement {

    String key;
    Expression e1;
    Expression e2;
    Expression e3;

    public ConditionalAssignment(String k, Expression e1,Expression e2, Expression e3)
    {
        key = k;
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExpressionException, EvaluationException {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(symTable.IsDefined(key)) {
            if (!e1.eval(symTable, heap).getType().equals(new BoolType()))
                throw new EvaluationException("Invalid condition!\n");
            else {
                BoolValue v = (BoolValue) e1.eval(symTable, heap);
                if (v.getValue()) {
                    Value val = e2.eval(symTable, heap);
                    Type keyType = (symTable.Find(key)).getType();
                    if (val.getType().equals(keyType))
                        symTable.Update(key, val);
                    else throw new ExpressionException("Type of variable and expression do not match!\n");

                } else {
                    Value val = e3.eval(symTable, heap);
                    Type keyType = (symTable.Find(key)).getType();
                    if (val.getType().equals(keyType))
                        symTable.Update(key, val);
                    else throw new ExpressionException("Type of variable and expression do not match!\n");
                }
            }
        }else throw new EvaluationException("Variable is not defined!");
        return null;
    }

    @Override
    public IDict<String,Type> typeCheck(IDict<String,Type> types) throws EvaluationException
    {
        Type typeVar = types.Find(key);
        Type typeExp = e2.typeCheck(types);
        Type typeExp2 = e3.typeCheck(types);
        if(typeVar.equals(typeExp) && typeVar.equals(typeExp2))
            return types;
        else throw new EvaluationException("Types do not match!\n");
    }

    @Override
    public String toString()
    {
        return key +" = "+ "( " + e1.toString() + " ) ? " + e2.toString() +":" + e3.toString();
    }

}
