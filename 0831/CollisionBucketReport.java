```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollisionBucketReport {

    public static void report(List<Integer> keys, int bucketCount) {

        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be > 0");
        }

        // 建立 buckets
        List<List<Integer>> buckets = new ArrayList<>();

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // 空輸入或 null
        if (keys != null) {
            for (Integer key : keys) {

                if (key == null) {
                    continue;
                }

                // floorMod 可以正確處理負數 key
                int index = Math.floorMod(key, bucketCount);

                // 重複 key 也照樣放入
                buckets.get(index).add(key);
            }
        }

        int collisions = 0;
        int longestChain = 0;

        System.out.println("=== Bucket Report ===");

        for (int i = 0; i < bucketCount; i++) {

            List<Integer> bucket = buckets.get(i);

            System.out.println(
                    "Bucket " + i + ": " + bucket
            );

            // 同一 bucket 第一筆不算 collision
            // 後續每一筆都算 collision
            if (bucket.size() > 1) {
                collisions += bucket.size() - 1;
            }

            if (bucket.size() > longestChain) {
                longestChain = bucket.size();
            }
        }

        System.out.println("Collisions = " + collisions);
        System.out.println("Longest chain = " + longestChain);
    }

    public static void main(String[] args) {

        List<Integer> keys = Arrays.asList(
                10, 15, 20, 25, -5, 10, -10
        );

        int bucketCount = 5;

        report(keys, bucketCount);

        System.out.println();

        // 測試空輸入
        System.out.println("=== Empty Input Test ===");
        report(new ArrayList<>(), bucketCount);
    }
}
```
