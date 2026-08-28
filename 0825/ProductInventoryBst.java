class Product {
    String id;
    String name;
    int stock;

    Product(String id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = Math.max(stock, 0);
    }

    @Override
    public String toString() {
        return id + " - " + name + " - stock: " + stock;
    }
}

class ProductNode {
    Product product;
    ProductNode left;
    ProductNode right;

    ProductNode(Product product) {
        this.product = product;
    }
}

class ProductBst {
    private ProductNode root;

    // 新增商品
    public boolean insert(Product product) {
        if (product == null || product.id == null) {
            return false;
        }

        if (root == null) {
            root = new ProductNode(product);
            return true;
        }

        ProductNode current = root;

        while (true) {
            int cmp = product.id.compareTo(current.product.id);

            // 重複 id 不加入
            if (cmp == 0) {
                return false;
            }

            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new ProductNode(product);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ProductNode(product);
                    return true;
                }
                current = current.right;
            }
        }
    }

    // 依 id 查詢商品
    public Product search(String id) {
        if (id == null) {
            return null;
        }

        ProductNode current = root;

        while (current != null) {
            int cmp = id.compareTo(current.product.id);

            if (cmp == 0) {
                return current.product;
            }

            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    // 補貨
    public boolean restock(String id, int amount) {
        if (amount <= 0) {
            return false;
        }

        // 先依 id 找 object
        Product product = search(id);

        if (product == null) {
            return false;
        }

        product.stock += amount;
        return true;
    }

    // 扣庫存
    public boolean reduceStock(String id, int amount) {
        if (amount <= 0) {
            return false;
        }

        // 先依 id 找 object
        Product product = search(id);

        if (product == null) {
            return false;
        }

        if (product.stock < amount) {
            return false;
        }

        product.stock -= amount;
        return true;
    }

    // 刪除商品
    public boolean delete(String id) {
        // 先確認 id 是否存在
        Product product = search(id);

        if (product == null) {
            return false;
        }

        root = delete(root, id);
        return true;
    }

    private ProductNode delete(ProductNode node, String id) {
        if (node == null) {
            return null;
        }

        int cmp = id.compareTo(node.product.id);

        if (cmp < 0) {
            node.left = delete(node.left, id);
        } else if (cmp > 0) {
            node.right = delete(node.right, id);
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
            ProductNode successor = findMin(node.right);

            node.product = successor.product;

            node.right = delete(
                node.right,
                successor.product.id
            );
        }

        return node;
    }

    private ProductNode findMin(ProductNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    // inorder report
    public void inorderReport() {
        System.out.println("===== Inventory Report =====");
        inorderReport(root);
    }

    private void inorderReport(ProductNode node) {
        if (node == null) {
            return;
        }

        inorderReport(node.left);
        System.out.println(node.product);
        inorderReport(node.right);
    }
}

public class ProductInventoryBst {

    public static void main(String[] args) {

        ProductBst inventory = new ProductBst();

        // 新增
        System.out.println("===== Insert =====");

        System.out.println(
            "Insert P003 = "
            + inventory.insert(new Product("P003", "Keyboard", 10))
        );

        System.out.println(
            "Insert P001 = "
            + inventory.insert(new Product("P001", "Mouse", 20))
        );

        System.out.println(
            "Insert P005 = "
            + inventory.insert(new Product("P005", "Monitor", 5))
        );

        System.out.println(
            "Insert P002 = "
            + inventory.insert(new Product("P002", "USB Cable", 30))
        );

        System.out.println(
            "Insert P004 = "
            + inventory.insert(new Product("P004", "Headset", 8))
        );

        // 重複 id
        System.out.println(
            "Duplicate P003 = "
            + inventory.insert(new Product("P003", "Other", 99))
        );

        System.out.println();

        // 查詢
        System.out.println("===== Search =====");

        Product found = inventory.search("P004");

        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Not found");
        }

        System.out.println();

        // 補貨
        System.out.println("===== Restock =====");

        System.out.println(
            "Restock P001 + 10 = "
            + inventory.restock("P001", 10)
        );

        System.out.println(
            "P001 = " + inventory.search("P001")
        );

        System.out.println();

        // 扣庫存
        System.out.println("===== Reduce Stock =====");

        System.out.println(
            "Reduce P003 by 3 = "
            + inventory.reduceStock("P003", 3)
        );

        System.out.println(
            "P003 = " + inventory.search("P003")
        );

        // 庫存不足
        System.out.println(
            "Reduce P005 by 100 = "
            + inventory.reduceStock("P005", 100)
        );

        System.out.println();

        // 刪除
        System.out.println("===== Delete =====");

        System.out.println(
            "Delete P003 = "
            + inventory.delete("P003")
        );

        System.out.println(
            "Delete P999 = "
            + inventory.delete("P999")
        );

        System.out.println();

        // inorder report
        inventory.inorderReport();
    }
}