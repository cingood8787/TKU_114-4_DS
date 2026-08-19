public class BookArrayReport {

    static class Book {
        private String id;
        private String title;
        private double price;
        private int stock;

        public Book(String id, String title, double price, int stock) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.stock = stock;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        @Override
        public String toString() {
            return "書號：" + id
                    + "，書名：" + title
                    + "，價格：" + price
                    + "，庫存：" + stock;
        }
    }

    public static void main(String[] args) {

        // 建立 Book 物件陣列
        Book[] books = {
                new Book("B001", "Java程式設計", 500, 5),
                new Book("B002", "資料結構", 650, 2),
                new Book("B003", "資料庫管理", 450, 8),
                new Book("B004", "網頁程式設計", 700, 3)
        };

        // 1. 輸出所有書籍
        System.out.println("=== 所有書籍 ===");

        for (Book book : books) {
            System.out.println(book);
        }

        // 2. 計算庫存總價值
        double totalValue = 0;

        for (Book book : books) {
            totalValue += book.getPrice() * book.getStock();
        }

        System.out.println("\n庫存總價值：" + totalValue);

        // 3. 找出價格最高的書
        Book mostExpensive = books[0];

        for (Book book : books) {
            if (book.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = book;
            }
        }

        System.out.println("\n=== 價格最高的書 ===");
        System.out.println(mostExpensive);

        // 4. 輸出庫存小於或等於 3 的書
        System.out.println("\n=== 庫存小於或等於 3 的書 ===");

        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.println(book);
            }
        }
    }
}