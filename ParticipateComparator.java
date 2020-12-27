  
import java.util.Comparator;

public class ParticipateComparator implements Comparator<Person> {

    @Override
    public int compare(Person m1, Person m2) {
        return m1.getParticipate().compareTo(m2.getParticipate());
    }
    
}