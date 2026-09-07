import java.util.ArrayList;
import java.util.List;

public class ThreeTraversalPractice {

    static class Node {
        char value;
        Node left;
        Node right;

        Node(char value) {
            this.value = value;
        }

        Node(char value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    // =========================
    // Preorder
    // Root -> Left -> Right
    // =========================
    public static void preorder(
            Node node,
            List<Character> result) {

        // 處理 null
        if (node == null) {
            return;
        }

        result.add(node.value);

        preorder(node.left, result);
        preorder(node.right, result);
    }

    // =========================
    // Inorder
    // Left -> Root -> Right
    // =========================
    public static void inorder(
            Node node,
            List<Character> result) {

        // 處理 null
        if (node == null) {
            return;
        }

        inorder(node.left, result);

        result.add(node.value);

        inorder(node.right, result);
    }

    // =========================
    // Postorder
    // Left -> Right -> Root
    // =========================
    public static void postorder(
            Node node,
            List<Character> result) {

        // 處理 null
        if (node == null) {
            return;
        }

        postorder(node.left, result);
        postorder(node.right, result);

        result.add(node.value);
    }

    public static void main(String[] args) {

        /*
                    M
                   / \
                  F   T
                 /   / \
                B   R   Z

            M(F(B,null),T(R,Z))
        */

        Node root =
                new Node(
                        'M',

                        new Node(
                                'F',
                                new Node('B'),
                                null
                        ),

                        new Node(
                                'T',
                                new Node('R'),
                                new Node('Z')
                        )
                );

        List<Character> pre =
                new ArrayList<>();

        List<Character> in =
                new ArrayList<>();

        List<Character> post =
                new ArrayList<>();

        preorder(root, pre);
        inorder(root, in);
        postorder(root, post);

        System.out.println(
                "Preorder  = " + pre
        );

        System.out.println(
                "Inorder   = " + in
        );

        System.out.println(
                "Postorder = " + post
        );

        // 額外測試 null
        List<Character> empty =
                new ArrayList<>();

        preorder(null, empty);

        System.out.println(
                "Null tree preorder = " + empty
        );
    }
}