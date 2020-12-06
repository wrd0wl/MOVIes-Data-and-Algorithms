import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;




public class MovidaCore implements IMovidaDB, IMovidaSearch{

    private IDictionary<String, Movie> movieTitle;
    private IDictionary<String, Person> moviePeople;
    
    public MovidaCore(){
        //Default structures
        movieTitle = new ABR<>();
        moviePeople = new ABR<>();
    }

    //Scan file and get data
    public void loadFromFile(File f) throws MovidaFileException { 
        try {
            String scanTitle;
            Integer scanYear;
            String scanDirector;
            String[] scanCast;
            Integer scanVotes;
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String[] scanData = new String[5];
                for (int i = 0; i < 5; i++) {
                    scanData[i] = scan.nextLine().split(":")[1].trim();
                }
                //Get data
                scanTitle = scanData[0].toLowerCase();
                scanYear = Integer.parseInt(scanData[1]);
                scanDirector = scanData[2].toLowerCase();
                scanCast = scanData[3].toLowerCase().split(", ");
                scanVotes = Integer.parseInt(scanData[4]);

                Person director = new Person(scanDirector);
                Person[] cast = new Person[scanCast.length];
                for (int i = 0; i < scanCast.length; i++) {
                    cast[i] = new Person(scanCast[i].trim());
                }

                Movie newMovie = new Movie(scanTitle, scanYear, scanVotes, cast, director);

                //Implement dictionary
                implementStructures(newMovie);
                if (scan.hasNextLine()) {
                    scan.nextLine();
                }
                
            }
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MovidaFileException();
        }
    }

    private void implementStructures(Movie movie) {
        movieTitle.insert(movie.getTitle(), movie);
        
        moviePeople.insert(movie.getDirector().getName(), movie.getDirector());

        for (int i = 0; i < movie.getCast().length; i++){

            //todo actors

            moviePeople.insert(movie.getCast()[i].getName(), movie.getCast()[i]);
        }
    }


    public void clear() {
        movieTitle.clear();
        moviePeople.clear();

        /*here we will clear all current structures*/ 
    }

    public int countMovies() {
        return movieTitle.keyValues().toArray().length;
    }

    public int countPeople() {
        return moviePeople.keyValues().toArray().length;
    }

    public boolean deleteMovieByTitle(String title) {
        Movie movieFound = getMovieByTitle(title);
        if (movieFound != null) {
            movieTitle.delete(movieFound.getTitle());
            /*todo director and cast*/
            return true;
        } else {
            return false;
        }
    }

    public Movie getMovieByTitle(String title) {
        return movieTitle.search(title.toLowerCase().trim());        
    }

    public Person getPersonByName(String name) {
        return moviePeople.search(name.toLowerCase().trim());
    }

    public Movie[] getAllMovies() {
        Movie[] allMovies = new Movie[movieTitle.keyValues().toArray().length];
        allMovies = movieTitle.keyValues().toArray(allMovies);
        return allMovies;
    }

    public Person[] getAllPeople() {
        Person[] allPeople = new Person[moviePeople.keyValues().toArray().length];
        allPeople = moviePeople.keyValues().toArray(allPeople);
        return allPeople;
    }

    public Movie[] searchMoviesByTitle(String title){
        ArrayList<Movie> found = new ArrayList<>();
        Movie[] movies = new Movie[movieTitle.keyValues().toArray().length];
        movies = movieTitle.keyValues().toArray(movies);
        for (Movie movie : movies) {
            if(title.toLowerCase().trim().contains(movie.getTitle())){
                found.add(movie);
            }
        }
        return found.toArray(new Movie[0]);
    }

    public Movie[] searchMoviesInYear(Integer year){
        ArrayList<Movie> found = new ArrayList<>();
        Movie[] movies = new Movie[movieTitle.keyValues().toArray().length];
        movies = movieTitle.keyValues().toArray(movies);
        for (Movie movie : movies) {
            if(year.equals(movie.getYear())){
                found.add(movie);
            }
        }
        return found.toArray(new Movie[0]);
    }
    
    public Movie[] searchMoviesDirectedBy(String name){
        ArrayList<Movie> found = new ArrayList<>();
        Movie[] movies = new Movie[movieTitle.keyValues().toArray().length];
        movies = movieTitle.keyValues().toArray(movies);
        for (Movie movie : movies) {
            if(name.toLowerCase().trim().equals(movie.getDirector().getName())){
                found.add(movie);
            }
        }
        return found.toArray(new Movie[0]);
    }

    public Movie[] searchMoviesStarredBy(String name){
        ArrayList<Movie> found = new ArrayList<>();
        Movie[] movies = new Movie[movieTitle.keyValues().toArray().length];
        movies = movieTitle.keyValues().toArray(movies);
        for (Movie movie : movies) {
            for(Person cast : movie.getCast()){
                if(name.toLowerCase().trim().equals(cast.getName())){
                    found.add(movie);
                }
            }
        }
        return found.toArray(new Movie[0]);
    }
}
