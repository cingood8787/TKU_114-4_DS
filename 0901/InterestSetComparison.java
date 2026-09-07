import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InterestSetComparison {

    // 聯集 Union
    public static Set<String> union(
            Set<String> first,
            Set<String> second) {

        // 建立新的 Set，不修改輸入
        Set<String> result = new HashSet<>(first);
        result.addAll(second);

        return result;
    }

    // 交集 Intersection
    public static Set<String> intersection(
            Set<String> first,
            Set<String> second) {

        Set<String> result = new HashSet<>(first);
        result.retainAll(second);

        return result;
    }

    // 只存在第一組
    public static Set<String> firstOnly(
            Set<String> first,
            Set<String> second) {

        Set<String> result = new HashSet<>(first);
        result.removeAll(second);

        return result;
    }

    // 只存在第二組
    public static Set<String> secondOnly(
            Set<String> first,
            Set<String> second) {

        Set<String> result = new HashSet<>(second);
        result.removeAll(first);

        return result;
    }

    public static void main(String[] args) {

        Set<String> first = new HashSet<>(
                Arrays.asList(
                        "Basketball",
                        "Music",
                        "Movies",
                        "Gaming"
                )
        );

        Set<String> second = new HashSet<>(
                Arrays.asList(
                        "Music",
                        "Travel",
                        "Gaming",
                        "Reading"
                )
        );

        System.out.println("First = " + first);
        System.out.println("Second = " + second);

        System.out.println();

        System.out.println(
                "Union = "
                        + union(first, second)
        );

        System.out.println(
                "Intersection = "
                        + intersection(first, second)
        );

        System.out.println(
                "First only = "
                        + firstOnly(first, second)
        );

        System.out.println(
                "Second only = "
                        + secondOnly(first, second)
        );

        // 確認原始 Set 沒有被修改
        System.out.println();
        System.out.println("=== Original Sets ===");
        System.out.println("First = " + first);
        System.out.println("Second = " + second);
    }
}