public class TraversalSelector {

    static class Node {
        String value;
        Node left;
        Node right;

        Node(String value) {
            this.value = value;
        }
    }

    // Preorder：Root -> Left -> Right
    // 對應 Prefix
    public static String preorder(Node node) {
        if (node == null) {
            return "";
        }

        String left = preorder(node.left);
        String right = preorder(node.right);

        String result = node.value;

        if (!left.isEmpty()) {
            result += " " + left;
        }

        if (!right.isEmpty()) {
            result += " " + right;
        }

        return result;
    }

    // Inorder：Left -> Root -> Right
    // 對應 Infix，而且要加括號
    public static String inorder(Node node) {
        if (node == null) {
            return "";
        }

        // 葉節點直接回傳值
        if (node.left == null && node.right == null) {
            return node.value;
        }

        return "(" + inorder(node.left)
                + " " + node.value + " "
                + inorder(node.right) + ")";
    }

    // Postorder：Left -> Right -> Root
    // 對應 Postfix
    public static String postorder(Node node) {
        if (node == null) {
            return "";
        }

        String left = postorder(node.left);
        String right = postorder(node.right);

        String result = "";

        if (!left.isEmpty()) {
            result += left;
        }

        if (!right.isEmpty()) {
            if (!result.isEmpty()) {
                result += " ";
            }
            result += right;
        }

        if (!result.isEmpty()) {
            result += " ";
        }

        result += node.value;

        return result;
    }

    public static void main(String[] args) {

        /*
                 *
                / \
               +   -
              / \ / \
             A  B C  D

           表示：
           (A + B) * (C - D)
        */

        Node root = new Node("*");

        root.left = new Node("+");
        root.right = new Node("-");

        root.left.left = new Node("A");
        root.left.right = new Node("B");

        root.right.left = new Node("C");
        root.right.right = new Node("D");

        System.out.println("Prefix  : " + preorder(root));
        System.out.println("Infix   : " + inorder(root));
        System.out.println("Postfix : " + postorder(root));
    }
}