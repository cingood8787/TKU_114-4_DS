```java
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HeapPropertyValidator {

    // 檢查是否為 Min Heap
    public static boolean isMinHeap(List<Integer> heap) {

        // null 回傳 false
        if (heap == null) {
            return false;
        }

        // empty List 或單一元素自然符合 Heap
        if (heap.size() <= 1) {
            return true;
        }

        // 逐一檢查 parent-child 關係
        for (int i = 0; i < heap.size() / 2; i++) {

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            // 檢查左子節點
            if (left < heap.size()
                    && heap.get(i) > heap.get(left)) {
                return false;
            }

            // 檢查右子節點
            if (right < heap.size()
                    && heap.get(i) > heap.get(right)) {
                return false;
            }
        }

        return true;
    }

    // 檢查是否為 Max Heap
    public static boolean isMaxHeap(List<Integer> heap) {

        // null 回傳 false
        if (heap == null) {
            return false;
        }

        // empty List 或單一元素自然符合 Heap
        if (heap.size() <= 1) {
            return true;
        }

        // 逐一檢查 parent-child 關係
        for (int i = 0; i < heap.size() / 2; i++) {

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            // 檢查左子節點
            if (left < heap.size()
                    && heap.get(i) < heap.get(left)) {
                return false;
            }

            // 檢查右子節點
            if (right < heap.size()
                    && heap.get(i) < heap.get(right)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        List<Integer> minHeap =
                Arrays.asList(10, 20, 15, 30, 40, 25);

        List<Integer> maxHeap =
                Arrays.asList(50, 40, 45, 20, 30, 10);

        List<Integer> invalidHeap =
                Arrays.asList(10, 50, 20, 30, 40);

        List<Integer> empty =
                Collections.emptyList();

        List<Integer> single =
                Collections.singletonList(100);

        System.out.println(
                "minHeap is Min Heap: "
                        + isMinHeap(minHeap)
        );

        System.out.println(
                "minHeap is Max Heap: "
                        + isMaxHeap(minHeap)
        );

        System.out.println(
                "maxHeap is Max Heap: "
                        + isMaxHeap(maxHeap)
        );

        System.out.println(
                "maxHeap is Min Heap: "
                        + isMinHeap(maxHeap)
        );

        System.out.println(
                "invalidHeap is Min Heap: "
                        + isMinHeap(invalidHeap)
        );

        System.out.println(
                "null: "
                        + isMinHeap(null)
        );

        System.out.println(
                "empty: "
                        + isMinHeap(empty)
        );

        System.out.println(
                "single: "
                        + isMinHeap(single)
        );
    }
}
```
