import java.util.ArrayList;
import java.util.List;

public class Q03_MinHeapRemove {

    private final ArrayList<Integer> heap = new ArrayList<>();

    public Q03_MinHeapRemove(List<Integer> values) {

        if (values != null) {
            for (Integer value : values) {
                if (value != null) {
                    heap.add(value);
                }
            }
        }

        // bottom-up heapify
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    public Integer removeMin() {

        if (heap.isEmpty()) {
            return null;
        }

        int min = heap.get(0);

        if (heap.size() == 1) {
            heap.remove(heap.size() - 1);
            return min;
        }

        int last = heap.remove(heap.size() - 1);
        heap.set(0, last);

        bubbleDown(0);

        return min;
    }

    private void bubbleDown(int index) {

        while (true) {

            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < heap.size()
                    && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }

            if (right < heap.size()
                    && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest == index) {
                break;
            }

            int temp = heap.get(index);
            heap.set(index, heap.get(smallest));
            heap.set(smallest, temp);

            index = smallest;
        }
    }

    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }

        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public List<Integer> snapshot() {
        return new ArrayList<>(heap);
    }
}