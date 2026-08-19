public class AccountTransferService {

    static class Account {
        private String accountId;
        private String owner;
        private int balance;

        public Account(String accountId, String owner, int balance) {
            this.accountId = accountId;
            this.owner = owner;
            this.balance = Math.max(balance, 0);
        }

        public int getBalance() {
            return balance;
        }

        private void deposit(int amount) {
            balance += amount;
        }

        private void withdraw(int amount) {
            balance -= amount;
        }

        @Override
        public String toString() {
            return "帳戶：" + accountId
                    + "，持有人：" + owner
                    + "，餘額：" + balance;
        }
    }

    static class TransferService {

        public boolean transfer(Account source,
                                Account target,
                                int amount) {

            // 先完成所有驗證
            if (source == null || target == null) {
                return false;
            }

            if (source == target) {
                return false;
            }

            if (amount <= 0) {
                return false;
            }

            if (source.getBalance() < amount) {
                return false;
            }

            // 全部驗證成功後才改變帳戶狀態
            source.withdraw(amount);
            target.deposit(amount);

            return true;
        }
    }

    public static void main(String[] args) {

        Account account1 =
                new Account("A001", "王小明", 1000);

        Account account2 =
                new Account("A002", "李小華", 500);

        TransferService service =
                new TransferService();

        System.out.println("=== 初始狀態 ===");
        System.out.println(account1);
        System.out.println(account2);

        // 1. 成功轉帳
        System.out.println("\n=== 成功轉帳 300 ===");
        System.out.println(
                "結果：" + service.transfer(
                        account1, account2, 300
                )
        );

        System.out.println(account1);
        System.out.println(account2);

        // 2. 餘額不足
        System.out.println("\n=== 餘額不足轉帳 1000 ===");
        System.out.println(
                "結果：" + service.transfer(
                        account1, account2, 1000
                )
        );

        System.out.println(account1);
        System.out.println(account2);

        // 3. 同帳戶轉帳
        System.out.println("\n=== 同帳戶轉帳 ===");
        System.out.println(
                "結果：" + service.transfer(
                        account1, account1, 100
                )
        );

        System.out.println(account1);
        System.out.println(account2);

        // 4. null 目標
        System.out.println("\n=== null 目標 ===");
        System.out.println(
                "結果：" + service.transfer(
                        account1, null, 100
                )
        );

        System.out.println(account1);
        System.out.println(account2);
    }
}