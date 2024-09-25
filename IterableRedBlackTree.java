// == CS400 Summer 2024 File Header Information ==
// Name: Kathryn Cole
// Email: kcole9@wisc.edu
// Lecturer: Jiazhen Zhou
// Notes to Grader: Changed "Spring" to "Summer" in header
import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;

public class IterableRedBlackTree<T extends Comparable<T>>
    extends RedBlackTree<T> implements IterableSortedCollection<T> {
	
    private Comparable<T> iterationStartPoint; //???

    /**
     * Stores a copy of the argument passed to it within a private instance field.
     * @param startPoint a Comparable<T> where you enter the tree. Can be null. 
     * @return
     */ 
    public void setIterationStartPoint(Comparable<T> startPoint) {
	//set default start point to value that is earlier/smaller than any value in any possible IterableRedBlackTree
	//initialize using anonymous class or labmda expression
	//define this object to contain a compareTo method that always returns -1
	//returns -1 even if the value passed in is null
	    if (startPoint != null) {
		    this.iterationStartPoint = startPoint;
	    }
	    else {
		    this.iterationStartPoint = new Comparable<T>(){	    
    	        @Override
		public int compareTo(T startPoint){
				//System.out.println("setting startPoint to -1");
				return -1;
			}
		};
	    }
    }
  
    /**
   * Overrides the insertHelper method to allow 2 or more nodes with the same data to be present in the tree. 
   * @param newNode the new node to be inserted
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided node is null
   */
  @Override
  protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
    if (newNode == null) throw new NullPointerException("new node cannot be null");

    if (this.root == null) {
      // add first node to an empty tree
      root = newNode;
      size++;
      return true;
    } else {
      // insert into subtree
      Node<T> current = this.root;
      while (true) {
        int compare = newNode.data.compareTo(current.data);
	//duplicated value
        // if (compare == 0) {
        //  return false;
        //} else if (compare < 0) {
        if (compare <= 0 ) {  
	  // insert in left subtree
          if (current.down[0] == null) {
            // empty space to insert into
            current.down[0] = newNode;
            newNode.up = current;
            this.size++;
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.down[0];
          }
        } else {
          // insert in right subtree
          if (current.down[1] == null) {
            // empty space to insert into
            current.down[1] = newNode;
            newNode.up = current;
            this.size++;
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.down[1];
          }
        }
       }
     }
   }

    /**
     * Instantiates and returns a new RBTIterator object.
     * @return RBTIterator object
     * */
    public Iterator<T> iterator() {
	//set iterationi start point if it hasn't been set yet
        if (this.iterationStartPoint == null){
		this.setIterationStartPoint(null);
	}
	return new RBTIterator(this.root, this.iterationStartPoint);
    }

    private static class RBTIterator<R> implements Iterator<R> {
    //private static class RBTIterator<R extends Comparable<R>> implements Iterator<R> {

	private Comparable<R> startPoint; 
	private Stack<Node<R>> discoveredNodes; 
	
	//constructor
	public RBTIterator(Node<R> root, Comparable<R> startPoint) {
		//store the specified start point in a private instance field within the iterator
		this.startPoint = startPoint;
		//create and store an empty Stack of Node<R> references within a private instance field 
		//to help track the parts of the tree which still need to be iterated through
		this.discoveredNodes = new Stack<Node<R>>();
		//call the buildStackHelper method, and pass in the root of the tree being iterated through
		buildStackHelper(root);	
		//System.out.println("stack: " + this.discoveredNodes.toString());
	}

	/*
	 * (1) Find the next data value stored in a (sub)tree >= startPoint, 
	 * and (2) build up the stack of ancestor nodes >= startPoint so those nodes' data
	 * can be visited later.
	 * */
	private void buildStackHelper(Node<R> node) {
		//cast as RBTNode so we can use RBTNode methods on it
		RBTNode<R> rbtNode = (RBTNode<R>) node;
		//Comparable<T> rbtStartPoint = (Comparable<T>) this.startPoint;
		//set iteration start point if it hasn't been set yet
        	//if (this.startPoint == null){
		//	this.setIterationStartPoint(null);
		//}
		//base case
		if (rbtNode == null) {
			return;
		}
		//first recursive case
		//when data in the node argument < the start value
		else if (this.startPoint.compareTo(rbtNode.data) > 0) {
		//else if (rbtNode.data < rbtStartPoint){
			//call this method on the node's right subtree, where values > startPoint can be found
			buildStackHelper(rbtNode.getDownRight());
		}
		//second recursive case	
		//when data in the node argument >= the start value
		else if (this.startPoint.compareTo(rbtNode.data) <= 0) {
		//else if (rbtNode.data >= rbtStartPoint) {
			//push node on the stack so its data can be visited later
			this.discoveredNodes.push(rbtNode);
			//System.out.println("added to stack: " + rbtNode.data);
			//call this method on the node's left subtree, where values <= startPoint can be found
			buildStackHelper(rbtNode.getDownLeft());
		}
	}

	/*
	 * Check whether the RBTIterator has a subsequent element. 
	 * @return true if there is another element, otherwise false. 
	 */
	public boolean hasNext() {
		if (!this.discoveredNodes.empty()){
			return true;
		}
		return false;
	}

	/*
	 * Get the next node to be traversed in the Stack of discoveredNodes. 
	 * @return next Node<R> that needs to be traversed.  
	 * @throws NoSuchElementException if next() is called with an empty stack
	 */
	public R next() throws NoSuchElementException { 
		//if stack is empty
		if (this.discoveredNodes.empty()){
			throw new NoSuchElementException("next element does not exist");
		} else {
			RBTNode<R> currentNode = (RBTNode<R>) this.discoveredNodes.pop();
			//if node has right child, go down that path
			if (currentNode.getDownRight() != null) {
				buildStackHelper(currentNode.getDownRight());
				//System.out.println("next: " + currentNode.data.toString());
				return currentNode.data;
			}
			//if node has no children, pop the next item from the stack
			else {
				//System.out.println("next: " + currentNode.data.toString());
				return currentNode.data;
			}
		}
	}
    }
}
