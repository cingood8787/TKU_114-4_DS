```java
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMinHeap {

    private int[] heap;
    private int size;

    // 預設容量
    public ArrayMinHeap() {
        heap = new int[4];
        size = 0;
    }

    // 加入元素
    public void add(int value) {
        ensureCapacity();

        heap[size] = value;
        int index = size;
        size++;

        // 向上調整
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    // 查看最小值
    public int peek() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        return heap[0];
    }

    // 移除最小值
    public int remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        int min = heap[0];

        heap[0] = heap[size - 1];
        size--;

        if (size > 0) {
            heapifyDown(0);
        }

        return min;
    }

    // 回傳目前 Heap 內容
    public int[] snapshot() {
        return Arrays.copyOf(heap, size);
    }

    // 取得目前元素數量
    public int size() {
        return size;
    }

    // 判斷是否為空
    public boolean isEmpty() {
        return size == 0;
    }

    // 容量不足時擴充兩倍
    private void ensureCapacity() {
        if (size == heap.length) {
            int oldCapacity = heap.length;
            int newCapacity = oldCapacity * 2;

            heap = Arrays.copyOf(heap, newCapacity);

            System.out.println(
                    "Capacity expanded: "
                            + oldCapacity + " -> " + newCapacity
            );
        }
    }

    // 向下調整
    private void heapifyDown(int index) {

        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;

            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }

            if (right < size && heap[right] < heap[smallest]) {
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
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {

        ArrayMinHeap minHeap = new ArrayMinHeap();

        // 至少 20 筆測試資料
        int[] data = {
                45, 20, 60, 10, 35,
                70, 5, 25, 50, 15,
                80, 30, 40, 55, 65,
                1, 90, 12, 18, 8
        };

        System.out.println("=== Add Test ===");

        for (int value : data) {
            minHeap.add(value);

            System.out.println(
                    "Add " + value
                            + " -> "
                            + Arrays.toString(minHeap.snapshot())
            );
        }

        System.out.println();
        System.out.println("Size = " + minHeap.size());
        System.out.println("Peek = " + minHeap.peek());

        System.out.println();
        System.out.println("=== Remove Test ===");

        while (!minHeap.isEmpty()) {

            int removed = minHeap.remove();

            System.out.println(
                    "Remove " + removed
                            + " -> "
                            + Arrays.toString(minHeap.snapshot())
            );
        }

        System.out.println();
        System.out.println("Heap empty = " + minHeap.isEmpty());

        // 測試空 Heap
        try {
            minHeap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("peek(): Heap is empty");
        }

        try {
            minHeap.remove();
        } catch (NoSuchElementException e) {
            System.out.println("remove(): Heap is empty");
        }
    }
}
```
