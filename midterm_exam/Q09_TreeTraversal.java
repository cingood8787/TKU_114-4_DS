import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q09_TreeTraversal {

    public static class Node {

        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static List<Integer> preorder(
            Node root) {

        List<Integer> result =
                new ArrayList<>();

        preorderRecursive(root, result);

        return result;
    }

    private static void preorderRecursive(
            Node node,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        result.add(node.value);

        preorderRecursive(
                node.left,
                result
        );

        preorderRecursive(
                node.right,
                result
        );
    }

    public static List<Integer> inorder(
            Node root) {

        List<Integer> result =
                new ArrayList<>();

        inorderRecursive(root, result);

        return result;
    }

    private static void inorderRecursive(
            Node node,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        inorderRecursive(
                node.left,
                result
        );

        result.add(node.value);

        inorderRecursive(
                node.right,
                result
        );
    }

    public static List<Integer> postorder(
            Node root) {

        List<Integer> result =
                new ArrayList<>();

        postorderRecursive(root, result);

        return result;
    }

    private static void postorderRecursive(
            Node node,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        postorderRecursive(
                node.left,
                result
        );

        postorderRecursive(
                node.right,
                result
        );

        result.add(node.value);
    }

    public static List<Integer> levelOrder(
            Node root) {

        List<Integer> result =
                new ArrayList<>();

        if (root == null) {
            return result;
        }

        Deque<Node> queue =
                new ArrayDeque<>();

        queue.addLast(root);

        while (!queue.isEmpty()) {

            Node node =
                    queue.removeFirst();

            result.add(node.value);

            if (node.left != null) {
                queue.addLast(node.left);
            }

            if (node.right != null) {
                queue.addLast(node.right);
            }
        }

        return result;
    }
}