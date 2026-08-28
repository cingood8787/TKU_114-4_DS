class CheckNode {
    int value;
    CheckNode left;
    CheckNode right;

    CheckNode(int value) {
        this.value = value;
    }
}

public class BstInvariantChecker {

    public static boolean isValid(CheckNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValid(CheckNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }

    public static void main(String[] args) {

        // =========================
        // 1. Valid Tree
        // =========================
        CheckNode validRoot = new CheckNode(50);

        validRoot.left = new CheckNode(30);
        validRoot.right = new CheckNode(70);

        validRoot.left.left = new CheckNode(20);
        validRoot.left.right = new CheckNode(40);

        validRoot.right.left = new CheckNode(60);
        validRoot.right.right = new CheckNode(80);

        System.out.println("===== Valid Tree =====");
        System.out.println("Valid = " + isValid(validRoot));
        System.out.println();


        // =========================
        // 2. Invalid Tree 1
        // 深層節點違反 root boundary
        // 55 在 50 的左子樹中，不合法
        // =========================
        CheckNode invalid1 = new CheckNode(50);

        invalid1.left = new CheckNode(30);
        invalid1.right = new CheckNode(70);

        invalid1.left.left = new CheckNode(20);
        invalid1.left.right = new CheckNode(40);

        invalid1.left.right.right = new CheckNode(55);

        System.out.println("===== Invalid Tree 1 =====");
        System.out.println("Valid = " + isValid(invalid1));
        System.out.println();


        // =========================
        // 3. Invalid Tree 2
        // 45 在 50 的右子樹中，不合法
        // =========================
        CheckNode invalid2 = new CheckNode(50);

        invalid2.left = new CheckNode(30);
        invalid2.right = new CheckNode(70);

        invalid2.right.left = new CheckNode(60);
        invalid2.right.right = new CheckNode(80);

        invalid2.right.left.left = new CheckNode(45);

        System.out.println("===== Invalid Tree 2 =====");
        System.out.println("Valid = " + isValid(invalid2));
        System.out.println();


        // =========================
        // 4. Invalid Tree 3
        // 75 位於 70 的左子樹中，不合法
        // =========================
        CheckNode invalid3 = new CheckNode(50);

        invalid3.left = new CheckNode(30);
        invalid3.right = new CheckNode(70);

        invalid3.right.left = new CheckNode(60);
        invalid3.right.right = new CheckNode(80);

        invalid3.right.left.right = new CheckNode(75);

        System.out.println("===== Invalid Tree 3 =====");
        System.out.println("Valid = " + isValid(invalid3));
    }
}