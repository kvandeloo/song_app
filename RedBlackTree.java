// == CS400 Summer 2024 File Header Information ==
// Name: Kathryn Cole
// Email: kcole9@wisc.edu
// Lecturer: Jiazhen Zhou
// Notes to Grader: Changed "Spring" to "Summer" in header

//package searchtrees;

import java.util.LinkedList;
import java.util.Stack;

/**
 *  Red Black Tree implementation with a Node inner class for representing the nodes of the tree.
 */

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> implements SortedCollectionInterface<T> {

  /** This class represents a node holding a single value within a red black tree. 
   * It extends the Node class from BinarySearchTree. */
  protected static class RBTNode<T> extends Node<T> {
      public boolean isBlack = false;
      public RBTNode(T data) { super(data); }
      public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
      public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
      public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
  }

  /*Red Black Tree methods*/

  /** Takes an RBTNode and resolves any red property violations that are introduced by inserting a new node into the Red-Black Tree. 
   * Assumes this method will be called after every insertion so the only node that could cause an imbalance
   * at any given time is the single most recently inserted node. This is not designed to rebalance a red-black 
   * tree that is out of balance in several places at the outset. 
   *
   * May be called recursively
   * Handle Case 1, Case 2, and Case 3
   * Use Rotate Method from BinarySearchTree
   * */
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> rbtNode){
	  
	boolean isInRightSubtree = rbtNode.data.compareTo(this.root.data) > 0; //rbtNode > root
	
	//base case
	if (rbtNode.getUp() == null) {
	      //root node can always be colored black without violating red black tree properties
	      //and it must always be black in the final red black tree
	      rbtNode.isBlack = true; //redundant when called within the insert method, but leaving this here in case we want to use this apart from insert
	      //System.out.println("found root node: " + rbtNode.data.toString() + " isBlack: " + rbtNode.isBlack);
	      return; 
	}
          
	//If current or parent node is black or parent is child of root: No change needed at this node
	else if (rbtNode.isBlack == true || rbtNode.getUp().isBlack == true || rbtNode.getUp().getUp() == null) {
	        //if parent is black, no color change is needed at this node
		//System.out.println(rbtNode.data.toString() + " isBlack: " + rbtNode.isBlack);
		//System.out.println("current or parent node is black, or parent is root. moving to next node: " + rbtNode.getUp().data.toString());
		enforceRBTreePropertiesAfterInsert(rbtNode.getUp());
	}
	//case 1: z is red. p(z) is red. uncle(z) is red. 
	else if (rbtNode.getUp().getUp().getDownLeft() != null 
		  && rbtNode.getUp().getUp().getDownLeft().isBlack == false 
		  && rbtNode.getUp().getUp().getDownRight() != null 
		  && rbtNode.getUp().getUp().getDownRight().isBlack == false) {
	      //System.out.println("--identified case 1--");
	      //Parent and uncle become red. Grandparent becomes black. 
	      //System.out.println("Turning parent and uncle black: " + rbtNode.getUp().getUp().getDownLeft().data.toString() + ", " + rbtNode.getUp().getUp().getDownRight().data.toString());
	      rbtNode.getUp().getUp().getDownLeft().isBlack = true;
	      rbtNode.getUp().getUp().getDownRight().isBlack = true;
	      //System.out.println("Turning grandparent red: " + rbtNode.getUp().getUp().data.toString());
	      rbtNode.getUp().getUp().isBlack = false;
	      //System.out.println("current node " + rbtNode.data.toString()  + " isBlack: " + rbtNode.isBlack);
	      //System.out.println("handled case 1. moving to next node: " + rbtNode.getUp().data.toString());
	      //check whether this caused any consecutive red nodes further up the tree
	      enforceRBTreePropertiesAfterInsert(rbtNode.getUp());  
	}
	//case 2 & 3: z is red. p(z) is red. uncle(z) is black (can be nil node). 
	//case 2: z is inside child (right child in left subtree or left child in right subtree)
	else if ((rbtNode.isRightChild() && !isInRightSubtree) //right child in left subtree
		    || (!rbtNode.isRightChild() && isInRightSubtree) //left child in right subtree
		    ){
		//System.out.println("--identified case 2--");
		//rotate around p(z) to make the offending node a left child
		//System.out.println("Rotating tree: " + this.toLevelOrderString());
		this.rotate(rbtNode,rbtNode.getUp());
		//System.out.println("New tree: " + this.toLevelOrderString());
		//System.out.println(rbtNode.data.toString() + " isBlack: " + rbtNode.isBlack);
		if (!isInRightSubtree) {
			//System.out.println("handled case 2. moving to next node: " + rbtNode.getDownLeft().data.toString());
			//case 2 becomes case 3 (z is now outside child - left left)
			enforceRBTreePropertiesAfterInsert(rbtNode.getDownLeft());
		}
		else { //is in right subtree	
			//System.out.println("handled case 2. moving to next node: " + rbtNode.getDownRight().data.toString());
			//case 2 becomes case 3 (z is now outside child - right right)
			enforceRBTreePropertiesAfterInsert(rbtNode.getDownRight());
		}
	}
	//case 3: z is outside child (left child in left subtree or right child in right subtree)
	else {
		//System.out.println("--identified case 3--");
		//p(z) becomes black
		//System.out.println("Turning parent black: " + rbtNode.getUp().data.toString());
		rbtNode.getUp().isBlack = true;
		//p(p(z)) becomes red
		//System.out.println("Turning grandparent red: " + rbtNode.getUp().getUp().data.toString());
		rbtNode.getUp().getUp().isBlack = false;
		//right rotate around p(p(z))
		//System.out.println("Rotating tree: " + this.toLevelOrderString());
		this.rotate(rbtNode.getUp(),rbtNode.getUp().getUp());
		//System.out.println("New tree: " + this.toLevelOrderString());
		//System.out.println(rbtNode.data.toString() + " isBlack: " + rbtNode.isBlack);
		//System.out.println("handled case 3. moving to next node: " + rbtNode.getUp().data.toString());
		//check whether there are any violations further up the tree --is this needed?
		enforceRBTreePropertiesAfterInsert(rbtNode.getUp());
	
	}


  }

  @Override
  public boolean insert(T data) throws NullPointerException {
    if (data == null)
      throw new NullPointerException("Cannot insert data value null into the tree.");
    RBTNode<T> rbtNode = new RBTNode<>(data);
    //System.out.println("");
    //System.out.println("Inserting node with data: " + data.toString());
    boolean success = this.insertHelper(rbtNode); //not sure whether we can name the node here or need to do it above
    //balance the tree
    enforceRBTreePropertiesAfterInsert(rbtNode);
    //set the root node to black
    ((RBTNode<T>) this.root).isBlack = true;
    //System.out.println("Tree after insert: " + this.toLevelOrderString());
    //System.out.println("");
    return success;
  }

/**
   * Helper method that will return the node in the tree that contains a specific key. Returns null
   * if there is no node that contains the key.
   *
   * @param data the data value for which we want to find the node that contains it
   * @return the node that contains the data value or null if there is no such node
   */
  @Override
  protected RBTNode<T> findNode(Comparable<T> data) {
    RBTNode<T> current = (RBTNode<T>) this.root;
    while (current != null) {
      int compare = data.compareTo(current.data);
      if (compare == 0) {
        // we found the value
        return current;
      } else if (compare < 0) {
        if (current.getDownLeft() == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the left subtree
        current = current.getDownLeft();
      } else {
        if (current.getDownRight() == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the right subtree
        current = current.getDownRight();
      }
    }
    return null;
  }

 ///////////////////////////////////////////
 //tests
 ////////////////  
 
//  /**
//   * Main method to run tests. If you'd like to add additional test methods, add a line for each of
//   * them.
//   *
//   * @param args
//   */
//  public static void main(String[] args) {
//    //System.out.println("Test insert root passed: " + testInsertFirstNode());
//    //System.out.println("Test no red violations passed: " + testNoRedViolations());
//    System.out.println("Test Case 1 passed: " + testCase1());
//  }
}
