// == CS400 Summer 2024 File Header Information ==
// Name: Kathryn Cole
// Email: kcole9@wisc.edu
// Lecturer: Jiazhen Zhou
// Notes to Grader: Changed "Spring" to "Summer" in header

/**
 * Class to represent a song in the songs.csv kaggle dataset. 
 */

public class Song implements SongInterface {

	//attributes
	private String title;
	private String artist;
	private String genres;
	private int year;
	private int BPM;
	private int energy;
	private int danceability;
	private int loudness;
	private int liveness;

	//constructor
	public Song(String title,String artist,String genres,int year,int BPM,int energy,int danceability, int loudness,int liveness) {
		this.title = title;
		this.artist = artist;
		this.genres = genres;
		this.year = year;
		this.BPM = BPM;
		this.energy = energy;
		this.danceability = danceability;
		this.loudness = loudness;
		this.liveness = liveness;
	}
	//setters
	public void setTitle(String title){
		this.title = title;
	}
	public void setArtist(String artist){
		this.artist = artist;
	}
	public void setGenres(String genres){
		this.genres = genres;
	}
	public void setYear(int year){
		this.year = year;
	}
	public void setBPM(int BPM){
		this.BPM = BPM;
	}
	public void setEnergy(Integer energy){
		this.energy = energy;
	}
	public void setDnce(int danceability){
		this.danceability = danceability;
	}
	public void setDB(int loudness){
		this.loudness = loudness;
	}
	public void setLive(int liveness){
		this.liveness = liveness;
	}
	//getters
	public String getTitle(){
		return title;
	}
	public String getArtist(){
		return artist;
	}
	public String getGenres(){
		return genres;
	}
	public int getYear(){
		return year;
	}
	public int getBPM(){
		return BPM;
	}
	public int getEnergy(){
		return energy;
	}
	public int getDanceability(){
		return danceability;
	}
	public int getLoudness(){
		return loudness;
	}
	public int getLiveness(){
		return liveness;
	}
	//main metric for comparison when using the red-black tree for storing Song objects: 
	//liveness
	//implement the compareTo method based on the liveness
	@Override
	public int compareTo(SongInterface song){
		if (this.liveness > song.getLiveness()){
			return 1;
		}
		else if (this.liveness < song.getLiveness()){
			return -1;
		}
		else {
			return 0;
		}
	}
	//public int compareTo(Song song){
	//	return compare(this.BPM, song.BPM);
	//}

	//@Override
	//public boolean equals(Song song){}
}
