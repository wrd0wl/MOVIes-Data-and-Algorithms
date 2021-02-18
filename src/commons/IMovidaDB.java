package src.commons;

import java.io.File;

/**
 * 
 * Interface that is used to describe data loading operations in the Movida application.
 * 
 */
public interface IMovidaDB {
	
	/**
	 * Load data from a file, organized according to the MOVIDA format.
	 * 
	 * A film is uniquely identified by the title (case-insensitive), a person by the name (case-insensitive).
	 * Simplification: homonyms and films with the same title are not managed.
	 * 
	 * The new data is added to the that one that is already loaded.
	 * 
	 * If there is a movie with the same title, the record is replaced.
	 * If a person with the same name exists, no other is created.
	 * 
	 * If the file does not respect the format, the data is not loaded and an exception is raised.
	 * 
	 * @param f load data from the file
	 * 
	 * @throws MovidaFileException in case of loading error
	 */
	public void loadFromFile(File f);

	/**
	 * Save all data to a file. 
	 * 
	 * The file is overwritten.
	 * If it is impossible to save it, for example due to a problem with permissions or paths, an exception is raised.
	 * 
	 * @param f save the data to the file
	 * 
	 * @throws MovidaFileException in case of save error
	 */
	public void saveToFile(File f);
	
	/**
	 * Erase all data.
	 * 
	 * You will then need to load more data to continue.
	 */
	public void clear();
	
	/**
	 * Returns the number of films.
	 * 
	 * @return number of total movies.
	 */
	public int countMovies();

	/**
	 * Returns the number of people.
	 * 
	 * @return total number of people.
	 */
	public int countPeople();
	
	/**
	 * Delete the movie with a given title, if it exists.
	 * 
	 * @param title title of the film.
	 * @return <code> true </code> if the movie was found and deleted <code> false </code> otherwise.
	 */
	public boolean deleteMovieByTitle(String title);
	
	/**
	 * Returns the record associated with a movie.
	 *  
	 * @param title the title of the film.
	 * @return record associated with a movie.
	 */
	public Movie getMovieByTitle(String title);

	/**
	 * Returns the record associated with a person, actor or director..
	 * 
	 * @param name the name of the person.
	 * @return record associated with a person.
	 */
	public Person getPersonByName(String name);
	
	
	/**
	 * Returns an array of all films.
	 * 
	 * @return array of movies.
	 */
	public Movie[] getAllMovies();

	/**
	 * Returns an array of all people.
	 *  
	 * @return array of people.
	 */
	public Person[] getAllPeople();

}