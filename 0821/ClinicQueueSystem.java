import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ClinicQueueSystem {

    // 病患類別
    static class Patient {
        private String medicalRecordNo;
        private String name;

        public Patient(String medicalRecordNo, String name) {
            this.medicalRecordNo = medicalRecordNo;
            this.name = name;
        }

        public String getMedicalRecordNo() {
            return medicalRecordNo;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return medicalRecordNo + " - " + name;
        }
    }

    // 等候 Queue
    private Deque<Patient> waitingQueue = new ArrayDeque<>();

    // 當日完成清單
    private List<Patient> completedList = new ArrayList<>();

    // 一般掛號
    public void register(Patient patient) {
        waitingQueue.offerLast(patient);
        System.out.println("掛號成功：" + patient);
    }

    // 取消指定病歷號
    public boolean cancel(String medicalRecordNo) {

        for (Patient patient : waitingQueue) {

            if (patient.getMedicalRecordNo().equals(medicalRecordNo)) {
                waitingQueue.remove(patient);

                System.out.println("取消掛號：" + patient);
                return true;
            }
        }

        System.out.println("找不到病歷號：" + medicalRecordNo);
        return false;
    }

    // 查看下一位
    public Patient nextPatient() {

        Patient patient = waitingQueue.peekFirst();

        if (patient == null) {
            System.out.println("目前沒有候診病患");
        } else {
            System.out.println("下一位：" + patient);
        }

        return patient;
    }

    // 叫號
    public Patient callNext() {

        // pollFirst 維持 FIFO
        Patient patient = waitingQueue.pollFirst();

        if (patient == null) {
            System.out.println("目前沒有病患可以叫號");
            return null;
        }

        completedList.add(patient);

        System.out.println("叫號：" + patient);

        return patient;
    }

    // 顯示目前候診隊伍
    public void showWaitingQueue() {
        System.out.println("候診 Queue：" + waitingQueue);
    }

    // 顯示當日完成清單
    public void showCompletedList() {

        System.out.println("===== 當日完成清單 =====");

        if (completedList.isEmpty()) {
            System.out.println("目前尚無完成病患");
            return;
        }

        for (Patient patient : completedList) {
            System.out.println(patient);
        }
    }

    public static void main(String[] args) {

        ClinicQueueSystem clinic = new ClinicQueueSystem();

        // 一般掛號
        clinic.register(new Patient("P001", "小明"));
        clinic.register(new Patient("P002", "小華"));
        clinic.register(new Patient("P003", "小美"));
        clinic.register(new Patient("P004", "小強"));

        clinic.showWaitingQueue();

        // 查看下一位
        clinic.nextPatient();

        // FIFO 叫號
        clinic.callNext();

        clinic.showWaitingQueue();

        // 取消指定病歷號
        clinic.cancel("P003");

        clinic.showWaitingQueue();

        // 再查看下一位
        clinic.nextPatient();

        // 繼續叫號
        clinic.callNext();
        clinic.callNext();

        // 空 Queue 測試
        clinic.callNext();
        clinic.nextPatient();

        // 顯示當日完成清單
        clinic.showCompletedList();
    }
}