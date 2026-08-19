public class DeliveryStrategySystem {

    // 配送方式介面
    interface DeliveryMethod {

        int calculateFee(int orderAmount);

        String getEstimate();
    }

    // 宅配
    static class HomeDelivery implements DeliveryMethod {

        @Override
        public int calculateFee(int orderAmount) {
            if (orderAmount >= 1000) {
                return 0;
            }

            return 100;
        }

        @Override
        public String getEstimate() {
            return "預計 1～2 個工作天送達";
        }
    }

    // 超商取貨
    static class StorePickup implements DeliveryMethod {

        @Override
        public int calculateFee(int orderAmount) {
            if (orderAmount >= 800) {
                return 0;
            }

            return 60;
        }

        @Override
        public String getEstimate() {
            return "預計 2～3 個工作天到店";
        }
    }

    // 自取
    static class SelfPickup implements DeliveryMethod {

        @Override
        public int calculateFee(int orderAmount) {
            return 0;
        }

        @Override
        public String getEstimate() {
            return "可於營業時間內自行取貨";
        }
    }

    // OrderService 使用 Composition 保存 DeliveryMethod
    static class OrderService {

        private String orderId;
        private int orderAmount;
        private DeliveryMethod deliveryMethod;

        public OrderService(
                String orderId,
                int orderAmount,
                DeliveryMethod deliveryMethod) {

            this.orderId = orderId;

            if (orderAmount < 0) {
                this.orderAmount = 0;
            } else {
                this.orderAmount = orderAmount;
            }

            this.deliveryMethod = deliveryMethod;
        }

        public int getDeliveryFee() {
            if (deliveryMethod == null) {
                return 0;
            }

            return deliveryMethod.calculateFee(orderAmount);
        }

        public String getEstimate() {
            if (deliveryMethod == null) {
                return "未設定配送方式";
            }

            return deliveryMethod.getEstimate();
        }

        public int getTotalAmount() {
            return orderAmount + getDeliveryFee();
        }

        public void printSummary() {

            System.out.println("訂單編號：" + orderId);
            System.out.println("商品金額：" + orderAmount);
            System.out.println("運費：" + getDeliveryFee());
            System.out.println("總金額：" + getTotalAmount());
            System.out.println("配送說明：" + getEstimate());
        }
    }

    public static void main(String[] args) {

        DeliveryMethod homeDelivery =
                new HomeDelivery();

        DeliveryMethod storePickup =
                new StorePickup();

        DeliveryMethod selfPickup =
                new SelfPickup();

        OrderService order1 =
                new OrderService(
                        "O001",
                        700,
                        homeDelivery
                );

        OrderService order2 =
                new OrderService(
                        "O002",
                        600,
                        storePickup
                );

        OrderService order3 =
                new OrderService(
                        "O003",
                        500,
                        selfPickup
                );

        System.out.println("=== 宅配訂單 ===");
        order1.printSummary();

        System.out.println("\n=== 超商取貨訂單 ===");
        order2.printSummary();

        System.out.println("\n=== 自取訂單 ===");
        order3.printSummary();
    }
}