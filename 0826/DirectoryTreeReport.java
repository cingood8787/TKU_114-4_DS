import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeReport {

    // =========================================
    // Node：可表示 File 或 Directory
    // =========================================
    static class Node {
        String name;
        boolean isFile;
        long size;               // file 使用，directory 本身為 0
        List<Node> children;

        // 建立 File
        public static Node file(String name, long size) {
            Node node = new Node();
            node.name = name;
            node.isFile = true;
            node.size = size;
            node.children = new ArrayList<>();
            return node;
        }

        // 建立 Directory
        public static Node directory(String name) {
            Node node = new Node();
            node.name = name;
            node.isFile = false;
            node.size = 0;
            node.children = new ArrayList<>();
            return node;
        }

        // Directory 加入 child
        public void add(Node child) {
            if (isFile) {
                throw new IllegalStateException(
                        "File cannot contain children"
                );
            }

            if (child != null) {
                children.add(child);
            }
        }
    }

    // =========================================
    // Postorder 計算每個 Directory 總容量
    // =========================================
    public static long calculateDirectorySize(Node node) {

        if (node == null) {
            return 0;
        }

        // File 直接回傳自己的容量
        if (node.isFile) {
            return node.size;
        }

        long total = 0;

        // 先處理所有 children
        for (Node child : node.children) {
            total += calculateDirectorySize(child);
        }

        // Postorder：
        // children 都完成後才處理目前 directory
        System.out.println(
                "Directory: " + node.name
                        + ", total size = " + total
        );

        return total;
    }

    // =========================================
    // Total Node
    // =========================================
    public static int totalNodeCount(Node node) {

        if (node == null) {
            return 0;
        }

        int count = 1;

        for (Node child : node.children) {
            count += totalNodeCount(child);
        }

        return count;
    }

    // =========================================
    // File Count
    // =========================================
    public static int fileCount(Node node) {

        if (node == null) {
            return 0;
        }

        if (node.isFile) {
            return 1;
        }

        int count = 0;

        for (Node child : node.children) {
            count += fileCount(child);
        }

        return count;
    }

    // =========================================
    // Directory Count
    // =========================================
    public static int directoryCount(Node node) {

        if (node == null) {
            return 0;
        }

        int count = node.isFile ? 0 : 1;

        for (Node child : node.children) {
            count += directoryCount(child);
        }

        return count;
    }

    // =========================================
    // Height
    //
    // empty tree = 0
    // single node = 1
    // =========================================
    public static int height(Node node) {

        if (node == null) {
            return 0;
        }

        int maxChildHeight = 0;

        for (Node child : node.children) {
            maxChildHeight =
                    Math.max(
                            maxChildHeight,
                            height(child)
                    );
        }

        return 1 + maxChildHeight;
    }

    // =========================================
    // 最大檔案
    // =========================================
    public static Node largestFile(Node node) {

        if (node == null) {
            return null;
        }

        // 如果目前就是 File
        if (node.isFile) {
            return node;
        }

        Node largest = null;

        for (Node child : node.children) {

            Node candidate = largestFile(child);

            if (candidate != null) {

                if (largest == null
                        || candidate.size > largest.size) {

                    largest = candidate;
                }
            }
        }

        return largest;
    }

    // =========================================
    // Main
    // =========================================
    public static void main(String[] args) {

        /*
            root
            ├── documents
            │   ├── report.pdf      1200
            │   └── note.txt         300
            │
            ├── pictures
            │   ├── photo1.jpg      2500
            │   └── vacation
            │       ├── photo2.jpg  4200
            │       └── photo3.jpg  3500
            │
            └── readme.txt           100
        */

        Node root = Node.directory("root");

        Node documents = Node.directory("documents");
        documents.add(Node.file("report.pdf", 1200));
        documents.add(Node.file("note.txt", 300));

        Node pictures = Node.directory("pictures");

        pictures.add(
                Node.file("photo1.jpg", 2500)
        );

        Node vacation = Node.directory("vacation");

        vacation.add(
                Node.file("photo2.jpg", 4200)
        );

        vacation.add(
                Node.file("photo3.jpg", 3500)
        );

        pictures.add(vacation);

        root.add(documents);
        root.add(pictures);
        root.add(Node.file("readme.txt", 100));


        // =====================================
        // Postorder Directory Capacity
        // =====================================
        System.out.println(
                "=== Directory Size Report (Postorder) ==="
        );

        long totalSize =
                calculateDirectorySize(root);


        System.out.println();

        // =====================================
        // Statistics
        // =====================================
        System.out.println(
                "=== File System Statistics ==="
        );

        System.out.println(
                "Total capacity = " + totalSize
        );

        System.out.println(
                "Total node = " + totalNodeCount(root)
        );

        System.out.println(
                "File count = " + fileCount(root)
        );

        System.out.println(
                "Directory count = "
                        + directoryCount(root)
        );

        System.out.println(
                "Height = " + height(root)
        );


        // =====================================
        // 最大檔案
        // =====================================
        Node largest = largestFile(root);

        if (largest != null) {

            System.out.println(
                    "Largest file = "
                            + largest.name
                            + ", size = "
                            + largest.size
            );

        } else {

            System.out.println(
                    "Largest file = none"
            );
        }
    }
}