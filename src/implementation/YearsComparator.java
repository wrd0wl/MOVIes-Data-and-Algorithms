package src.implementation;

import src.commons.*;

import java.util.Comparator;

public class YearsComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie m1, Movie m2) {
        return m2.getYear().compareTo(m1.getYear());
    }
    
}