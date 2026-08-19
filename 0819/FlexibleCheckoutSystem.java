public class FlexibleCheckoutSystem {

    // ===== Pricing Policy =====

    interface PricingPolicy {
        int calculateFinalPrice(int originalPrice);

        String getName();
    }

    // 原價
    static class RegularPricing implements PricingPolicy {

        @Override
        public int calculateFinalPrice(int originalPrice) {
            return Math.max(originalPrice, 0);
        }

        @Override
        public String getName() {
            return "原價";
        }
    }

    // VIP 八五折
    static class VipPricing implements PricingPolicy {

        @Override
        public int calculateFinalPrice(int originalPrice) {
            if (originalPrice < 0) {
                return 0;
            }

            return (int) Math.round(originalPrice * 0.85);
        }

        @Override
        public String getName() {
            return "VIP 八五折";
        }
    }

    // 滿 2000 折 300
    static class Spend2000DiscountPricing
            implements PricingPolicy {

        @Override
        public int calculateFinalPrice(int originalPrice) {

            if (originalPrice < 0) {
                return 0;
            }

            if (originalPrice >= 2000) {
                return originalPrice - 300;
            }

            return originalPrice;
        }

        @Override
        public String getName() {
            return "滿 2000 折 300";
        }
    }

    // ===== Notification Channel =====

    interface NotificationChannel {

        boolean send(
                String receiver,
                String message
        );

        String getName();
    }

    // Email
    static class EmailNotification
            implements NotificationChannel {

        @Override
        public boolean send(
                String receiver,
                String message) {

            if (receiver == null
                    || receiver.trim().isEmpty()
                    || message == null
                    || message.trim().isEmpty()) {

                return false;
            }

            System.out.println(
                    "[Email] 寄送給 "
                            + receiver
                            + "：" + message
            );

            return true;
        }

        @Override
        public String getName() {
            return "Email";
        }
    }

    // SMS
    static class SmsNotification
            implements NotificationChannel {

        @Override
        public boolean send(
                String receiver,
                String message) {

            if (receiver == null
                    || receiver.trim().isEmpty()
                    || message == null
                    || message.trim().isEmpty()) {

                return false;
            }

            System.out.println(
                    "[SMS] 傳送給 "
                            + receiver
                            + "：" + message
            );

            return true;
        }

        @Override
        public String getName() {
            return "SMS";
        }
    }

    // Console
    static class ConsoleNotification
            implements NotificationChannel {

        @Override
        public boolean send(
                String receiver,
                String message) {

            if (receiver == null
                    || receiver.trim().isEmpty()
                    || message == null
                    || message.trim().isEmpty()) {

                return false;
            }

            System.out.println(
                    "[Console] "
                            + receiver
                            + "：" + message
            );

            return true;
        }

        @Override
        public String getName() {
            return "Console";
        }
    }

    // ===== Checkout Result =====

    static class CheckoutResult {

        private String orderId;
        private int originalPrice;
        private int finalPrice;
        private boolean notificationStatus;

        public CheckoutResult(
                String orderId,
                int originalPrice,
                int finalPrice,
                boolean notificationStatus) {

            this.orderId = orderId;
            this.originalPrice = originalPrice;
            this.finalPrice = finalPrice;
            this.notificationStatus =
                    notificationStatus;
        }

        @Override
        public String toString() {

            return "訂單編號：" + orderId
                    + "，原價：" + originalPrice
                    + "，結帳價：" + finalPrice
                    + "，通知狀態："
                    + (notificationStatus
                    ? "成功"
                    : "失敗");
        }
    }

    // ===== Checkout Service =====

    static class CheckoutService {

        private PricingPolicy pricingPolicy;
        private NotificationChannel notificationChannel;

        public CheckoutService(
                PricingPolicy pricingPolicy,
                NotificationChannel notificationChannel) {

            this.pricingPolicy = pricingPolicy;
            this.notificationChannel =
                    notificationChannel;
        }

        // checkout 回傳 CheckoutResult
        public CheckoutResult checkout(
                String orderId,
                int originalPrice,
                String receiver) {

            int safePrice =
                    Math.max(originalPrice, 0);

            int finalPrice =
                    pricingPolicy
                            .calculateFinalPrice(
                                    safePrice
                            );

            String message =
                    "訂單 " + orderId
                            + " 結帳完成，"
                            + "原價 "
                            + safePrice
                            + " 元，"
                            + "結帳價 "
                            + finalPrice
                            + " 元。";

            boolean notificationStatus =
                    notificationChannel.send(
                            receiver,
                            message
                    );

            return new CheckoutResult(
                    orderId,
                    safePrice,
                    finalPrice,
                    notificationStatus
            );
        }
    }

    public static void main(String[] args) {

        // Pricing Policy
        PricingPolicy regular =
                new RegularPricing();

        PricingPolicy vip =
                new VipPricing();

        PricingPolicy discount =
                new Spend2000DiscountPricing();

        // Notification Channel
        NotificationChannel email =
                new EmailNotification();

        NotificationChannel sms =
                new SmsNotification();

        NotificationChannel console =
                new ConsoleNotification();

        // ===== 測試 1 =====
        System.out.println(
                "=== 測試 1：原價 + Email ==="
        );

        CheckoutService service1 =
                new CheckoutService(
                        regular,
                        email
                );

        CheckoutResult result1 =
                service1.checkout(
                        "O001",
                        1000,
                        "user1@gmail.com"
                );

        System.out.println(result1);

        // ===== 測試 2 =====
        System.out.println(
                "\n=== 測試 2：原價 + SMS ==="
        );

        CheckoutService service2 =
                new CheckoutService(
                        regular,
                        sms
                );

        CheckoutResult result2 =
                service2.checkout(
                        "O002",
                        1500,
                        "0912345678"
                );

        System.out.println(result2);

        // ===== 測試 3 =====
        System.out.println(
                "\n=== 測試 3：VIP + Email ==="
        );

        CheckoutService service3 =
                new CheckoutService(
                        vip,
                        email
                );

        CheckoutResult result3 =
                service3.checkout(
                        "O003",
                        2000,
                        "vip@gmail.com"
                );

        System.out.println(result3);

        // ===== 測試 4 =====
        System.out.println(
                "\n=== 測試 4：VIP + Console ==="
        );

        CheckoutService service4 =
                new CheckoutService(
                        vip,
                        console
                );

        CheckoutResult result4 =
                service4.checkout(
                        "O004",
                        3000,
                        "王小明"
                );

        System.out.println(result4);

        // ===== 測試 5 =====
        System.out.println(
                "\n=== 測試 5：滿2000折300 + SMS ==="
        );

        CheckoutService service5 =
                new CheckoutService(
                        discount,
                        sms
                );

        CheckoutResult result5 =
                service5.checkout(
                        "O005",
                        2500,
                        "0987654321"
                );

        System.out.println(result5);

        // ===== 測試 6 =====
        System.out.println(
                "\n=== 測試 6：滿2000折300 + Console ==="
        );

        CheckoutService service6 =
                new CheckoutService(
                        discount,
                        console
                );

        CheckoutResult result6 =
                service6.checkout(
                        "O006",
                        1800,
                        "李小華"
                );

        System.out.println(result6);
    }
}