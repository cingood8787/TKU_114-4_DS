import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TraversalResultCollector {

    static class Node {
        String value;
        Node left;
        Node right;

        Node(String value) {
            this.value = value;
        }

        Node(String value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    // =========================
    // 1. Preorder
    // Root -> Left -> Right
    // =========================
    public static List<String> preorder(Node root) {

        List<String> result = new ArrayList<>();

        preorderHelper(root, result);

        return result;
    }

    private static void preorderHelper(
            Node node,
            List<String> result) {

        if (node == null) {
            return;
        }

        result.add(node.value);

        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }


    // =========================
    // 2. Inorder
    // Left -> Root -> Right
    // =========================
    public static List<String> inorder(Node root) {

        List<String> result = new ArrayList<>();

        inorderHelper(root, result);

        return result;
    }

    private static void inorderHelper(
            Node node,
            List<String> result) {

        if (node == null) {
            return;
        }

        inorderHelper(node.left, result);

        result.add(node.value);

        inorderHelper(node.right, result);
    }


    // =========================
    // 3. Postorder
    // Left -> Right -> Root
    // =========================
    public static List<String> postorder(Node root) {

        List<String> result = new ArrayList<>();

        postorderHelper(root, result);

        return result;
    }

    private static void postorderHelper(
            Node node,
            List<String> result) {

        if (node == null) {
            return;
        }

        postorderHelper(node.left, result);
        postorderHelper(node.right, result);

        result.add(node.value);
    }


    // =========================
    // 4. Level-order
    // BFS / Queue
    // =========================
    public static List<String> levelOrder(Node root) {

        List<String> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<Node> queue = new LinkedList<>();

        queue.offer(root);

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            result.add(current.value);

            if (current.left != null) {
                queue.offer(current.left);
            }

            if (current.right != null) {
                queue.offer(current.right);
            }
        }

        return result;
    }


    // =========================
    // 測試用
    // =========================
    public static void showTest(
            String title,
            Node root) {

        System.out.println(
                "=== " + title + " ==="
        );

        System.out.println(
                "Preorder   = " + preorder(root)
        );

        System.out.println(
                "Inorder    = " + inorder(root)
        );

        System.out.println(
                "Postorder  = " + postorder(root)
        );

        System.out.println(
                "Level-order = " + levelOrder(root)
        );

        System.out.println();
    }


    public static void main(String[] args) {

        // =========================
        // 1. Empty Tree
        // =========================
        showTest(
                "Empty Tree",
                null
        );


        // =========================
        // 2. Single-node Tree
        //
        // A
        // =========================
        Node single = new Node("A");

        showTest(
                "Single Node Tree",
                single
        );


        // =========================
        // 3. Left-skewed Tree
        //
        //       A
        //      /
        //     B
        //    /
        //   C
        //  /
        // D
        // =========================
        Node leftSkewed = new Node("A");

        leftSkewed.left =
                new Node("B");

        leftSkewed.left.left =
                new Node("C");

        leftSkewed.left.left.left =
                new Node("D");

        showTest(
                "Left Skewed Tree",
                leftSkewed
        );


        // =========================
        // 4. Complete Tree
        //
        //         A
        //       /   \
        //      B     C
        //     / \   / \
        //    D   E F   G
        // =========================
        Node complete =
                new Node(
                        "A",

                        new Node(
                                "B",
                                new Node("D"),
                                new Node("E")
                        ),

                        new Node(
                                "C",
                                new Node("F"),
                                new Node("G")
                        )
                );

        showTest(
                "Complete Tree",
                complete
        );
    }
}