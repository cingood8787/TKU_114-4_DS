public class DynamicArrayPractice {

    static class DynamicArray<T> {

        private Object[] data;
        private int size;

        // 預設容量
        public DynamicArray() {
            data = new Object[2];
            size = 0;
        }

        // 尾端新增
        public void add(T value) {
            ensureCapacity();
            data[size] = value;
            size++;
        }

        // 指定位置插入
        public void add(int index, T value) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException(
                        "index = " + index + ", size = " + size);
            }

            ensureCapacity();

            // 元素往右移
            for (int i = size; i > index; i--) {
                data[i] = data[i - 1];
            }

            data[index] = value;
            size++;
        }

        // 取得指定位置元素
        @SuppressWarnings("unchecked")
        public T get(int index) {
            checkIndex(index);
            return (T) data[index];
        }

        // 修改指定位置元素，並回傳舊值
        @SuppressWarnings("unchecked")
        public T set(int index, T value) {
            checkIndex(index);

            T oldValue = (T) data[index];
            data[index] = value;

            return oldValue;
        }

        // 刪除指定位置元素
        @SuppressWarnings("unchecked")
        public T remove(int index) {
            checkIndex(index);

            T removedValue = (T) data[index];

            // 元素往左移
            for (int i = index; i < size - 1; i++) {
                data[i] = data[i + 1];
            }

            size--;

            // 最後一個無效格設為 null
            data[size] = null;

            return removedValue;
        }

        // 目前元素數量
        public int size() {
            return size;
        }

        // 目前容量
        public int capacity() {
            return data.length;
        }

        // 檢查容量
        private void ensureCapacity() {
            if (size == data.length) {

                Object[] newData = new Object[data.length * 2];

                for (int i = 0; i < size; i++) {
                    newData[i] = data[i];
                }

                data = newData;
            }
        }

        // 檢查 index
        private void checkIndex(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(
                        "index = " + index + ", size = " + size);
            }
        }

        // 顯示內容
        public void print() {
            System.out.print("[");

            for (int i = 0; i < size; i++) {
                System.out.print(data[i]);

                if (i < size - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println("]");
        }
    }

    public static void main(String[] args) {

        // =========================
        // String 測試
        // =========================
        System.out.println("===== String 測試 =====");

        DynamicArray<String> words = new DynamicArray<>();

        words.add("Java");
        words.add("Python");

        System.out.println("size = " + words.size());
        System.out.println("capacity = " + words.capacity());

        // 容量滿，自動擴充
        words.add("C++");

        System.out.println("加入 C++ 後");
        words.print();

        System.out.println("capacity = " + words.capacity());

        // 指定位置插入
        words.add(1, "JavaScript");
        words.print();

        // get
        System.out.println("get(1) = " + words.get(1));

        // set
        String old = words.set(1, "HTML");
        System.out.println("被取代的值 = " + old);
        words.print();

        // remove
        String removed = words.remove(1);
        System.out.println("刪除 = " + removed);
        words.print();


        // =========================
        // Integer 測試
        // =========================
        System.out.println("\n===== Integer 測試 =====");

        DynamicArray<Integer> numbers = new DynamicArray<>();

        numbers.add(10);
        numbers.add(20);
        numbers.add(30);

        numbers.print();

        numbers.add(1, 15);
        numbers.print();

        System.out.println("remove(2) = " + numbers.remove(2));

        numbers.print();

        System.out.println("size = " + numbers.size());
        System.out.println("capacity = " + numbers.capacity());


        // =========================
        // 錯誤 index 測試
        // =========================
        System.out.println("\n===== Index 錯誤測試 =====");

        // 測試 index -1
        try {
            numbers.get(-1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index -1 錯誤：" + e.getMessage());
        }

        // 測試 index == size
        try {
            numbers.get(numbers.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index size 錯誤：" + e.getMessage());
        }


        // =========================
        // 空結構刪除測試
        // =========================
        System.out.println("\n===== 空結構刪除測試 =====");

        DynamicArray<String> empty = new DynamicArray<>();

        try {
            empty.remove(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("空結構無法刪除：" + e.getMessage());
        }
    }
}