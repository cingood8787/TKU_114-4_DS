import java.util.ArrayList;
import java.util.List;

public class BookIsbnHashTable {

    static class Book {
        String isbn;
        String title;

        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        @Override
        public String toString() {
            return isbn + "=" + title;
        }
    }

    private final List<Book>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public BookIsbnHashTable(int capacity) {

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

    // 計算 ISBN 所在 bucket
    private int indexFor(String isbn) {

        if (isbn == null) {
            throw new IllegalArgumentException(
                    "isbn cannot be null"
            );
        }

        return Math.floorMod(
                isbn.hashCode(),
                buckets.length
        );
    }

    // 新增或更新
    public void put(String isbn, String title) {

        int index = indexFor(isbn);
        List<Book> bucket = buckets[index];

        // 相同 ISBN 已存在 -> 更新
        for (Book book : bucket) {

            if (book.isbn.equals(isbn)) {
                book.title = title;
                return;
            }
        }

        // 新增
        bucket.add(
                new Book(isbn, title)
        );

        size++;
    }

    // 搜尋
    public String get(String isbn) {

        int index = indexFor(isbn);

        for (Book book : buckets[index]) {

            if (book.isbn.equals(isbn)) {
                return book.title;
            }
        }

        return null;
    }

    // 是否包含 ISBN
    public boolean containsKey(String isbn) {

        int index = indexFor(isbn);

        for (Book book : buckets[index]) {

            if (book.isbn.equals(isbn)) {
                return true;
            }
        }

        return false;
    }

    // 刪除
    public String remove(String isbn) {

        int index = indexFor(isbn);
        List<Book> bucket = buckets[index];

        for (int i = 0; i < bucket.size(); i++) {

            Book book = bucket.get(i);

            if (book.isbn.equals(isbn)) {

                bucket.remove(i);
                size--;

                return book.title;
            }
        }

        return null;
    }

    // 資料筆數
    public int size() {
        return size;
    }

    // Load Factor
    public double loadFactor() {

        return (double) size
                / buckets.length;
    }

    // Bucket Report
    public void bucketReport() {

        System.out.println(
                "=== Book ISBN Bucket Report ==="
        );

        for (int i = 0; i < buckets.length; i++) {

            System.out.println(
                    "Bucket "
                            + i
                            + ": "
                            + buckets[i]
            );
        }

        System.out.println(
                "Size = " + size
        );

        System.out.printf(
                "Load factor = %.2f%n",
                loadFactor()
        );
    }

    public static void main(String[] args) {

        BookIsbnHashTable table =
                new BookIsbnHashTable(5);

        // 新增
        table.put(
                "978001",
                "Java Programming"
        );

        table.put(
                "978002",
                "Data Structures"
        );

        table.put(
                "978003",
                "Database Systems"
        );

        table.put(
                "978004",
                "Web Programming"
        );

        table.put(
                "978005",
                "Algorithms"
        );

        System.out.println(
                "=== After Insert ==="
        );

        table.bucketReport();

        System.out.println();

        // 搜尋
        System.out.println(
                "get(978002) = "
                        + table.get("978002")
        );

        System.out.println(
                "containsKey(978003) = "
                        + table.containsKey("978003")
        );

        System.out.println(
                "containsKey(999999) = "
                        + table.containsKey("999999")
        );

        System.out.println();

        // 更新
        System.out.println(
                "=== Update Test ==="
        );

        int beforeSize = table.size();

        table.put(
                "978002",
                "Advanced Data Structures"
        );

        System.out.println(
                "Updated title = "
                        + table.get("978002")
        );

        System.out.println(
                "Size before = "
                        + beforeSize
        );

        System.out.println(
                "Size after = "
                        + table.size()
        );

        System.out.println();

        // 刪除
        System.out.println(
                "=== Remove Test ==="
        );

        System.out.println(
                "remove(978004) = "
                        + table.remove("978004")
        );

        System.out.println(
                "remove(999999) = "
                        + table.remove("999999")
        );

        System.out.println();

        table.bucketReport();
    }
}