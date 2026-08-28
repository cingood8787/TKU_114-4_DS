class BstNode {
    int value;
    BstNode left;
    BstNode right;

    BstNode(int value) {
        this.value = value;
    }
}

class Bst {
    private BstNode root;

    public boolean add(int value) {
        if (root == null) {
            root = new BstNode(value);
            return true;
        }

        BstNode current = root;

        while (true) {
            if (value == current.value) {
                return false;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new BstNode(value);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new BstNode(value);
                    return true;
                }
                current = current.right;
            }
        }
    }

    public boolean searchTrace(int target) {
        BstNode current = root;
        int comparisonCount = 0;

        System.out.println("Search target = " + target);

        while (current != null) {
            comparisonCount++;

            System.out.println(
                "current = " + current.value +
                ", comparison count = " + comparisonCount
            );

            if (target == current.value) {
                System.out.println("direction = FOUND");
                System.out.println("Result: found");
                System.out.println("Total comparisons = " + comparisonCount);
                System.out.println();
                return true;
            }

            if (target < current.value) {
                System.out.println("direction = LEFT");
                current = current.left;
            } else {
                System.out.println("direction = RIGHT");
                current = current.right;
            }
        }

        System.out.println("current = null");
        System.out.println("Result: not found");
        System.out.println("Total comparisons = " + comparisonCount);
        System.out.println();

        return false;
    }
}

public class BstSearchTrace {

    public static void main(String[] args) {

        Bst tree = new Bst();

        int[] values = {
            50, 30, 70, 20, 40, 60, 80
        };

        for (int value : values) {
            tree.add(value);
        }

        // root
        System.out.println("===== Root =====");
        tree.searchTrace(50);

        // leaf
        System.out.println("===== Leaf =====");
        tree.searchTrace(20);

        // internal node
        System.out.println("===== Internal Node =====");
        tree.searchTrace(70);

        // missing value
        System.out.println("===== Missing Value =====");
        tree.searchTrace(65);
    }
}