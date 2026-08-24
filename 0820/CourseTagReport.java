import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class CourseTagReport {

    public static void main(String[] args) {

        // 輸入一組可能重複的課程標籤
        String[] tags = {
            "Java", "Database", "Java",
            "Web", "Database", "Java"
        };

        // List：保存原始順序，可重複
        List<String> tagList = new ArrayList<>();

        // Set：保存不重複的標籤
        Set<String> tagSet = new HashSet<>();

        // Map：統計每個標籤出現次數
        Map<String, Integer> tagCount = new HashMap<>();

        for (String tag : tags) {

            // 加入 List
            tagList.add(tag);

            // 加入 Set
            tagSet.add(tag);

            // 統計次數
            tagCount.put(
                tag,
                tagCount.getOrDefault(tag, 0) + 1
            );
        }

        // 輸出結果
        System.out.println("=== List ===");
        System.out.println(tagList);
        System.out.println("用途：保存原始順序，允許重複資料。");

        System.out.println();

        System.out.println("=== Set ===");
        System.out.println(tagSet);
        System.out.println("用途：保存不重複的課程標籤。");

        System.out.println();

        System.out.println("=== Map ===");
        System.out.println(tagCount);
        System.out.println("用途：用標籤當 Key，統計每個標籤出現的次數。");
    }
}