// == CS400 Summer 2024 File Header Information ==
// Name: Kathryn Cole
// Email: kcole9@wisc.edu
// Lecturer: Jiazhen Zhou
// Notes to Grader: Changed "Spring" to "Summer" in header

import java.util.Iterator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

//JUnit test program for the IterableRedBlackTree class

/**
* This class extends the IterableRedBlackTree class to run tests on it.
*/
public class IterableRedBlackTreeTest extends IterableRedBlackTree {
  static final int TIMEOUT = 2; //2 seconds
    
  //helper method to create  red black tree we can use for testing
  private static IterableRedBlackTree<Integer> buildIRBTInteger(Integer[] valuesToInsert) {
    System.out.println("Building IRBT");
    //create empty tree
    IterableRedBlackTree<Integer> outputTree = new IterableRedBlackTree<Integer>();
    //insert each value in the input list
    for (int i=0; i<valuesToInsert.length; i++){
    	Integer newNodeValue = valuesToInsert[i];
	outputTree.insert(newNodeValue);
	System.out.println("tree: " + outputTree.toLevelOrderString());
    }
	  return outputTree;
  }
  
  //helper method to create  red black tree we can use for testing
  private static IterableRedBlackTree<String> buildIRBTString(String[] valuesToInsert) {
    System.out.println("Building IRBT");
    //create empty tree
    IterableRedBlackTree<String> outputTree = new IterableRedBlackTree<String>();
    //insert each value in the input list
    for (int i=0; i<valuesToInsert.length; i++){
    	String newNodeValue = valuesToInsert[i];
	outputTree.insert(newNodeValue);
	System.out.println("tree: " + outputTree.toLevelOrderString());
    }
	  return outputTree;
  }


    /**
    * Check when data is String and tree contains no duplicates. 
    * @return
    */
   @Test
   @Timeout(TIMEOUT) //timeout at 2 seconds
   public void testStringDataNoDuplicates(){
	//build red black tree
	String[] insertArray = {"a","b","c"};
	IterableRedBlackTree<String> irbTree = buildIRBTString(insertArray);
	//confirm that tree has the correct level order
	String expectedLevelOrderString = "[ b, a, c ]";
	String actualLevelOrderString = irbTree.toLevelOrderString();
	Assertions.assertEquals(expectedLevelOrderString,actualLevelOrderString,"Level order is correct with set of unique strings.");
   }

    /**
    * Check when data is Integer and tree contains duplicates.
    * @return
    */
   @Test
   @Timeout(TIMEOUT) //timeout at 2 seconds
   public void testIntegerDataWithDuplicates(){
	//build red black tree
	Integer[] insertArray = {1,2,3};
	IterableRedBlackTree<Integer> irbTree = buildIRBTInteger(insertArray);
	//insert duplicate data
	irbTree.insert(3);
	irbTree.insert(3);
	//confirm that tree has the correct level order
	String expectedLevelOrderString = "[ 2, 1, 3, 3, 3 ]";
	String actualLevelOrderString = irbTree.toLevelOrderString();
	Assertions.assertEquals(expectedLevelOrderString,actualLevelOrderString,"Level order is correct with set of nonunique integers.");
    }

    /**
    * Check iterator with specified starting point. 
    * @return
    */
   @Test
   @Timeout(TIMEOUT) //timeout at 2 seconds
   public void testIteratorFromStartingPoint(){ 
	//build red black tree
	Integer[] insertArray = {1,2,3,4,5};
	IterableRedBlackTree<Integer> irbTree = buildIRBTInteger(insertArray);
	//set start point, do one iteration forward in the tree
	irbTree.setIterationStartPoint(3);
	Iterator<Integer> irbtIterator = irbTree.iterator();
	//confirm that iterator moves to the correct next node
	Integer expectedNext = 3;
	Integer actualNext = irbtIterator.next();	
   	//while (irbtIterator.hasNext()==true){
	//	System.out.println("next: " + irbtIterator.next());
	//}
	Assertions.assertEquals(expectedNext,actualNext,"IRBTIterator moves to correct next node when start point is set.");
   }

   
   /**
    * Check iterator without specific starting point.  
    * @return
    */
    @Test
    @Timeout(TIMEOUT) //timeout at 2 seconds
    public void testIteratorFromRoot() {
	//build red black tree
	Integer[] insertArray = {1,2,3,4,5};
	IterableRedBlackTree<Integer> irbTree = buildIRBTInteger(insertArray);
	//System.out.println("last tree built");
	//do one iteration forward in the tree
	Iterator<Integer> irbtIterator = irbTree.iterator();
	//System.out.println("iterator created");
	//confirm that iterator moves to the correct next node
	Integer expectedNext = 1;
	Integer actualNext = irbtIterator.next();
	//System.out.println("next called");	
   	//while (irbtIterator.hasNext()==true){
	//	System.out.println("next: " + irbtIterator.next());
	//}
	Assertions.assertEquals(expectedNext,actualNext,"IRBTIterator moves to correct next node when start point is NOT set.");
   }
    
} 
