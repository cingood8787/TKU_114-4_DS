import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Q06_EnrollmentIndex {

    private final Map<String, Set<String>> enrollments
            = new TreeMap<>();

    public boolean enroll(
            String courseCode,
            String studentId) {

        if (courseCode == null
                || courseCode.isBlank()
                || studentId == null
                || studentId.isBlank()) {

            return false;
        }

        courseCode = courseCode.trim();
        studentId = studentId.trim();

        Set<String> students =
                enrollments.computeIfAbsent(
                        courseCode,
                        k -> new TreeSet<>()
                );

        return students.add(studentId);
    }

    public boolean drop(
            String courseCode,
            String studentId) {

        if (courseCode == null
                || courseCode.isBlank()
                || studentId == null
                || studentId.isBlank()) {

            return false;
        }

        courseCode = courseCode.trim();
        studentId = studentId.trim();

        Set<String> students =
                enrollments.get(courseCode);

        if (students == null
                || !students.remove(studentId)) {

            return false;
        }

        if (students.isEmpty()) {
            enrollments.remove(courseCode);
        }

        return true;
    }

    public int courseSize(String courseCode) {

        if (courseCode == null
                || courseCode.isBlank()) {

            return 0;
        }

        Set<String> students =
                enrollments.get(courseCode.trim());

        return students == null
                ? 0
                : students.size();
    }

    public List<String> studentsOf(
            String courseCode) {

        List<String> result =
                new ArrayList<>();

        if (courseCode == null
                || courseCode.isBlank()) {

            return result;
        }

        Set<String> students =
                enrollments.get(courseCode.trim());

        if (students != null) {
            result.addAll(students);
        }

        return result;
    }

    public List<String> coursesOf(
            String studentId) {

        List<String> result =
                new ArrayList<>();

        if (studentId == null
                || studentId.isBlank()) {

            return result;
        }

        String target = studentId.trim();

        for (Map.Entry<String, Set<String>> entry
                : enrollments.entrySet()) {

            if (entry.getValue().contains(target)) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    public Map<String, Integer> summary() {

        Map<String, Integer> result =
                new TreeMap<>();

        for (Map.Entry<String, Set<String>> entry
                : enrollments.entrySet()) {

            result.put(
                    entry.getKey(),
                    entry.getValue().size()
            );
        }

        return result;
    }
}