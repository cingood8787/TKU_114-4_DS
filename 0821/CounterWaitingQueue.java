import java.util.ArrayDeque;
import java.util.Deque;

public class CounterWaitingQueue {

    // 顧客類別
    static class Customer {
        private String name;

        public Customer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // 使用 Deque 管理 FIFO 隊列
    private Deque<Customer> queue = new ArrayDeque<>();

    // 加入顧客
    public void addCustomer(Customer customer) {
        queue.offerLast(customer);
        System.out.println(customer + " 已加入隊伍");
    }

    // 查看下一位
    public Customer nextCustomer() {
        Customer customer = queue.peekFirst();

        if (customer == null) {
            System.out.println("目前沒有等候顧客");
        } else {
            System.out.println("下一位顧客：" + customer);
        }

        return customer;
    }

    // 服務下一位
    public Customer serveNext() {
        Customer customer = queue.pollFirst();

        if (customer == null) {
            System.out.println("目前沒有顧客可以服務");
        } else {
            System.out.println("正在服務：" + customer);
        }

        return customer;
    }

    // 顯示等候人數
    public int waitingCount() {
        return queue.size();
    }

    public static void main(String[] args) {

        CounterWaitingQueue counter = new CounterWaitingQueue();

        // 加入顧客
        counter.addCustomer(new Customer("小明"));
        counter.addCustomer(new Customer("小華"));
        counter.addCustomer(new Customer("小美"));

        System.out.println("等候人數：" + counter.waitingCount());

        // 查看下一位
        counter.nextCustomer();

        // 依照 FIFO 順序服務
        counter.serveNext();
        counter.serveNext();

        System.out.println("等候人數：" + counter.waitingCount());

        counter.serveNext();

        // 測試空隊列
        counter.serveNext();
        counter.nextCustomer();

        System.out.println("等候人數：" + counter.waitingCount());
    }
}