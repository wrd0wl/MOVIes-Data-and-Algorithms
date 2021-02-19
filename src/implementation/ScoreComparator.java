package src.implementation;

import src.commons.*;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Collaboration> {

    @Override
    public int compare(Collaboration m1, Collaboration m2) {
        return m2.getScore().compareTo(m1.getScore());
    }
    
}
