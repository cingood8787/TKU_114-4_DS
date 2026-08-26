import java.util.ArrayList;
import java.util.List;

public class Q11_BstDeletion {

    private static class Node {

        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public boolean add(int value) {

        if (root == null) {

            root = new Node(value);
            size = 1;

            return true;
        }

        Node current = root;

        while (true) {

            if (value == current.value) {
                return false;
            }

            if (value < current.value) {

                if (current.left == null) {

                    current.left =
                            new Node(value);

                    size++;

                    return true;
                }

                current = current.left;

            } else {

                if (current.right == null) {

                    current.right =
                            new Node(value);

                    size++;

                    return true;
                }

                current = current.right;
            }
        }
    }

    public boolean remove(int value) {

        if (!contains(value)) {
            return false;
        }

        root = removeNode(root, value);

        size--;

        return true;
    }

    private Node removeNode(
            Node node,
            int value) {

        if (node == null) {
            return null;
        }

        if (value < node.value) {

            node.left =
                    removeNode(
                            node.left,
                            value
                    );

        } else if (value > node.value) {

            node.right =
                    removeNode(
                            node.right,
                            value
                    );

        } else {

            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            Node successor =
                    findMin(node.right);

            node.value =
                    successor.value;

            node.right =
                    removeNode(
                            node.right,
                            successor.value
                    );
        }

        return node;
    }

    private Node findMin(Node node) {

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public boolean contains(int value) {

        Node current = root;

        while (current != null) {

            if (value == current.value) {
                return true;
            }

            current =
                    value < current.value
                            ? current.left
                            : current.right;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public List<Integer> inorder() {

        List<Integer> result =
                new ArrayList<>();

        inorder(root, result);

        return result;
    }

    private void inorder(
            Node node,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        inorder(node.left, result);

        result.add(node.value);

        inorder(node.right, result);
    }

    public boolean isValid() {

        return isValid(
                root,
                Long.MIN_VALUE,
                Long.MAX_VALUE
        );
    }

    private boolean isValid(
            Node node,
            long low,
            long high) {

        if (node == null) {
            return true;
        }

        if (node.value <= low
                || node.value >= high) {

            return false;
        }

        return isValid(
                node.left,
                low,
                node.value
        )
                && isValid(
                node.right,
                node.value,
                high
        );
    }
}