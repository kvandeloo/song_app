// == CS400 Summer 2024 File Header Information ==
// Name: Kathryn Cole
// Email: kcole9@wisc.edu
// Lecturer: Jiazhen Zhou
// Notes to Grader: Changed "Spring" to "Summer" in header

//package searchtrees;

public interface SortedCollectionInterface<T extends Comparable<T>> {

    public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

    public boolean contains(Comparable<T> data);

    public int size();

    public boolean isEmpty();

    public void clear();
    
}
