import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;




public class MovidaCore implements IMovidaDB, IMovidaSearch{
    
    private MapImplementation map;
    private SortingAlgorithm sort;
    
    private IDictionary<String, Movie> movieTitle;
    private IDictionary<String, Person> allDirectors;
    private IDictionary<String, Person> allActors;
    private IDictionary<Integer, ArrayList<Movie>> movieYear;
    private IDictionary<String, ArrayList<Movie>> movieDirector;
    private IDictionary<String, ArrayList<Movie>> movieActor;
    private ISorting sorting;

    /********** Default Structures and Configurations **********/
    public MovidaCore(){
        this.movieTitle = new ABR<>();
        this.allDirectors = new ABR<>();
        this.allActors = new ABR<>();
        this.movieYear = new ABR<>();
        this.movieDirector = new ABR<>();
        this.movieActor = new ABR<>();
        this.sorting = new QuickSort();
        this.map = MapImplementation.ABR;
        this.sort = SortingAlgorithm.QuickSort;
    }
    
    /********** IMovidaConfig Implementation **********/ 
    public boolean setSort(SortingAlgorithm a){
        boolean configured = false;
        if(a == SortingAlgorithm.InsertionSort || a == SortingAlgorithm.QuickSort){
            if(a != sort){
                if(a == SortingAlgorithm.InsertionSort){
                    sort = SortingAlgorithm.InsertionSort;
                    sorting = new InsertionSort();
                }
                else{
                    sort = SortingAlgorithm.QuickSort;
                    sorting = new QuickSort();
                }
                configured = true;
            } 
        }
        return configured;
    }

    public boolean setMap(MapImplementation m){
        boolean configured = false;
        if(m == MapImplementation.ABR || m == MapImplementation.BTree){
            if(m != map){
                Movie[] movies = getAllMovies();
                clear();
                if(m == MapImplementation.BTree){
                    map = MapImplementation.BTree;
                    movieTitle = new Btree<>();
                    allDirectors = new Btree<>();
                    allActors = new Btree<>();
                    movieYear = new Btree<>();
                    movieDirector = new Btree<>();
                    movieActor = new Btree<>();
                }
                else{
                    map = MapImplementation.ABR;
                    movieTitle = new ABR<>();
                    allDirectors = new ABR<>();
                    allActors = new ABR<>();
                    movieYear = new ABR<>();
                    movieDirector = new ABR<>();
                    movieActor = new ABR<>();
                }
                for(Movie movie : movies){
                    implementStructures(movie);
                }
                configured = true;
            }
        }
        return configured;
    }
    
    

    /********** IMovidaDB Implementation **********/
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


    public void clear() {
        movieTitle.clear();
        allDirectors.clear();
        allActors.clear();
        movieYear.clear();
        movieDirector.clear();
        movieActor.clear();
    }

    public int countMovies() {
        return getAllMovies().length;
    }

    public int countPeople() {
        return getAllPeople().length;
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

            /*remove Person from allDirectors*/
            if(allDirectors.search(movieFound.getDirector().getName()).getParticipate() > 1){
                allDirectors.search(movieFound.getDirector().getName()).decrease();
            }
            else{
                allDirectors.delete(movieFound.getDirector().getName());
            }
            

            /*remove Movie from movieActor dictionary and Person from allActors*/
            for(Person cast : movieFound.getCast()){
                if(movieActor.search(cast.getName()).size() > 1){
                    movieActor.search(cast.getName()).remove(movieFound);
                }
                else{
                    movieActor.delete(cast.getName());
                }

                if(allActors.search(cast.getName()).getParticipate() > 1){
                    allActors.search(cast.getName()).decrease();
                }
                else{
                    allActors.delete(cast.getName());
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
        if(allDirectors.search(name.toLowerCase().trim()) != null){
            return allDirectors.search(name.toLowerCase().trim());
        }
        else{
            return allActors.search(name.toLowerCase().trim());
        }
    }

    public Movie[] getAllMovies() {
        Movie[] allMovies = new Movie[movieTitle.keyValues().toArray().length];
        allMovies = movieTitle.keyValues().toArray(allMovies);
        return allMovies;
    }

      public Person[] getAllPeople() {
        ArrayList<Person> allPeople = new ArrayList<>();
        allPeople.addAll(allDirectors.keyValues());
        allPeople.addAll(allActors.keyValues());
        return allPeople.toArray(new Person[0]);
    }
    
   /********** IMovidaSearch Implementation **********/

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
            Movie[] votedMovies = new Movie[movies.length];
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
    public Movie[] searchMostRecentMovies(Integer N){
        Movie[] movies = getAllMovies();
        sorting.sort(movies, new YearsComparator());
        if(movies.length <= N){
            Movie[] recentMovies = new Movie[movies.length];

            for(int i = movies.length - 1, j = 0; i >= 0 && j < N; i --, j ++){
                recentMovies[j] = movies[i];
            }
            return recentMovies;
        }
        else{
            Movie[] recentMovies = new Movie[N];

            for (int i = movies.length - 1, j = 0; i > (movies.length - 1) - N && j < N; i --, j ++){
                recentMovies[j] = movies[i];
            }
            return recentMovies;
        }
    }
    public Person[] searchMostActiveActors(Integer N){
        Person[] actors = getAllActors();
        sorting.sort(actors, new ParticipateComparator());
        if(actors.length <= N){
            Person[] participatedActor = new Person[actors.length];

            for(int i = actors.length - 1, j = 0; i >= 0 && j < N; i --, j ++){
                participatedActor[j] = actors[i];
            }
            return participatedActor;
        }
        else{
            Person[] participatedActor = new Person[N];

            for (int i = actors.length - 1, j = 0; i > (actors.length - 1) - N && j < N; i --, j ++){
                participatedActor[j] = actors[i];
            }
            return participatedActor;
        }
    }
    
    /********** Auxiliary Methods **********/
    
    private void implementStructures(Movie movie) {
        movieTitle.insert(movie.getTitle(), movie);

        if(movieYear.search(movie.getYear()) == null){
            movieYear.insert(movie.getYear(), new ArrayList<>());
        }

        movieYear.search(movie.getYear()).add(movie);

        if(movieDirector.search(movie.getDirector().getName()) == null){
            movieDirector.insert(movie.getDirector().getName(), new ArrayList<>());
            allDirectors.insert(movie.getDirector().getName(), movie.getDirector());
        }

        movieDirector.search(movie.getDirector().getName()).add(movie);
        allDirectors.search(movie.getDirector().getName()).increase();

        for(Person actor : movie.getCast()){
            if(allActors.search(actor.getName()) == null){
                allActors.insert(actor.getName(), actor);
            }

            allActors.search(actor.getName()).increase();
            
            if(movieActor.search(actor.getName()) == null){
                movieActor.insert(actor.getName(), new ArrayList<>());
            }
            movieActor.search(actor.getName()).add(movie);
        }
    }
    
    private Person[] getAllActors() {
        Person[] actors = new Person[allActors.keyValues().toArray().length];
        actors = allActors.keyValues().toArray(actors);
        return actors;
    }

}
