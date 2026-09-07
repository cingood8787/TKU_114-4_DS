```java
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class EventSimulationQueue {

    static class Event {
        int time;
        String type;
        int sequence;

        public Event(int time, String type, int sequence) {
            this.time = time;
            this.type = type;
            this.sequence = sequence;
        }

        @Override
        public String toString() {
            return time + "|" + type + "|" + sequence;
        }
    }

    private final PriorityQueue<Event> queue;

    public EventSimulationQueue() {
        queue = new PriorityQueue<>((a, b) -> {

            // 時間越早越優先
            if (a.time != b.time) {
                return Integer.compare(a.time, b.time);
            }

            // 時間相同時 sequence 越小越優先
            return Integer.compare(a.sequence, b.sequence);
        });
    }

    // 加入事件
    public void addEvent(int time, String type, int sequence) {
        queue.offer(new Event(time, type, sequence));
    }

    // 取消指定 sequence 的事件
    public boolean cancelEvent(int sequence) {

        Event target = null;

        for (Event event : queue) {
            if (event.sequence == sequence) {
                target = event;
                break;
            }
        }

        if (target != null) {
            queue.remove(target);
            return true;
        }

        return false;
    }

    // 執行所有事件並回傳紀錄
    public List<String> runAll() {

        List<String> log = new ArrayList<>();

        while (!queue.isEmpty()) {
            Event event = queue.poll();

            String record = "Execute: " + event;
            log.add(record);
        }

        return log;
    }

    // 查詢目前事件數量
    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {

        EventSimulationQueue simulator =
                new EventSimulationQueue();

        // 加入測試事件
        simulator.addEvent(10, "LOGIN", 1);
        simulator.addEvent(5, "START", 2);
        simulator.addEvent(10, "MESSAGE", 3);
        simulator.addEvent(3, "INIT", 4);
        simulator.addEvent(5, "UPDATE", 5);
        simulator.addEvent(10, "LOGOUT", 6);

        System.out.println(
                "Event count = " + simulator.size()
        );

        // 取消事件
        boolean cancelled = simulator.cancelEvent(3);

        System.out.println(
                "Cancel sequence 3 = " + cancelled
        );

        // 測試取消不存在事件
        boolean cancelled2 = simulator.cancelEvent(99);

        System.out.println(
                "Cancel sequence 99 = " + cancelled2
        );

        System.out.println();
        System.out.println("=== Execution Log ===");

        List<String> log = simulator.runAll();

        for (String record : log) {
            System.out.println(record);
        }

        System.out.println();
        System.out.println(
                "Remaining events = " + simulator.size()
        );
    }
}
```
