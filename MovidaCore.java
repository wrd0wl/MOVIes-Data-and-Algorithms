import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MovidaCore{

    //Default structures
    private static IDictionary<String, Movie> movieTitle = new ABR<>();

    // scan file and get data
    public static void loadFromFile(File f) { // remove static
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
                    cast[i] = new Person(scanCast[i]);
                }

                Movie newMovie = new Movie(scanTitle, scanYear, scanVotes, cast, director);

                //Implement dictionary
                implementDictionary(newMovie);
                if (scan.hasNextLine()) {
                    scan.nextLine();
                }
            }
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // throw new MovidaFileException();
        }
    }

    private static void implementDictionary(Movie movie) {
        String title = movie.getTitle();
        movieTitle.insert(title, movie);
    }
	
    public static void clear(){
        movieTitle.clear();

        /*here we will clear all current structures*/ 
    }

    public static int countMovies(){
        return movieTitle.keyValues().toArray().length;
    }
	
    public static boolean deleteMovieByTitle(String title) {
        if (getMovieByTitle(title) != null) {
            movieTitle.delete(title.toLowerCase());
            return true;
        } else {
            return false;
        }

    }

    public static Movie getMovieByTitle(String title){
        return movieTitle.search(title.toLowerCase());        
    }

    public static Movie[] getAllMovies() {
        Movie[] allMovies = new Movie[movieTitle.keyValues().toArray().length];
        allMovies = movieTitle.keyValues().toArray(allMovies);
        return allMovies;
    }

    // TESTING PART. SOON WILL BE REMOVED
   public static void main(String[] args) throws FileNotFoundException {
        File newFile = new File("data.txt");
        loadFromFile(newFile);
        System.out.println(countMovies());
        boolean delete = deleteMovieByTitle("The Fugitive");
        Movie[] movie = new Movie[getAllMovies().length];
        movie = getAllMovies();
	clear();
	}

}
