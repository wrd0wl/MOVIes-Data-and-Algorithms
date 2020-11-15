
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MovidaCore{

    public class MovidaCore{

    static ArrayList<Movie> MovieList = new ArrayList<>();

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
                    System.out.println(scanData[i]);
                }
                scanTitle = scanData[0];
                scanYear = Integer.parseInt(scanData[1]);
                scanDirector = scanData[2];
                scanCast = scanData[3].split(", ");
                scanVotes = Integer.parseInt(scanData[4]);

                Person director = new Person(scanDirector);
                Person[] cast = new Person[scanCast.length];
                Movie movie = new Movie(scanTitle, scanYear, scanVotes, cast, director);
                MovieList.add(movie);
                if(scan.hasNextLine()){
                    scan.nextLine();
                }
            }
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //throw new MovidaFileException();
        }
    }


    // TESTING PART. SOON WILL BE REMOVED
    public static void main(String[] args) throws FileNotFoundException {
        File newFile = new File("data.txt");
        //Scanner sc = new Scanner(newFile);
        loadFromFile(newFile);
	}

}
