public class TreeShapeComparison {

    static class Node {
        int key;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
        }
    }

    static class BST {
        private Node root;

        // =========================
        // Add
        // =========================
        public boolean add(int key) {

            if (root == null) {
                root = new Node(key);
                return true;
            }

            Node current = root;

            while (true) {

                if (key < current.key) {

                    if (current.left == null) {
                        current.left = new Node(key);
                        return true;
                    }

                    current = current.left;

                } else if (key > current.key) {

                    if (current.right == null) {
                        current.right = new Node(key);
                        return true;
                    }

                    current = current.right;

                } else {

                    // duplicate
                    return false;
                }
            }
        }

        // =========================
        // Height
        // empty = 0
        // leaf = 1
        // =========================
        public int height() {
            return heightRecursive(root);
        }

        private int heightRecursive(Node node) {

            if (node == null) {
                return 0;
            }

            return 1 + Math.max(
                    heightRecursive(node.left),
                    heightRecursive(node.right)
            );
        }

        // =========================
        // Search Comparison Count
        //
        // 每檢查一個 node 算一次 comparison
        // =========================
        public int searchComparisonCount(int target) {

            Node current = root;
            int comparisons = 0;

            while (current != null) {

                comparisons++;

                if (target == current.key) {
                    return comparisons;
                }

                if (target < current.key) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            return comparisons;
        }

        // =========================
        // 全部 Key 搜尋比較總數
        // =========================
        public int totalSearchComparisons(int[] keys) {

            int total = 0;

            for (int key : keys) {
                total += searchComparisonCount(key);
            }

            return total;
        }
    }

    // =========================
    // 建立 BST
    // =========================
    public static BST buildTree(int[] order) {

        BST tree = new BST();

        for (int key : order) {
            tree.add(key);
        }

        return tree;
    }

    // =========================
    // Report
    // =========================
    public static void printReport(
            String name,
            BST tree,
            int[] allKeys,
            int missingKey) {

        System.out.println("=== " + name + " ===");

        System.out.println(
                "Height = " + tree.height()
        );

        System.out.println(
                "Total comparisons for all keys = "
                        + tree.totalSearchComparisons(allKeys)
        );

        System.out.println(
                "Missing key " + missingKey
                        + " comparisons = "
                        + tree.searchComparisonCount(missingKey)
        );

        System.out.println();
    }

    public static void main(String[] args) {

        // =========================================
        // 相同的 15 個 Key
        // =========================================
        int[] allKeys = {
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                11, 12, 13, 14, 15
        };

        // =========================================
        // 1. 升冪
        // =========================================
        int[] ascending = {
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                11, 12, 13, 14, 15
        };

        // =========================================
        // 2. 降冪
        // =========================================
        int[] descending = {
                15, 14, 13, 12, 11,
                10, 9, 8, 7, 6,
                5, 4, 3, 2, 1
        };

        // =========================================
        // 3. 接近平衡
        //
        //           8
        //        /     \
        //       4       12
        //      / \     /  \
        //     2   6   10  14
        //    / \ / \  / \ / \
        //   1 3 5 7 9 11 13 15
        // =========================================
        int[] nearBalanced = {
                8,
                4, 12,
                2, 6, 10, 14,
                1, 3, 5, 7,
                9, 11, 13, 15
        };

        BST ascendingTree =
                buildTree(ascending);

        BST descendingTree =
                buildTree(descending);

        BST balancedTree =
                buildTree(nearBalanced);

        // 不存在的 key
        int missingKey = 16;

        // =========================================
        // Output
        // =========================================
        System.out.println(
                "=== Tree Shape Comparison ==="
        );

        System.out.println();

        printReport(
                "Ascending Order",
                ascendingTree,
                allKeys,
                missingKey
        );

        printReport(
                "Descending Order",
                descendingTree,
                allKeys,
                missingKey
        );

        printReport(
                "Near Balanced Order",
                balancedTree,
                allKeys,
                missingKey
        );
    }
}