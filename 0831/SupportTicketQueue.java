```java
import java.util.PriorityQueue;

public class SupportTicketQueue {

    // Ticket 類別
    static class Ticket {
        String id;
        int severity;
        int createdOrder;

        public Ticket(String id, int severity, int createdOrder) {
            this.id = id;
            this.severity = severity;
            this.createdOrder = createdOrder;
        }

        @Override
        public String toString() {
            return id + "|" + severity + "|" + createdOrder;
        }
    }

    public static void main(String[] args) {

        // severity 越大越優先
        // severity 相同時 createdOrder 越小越優先
        PriorityQueue<Ticket> queue = new PriorityQueue<>(
            (a, b) -> {
                if (a.severity != b.severity) {
                    return Integer.compare(b.severity, a.severity);
                }

                return Integer.compare(a.createdOrder, b.createdOrder);
            }
        );

        // 測試資料
        queue.add(new Ticket("T001", 3, 1));
        queue.add(new Ticket("T002", 5, 2));
        queue.add(new Ticket("T003", 3, 3));
        queue.add(new Ticket("T004", 5, 4));
        queue.add(new Ticket("T005", 1, 5));
        queue.add(new Ticket("T006", 5, 6));

        // 依照優先順序取出全部 Ticket
        while (!queue.isEmpty()) {
            Ticket ticket = queue.poll();
            System.out.println(ticket);
        }
    }
}
```
