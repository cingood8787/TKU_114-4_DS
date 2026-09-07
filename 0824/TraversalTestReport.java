import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TraversalTestReport {

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
    // Preorder
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
    // Inorder
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
    // Postorder
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
    // Level-order
    // BFS
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

    // 建立 List<String>
    public static List<String> list(String... values) {

        List<String> result = new ArrayList<>();

        for (String value : values) {
            result.add(value);
        }

        return result;
    }

    // =========================
    // 測試單一 traversal
    // =========================
    public static void printResult(
            String traversalName,
            List<String> expected,
            List<String> actual) {

        System.out.println(
                traversalName
                        + " expected = "
                        + expected
        );

        System.out.println(
                traversalName
                        + " actual   = "
                        + actual
        );

        System.out.println(
                traversalName
                        + " same     = "
                        + expected.equals(actual)
        );

        System.out.println();
    }

    // =========================
    // 完整 Tree 測試
    // =========================
    public static void testTree(
            String title,
            Node root,
            List<String> expectedPreorder,
            List<String> expectedInorder,
            List<String> expectedPostorder,
            List<String> expectedLevelOrder) {

        System.out.println(
                "================================"
        );

        System.out.println(
                "Test: " + title
        );

        System.out.println(
                "================================"
        );

        printResult(
                "Preorder",
                expectedPreorder,
                preorder(root)
        );

        printResult(
                "Inorder",
                expectedInorder,
                inorder(root)
        );

        printResult(
                "Postorder",
                expectedPostorder,
                postorder(root)
        );

        printResult(
                "Level-order",
                expectedLevelOrder,
                levelOrder(root)
        );
    }

    public static void main(String[] args) {

        // =========================
        // 1. Empty Tree
        // =========================
        testTree(
                "Empty Tree",
                null,
                list(),
                list(),
                list(),
                list()
        );


        // =========================
        // 2. Single-node Tree
        //
        // A
        // =========================
        Node single =
                new Node("A");

        testTree(
                "Single Node",
                single,
                list("A"),
                list("A"),
                list("A"),
                list("A")
        );


        // =========================
        // 3. Only-left Tree
        //
        //     A
        //    /
        //   B
        //  /
        // C
        // =========================
        Node onlyLeft =
                new Node(
                        "A",
                        new Node(
                                "B",
                                new Node("C"),
                                null
                        ),
                        null
                );

        testTree(
                "Only Left",
                onlyLeft,
                list("A", "B", "C"),
                list("C", "B", "A"),
                list("C", "B", "A"),
                list("A", "B", "C")
        );


        // =========================
        // 4. Only-right Tree
        //
        // A
        //  \
        //   B
        //    \
        //     C
        // =========================
        Node onlyRight =
                new Node(
                        "A",
                        null,
                        new Node(
                                "B",
                                null,
                                new Node("C")
                        )
                );

        testTree(
                "Only Right",
                onlyRight,
                list("A", "B", "C"),
                list("A", "B", "C"),
                list("C", "B", "A"),
                list("A", "B", "C")
        );


        // =========================
        // 5. Complete Tree
        //
        //        A
        //      /   \
        //     B     C
        //    / \   / \
        //   D   E F   G
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

        testTree(
                "Complete Tree",
                complete,
                list(
                        "A", "B", "D",
                        "E", "C", "F", "G"
                ),
                list(
                        "D", "B", "E",
                        "A", "F", "C", "G"
                ),
                list(
                        "D", "E", "B",
                        "F", "G", "C", "A"
                ),
                list(
                        "A", "B", "C",
                        "D", "E", "F", "G"
                )
        );


        // =========================
        // 6. Irregular Tree
        //
        //         A
        //       /   \
        //      B     C
        //       \   /
        //        D E
        //       /
        //      F
        // =========================
        Node irregular =
                new Node(
                        "A",

                        new Node(
                                "B",
                                null,
                                new Node(
                                        "D",
                                        new Node("F"),
                                        null
                                )
                        ),

                        new Node(
                                "C",
                                new Node("E"),
                                null
                        )
                );

        testTree(
                "Irregular Tree",
                irregular,
                list(
                        "A", "B", "D",
                        "F", "C", "E"
                ),
                list(
                        "B", "F", "D",
                        "A", "E", "C"
                ),
                list(
                        "F", "D", "B",
                        "E", "C", "A"
                ),
                list(
                        "A", "B", "C",
                        "D", "E", "F"
                )
        );
    }
}