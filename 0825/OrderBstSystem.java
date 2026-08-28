class Order {
    String orderId;
    String customerName;
    double amount;

    Order(String orderId, String customerName, double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = Math.max(amount, 0);
    }

    @Override
    public String toString() {
        return orderId + " - " + customerName
                + " - amount: " + String.format("%.2f", amount);
    }
}

class OrderNode {
    Order order;
    OrderNode left;
    OrderNode right;

    OrderNode(Order order) {
        this.order = order;
    }
}

class OrderBst {
    private OrderNode root;
    private int size = 0;

    // 新增訂單
    public boolean add(Order order) {
        if (order == null || order.orderId == null) {
            return false;
        }

        if (root == null) {
            root = new OrderNode(order);
            size++;
            return true;
        }

        OrderNode current = root;

        while (true) {
            int cmp = order.orderId.compareTo(current.order.orderId);

            // 重複 orderId 不加入
            if (cmp == 0) {
                return false;
            }

            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new OrderNode(order);
                    size++;
                    return true;
                }

                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new OrderNode(order);
                    size++;
                    return true;
                }

                current = current.right;
            }
        }
    }

    // 查詢訂單
    public Order find(String orderId) {
        if (orderId == null) {
            return null;
        }

        OrderNode current = root;

        while (current != null) {
            int cmp = orderId.compareTo(current.order.orderId);

            if (cmp == 0) {
                return current.order;
            }

            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    // 修改金額
    public boolean updateAmount(String orderId, double newAmount) {
        if (newAmount < 0) {
            return false;
        }

        // 先依 orderId 找到 Order
        Order order = find(orderId);

        if (order == null) {
            return false;
        }

        order.amount = newAmount;
        return true;
    }

    // 取消訂單 = 從 BST 刪除
    public boolean cancel(String orderId) {
        if (find(orderId) == null) {
            return false;
        }

        root = delete(root, orderId);
        size--;

        return true;
    }

    private OrderNode delete(OrderNode node, String orderId) {
        if (node == null) {
            return null;
        }

        int cmp = orderId.compareTo(node.order.orderId);

        if (cmp < 0) {
            node.left = delete(node.left, orderId);
        } else if (cmp > 0) {
            node.right = delete(node.right, orderId);
        } else {

            // Case 1：leaf
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
            OrderNode successor = findMin(node.right);

            node.order = successor.order;

            node.right = delete(
                    node.right,
                    successor.order.orderId
            );
        }

        return node;
    }

    private OrderNode findMin(OrderNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    // 指定 orderId 範圍輸出
    // 包含 lowId 與 highId
    public void rangeReport(String lowId, String highId) {
        if (lowId == null || highId == null) {
            return;
        }

        // low > high 時交換
        if (lowId.compareTo(highId) > 0) {
            String temp = lowId;
            lowId = highId;
            highId = temp;
        }

        System.out.println(
                "===== Range " + lowId + " ~ " + highId + " ====="
        );

        rangeReport(root, lowId, highId);
    }

    private void rangeReport(
            OrderNode node,
            String lowId,
            String highId) {

        if (node == null) {
            return;
        }

        if (node.order.orderId.compareTo(lowId) > 0) {
            rangeReport(node.left, lowId, highId);
        }

        if (node.order.orderId.compareTo(lowId) >= 0
                && node.order.orderId.compareTo(highId) <= 0) {

            System.out.println(node.order);
        }

        if (node.order.orderId.compareTo(highId) < 0) {
            rangeReport(node.right, lowId, highId);
        }
    }

    // inorder report
    public void inorderReport() {
        System.out.println("===== All Orders =====");
        inorderReport(root);
    }

    private void inorderReport(OrderNode node) {
        if (node == null) {
            return;
        }

        inorderReport(node.left);
        System.out.println(node.order);
        inorderReport(node.right);
    }

    // summary
    public void summary() {
        double totalAmount = totalAmount(root);

        System.out.println("===== Summary =====");
        System.out.println("Order count = " + size);
        System.out.printf("Total amount = %.2f%n", totalAmount);

        if (size == 0) {
            System.out.println("Average amount = 0.00");
        } else {
            System.out.printf(
                    "Average amount = %.2f%n",
                    totalAmount / size
            );
        }
    }

    private double totalAmount(OrderNode node) {
        if (node == null) {
            return 0;
        }

        return node.order.amount
                + totalAmount(node.left)
                + totalAmount(node.right);
    }
}

public class OrderBstSystem {

    public static void main(String[] args) {

        OrderBst system = new OrderBst();

        // =========================
        // Add
        // =========================
        System.out.println("===== Add =====");

        System.out.println(
                "Add O003 = "
                        + system.add(new Order("O003", "Amy", 1200))
        );

        System.out.println(
                "Add O001 = "
                        + system.add(new Order("O001", "Ben", 500))
        );

        System.out.println(
                "Add O005 = "
                        + system.add(new Order("O005", "Cindy", 2500))
        );

        System.out.println(
                "Add O002 = "
                        + system.add(new Order("O002", "David", 800))
        );

        System.out.println(
                "Add O004 = "
                        + system.add(new Order("O004", "Eric", 1500))
        );

        // 測試重複 orderId
        System.out.println(
                "Duplicate O003 = "
                        + system.add(new Order("O003", "Frank", 9999))
        );

        System.out.println();


        // =========================
        // Find
        // =========================
        System.out.println("===== Find =====");

        Order found = system.find("O004");

        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Not found");
        }

        Order missing = system.find("O999");

        if (missing == null) {
            System.out.println("O999 not found");
        }

        System.out.println();


        // =========================
        // Update Amount
        // =========================
        System.out.println("===== Update Amount =====");

        System.out.println(
                "Update O002 = "
                        + system.updateAmount("O002", 1000)
        );

        System.out.println(
                "O002 = " + system.find("O002")
        );

        System.out.println(
                "Update O999 = "
                        + system.updateAmount("O999", 2000)
        );

        System.out.println();


        // =========================
        // Range Report
        // =========================
        system.rangeReport("O002", "O004");

        System.out.println();


        // =========================
        // Summary
        // =========================
        system.summary();

        System.out.println();


        // =========================
        // Cancel
        // =========================
        System.out.println("===== Cancel =====");

        System.out.println(
                "Cancel O003 = "
                        + system.cancel("O003")
        );

        System.out.println(
                "Cancel O999 = "
                        + system.cancel("O999")
        );

        System.out.println();


        // =========================
        // Final Report
        // =========================
        system.inorderReport();

        System.out.println();

        system.summary();
    }
}