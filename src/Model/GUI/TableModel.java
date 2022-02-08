package Model.GUI;

import javafx.beans.property.SimpleStringProperty;

public class TableModel {

    private  SimpleStringProperty firstCol = new SimpleStringProperty("");
    private  SimpleStringProperty secondCol = new SimpleStringProperty("");

    public TableModel(String first, String second)
    {
        this.setFirstCol(first);
        this.setSecondCol(second);
    }

    public String getFirstCol()
    {
        return firstCol.get();
    }

    public void setFirstCol(String newValue)
    {
        firstCol.set(newValue);
    }

    public String getSecondCol()
    {
        return secondCol.get();
    }

    public void setSecondCol(String newValue)
    {
        secondCol.set(newValue);
    }
}
