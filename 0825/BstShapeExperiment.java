class ShapeNode {
    int value;
    ShapeNode left;
    ShapeNode right;

    ShapeNode(int value) {
        this.value = value;
    }
}

class ShapeBst {
    private ShapeNode root;

    public void insert(int value) {
        if (root == null) {
            root = new ShapeNode(value);
            return;
        }

        ShapeNode current = root;

        while (true) {
            if (value == current.value) {
                return;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new ShapeNode(value);
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ShapeNode(value);
                    return;
                }
                current = current.right;
            }
        }
    }

    public int height() {
        return height(root);
    }

    private int height(ShapeNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(
            height(node.left),
            height(node.right)
        );
    }

    public int searchComparisonCount(int target) {
        ShapeNode current = root;
        int count = 0;

        while (current != null) {
            count++;

            if (target == current.value) {
                return count;
            }

            if (target < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return count;
    }
}

public class BstShapeExperiment {

    public static void printReport(
            String title,
            int[] insertOrder,
            int[] searchValues) {

        ShapeBst tree = new ShapeBst();

        for (int value : insertOrder) {
            tree.insert(value);
        }

        System.out.println("===== " + title + " =====");

        System.out.print("Insert order: ");
        for (int value : insertOrder) {
            System.out.print(value + " ");
        }
        System.out.println();

        System.out.println("Height = " + tree.height());

        int totalComparisons = 0;

        System.out.println("Search comparison count:");

        for (int value : searchValues) {
            int count = tree.searchComparisonCount(value);

            totalComparisons += count;

            System.out.println(
                "Search " + value + " = " + count
            );
        }

        System.out.println(
            "Total comparison count = " + totalComparisons
        );

        double average =
            (double) totalComparisons / searchValues.length;

        System.out.printf(
            "Average comparison count = %.2f%n",
            average
        );

        System.out.println();
    }

    public static void main(String[] args) {

        // 相同的 15 個值
        int[] allValues = {
            10, 20, 30, 40, 50,
            60, 70, 80, 90, 100,
            110, 120, 130, 140, 150
        };

        // 1. 排序順序
        // 會形成非常偏斜的 BST
        int[] sortedOrder = {
            10, 20, 30, 40, 50,
            60, 70, 80, 90, 100,
            110, 120, 130, 140, 150
        };

        // 2. 平衡順序
        int[] balancedOrder = {
            80,
            40, 120,
            20, 60, 100, 140,
            10, 30, 50, 70,
            90, 110, 130, 150
        };

        // 3. 另一種不同順序
        int[] mixedOrder = {
            50, 120, 20, 90, 140,
            10, 40, 70, 110, 150,
            30, 60, 80, 100, 130
        };

        printReport(
            "Order 1 - Sorted",
            sortedOrder,
            allValues
        );

        printReport(
            "Order 2 - Balanced",
            balancedOrder,
            allValues
        );

        printReport(
            "Order 3 - Mixed",
            mixedOrder,
            allValues
        );
    }
}