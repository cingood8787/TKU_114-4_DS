public class WalletHistoryManager {

    static class Transaction {
        private int sequence;
        private String type;
        private int amount;
        private String note;

        public Transaction(int sequence, String type,
                           int amount, String note) {
            this.sequence = sequence;
            this.type = type;
            this.amount = amount;
            this.note = note;
        }

        public int getSequence() {
            return sequence;
        }

        public String getType() {
            return type;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "序號：" + sequence
                    + "，類型：" + type
                    + "，金額：" + amount
                    + "，備註：" + note;
        }
    }

    static class Wallet {
        private String walletId;
        private String owner;
        private int balance;

        private Transaction[] transactions;
        private int transactionCount;
        private int nextSequence;

        public Wallet(String walletId, String owner,
                      int balance, int historySize) {

            this.walletId = walletId;
            this.owner = owner;

            if (balance < 0) {
                this.balance = 0;
            } else {
                this.balance = balance;
            }

            if (historySize < 0) {
                historySize = 0;
            }

            transactions = new Transaction[historySize];
            transactionCount = 0;
            nextSequence = 1;
        }

        // 判斷交易紀錄是否已滿
        private boolean hasSpace() {
            return transactionCount < transactions.length;
        }

        // 新增交易紀錄
        private void addTransaction(String type,
                                    int amount,
                                    String note) {

            transactions[transactionCount] =
                    new Transaction(
                            nextSequence,
                            type,
                            amount,
                            note
                    );

            transactionCount++;
            nextSequence++;
        }

        // 儲值
        public boolean deposit(int amount) {

            if (amount <= 0) {
                return false;
            }

            // 陣列滿了不能改餘額
            if (!hasSpace()) {
                return false;
            }

            balance += amount;

            addTransaction(
                    "DEPOSIT",
                    amount,
                    "儲值"
            );

            return true;
        }

        // 付款
        public boolean pay(int amount) {

            if (amount <= 0) {
                return false;
            }

            if (balance < amount) {
                return false;
            }

            // 陣列滿了不能改餘額
            if (!hasSpace()) {
                return false;
            }

            balance -= amount;

            addTransaction(
                    "PAY",
                    amount,
                    "付款"
            );

            return true;
        }

        // 1. 尋找指定序號交易
        public Transaction findTransaction(int sequence) {

            for (int i = 0; i < transactionCount; i++) {

                if (transactions[i].getSequence()
                        == sequence) {

                    return transactions[i];
                }
            }

            return null;
        }

        // 2. 計算指定交易類型總金額
        public int totalByType(String type) {

            int total = 0;

            if (type == null) {
                return 0;
            }

            for (int i = 0; i < transactionCount; i++) {

                if (transactions[i]
                        .getType()
                        .equalsIgnoreCase(type)) {

                    total += transactions[i].getAmount();
                }
            }

            return total;
        }

        // 3. 轉帳
        public boolean transferTo(Wallet target,
                                  int amount) {

            if (target == null) {
                return false;
            }

            if (target == this) {
                return false;
            }

            if (amount <= 0) {
                return false;
            }

            if (balance < amount) {
                return false;
            }

            // 兩個錢包都必須還有紀錄空間
            if (!this.hasSpace()
                    || !target.hasSpace()) {

                return false;
            }

            // 全部驗證完成後才修改餘額
            this.balance -= amount;
            target.balance += amount;

            // 來源留下紀錄
            this.addTransaction(
                    "TRANSFER_OUT",
                    amount,
                    "轉帳給 " + target.walletId
            );

            // 目標留下紀錄
            target.addTransaction(
                    "TRANSFER_IN",
                    amount,
                    "收到 " + this.walletId + " 轉帳"
            );

            return true;
        }

        // 5. 完整 statement
        public String statement() {

            String result = "";

            result += "====================\n";
            result += "錢包編號：" + walletId + "\n";
            result += "持有人：" + owner + "\n";
            result += "目前餘額：" + balance + "\n";
            result += "交易紀錄：\n";

            if (transactionCount == 0) {
                result += "無交易紀錄\n";
            } else {

                for (int i = 0;
                     i < transactionCount;
                     i++) {

                    result += transactions[i] + "\n";
                }
            }

            result += "====================";

            return result;
        }
    }

    public static void main(String[] args) {

        Wallet wallet1 =
                new Wallet(
                        "W001",
                        "王小明",
                        1000,
                        10
                );

        Wallet wallet2 =
                new Wallet(
                        "W002",
                        "李小華",
                        500,
                        10
                );

        // 儲值
        wallet1.deposit(500);

        // 付款
        wallet1.pay(200);

        // 轉帳
        System.out.println(
                "轉帳結果："
                        + wallet1.transferTo(
                                wallet2, 300
                        )
        );

        // 1. findTransaction()
        System.out.println(
                "\n=== 查詢交易序號 1 ==="
        );

        Transaction found =
                wallet1.findTransaction(1);

        if (found != null) {
            System.out.println(found);
        } else {
            System.out.println("找不到交易");
        }

        // 測試找不到
        System.out.println(
                "\n=== 查詢交易序號 99 ==="
        );

        Transaction notFound =
                wallet1.findTransaction(99);

        System.out.println(notFound);

        // 2. totalByType()
        System.out.println(
                "\nDEPOSIT 總金額："
                        + wallet1.totalByType(
                                "DEPOSIT"
                        )
        );

        System.out.println(
                "PAY 總金額："
                        + wallet1.totalByType(
                                "PAY"
                        )
        );

        System.out.println(
                "TRANSFER_OUT 總金額："
                        + wallet1.totalByType(
                                "TRANSFER_OUT"
                        )
        );

        // 5. 輸出兩個錢包 statement
        System.out.println(
                "\n=== Wallet 1 Statement ==="
        );

        System.out.println(
                wallet1.statement()
        );

        System.out.println(
                "\n=== Wallet 2 Statement ==="
        );

        System.out.println(
                wallet2.statement()
        );
    }
}