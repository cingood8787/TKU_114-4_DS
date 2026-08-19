public class PayrollPolymorphismSystem {

    // 抽象父類別
    static abstract class Employee {
        private String id;
        private String name;

        public Employee(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        // 由 subclass 實作
        public abstract double calculatePay();

        @Override
        public String toString() {
            return "員工編號：" + id
                    + "，姓名：" + name
                    + "，薪資：" + calculatePay();
        }
    }

    // 月薪員工
    static class SalariedEmployee extends Employee {
        private double monthlySalary;

        public SalariedEmployee(
                String id,
                String name,
                double monthlySalary) {

            super(id, name);

            if (monthlySalary < 0) {
                this.monthlySalary = 0;
            } else {
                this.monthlySalary = monthlySalary;
            }
        }

        @Override
        public double calculatePay() {
            return monthlySalary;
        }
    }

    // 時薪員工
    static class HourlyEmployee extends Employee {
        private double hourlyRate;
        private double hours;

        public HourlyEmployee(
                String id,
                String name,
                double hourlyRate,
                double hours) {

            super(id, name);

            this.hourlyRate =
                    hourlyRate < 0 ? 0 : hourlyRate;

            this.hours =
                    hours < 0 ? 0 : hours;
        }

        @Override
        public double calculatePay() {
            return hourlyRate * hours;
        }
    }

    // 業務員工
    static class SalesEmployee extends Employee {
        private double baseSalary;
        private double salesAmount;
        private double commissionRate;

        public SalesEmployee(
                String id,
                String name,
                double baseSalary,
                double salesAmount,
                double commissionRate) {

            super(id, name);

            this.baseSalary =
                    baseSalary < 0 ? 0 : baseSalary;

            this.salesAmount =
                    salesAmount < 0 ? 0 : salesAmount;

            this.commissionRate =
                    commissionRate < 0 ? 0 : commissionRate;
        }

        @Override
        public double calculatePay() {
            return baseSalary
                    + salesAmount * commissionRate;
        }
    }

    public static void main(String[] args) {

        // 使用 Employee[] 保存不同 subclass
        Employee[] employees = {
                new SalariedEmployee(
                        "E001",
                        "王小明",
                        40000
                ),

                new HourlyEmployee(
                        "E002",
                        "李小華",
                        200,
                        160
                ),

                new SalesEmployee(
                        "E003",
                        "陳大偉",
                        30000,
                        200000,
                        0.05
                ),

                new SalariedEmployee(
                        "E004",
                        "林小美",
                        45000
                )
        };

        System.out.println("=== 員工薪資 ===");

        double totalPay = 0;
        Employee highestEmployee = employees[0];

        for (Employee employee : employees) {

            // Polymorphism
            double pay = employee.calculatePay();

            System.out.println(employee);

            // 計算薪資總額
            totalPay += pay;

            // 找最高薪資
            if (pay > highestEmployee.calculatePay()) {
                highestEmployee = employee;
            }
        }

        System.out.println("\n=== 薪資統計 ===");

        System.out.println(
                "薪資總額：" + totalPay
        );

        System.out.println(
                "最高薪資員工："
                        + highestEmployee.getName()
        );

        System.out.println(
                "最高薪資："
                        + highestEmployee.calculatePay()
        );
    }
}