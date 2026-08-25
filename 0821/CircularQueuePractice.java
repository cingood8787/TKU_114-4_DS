import java.util.Arrays;

public class CircularQueuePractice {

    static class CircularQueue<T> {

        private Object[] data;
        private int front;
        private int rear;
        private int size;

        public CircularQueue(int capacity) {
            data = new Object[capacity];
            front = 0;
            rear = 0;
            size = 0;
        }

        // 加入元素
        public boolean enqueue(T value) {
            if (isFull()) {
                System.out.println("Queue 已滿，無法加入：" + value);
                return false;
            }

            data[rear] = value;

            // 使用 modulo 循環
            rear = (rear + 1) % data.length;

            size++;
            return true;
        }

        // 取出元素
        @SuppressWarnings("unchecked")
        public T dequeue() {
            if (isEmpty()) {
                System.out.println("Queue 為空，無法 dequeue");
                return null;
            }

            T value = (T) data[front];

            // 清除原本位置
            data[front] = null;

            // 使用 modulo 循環
            front = (front + 1) % data.length;

            size--;

            return value;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == data.length;
        }

        public int size() {
            return size;
        }

        // 顯示內部狀態
        public void printState() {
            System.out.println(
                    "array = " + Arrays.toString(data)
                    + ", front = " + front
                    + ", rear = " + rear
                    + ", size = " + size
            );
        }
    }

    public static void main(String[] args) {

        // 容量 4
        CircularQueue<String> queue = new CircularQueue<>(4);

        System.out.println("===== Circular Queue 狀態追蹤 =====");

        queue.enqueue("A");
        System.out.print("enqueue A -> ");
        queue.printState();

        queue.enqueue("B");
        System.out.print("enqueue B -> ");
        queue.printState();

        queue.enqueue("C");
        System.out.print("enqueue C -> ");
        queue.printState();


        System.out.println("dequeue = " + queue.dequeue());
        queue.printState();

        System.out.println("dequeue = " + queue.dequeue());
        queue.printState();


        queue.enqueue("D");
        System.out.print("enqueue D -> ");
        queue.printState();

        queue.enqueue("E");
        System.out.print("enqueue E -> ");
        queue.printState();

        queue.enqueue("F");
        System.out.print("enqueue F -> ");
        queue.printState();


        System.out.println("dequeue = " + queue.dequeue());
        queue.printState();


        queue.enqueue("G");
        System.out.print("enqueue G -> ");
        queue.printState();


        // 最後依 FIFO 順序全部取出
        System.out.println("\n===== 最後依 FIFO 順序取出 =====");

        while (!queue.isEmpty()) {
            System.out.println("取出：" + queue.dequeue());
            queue.printState();
        }
    }
}