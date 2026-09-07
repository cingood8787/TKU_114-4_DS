public class RecursiveArrayStatistics {

    // =========================
    // maximum
    // =========================
    public static int maximum(int[] array) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(
                    "array cannot be null or empty"
            );
        }

        return maximumHelper(array, 0);
    }

    private static int maximumHelper(
            int[] array,
            int index) {

        // 最後一個元素
        if (index == array.length - 1) {
            return array[index];
        }

        int maxOfRest =
                maximumHelper(array, index + 1);

        return Math.max(
                array[index],
                maxOfRest
        );
    }


    // =========================
    // minimum
    // =========================
    public static int minimum(int[] array) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(
                    "array cannot be null or empty"
            );
        }

        return minimumHelper(array, 0);
    }

    private static int minimumHelper(
            int[] array,
            int index) {

        // 最後一個元素
        if (index == array.length - 1) {
            return array[index];
        }

        int minOfRest =
                minimumHelper(array, index + 1);

        return Math.min(
                array[index],
                minOfRest
        );
    }


    // =========================
    // countAbove
    // =========================
    public static int countAbove(
            int[] array,
            int threshold) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(
                    "array cannot be null or empty"
            );
        }

        return countAboveHelper(
                array,
                threshold,
                0
        );
    }

    private static int countAboveHelper(
            int[] array,
            int threshold,
            int index) {

        // 已經走完整個陣列
        if (index == array.length) {
            return 0;
        }

        int count =
                array[index] > threshold ? 1 : 0;

        return count
                + countAboveHelper(
                        array,
                        threshold,
                        index + 1
                );
    }


    // =========================
    // main 測試
    // =========================
    public static void main(String[] args) {

        int[] numbers = {
                12, 5, 30, 8, 25, 3, 18
        };

        System.out.println(
                "Maximum = " + maximum(numbers)
        );

        System.out.println(
                "Minimum = " + minimum(numbers)
        );

        System.out.println(
                "Count above 15 = "
                        + countAbove(numbers, 15)
        );

        // 測試只有一個元素
        int[] single = {100};

        System.out.println();
        System.out.println(
                "Single maximum = "
                        + maximum(single)
        );

        System.out.println(
                "Single minimum = "
                        + minimum(single)
        );

        // 測試 empty array
        try {

            int[] empty = {};

            maximum(empty);

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println(
                    "Empty array error: "
                            + e.getMessage()
            );
        }

        // 測試 null
        try {

            minimum(null);

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Null array error: "
                            + e.getMessage()
            );
        }
    }
}