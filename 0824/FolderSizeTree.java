import java.util.ArrayList;
import java.util.List;

public class FolderSizeTree {

    static class FolderNode {
        String name;
        int ownSize;
        FolderNode left;
        FolderNode right;

        FolderNode(String name, int ownSize) {
            this.name = name;
            this.ownSize = ownSize;
        }

        FolderNode(
                String name,
                int ownSize,
                FolderNode left,
                FolderNode right) {

            this.name = name;
            this.ownSize = ownSize;
            this.left = left;
            this.right = right;
        }
    }

    // 保存最大 subtree 的資料
    static class MaxSubtree {
        String name;
        int size = Integer.MIN_VALUE;
    }

    // =========================
    // Postorder 計算 Subtree Size
    // Left -> Right -> Root
    // =========================
    public static int subtreeSize(FolderNode node) {

        if (node == null) {
            return 0;
        }

        int leftSize =
                subtreeSize(node.left);

        int rightSize =
                subtreeSize(node.right);

        return node.ownSize
                + leftSize
                + rightSize;
    }

    // =========================
    // 使用 Postorder 輸出
    // 每個 Folder 的 Subtree Size
    // =========================
    public static int postorderReport(
            FolderNode node) {

        if (node == null) {
            return 0;
        }

        int leftSize =
                postorderReport(node.left);

        int rightSize =
                postorderReport(node.right);

        int total =
                node.ownSize
                        + leftSize
                        + rightSize;

        System.out.println(
                node.name
                        + " subtree size = "
                        + total
        );

        return total;
    }

    // =========================
    // 找最大 Subtree
    // =========================
    public static void findLargestSubtree(
            FolderNode node,
            MaxSubtree result) {

        if (node == null) {
            return;
        }

        findLargestSubtree(
                node.left,
                result
        );

        findLargestSubtree(
                node.right,
                result
        );

        int currentSize =
                subtreeSize(node);

        if (currentSize > result.size) {
            result.size = currentSize;
            result.name = node.name;
        }
    }

    // =========================
    // 找 Leaf Folder
    // =========================
    public static List<String> leafFolders(
            FolderNode root) {

        List<String> result =
                new ArrayList<>();

        collectLeaves(
                root,
                result
        );

        return result;
    }

    private static void collectLeaves(
            FolderNode node,
            List<String> result) {

        if (node == null) {
            return;
        }

        if (node.left == null
                && node.right == null) {

            result.add(node.name);
            return;
        }

        collectLeaves(
                node.left,
                result
        );

        collectLeaves(
                node.right,
                result
        );
    }

    // =========================
    // 完整報告
    // =========================
    public static void report(
            FolderNode root) {

        if (root == null) {
            System.out.println(
                    "Empty folder tree"
            );
            return;
        }

        System.out.println(
                "=== Postorder Subtree Report ==="
        );

        int totalSize =
                postorderReport(root);

        MaxSubtree max =
                new MaxSubtree();

        findLargestSubtree(
                root,
                max
        );

        System.out.println();
        System.out.println(
                "=== Folder Size Summary ==="
        );

        System.out.println(
                "Total size = "
                        + totalSize
        );

        System.out.println(
                "Largest subtree = "
                        + max.name
                        + " ("
                        + max.size
                        + ")"
        );

        System.out.println(
                "Leaf folders = "
                        + leafFolders(root)
        );
    }

    public static void main(String[] args) {

        /*
                       Root(10)
                      /        \
                Documents(20)   Media(30)
                 /      \        /     \
             Work(40) Personal(15) Music(50) Video(100)

        Leaf folders:
        Work, Personal, Music, Video

        Total:
        10 + 20 + 30 + 40 + 15 + 50 + 100
        = 265
        */

        FolderNode root =
                new FolderNode(
                        "Root",
                        10,

                        new FolderNode(
                                "Documents",
                                20,
                                new FolderNode(
                                        "Work",
                                        40
                                ),
                                new FolderNode(
                                        "Personal",
                                        15
                                )
                        ),

                        new FolderNode(
                                "Media",
                                30,
                                new FolderNode(
                                        "Music",
                                        50
                                ),
                                new FolderNode(
                                        "Video",
                                        100
                                )
                        )
                );

        report(root);

        // Empty Tree 測試
        System.out.println();
        System.out.println(
                "=== Empty Tree Test ==="
        );

        report(null);
    }
}