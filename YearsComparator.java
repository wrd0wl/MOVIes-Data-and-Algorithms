  
import java.util.Comparator;

public class YearsComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie m1, Movie m2) {
        return m1.getYear().compareTo(m2.getYear());
    }
    
}