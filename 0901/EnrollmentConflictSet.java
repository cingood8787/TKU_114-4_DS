import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnrollmentConflictSet {

    // 複合 key：學號 + 課號
    static class EnrollmentKey {
        String studentId;
        String courseId;

        EnrollmentKey(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof EnrollmentKey)) {
                return false;
            }

            EnrollmentKey other = (EnrollmentKey) obj;

            return studentId.equals(other.studentId)
                    && courseId.equals(other.courseId);
        }

        @Override
        public int hashCode() {
            return 31 * studentId.hashCode()
                    + courseId.hashCode();
        }

        @Override
        public String toString() {
            return studentId + "|" + courseId;
        }
    }

    static class Enrollment {
        String studentId;
        String courseId;

        Enrollment(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }
    }

    public static void analyze(List<Enrollment> records) {

        // 用來檢查重複
        Set<EnrollmentKey> seen = new HashSet<>();

        // 保存重複紀錄
        Set<EnrollmentKey> duplicates = new HashSet<>();

        // 每位學生的課程集合
        Map<String, Set<String>> studentCourses =
                new HashMap<>();

        // 每門課的學生集合
        Map<String, Set<String>> courseStudents =
                new HashMap<>();

        if (records != null) {

            for (Enrollment record : records) {

                if (record == null
                        || record.studentId == null
                        || record.courseId == null) {
                    continue;
                }

                EnrollmentKey key =
                        new EnrollmentKey(
                                record.studentId,
                                record.courseId
                        );

                // 如果已經存在，代表重複
                if (!seen.add(key)) {
                    duplicates.add(key);
                }

                // 每人課程集合
                studentCourses
                        .computeIfAbsent(
                                record.studentId,
                                k -> new HashSet<>()
                        )
                        .add(record.courseId);

                // 每門課學生集合
                courseStudents
                        .computeIfAbsent(
                                record.courseId,
                                k -> new HashSet<>()
                        )
                        .add(record.studentId);
            }
        }

        System.out.println(
                "=== Duplicate Enrollment Records ==="
        );

        if (duplicates.isEmpty()) {
            System.out.println("No duplicate records.");
        } else {
            List<EnrollmentKey> duplicateList =
                    new ArrayList<>(duplicates);

            duplicateList.sort(
                    (a, b) -> {
                        int result =
                                a.studentId.compareTo(b.studentId);

                        if (result != 0) {
                            return result;
                        }

                        return a.courseId.compareTo(b.courseId);
                    }
            );

            for (EnrollmentKey key : duplicateList) {
                System.out.println(key);
            }
        }

        System.out.println();
        System.out.println(
                "=== Student Course Sets ==="
        );

        List<String> students =
                new ArrayList<>(studentCourses.keySet());

        students.sort(String::compareTo);

        for (String student : students) {

            List<String> courses =
                    new ArrayList<>(
                            studentCourses.get(student)
                    );

            courses.sort(String::compareTo);

            System.out.println(
                    student + " -> " + courses
            );
        }

        System.out.println();
        System.out.println(
                "=== Course Enrollment Counts ==="
        );

        List<String> courses =
                new ArrayList<>(courseStudents.keySet());

        courses.sort(String::compareTo);

        for (String course : courses) {

            System.out.println(
                    course
                            + " -> "
                            + courseStudents.get(course).size()
                            + " student(s)"
            );
        }
    }

    public static void main(String[] args) {

        List<Enrollment> records =
                new ArrayList<>();

        records.add(
                new Enrollment("S001", "CS101")
        );

        records.add(
                new Enrollment("S001", "DS201")
        );

        records.add(
                new Enrollment("S002", "CS101")
        );

        records.add(
                new Enrollment("S002", "DB301")
        );

        records.add(
                new Enrollment("S003", "CS101")
        );

        records.add(
                new Enrollment("S003", "DS201")
        );

        // 重複紀錄
        records.add(
                new Enrollment("S001", "CS101")
        );

        records.add(
                new Enrollment("S002", "DB301")
        );

        records.add(
                new Enrollment("S001", "DS201")
        );

        analyze(records);
    }
}