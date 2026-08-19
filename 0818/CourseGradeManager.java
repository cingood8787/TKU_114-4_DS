public class CourseGradeManager {

    static class CourseGrade {
        private String studentId;
        private String name;
        private double regularScore;
        private double midtermScore;
        private double finalExamScore;
        private double attendance;

        public CourseGrade(String studentId, String name,
                           double regularScore,
                           double midtermScore,
                           double finalExamScore,
                           double attendance) {

            this.studentId = studentId;
            this.name = name;
            this.regularScore = checkScore(regularScore);
            this.midtermScore = checkScore(midtermScore);
            this.finalExamScore = checkScore(finalExamScore);
            this.attendance = checkScore(attendance);
        }

        // 成績限制在 0 ~ 100
        private double checkScore(double score) {
            if (score < 0) {
                return 0;
            }

            if (score > 100) {
                return 100;
            }

            return score;
        }

        // 計算總成績
        public double calculateFinalScore() {
            return regularScore * 0.5
                    + midtermScore * 0.2
                    + finalExamScore * 0.2
                    + attendance * 0.1;
        }

        // 取得等級
        public String getLevel() {
            double score = calculateFinalScore();

            if (score >= 90) {
                return "A";
            } else if (score >= 80) {
                return "B";
            } else if (score >= 70) {
                return "C";
            } else if (score >= 60) {
                return "D";
            } else {
                return "F";
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "學號：" + studentId
                    + "，姓名：" + name
                    + "，平時：" + regularScore
                    + "，期中：" + midtermScore
                    + "，期末：" + finalExamScore
                    + "，出席：" + attendance
                    + "，總分：" + calculateFinalScore()
                    + "，等級：" + getLevel();
        }
    }

    public static void main(String[] args) {

        // 建立至少五筆資料
        CourseGrade[] students = {
                new CourseGrade(
                        "S001", "王小明",
                        90, 85, 88, 100
                ),

                new CourseGrade(
                        "S002", "李小華",
                        75, 80, 70, 90
                ),

                new CourseGrade(
                        "S003", "陳大偉",
                        50, 45, 55, 80
                ),

                new CourseGrade(
                        "S004", "林小美",
                        95, 92, 96, 100
                ),

                new CourseGrade(
                        "S005", "張志明",
                        55, 50, 40, 70
                )
        };

        System.out.println("=== 所有學生成績 ===");

        double total = 0;
        CourseGrade highest = students[0];

        for (CourseGrade student : students) {
            System.out.println(student);

            total += student.calculateFinalScore();

            if (student.calculateFinalScore()
                    > highest.calculateFinalScore()) {

                highest = student;
            }
        }

        // 計算平均
        double average = total / students.length;

        System.out.println("\n=== 成績統計 ===");
        System.out.println("全班平均：" + average);

        System.out.println("最高分學生："
                + highest.getName());

        System.out.println("最高分："
                + highest.calculateFinalScore());

        // 輸出不及格名單
        System.out.println("\n=== 不及格名單 ===");

        for (CourseGrade student : students) {
            if (student.calculateFinalScore() < 60) {
                System.out.println(student);
            }
        }
    }
}