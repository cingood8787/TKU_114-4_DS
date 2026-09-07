import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseDependencyGraph {

    // prerequisite -> 後續課程
    private final Map<String, Set<String>> graph = new HashMap<>();

    // 新增課程
    public void addCourse(String course) {
        graph.putIfAbsent(course, new HashSet<>());
    }

    // 新增相依關係
    // prerequisite -> course
    public boolean addDependency(
            String prerequisite,
            String course) {

        if (prerequisite == null || course == null) {
            return false;
        }

        if (prerequisite.equals(course)) {
            return false;
        }

        addCourse(prerequisite);
        addCourse(course);

        // 重複 edge 不重複加入
        return graph.get(prerequisite).add(course);
    }

    // 查詢某門課的 prerequisites
    public List<String> prerequisitesOf(String course) {

        List<String> result = new ArrayList<>();

        if (!graph.containsKey(course)) {
            return result;
        }

        for (Map.Entry<String, Set<String>> entry
                : graph.entrySet()) {

            if (entry.getValue().contains(course)) {
                result.add(entry.getKey());
            }
        }

        result.sort(String::compareTo);

        return result;
    }

    // 查詢某門課的後續課程
    public List<String> nextCoursesOf(String course) {

        if (!graph.containsKey(course)) {
            return new ArrayList<>();
        }

        List<String> result =
                new ArrayList<>(graph.get(course));

        result.sort(String::compareTo);

        return result;
    }

    // in-degree
    public int inDegree(String course) {

        if (!graph.containsKey(course)) {
            return -1;
        }

        int count = 0;

        for (Set<String> nextCourses : graph.values()) {
            if (nextCourses.contains(course)) {
                count++;
            }
        }

        return count;
    }

    // out-degree
    public int outDegree(String course) {

        if (!graph.containsKey(course)) {
            return -1;
        }

        return graph.get(course).size();
    }

    // 輸出完整報告
    public void report() {

        List<String> courses =
                new ArrayList<>(graph.keySet());

        courses.sort(String::compareTo);

        System.out.println(
                "=== Course Dependency Report ==="
        );

        for (String course : courses) {

            System.out.println(
                    course
                            + " | prerequisites = "
                            + prerequisitesOf(course)
                            + " | next = "
                            + nextCoursesOf(course)
                            + " | in-degree = "
                            + inDegree(course)
                            + " | out-degree = "
                            + outDegree(course)
            );
        }
    }

    public static void main(String[] args) {

        CourseDependencyGraph graph =
                new CourseDependencyGraph();

        // 新增課程
        graph.addCourse("Programming");
        graph.addCourse("DataStructures");
        graph.addCourse("Algorithms");
        graph.addCourse("Database");
        graph.addCourse("WebProgramming");

        // 建立 directed dependency
        graph.addDependency(
                "Programming",
                "DataStructures"
        );

        graph.addDependency(
                "DataStructures",
                "Algorithms"
        );

        graph.addDependency(
                "Programming",
                "WebProgramming"
        );

        graph.addDependency(
                "Database",
                "WebProgramming"
        );

        // 測試重複 dependency
        boolean duplicate =
                graph.addDependency(
                        "Programming",
                        "DataStructures"
                );

        System.out.println(
                "Duplicate dependency added = "
                        + duplicate
        );

        System.out.println();

        graph.report();

        System.out.println();

        System.out.println(
                "WebProgramming prerequisites = "
                        + graph.prerequisitesOf(
                                "WebProgramming"
                        )
        );

        System.out.println(
                "Programming next courses = "
                        + graph.nextCoursesOf(
                                "Programming"
                        )
        );
    }
}