public class CustomerOrderSystem {

    static class Customer {
        private String customerId;
        private String name;

        public Customer(String customerId, String name) {
            this.customerId = customerId;
            this.name = name;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "顧客編號：" + customerId
                    + "，姓名：" + name;
        }
    }

    static class OrderItem {
        private String productName;
        private double price;
        private int quantity;

        public OrderItem(String productName, double price, int quantity) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        public double getSubtotal() {
            return price * quantity;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return "商品：" + productName
                    + "，單價：" + price
                    + "，數量：" + quantity
                    + "，小計：" + getSubtotal();
        }
    }

    static class CustomerOrder {
        private String orderId;
        private Customer customer;
        private OrderItem[] items;

        public CustomerOrder(String orderId,
                             Customer customer,
                             OrderItem[] items) {
            this.orderId = orderId;
            this.customer = customer;
            this.items = items;
        }

        // 計算訂單總額
        public double getTotalAmount() {
            double total = 0;

            for (OrderItem item : items) {
                if (item != null) {
                    total += item.getSubtotal();
                }
            }

            return total;
        }

        // 計算商品總數量
        public int getTotalQuantity() {
            int totalQuantity = 0;

            for (OrderItem item : items) {
                if (item != null) {
                    totalQuantity += item.getQuantity();
                }
            }

            return totalQuantity;
        }

        // 訂單摘要
        public String summary() {
            String result = "訂單編號：" + orderId + "\n";
            result += customer + "\n";
            result += "=== 訂單品項 ===\n";

            for (OrderItem item : items) {
                if (item != null) {
                    result += item + "\n";
                }
            }

            result += "商品總數量：" + getTotalQuantity() + "\n";
            result += "訂單總額：" + getTotalAmount();

            return result;
        }
    }

    public static void main(String[] args) {

        // 建立顧客
        Customer customer =
                new Customer("C001", "王小明");

        // 固定長度的 OrderItem[]
        OrderItem[] items = new OrderItem[3];

        items[0] = new OrderItem(
                "鍵盤", 800, 1
        );

        items[1] = new OrderItem(
                "滑鼠", 500, 2
        );

        items[2] = new OrderItem(
                "耳機", 1200, 1
        );

        // 建立訂單
        CustomerOrder order =
                new CustomerOrder(
                        "O001",
                        customer,
                        items
                );

        System.out.println(order.summary());
    }
}