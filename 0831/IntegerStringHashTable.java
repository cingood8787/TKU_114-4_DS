```java
import java.util.ArrayList;
import java.util.List;

public class IntegerStringHashTable {

    static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private final List<Entry>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public IntegerStringHashTable(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "capacity must be greater than 0"
            );
        }

        buckets = new ArrayList[capacity];

        for (int i = 0; i < capacity; i++) {
            buckets[i] = new ArrayList<>();
        }

        size = 0;
    }

    // 計算 bucket index
    private int indexFor(int key) {
        return Math.floorMod(key, buckets.length);
    }

    // 新增或更新
    public void put(int key, String value) {

        int index = indexFor(key);
        List<Entry> bucket = buckets[index];

        // 相同 key 已存在，只更新 value
        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        // 新 key
        bucket.add(new Entry(key, value));
        size++;
    }

    // 取得 value
    public String get(int key) {

        int index = indexFor(key);

        for (Entry entry : buckets[index]) {
            if (entry.key == key) {
                return entry.value;
            }
        }

        return null;
    }

    // 是否包含 key
    public boolean containsKey(int key) {

        int index = indexFor(key);

        for (Entry entry : buckets[index]) {
            if (entry.key == key) {
                return true;
            }
        }

        return false;
    }

    // 移除 key
    public String remove(int key) {

        int index = indexFor(key);
        List<Entry> bucket = buckets[index];

        for (int i = 0; i < bucket.size(); i++) {

            Entry entry = bucket.get(i);

            if (entry.key == key) {
                bucket.remove(i);
                size--;

                return entry.value;
            }
        }

        return null;
    }

    // 目前資料筆數
    public int size() {
        return size;
    }

    // 輸出所有 bucket
    public void bucketReport() {

        System.out.println("=== Bucket Report ===");

        for (int i = 0; i < buckets.length; i++) {
            System.out.println(
                    "Bucket " + i + ": " + buckets[i]
            );
        }

        System.out.println("Size = " + size);
    }

    public static void main(String[] args) {

        IntegerStringHashTable table =
                new IntegerStringHashTable(5);

        // 新增資料
        table.put(10, "Apple");
        table.put(15, "Banana");
        table.put(7, "Orange");
        table.put(12, "Grape");
        table.put(-5, "Mango");

        System.out.println("=== After Insert ===");
        table.bucketReport();

        System.out.println();

        // 測試 get
        System.out.println(
                "get(10) = " + table.get(10)
        );

        System.out.println(
                "get(99) = " + table.get(99)
        );

        // containsKey
        System.out.println(
                "containsKey(15) = "
                        + table.containsKey(15)
        );

        System.out.println(
                "containsKey(100) = "
                        + table.containsKey(100)
        );

        System.out.println();

        // 相同 key 更新
        System.out.println("=== Update Key 10 ===");

        int beforeSize = table.size();

        table.put(10, "Watermelon");

        System.out.println(
                "get(10) = " + table.get(10)
        );

        System.out.println(
                "Size before = " + beforeSize
        );

        System.out.println(
                "Size after = " + table.size()
        );

        System.out.println();

        // 移除
        System.out.println("=== Remove ===");

        System.out.println(
                "remove(15) = " + table.remove(15)
        );

        System.out.println(
                "remove(99) = " + table.remove(99)
        );

        System.out.println();

        table.bucketReport();
    }
}
```
