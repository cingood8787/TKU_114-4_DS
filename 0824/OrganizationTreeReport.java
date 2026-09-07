import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrganizationTreeReport {

    static class OrgNode {
        String name;
        OrgNode left;
        OrgNode right;

        OrgNode(String name) {
            this.name = name;
        }

        OrgNode(String name, OrgNode left, OrgNode right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
    }

    private final OrgNode root;

    public OrganizationTreeReport(OrgNode root) {
        this.root = root;
    }

    // =========================
    // findParent
    // 找不到回傳 null
    // root 本身沒有 parent
    // =========================
    public String findParent(String target) {

        if (target == null || root == null) {
            return null;
        }

        OrgNode parent = findParentHelper(
                root,
                target
        );

        return parent == null
                ? null
                : parent.name;
    }

    private OrgNode findParentHelper(
            OrgNode node,
            String target) {

        if (node == null) {
            return null;
        }

        if (node.left != null
                && node.left.name.equals(target)) {
            return node;
        }

        if (node.right != null
                && node.right.name.equals(target)) {
            return node;
        }

        OrgNode leftResult =
                findParentHelper(
                        node.left,
                        target
                );

        if (leftResult != null) {
            return leftResult;
        }

        return findParentHelper(
                node.right,
                target
        );
    }

    // =========================
    // findDepth
    // root depth = 0
    // 找不到回傳 -1
    // =========================
    public int findDepth(String target) {

        if (target == null) {
            return -1;
        }

        return findDepthHelper(
                root,
                target,
                0
        );
    }

    private int findDepthHelper(
            OrgNode node,
            String target,
            int depth) {

        if (node == null) {
            return -1;
        }

        if (node.name.equals(target)) {
            return depth;
        }

        int left =
                findDepthHelper(
                        node.left,
                        target,
                        depth + 1
                );

        if (left != -1) {
            return left;
        }

        return findDepthHelper(
                node.right,
                target,
                depth + 1
        );
    }

    // =========================
    // pathFromRoot
    // 找不到回傳 empty list
    // =========================
    public List<String> pathFromRoot(
            String target) {

        List<String> path =
                new ArrayList<>();

        if (target == null) {
            return path;
        }

        boolean found =
                buildPath(
                        root,
                        target,
                        path
                );

        if (!found) {
            path.clear();
        }

        return path;
    }

    private boolean buildPath(
            OrgNode node,
            String target,
            List<String> path) {

        if (node == null) {
            return false;
        }

        path.add(node.name);

        if (node.name.equals(target)) {
            return true;
        }

        if (buildPath(
                node.left,
                target,
                path)) {
            return true;
        }

        if (buildPath(
                node.right,
                target,
                path)) {
            return true;
        }

        path.remove(path.size() - 1);

        return false;
    }

    // =========================
    // printByLevel
    // 使用 Queue 做 BFS
    // =========================
    public void printByLevel() {

        if (root == null) {
            System.out.println("Empty organization");
            return;
        }

        Queue<OrgNode> queue =
                new LinkedList<>();

        queue.offer(root);

        int level = 0;

        while (!queue.isEmpty()) {

            int levelSize =
                    queue.size();

            System.out.print(
                    "Level "
                            + level
                            + ": "
            );

            for (int i = 0; i < levelSize; i++) {

                OrgNode current =
                        queue.poll();

                System.out.print(
                        current.name
                );

                if (i < levelSize - 1) {
                    System.out.print(" | ");
                }

                if (current.left != null) {
                    queue.offer(current.left);
                }

                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            System.out.println();
            level++;
        }
    }

    // =========================
    // report
    // =========================
    public void report(String target) {

        System.out.println(
                "=== Search: "
                        + target
                        + " ==="
        );

        String parent =
                findParent(target);

        System.out.println(
                "Parent = "
                        + (parent == null
                        ? "None"
                        : parent)
        );

        System.out.println(
                "Depth = "
                        + findDepth(target)
        );

        System.out.println(
                "Path = "
                        + pathFromRoot(target)
        );

        System.out.println();
    }

    public static void main(String[] args) {

        /*
                         Company
                        /       \
                      IT         Sales
                    /   \       /    \
                  Dev    QA   Domestic Overseas
                 /  \
              Frontend Backend
        */

        OrgNode root =
                new OrgNode(
                        "Company",

                        new OrgNode(
                                "IT",

                                new OrgNode(
                                        "Dev",
                                        new OrgNode(
                                                "Frontend"
                                        ),
                                        new OrgNode(
                                                "Backend"
                                        )
                                ),

                                new OrgNode(
                                        "QA"
                                )
                        ),

                        new OrgNode(
                                "Sales",

                                new OrgNode(
                                        "Domestic"
                                ),

                                new OrgNode(
                                        "Overseas"
                                )
                        )
                );

        OrganizationTreeReport org =
                new OrganizationTreeReport(
                        root
                );

        // Level-order
        System.out.println(
                "=== Organization By Level ==="
        );

        org.printByLevel();

        System.out.println();

        // 測試存在的單位
        org.report("Backend");
        org.report("Sales");

        // root 測試
        org.report("Company");

        // 找不到的單位
        org.report("HR");


        // Empty Tree
        System.out.println(
                "=== Empty Tree ==="
        );

        OrganizationTreeReport empty =
                new OrganizationTreeReport(
                        null
                );

        empty.printByLevel();

        System.out.println(
                "Parent = "
                        + empty.findParent("IT")
        );

        System.out.println(
                "Depth = "
                        + empty.findDepth("IT")
        );

        System.out.println(
                "Path = "
                        + empty.pathFromRoot("IT")
        );
    }
}