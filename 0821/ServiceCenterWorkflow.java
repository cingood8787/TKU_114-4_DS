import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServiceCenterWorkflow {

    static class ServiceTicket {
        private String id;
        private String customerName;
        private String status;

        public ServiceTicket(String id, String customerName) {
            this.id = id;
            this.customerName = customerName;
            this.status = "WAITING";
        }

        public String getId() {
            return id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ServiceTicket{id='" + id
                    + "', customerName='" + customerName
                    + "', status='" + status + "'}";
        }
    }

    // 依 ticket id 查詢
    private Map<String, ServiceTicket> ticketMap = new HashMap<>();

    // 等待 Queue
    private Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();

    // 完成歷程 Stack
    private Deque<ServiceTicket> completedStack = new ArrayDeque<>();

    // 防止重複 id
    private Set<String> ticketIds = new HashSet<>();


    // 建立 Ticket
    public boolean createTicket(String id, String customerName) {

        if (ticketIds.contains(id)) {
            System.out.println("建立失敗，重複 ticket id：" + id);
            return false;
        }

        ServiceTicket ticket = new ServiceTicket(id, customerName);

        ticketIds.add(id);
        ticketMap.put(id, ticket);

        // 從 Queue 尾端加入
        waitingQueue.offerLast(ticket);

        System.out.println("建立成功：" + ticket);

        return true;
    }


    // 處理下一張 Ticket
    public ServiceTicket processNext() {

        ServiceTicket ticket = waitingQueue.pollFirst();

        if (ticket == null) {
            System.out.println("Waiting Queue 為空，沒有 ticket 可以處理");
            return null;
        }

        ticket.setStatus("COMPLETED");

        // 加到完成 Stack 最上方
        completedStack.push(ticket);

        System.out.println("處理完成：" + ticket);

        return ticket;
    }


    // 取消尚未處理的 Ticket
    public boolean cancelWaiting(String id) {

        ServiceTicket ticket = ticketMap.get(id);

        if (ticket == null) {
            System.out.println("取消失敗，找不到 ticket id：" + id);
            return false;
        }

        // 只能取消 WAITING 狀態
        if (!ticket.getStatus().equals("WAITING")) {
            System.out.println(
                    "取消失敗，ticket 已經處理，不能取消：" + id);
            return false;
        }

        boolean removed = waitingQueue.remove(ticket);

        if (!removed) {
            System.out.println("取消失敗，ticket 不在 waiting queue：" + id);
            return false;
        }

        ticket.setStatus("CANCELLED");

        System.out.println("取消成功：" + ticket);

        return true;
    }


    // Undo 最近完成的 Ticket
    public ServiceTicket undoLastCompletion() {

        if (completedStack.isEmpty()) {
            System.out.println("沒有完成紀錄可以 Undo");
            return null;
        }

        // Stack 取出最近完成
        ServiceTicket ticket = completedStack.pop();

        ticket.setStatus("WAITING");

        // 題目要求放回 Queue 前端
        waitingQueue.offerFirst(ticket);

        System.out.println("Undo 完成：" + ticket);

        return ticket;
    }


    // 依 ID 查詢
    public ServiceTicket findById(String id) {

        ServiceTicket ticket = ticketMap.get(id);

        if (ticket == null) {
            System.out.println("查無 ticket：" + id);
        } else {
            System.out.println("查詢結果：" + ticket);
        }

        return ticket;
    }


    // 顯示摘要
    public void printSummary() {

        int waiting = 0;
        int completed = 0;
        int cancelled = 0;

        for (ServiceTicket ticket : ticketMap.values()) {

            if (ticket.getStatus().equals("WAITING")) {
                waiting++;
            } else if (ticket.getStatus().equals("COMPLETED")) {
                completed++;
            } else if (ticket.getStatus().equals("CANCELLED")) {
                cancelled++;
            }
        }

        System.out.println("\n===== Service Center Summary =====");
        System.out.println("全部 Ticket：" + ticketMap.size());
        System.out.println("等待中：" + waiting);
        System.out.println("已完成：" + completed);
        System.out.println("已取消：" + cancelled);

        System.out.println("Waiting Queue：" + waitingQueue);
        System.out.println("Completed Stack：" + completedStack);
    }


    public static void main(String[] args) {

        ServiceCenterWorkflow system =
                new ServiceCenterWorkflow();


        // ======================================
        // 測試空 Queue
        // ======================================
        System.out.println("===== 空 Queue 測試 =====");

        system.processNext();


        // ======================================
        // 建立 Ticket
        // ======================================
        System.out.println("\n===== 建立 Ticket =====");

        system.createTicket("T001", "小明");
        system.createTicket("T002", "小華");
        system.createTicket("T003", "小美");
        system.createTicket("T004", "小強");


        // ======================================
        // 測試重複 ID
        // ======================================
        System.out.println("\n===== 重複 ID 測試 =====");

        system.createTicket("T002", "重複客戶");


        // ======================================
        // 查詢
        // ======================================
        System.out.println("\n===== 查詢 =====");

        system.findById("T001");
        system.findById("T999");


        // ======================================
        // 取消不存在 ID
        // ======================================
        System.out.println("\n===== 取消不存在 ID =====");

        system.cancelWaiting("T999");


        // ======================================
        // 取消等待中的 Ticket
        // ======================================
        System.out.println("\n===== 取消 Waiting Ticket =====");

        system.cancelWaiting("T003");


        // ======================================
        // 處理 Ticket
        // ======================================
        System.out.println("\n===== 處理 Ticket =====");

        system.processNext(); // T001
        system.processNext(); // T002


        // ======================================
        // 測試不能取消已處理 Ticket
        // ======================================
        System.out.println("\n===== 取消已處理 Ticket =====");

        system.cancelWaiting("T001");


        // ======================================
        // 連續兩次 Undo
        // ======================================
        System.out.println("\n===== 連續兩次 Undo =====");

        system.undoLastCompletion(); // T002 放回前端
        system.undoLastCompletion(); // T001 放回前端


        // Queue 現在順序應該：
        // T001, T002, T004
        system.printSummary();


        // ======================================
        // 再依 FIFO 處理
        // ======================================
        System.out.println("\n===== 再次處理 =====");

        system.processNext();
        system.processNext();
        system.processNext();


        // 再測試空 Queue
        system.processNext();


        // 最後摘要
        system.printSummary();
    }
}