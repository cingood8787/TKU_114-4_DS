class TestNode {
    int value;
    TestNode left;
    TestNode right;

    TestNode(int value) {
        this.value = value;
    }
}

class TestBst {
    private TestNode root;
    private int size = 0;

    public boolean insert(int value) {
        if (root == null) {
            root = new TestNode(value);
            size++;
            return true;
        }

        TestNode current = root;

        while (true) {
            if (value == current.value) {
                return false;
            }

            if (value < current.value) {
                if (current.left == null) {
                    current.left = new TestNode(value);
                    size++;
                    return true;
                }

                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TestNode(value);
                    size++;
                    return true;
                }

                current = current.right;
            }
        }
    }

    public boolean contains(int value) {
        TestNode current = root;

        while (current != null) {
            if (value == current.value) {
                return true;
            }

            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return false;
    }

    public boolean delete(int value) {
        if (!contains(value)) {
            return false;
        }

        root = deleteNode(root, value);
        size--;

        return true;
    }

    private TestNode deleteNode(TestNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = deleteNode(node.left, value);
        } else if (value > node.value) {
            node.right = deleteNode(node.right, value);
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
            TestNode successor = findMin(node.right);

            node.value = successor.value;

            node.right = deleteNode(
                node.right,
                successor.value
            );
        }

        return node;
    }

    private TestNode findMin(TestNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean isValid() {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValid(TestNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.value <= min || node.value >= max) {
            return false;
        }

        return isValid(node.left, min, node.value)
                && isValid(node.right, node.value, max);
    }

    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(TestNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.print(node.value + " ");
        inorder(node.right);
    }

    public void printStatus() {
        System.out.print("Inorder: ");
        inorder();

        System.out.println("Size: " + size());
        System.out.println("Empty: " + isEmpty());
        System.out.println("Valid: " + isValid());
    }
}

public class BstDeleteTestSuite {

    public static void main(String[] args) {

        // ====================================
        // Test 1：Empty Tree
        // ====================================
        System.out.println("===== Test 1: Empty Tree =====");

        TestBst tree1 = new TestBst();

        System.out.println(
            "Delete 10 = " + tree1.delete(10)
        );

        tree1.printStatus();

        System.out.println();


        // ====================================
        // Test 2：Missing Value
        // ====================================
        System.out.println("===== Test 2: Missing Value =====");

        TestBst tree2 = new TestBst();

        tree2.insert(50);
        tree2.insert(30);
        tree2.insert(70);

        System.out.println("Before:");
        tree2.printStatus();

        System.out.println(
            "Delete 99 = " + tree2.delete(99)
        );

        System.out.println("After:");
        tree2.printStatus();

        System.out.println();


        // ====================================
        // Test 3：Single Root
        // ====================================
        System.out.println("===== Test 3: Single Root =====");

        TestBst tree3 = new TestBst();

        tree3.insert(50);

        System.out.println("Before:");
        tree3.printStatus();

        System.out.println(
            "Delete 50 = " + tree3.delete(50)
        );

        System.out.println("After:");
        tree3.printStatus();

        System.out.println();


        // ====================================
        // Test 4：Root With One Child
        // ====================================
        System.out.println(
            "===== Test 4: Root With One Child ====="
        );

        TestBst tree4 = new TestBst();

        tree4.insert(50);
        tree4.insert(30);

        System.out.println("Before:");
        tree4.printStatus();

        System.out.println(
            "Delete 50 = " + tree4.delete(50)
        );

        System.out.println("After:");
        tree4.printStatus();

        System.out.println();


        // ====================================
        // Test 5：Root With Two Children
        // ====================================
        System.out.println(
            "===== Test 5: Root With Two Children ====="
        );

        TestBst tree5 = new TestBst();

        tree5.insert(50);
        tree5.insert(30);
        tree5.insert(70);
        tree5.insert(20);
        tree5.insert(40);
        tree5.insert(60);
        tree5.insert(80);

        System.out.println("Before:");
        tree5.printStatus();

        System.out.println(
            "Delete 50 = " + tree5.delete(50)
        );

        System.out.println("After:");
        tree5.printStatus();

        System.out.println();


        // ====================================
        // Test 6：Continuous Delete To Empty
        // ====================================
        System.out.println(
            "===== Test 6: Delete Until Empty ====="
        );

        TestBst tree6 = new TestBst();

        int[] values = {
            50, 30, 70, 20, 40, 60, 80
        };

        for (int value : values) {
            tree6.insert(value);
        }

        System.out.println("Original:");
        tree6.printStatus();

        int[] deleteOrder = {
            20, 30, 40, 60, 80, 70, 50
        };

        for (int value : deleteOrder) {

            System.out.println();
            System.out.println("Delete " + value);

            System.out.println(
                "Result = " + tree6.delete(value)
            );

            tree6.printStatus();
        }
    }
}