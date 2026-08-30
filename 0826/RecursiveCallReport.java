public class RecursiveCallReport {

    public static int sum(int[] data, int index) {

        // Base Case：走到陣列最後
        if (index >= data.length) {
            System.out.println("index=" + index
                    + ", current value=none"
                    + ", recursive result=0"
                    + ", return value=0");
            return 0;
        }

        // Recursive Call
        int recursiveResult = sum(data, index + 1);

        // 計算目前這一層要回傳的結果
        int returnValue = data[index] + recursiveResult;

        // 輸出這一層的資料
        System.out.println("index=" + index
                + ", current value=" + data[index]
                + ", recursive result=" + recursiveResult
                + ", return value=" + returnValue);

        return returnValue;
    }

    public static void main(String[] args) {

        // 1. 一般陣列
        System.out.println("=== Normal Array ===");
        int[] data1 = {10, 20, 30, 40};
        int result1 = sum(data1, 0);
        System.out.println("Final sum = " + result1);

        System.out.println();

        // 2. 單一元素
        System.out.println("=== Single Element ===");
        int[] data2 = {50};
        int result2 = sum(data2, 0);
        System.out.println("Final sum = " + result2);

        System.out.println();

        // 3. Empty Array
        System.out.println("=== Empty Array ===");
        int[] data3 = {};
        int result3 = sum(data3, 0);
        System.out.println("Final sum = " + result3);
    }
}