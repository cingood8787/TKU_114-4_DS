import java.util.ArrayList;
import java.util.List;

public class BstRangeStatistics {

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

        // =========================
        // Add
        // =========================
        public boolean add(int value) {
            if (root == null) {
                root = new Node(value);
                return true;
            }

            Node current = root;

            while (true) {
                if (value < current.value) {
                    if (current.left == null) {
                        current.left = new Node(value);
                        return true;
                    }
                    current = current.left;

                } else if (value > current.value) {
                    if (current.right == null) {
                        current.right = new Node(value);
                        return true;
                    }
                    current = current.right;

                } else {
                    // duplicate 不加入
                    return false;
                }
            }
        }

        // =========================
        // valuesBetween
        // =========================
        public List<Integer> valuesBetween(int low, int high) {
            List<Integer> result = new ArrayList<>();

            if (low > high) {
                return result;
            }

            valuesBetweenRecursive(root, low, high, result);

            return result;
        }

        private void valuesBetweenRecursive(
                Node node,
                int low,
                int high,
                List<Integer> result) {

            if (node == null) {
                return;
            }

            // 如果目前值比 low 大，
            // 左子樹才有可能還有符合範圍的值
            if (node.value > low) {
                valuesBetweenRecursive(
                        node.left,
                        low,
                        high,
                        result
                );
            }

            // 目前節點在範圍內
            if (node.value >= low && node.value <= high) {
                result.add(node.value);
            }

            // 如果目前值比 high 小，
            // 右子樹才有可能還有符合範圍的值
            if (node.value < high) {
                valuesBetweenRecursive(
                        node.right,
                        low,
                        high,
                        result
                );
            }
        }

        // =========================
        // countBetween
        // =========================
        public int countBetween(int low, int high) {
            if (low > high) {
                return 0;
            }

            return countBetweenRecursive(root, low, high);
        }

        private int countBetweenRecursive(
                Node node,
                int low,
                int high) {

            if (node == null) {
                return 0;
            }

            // 節點太小
            // 左邊一定更小，所以直接往右
            if (node.value < low) {
                return countBetweenRecursive(
                        node.right,
                        low,
                        high
                );
            }

            // 節點太大
            // 右邊一定更大，所以直接往左
            if (node.value > high) {
                return countBetweenRecursive(
                        node.left,
                        low,
                        high
                );
            }

            // 節點在範圍內
            return 1
                    + countBetweenRecursive(
                            node.left,
                            low,
                            high
                    )
                    + countBetweenRecursive(
                            node.right,
                            low,
                            high
                    );
        }

        // =========================
        // sumBetween
        // =========================
        public int sumBetween(int low, int high) {
            if (low > high) {
                return 0;
            }

            return sumBetweenRecursive(root, low, high);
        }

        private int sumBetweenRecursive(
                Node node,
                int low,
                int high) {

            if (node == null) {
                return 0;
            }

            // 節點太小
            // 左邊不用找
            if (node.value < low) {
                return sumBetweenRecursive(
                        node.right,
                        low,
                        high
                );
            }

            // 節點太大
            // 右邊不用找
            if (node.value > high) {
                return sumBetweenRecursive(
                        node.left,
                        low,
                        high
                );
            }

            // 節點在範圍內
            return node.value
                    + sumBetweenRecursive(
                            node.left,
                            low,
                            high
                    )
                    + sumBetweenRecursive(
                            node.right,
                            low,
                            high
                    );
        }
    }

    public static void main(String[] args) {

        BST tree = new BST();

        // 建立 BST
        tree.add(50);
        tree.add(30);
        tree.add(70);
        tree.add(20);
        tree.add(40);
        tree.add(60);
        tree.add(80);
        tree.add(35);
        tree.add(65);

        // =========================
        // 一般範圍
        // =========================
        System.out.println("=== Range 30 ~ 65 ===");

        System.out.println(
                "valuesBetween = "
                        + tree.valuesBetween(30, 65)
        );

        System.out.println(
                "countBetween = "
                        + tree.countBetween(30, 65)
        );

        System.out.println(
                "sumBetween = "
                        + tree.sumBetween(30, 65)
        );


        System.out.println();

        // =========================
        // 空範圍
        // 範圍內沒有任何資料
        // =========================
        System.out.println("=== Empty Range 90 ~ 100 ===");

        System.out.println(
                "valuesBetween = "
                        + tree.valuesBetween(90, 100)
        );

        System.out.println(
                "countBetween = "
                        + tree.countBetween(90, 100)
        );

        System.out.println(
                "sumBetween = "
                        + tree.sumBetween(90, 100)
        );


        System.out.println();

        // =========================
        // low > high
        // =========================
        System.out.println("=== low > high : 70 ~ 30 ===");

        System.out.println(
                "valuesBetween = "
                        + tree.valuesBetween(70, 30)
        );

        System.out.println(
                "countBetween = "
                        + tree.countBetween(70, 30)
        );

        System.out.println(
                "sumBetween = "
                        + tree.sumBetween(70, 30)
        );
    }
}