import java.util.ArrayList;
import java.util.List;

public class Q05_BoundedBox
        <T extends Comparable<T>> {

    private final Object[] data;
    private int size;

    public Q05_BoundedBox(int capacity) {

        if (capacity < 1) {
            throw new IllegalArgumentException();
        }

        data = new Object[capacity];
        size = 0;
    }

    public boolean add(T value) {

        if (value == null || isFull()) {
            return false;
        }

        data[size++] = value;

        return true;
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size == data.length;
    }

    @SuppressWarnings("unchecked")
    public T minimum() {

        if (size == 0) {
            return null;
        }

        T min = (T) data[0];

        for (int i = 1; i < size; i++) {

            T value = (T) data[i];

            if (value.compareTo(min) < 0) {
                min = value;
            }
        }

        return min;
    }

    @SuppressWarnings("unchecked")
    public T maximum() {

        if (size == 0) {
            return null;
        }

        T max = (T) data[0];

        for (int i = 1; i < size; i++) {

            T value = (T) data[i];

            if (value.compareTo(max) > 0) {
                max = value;
            }
        }

        return max;
    }

    @SuppressWarnings("unchecked")
    public int countGreaterThan(T threshold) {

        if (threshold == null) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < size; i++) {

            T value = (T) data[i];

            if (value.compareTo(threshold) > 0) {
                count++;
            }
        }

        return count;
    }

    @SuppressWarnings("unchecked")
    public List<T> snapshot() {

        List<T> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            result.add((T) data[i]);
        }

        return result;
    }
}