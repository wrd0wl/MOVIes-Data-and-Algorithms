
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MovidaCore{

    public static void loadFromFile(File f) { //remove static
        try {
            String scanTitle;
            Integer scanYear;
            String scanDirector;
            String[] scanCast;
            Integer scanVotes;
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String[] scanData = new String[5];
                for(int i = 0; i < 5; i++){
                    scanData[i] = scan.nextLine().split(":")[1].trim();
                }
                scanTitle = scanData[0];
                scanYear = Integer.parseInt(scanData[1]);
                scanDirector = scanData[2];
                scanCast = scanData[3].split(", ");
                scanVotes = Integer.parseInt(scanData[4]);
                System.out.println(scanTitle);
                System.out.println(scanYear);
                System.out.println(scanDirector);
                for(int i = 0; i < scanCast.length; i++){
                    System.out.println(scanCast[i]);
                }
                System.out.println(scanVotes);
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