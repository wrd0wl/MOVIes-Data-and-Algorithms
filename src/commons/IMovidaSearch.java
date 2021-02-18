package src.commons;

/**
 * 
 * Interface that is used to describe the searching operations in the Movida application.
 * 
 */
public interface IMovidaSearch {
	
	/**
	 * 
	 * Search movie by title. 
	 * 
	 * Returns movies whose title contains the string <code> title </code> passed as a parameter.
	 * 
	 * For the exact match use the <code> getMovieByTitle (String s) </code> method
	 * 
	 * Returns an empty array if no movie matches the search criteria.  
	 *  
	 * @param title search by the title of the film.
	 * @return array of movies.
	 */
	public Movie[] searchMoviesByTitle(String title);
	
	/**
	 * Search movies by year. 
	 * 
	 * Returns the movies released in the year <code> year </code> passed as a parameter.
	 *  
	 * Returns an empty array if no movie matches the search criteria.  
	 *  
	 * @param year search by the year of the movie.
	 * @return array of movies.
	 */
	public Movie[] searchMoviesInYear(Integer year);

	/**
	 * Search for films by director.
	 * 
	 * Returns the films directed by the director whose name is passed as a parameter. 
	 *  
	 * Returns an empty array if no movie matches the search criteria.
	 *  
	 * @param name search the movie by director.
	 * @return array of movies.
	 */
	public Movie[] searchMoviesDirectedBy(String name);

	/**
	 * Search films by actor. 
	 * 
	 * Returns the films in which the person whose name is passed as a parameter participated as an actor.
	 *  
	 * Returns an empty vector if no movie matches the search criteria.  
	 *  
	 * @param name search the movie by actor.
	 * @return array of movies.
	 */
	public Movie[] searchMoviesStarredBy(String name);

	/**
	 * Search top rated movies.
	 * 
	 * Returns the <code> N </code> films that received the most votes, in descending order of votes.
	 * 
	 * If the total number of films is less than N returns all films in order.
	 *   
	 * @param N number of films that the search must return.
	 * @return array of movies.
	 */
	public Movie[] searchMostVotedMovies(Integer N);

	/**
	 * Search the most recent movies.
	 * 
	 * Returns the most recent <code> N </code> movies, based on the release year compared to the current year.
	 * 
	 * If the total number of films is less than N returns all films, however in order.
	 *   
	 * @param N number of films that the search must return.
	 * @return array of movies.
	 */
	public Movie[] searchMostRecentMovies(Integer N);

	/**
	 * Search the most active actors. 
	 * 
	 * Returns the <code> N </code> actors who participated in the highest number of films.
	 * 
	 * If the number of actors is less than N it returns all the actors, in order.
	 *
	 * @param N number of actors that the search must return.
	 * @return array of actors.
	 */
	public Person[] searchMostActiveActors(Integer N);
}
