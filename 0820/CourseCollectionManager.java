import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class CourseEnrollment {

    private final String studentId;
    private final String name;
    private int score;
    private final Set<String> tags = new HashSet<>();

    public CourseEnrollment(String studentId, String name, int score) {
        this.studentId = studentId;
        this.name = name;
        setScore(score);
    }

    public String getStudentId() {
        return studentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = Math.max(0, Math.min(100, score));
    }

    public void addTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            tags.add(tag.toLowerCase());
        }
    }

    public boolean hasTag(String tag) {
        return tag != null
                && !tag.isBlank()
                && tags.contains(tag.toLowerCase());
    }

    @Override
    public String toString() {
        return studentId + " " + name
                + " score=" + score
                + " tags=" + tags;
    }
}


class RegistrationBook {

    // 保存原始報名順序
    private final List<CourseEnrollment> order = new ArrayList<>();

    // 防止重複學號
    private final Set<String> registeredIds = new HashSet<>();

    // 使用學號快速查詢
    private final Map<String, CourseEnrollment> byId = new HashMap<>();


    // 新增報名資料
    public boolean enroll(CourseEnrollment enrollment) {

        if (enrollment == null) {
            return false;
        }

        // 學號重複就不能加入
        if (!registeredIds.add(enrollment.getStudentId())) {
            return false;
        }

        order.add(enrollment);
        byId.put(enrollment.getStudentId(), enrollment);

        return true;
    }


    // 使用學號查詢
    public CourseEnrollment find(String studentId) {
        return byId.get(studentId);
    }


    // 1. 更新成績
    public boolean updateScore(String studentId, int score) {

        CourseEnrollment enrollment = byId.get(studentId);

        if (enrollment == null) {
            return false;
        }

        enrollment.setScore(score);
        return true;
    }


    // 2. 依照 tag 查詢
    public List<CourseEnrollment> findByTag(String tag) {

        List<CourseEnrollment> result = new ArrayList<>();

        if (tag == null || tag.isBlank()) {
            return result;
        }

        for (CourseEnrollment enrollment : order) {

            if (enrollment.hasTag(tag)) {
                result.add(enrollment);
            }
        }

        return result;
    }


    // 成績排名
    public List<CourseEnrollment> ranking() {

        // 建立新的 List，不改變原始報名順序
        List<CourseEnrollment> result = new ArrayList<>(order);

        result.sort(
                Comparator
                        .comparingInt(CourseEnrollment::getScore)
                        .reversed()
                        .thenComparing(CourseEnrollment::getStudentId)
        );

        return result;
    }


    // 3. 成績分布 A、B、C、D、F
    public Map<String, Integer> scoreDistribution() {

        Map<String, Integer> result = new LinkedHashMap<>();

        result.put("A", 0);
        result.put("B", 0);
        result.put("C", 0);
        result.put("D", 0);
        result.put("F", 0);

        for (CourseEnrollment enrollment : order) {

            int score = enrollment.getScore();

            String grade;

            if (score >= 90) {
                grade = "A";
            } else if (score >= 80) {
                grade = "B";
            } else if (score >= 70) {
                grade = "C";
            } else if (score >= 60) {
                grade = "D";
            } else {
                grade = "F";
            }

            result.put(grade, result.get(grade) + 1);
        }

        return result;
    }


    // 4. 取得前 count 名
    public List<CourseEnrollment> top(int count) {

        List<CourseEnrollment> ranked = ranking();

        if (count <= 0) {
            return new ArrayList<>();
        }

        // count 大於人數時回傳全部
        if (count >= ranked.size()) {
            return ranked;
        }

        return new ArrayList<>(ranked.subList(0, count));
    }


    // 5. 移除低於 minimum 的學生
    public void removeBelow(int minimum) {

        order.removeIf(
                enrollment -> enrollment.getScore() < minimum
        );

        // 重新建立 Set 與 Map
        // 確保 List、Set、Map 三者一致
        registeredIds.clear();
        byId.clear();

        for (CourseEnrollment enrollment : order) {

            registeredIds.add(enrollment.getStudentId());

            byId.put(
                    enrollment.getStudentId(),
                    enrollment
            );
        }
    }


    // 顯示三個集合的狀態
    public void printStatus() {

        System.out.println("List 人數：" + order.size());
        System.out.println("Set 人數：" + registeredIds.size());
        System.out.println("Map 人數：" + byId.size());

        System.out.println("List：" + order);
        System.out.println("Set：" + registeredIds);
        System.out.println("Map：" + byId);
    }
}


public class CourseCollectionManager {

    public static void main(String[] args) {

        RegistrationBook book = new RegistrationBook();


        // 第 1 筆
        CourseEnrollment amy =
                new CourseEnrollment("S101", "Amy", 95);

        amy.addTag("Java");
        amy.addTag("Tree");


        // 第 2 筆
        CourseEnrollment ben =
                new CourseEnrollment("S102", "Ben", 82);

        ben.addTag("Java");


        // 第 3 筆
        CourseEnrollment cara =
                new CourseEnrollment("S103", "Cara", 82);

        cara.addTag("Database");


        // 第 4 筆
        CourseEnrollment david =
                new CourseEnrollment("S104", "David", 73);

        david.addTag("Web");


        // 第 5 筆
        CourseEnrollment emma =
                new CourseEnrollment("S105", "Emma", 65);

        emma.addTag("Java");


        // 第 6 筆
        CourseEnrollment frank =
                new CourseEnrollment("S106", "Frank", 50);

        // 空白 tag，應該不會被加入
        frank.addTag("   ");


        // 第 7 筆：重複學號
        CourseEnrollment duplicate =
                new CourseEnrollment("S101", "Amy2", 100);


        // 加入資料
        System.out.println("Amy：" + book.enroll(amy));
        System.out.println("Ben：" + book.enroll(ben));
        System.out.println("Cara：" + book.enroll(cara));
        System.out.println("David：" + book.enroll(david));
        System.out.println("Emma：" + book.enroll(emma));
        System.out.println("Frank：" + book.enroll(frank));

        // 重複學號應該 false
        System.out.println(
                "重複 S101：" + book.enroll(duplicate)
        );


        System.out.println();
        System.out.println("===== 原始資料 =====");

        book.printStatus();


        System.out.println();
        System.out.println("===== updateScore =====");

        System.out.println(
                "更新 S104：" +
                        book.updateScore("S104", 88)
        );

        System.out.println(
                "S104：" + book.find("S104")
        );


        System.out.println();
        System.out.println("===== findByTag =====");

        System.out.println(
                "Java：" + book.findByTag("Java")
        );

        System.out.println(
                "空白 tag：" + book.findByTag(" ")
        );


        System.out.println();
        System.out.println("===== 成績排名 =====");

        System.out.println(book.ranking());


        System.out.println();
        System.out.println("===== scoreDistribution =====");

        System.out.println(book.scoreDistribution());


        System.out.println();
        System.out.println("===== Top 3 =====");

        System.out.println(book.top(3));


        System.out.println();
        System.out.println("===== Top 100 =====");

        // 超過人數，回傳全部
        System.out.println(book.top(100));


        System.out.println();
        System.out.println("===== removeBelow 60 =====");

        book.removeBelow(60);

        book.printStatus();
    }
}