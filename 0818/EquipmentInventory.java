public class EquipmentInventory {

    static class Equipment {
        private String id;
        private String name;
        private int availableCount;

        // Constructor
        public Equipment(String id, String name, int availableCount) {
            if (id == null || id.trim().isEmpty()) {
                this.id = "Unknown";
            } else {
                this.id = id;
            }

            if (name == null || name.trim().isEmpty()) {
                this.name = "Unknown";
            } else {
                this.name = name;
            }

            if (availableCount < 0) {
                this.availableCount = 0;
            } else {
                this.availableCount = availableCount;
            }
        }

        // 借用一個設備
        public boolean borrowOne() {
            if (availableCount > 0) {
                availableCount--;
                return true;
            }
            return false;
        }

        // 歸還設備
        public void returnItems(int quantity) {
            if (quantity > 0) {
                availableCount += quantity;
            }
        }

        // 顯示設備資訊
        @Override
        public String toString() {
            return "設備編號：" + id +
                   "，名稱：" + name +
                   "，可借數量：" + availableCount;
        }
    }

    public static void main(String[] args) {

        // 建立兩個設備
        Equipment equipment1 =
                new Equipment("E001", "筆記型電腦", 1);

        Equipment equipment2 =
                new Equipment("", "投影機", 0);

        System.out.println("初始狀態：");
        System.out.println(equipment1);
        System.out.println(equipment2);

        // 測試借用成功
        System.out.println("\n借用筆記型電腦：");
        System.out.println(equipment1.borrowOne());
        System.out.println(equipment1);

        // 測試借用失敗
        System.out.println("\n再次借用筆記型電腦：");
        System.out.println(equipment1.borrowOne());
        System.out.println(equipment1);

        // 測試歸還
        System.out.println("\n歸還 2 台筆記型電腦：");
        equipment1.returnItems(2);
        System.out.println(equipment1);
    }
}