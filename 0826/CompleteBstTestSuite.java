import java.util.ArrayList;
import java.util.List;

public class CompleteBstTestSuite {

    // =========================================
    // BST Node
    // =========================================
    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    // =========================================
    // BST
    // =========================================
    static class BST {

        private Node root;
        private int size;

        // -------------------------------------
        // add
        // -------------------------------------
        public boolean add(int value) {

            if (root == null) {
                root = new Node(value);
                size++;
                return true;
            }

            Node current = root;

            while (true) {

                if (value < current.value) {

                    if (current.left == null) {
                        current.left = new Node(value);
                        size++;
                        return true;
                    }

                    current = current.left;

                } else if (value > current.value) {

                    if (current.right == null) {
                        current.right = new Node(value);
                        size++;
                        return true;
                    }

                    current = current.right;

                } else {

                    // duplicate
                    return false;
                }
            }
        }

        // -------------------------------------
        // contains
        // -------------------------------------
        public boolean contains(int value) {

            Node current = root;

            while (current != null) {

                if (value < current.value) {
                    current = current.left;

                } else if (value > current.value) {
                    current = current.right;

                } else {
                    return true;
                }
            }

            return false;
        }

        // -------------------------------------
        // remove
        // -------------------------------------
        public boolean remove(int value) {

            if (!contains(value)) {
                return false;
            }

            root = removeRecursive(root, value);

            size--;

            return true;
        }

        private Node removeRecursive(
                Node node,
                int value) {

            if (node == null) {
                return null;
            }

            if (value < node.value) {

                node.left =
                        removeRecursive(
                                node.left,
                                value
                        );

            } else if (value > node.value) {

                node.right =
                        removeRecursive(
                                node.right,
                                value
                        );

            } else {

                // Case 1：Leaf
                if (node.left == null
                        && node.right == null) {

                    return null;
                }

                // Case 2：Only right child
                if (node.left == null) {
                    return node.right;
                }

                // Case 2：Only left child
                if (node.right == null) {
                    return node.left;
                }

                // Case 3：Two children
                Node successor =
                        findMin(node.right);

                node.value = successor.value;

                node.right =
                        removeRecursive(
                                node.right,
                                successor.value
                        );
            }

            return node;
        }

        private Node findMin(Node node) {

            Node current = node;

            while (current.left != null) {
                current = current.left;
            }

            return current;
        }

        // -------------------------------------
        // size
        // -------------------------------------
        public int size() {
            return size;
        }

        // -------------------------------------
        // height
        // empty = 0
        // leaf = 1
        // -------------------------------------
        public int height() {
            return heightRecursive(root);
        }

        private int heightRecursive(Node node) {

            if (node == null) {
                return 0;
            }

            return 1 + Math.max(
                    heightRecursive(node.left),
                    heightRecursive(node.right)
            );
        }

        // -------------------------------------
        // inorder
        // -------------------------------------
        public List<Integer> inorder() {

            List<Integer> result =
                    new ArrayList<>();

            inorderRecursive(root, result);

            return result;
        }

        private void inorderRecursive(
                Node node,
                List<Integer> result) {

            if (node == null) {
                return;
            }

            inorderRecursive(
                    node.left,
                    result
            );

            result.add(node.value);

            inorderRecursive(
                    node.right,
                    result
            );
        }

        // -------------------------------------
        // range query
        // -------------------------------------
        public List<Integer> range(
                int low,
                int high) {

            List<Integer> result =
                    new ArrayList<>();

            if (low > high) {
                return result;
            }

            rangeRecursive(
                    root,
                    low,
                    high,
                    result
            );

            return result;
        }

        private void rangeRecursive(
                Node node,
                int low,
                int high,
                List<Integer> result) {

            if (node == null) {
                return;
            }

            // BST 剪枝
            if (node.value > low) {

                rangeRecursive(
                        node.left,
                        low,
                        high,
                        result
                );
            }

            if (node.value >= low
                    && node.value <= high) {

                result.add(node.value);
            }

            if (node.value < high) {

                rangeRecursive(
                        node.right,
                        low,
                        high,
                        result
                );
            }
        }

        // -------------------------------------
        // BST invariant validation
        // -------------------------------------
        public boolean isValid() {

            return validate(
                    root,
                    Long.MIN_VALUE,
                    Long.MAX_VALUE
            );
        }

        private boolean validate(
                Node node,
                long min,
                long max) {

            if (node == null) {
                return true;
            }

            if (node.value <= min
                    || node.value >= max) {

                return false;
            }

            return validate(
                    node.left,
                    min,
                    node.value
            )
                    &&
                    validate(
                            node.right,
                            node.value,
                            max
                    );
        }
    }

    // =========================================
    // Test Suite
    // =========================================

    static int passCount = 0;
    static int failCount = 0;
    static int totalCount = 0;

    // 老師指定的 check
    public static void check(
            String description,
            boolean condition) {

        totalCount++;

        if (condition) {

            passCount++;

            System.out.println(
                    "[PASS] " + description
            );

        } else {

            failCount++;

            System.out.println(
                    "[FAIL] " + description
            );
        }
    }

    // =========================================
    // Main
    // =========================================
    public static void main(String[] args) {

        System.out.println(
                "=== Complete BST Test Suite ==="
        );

        System.out.println();


        // =====================================
        // 1. Empty Tree
        // =====================================

        BST emptyTree = new BST();

        check(
                "empty size = 0",
                emptyTree.size() == 0
        );

        check(
                "empty height = 0",
                emptyTree.height() == 0
        );

        check(
                "empty contains returns false",
                !emptyTree.contains(10)
        );

        check(
                "remove from empty returns false",
                !emptyTree.remove(10)
        );

        check(
                "empty inorder is empty",
                emptyTree.inorder().isEmpty()
        );

        check(
                "empty tree is valid",
                emptyTree.isValid()
        );


        // =====================================
        // 2. Add / Root
        // =====================================

        BST tree = new BST();

        check(
                "add root 50",
                tree.add(50)
        );

        check(
                "root exists",
                tree.contains(50)
        );

        check(
                "size after root = 1",
                tree.size() == 1
        );

        check(
                "root-only height = 1",
                tree.height() == 1
        );


        // =====================================
        // 3. Add More Nodes
        // =====================================

        check(
                "add 30",
                tree.add(30)
        );

        check(
                "add 70",
                tree.add(70)
        );

        check(
                "add 20",
                tree.add(20)
        );

        check(
                "add 40",
                tree.add(40)
        );

        check(
                "add 60",
                tree.add(60)
        );

        check(
                "add 80",
                tree.add(80)
        );


        // BST:
        //
        //          50
        //        /    \
        //      30      70
        //     /  \    /  \
        //   20   40  60   80


        // =====================================
        // 4. Duplicate
        // =====================================

        int sizeBeforeDuplicate =
                tree.size();

        check(
                "duplicate add returns false",
                !tree.add(30)
        );

        check(
                "duplicate does not change size",
                tree.size()
                        == sizeBeforeDuplicate
        );


        // =====================================
        // 5. Find / Missing
        // =====================================

        check(
                "contains existing 60",
                tree.contains(60)
        );

        check(
                "contains missing 999 = false",
                !tree.contains(999)
        );


        // =====================================
        // 6. Inorder
        // =====================================

        check(
                "inorder sorted",
                tree.inorder().toString()
                        .equals(
                                "[20, 30, 40, 50, 60, 70, 80]"
                        )
        );

        check(
                "BST valid after adds",
                tree.isValid()
        );


        // =====================================
        // 7. Range
        // =====================================

        check(
                "range 30~70",
                tree.range(30, 70)
                        .toString()
                        .equals(
                                "[30, 40, 50, 60, 70]"
                        )
        );

        check(
                "range one value",
                tree.range(40, 40)
                        .toString()
                        .equals("[40]")
        );

        check(
                "range empty",
                tree.range(90, 100)
                        .isEmpty()
        );

        check(
                "range low > high",
                tree.range(70, 30)
                        .isEmpty()
        );


        // =====================================
        // 8. Delete Leaf
        // 20 是 leaf
        // =====================================

        int beforeLeaf =
                tree.size();

        check(
                "remove leaf 20",
                tree.remove(20)
        );

        check(
                "leaf 20 no longer exists",
                !tree.contains(20)
        );

        check(
                "leaf delete size -1",
                tree.size()
                        == beforeLeaf - 1
        );

        check(
                "valid after leaf delete",
                tree.isValid()
        );


        // =====================================
        // 9. One Child
        // 先新增 65，使 60 只有右 child
        // =====================================

        check(
                "add 65",
                tree.add(65)
        );

        /*
              60
                \
                 65
        */

        int beforeOneChild =
                tree.size();

        check(
                "remove one-child node 60",
                tree.remove(60)
        );

        check(
                "60 removed",
                !tree.contains(60)
        );

        check(
                "child 65 preserved",
                tree.contains(65)
        );

        check(
                "one-child delete size -1",
                tree.size()
                        == beforeOneChild - 1
        );

        check(
                "valid after one-child delete",
                tree.isValid()
        );


        // =====================================
        // 10. Two Children
        // 70 有 65 與 80
        // =====================================

        int beforeTwoChildren =
                tree.size();

        check(
                "remove two-child node 70",
                tree.remove(70)
        );

        check(
                "70 removed",
                !tree.contains(70)
        );

        check(
                "65 preserved",
                tree.contains(65)
        );

        check(
                "80 preserved",
                tree.contains(80)
        );

        check(
                "two-child delete size -1",
                tree.size()
                        == beforeTwoChildren - 1
        );

        check(
                "valid after two-child delete",
                tree.isValid()
        );


        // =====================================
        // 11. Missing Remove
        // =====================================

        int beforeMissing =
                tree.size();

        check(
                "remove missing returns false",
                !tree.remove(999)
        );

        check(
                "missing remove size unchanged",
                tree.size()
                        == beforeMissing
        );


        // =====================================
        // 12. Delete Root
        // =====================================

        check(
                "root 50 currently exists",
                tree.contains(50)
        );

        check(
                "remove root 50",
                tree.remove(50)
        );

        check(
                "old root 50 removed",
                !tree.contains(50)
        );

        check(
                "tree valid after root delete",
                tree.isValid()
        );


        // =====================================
        // 13. Final Invariant
        // =====================================

        List<Integer> finalInorder =
                tree.inorder();

        boolean sorted = true;

        for (int i = 1;
             i < finalInorder.size();
             i++) {

            if (finalInorder.get(i - 1)
                    >= finalInorder.get(i)) {

                sorted = false;
                break;
            }
        }

        check(
                "final inorder strictly sorted",
                sorted
        );

        check(
                "final size equals inorder count",
                tree.size()
                        == finalInorder.size()
        );

        check(
                "final BST invariant valid",
                tree.isValid()
        );


        // =====================================
        // Summary
        // =====================================

        System.out.println();
        System.out.println(
                "=== Test Summary ==="
        );

        System.out.println(
                "Total = " + totalCount
        );

        System.out.println(
                "PASS  = " + passCount
        );

        System.out.println(
                "FAIL  = " + failCount
        );

        if (failCount == 0) {
            System.out.println(
                    "RESULT: ALL TESTS PASSED"
            );
        } else {
            System.out.println(
                    "RESULT: SOME TESTS FAILED"
            );
        }
    }
}