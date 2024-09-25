import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;

public class IterableRedBlackTree<T extends Comparable<T>>
    extends RedBlackTree<T> implements IterableSortedCollection<T> {

    

    public void setIterationStartPoint(Comparable<T> startPoint) {}

    public Iterator<T> iterator() { return null; }

    private static class RBTIterator<R> implements Iterator<R> {

	public RBTIterator(Node<R> root, Comparable<R> startPoint) {}

	private void buildStackHelper(Node<R> node) {}

	public boolean hasNext() { return false; }

	public R next() { return null; }
    }

}
