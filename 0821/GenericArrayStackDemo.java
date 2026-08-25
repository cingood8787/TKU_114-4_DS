public class GenericArrayStackDemo {

    // Generic Stack
    static class ArrayStack<T> {

        private T[] data;
        private int top;

        // 建立固定容量 Stack
        @SuppressWarnings("unchecked")
        public ArrayStack(int capacity) {
            data = (T[]) new Object[capacity];
            top = 0;
        }

        // 放入元素
        public boolean push(T value) {
            if (isFull()) {
                System.out.println("Stack 已滿，無法加入：" + value);
                return false;
            }

            data[top] = value;
            top++;
            return true;
        }

        // 取出最上面的元素
        public T pop() {
            if (isEmpty()) {
                System.out.println("Stack 為空，無法 pop");
                return null;
            }

            top--;
            T value = data[top];
            data[top] = null;

            return value;
        }

        // 查看最上面的元素
        public T peek() {
            if (isEmpty()) {
                System.out.println("Stack 為空，無法 peek");
                return null;
            }

            return data[top - 1];
        }

        // 目前元素數量
        public int size() {
            return top;
        }

        // 是否為空
        public boolean isEmpty() {
            return top == 0;
        }

        // 是否已滿
        public boolean isFull() {
            return top == data.length;
        }
    }

    public static void main(String[] args) {

        // ===== String Stack 測試 =====
        System.out.println("===== ArrayStack<String> =====");

        ArrayStack<String> stringStack = new ArrayStack<>(3);

        stringStack.push("Java");
        stringStack.push("Python");
        stringStack.push("C++");

        System.out.println("size = " + stringStack.size());
        System.out.println("peek = " + stringStack.peek());
        System.out.println("isFull = " + stringStack.isFull());

        // 測試容量已滿
        stringStack.push("JavaScript");

        System.out.println("pop = " + stringStack.pop());
        System.out.println("pop = " + stringStack.pop());

        System.out.println("size = " + stringStack.size());


        // ===== Integer Stack 測試 =====
        System.out.println("\n===== ArrayStack<Integer> =====");

        ArrayStack<Integer> intStack = new ArrayStack<>(3);

        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        System.out.println("size = " + intStack.size());
        System.out.println("peek = " + intStack.peek());

        System.out.println("pop = " + intStack.pop());
        System.out.println("pop = " + intStack.pop());
        System.out.println("pop = " + intStack.pop());

        System.out.println("isEmpty = " + intStack.isEmpty());

        // 測試空 Stack
        intStack.pop();
    }
}