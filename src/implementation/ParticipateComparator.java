package src.implementation;

import src.commons.*;
import java.util.Comparator;

public class ParticipateComparator implements Comparator<Person> {

    @Override
    public int compare(Person m1, Person m2) {
        return m2.getParticipate().compareTo(m1.getParticipate());
    }
    
}