public class MenuTreeSearch {

    static class Node {
        String name;
        Node left;
        Node right;

        Node(String name) {
            this.name = name;
        }

        Node(String name, Node left, Node right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
    }

    // =========================
    // contains
    // 搜尋選單是否存在
    // =========================
    public static boolean contains(
            Node root,
            String target) {

        if (root == null) {
            return false;
        }

        if (root.name.equals(target)) {
            return true;
        }

        return contains(root.left, target)
                || contains(root.right, target);
    }

    // =========================
    // findDepth
    // Root depth = 0
    // 找不到回傳 -1
    // =========================
    public static int findDepth(
            Node root,
            String target) {

        return findDepthHelper(
                root,
                target,
                0
        );
    }

    private static int findDepthHelper(
            Node node,
            String target,
            int depth) {

        if (node == null) {
            return -1;
        }

        if (node.name.equals(target)) {
            return depth;
        }

        // 先搜尋左子樹
        int leftResult =
                findDepthHelper(
                        node.left,
                        target,
                        depth + 1
                );

        if (leftResult != -1) {
            return leftResult;
        }

        // 左邊找不到，再搜尋右子樹
        return findDepthHelper(
                node.right,
                target,
                depth + 1
        );
    }

    // =========================
    // countLeaves
    // 計算 leaf 數量
    // =========================
    public static int countLeaves(Node root) {

        if (root == null) {
            return 0;
        }

        // 左右都沒有 child = leaf
        if (root.left == null
                && root.right == null) {
            return 1;
        }

        return countLeaves(root.left)
                + countLeaves(root.right);
    }

    // =========================
    // Preorder Display
    // Root -> Left -> Right
    // =========================
    public static void preorderDisplay(
            Node root) {

        if (root == null) {
            return;
        }

        System.out.print(
                root.name + " "
        );

        preorderDisplay(root.left);
        preorderDisplay(root.right);
    }

    public static void main(String[] args) {

        /*
                       Main
                      /    \
                  Product   Member
                  /    \     /   \
               Food   Drink Login Register

            Preorder:
            Main Product Food Drink
            Member Login Register
        */

        Node root =
                new Node(
                        "Main",

                        new Node(
                                "Product",
                                new Node("Food"),
                                new Node("Drink")
                        ),

                        new Node(
                                "Member",
                                new Node("Login"),
                                new Node("Register")
                        )
                );

        // =========================
        // Preorder Display
        // =========================
        System.out.println(
                "=== Menu Preorder ==="
        );

        preorderDisplay(root);

        System.out.println();
        System.out.println();

        // =========================
        // contains 測試
        // =========================
        System.out.println(
                "contains Food = "
                        + contains(root, "Food")
        );

        System.out.println(
                "contains Login = "
                        + contains(root, "Login")
        );

        System.out.println(
                "contains Cart = "
                        + contains(root, "Cart")
        );

        System.out.println();

        // =========================
        // findDepth 測試
        // =========================
        System.out.println(
                "Main depth = "
                        + findDepth(root, "Main")
        );

        System.out.println(
                "Product depth = "
                        + findDepth(root, "Product")
        );

        System.out.println(
                "Food depth = "
                        + findDepth(root, "Food")
        );

        System.out.println(
                "Register depth = "
                        + findDepth(root, "Register")
        );

        System.out.println(
                "Cart depth = "
                        + findDepth(root, "Cart")
        );

        System.out.println();

        // =========================
        // Leaf Count
        // =========================
        System.out.println(
                "Leaf count = "
                        + countLeaves(root)
        );

        // Empty Tree
        System.out.println(
                "Empty tree leaf count = "
                        + countLeaves(null)
        );
    }
}