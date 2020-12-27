import java.util.Comparator;

public class VotesComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie m1, Movie m2) {
        return m1.getVotes().compareTo(m2.getVotes());
    }
    
}