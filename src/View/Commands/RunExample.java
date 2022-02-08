package View.Commands;

import Controller.Controller;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StackException;

public class RunExample extends Command {
    private Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep();
        } catch (ExpressionException | EvaluationException | StackException e) {
            System.out.println(e.getMessage());
        }
    }
}