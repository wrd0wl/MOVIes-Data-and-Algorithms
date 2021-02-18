package src.implementation;

import src.commons.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;




public class MovidaCore implements IMovidaDB, IMovidaSearch, ICollaborations{
    
    private MapImplementation map;
    private SortingAlgorithm sort;
    
    private IDictionary<String, Movie> movieTitle;
    private IDictionary<String, Person> allDirectors;
    private IDictionary<String, Person> allActors;
    private IDictionary<Integer, ArrayList<Movie>> movieYear;
    private IDictionary<String, ArrayList<Movie>> movieDirector;
    private IDictionary<String, ArrayList<Movie>> movieActor;
    private ISorting sorting;
    private Graph<Person> graph;

    /********** Default Structures and Configurations **********/
    public MovidaCore(){
        this.movieTitle = new BST<>();
        this.allDirectors = new BST<>();
        this.allActors = new BST<>();
        this.movieYear = new BST<>();
        this.movieDirector = new BST<>();
        this.movieActor = new BST<>();
        this.sorting = new QuickSort();
        this.map = MapImplementation.BST;
        this.sort = SortingAlgorithm.QuickSort;
        this.graph = new Graph<>();
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
        if(m == MapImplementation.BST || m == MapImplementation.BTree){
            if(m != map){
                Movie[] movies = getAllMovies();
                clear();
                if(m == MapImplementation.BTree){
                    map = MapImplementation.BTree;
                    movieTitle = new BTree<>();
                    allDirectors = new BTree<>();
                    allActors = new BTree<>();
                    movieYear = new BTree<>();
                    movieDirector = new BTree<>();
                    movieActor = new BTree<>();
                }
                else{
                    map = MapImplementation.BST;
                    movieTitle = new BST<>();
                    allDirectors = new BST<>();
                    allActors = new BST<>();
                    movieYear = new BST<>();
                    movieDirector = new BST<>();
                    movieActor = new BST<>();
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
            createGraph();
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MovidaFileException();
        }
    }

    public void saveToFile(File f) throws MovidaFileException{
        try {
            FileWriter writer = new FileWriter(f, false);
            Movie[] movies = getAllMovies();
            for (Movie movie : movies) {
                writer.append("Title: " + movie.getTitle() +"\n");
                writer.append("Year: " + movie.getYear().toString() +"\n");
                writer.append("Director: " + movie.getDirector().getName() +"\n");
                writer.append("Cast: ");
                for(int i = 0; i < movie.getCast().length; i++){
                    if(i == movie.getCast().length - 1){
                        writer.append(movie.getCast()[i].getName() +"\n");
                    }
                    else{
                        writer.append(movie.getCast()[i].getName() +", ");
                    }
                }
                writer.append("Votes: " + movie.getVotes().toString() + "\n");
                writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
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

            //create graph with updated data
            graph.clear();
            createGraph();

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
            return movies;
        }
        else{
            return Arrays.copyOfRange(movies, 0, N);
        }
    }
    public Movie[] searchMostRecentMovies(Integer N){
        Movie[] movies = getAllMovies();
        sorting.sort(movies, new YearsComparator());
        if(movies.length <= N){
            return movies;
        }
        else{
            return Arrays.copyOfRange(movies, 0, N);
        }
    }
    public Person[] searchMostActiveActors(Integer N){
        Person[] actors = getAllActors();
        sorting.sort(actors, new ParticipateComparator());
        if(actors.length <= N){
            return actors;
        }
        else{
            return Arrays.copyOfRange(actors, 0, N);
        }
    }

    /********** Collaborations **********/

    public Person[] getDirectCollaboratorsOf(Person actor){
        return graph.getSet(actor).toArray(new Person[0]);
    }
    
    public Person[] getTeamOf(Person actor){
        Queue<Person> visit = new LinkedList<Person>();
        ArrayList<Person> visited = new ArrayList<>();
        visit.add(actor);
        while(!visit.isEmpty()){
            Person person = visit.poll();
            Person[] team = getDirectCollaboratorsOf(person);
            for(Person collab : team) {
                if(!visited.contains(collab)){
                    if (!visit.contains(collab)){
                        visit.add(collab);
                    }
                    visited.add(collab);
                } 
            }
        }
        return visited.toArray(new Person[0]);
    }

    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor){
        Collaboration[] collaboration = createCollaborations(actor);
        sorting.sort(collaboration, new ScoreComparator());
        for(int i = 0; i < collaboration.length / 2; i++){
            Collaboration temp = collaboration[i];
            collaboration[i] = collaboration[collaboration.length - i - 1];
            collaboration[collaboration.length - i - 1] = temp;
        }
        Collaboration[] max = maximize(new ArrayList<>(Arrays.asList(collaboration)));
        return max;
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

    private void createGraph(){
        Movie[] movies = getAllMovies();
        for(Movie movie : movies){
            graph.addToGraph(movie.getCast());
        }
        
    }
    
    private Person[] getAllActors() {
        Person[] actors = new Person[allActors.keyValues().toArray().length];
        actors = allActors.keyValues().toArray(actors);
        return actors;
    }

    private Collaboration[] createCollaborations(Person actor){
        ArrayList<Collaboration> collaboration = new ArrayList<>();
        ArrayList<Person> visit = new ArrayList<Person>();
        ArrayList<Person> visited = new ArrayList<>();
        visit.add(actor);
        int i = 0;
        while(visit.size() > i){
            Person current = visit.get(i);
            visited.add(current);
            Person[] team = getDirectCollaboratorsOf(current);
            List<Person> direct = graph.getSet(current);
            for(Person person : team){
                if(!visit.contains(person)){
                    visit.add(person);
                }
                if(direct.contains(person) && !visited.contains(person)){
                    Collaboration newCollab = new Collaboration(current, person);
                    ArrayList<Movie> movieA = new ArrayList<>(Arrays.asList(searchMoviesStarredBy(current.getName())));
                    ArrayList<Movie> movieB = new ArrayList<>(Arrays.asList(searchMoviesStarredBy(person.getName())));
                    for(Movie movie: movieA){
                        if(movieB.contains(movie)){
                            movieB.remove(movie);
                        }
                    }
                    newCollab.addMovie(movieA);
                    newCollab.addMovie(movieB);
                    collaboration.add(newCollab);
                }
            }
            i++;
        }
        return collaboration.toArray(new Collaboration[0]);
    }

    private Collaboration[] maximize (ArrayList<Collaboration> collabList){
        ArrayList<Collaboration> maximize = new ArrayList<>();
        ArrayList<Person> visited = new ArrayList<>();
        ArrayList<Collaboration> visit = new ArrayList<>();
        visit.add(collabList.remove(0));
        while(!visit.isEmpty()){
            Collaboration person = visit.remove(0);
            if(!visited.contains(person.getActorB())){
                visited.add(person.getActorB());
                maximize.add(person);
                for(Collaboration collab : collabList){
                    if(!visited.contains(collab.getActorA()) || !visited.contains(collab.getActorB())){
                        visit.add(collab);   
                    }
                } 
            }
        }
        return maximize.toArray(new Collaboration[0]);
    }
}