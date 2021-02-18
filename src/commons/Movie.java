package src.commons;

/**
 * 
 * The class that is used to represent a movie in the Movida application.
 * 
 * A film is uniquely identified by the case-insensitive title, without leading and trailing spaces, without double spaces.
 * 
 * The class can be modified or extended but it must implement all the getter methods to retrieve the information characterizing a movie.
 * 
 */
public class Movie{
	
	private String title;
	private Integer year;
	private Integer votes;
	private Person[] cast;
	private Person director;
	
	public Movie(String title, Integer year, Integer votes,
			Person[] cast, Person director) {
		this.title = title;
		this.year = year;
		this.votes = votes;
		this.cast = cast;
		this.director = director;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getYear() {
		return this.year;
	}

	public Integer getVotes() {
		return this.votes;
	}

	public Person[] getCast() {
		return this.cast;
	}

	public Person getDirector() {
		return this.director;
	}
}
