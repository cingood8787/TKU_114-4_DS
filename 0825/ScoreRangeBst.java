class ScoreRecord {
    int score;
    String studentId;
    String name;

    ScoreRecord(int score, String studentId, String name) {
        this.score = score;
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return studentId + " - " + name + " - score: " + score;
    }
}

class ScoreNode {
    ScoreRecord data;
    ScoreNode left;
    ScoreNode right;

    ScoreNode(ScoreRecord data) {
        this.data = data;
    }
}

class ScoreBst {
    private ScoreNode root;

    // 複合比較：
    // 先比 score
    // score 相同時，再比 studentId
    private int compare(ScoreRecord a, ScoreRecord b) {
        if (a.score < b.score) {
            return -1;
        }

        if (a.score > b.score) {
            return 1;
        }

        return a.studentId.compareTo(b.studentId);
    }

    public boolean insert(ScoreRecord data) {
        if (data == null || data.studentId == null) {
            return false;
        }

        if (root == null) {
            root = new ScoreNode(data);
            return true;
        }

        ScoreNode current = root;

        while (true) {
            int cmp = compare(data, current.data);

            // score 與 studentId 都相同
            if (cmp == 0) {
                return false;
            }

            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new ScoreNode(data);
                    return true;
                }

                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ScoreNode(data);
                    return true;
                }

                current = current.right;
            }
        }
    }

    // 印出全部資料
    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(ScoreNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.println(node.data);
        inorder(node.right);
    }

    // 分數範圍查詢，包含 low 與 high
    public void printRange(int low, int high) {
        if (low > high) {
            int temp = low;
            low = high;
            high = temp;
        }

        System.out.println(
            "===== Score Range " + low + " ~ " + high + " ====="
        );

        printRange(root, low, high);
    }

    private void printRange(ScoreNode node, int low, int high) {
        if (node == null) {
            return;
        }

        // 目前分數大於 low，
        // 左邊仍可能有符合範圍的資料
        if (node.data.score > low) {
            printRange(node.left, low, high);
        }

        // 包含端點
        if (node.data.score >= low &&
            node.data.score <= high) {

            System.out.println(node.data);
        }

        // 目前分數小於 high，
        // 右邊仍可能有符合範圍的資料
        if (node.data.score < high) {
            printRange(node.right, low, high);
        }
    }
}

public class ScoreRangeBst {

    public static void main(String[] args) {

        ScoreBst tree = new ScoreBst();

        tree.insert(
            new ScoreRecord(80, "S003", "Amy")
        );

        tree.insert(
            new ScoreRecord(90, "S001", "Ben")
        );

        tree.insert(
            new ScoreRecord(75, "S005", "Cindy")
        );

        tree.insert(
            new ScoreRecord(80, "S001", "David")
        );

        tree.insert(
            new ScoreRecord(85, "S004", "Eric")
        );

        tree.insert(
            new ScoreRecord(90, "S002", "Frank")
        );

        tree.insert(
            new ScoreRecord(80, "S006", "Grace")
        );

        tree.insert(
            new ScoreRecord(70, "S007", "Helen")
        );

        System.out.println("===== Inorder =====");
        tree.inorder();

        tree.printRange(80, 90);

        System.out.println();

        // 測試 low > high
        tree.printRange(90, 80);
    }
}