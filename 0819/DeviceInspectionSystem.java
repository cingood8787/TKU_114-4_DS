public class DeviceInspectionSystem {

    // 父類別
    static class Device {
        private String name;

        public Device(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void runDiagnostic() {
            System.out.println(
                    name + "：執行一般設備診斷"
            );
        }
    }

    // Laptop
    static class Laptop extends Device {

        public Laptop(String name) {
            super(name);
        }

        @Override
        public void runDiagnostic() {
            System.out.println(
                    getName() + "：檢查電池、記憶體與硬碟"
            );
        }
    }

    // Printer
    static class Printer extends Device {

        public Printer(String name) {
            super(name);
        }

        @Override
        public void runDiagnostic() {
            System.out.println(
                    getName() + "：檢查墨水與列印狀態"
            );
        }

        // 只有 Printer 有這個方法
        public void cleanPrintHead() {
            System.out.println(
                    getName() + "：正在清潔印字頭"
            );
        }
    }

    // Router
    static class Router extends Device {

        public Router(String name) {
            super(name);
        }

        @Override
        public void runDiagnostic() {
            System.out.println(
                    getName() + "：檢查網路連線與訊號"
            );
        }
    }

    public static void main(String[] args) {

        // 使用 Device[] 保存不同類型物件
        Device[] devices = {
                new Laptop("ASUS Laptop"),
                new Printer("HP Printer"),
                new Router("TP-Link Router"),
                new Printer("Canon Printer")
        };

        System.out.println("=== 設備檢查 ===");

        for (Device device : devices) {

            // Polymorphism
            device.runDiagnostic();

            // Pattern Matching instanceof
            // 只有 Printer 才執行清潔
            if (device instanceof Printer printer) {
                printer.cleanPrintHead();
            }

            System.out.println();
        }
    }
}