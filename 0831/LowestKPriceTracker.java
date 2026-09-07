```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LowestKPriceTracker {

    public static List<Integer> lowestKPrices(List<Integer> prices, int k) {

        // K <= 0，回傳空 List
        if (k <= 0) {
            return new ArrayList<>();
        }

        // Max Heap
        PriorityQueue<Integer> maxHeap =
                new PriorityQueue<>(Collections.reverseOrder());

        for (Integer price : prices) {

            // 忽略 null 與負數
            if (price == null || price < 0) {
                continue;
            }

            // Heap 還沒滿
            if (maxHeap.size() < k) {
                maxHeap.offer(price);
            }
            // 如果目前價格比 Heap 最大值更小
            else if (price < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(price);
            }
        }

        // 轉成 List
        List<Integer> result = new ArrayList<>(maxHeap);

        // 依價格遞增排列
        Collections.sort(result);

        return result;
    }

    public static void main(String[] args) {

        List<Integer> prices = new ArrayList<>();

        prices.add(120);
        prices.add(50);
        prices.add(null);
        prices.add(80);
        prices.add(-10);
        prices.add(30);
        prices.add(60);
        prices.add(20);

        int k = 3;

        List<Integer> result = lowestKPrices(prices, k);

        System.out.println("Prices = " + prices);
        System.out.println("K = " + k);
        System.out.println("Lowest K prices = " + result);

        // 測試 K <= 0
        System.out.println(
                "K = 0 -> " + lowestKPrices(prices, 0)
        );
    }
}
```
