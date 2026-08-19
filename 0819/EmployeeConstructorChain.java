public class EmployeeConstructorChain {

    // 抽象父類別
    static abstract class EmployeeBase {
        private String id;
        private String name;

        public EmployeeBase(String id, String name) {
            this.id = id;
            this.name = name;

            System.out.println(
                    "EmployeeBase constructor 執行"
            );
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        // 由 subclass override
        public abstract double calculatePay();

        @Override
        public String toString() {
            return "員工編號：" + id
                    + "，姓名：" + name
                    + "，薪資：" + calculatePay();
        }
    }

    // 正職員工
    static class FullTimeEmployee extends EmployeeBase {

        private double salary;

        public FullTimeEmployee(
                String id,
                String name,
                double salary) {

            // 呼叫父類別 Constructor
            super(id, name);

            if (salary < 0) {
                this.salary = 0;
            } else {
                this.salary = salary;
            }

            System.out.println(
                    "FullTimeEmployee constructor 執行"
            );
        }

        @Override
        public double calculatePay() {
            return salary;
        }
    }

    // 兼職員工
    static class PartTimeEmployee extends EmployeeBase {

        private double hourlyRate;
        private double hours;

        public PartTimeEmployee(
                String id,
                String name,
                double hourlyRate,
                double hours) {

            // 呼叫父類別 Constructor
            super(id, name);

            if (hourlyRate < 0) {
                this.hourlyRate = 0;
            } else {
                this.hourlyRate = hourlyRate;
            }

            if (hours < 0) {
                this.hours = 0;
            } else {
                this.hours = hours;
            }

            System.out.println(
                    "PartTimeEmployee constructor 執行"
            );
        }

        @Override
        public double calculatePay() {
            return hourlyRate * hours;
        }
    }

    public static void main(String[] args) {

        System.out.println(
                "=== 建立 FullTimeEmployee ==="
        );

        FullTimeEmployee fullTime =
                new FullTimeEmployee(
                        "E001",
                        "王小明",
                        40000
                );

        System.out.println(fullTime);

        System.out.println(
                "\n=== 建立 PartTimeEmployee ==="
        );

        PartTimeEmployee partTime =
                new PartTimeEmployee(
                        "E002",
                        "李小華",
                        200,
                        80
                );

        System.out.println(partTime);

        // 測試負數
        System.out.println(
                "\n=== 負數測試 ==="
        );

        PartTimeEmployee test =
                new PartTimeEmployee(
                        "E003",
                        "陳大偉",
                        -200,
                        -10
                );

        System.out.println(test);
    }
}