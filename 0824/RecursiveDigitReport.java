public class RecursiveDigitReport {

    // 計算各位數字總和
    public static int digitSum(int n) {
        long value = Math.abs((long) n);
        return digitSumPositive(value);
    }

    private static int digitSumPositive(long n) {
        if (n < 10) {
            return (int) n;
        }

        return (int) (n % 10)
                + digitSumPositive(n / 10);
    }

    // 計算數字有幾位
    public static int digitCount(int n) {
        long value = Math.abs((long) n);

        // 題目指定 digitCount(0) = 1
        if (value == 0) {
            return 1;
        }

        return digitCountPositive(value);
    }

    private static int digitCountPositive(long n) {
        if (n < 10) {
            return 1;
        }

        return 1 + digitCountPositive(n / 10);
    }

    // 計算指定數字 target 出現幾次
    public static int countDigit(int n, int target) {
        if (target < 0 || target > 9) {
            throw new IllegalArgumentException(
                    "target must be between 0 and 9"
            );
        }

        long value = Math.abs((long) n);

        // 0 本身有一個數位 0
        if (value == 0) {
            return target == 0 ? 1 : 0;
        }

        return countDigitPositive(value, target);
    }

    private static int countDigitPositive(
            long n,
            int target) {

        if (n == 0) {
            return 0;
        }

        int currentDigit = (int) (n % 10);

        int count =
                currentDigit == target ? 1 : 0;

        return count
                + countDigitPositive(
                        n / 10,
                        target
                );
    }

    // 輸出測試報告
    public static void report(int n, int target) {

        System.out.println("Number: " + n);
        System.out.println(
                "digitSum = " + digitSum(n)
        );
        System.out.println(
                "digitCount = " + digitCount(n)
        );
        System.out.println(
                "countDigit("
                        + target
                        + ") = "
                        + countDigit(n, target)
        );

        System.out.println();
    }

    public static void main(String[] args) {

        // 題目指定測試
        report(50205, 0);
        report(0, 0);
        report(-731, 7);
    }
}