import java.util.Arrays;

public class InventorySnapshotPractice {

    static final class InventorySnapshot {
        private final String warehouseId;
        private final int[] quantities;

        // Constructor
        public InventorySnapshot(String warehouseId, int[] quantities) {
            this.warehouseId = warehouseId;

            // Defensive Copy
            if (quantities == null) {
                this.quantities = new int[0];
            } else {
                this.quantities = Arrays.copyOf(
                        quantities, quantities.length
                );
            }
        }

        public String getWarehouseId() {
            return warehouseId;
        }

        // Getter 也使用 Defensive Copy
        public int[] getQuantities() {
            return Arrays.copyOf(
                    quantities, quantities.length
            );
        }

        // 計算總數量
        public int totalQuantity() {
            int total = 0;

            for (int quantity : quantities) {
                total += quantity;
            }

            return total;
        }

        // 計算缺貨品項數
        public int outOfStockCount() {
            int count = 0;

            for (int quantity : quantities) {
                if (quantity == 0) {
                    count++;
                }
            }

            return count;
        }
    }

    public static void main(String[] args) {

        int[] data = {5, 0, 3, 0};

        InventorySnapshot snapshot =
                new InventorySnapshot("W001", data);

        System.out.println("倉庫編號："
                + snapshot.getWarehouseId());

        System.out.println("庫存資料："
                + Arrays.toString(snapshot.getQuantities()));

        System.out.println("總數量："
                + snapshot.totalQuantity());

        System.out.println("缺貨品項："
                + snapshot.outOfStockCount());

        // 測試 Defensive Copy
        data[0] = 999;

        System.out.println("\n修改原始陣列後：");
        System.out.println("庫存資料："
                + Arrays.toString(snapshot.getQuantities()));

        // 測試 null
        InventorySnapshot emptySnapshot =
                new InventorySnapshot("W002", null);

        System.out.println("\nnull 陣列測試：");
        System.out.println("陣列長度："
                + emptySnapshot.getQuantities().length);
    }
}