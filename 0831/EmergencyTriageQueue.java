```java
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class EmergencyTriageQueue {

    static class Patient {
        String medicalRecordNo;
        int severity;
        int arrivalOrder;

        public Patient(String medicalRecordNo, int severity, int arrivalOrder) {
            this.medicalRecordNo = medicalRecordNo;
            this.severity = severity;
            this.arrivalOrder = arrivalOrder;
        }

        @Override
        public String toString() {
            return medicalRecordNo + "|" + severity + "|" + arrivalOrder;
        }
    }

    private final PriorityQueue<Patient> queue;

    public EmergencyTriageQueue() {

        queue = new PriorityQueue<>((a, b) -> {

            // 危急程度越高，優先度越高
            if (a.severity != b.severity) {
                return Integer.compare(b.severity, a.severity);
            }

            // 危急程度相同，到院順序越小越優先
            if (a.arrivalOrder != b.arrivalOrder) {
                return Integer.compare(a.arrivalOrder, b.arrivalOrder);
            }

            // 前兩者相同時，用病歷號維持穩定排序
            return a.medicalRecordNo.compareTo(b.medicalRecordNo);
        });
    }

    // 報到
    public void checkIn(String medicalRecordNo,
                        int severity,
                        int arrivalOrder) {

        queue.offer(
                new Patient(
                        medicalRecordNo,
                        severity,
                        arrivalOrder
                )
        );
    }

    // 查看下一位，但不移除
    public Patient peekNext() {

        if (queue.isEmpty()) {
            throw new NoSuchElementException(
                    "No patient waiting"
            );
        }

        return queue.peek();
    }

    // 叫號並移除
    public Patient callNext() {

        if (queue.isEmpty()) {
            throw new NoSuchElementException(
                    "No patient waiting"
            );
        }

        return queue.poll();
    }

    // 目前候診人數
    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {

        EmergencyTriageQueue triage =
                new EmergencyTriageQueue();

        // 報到測試
        triage.checkIn("P001", 3, 1);
        triage.checkIn("P002", 5, 2);
        triage.checkIn("P003", 4, 3);
        triage.checkIn("P004", 5, 4);
        triage.checkIn("P005", 3, 5);
        triage.checkIn("P006", 5, 6);

        System.out.println(
                "Waiting count = " + triage.size()
        );

        System.out.println(
                "Next patient = " + triage.peekNext()
        );

        System.out.println();
        System.out.println("=== Call Patients ===");

        while (!triage.isEmpty()) {

            Patient patient = triage.callNext();

            System.out.println(
                    "Call: " + patient
            );

            System.out.println(
                    "Remaining: " + triage.size()
            );
        }

        // 空佇列處理
        System.out.println();
        System.out.println("=== Empty Queue Test ===");

        try {
            triage.peekNext();
        } catch (NoSuchElementException e) {
            System.out.println(
                    "peekNext(): queue is empty"
            );
        }

        try {
            triage.callNext();
        } catch (NoSuchElementException e) {
            System.out.println(
                    "callNext(): queue is empty"
            );
        }
    }
}
```
