public class BinaryTreeStatistics {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }

        Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;

    public BinaryTreeStatistics() {
        root = null;
    }

    public BinaryTreeStatistics(Node root) {
        this.root = root;
    }

    // =========================
    // size
    // =========================
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        return 1
                + size(node.left)
                + size(node.right);
    }

    // =========================
    // sum
    // =========================
    public int sum() {
        return sum(root);
    }

    private int sum(Node node) {
        if (node == null) {
            return 0;
        }

        return node.value
                + sum(node.left)
                + sum(node.right);
    }

    // =========================
    // maximum
    // Empty Tree 明確處理
    // =========================
    public int maximum() {

        if (root == null) {
            throw new IllegalStateException(
                    "Cannot find maximum of empty tree"
            );
        }

        return maximum(root);
    }

    private int maximum(Node node) {

        int max = node.value;

        if (node.left != null) {
            max = Math.max(
                    max,
                    maximum(node.left)
            );
        }

        if (node.right != null) {
            max = Math.max(
                    max,
                    maximum(node.right)
            );
        }

        return max;
    }

    // =========================
    // leaf count
    // =========================
    public int leafCount() {
        return leafCount(root);
    }

    private int leafCount(Node node) {

        if (node == null) {
            return 0;
        }

        if (node.left == null
                && node.right == null) {
            return 1;
        }

        return leafCount(node.left)
                + leafCount(node.right);
    }

    // =========================
    // height
    // Empty Tree = 0
    // Single Node = 1
    // =========================
    public int height() {
        return height(root);
    }

    private int height(Node node) {

        if (node == null) {
            return 0;
        }

        return 1 + Math.max(
                height(node.left),
                height(node.right)
        );
    }

    // =========================
    // contains
    // =========================
    public boolean contains(int target) {
        return contains(root, target);
    }

    private boolean contains(
            Node node,
            int target) {

        if (node == null) {
            return false;
        }

        if (node.value == target) {
            return true;
        }

        return contains(node.left, target)
                || contains(node.right, target);
    }

    // =========================
    // Report
    // =========================
    public void report() {

        System.out.println(
                "Size = " + size()
        );

        System.out.println(
                "Sum = " + sum()
        );

        if (root == null) {
            System.out.println(
                    "Maximum = undefined (empty tree)"
            );
        } else {
            System.out.println(
                    "Maximum = " + maximum()
            );
        }

        System.out.println(
                "Leaf count = " + leafCount()
        );

        System.out.println(
                "Height = " + height()
        );
    }

    public static void main(String[] args) {

        /*
                     10
                    /  \
                   5    20
                  / \   / \
                 3   8 15  30

            Size = 7
            Sum = 91
            Maximum = 30
            Leaves = 4
            Height = 3
        */

        Node root =
                new Node(
                        10,
                        new Node(
                                5,
                                new Node(3),
                                new Node(8)
                        ),
                        new Node(
                                20,
                                new Node(15),
                                new Node(30)
                        )
                );

        BinaryTreeStatistics tree =
                new BinaryTreeStatistics(root);

        System.out.println(
                "=== Normal Tree ==="
        );

        tree.report();

        System.out.println(
                "Contains 15 = "
                        + tree.contains(15)
        );

        System.out.println(
                "Contains 100 = "
                        + tree.contains(100)
        );


        // =========================
        // Single Node Tree
        // =========================
        System.out.println();
        System.out.println(
                "=== Single Node Tree ==="
        );

        BinaryTreeStatistics single =
                new BinaryTreeStatistics(
                        new Node(-5)
                );

        single.report();

        System.out.println(
                "Contains -5 = "
                        + single.contains(-5)
        );


        // =========================
        // Empty Tree
        // =========================
        System.out.println();
        System.out.println(
                "=== Empty Tree ==="
        );

        BinaryTreeStatistics empty =
                new BinaryTreeStatistics();

        empty.report();

        System.out.println(
                "Contains 10 = "
                        + empty.contains(10)
        );

        // maximum empty tree 測試
        try {
            empty.maximum();
        } catch (IllegalStateException e) {
            System.out.println(
                    "Maximum error: "
                            + e.getMessage()
            );
        }
    }
}