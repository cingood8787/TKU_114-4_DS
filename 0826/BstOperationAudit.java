public class BstOperationAudit {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    static class BST {
        private Node root;
        private int size;

        // =========================
        // Add
        // =========================
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

        // =========================
        // Remove
        // =========================
        public boolean remove(int value) {
            if (!contains(value)) {
                return false;
            }

            root = removeRecursive(root, value);
            size--;
            return true;
        }

        private Node removeRecursive(Node node, int value) {
            if (node == null) {
                return null;
            }

            if (value < node.value) {
                node.left = removeRecursive(node.left, value);

            } else if (value > node.value) {
                node.right = removeRecursive(node.right, value);

            } else {

                // Case 1：沒有子節點（leaf）
                if (node.left == null && node.right == null) {
                    return null;
                }

                // Case 2：只有右子節點
                if (node.left == null) {
                    return node.right;
                }

                // Case 2：只有左子節點
                if (node.right == null) {
                    return node.left;
                }

                // Case 3：有兩個子節點
                // 找右子樹中最小的值
                Node successor = findMin(node.right);

                node.value = successor.value;

                node.right =
                        removeRecursive(node.right, successor.value);
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

        // =========================
        // Contains
        // =========================
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

        // =========================
        // Inorder
        // =========================
        public String inorder() {
            StringBuilder sb = new StringBuilder();

            inorderRecursive(root, sb);

            return sb.toString().trim();
        }

        private void inorderRecursive(Node node, StringBuilder sb) {
            if (node == null) {
                return;
            }

            inorderRecursive(node.left, sb);

            sb.append(node.value).append(" ");

            inorderRecursive(node.right, sb);
        }

        // =========================
        // Size
        // =========================
        public int size() {
            return size;
        }

        // =========================
        // Height
        // empty tree = 0
        // leaf = 1
        // =========================
        public int height() {
            return heightRecursive(root);
        }

        private int heightRecursive(Node node) {
            if (node == null) {
                return 0;
            }

            int leftHeight = heightRecursive(node.left);
            int rightHeight = heightRecursive(node.right);

            return 1 + Math.max(leftHeight, rightHeight);
        }

        // =========================
        // Validate BST
        // =========================
        public boolean isValid() {
            return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        private boolean validate(Node node, long min, long max) {
            if (node == null) {
                return true;
            }

            if (node.value <= min || node.value >= max) {
                return false;
            }

            return validate(node.left, min, node.value)
                    && validate(node.right, node.value, max);
        }
    }

    // =========================
    // Audit
    // =========================
    public static void audit(
            BST tree,
            String operation,
            boolean result) {

        System.out.println(
                "operation=" + operation
                        + ", result=" + result
                        + ", inorder=[" + tree.inorder() + "]"
                        + ", size=" + tree.size()
                        + ", height=" + tree.height()
                        + ", valid=" + tree.isValid()
        );
    }

    public static void main(String[] args) {

        BST tree = new BST();

        // 建立 BST
        boolean result;

        result = tree.add(50);
        audit(tree, "add 50", result);

        result = tree.add(30);
        audit(tree, "add 30", result);

        result = tree.add(70);
        audit(tree, "add 70", result);

        result = tree.add(20);
        audit(tree, "add 20", result);

        result = tree.add(40);
        audit(tree, "add 40", result);

        result = tree.add(60);
        audit(tree, "add 60", result);

        result = tree.add(80);
        audit(tree, "add 80", result);

        result = tree.add(65);
        audit(tree, "add 65", result);


        // =========================
        // Duplicate
        // =========================
        result = tree.add(30);
        audit(tree, "add 30 (duplicate)", result);


        // =========================
        // Missing
        // =========================
        result = tree.remove(999);
        audit(tree, "remove 999 (missing)", result);


        // =========================
        // Delete Case 1
        // Leaf：20 沒有子節點
        // =========================
        result = tree.remove(20);
        audit(tree, "remove 20 (leaf)", result);


        // =========================
        // Delete Case 2
        // One child：
        // 60 只有右子節點 65
        // =========================
        result = tree.remove(60);
        audit(tree, "remove 60 (one child)", result);


        // =========================
        // Delete Case 3
        // Two children：
        // 70 有左右兩個子節點
        // =========================
        result = tree.remove(70);
        audit(tree, "remove 70 (two children)", result);
    }
}