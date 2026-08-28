class CountNode {
    int key;
    int count;
    CountNode left;
    CountNode right;

    CountNode(int key) {
        this.key = key;
        this.count = 1;
    }
}

class CountBst {
    private CountNode root;

    public void add(int key) {
        if (root == null) {
            root = new CountNode(key);
            return;
        }

        CountNode current = root;

        while (true) {

            // 相同 key，不建立新 Node
            // 只把 count + 1
            if (key == current.key) {
                current.count++;
                return;
            }

            if (key < current.key) {
                if (current.left == null) {
                    current.left = new CountNode(key);
                    return;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new CountNode(key);
                    return;
                }
                current = current.right;
            }
        }
    }

    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(CountNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);

        // 題目要求格式：key(count)
        System.out.print(node.key + "(" + node.count + ") ");

        inorder(node.right);
    }
}

public class BstDuplicateCounter {

    public static void main(String[] args) {

        CountBst tree = new CountBst();

        int[] values = {
            50, 30, 70, 30, 50, 50, 20, 70, 40
        };

        for (int value : values) {
            tree.add(value);
        }

        System.out.println("Inorder:");
        tree.inorder();
    }
}