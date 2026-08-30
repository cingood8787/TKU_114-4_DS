public class TreeBugLab {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    // =====================================================
    // 建立測試用 BST
    // =====================================================
    public static Node buildTree() {
        /*
                50
               /  \
             30    70
            / \    / \
           20 40  60 80
        */

        Node root = new Node(50);

        root.left = new Node(30);
        root.right = new Node(70);

        root.left.left = new Node(20);
        root.left.right = new Node(40);

        root.right.left = new Node(60);
        root.right.right = new Node(80);

        return root;
    }

    // =====================================================
    // Bug 1：Search 方向相反
    // =====================================================

    // 錯誤版本
    public static boolean buggySearch(Node node, int target) {
        if (node == null) {
            return false;
        }

        if (target == node.value) {
            return true;
        }

        // 錯誤：
        // target 比目前值小，卻往右找
        if (target < node.value) {
            return buggySearch(node.right, target);
        }

        // target 比目前值大，卻往左找
        return buggySearch(node.left, target);
    }

    // 修正版
    public static boolean fixedSearch(Node node, int target) {
        if (node == null) {
            return false;
        }

        if (target == node.value) {
            return true;
        }

        if (target < node.value) {
            return fixedSearch(node.left, target);
        }

        return fixedSearch(node.right, target);
    }


    // =====================================================
    // Bug 2：Inorder 順序錯誤
    // =====================================================

    // 錯誤版本
    // 錯寫成 Root -> Left -> Right
    public static void buggyInorder(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.value + " ");
        buggyInorder(node.left);
        buggyInorder(node.right);
    }

    // 修正版
    // Left -> Root -> Right
    public static void fixedInorder(Node node) {
        if (node == null) {
            return;
        }

        fixedInorder(node.left);
        System.out.print(node.value + " ");
        fixedInorder(node.right);
    }


    // =====================================================
    // Bug 3：Delete 遺失 Child
    // =====================================================

    /*
        最小失敗案例：

            50
           /
          30
         /
        20

        如果刪掉 30，
        正確結果應該是：

            50
           /
          20

        不能把 20 一起弄丟。
    */

    // 錯誤版本
    public static Node buggyDelete(Node node, int target) {
        if (node == null) {
            return null;
        }

        if (target < node.value) {
            node.left = buggyDelete(node.left, target);

        } else if (target > node.value) {
            node.right = buggyDelete(node.right, target);

        } else {

            // 錯誤：
            // 有一個 child 時直接 return null
            // 會造成 child 遺失

            if (node.left == null || node.right == null) {
                return null;
            }

            Node successor = findMin(node.right);

            node.value = successor.value;

            node.right =
                    buggyDelete(node.right, successor.value);
        }

        return node;
    }

    // 修正版
    public static Node fixedDelete(Node node, int target) {
        if (node == null) {
            return null;
        }

        if (target < node.value) {
            node.left = fixedDelete(node.left, target);

        } else if (target > node.value) {
            node.right = fixedDelete(node.right, target);

        } else {

            // Case 1：沒有 child
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2：只有右 child
            if (node.left == null) {
                return node.right;
            }

            // Case 2：只有左 child
            if (node.right == null) {
                return node.left;
            }

            // Case 3：有兩個 child
            Node successor = findMin(node.right);

            node.value = successor.value;

            node.right =
                    fixedDelete(node.right, successor.value);
        }

        return node;
    }

    public static Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }


    // =====================================================
    // Bug 4：Validation 只檢查直接 Child
    // =====================================================

    /*
        最小失敗案例：

              50
             /  \
           30    70
               /
              40

        70 的左 child 是 40，
        40 < 70，所以只看 parent-child 好像正確。

        但 40 位於 50 的右子樹，
        應該必須 > 50。

        所以整棵 BST 其實是錯的。
    */

    // 錯誤版本
    public static boolean buggyValidate(Node node) {
        if (node == null) {
            return true;
        }

        // 只檢查直接 child
        if (node.left != null
                && node.left.value >= node.value) {
            return false;
        }

        if (node.right != null
                && node.right.value <= node.value) {
            return false;
        }

        return buggyValidate(node.left)
                && buggyValidate(node.right);
    }

    // 修正版
    public static boolean fixedValidate(Node node) {
        return fixedValidate(
                node,
                Long.MIN_VALUE,
                Long.MAX_VALUE
        );
    }

    public static boolean fixedValidate(
            Node node,
            long min,
            long max) {

        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return fixedValidate(
                node.left,
                min,
                node.value
        )
                &&
                fixedValidate(
                        node.right,
                        node.value,
                        max
                );
    }


    // =====================================================
    // Main：測試四種 Bug
    // =====================================================
    public static void main(String[] args) {

        // =================================================
        // Bug 1：Search 方向相反
        // =================================================
        System.out.println("=== Bug 1: Search Direction ===");

        Node searchTree = buildTree();

        System.out.println(
                "Search 20 with buggySearch = "
                        + buggySearch(searchTree, 20)
        );

        System.out.println(
                "Search 20 with fixedSearch = "
                        + fixedSearch(searchTree, 20)
        );


        System.out.println();


        // =================================================
        // Bug 2：Inorder 順序錯誤
        // =================================================
        System.out.println("=== Bug 2: Inorder Order ===");

        Node inorderTree = buildTree();

        System.out.print("Buggy inorder : ");
        buggyInorder(inorderTree);
        System.out.println();

        System.out.print("Fixed inorder : ");
        fixedInorder(inorderTree);
        System.out.println();


        System.out.println();


        // =================================================
        // Bug 3：Delete 遺失 Child
        // =================================================
        System.out.println("=== Bug 3: Delete Lost Child ===");

        // 最小失敗案例
        Node deleteTree1 = new Node(50);
        deleteTree1.left = new Node(30);
        deleteTree1.left.left = new Node(20);

        System.out.print("Before buggy delete : ");
        fixedInorder(deleteTree1);
        System.out.println();

        deleteTree1 = buggyDelete(deleteTree1, 30);

        System.out.print("After buggy delete  : ");
        fixedInorder(deleteTree1);
        System.out.println();


        Node deleteTree2 = new Node(50);
        deleteTree2.left = new Node(30);
        deleteTree2.left.left = new Node(20);

        System.out.print("Before fixed delete : ");
        fixedInorder(deleteTree2);
        System.out.println();

        deleteTree2 = fixedDelete(deleteTree2, 30);

        System.out.print("After fixed delete  : ");
        fixedInorder(deleteTree2);
        System.out.println();


        System.out.println();


        // =================================================
        // Bug 4：Validation 只檢查直接 Child
        // =================================================
        System.out.println("=== Bug 4: Validation ===");

        /*
              50
             /  \
           30    70
               /
              40
        */

        Node invalidTree = new Node(50);

        invalidTree.left = new Node(30);
        invalidTree.right = new Node(70);

        invalidTree.right.left = new Node(40);

        System.out.println(
                "buggyValidate = "
                        + buggyValidate(invalidTree)
        );

        System.out.println(
                "fixedValidate = "
                        + fixedValidate(invalidTree)
        );
    }
}