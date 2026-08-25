import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class DeliveryWorkflowSystem {

    static class Delivery {
        private String id;
        private String address;
        private String status;

        public Delivery(String id, String address) {
            this.id = id;
            this.address = address;
            this.status = "WAITING";
        }

        public String getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Delivery{id='" + id
                    + "', address='" + address
                    + "', status='" + status + "'}";
        }
    }

    // 依配送編號查詢
    private Map<String, Delivery> deliveryMap = new HashMap<>();

    // 等待配送 Queue
    private Queue<Delivery> waitingQueue = new ArrayDeque<>();

    // 已完成歷程 Stack
    private Deque<Delivery> completedStack = new ArrayDeque<>();


    // 新增配送
    public boolean addDelivery(String id, String address) {

        // 重複 id 不得加入
        if (deliveryMap.containsKey(id)) {
            System.out.println("新增失敗，重複配送編號：" + id);
            return false;
        }

        Delivery delivery = new Delivery(id, address);

        deliveryMap.put(id, delivery);
        waitingQueue.offer(delivery);

        System.out.println("新增配送：" + delivery);

        return true;
    }


    // 處理下一筆配送
    public Delivery processNext() {

        Delivery delivery = waitingQueue.poll();

        if (delivery == null) {
            System.out.println("目前沒有等待配送");
            return null;
        }

        delivery.setStatus("COMPLETED");

        // 放入完成 Stack
        completedStack.push(delivery);

        System.out.println("完成配送：" + delivery);

        return delivery;
    }


    // Undo 最近一次完成配送
    public Delivery undo() {

        if (completedStack.isEmpty()) {
            System.out.println("沒有可以 Undo 的配送");
            return null;
        }

        Delivery delivery = completedStack.pop();

        delivery.setStatus("WAITING");

        // Undo 後重新放回等待 Queue
        waitingQueue.offer(delivery);

        System.out.println("Undo：" + delivery);

        return delivery;
    }


    // 依 ID 查詢
    public Delivery findById(String id) {

        Delivery delivery = deliveryMap.get(id);

        if (delivery == null) {
            System.out.println("查無配送編號：" + id);
        } else {
            System.out.println("查詢結果：" + delivery);
        }

        return delivery;
    }


    // 顯示統計
    public void showStatistics() {

        System.out.println("===== 配送統計 =====");
        System.out.println("全部配送數：" + deliveryMap.size());
        System.out.println("等待配送數：" + waitingQueue.size());
        System.out.println("已完成配送數：" + completedStack.size());
    }


    // 顯示等待 Queue
    public void showWaitingQueue() {
        System.out.println("等待配送 Queue：" + waitingQueue);
    }


    // 顯示完成 Stack
    public void showCompletedStack() {
        System.out.println("完成歷程 Stack：" + completedStack);
    }


    public static void main(String[] args) {

        DeliveryWorkflowSystem system =
                new DeliveryWorkflowSystem();


        // 新增配送
        system.addDelivery("D001", "台北市");
        system.addDelivery("D002", "新北市");
        system.addDelivery("D003", "桃園市");

        // 測試重複 ID
        system.addDelivery("D002", "台中市");

        System.out.println();

        system.showWaitingQueue();
        system.showStatistics();


        // 處理配送
        System.out.println("\n===== 處理配送 =====");

        system.processNext();
        system.processNext();

        system.showWaitingQueue();
        system.showCompletedStack();
        system.showStatistics();


        // 查詢
        System.out.println("\n===== 查詢 =====");

        system.findById("D001");
        system.findById("D003");
        system.findById("D999");


        // Undo
        System.out.println("\n===== Undo =====");

        system.undo();

        system.showWaitingQueue();
        system.showCompletedStack();
        system.showStatistics();


        // 繼續處理
        System.out.println("\n===== 繼續配送 =====");

        system.processNext();
        system.processNext();

        system.showStatistics();
    }
}