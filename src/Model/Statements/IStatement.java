package Model.Statements;

import Model.Containers.IDict;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws EvaluationException,ExpressionException;
    IDict<String,Type> typeCheck(IDict<String,Type> types) throws EvaluationException;

}
