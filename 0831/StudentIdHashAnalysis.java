```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentIdHashAnalysis {

    static class AnalysisResult {
        int bucketCount;
        int[] bucketSizes;
        int totalCollisions;
        int maxChain;
        double averageChainLength;

        public AnalysisResult(
                int bucketCount,
                int[] bucketSizes,
                int totalCollisions,
                int maxChain,
                double averageChainLength) {

            this.bucketCount = bucketCount;
            this.bucketSizes = bucketSizes;
            this.totalCollisions = totalCollisions;
            this.maxChain = maxChain;
            this.averageChainLength = averageChainLength;
        }
    }

    // 將學號轉成 hash value
    private static int hashStudentId(String studentId) {
        return studentId.hashCode();
    }

    // 分析指定 bucket count
    public static AnalysisResult analyze(
            List<String> studentIds,
            int bucketCount) {

        if (bucketCount <= 0) {
            throw new IllegalArgumentException(
                    "bucketCount must be greater than 0"
            );
        }

        int[] bucketSizes = new int[bucketCount];

        if (studentIds != null) {

            for (String studentId : studentIds) {

                if (studentId == null) {
                    continue;
                }

                int hash = hashStudentId(studentId);

                // 正確處理負 hash 值
                int index = Math.floorMod(hash, bucketCount);

                bucketSizes[index]++;
            }
        }

        int totalCollisions = 0;
        int maxChain = 0;
        int totalItems = 0;

        for (int size : bucketSizes) {

            totalItems += size;

            if (size > 1) {
                totalCollisions += size - 1;
            }

            if (size > maxChain) {
                maxChain = size;
            }
        }

        double averageChainLength;

        if (bucketCount == 0) {
            averageChainLength = 0;
        } else {
            averageChainLength =
                    (double) totalItems / bucketCount;
        }

        return new AnalysisResult(
                bucketCount,
                bucketSizes,
                totalCollisions,
                maxChain,
                averageChainLength
        );
    }

    // 顯示分析結果
    public static void printResult(AnalysisResult result) {

        System.out.println(
                "=== Bucket Count = "
                        + result.bucketCount
                        + " ==="
        );

        for (int i = 0; i < result.bucketSizes.length; i++) {

            System.out.println(
                    "Bucket "
                            + i
                            + ": "
                            + result.bucketSizes[i]
                            + " student(s)"
            );
        }

        System.out.println(
                "Total collisions = "
                        + result.totalCollisions
        );

        System.out.println(
                "Max chain = "
                        + result.maxChain
        );

        System.out.printf(
                "Average chain length = %.2f%n",
                result.averageChainLength
        );
    }

    // 比較兩種 bucket count
    public static void compare(
            AnalysisResult a,
            AnalysisResult b) {

        System.out.println();
        System.out.println("=== Comparison ===");

        System.out.println(
                "Bucket count: "
                        + a.bucketCount
                        + " vs "
                        + b.bucketCount
        );

        System.out.println(
                "Collisions: "
                        + a.totalCollisions
                        + " vs "
                        + b.totalCollisions
        );

        System.out.println(
                "Max chain: "
                        + a.maxChain
                        + " vs "
                        + b.maxChain
        );

        System.out.printf(
                "Average chain length: %.2f vs %.2f%n",
                a.averageChainLength,
                b.averageChainLength
        );

        if (a.totalCollisions < b.totalCollisions) {
            System.out.println(
                    "Bucket count "
                            + a.bucketCount
                            + " has fewer collisions."
            );
        } else if (b.totalCollisions < a.totalCollisions) {
            System.out.println(
                    "Bucket count "
                            + b.bucketCount
                            + " has fewer collisions."
            );
        } else {
            System.out.println(
                    "Both have the same collision count."
            );
        }
    }

    public static void main(String[] args) {

        // 測試學號
        List<String> studentIds = Arrays.asList(
                "411631001",
                "411631002",
                "411631003",
                "411631004",
                "411631005",
                "411631006",
                "411631007",
                "411631008",
                "411631009",
                "411631010",
                "411631011",
                "411631012",
                "411631013",
                "411631014",
                "411631015",
                "411631016",
                "411631017",
                "411631018",
                "411631019",
                "411631020"
        );

        // 比較兩種 bucket count
        int bucketCount1 = 5;
        int bucketCount2 = 11;

        AnalysisResult result1 =
                analyze(studentIds, bucketCount1);

        AnalysisResult result2 =
                analyze(studentIds, bucketCount2);

        printResult(result1);

        System.out.println();

        printResult(result2);

        compare(result1, result2);
    }
}
```
