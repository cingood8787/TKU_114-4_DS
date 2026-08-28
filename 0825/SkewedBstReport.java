class ReportNode {
    int value;
    ReportNode left;
    ReportNode right;

    ReportNode(int value) {
        this.value = value;
    }
}

class ReportBst {
    private ReportNode root;
    private int size = 0;

    public void add(int value) {
        if (root == null) {
            root = new ReportNode(value);
            size++;
            return;
        }

        ReportNode current = root;

        while (true) {
            if (value == current.value) {
                return;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new ReportNode(value);
                    size++;
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ReportNode(value);
                    size++;
                    return;
                }
                current = current.right;
            }
        }
    }

    public int size() {
        return size;
    }

    public int height() {
        return height(root);
    }

    private int height(ReportNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int searchComparisonCount(int target) {
        ReportNode current = root;
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

public class SkewedBstReport {

    public static void main(String[] args) {

        ReportBst skewedTree = new ReportBst();
        ReportBst balancedTree = new ReportBst();

        // 排序資料：會形成 skewed tree
        int[] sortedData = {
            10, 20, 30, 40, 50, 60, 70
        };

        // 平衡順序
        int[] balancedOrder = {
            40, 20, 60, 10, 30, 50, 70
        };

        for (int value : sortedData) {
            skewedTree.add(value);
        }

        for (int value : balancedOrder) {
            balancedTree.add(value);
        }

        int target = 70;

        System.out.println("===== Skewed Tree =====");
        System.out.println("Size = " + skewedTree.size());
        System.out.println("Height = " + skewedTree.height());
        System.out.println(
            "Search " + target + " comparison count = "
            + skewedTree.searchComparisonCount(target)
        );

        System.out.println();

        System.out.println("===== Balanced Tree =====");
        System.out.println("Size = " + balancedTree.size());
        System.out.println("Height = " + balancedTree.height());
        System.out.println(
            "Search " + target + " comparison count = "
            + balancedTree.searchComparisonCount(target)
        );
    }
}