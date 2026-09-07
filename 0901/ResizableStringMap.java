import java.util.ArrayList;
import java.util.List;

public class ResizableStringMap {

    static class Entry {
        String key;
        String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private List<Entry>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public ResizableStringMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "initialCapacity must be greater than 0"
            );
        }

        buckets = new ArrayList[initialCapacity];

        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = new ArrayList<>();
        }

        size = 0;
    }

    private int indexFor(String key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    public void put(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        int index = indexFor(key);

        for (Entry entry : buckets[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        buckets[index].add(new Entry(key, value));
        size++;

        if (loadFactor() > 0.75) {
            resize();
        }
    }

    public String get(String key) {
        if (key == null) {
            return null;
        }

        int index = indexFor(key);

        for (Entry entry : buckets[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    public boolean containsKey(String key) {
        if (key == null) {
            return false;
        }

        int index = indexFor(key);

        for (Entry entry : buckets[index]) {
            if (entry.key.equals(key)) {
                return true;
            }
        }

        return false;
    }

    public String remove(String key) {
        if (key == null) {
            return null;
        }

        int index = indexFor(key);
        List<Entry> bucket = buckets[index];

        for (int i = 0; i < bucket.size(); i++) {
            Entry entry = bucket.get(i);

            if (entry.key.equals(key)) {
                bucket.remove(i);
                size--;
                return entry.value;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public int bucketCount() {
        return buckets.length;
    }

    public double loadFactor() {
        return (double) size / buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldBucketCount = buckets.length;
        int newBucketCount = oldBucketCount * 2 + 1;

        List<Entry>[] oldBuckets = buckets;

        buckets = new ArrayList[newBucketCount];

        for (int i = 0; i < newBucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 重新配置所有 entry
        for (List<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                int newIndex =
                        Math.floorMod(
                                entry.key.hashCode(),
                                newBucketCount
                        );

                buckets[newIndex].add(entry);
            }
        }

        System.out.println(
                "Resize: "
                        + oldBucketCount
                        + " -> "
                        + newBucketCount
        );
    }

    public void bucketReport() {
        System.out.println("=== Bucket Report ===");

        for (int i = 0; i < buckets.length; i++) {
            System.out.println(
                    "Bucket " + i + ": " + buckets[i]
            );
        }

        System.out.println("Size = " + size);
        System.out.println("Bucket count = " + buckets.length);
        System.out.printf(
                "Load factor = %.2f%n",
                loadFactor()
        );
    }

    public static void main(String[] args) {

        ResizableStringMap map =
                new ResizableStringMap(3);

        map.put("A", "Apple");
        map.put("B", "Banana");

        System.out.println("After 2 entries:");
        map.bucketReport();

        System.out.println();

        // 3 / 3 = 1.0 > 0.75，會觸發 resize
        map.put("C", "Cat");

        System.out.println();
        System.out.println("After resize:");
        map.bucketReport();

        System.out.println();

        map.put("D", "Dog");
        map.put("E", "Egg");

        System.out.println(
                "get(A) = " + map.get("A")
        );

        System.out.println(
                "containsKey(C) = "
                        + map.containsKey("C")
        );

        System.out.println(
                "remove(B) = "
                        + map.remove("B")
        );

        System.out.println();

        map.bucketReport();
    }
}