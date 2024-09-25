// == CS400 Summer 2024 File Header Information ==
// Name: Kathryn Cole
// Email: kcole9@wisc.edu
// Lecturer: Jiazhen Zhou
// Notes to Grader: Changed "Spring" to "Summer" in header
import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
//import java.io.IOException;
import java.io.*;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.FileReader;
import java.util.Scanner;

/**
 * Main entry point for running the iSongify app.
 */
public class SongAPP {
    public static void main(String[] args) throws IOException {
    	//Call the readData method to create a red-balck tree using the provided file
	    	
		//try {
			IterableSortedCollection<SongInterface> songTree = readData("songs.csv","liveness");
		//}
		//catch (IOException e){
			//System.out.println(e.toString());
			//System.out.println("Failed to read data from csv");
		//}
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
    public static void runCommandLoop(IterableSortedCollection<SongInterface> tree ) {
    	//TODO: create a while loop to include all codes below. The loop should
    	//	allow one keep searching song list
    	//		that satisfy a criteria you specify
	String inputString = new String();
	Scanner reader = new Scanner(System.in);
	while (inputString.toLowerCase() != "q"){
    		displayMainMenu();
    	
    		//TODO: Obtain the input for your operation and related arguments
    		//try {
			inputString = reader.nextLine();
		//} 
		//catch (IOException e) {
		//	System.out.println(e.toString());
		//	System.out.println("invalid input");
		//}
		//System.out.println("You entered " + inputString);
		if (inputString.toLowerCase().charAt(0) == 'q'){
			System.out.println("Goodbye!");
			break;
		}
		else if (inputString.toLowerCase().charAt(0) == 'g'){
			int lowInt = -1;
			//System.out.println("Enter the low boundary as an integer: ");
			//try {
				String rangeString = reader.nextLine();
				int separator = rangeString.indexOf("-");
				if (separator == -1){	
					System.out.println("Invalid input(s). Please try again.");
					continue;
				}
				String low = rangeString.substring(0,separator).trim();
				//System.out.println("low: " + low);
				lowInt = Integer.parseInt(low);

			//} 
			//catch (IOException e) {
			//	System.out.println(e.toString());
			//	System.out.println("invalid input");
			//}
			int highInt = -1;
			//System.out.println("Enter the high boundary as an integer: ");
			//try {
				String high = rangeString.substring(separator+1,rangeString.length()).trim();
				//System.out.println("high: " + high);
				highInt = Integer.parseInt(high);
			//} 
			//catch (IOException e) {
			//	System.out.println(e.toString());
			//	System.out.println("invalid input");
			//}
			
			//TODO: Call the getRange method and print out the search results
			if (lowInt == -1 && highInt == -1){
				System.out.println("Invalid input(s). Please try again.");
			}
			else {
				List<String> rangeList = getRange(lowInt,highInt,tree);
				//System.out.println("Results: ");
				System.out.println(rangeList.toString());
			}
		}
		else {
			System.out.println("Please enter a valid input from the menu options.");
		}
	}
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
	System.out.println("~~~ Command Menu ~~~");
	System.out.println("[G]et Songs by Tempo (beats per minute) [min - max]");
	System.out.println("[Q]uit");
    }

    /**
     * Loads data from the .csv file referenced by filename.
     * @param fileName is the name of the csv file to load data from
     * @param attrName is the name of the attribute you choose to use for sort and search
     * @throws IOException when there is trouble finding/reading file
     */
    public static IterableSortedCollection<SongInterface> readData(String fileName, String attrName) throws IOException {
	// Note: this placeholder doesn't need to output anything,
	// it will be implemented by the backend developer in P105.
    	
    	IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree();
    	//TODO: Read in the records in the song.csv file 
    	//and store them in a red-black tree using  attrName as the comparable key
	//try {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		int k=0; //so we can exclude header row
		String line = "";
		while ((line = br.readLine()) != null){
			//skip header row
			if (k==0) {
				k++;
				continue;
			}
			//System.out.println("reading... " + line);
			ArrayList<String> csvLine = lineSplit(line);
			String title = csvLine.get(0);
			String artist = csvLine.get(1);
			String topGenre = csvLine.get(2);
			int year = Integer.parseInt(csvLine.get(3));
			int BPM =  Integer.parseInt(csvLine.get(4));
			int energy =  Integer.parseInt(csvLine.get(5));
			int danceability =  Integer.parseInt(csvLine.get(6));
			int loudness =  Integer.parseInt(csvLine.get(7));
			int liveness =  Integer.parseInt(csvLine.get(8));
			Song newSong = new Song(title, artist, topGenre, year, BPM, energy, danceability, loudness, liveness);
		tree.insert(newSong);
		//System.out.println("number of songs inserted: " + Integer.toString(k));
		k++; //count number of songs imported
		}
	//}
	//catch (IOException e) {
	//	System.out.println(e.toString());
	//	System.out.println("error occurred while importing csv data");
	//}
	
    	return tree;
      }

    //helper method to parse csv string
    public static ArrayList<String> lineSplit(String inString){
    	boolean insideQuotes = false;
	String nextField = "";
	ArrayList<String> lineList = new ArrayList<String>();
	for (int i=0;i<inString.length();i++){
		char character = inString.charAt(i);
		//check the char and add to field OR split fields
		if (character != ',' && character != '"'){
			nextField = nextField + character;
		}
		else if (character == ',') {
			if (insideQuotes == true){
			nextField = nextField + character;
			}
			else { //(character == ',' && insideQuotes == false)
			//end of field
			lineList.add(nextField);
			//System.out.println("added to lineList: " + nextField);
			nextField = "";
			}
		}
		else if (character == '"'){
			//quotes used as escape characters
			if (i+1<inString.length() && inString.charAt(i+1) == '"'){
				nextField = nextField + character;//to include quotes
				nextField = nextField + inString.charAt(i+1);
				i++;//skip escape character
			}
			else if (insideQuotes == true){
				//end of field
				nextField = nextField + character;//to include quotes
				lineList.add(nextField);
				nextField = "";
				insideQuotes = false;
				i++;//skip comma
			}
			else { //insideQuotes == false
			        nextField = nextField + character;//to include quotes
				insideQuotes = true;
			}
		}
    
	}
	//add last field if it was not closed by quotes
	if (nextField != ""){
		lineList.add(nextField);
		//System.out.println("added to lineList: " + nextField);
		nextField = "";
	}
	//System.out.println("line list length: " + Integer.toString(lineList.size()));
	return lineList;
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
    public static List<String> getRange(int low, int high, IterableSortedCollection<SongInterface> songTree) {
	// TODO: implement this method
	List<String> rangeList = new ArrayList<String>();
	//find the first song in the red-black tree with the minimum-value element
	//create a dummy song that will not be returned since it is not actually in the tree
	Song lowestSong = new Song("","","",0,0,0,0,0,low);
	songTree.setIterationStartPoint(lowestSong);
	Iterator<SongInterface> songIterator = ((IterableRedBlackTree<SongInterface>)songTree).iterator();
	
	while (songIterator.hasNext()) {
		SongInterface currentSong = songIterator.next();
		//if (currentSong.getLiveness() < low){
		//	continue;
		//}
		if (currentSong.getLiveness() <= high){
			rangeList.add(currentSong.getTitle());
		} else {
			break;
		}
	}
	//start adding songs to the list as the tree is traversed
	//stop printing when any value above the maximum is reached
	return rangeList;
    }
} 
