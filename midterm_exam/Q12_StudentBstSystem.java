import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {

    public static class Student {

        private final int id;
        private final String name;
        private int score;

        public Student(
                int id,
                String name,
                int score) {

            if (id <= 0
                    || name == null
                    || name.isBlank()) {

                throw new IllegalArgumentException();
            }

            this.id = id;
            this.name = name.trim();
            this.score = clampScore(score);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        private void setScore(int score) {
            this.score = clampScore(score);
        }

        private static int clampScore(
                int score) {

            return Math.max(
                    0,
                    Math.min(100, score)
            );
        }

        @Override
        public String toString() {

            return id
                    + "|"
                    + name
                    + "|"
                    + score;
        }
    }

    private static class Node {

        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public boolean add(Student student) {

        if (student == null) {
            return false;
        }

        if (root == null) {

            root = new Node(student);

            return true;
        }

        Node current = root;

        while (true) {

            int id =
                    student.getId();

            int currentId =
                    current.student.getId();

            if (id == currentId) {
                return false;
            }

            if (id < currentId) {

                if (current.left == null) {

                    current.left =
                            new Node(student);

                    return true;
                }

                current = current.left;

            } else {

                if (current.right == null) {

                    current.right =
                            new Node(student);

                    return true;
                }

                current = current.right;
            }
        }
    }

    public Student find(int id) {

        Node node = findNode(id);

        return node == null
                ? null
                : node.student;
    }

    private Node findNode(int id) {

        Node current = root;

        while (current != null) {

            int currentId =
                    current.student.getId();

            if (id == currentId) {
                return current;
            }

            current =
                    id < currentId
                            ? current.left
                            : current.right;
        }

        return null;
    }

    public boolean updateScore(
            int id,
            int score) {

        Node node = findNode(id);

        if (node == null) {
            return false;
        }

        node.student.setScore(score);

        return true;
    }

    public boolean remove(int id) {

        if (find(id) == null) {
            return false;
        }

        root = removeNode(root, id);

        return true;
    }

    private Node removeNode(
            Node node,
            int id) {

        if (node == null) {
            return null;
        }

        int currentId =
                node.student.getId();

        if (id < currentId) {

            node.left =
                    removeNode(
                            node.left,
                            id
                    );

        } else if (id > currentId) {

            node.right =
                    removeNode(
                            node.right,
                            id
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

            node.student =
                    successor.student;

            node.right =
                    removeNode(
                            node.right,
                            successor.student.getId()
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

    public List<Student> studentsBetween(
            int lowId,
            int highId) {

        List<Student> result =
                new ArrayList<>();

        if (lowId > highId) {
            return result;
        }

        studentsBetween(
                root,
                lowId,
                highId,
                result
        );

        return result;
    }

    private void studentsBetween(
            Node node,
            int lowId,
            int highId,
            List<Student> result) {

        if (node == null) {
            return;
        }

        int id =
                node.student.getId();

        if (lowId < id) {

            studentsBetween(
                    node.left,
                    lowId,
                    highId,
                    result
            );
        }

        if (id >= lowId
                && id <= highId) {

            result.add(node.student);
        }

        if (id < highId) {

            studentsBetween(
                    node.right,
                    lowId,
                    highId,
                    result
            );
        }
    }

    public List<Student> inorder() {

        List<Student> result =
                new ArrayList<>();

        inorder(root, result);

        return result;
    }

    private void inorder(
            Node node,
            List<Student> result) {

        if (node == null) {
            return;
        }

        inorder(node.left, result);

        result.add(node.student);

        inorder(node.right, result);
    }
}