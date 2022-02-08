package Mechanism.Semaphore;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableModelSemaphore {

    private  SimpleIntegerProperty firstCol = new SimpleIntegerProperty();
    private  SimpleIntegerProperty secondCol = new SimpleIntegerProperty();
    private SimpleStringProperty thirdCol = new SimpleStringProperty("");
    public TableModelSemaphore(Integer first, Integer second, String third)
    {
        this.setFirstCol(first);
        this.setSecondCol(second);
        this.setThirdCol(third);
    }

    public Integer getFirstCol()
    {
        return firstCol.get();
    }

    public void setFirstCol(Integer newValue)
    {
        firstCol.set(newValue);
    }

    public Integer getSecondCol()
    {
        return secondCol.get();
    }

    public void setSecondCol(Integer newValue)
    {
        secondCol.set(newValue);
    }

    public void setThirdCol(String newValue) { thirdCol.set(newValue);}
}
