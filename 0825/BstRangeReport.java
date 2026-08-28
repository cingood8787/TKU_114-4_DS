class RangeNode {
    int value;
    RangeNode left;
    RangeNode right;

    RangeNode(int value) {
        this.value = value;
    }
}

class RangeBst {
    private RangeNode root;

    public void add(int value) {
        if (root == null) {
            root = new RangeNode(value);
            return;
        }

        RangeNode current = root;

        while (true) {
            if (value == current.value) {
                return;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new RangeNode(value);
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new RangeNode(value);
                    return;
                }
                current = current.right;
            }
        }
    }

    public Integer min() {
        if (root == null) {
            return null;
        }

        RangeNode current = root;

        while (current.left != null) {
            current = current.left;
        }

        return current.value;
    }

    public Integer max() {
        if (root == null) {
            return null;
        }

        RangeNode current = root;

        while (current.right != null) {
            current = current.right;
        }

        return current.value;
    }

    public void printRange(int low, int high) {
        if (low > high) {
            int temp = low;
            low = high;
            high = temp;
        }

        printRange(root, low, high);
        System.out.println();
    }

    private void printRange(RangeNode node, int low, int high) {
        if (node == null) {
            return;
        }

        if (node.value > low) {
            printRange(node.left, low, high);
        }

        if (node.value >= low && node.value <= high) {
            System.out.print(node.value + " ");
        }

        if (node.value < high) {
            printRange(node.right, low, high);
        }
    }
}

public class BstRangeReport {

    public static void main(String[] args) {
        RangeBst tree = new RangeBst();

        int[] values = {
            50, 30, 70, 20, 40, 60, 80, 35, 65
        };

        for (int value : values) {
            tree.add(value);
        }

        System.out.println("Min = " + tree.min());
        System.out.println("Max = " + tree.max());

        System.out.print("Range 30 ~ 65: ");
        tree.printRange(30, 65);

        System.out.print("Range 65 ~ 30: ");
        tree.printRange(65, 30);
    }
}