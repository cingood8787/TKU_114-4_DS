import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Q05_StudentHashIndex {

    private final Map<String, Set<String>> studentCourses =
            new HashMap<>();

    private final Map<String, Set<String>> courseStudents =
            new HashMap<>();

    private String normalize(String value) {

        if (value == null) {
            return null;
        }

        String result = value.trim().toUpperCase();

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    public boolean enroll(String studentId, String courseId) {

        studentId = normalize(studentId);
        courseId = normalize(courseId);

        if (studentId == null || courseId == null) {
            return false;
        }

        Set<String> courses =
                studentCourses.computeIfAbsent(
                        studentId,
                        k -> new HashSet<>());

        if (!courses.add(courseId)) {
            return false;
        }

        courseStudents
                .computeIfAbsent(
                        courseId,
                        k -> new HashSet<>())
                .add(studentId);

        return true;
    }

    public boolean drop(String studentId, String courseId) {

        studentId = normalize(studentId);
        courseId = normalize(courseId);

        if (studentId == null || courseId == null) {
            return false;
        }

        Set<String> courses = studentCourses.get(studentId);

        if (courses == null || !courses.remove(courseId)) {
            return false;
        }

        if (courses.isEmpty()) {
            studentCourses.remove(studentId);
        }

        Set<String> students = courseStudents.get(courseId);

        if (students != null) {

            students.remove(studentId);

            if (students.isEmpty()) {
                courseStudents.remove(courseId);
            }
        }

        return true;
    }

    public Set<String> coursesOf(String studentId) {

        studentId = normalize(studentId);

        if (studentId == null) {
            return new HashSet<>();
        }

        Set<String> courses = studentCourses.get(studentId);

        if (courses == null) {
            return new HashSet<>();
        }

        return new HashSet<>(courses);
    }

    public Set<String> studentsIn(String courseId) {

        courseId = normalize(courseId);

        if (courseId == null) {
            return new HashSet<>();
        }

        Set<String> students = courseStudents.get(courseId);

        if (students == null) {
            return new HashSet<>();
        }

        return new HashSet<>(students);
    }

    public int enrollmentCount() {

        int count = 0;

        for (Set<String> courses : studentCourses.values()) {
            count += courses.size();
        }

        return count;
    }
}