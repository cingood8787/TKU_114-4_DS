public class DigitalWalletSystem {

    static class DigitalWallet {
        private String walletId;
        private String owner;
        private double balance;
        private int transactionCount;

        // Constructor
        public DigitalWallet(String walletId, String owner, double balance) {
            this.walletId = walletId;
            this.owner = owner;

            if (balance < 0) {
                this.balance = 0;
            } else {
                this.balance = balance;
            }

            this.transactionCount = 0;
        }

        // 儲值
        public boolean deposit(double amount) {
            if (amount <= 0) {
                return false;
            }

            balance += amount;
            transactionCount++;
            return true;
        }

        // 付款
        public boolean pay(double amount) {
            if (amount <= 0 || amount > balance) {
                return false;
            }

            balance -= amount;
            transactionCount++;
            return true;
        }

        // 退款
        public boolean refund(double amount) {
            if (amount <= 0) {
                return false;
            }

            balance += amount;
            transactionCount++;
            return true;
        }

        public double getBalance() {
            return balance;
        }

        public int getTransactionCount() {
            return transactionCount;
        }

        @Override
        public String toString() {
            return "錢包編號：" + walletId
                    + "，持有人：" + owner
                    + "，餘額：" + balance
                    + "，交易次數：" + transactionCount;
        }
    }

    public static void main(String[] args) {

        DigitalWallet wallet =
                new DigitalWallet("W001", "王小明", 1000);

        System.out.println("=== 初始狀態 ===");
        System.out.println(wallet);

        // 1. 正常儲值
        System.out.println("\n=== 正常儲值 500 ===");
        System.out.println("儲值結果：" + wallet.deposit(500));
        System.out.println(wallet);

        // 2. 正常付款
        System.out.println("\n=== 正常付款 300 ===");
        System.out.println("付款結果：" + wallet.pay(300));
        System.out.println(wallet);

        // 3. 餘額不足
        System.out.println("\n=== 付款 2000（餘額不足）===");
        System.out.println("付款結果：" + wallet.pay(2000));
        System.out.println(wallet);

        // 4. 負數金額
        System.out.println("\n=== 儲值 -100（不合法）===");
        System.out.println("儲值結果：" + wallet.deposit(-100));
        System.out.println(wallet);

        System.out.println("\n=== 付款 -100（不合法）===");
        System.out.println("付款結果：" + wallet.pay(-100));
        System.out.println(wallet);

        // 5. 退款
        System.out.println("\n=== 退款 200 ===");
        System.out.println("退款結果：" + wallet.refund(200));
        System.out.println(wallet);

        System.out.println("\n=== 最終結果 ===");
        System.out.println("目前餘額：" + wallet.getBalance());
        System.out.println("成功交易次數：" + wallet.getTransactionCount());
    }
}