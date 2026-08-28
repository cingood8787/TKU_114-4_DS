class DeleteNode {
    int value;
    DeleteNode left;
    DeleteNode right;

    DeleteNode(int value) {
        this.value = value;
    }
}

class DeleteBst {
    private DeleteNode root;
    private int size = 0;

    public void add(int value) {
        root = add(root, value);
    }

    private DeleteNode add(DeleteNode node, int value) {
        if (node == null) {
            size++;
            return new DeleteNode(value);
        }

        if (value < node.value) {
            node.left = add(node.left, value);
        } else if (value > node.value) {
            node.right = add(node.right, value);
        }

        return node;
    }

    public void delete(int value) {
        root = delete(root, value);
    }

    private DeleteNode delete(DeleteNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = delete(node.left, value);
        } else if (value > node.value) {
            node.right = delete(node.right, value);
        } else {

            // Case 1：leaf
            if (node.left == null && node.right == null) {
                size--;
                return null;
            }

            // Case 2：只有右子樹
            if (node.left == null) {
                size--;
                return node.right;
            }

            // Case 2：只有左子樹
            if (node.right == null) {
                size--;
                return node.left;
            }

            // Case 3：有兩個 child
            DeleteNode successor = findMin(node.right);

            node.value = successor.value;

            // 刪除 successor
            node.right = deleteSuccessor(node.right, successor.value);
        }

        return node;
    }

    private DeleteNode deleteSuccessor(DeleteNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = deleteSuccessor(node.left, value);
        } else if (value > node.value) {
            node.right = deleteSuccessor(node.right, value);
        } else {
            size--;

            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }
        }

        return node;
    }

    private DeleteNode findMin(DeleteNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public int size() {
        return size;
    }

    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(DeleteNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.print(node.value + " ");
        inorder(node.right);
    }

    public boolean isValid() {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValid(DeleteNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }

    public void printStatus() {
        System.out.print("Inorder: ");
        inorder();

        System.out.println("Size: " + size());
        System.out.println("Valid: " + isValid());
        System.out.println();
    }
}

public class BstDeleteCases {

    public static void main(String[] args) {

        DeleteBst tree = new DeleteBst();

        int[] values = {
            50, 30, 70, 20, 40, 60, 80, 65
        };

        for (int value : values) {
            tree.add(value);
        }

        System.out.println("===== Original Tree =====");
        tree.printStatus();

        // Case 1：刪除 leaf
        System.out.println("===== Delete Leaf: 20 =====");
        tree.delete(20);
        tree.printStatus();

        // Case 2：刪除 single-child node
        // 60 只有右邊的 65
        System.out.println("===== Delete Single-Child Node: 60 =====");
        tree.delete(60);
        tree.printStatus();

        // Case 3：刪除 two-child node
        // 70 左邊有 65，右邊有 80
        System.out.println("===== Delete Two-Child Node: 70 =====");
        tree.delete(70);
        tree.printStatus();
    }
}