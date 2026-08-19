public class DocumentCapabilityDemo {

    // 匯出能力
    interface Exportable {
        void export();
    }

    // 壓縮能力
    interface Compressible {
        void compress();
    }

    // 同時實作兩個 Interface
    static class BackupDocument
            implements Exportable, Compressible {

        private String fileName;

        public BackupDocument(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void export() {
            System.out.println(
                    fileName + "：正在匯出文件"
            );
        }

        @Override
        public void compress() {
            System.out.println(
                    fileName + "：正在壓縮文件"
            );
        }

        public void showInfo() {
            System.out.println(
                    "文件名稱：" + fileName
            );
        }
    }

    public static void main(String[] args) {

        // 建立一個 BackupDocument 物件
        BackupDocument document =
                new BackupDocument("backup.zip");

        // 使用不同 Interface Reference
        Exportable exportRef = document;
        Compressible compressRef = document;

        System.out.println("=== Exportable Reference ===");

        // Exportable reference 只能看到 export()
        exportRef.export();

        System.out.println("\n=== Compressible Reference ===");

        // Compressible reference 只能看到 compress()
        compressRef.compress();

        // 驗證兩個 Reference 指向同一個物件
        System.out.println("\n=== Reference 比較 ===");

        System.out.println(
                "是否指向同一物件："
                        + ((Object) exportRef
                        == (Object) compressRef)
        );

        // 原本 BackupDocument reference
        System.out.println("\n=== BackupDocument Reference ===");

        document.export();
        document.compress();
        document.showInfo();
    }
}