import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;




public class MovidaCore implements IMovidaDB, IMovidaSearch{

    private IDictionary<String, Movie> movieTitle;
    private IDictionary<String, Person> moviePeople;
    private IDictionary<Integer, ArrayList<Movie>> movieYear;
    private IDictionary<String, ArrayList<Movie>> movieDirector;
    private IDictionary<String, ArrayList<Movie>> movieActor;
    
    public MovidaCore(){
        //Default structures
        movieTitle = new ABR<>();
        moviePeople = new ABR<>();
        movieYear = new ABR<>();
        movieDirector = new ABR<>();
        movieActor = new ABR<>();
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

        if(movieYear.search(movie.getYear()) == null){
            movieYear.insert(movie.getYear(), new ArrayList<>());
        }

        movieYear.search(movie.getYear()).add(movie);

        if(movieDirector.search(movie.getDirector().getName()) == null){
            movieDirector.insert(movie.getDirector().getName(), new ArrayList<>());
        }

        movieDirector.search(movie.getDirector().getName()).add(movie);

        for(Person actor : movie.getCast()){
            if(movieActor.search(actor.getName()) == null){
                movieActor.insert(actor.getName(), new ArrayList<>());
            }
            movieActor.search(actor.getName()).add(movie);
        }
    }


    public void clear() {
        movieTitle.clear();
        moviePeople.clear();
        movieYear.clear();
        movieDirector.clear();
        movieActor.clear();

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

            /*remove Movie by title*/ 
            movieTitle.delete(movieFound.getTitle());

            /*remove Movie from movieYear dictionary*/
            if(movieYear.search(movieFound.getYear()).size() > 1){
                movieYear.search(movieFound.getYear()).remove(movieFound);
            }
            else{
                movieYear.delete(movieFound.getYear());
            }

            /*remove Movie from movieDirector dictionary*/
            if(movieDirector.search(movieFound.getDirector().getName()).size() > 1){
                movieDirector.search(movieFound.getDirector().getName()).remove(movieFound);
            }
            else{
                movieDirector.delete(movieFound.getDirector().getName());
            }

            /*remove Movie from movieActor dictionary*/
            for(Person cast : movieFound.getCast()){
                if(movieActor.search(cast.getName()).size() > 1){
                    movieActor.search(cast.getName()).remove(movieFound);
                }
                else{
                    movieActor.delete(cast.getName());
                }
            }
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
        ArrayList<Movie> foundMovies = new ArrayList<>();
        if(getMovieByTitle(title) == null){
            Movie[] movies = getAllMovies();
            for (Movie movie : movies) {
                if(movie.getTitle().contains(title.toLowerCase().trim())){
                    foundMovies.add(movie);
                }
            }
        }
        else{
            foundMovies.add(getMovieByTitle(title)); 
        }
        return foundMovies.toArray(new Movie[0]);
    }

    public Movie[] searchMoviesInYear(Integer year){
        ArrayList<Movie> foundYear = new ArrayList<>();
        foundYear.addAll(movieYear.search(year));
        return foundYear.toArray(new Movie[0]);
    }
    
    public Movie[] searchMoviesDirectedBy(String name){
        ArrayList<Movie> foundDirector = new ArrayList<>();
        foundDirector.addAll(movieDirector.search(name.toLowerCase().trim()));
        return foundDirector.toArray(new Movie[0]);
    }

    public Movie[] searchMoviesStarredBy(String name){
        ArrayList<Movie> foundActor = new ArrayList<>();
        foundActor.addAll(movieActor.search(name.toLowerCase().trim()));
        return foundActor.toArray(new Movie[0]);
    }
    
    public Movie[] searchMostVotedMovies(Integer N){
        Movie[] movies = getAllMovies();
        sorting.sort(movies, new VotesComparator());
        if(movies.length <= N){
            Movie[] votedMovies = new Movie[N];

            for(int i = movies.length - 1, j = 0; i >= 0 && j < N; i --, j ++){
                votedMovies[j] = movies[i];
            }
            return votedMovies;
        }
        else{
            Movie[] votedMovies = new Movie[N];

            for (int i = movies.length - 1, j = 0; i > (movies.length - 1) - N && j < N; i --, j ++){
                votedMovies[j] = movies[i];
            }
            return votedMovies;
        }
    }
}
