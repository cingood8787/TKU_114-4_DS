import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q11_BstHashDirectory {

    private static class Node {

        int id;
        Node left;
        Node right;

        Node(int id) {
            this.id = id;
        }
    }

    private Node root;

    private final Map<Integer, String> names =
            new HashMap<>();

    public boolean add(int id, String name) {

        if (id <= 0 || name == null) {
            return false;
        }

        name = name.trim();

        if (name.isEmpty() || names.containsKey(id)) {
            return false;
        }

        root = insert(root, id);
        names.put(id, name);

        return true;
    }

    private Node insert(Node node, int id) {

        if (node == null) {
            return new Node(id);
        }

        if (id < node.id) {
            node.left = insert(node.left, id);
        } else if (id > node.id) {
            node.right = insert(node.right, id);
        }

        return node;
    }

    public String findName(int id) {
        return names.get(id);
    }

    public boolean remove(int id) {

        if (!names.containsKey(id)) {
            return false;
        }

        root = delete(root, id);
        names.remove(id);

        return true;
    }

    private Node delete(Node node, int id) {

        if (node == null) {
            return null;
        }

        if (id < node.id) {

            node.left = delete(node.left, id);

        } else if (id > node.id) {

            node.right = delete(node.right, id);

        } else {

            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            Node successor = findMin(node.right);

            node.id = successor.id;

            node.right =
                    delete(node.right, successor.id);
        }

        return node;
    }

    private Node findMin(Node node) {

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public List<Integer> idsBetween(int low, int high) {

        List<Integer> result = new ArrayList<>();

        if (low > high) {
            return result;
        }

        range(root, low, high, result);

        return result;
    }

    private void range(
            Node node,
            int low,
            int high,
            List<Integer> result) {

        if (node == null) {
            return;
        }

        if (node.id > low) {
            range(node.left, low, high, result);
        }

        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }

        if (node.id < high) {
            range(node.right, low, high, result);
        }
    }

    public int size() {
        return names.size();
    }
}