package src.commons;

import java.util.ArrayList;

/**
 * 
 * The class is used to memorize the two actors and the set of all the films in which they collaborated.
 * 
 * Each collaboration is characterized by a score.
 * 
 */

public class Collaboration {

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;
	
	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public void addMovie(ArrayList<Movie> m){
		this.movies.addAll(m);
	}

	public Double getScore(){
		
		Double score = 0.0;
		
		for (Movie m : movies)
			score += m.getVotes();
		
		return score / movies.size();
	}

	public boolean equals(Object obj) { 
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Collaboration)) {
			return false;
		}
		Collaboration other = (Collaboration) obj;
		return this.actorA.getName().equals(other.actorA.getName()) && this.actorB.getName().equals(other.actorB.getName());
	}

	public int hashCode() { 
		return this.actorA.hashCode() + this.actorB.hashCode();
	}
}
