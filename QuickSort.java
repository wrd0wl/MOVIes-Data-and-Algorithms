import java.util.Comparator;
import java.util.Random;

public class QuickSort implements ISorting {

    Random rand = new Random();
    @Override
    public <T> void sort(T[] array, Comparator<T> parameter) {
        quickSort(array, 0, array.length - 1, parameter);

    }

    private <T> void quickSort(T[] array, int start, int end, Comparator<T> parameter) {
        if (start < end) {
            int partitionIndex = partition(array, start, end, parameter);
            quickSort(array, start, partitionIndex - 1, parameter);
            quickSort(array, partitionIndex + 1, end, parameter);
        }
    }

    private <T> int partition(T[] array, int start, int end, Comparator<T> parameter) {
        int inf = start;
        int sup = end + 1;
        int pivot = inf + this.rand.nextInt(end - start + 1);
        T temp;
        T x = array[pivot];
        array[pivot] = array[inf];
        array[inf] = x;
        while (true) {
            do {
                inf++;
            } while (inf <= end && (parameter.compare(x, array[inf]) > 0));

            do {
                sup--;
            } while (parameter.compare(x, array[sup]) < 0);

            if (inf < sup) {
                temp = array[inf];
                array[inf] = array[sup];
                array[sup] = temp;
            } else {
                break;
            }
        }
        temp = array[start];
        array[start] = array[sup];
        array[sup] = temp;
        return sup;
    }
}
