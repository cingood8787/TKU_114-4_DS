import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CourseGradeMap {

    private final Map<String, List<Integer>> gradeMap = new TreeMap<>();

    // 新增成績
    public void addGrade(String courseId, int score) {

        gradeMap
                .computeIfAbsent(courseId, k -> new ArrayList<>())
                .add(score);
    }

    // 計算平均
    public double average(String courseId) {

        List<Integer> grades = gradeMap.get(courseId);

        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        int sum = 0;

        for (int score : grades) {
            sum += score;
        }

        return (double) sum / grades.size();
    }

    // 取得最高分
    public int highest(String courseId) {

        List<Integer> grades = gradeMap.get(courseId);

        if (grades == null || grades.isEmpty()) {
            throw new IllegalArgumentException(
                    "Course not found or no grades"
            );
        }

        int max = grades.get(0);

        for (int score : grades) {
            if (score > max) {
                max = score;
            }
        }

        return max;
    }

    // 依課號排序輸出報告
    public void report() {

        System.out.println("=== Course Grade Report ===");

        for (Map.Entry<String, List<Integer>> entry
                : gradeMap.entrySet()) {

            String courseId = entry.getKey();
            List<Integer> grades = entry.getValue();

            System.out.println(
                    courseId
                            + " | Grades = " + grades
                            + " | Average = "
                            + String.format("%.2f", average(courseId))
                            + " | Highest = "
                            + highest(courseId)
            );
        }
    }

    public static void main(String[] args) {

        CourseGradeMap manager =
                new CourseGradeMap();

        // 新增測試資料
        manager.addGrade("CS101", 80);
        manager.addGrade("CS101", 90);
        manager.addGrade("CS101", 70);

        manager.addGrade("DS201", 95);
        manager.addGrade("DS201", 88);
        manager.addGrade("DS201", 92);

        manager.addGrade("AP102", 75);
        manager.addGrade("AP102", 85);
        manager.addGrade("AP102", 65);

        System.out.println(
                "CS101 average = "
                        + manager.average("CS101")
        );

        System.out.println(
                "DS201 highest = "
                        + manager.highest("DS201")
        );

        System.out.println();

        manager.report();
    }
}