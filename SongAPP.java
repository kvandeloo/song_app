import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;



/**
 * Main entry point for running the iSongify app.
 */
public class SongApp {
    public static void main(String[] args) {
    	//Call the readData method to create a red-balck tree using the provided file
	    	
		IterableSortedCollection<Song> songTree = readData("song.csv");
	
		System.out.println("Welcome to iSongify Summer version!");
		System.out.println("===================");
		
		runCommandLoop(songTree);
	
		System.out.println();
		System.out.println("===================");
		System.out.println("Thanks, and Goodbye");
    }

    /**
     * Repeated gives the user an opportunity to issue new commands until
     * they select Q to quit.
     */
    public static void runCommandLoop(IterableSortedCollection<Song> tree ) {
    	//TODO: create a while loop to include all codes below. The loop should
    	//	allow one keep searching song list
    	//		that satisfy a criteria you specify
    	displayMainMenu();
    	
    	//TODO: Obtain the input for your operation and related arguments
    	
    	//TODO: Call the getRange method and print out the search results
	
    }

    /**
     * Displays the menu of command options to the user.y
     * 
     */
    public static void displayMainMenu() {
		
	//TODO: Display the menu as follows; if you use 
    //	a metric different from Liveness, use it to replace Liveness
	/*    
	    ~~~ Command Menu ~~~
	        [G]et Songs by Liveness [min - max]
	        [Q]uit
	 */
    }

    /**
     * Loads data from the .csv file referenced by filename.
     * @param fileName is the name of the csv file to load data from
     * @param attrName is the name of the attribute you choose to use for sort and search
     * @throws IOException when there is trouble finding/reading file
     */
    public static IterableSortedCollection<Song> readData(String fileName, String attrName) throws IOException {
	// Note: this placeholder doesn't need to output anything,
	// it will be implemented by the backend developer in P105.
    	
    	IterableSortedCollection<Song> tree = new IterableRedBlackTree();
    	//TODO: Read in the records in the song.csv file 
    	//and store them in a red-black tree using  attrName as the comparable key
    	
    	return tree;
    }

    /**
     * Retrieves a list of song titles for songs that have a attribute
     * within the specified range (sorted by the attribute in ascending order).  
     *
     * @param low is the minimum attribute value of songs in the returned list
     * @param hight is the maximum attribute value of songs in the returned list
     * @param songTree is the red-black tree of Song objects sorted with the attribute
     * 			value you selected when reading the file and creating the tree
     * @return List of titles for all songs in specified range 
     */
    public static List<String> getRange(int low, int high, IterableSortedCollection<Song> songTree) {
	// TODO: implement this method

    }

    