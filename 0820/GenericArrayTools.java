public class GenericArrayTools {

    // 計算 target 在陣列中出現幾次
    static <T> int countMatches(T[] data, T target) {
        if (data == null) {
            return 0;
        }

        int count = 0;

        for (T value : data) {
            if (target == null) {
                if (value == null) {
                    count++;
                }
            } else {
                if (target.equals(value)) {
                    count++;
                }
            }
        }

        return count;
    }

    // 取得陣列最後一個元素
    static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        return data[data.length - 1];
    }

    // 交換陣列中兩個位置的元素
    static <T> void swap(T[] data, int first, int second) {
        if (data == null ||
            first < 0 || first >= data.length ||
            second < 0 || second >= data.length) {
            return;
        }

        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {

        String[] names = {"Amy", "Ben", "Amy", "Cara"};

        System.out.println("Amy 出現次數：" + countMatches(names, "Amy"));
        System.out.println("最後一個：" + last(names));

        swap(names, 0, 3);

        System.out.print("交換後：");
        for (String name : names) {
            System.out.print(name + " ");
        }

        System.out.println();

        Integer[] numbers = {10, 20, 10, 30};

        System.out.println("10 出現次數：" + countMatches(numbers, 10));
        System.out.println("最後一個：" + last(numbers));

        // 測試空陣列
        String[] empty = {};
        System.out.println("空陣列最後一個：" + last(empty));

        // 測試 null
        String[] nullArray = null;
        System.out.println("null 陣列：" + countMatches(nullArray, "Amy"));

        // 測試不合法 index，不會發生錯誤
        swap(names, -1, 100);
    }
}