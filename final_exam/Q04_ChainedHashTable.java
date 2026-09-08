import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q04_ChainedHashTable {

    private static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<LinkedList<Entry>> buckets;
    private int size;

    public Q04_ChainedHashTable(int bucketCount) {

        if (bucketCount <= 0) {
            throw new IllegalArgumentException(
                    "bucketCount must be greater than 0");
        }

        buckets = new ArrayList<>();

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    private int index(int key) {
        return Math.floorMod(key, buckets.size());
    }

    public void put(int key, String value) {

        LinkedList<Entry> bucket = buckets.get(index(key));

        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {

        LinkedList<Entry> bucket = buckets.get(index(key));

        for (Entry entry : bucket) {
            if (entry.key == key) {
                return entry.value;
            }
        }

        return null;
    }

    public boolean remove(int key) {

        LinkedList<Entry> bucket = buckets.get(index(key));

        for (int i = 0; i < bucket.size(); i++) {

            if (bucket.get(i).key == key) {
                bucket.remove(i);
                size--;
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public int longestChain() {

        int longest = 0;

        for (LinkedList<Entry> bucket : buckets) {
            longest = Math.max(longest, bucket.size());
        }

        return longest;
    }
}