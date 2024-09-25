// == CS400 Summer 2024 File Header Information ==
// Name: Kathryn Cole
// Email: kcole9@wisc.edu
// Lecturer: Jiazhen Zhou
// Notes to Grader: Changed "Spring" to "Summer" in header

//package searchtrees;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Binary Search Tree implementation with a Node inner class for representing the nodes of the tree.
 * We will turn this Binary Search Tree into a self-balancing tree as part of project 1 by modifying
 * its insert functionality. In the first part of project 1, we will start this process by implementing tree
 * rotations.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

  /** This class represents a node holding a single value within a binary tree. */
  protected static class Node<T> {
    public T data;

    // up stores a reference to the node's parent
    public Node<T> up;

    // The down array stores references to the node's children:
    // - down[0] is the left child reference of the node,
    // - down[1] is the right child reference of the node.
    // The @SupressWarning("unchecked") annotation is used to supress an unchecked
    // cast warning. Java only allows us to instantiate arrays without generic
    // type parameters, so we use this cast here to avoid future casts of the
    // node type's data field.
    @SuppressWarnings("unchecked")
    public Node<T>[] down = (Node<T>[]) new Node[2];

    public Node(T data) {
      this.data = data;
    }

    /**
     * @return true when this node has a parent and is the right child of that parent, otherwise
     *     return false
     */
    public boolean isRightChild() {
      return this.up != null && this.up.down[1] == this;
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree

  /**
   * Inserts a new data value into the tree. This tree will not hold null references, nor duplicate
   * data values.
   *
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided data argument is null
   */
  public boolean insert(T data) throws NullPointerException {
    if (data == null)
      throw new NullPointerException("Cannot insert data value null into the tree.");
    return this.insertHelper(new Node<>(data));
  }

  /**
   * Performs a basic insertion into a binary search tree: adding the new node in a leaf position
   * within the tree. After this insertion, the tree is still a BST, but no attempt is made to
   * restructure or balance the tree.
   *
   * @param newNode the new node to be inserted
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided node is null
   */
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
        if (compare == 0) {
          return false;
        } else if (compare < 0) {
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
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a left child of the provided parent, this method will perform a right rotation. When the
   * provided child is a right child of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will throw
   * an IllegalArgumentException.
   *
   * @param child is the node being rotated from child to parent position (between these two node
   *     arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *     arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *     initially (pre-rotation) related that way
   */
  protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // TODO: Implement this method.

    //Since rotate operations are symmetrical, creating variables for left and right
    //allows us to write the actual rotate code only once instead of using copy and paste 
    int leftRotate0rightRotate1 = 0;
    int leftRotate1rightRotate0 = 1;
    
    // if parent argument is not parent node of child argument, throw exception
    if (child.up != parent) {
	throw new IllegalArgumentException("first argument must be child node of second argument");
    }
    //if child is a right child, rotate left
    else if (parent.down[1] == child) {
	leftRotate0rightRotate1 = 0;
	leftRotate1rightRotate0 = 1;
    }
    //if child is a left child, rotate right 
    else {
	 leftRotate0rightRotate1 = 1;
         leftRotate1rightRotate0 = 0;
    }
    
    //update up,down[0],down[1] field of each relevant node
    //1. detach right child's left subtree (or left child's right subtree)
    Node<T> orphan = child.down[leftRotate0rightRotate1];

    //2. consider left child to be the new parent
    //update the root or grandparent fields
    //if old parent was the root, make the new parent the root; otherwise adjust grandparent.down[leftRotate0rightRotate1] pointer below
    if (parent.up == null){
	   this.root = child;
    }
    //if old parent was not the root, set the old grandparent to point to the new parent (old child)
    else if (parent == parent.up.down[leftRotate0rightRotate1]){
	    parent.up.down[leftRotate0rightRotate1] = child; 
    }
    else {
	    parent.up.down[leftRotate1rightRotate0] = child;
    }
		
    //update the child fields to link to old grandparent
    //old child is now the parent, so its up field must point to the old grandparent
    child.up = parent.up;

    //3. attach old parent onto left (or right) of new parent (old child)
    child.down[leftRotate0rightRotate1] = parent;
    //no action is needed for child.down[leftrotate1rightrotate0], becuase it keeps pointing to the same thing
    
    //old parent node is now the child, so update its up field to point to the old child
    parent.up = child;

    //4. attach new parent's old left subtree as right subtree of old parent (or new parent's old right subtree as left subtree of old parent)
    parent.down[leftRotate1rightRotate0] = orphan;
    //no action is needed for parent.down[leftRotate0rightRotate1], because it keeps pointing to the same thing
	
    //point the old right child's left child (or old left child's right child)
    //up to the new parent (parent was already updated to become the new child) 
    if (parent.down[leftRotate1rightRotate0] != null){
	    parent.down[leftRotate1rightRotate0].up = parent;
    }

    return;
  }

  /**
   * Get the size of the tree (its number of nodes).
   *
   * @return the number of nodes in the tree
   */
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   *
   * @return true of this.size() returns 0, false if this.size() != 0
   */
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Checks whether the tree contains the value *data*.
   *
   * @param data a comparable for the data value to check for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  public boolean contains(Comparable<T> data) {
    // null references will not be stored within this tree
    if (data == null) {
      throw new NullPointerException("This tree cannot store null references.");
    } else {
      Node<T> nodeWithData = this.findNode(data);
      // return false if the node is null, true otherwise
      return (nodeWithData != null);
    }
  }

  /** Removes all keys from the tree. */
  public void clear() {
    this.root = null;
    this.size = 0;
  }

  /**
   * Helper method that will return the node in the tree that contains a specific key. Returns null
   * if there is no node that contains the key.
   *
   * @param data the data value for which we want to find the node that contains it
   * @return the node that contains the data value or null if there is no such node
   */
  protected Node<T> findNode(Comparable<T> data) {
    Node<T> current = this.root;
    while (current != null) {
      int compare = data.compareTo(current.data);
      if (compare == 0) {
        // we found the value
        return current;
      } else if (compare < 0) {
        if (current.down[0] == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the left subtree
        current = current.down[0];
      } else {
        if (current.down[1] == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the right subtree
        current = current.down[1];
      }
    }
    return null;
  }

  /**
   * This method performs an inorder traversal of the tree. The string representations of each data
   * value within this tree are assembled into a comma separated string within brackets (similar to
   * many implementations of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
   *
   * @return string containing the ordered values of this tree (in-order traversal)
   */
  public String toInOrderString() {
    // generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    int nodesVisited = 0;
    if (this.root != null) {
      Stack<Node<T>> nodeStack = new Stack<>();
      Node<T> current = this.root;
      while (!nodeStack.isEmpty() || current != null) {
        if (current == null) {
          Node<T> popped = nodeStack.pop();
          if (++nodesVisited > this.size()) {
            throw new RuntimeException(
                "visited more nodes during traversal than there are keys in the tree; make sure"
                    + " there is no loop in the tree structure");
          }
          sb.append(popped.data.toString());
          if (!nodeStack.isEmpty() || popped.down[1] != null) sb.append(", ");
          current = popped.down[1];
        } else {
          nodeStack.add(current);
          current = current.down[0];
        }
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection). This method will be helpful as a
   * helper for the debugging and testing of your rotation implementation.
   *
   * @return string containing the values of this tree in level order
   */
  public String toLevelOrderString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    int nodesVisited = 0;
    if (this.root != null) {
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this.root);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (++nodesVisited > this.size()) {
          throw new RuntimeException(
              "visited more nodes during traversal than there are keys in the tree; make sure there"
                  + " is no loop in the tree structure");
        }
        if (next.down[0] != null) q.add(next.down[0]);
        if (next.down[1] != null) q.add(next.down[1]);
        sb.append(next.data.toString());
        if (!q.isEmpty()) sb.append(", ");
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  public String toString() {
    return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
  }

  // Implement at least 4 tests using the methods below. You can
  // use the examples from our lectures to test with.
  // Make sure to include rotations at the root of a tree in your test cases.
  // Give each of the methods a meaningful header comment that describes what is being
  // tested and make sure your tests have inline comments that help with reading your test code.
  // If you'd like to add additional tests, then name those methods similar to the ones given below.
  // Eg: public static boolean test5() {}
  // Do not change the method name or return type of the existing tests.
  // You can run your tests through the static main method of this class.

  //helper method to create binary search tree we can use for testing
  public static BinarySearchTree<Integer> buildBST(Integer[] valuesToInsert) {
    //create empty tree
    BinarySearchTree<Integer> outputTree = new BinarySearchTree<Integer>();
    //insert each value in the input list
    for (int i=0; i<valuesToInsert.length; i++){
    	Integer newNode = valuesToInsert[i];
	outputTree.insert(newNode);
    }
	  return outputTree;
  }
  
  //helper method to compare results of expected and actual trees
  public static boolean treeMatch (BinarySearchTree<Integer> expectedTree, BinarySearchTree<Integer> actualTree) { 
    if (actualTree.toLevelOrderString().equals(expectedTree.toLevelOrderString())
	&& actualTree.toInOrderString().equals(expectedTree.toInOrderString())){
        System.out.println("MATCH! Expected and actual trees are the same");
	return true;
    }
    else {
        System.out.println("NO MATCH between expected and actual");
        return false;
    }
  }
  
  public static boolean test1() {
    // TODO: Implement this test.
    //return true or false depending on whether the actual result matches the expected result or not 
    //test right rotate, not root node
    System.out.println("Test right rotate, not around root node.");
    //make expected tree
    Integer[] insertArrayExpected = {12,4,16,2,8,14,1,6,10};
    BinarySearchTree<Integer> expectedTree = buildBST(insertArrayExpected);
    //make actual tree
    Integer[] insertArrayActual = {12,8,16,4,10,14,2,6}; 
    BinarySearchTree<Integer> actualTree = buildBST(insertArrayActual); 
    //insert under left-left grandchild
    actualTree.insert(1);
    //print actual tree
    System.out.println("actual before rotate: " + actualTree.toLevelOrderString());
    //rotate so 4 becomes the parent of 8
    Integer childValue = Integer.valueOf(4);
    Integer parentValue = Integer.valueOf(8);
    actualTree.rotate(actualTree.findNode(childValue),actualTree.findNode(parentValue));
    //print actual tree
    System.out.println("actual after rotate: " + actualTree.toLevelOrderString());
    //print expected tree
    System.out.println("expect after rotate: " + expectedTree.toLevelOrderString());
    //check whether actual == expected
    return treeMatch(expectedTree,actualTree);
  }

  public static boolean test2() {
    // TODO: Implement this test.
    //return true or false depending on whether the actual result matches the expected result or not 
    //left rotate, root node
    System.out.println("Test left rotate where parent is root node.");
    //make expected tree
    Integer[] insertArrayExpected = {65,43,87,21,51,73};
    BinarySearchTree<Integer> expectedTree = buildBST(insertArrayExpected);
    //make actual tree
    Integer[] insertArrayActual = {43,21,65,51,87};
    BinarySearchTree<Integer> actualTree = buildBST(insertArrayActual);
    //insert under right-right grandchild
    actualTree.insert(73);
    //print actual tree
    System.out.println("actual before rotate: " + actualTree.toLevelOrderString());
    //rotate so 65 becomes the parent of 43
    Integer childValue = Integer.valueOf(65);
    Integer parentValue = Integer.valueOf(43);
    actualTree.rotate(actualTree.findNode(childValue),actualTree.findNode(parentValue));
    //print actual tree
    System.out.println("actual after rotate: " + actualTree.toLevelOrderString());
    //print expected tree
    System.out.println("expect after rotate: " + expectedTree.toLevelOrderString());
    //check whether actual == expected
    return treeMatch(expectedTree,actualTree);
  }

  public static boolean test3() {
    // TODO: Implement this test.
    //return true or false depending on whether the actual result matches the expected result or not 
    //left-right rotate
    System.out.println("Test left-right rotate, not around root node.");
    //make expected tree
    Integer[] insertArrayExpected = {12,6,16,4,8,14,2,5,10};
    BinarySearchTree<Integer> expectedTree = buildBST(insertArrayExpected);
    //make intermediate tree
    Integer[] insertArrayIntermediateExpected = {12,8,16,6,10,14,4,2,5};
    BinarySearchTree<Integer> intermediateExpectedTree = buildBST(insertArrayIntermediateExpected);
    //make actual tree
    Integer[] insertArrayActual = {12,8,16,4,10,14,2,6};
    BinarySearchTree<Integer> actualTree = buildBST(insertArrayActual);
    //insert under left-left-right great-grandchild
    actualTree.insert(5);
    //print actual tree
    System.out.println("actual before rotate: " + actualTree.toLevelOrderString());

    //rotate left
    Integer childValue = Integer.valueOf(6);
    Integer parentValue = Integer.valueOf(4);
    actualTree.rotate(actualTree.findNode(childValue),actualTree.findNode(parentValue));
    //print actual tree (intermediate)
    System.out.println("actual after first rotate : " + actualTree.toLevelOrderString());
    //print expected intermediate tree
    System.out.println("expected intermediate tree: " + intermediateExpectedTree.toLevelOrderString());

    //rotate right
    Integer childValue2 = Integer.valueOf(6);
    Integer parentValue2 = Integer.valueOf(8);
    actualTree.rotate(actualTree.findNode(childValue2),actualTree.findNode(parentValue2));
    //print actual tree
    System.out.println("actual after rotate: " + actualTree.toLevelOrderString());
    //print expected tree
    System.out.println("expect after rotate: " + expectedTree.toLevelOrderString());
    //check whether actual == expected
    return treeMatch(expectedTree,actualTree);
  }

  public static boolean test4() {
    // TODO: Implement this test.
    //return true or false depending on whether the actual result matches the expected result or not 
    //right-left rotate
    System.out.println("Test right-left rotate, not around root node.");
    //make expected tree
    Integer[] insertArrayExpected = {45,20,80,10,35,70,90,30,40,60,77,99};
    BinarySearchTree<Integer> expectedTree = buildBST(insertArrayExpected);
    //make intermediate tree
    Integer[] insertArrayIntermediateExpected = {45,20,70,10,35,60,80,30,40,77,90,99};
    BinarySearchTree<Integer> intermediateExpectedTree = buildBST(insertArrayIntermediateExpected);
    //make actual tree
    Integer[] insertArrayActual = {45,20,70,10,35,60,90,30,40,80,99};
    BinarySearchTree<Integer> actualTree = buildBST(insertArrayActual);
    //insert under right-right-left great-grandchild
    actualTree.insert(77);
    //print actual tree
    System.out.println("actual before rotate: " + actualTree.toLevelOrderString());
    //rotate right
    Integer childValue = Integer.valueOf(80);
    Integer parentValue = Integer.valueOf(90);
    actualTree.rotate(actualTree.findNode(childValue),actualTree.findNode(parentValue));
    //print actual tree (intermediate)
    System.out.println("actual after first rotate : " + actualTree.toLevelOrderString());
    //print expected intermediate tree
    System.out.println("expected intermediate tree: " + intermediateExpectedTree.toLevelOrderString());
    
    //rotate left
    Integer childValue2 = Integer.valueOf(80);
    Integer parentValue2 = Integer.valueOf(70);
    actualTree.rotate(actualTree.findNode(childValue2),actualTree.findNode(parentValue2));
    //print actual tree
    System.out.println("actual after rotate: " + actualTree.toLevelOrderString());
    //print expected tree
    System.out.println("expect after rotate: " + expectedTree.toLevelOrderString());
    //check whether actual == expected
    return treeMatch(expectedTree,actualTree);
  }

  public static boolean test5(){
  //return true or false depending on whether the exception is thrown as expected
  //test that exception is thrown when first argument is not child of second argument
  System.out.println("Test whether invalid argument exception is thrown.");
  Integer[] insertArrayActual = {45,20,70,10,35,60,90,30,40,80,99,77};
  BinarySearchTree<Integer> actualTree = buildBST(insertArrayActual);
  try{
      actualTree.rotate(actualTree.findNode(Integer.valueOf(90)),actualTree.findNode(Integer.valueOf(45)));
   } catch (IllegalArgumentException e) {
      e.printStackTrace();
      System.out.println("Exception thrown as expected");
      return true;
   }  
  return false; //if rotate was executed successfully, test does not pass
  }
  /**
   * Main method to run tests. If you'd like to add additional test methods, add a line for each of
   * them.
   *
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("Test 1 passed: " + test1());
    System.out.println("Test 2 passed: " + test2());
    System.out.println("Test 3 passed: " + test3());
    System.out.println("Test 4 passed: " + test4());
    System.out.println("Test 5 passed: " + test5());
  }
}
