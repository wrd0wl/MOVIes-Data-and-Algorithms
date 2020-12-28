
import java.util.Comparator;

public class InsertionSort implements ISorting {
@Override
public <T> void sort(T[] array, Comparator<T> parameter){
    insertionSorts(array, parameter);

}
private <T> void insertionSorts(T[] array, Comparator<T> parameter)
    {
        // start at the first index and iterate through to the end
        for (int i = 1; i < array.length; ++i) { 
            T key = array[i]; 
            int j = i - 1; 
  
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j >= 0 && parameter.compare(key, array[j]) < 0) { 
                array[j + 1] = array[j]; 
                j = j - 1; 
            } 
            array[j + 1] = key; 
        } 
    } 
}
