import java.io.File;
import java.io.FileNotFoundException;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        MovidaCore newMovida = new MovidaCore();
        File newFile = new File("data.txt");
        newMovida.loadFromFile(newFile);
        //Movie found = getMovieByTitle("The Fugitive");
        //System.out.println(found.getDirector().getName());
        // Movie found1 = movieTitle.search("die hard");
        // System.out.println(found1.getYear());
        // remove node with one child
        //System.out.println(countMovies());
        //boolean delete = deleteMovieByTitle("The Fugitive");
        //Movie[] movie = new Movie[getAllMovies().length];
        //movie = getAllMovies();

        //test countPeople()
        System.out.println(newMovida.countPeople());

        //test getPersonByName()
        System.out.println(newMovida.getPersonByName("Jodie Foster").getName());

        //test getAllPeople()
        Person[] allPeople = newMovida.getAllPeople();
        for(Person people : allPeople){
            System.out.println(people.getName());
        }
	
	//change Dictionary
	boolean mapbtree = newMovida.setMap(MapImplementation.BTree);
	    
        //test searchMoviesByTitle, test searchMoviesByYear, searchMoviesByDirector
        Movie[] movie = newMovida.searchMoviesByTitle("Cape Fear");
        Movie[] movies = newMovida.searchMoviesByTitle("a");
        Movie[] years = newMovida.searchMoviesInYear(1997);
        Movie[] directors = newMovida.searchMoviesDirectedBy("Martin Scorsese");
        Movie[] actors = newMovida.searchMoviesStarredBy("Bruce Willis");
	
	//change Dictionary
	boolean mapabr = newMovida.setMap(MapImplementation.ABR);
	
	//test searchMostVotedMovies
        Movie[] votedMovies = newMovida.searchMostVotedMovies(5);
        Movie[] voteMovies1 = newMovida.searchMostVotedMovies(10);
        
	//change Sorting
        boolean setis = newMovida.setSort(SortingAlgorithm.InsertionSort);

        //test searchMostRecentMovies
        Movie[] recentMovies = newMovida.searchMostRecentMovies(6);
        Movie[] recentMovies1 = newMovida.searchMostRecentMovies(12);
	
	//change Sorting
        boolean setqs = newMovida.setSort(SortingAlgorithm.QuickSort);

        //test searchMostActiveActors
        Person[] activeActors = newMovida.searchMostActiveActors(4);
        Person[] activeActors1 = newMovida.searchMostActiveActors(11);
	    
        //test deleteMoviesByTitle
        //node with one child
        boolean film0 = newMovida.deleteMovieByTitle("Contact");
        //node without children
        boolean film1 = newMovida.deleteMovieByTitle("Die Hard");
        //node with two children
        boolean film2 = newMovida.deleteMovieByTitle("Cape Fear");

        //clear all data
        newMovida.clear();
	}
}
    

