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

        //test searchMoviesByTitle, test searchMoviesByYear, searchMoviesByDirector
        Movie[] movies = newMovida.searchMoviesByTitle("the fugitive, Die Hard. Contact");
        Movie[] years = newMovida.searchMoviesInYear(1997);
        Movie[] directors = newMovida.searchMoviesDirectedBy("Martin Scorsese");
        Movie[] actors = newMovida.searchMoviesStarredBy("Bruce Willis");

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
    

