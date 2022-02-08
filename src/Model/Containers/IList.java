package Model.Containers;

public interface IList<Elem> {
    void Add(Elem e);
    Elem Get(int index);
    int size();

}
