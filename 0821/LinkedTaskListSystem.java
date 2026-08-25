public class LinkedTaskListSystem {

    // =========================
    // Task
    // =========================
    static class Task {
        private String id;
        private String title;

        public Task(String id, String title) {
            this.id = id;
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "Task{id='" + id + "', title='" + title + "'}";
        }
    }


    // =========================
    // TaskNode
    // =========================
    static class TaskNode {
        Task task;
        TaskNode next;

        public TaskNode(Task task) {
            this.task = task;
            this.next = null;
        }
    }


    // =========================
    // TaskLinkedList
    // =========================
    static class TaskLinkedList {

        private TaskNode head;
        private int size;

        public TaskLinkedList() {
            head = null;
            size = 0;
        }

        // 加到最前面
        public boolean addFirst(Task task) {

            if (findById(task.getId()) != null) {
                System.out.println("新增失敗，重複 id：" + task.getId());
                return false;
            }

            TaskNode newNode = new TaskNode(task);

            newNode.next = head;
            head = newNode;

            size++;

            return true;
        }


        // 加到最後面
        public boolean addLast(Task task) {

            if (findById(task.getId()) != null) {
                System.out.println("新增失敗，重複 id：" + task.getId());
                return false;
            }

            TaskNode newNode = new TaskNode(task);

            // 空 List
            if (head == null) {
                head = newNode;
                size++;
                return true;
            }

            TaskNode current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;

            size++;

            return true;
        }


        // 依 ID 尋找
        public Task findById(String id) {

            TaskNode current = head;

            while (current != null) {

                if (current.task.getId().equals(id)) {
                    return current.task;
                }

                current = current.next;
            }

            return null;
        }


        // 依 ID 刪除
        public boolean removeById(String id) {

            // 空 List
            if (head == null) {
                System.out.println("List 為空，無法刪除：" + id);
                return false;
            }

            // 刪除 head
            if (head.task.getId().equals(id)) {

                head = head.next;
                size--;

                System.out.println("刪除成功：" + id);
                return true;
            }

            TaskNode current = head;

            while (current.next != null) {

                if (current.next.task.getId().equals(id)) {

                    current.next = current.next.next;
                    size--;

                    System.out.println("刪除成功：" + id);
                    return true;
                }

                current = current.next;
            }

            System.out.println("找不到 id：" + id);

            return false;
        }


        // 插入指定 ID 後面
        public boolean insertAfter(String existingId, Task task) {

            // 新 task ID 不得重複
            if (findById(task.getId()) != null) {
                System.out.println("新增失敗，重複 id：" + task.getId());
                return false;
            }

            TaskNode current = head;

            while (current != null) {

                if (current.task.getId().equals(existingId)) {

                    TaskNode newNode = new TaskNode(task);

                    newNode.next = current.next;
                    current.next = newNode;

                    size++;

                    return true;
                }

                current = current.next;
            }

            System.out.println("找不到 existingId：" + existingId);

            return false;
        }


        // 回傳目前節點數量
        public int size() {
            return size;
        }


        // 顯示所有 Task
        public void printAll() {

            System.out.println("===== Task List =====");

            if (head == null) {
                System.out.println("List 為空");
                return;
            }

            TaskNode current = head;

            while (current != null) {
                System.out.println(current.task);
                current = current.next;
            }

            System.out.println("size = " + size);
        }
    }


    // =========================
    // Main 測試
    // =========================
    public static void main(String[] args) {

        TaskLinkedList list = new TaskLinkedList();


        // =================================
        // 1. 測試空 List
        // =================================
        System.out.println("===== 測試空 List =====");

        list.printAll();

        System.out.println("搜尋 T001：" + list.findById("T001"));

        list.removeById("T001");


        // =================================
        // 2. 新增資料
        // =================================
        System.out.println("\n===== 新增資料 =====");

        list.addFirst(new Task("T001", "寫作業"));

        list.addLast(new Task("T002", "讀書"));
        list.addLast(new Task("T003", "運動"));
        list.addLast(new Task("T004", "整理資料"));

        list.printAll();


        // =================================
        // 3. 測試重複 ID
        // =================================
        System.out.println("\n===== 測試重複 ID =====");

        list.addLast(new Task("T002", "重複任務"));


        // =================================
        // 4. findById
        // =================================
        System.out.println("\n===== 搜尋 =====");

        System.out.println(
                "找到 T003：" + list.findById("T003")
        );

        System.out.println(
                "找到 T999：" + list.findById("T999")
        );


        // =================================
        // 5. insertAfter
        // =================================
        System.out.println("\n===== insertAfter =====");

        list.insertAfter(
                "T002",
                new Task("T005", "準備報告")
        );

        list.printAll();


        // =================================
        // 6. 刪除 Head
        // =================================
        System.out.println("\n===== 刪除 Head =====");

        list.removeById("T001");

        list.printAll();


        // =================================
        // 7. 刪除 Middle
        // =================================
        System.out.println("\n===== 刪除 Middle =====");

        list.removeById("T005");

        list.printAll();


        // =================================
        // 8. 刪除 Tail
        // =================================
        System.out.println("\n===== 刪除 Tail =====");

        list.removeById("T004");

        list.printAll();


        // =================================
        // 9. 找不到 ID
        // =================================
        System.out.println("\n===== 找不到 ID =====");

        list.removeById("T999");

        list.insertAfter(
                "T999",
                new Task("T006", "不存在的位置")
        );


        // 最後結果
        System.out.println("\n===== 最後結果 =====");

        list.printAll();
    }
}