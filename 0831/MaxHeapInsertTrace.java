```java
import java.util.ArrayList;
import java.util.List;

public class MaxHeapInsertTrace {

    static class MaxHeap {
        private final List<Integer> heap = new ArrayList<>();

        // 加入元素
        public void add(int value) {
            heap.add(value);

            int index = heap.size() - 1;

            // 向上調整
            while (index > 0) {
                int parent = (index - 1) / 2;

                if (heap.get(parent) >= heap.get(index)) {
                    break;
                }

                swap(parent, index);
                index = parent;
            }
        }

        // 取得最大值
        public int peekMax() {
            if (heap.isEmpty()) {
                throw new IllegalStateException("Heap is empty");
            }

            return heap.get(0);
        }

        // 回傳目前 Heap 陣列狀態
        public List<Integer> snapshot() {
            return new ArrayList<>(heap);
        }

        // 交換位置
        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    public static void main(String[] args) {

        MaxHeap maxHeap = new MaxHeap();

        int[] data = {25, 40, 10, 50, 30, 50};

        for (int value : data) {
            maxHeap.add(value);

            System.out.println(
                    "add " + value + " -> " + maxHeap.snapshot()
            );
        }

        System.out.println("Max = " + maxHeap.peekMax());
        System.out.println("Root = " + maxHeap.peekMax());
    }
}
```
