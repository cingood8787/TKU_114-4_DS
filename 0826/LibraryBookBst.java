import java.util.ArrayList;
import java.util.List;

public class LibraryBookBst {

    // =========================================
    // Book
    // =========================================
    static class Book {
        private String isbn;
        private String title;
        private String author;
        private boolean available;

        public Book(String isbn, String title, String author) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.available = true;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        @Override
        public String toString() {
            return "Book{isbn='" + isbn
                    + "', title='" + title
                    + "', author='" + author
                    + "', available=" + available
                    + "}";
        }
    }

    // =========================================
    // Node
    // =========================================
    static class Node {
        Book book;
        Node left;
        Node right;

        Node(Book book) {
            this.book = book;
        }
    }

    // =========================================
    // Book BST
    // =========================================
    static class BookBST {

        private Node root;
        private int size;

        // -----------------------------------------
        // Add
        // ISBN 不可重複
        // -----------------------------------------
        public boolean add(Book book) {

            if (book == null) {
                return false;
            }

            if (book.getIsbn() == null
                    || book.getIsbn().trim().isEmpty()) {
                return false;
            }

            if (root == null) {
                root = new Node(book);
                size++;
                return true;
            }

            Node current = root;

            while (true) {

                int compare =
                        book.getIsbn()
                                .compareTo(
                                        current.book.getIsbn()
                                );

                if (compare < 0) {

                    if (current.left == null) {
                        current.left = new Node(book);
                        size++;
                        return true;
                    }

                    current = current.left;

                } else if (compare > 0) {

                    if (current.right == null) {
                        current.right = new Node(book);
                        size++;
                        return true;
                    }

                    current = current.right;

                } else {

                    // duplicate ISBN
                    return false;
                }
            }
        }

        // -----------------------------------------
        // Find
        // -----------------------------------------
        public Book find(String isbn) {

            if (isbn == null) {
                return null;
            }

            Node current = root;

            while (current != null) {

                int compare =
                        isbn.compareTo(
                                current.book.getIsbn()
                        );

                if (compare < 0) {
                    current = current.left;

                } else if (compare > 0) {
                    current = current.right;

                } else {
                    return current.book;
                }
            }

            return null;
        }

        // -----------------------------------------
        // Borrow
        // -----------------------------------------
        public boolean borrow(String isbn) {

            Book book = find(isbn);

            if (book == null) {
                return false;
            }

            // 已借出
            if (!book.isAvailable()) {
                return false;
            }

            book.setAvailable(false);

            return true;
        }

        // -----------------------------------------
        // Return Book
        // -----------------------------------------
        public boolean returnBook(String isbn) {

            Book book = find(isbn);

            if (book == null) {
                return false;
            }

            // 原本就可借，代表沒有借出
            if (book.isAvailable()) {
                return false;
            }

            book.setAvailable(true);

            return true;
        }

        // -----------------------------------------
        // Remove
        // 借出中的書不可 remove
        // -----------------------------------------
        public boolean remove(String isbn) {

            Book book = find(isbn);

            // 不存在
            if (book == null) {
                return false;
            }

            // 借出中，不可以刪
            if (!book.isAvailable()) {
                return false;
            }

            root = removeRecursive(root, isbn);

            size--;

            return true;
        }

        private Node removeRecursive(
                Node node,
                String isbn) {

            if (node == null) {
                return null;
            }

            int compare =
                    isbn.compareTo(
                            node.book.getIsbn()
                    );

            if (compare < 0) {

                node.left =
                        removeRecursive(
                                node.left,
                                isbn
                        );

            } else if (compare > 0) {

                node.right =
                        removeRecursive(
                                node.right,
                                isbn
                        );

            } else {

                // Case 1：沒有 child
                if (node.left == null
                        && node.right == null) {

                    return null;
                }

                // Case 2：只有右 child
                if (node.left == null) {
                    return node.right;
                }

                // Case 2：只有左 child
                if (node.right == null) {
                    return node.left;
                }

                // Case 3：兩個 child
                Node successor =
                        findMin(node.right);

                node.book = successor.book;

                node.right =
                        removeRecursive(
                                node.right,
                                successor.book.getIsbn()
                        );
            }

            return node;
        }

        private Node findMin(Node node) {

            Node current = node;

            while (current.left != null) {
                current = current.left;
            }

            return current;
        }

        // -----------------------------------------
        // Range Query
        // ISBN low ~ high
        // 使用 BST 剪枝
        // -----------------------------------------
        public List<Book> rangeQuery(
                String low,
                String high) {

            List<Book> result =
                    new ArrayList<>();

            if (low == null || high == null) {
                return result;
            }

            if (low.compareTo(high) > 0) {
                return result;
            }

            rangeRecursive(
                    root,
                    low,
                    high,
                    result
            );

            return result;
        }

        private void rangeRecursive(
                Node node,
                String low,
                String high,
                List<Book> result) {

            if (node == null) {
                return;
            }

            String isbn =
                    node.book.getIsbn();

            // 左子樹可能還有符合資料
            if (isbn.compareTo(low) > 0) {

                rangeRecursive(
                        node.left,
                        low,
                        high,
                        result
                );
            }

            // 目前節點符合範圍
            if (isbn.compareTo(low) >= 0
                    && isbn.compareTo(high) <= 0) {

                result.add(node.book);
            }

            // 右子樹可能還有符合資料
            if (isbn.compareTo(high) < 0) {

                rangeRecursive(
                        node.right,
                        low,
                        high,
                        result
                );
            }
        }

        // -----------------------------------------
        // Inorder Report
        // -----------------------------------------
        public void inorderReport() {

            System.out.println(
                    "=== Library Book Inorder Report ==="
            );

            if (root == null) {
                System.out.println("(empty)");
                return;
            }

            inorderRecursive(root);

            System.out.println(
                    "Total books = " + size
            );
        }

        private void inorderRecursive(Node node) {

            if (node == null) {
                return;
            }

            inorderRecursive(node.left);

            System.out.println(node.book);

            inorderRecursive(node.right);
        }

        public int size() {
            return size;
        }
    }

    // =========================================
    // Main Test
    // =========================================
    public static void main(String[] args) {

        BookBST library = new BookBST();

        // =====================================
        // Add
        // =====================================
        System.out.println("=== Add ===");

        System.out.println(
                "add 978003 = "
                        + library.add(
                        new Book(
                                "978003",
                                "Data Structures",
                                "Amy"
                        )
                )
        );

        System.out.println(
                "add 978001 = "
                        + library.add(
                        new Book(
                                "978001",
                                "Java Basics",
                                "Ben"
                        )
                )
        );

        System.out.println(
                "add 978005 = "
                        + library.add(
                        new Book(
                                "978005",
                                "Algorithms",
                                "Cindy"
                        )
                )
        );

        System.out.println(
                "add 978002 = "
                        + library.add(
                        new Book(
                                "978002",
                                "Database",
                                "David"
                        )
                )
        );

        System.out.println(
                "add 978004 = "
                        + library.add(
                        new Book(
                                "978004",
                                "Operating Systems",
                                "Eric"
                        )
                )
        );

        System.out.println(
                "add 978006 = "
                        + library.add(
                        new Book(
                                "978006",
                                "Computer Networks",
                                "Fiona"
                        )
                )
        );


        System.out.println();

        // =====================================
        // Duplicate ISBN
        // =====================================
        System.out.println(
                "=== Duplicate ISBN ==="
        );

        System.out.println(
                "add duplicate 978003 = "
                        + library.add(
                        new Book(
                                "978003",
                                "Duplicate Book",
                                "Other"
                        )
                )
        );


        System.out.println();

        // =====================================
        // Find
        // =====================================
        System.out.println("=== Find ===");

        System.out.println(
                "find 978004 = "
                        + library.find("978004")
        );

        System.out.println(
                "find 999999 = "
                        + library.find("999999")
        );


        System.out.println();

        // =====================================
        // Borrow
        // =====================================
        System.out.println("=== Borrow ===");

        System.out.println(
                "borrow 978002 = "
                        + library.borrow("978002")
        );

        System.out.println(
                "borrow 978002 again = "
                        + library.borrow("978002")
        );

        System.out.println(
                "after borrow = "
                        + library.find("978002")
        );


        System.out.println();

        // =====================================
        // 借出中的書不能 Remove
        // =====================================
        System.out.println(
                "=== Remove Borrowed Book ==="
        );

        System.out.println(
                "remove borrowed 978002 = "
                        + library.remove("978002")
        );

        System.out.println(
                "978002 still exists = "
                        + (library.find("978002") != null)
        );


        System.out.println();

        // =====================================
        // Return
        // =====================================
        System.out.println("=== Return Book ===");

        System.out.println(
                "return 978002 = "
                        + library.returnBook("978002")
        );

        System.out.println(
                "return 978002 again = "
                        + library.returnBook("978002")
        );

        System.out.println(
                "after return = "
                        + library.find("978002")
        );


        System.out.println();

        // =====================================
        // Return 後可以 Remove
        // =====================================
        System.out.println(
                "=== Remove After Return ==="
        );

        System.out.println(
                "remove 978002 = "
                        + library.remove("978002")
        );

        System.out.println(
                "find 978002 = "
                        + library.find("978002")
        );


        System.out.println();

        // =====================================
        // Range Query
        // =====================================
        System.out.println(
                "=== ISBN Range 978003 ~ 978005 ==="
        );

        List<Book> range =
                library.rangeQuery(
                        "978003",
                        "978005"
                );

        for (Book book : range) {
            System.out.println(book);
        }


        System.out.println();

        // low > high
        System.out.println(
                "=== Range low > high ==="
        );

        System.out.println(
                library.rangeQuery(
                        "978006",
                        "978001"
                )
        );


        System.out.println();

        // =====================================
        // Inorder Report
        // =====================================
        library.inorderReport();


        System.out.println();

        // =====================================
        // Remove Missing
        // =====================================
        System.out.println(
                "=== Remove Missing ==="
        );

        System.out.println(
                "remove 999999 = "
                        + library.remove("999999")
        );
    }
}