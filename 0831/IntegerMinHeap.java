```java
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class IntegerMinHeap {

    private final List<Integer> heap = new ArrayList<>();

    // 加入元素
    public void add(int value) {
        heap.add(value);
        int index = heap.size() - 1;

        // 向上調整
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(parent) <= heap.get(index)) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    // 查看最小值
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        return heap.get(0);
    }

    // 移除並回傳最小值
    public int removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);

        // 如果移除後還有元素
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return min;
    }

    // Heap 大小
    public int size() {
        return heap.size();
    }

    // 判斷是否為空
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // 向下調整
    private void heapifyDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
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

            swap(index, smallest);
            index = smallest;
        }
    }

    // 交換元素
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // 測試
    public static void main(String[] args) {

        IntegerMinHeap minHeap = new IntegerMinHeap();

        int[] data = {25, 40, 10, 50, 30, 10};

        System.out.println("=== Add Test ===");

        for (int value : data) {
            minHeap.add(value);
            System.out.println("Add: " + value);
        }

        System.out.println();
        System.out.println("Size = " + minHeap.size());
        System.out.println("Min = " + minHeap.peek());

        System.out.println();
        System.out.println("=== Remove Test ===");

        int previous = Integer.MIN_VALUE;
        boolean nonDecreasing = true;

        while (!minHeap.isEmpty()) {
            int current = minHeap.removeMin();

            System.out.print(current + " ");

            if (current < previous) {
                nonDecreasing = false;
            }

            previous = current;
        }

        System.out.println();
        System.out.println(
                "Non-decreasing order: " + nonDecreasing
        );

        // 測試空 Heap 例外
        System.out.println();
        System.out.println("=== Empty Heap Test ===");

        try {
            minHeap.peek();
        } catch (NoSuchElementException e) {
            System.out.println(
                    "peek(): NoSuchElementException"
            );
        }

        try {
            minHeap.removeMin();
        } catch (NoSuchElementException e) {
            System.out.println(
                    "removeMin(): NoSuchElementException"
            );
        }
    }
}
```
