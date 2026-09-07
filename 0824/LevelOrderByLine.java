import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderByLine {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    // 使用 Queue 做 Level-order Traversal
    public static void levelOrderByLine(Node root) {

        // Empty Tree
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }

        Queue<Node> queue = new LinkedList<>();

        queue.offer(root);

        int level = 0;

        while (!queue.isEmpty()) {

            // 目前這一層的 node 數量
            int levelSize = queue.size();

            System.out.print(
                    "Level " + level
                            + " (" + levelSize
                            + " nodes): "
            );

            // 只處理目前這一層
            for (int i = 0; i < levelSize; i++) {

                Node current = queue.poll();

                System.out.print(current.value);

                if (i < levelSize - 1) {
                    System.out.print(" ");
                }

                // 下一層放進 Queue
                if (current.left != null) {
                    queue.offer(current.left);
                }

                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            // 每層結束後換行
            System.out.println();

            level++;
        }
    }

    public static void main(String[] args) {

        /*
                    10
                   /  \
                 20    30
                / \    / \
              40  50  60  70
             /
            80
        */

        Node root = new Node(10);

        root.left = new Node(20);
        root.right = new Node(30);

        root.left.left = new Node(40);
        root.left.right = new Node(50);

        root.right.left = new Node(60);
        root.right.right = new Node(70);

        root.left.left.left = new Node(80);

        System.out.println(
                "=== Level Order By Line ==="
        );

        levelOrderByLine(root);

        // 測試 Empty Tree
        System.out.println();
        System.out.println(
                "=== Empty Tree ==="
        );

        levelOrderByLine(null);

        // 測試 Single Node
        System.out.println();
        System.out.println(
                "=== Single Node Tree ==="
        );

        Node single = new Node(100);

        levelOrderByLine(single);
    }
}