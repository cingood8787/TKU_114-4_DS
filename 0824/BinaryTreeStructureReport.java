import java.util.ArrayList;
import java.util.List;

public class BinaryTreeStructureReport {

    // Node
    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    // Binary Tree
    static class BinaryTree {
        Node root;

        // 取得 root
        public Integer rootValue() {
            return root == null ? null : root.value;
        }

        // 計算 size
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

        // 計算 leaf count
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

        // 計算 height
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

        // 取得所有 leaf
        public List<Integer> leaves() {
            List<Integer> result =
                    new ArrayList<>();

            collectLeaves(root, result);

            return result;
        }

        private void collectLeaves(
                Node node,
                List<Integer> result) {

            if (node == null) {
                return;
            }

            if (node.left == null
                    && node.right == null) {

                result.add(node.value);
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

        // 輸出報表
        public void report(String title) {

            System.out.println(
                    "=== " + title + " ==="
            );

            System.out.println(
                    "Root = " + rootValue()
            );

            System.out.println(
                    "Leaves = " + leaves()
            );

            System.out.println(
                    "Size = " + size()
            );

            System.out.println(
                    "Leaf count = " + leafCount()
            );

            System.out.println(
                    "Height = " + height()
            );

            System.out.println();
        }
    }

    public static void main(String[] args) {

        // =========================
        // 一般 Binary Tree
        // 至少 7 個 node
        // =========================
        BinaryTree tree =
                new BinaryTree();

        tree.root = new Node(10);

        tree.root.left =
                new Node(20);

        tree.root.right =
                new Node(30);

        tree.root.left.left =
                new Node(40);

        tree.root.left.right =
                new Node(50);

        tree.root.right.left =
                new Node(60);

        tree.root.right.right =
                new Node(70);

        /*
                    10
                   /  \
                 20    30
                / \    / \
              40  50  60  70
         */

        tree.report(
                "Normal Tree"
        );


        // =========================
        // Empty Tree
        // =========================
        BinaryTree emptyTree =
                new BinaryTree();

        emptyTree.report(
                "Empty Tree"
        );


        // =========================
        // Single-node Tree
        // =========================
        BinaryTree singleTree =
                new BinaryTree();

        singleTree.root =
                new Node(100);

        singleTree.report(
                "Single Node Tree"
        );
    }
}