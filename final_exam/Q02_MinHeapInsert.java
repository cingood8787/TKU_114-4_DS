import java.util.ArrayList;
import java.util.List;

public class Q02_MinHeapInsert {

    private final ArrayList<Integer> heap = new ArrayList<>();

    public void add(int value) {
        heap.add(value);

        int index = heap.size() - 1;

        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(parent) <= heap.get(index)) {
                break;
            }

            int temp = heap.get(parent);
            heap.set(parent, heap.get(index));
            heap.set(index, temp);

            index = parent;
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

    public boolean isValidMinHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < heap.size()
                    && heap.get(i) > heap.get(left)) {
                return false;
            }

            if (right < heap.size()
                    && heap.get(i) > heap.get(right)) {
                return false;
            }
        }

        return true;
    }
}