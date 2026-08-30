import java.util.ArrayList;
import java.util.List;

public class OrderManagementBst {

    // =========================================
    // Order
    // =========================================
    static class Order {
        private int orderId;
        private String customer;
        private double amount;
        private String status;

        public Order(int orderId, String customer, double amount, String status) {

            if (amount < 0) {
                throw new IllegalArgumentException(
                        "Amount cannot be negative"
                );
            }

            this.orderId = orderId;
            this.customer = customer;
            this.amount = amount;
            this.status = status;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getCustomer() {
            return customer;
        }

        public double getAmount() {
            return amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Order{orderId=" + orderId
                    + ", customer='" + customer + '\''
                    + ", amount=" + amount
                    + ", status='" + status + '\''
                    + "}";
        }
    }

    // =========================================
    // Node
    // =========================================
    static class Node {
        Order order;
        Node left;
        Node right;

        Node(Order order) {
            this.order = order;
        }
    }

    // =========================================
    // Order BST
    // =========================================
    static class OrderBST {

        private Node root;
        private int size;

        // -----------------------------------------
        // Add
        // orderId 不可重複
        // -----------------------------------------
        public boolean add(Order order) {

            if (order == null) {
                return false;
            }

            if (order.getAmount() < 0) {
                return false;
            }

            if (root == null) {
                root = new Node(order);
                size++;
                return true;
            }

            Node current = root;

            while (true) {

                if (order.getOrderId()
                        < current.order.getOrderId()) {

                    if (current.left == null) {
                        current.left = new Node(order);
                        size++;
                        return true;
                    }

                    current = current.left;

                } else if (order.getOrderId()
                        > current.order.getOrderId()) {

                    if (current.right == null) {
                        current.right = new Node(order);
                        size++;
                        return true;
                    }

                    current = current.right;

                } else {

                    // duplicate orderId
                    return false;
                }
            }
        }

        // -----------------------------------------
        // Find
        // -----------------------------------------
        public Order find(int orderId) {

            Node current = root;

            while (current != null) {

                if (orderId < current.order.getOrderId()) {
                    current = current.left;

                } else if (orderId > current.order.getOrderId()) {
                    current = current.right;

                } else {
                    return current.order;
                }
            }

            return null;
        }

        // -----------------------------------------
        // Update Status
        // -----------------------------------------
        public boolean updateStatus(
                int orderId,
                String newStatus) {

            if (newStatus == null
                    || newStatus.trim().isEmpty()) {

                return false;
            }

            Order order = find(orderId);

            if (order == null) {
                return false;
            }

            order.setStatus(newStatus);

            return true;
        }

        // -----------------------------------------
        // Cancel
        // -----------------------------------------
        public boolean cancel(int orderId) {

            Order order = find(orderId);

            if (order == null) {
                return false;
            }

            // 已經取消
            if ("CANCELLED".equals(order.getStatus())) {
                return false;
            }

            order.setStatus("CANCELLED");

            return true;
        }

        // -----------------------------------------
        // Remove
        // 只有 CANCELLED 可以刪除
        // -----------------------------------------
        public boolean remove(int orderId) {

            Order order = find(orderId);

            // 不存在
            if (order == null) {
                return false;
            }

            // 不是 CANCELLED，不可刪除
            if (!"CANCELLED".equals(order.getStatus())) {
                return false;
            }

            root = removeRecursive(root, orderId);

            size--;

            return true;
        }

        private Node removeRecursive(
                Node node,
                int orderId) {

            if (node == null) {
                return null;
            }

            if (orderId < node.order.getOrderId()) {

                node.left =
                        removeRecursive(
                                node.left,
                                orderId
                        );

            } else if (orderId > node.order.getOrderId()) {

                node.right =
                        removeRecursive(
                                node.right,
                                orderId
                        );

            } else {

                // Case 1：Leaf
                if (node.left == null
                        && node.right == null) {

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

                // Case 3：兩個 child
                Node successor =
                        findMin(node.right);

                node.order = successor.order;

                node.right =
                        removeRecursive(
                                node.right,
                                successor.order.getOrderId()
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

        // -----------------------------------------
        // ID Range Report
        // -----------------------------------------
        public List<Order> idRangeReport(
                int low,
                int high) {

            List<Order> result =
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
                List<Order> result) {

            if (node == null) {
                return;
            }

            int id = node.order.getOrderId();

            // BST 剪枝
            if (id > low) {

                rangeRecursive(
                        node.left,
                        low,
                        high,
                        result
                );
            }

            if (id >= low && id <= high) {
                result.add(node.order);
            }

            if (id < high) {

                rangeRecursive(
                        node.right,
                        low,
                        high,
                        result
                );
            }
        }

        // -----------------------------------------
        // Total Amount
        // 全部目前存在 BST 中訂單的總金額
        // -----------------------------------------
        public double totalAmount() {
            return totalAmountRecursive(root);
        }

        private double totalAmountRecursive(Node node) {

            if (node == null) {
                return 0;
            }

            return node.order.getAmount()
                    + totalAmountRecursive(node.left)
                    + totalAmountRecursive(node.right);
        }

        // -----------------------------------------
        // Inorder Report
        // -----------------------------------------
        public void inorderReport() {

            System.out.println(
                    "=== Order Inorder Report ==="
            );

            if (root == null) {
                System.out.println("(empty)");
                return;
            }

            inorderRecursive(root);

            System.out.println(
                    "Total orders = " + size
            );

            System.out.println(
                    "Total amount = " + totalAmount()
            );
        }

        private void inorderRecursive(Node node) {

            if (node == null) {
                return;
            }

            inorderRecursive(node.left);

            System.out.println(node.order);

            inorderRecursive(node.right);
        }

        public int size() {
            return size;
        }
    }

    // =========================================
    // Main Test
    // =========================================
    public static void main(String[] args) {

        OrderBST tree = new OrderBST();

        // =====================================
        // Add
        // =====================================
        System.out.println("=== Add ===");

        System.out.println(
                "add 1003 = "
                        + tree.add(
                        new Order(
                                1003,
                                "Amy",
                                1500,
                                "NEW"
                        )
                )
        );

        System.out.println(
                "add 1001 = "
                        + tree.add(
                        new Order(
                                1001,
                                "Ben",
                                800,
                                "NEW"
                        )
                )
        );

        System.out.println(
                "add 1005 = "
                        + tree.add(
                        new Order(
                                1005,
                                "Cindy",
                                2300,
                                "PAID"
                        )
                )
        );

        System.out.println(
                "add 1002 = "
                        + tree.add(
                        new Order(
                                1002,
                                "David",
                                1200,
                                "PROCESSING"
                        )
                )
        );

        System.out.println(
                "add 1004 = "
                        + tree.add(
                        new Order(
                                1004,
                                "Eric",
                                600,
                                "NEW"
                        )
                )
        );

        System.out.println(
                "add 1006 = "
                        + tree.add(
                        new Order(
                                1006,
                                "Fiona",
                                3200,
                                "PAID"
                        )
                )
        );


        System.out.println();

        // =====================================
        // Duplicate
        // =====================================
        System.out.println(
                "=== Duplicate ==="
        );

        System.out.println(
                "add duplicate 1003 = "
                        + tree.add(
                        new Order(
                                1003,
                                "Other",
                                999,
                                "NEW"
                        )
                )
        );


        System.out.println();

        // =====================================
        // Find
        // =====================================
        System.out.println("=== Find ===");

        System.out.println(
                "find 1004 = "
                        + tree.find(1004)
        );

        System.out.println(
                "find 9999 = "
                        + tree.find(9999)
        );


        System.out.println();

        // =====================================
        // Update Status
        // =====================================
        System.out.println(
                "=== Update Status ==="
        );

        System.out.println(
                "update 1001 to PAID = "
                        + tree.updateStatus(
                        1001,
                        "PAID"
                )
        );

        System.out.println(
                "after update = "
                        + tree.find(1001)
        );

        System.out.println(
                "update missing = "
                        + tree.updateStatus(
                        9999,
                        "PAID"
                )
        );


        System.out.println();

        // =====================================
        // Remove 非 CANCELLED
        // =====================================
        System.out.println(
                "=== Remove Non-Cancelled ==="
        );

        System.out.println(
                "remove 1001 = "
                        + tree.remove(1001)
        );

        System.out.println(
                "1001 still exists = "
                        + (tree.find(1001) != null)
        );


        System.out.println();

        // =====================================
        // Cancel
        // =====================================
        System.out.println("=== Cancel ===");

        System.out.println(
                "cancel 1002 = "
                        + tree.cancel(1002)
        );

        System.out.println(
                "after cancel = "
                        + tree.find(1002)
        );

        System.out.println(
                "cancel 1002 again = "
                        + tree.cancel(1002)
        );


        System.out.println();

        // =====================================
        // CANCELLED 才能 Remove
        // =====================================
        System.out.println(
                "=== Remove Cancelled ==="
        );

        System.out.println(
                "remove 1002 = "
                        + tree.remove(1002)
        );

        System.out.println(
                "find 1002 = "
                        + tree.find(1002)
        );


        System.out.println();

        // =====================================
        // ID Range Report
        // =====================================
        System.out.println(
                "=== ID Range 1003 ~ 1005 ==="
        );

        List<Order> range =
                tree.idRangeReport(
                        1003,
                        1005
                );

        for (Order order : range) {
            System.out.println(order);
        }


        System.out.println();

        // low > high
        System.out.println(
                "=== Range low > high ==="
        );

        System.out.println(
                tree.idRangeReport(
                        1005,
                        1001
                )
        );


        System.out.println();

        // =====================================
        // Total Amount
        // =====================================
        System.out.println(
                "=== Total Amount ==="
        );

        System.out.println(
                "total amount = "
                        + tree.totalAmount()
        );


        System.out.println();

        // =====================================
        // Inorder Report
        // =====================================
        tree.inorderReport();


        System.out.println();

        // =====================================
        // Remove Missing
        // =====================================
        System.out.println(
                "=== Remove Missing ==="
        );

        System.out.println(
                "remove 9999 = "
                        + tree.remove(9999)
        );
    }
}