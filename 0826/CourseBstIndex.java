import java.util.ArrayList;
import java.util.List;

public class CourseBstIndex {

    // =========================================
    // Course
    // =========================================
    static class Course {
        private String courseCode;
        private String courseName;
        private int credit;

        public Course(String courseCode, String courseName, int credit) {
            if (courseCode == null || courseCode.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Course code cannot be blank"
                );
            }

            if (credit < 1 || credit > 6) {
                throw new IllegalArgumentException(
                        "Credit must be between 1 and 6"
                );
            }

            this.courseCode = courseCode;
            this.courseName = courseName;
            this.credit = credit;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            if (credit < 1 || credit > 6) {
                throw new IllegalArgumentException(
                        "Credit must be between 1 and 6"
                );
            }

            this.credit = credit;
        }

        @Override
        public String toString() {
            return "Course{code='" + courseCode
                    + "', name='" + courseName
                    + "', credit=" + credit
                    + "}";
        }
    }

    // =========================================
    // Node
    // =========================================
    static class Node {
        Course course;
        Node left;
        Node right;

        Node(Course course) {
            this.course = course;
        }
    }

    // =========================================
    // Course BST
    // =========================================
    static class CourseBST {

        private Node root;
        private int size;

        // -----------------------------------------
        // Add
        // duplicate code 不得加入
        // -----------------------------------------
        public boolean add(Course course) {

            if (course == null) {
                return false;
            }

            if (root == null) {
                root = new Node(course);
                size++;
                return true;
            }

            Node current = root;

            while (true) {

                int compare =
                        course.getCourseCode()
                                .compareTo(
                                        current.course.getCourseCode()
                                );

                if (compare < 0) {

                    if (current.left == null) {
                        current.left = new Node(course);
                        size++;
                        return true;
                    }

                    current = current.left;

                } else if (compare > 0) {

                    if (current.right == null) {
                        current.right = new Node(course);
                        size++;
                        return true;
                    }

                    current = current.right;

                } else {

                    // duplicate code
                    return false;
                }
            }
        }

        // -----------------------------------------
        // Find
        // -----------------------------------------
        public Course find(String courseCode) {

            if (courseCode == null) {
                return null;
            }

            Node current = root;

            while (current != null) {

                int compare =
                        courseCode.compareTo(
                                current.course.getCourseCode()
                        );

                if (compare < 0) {
                    current = current.left;

                } else if (compare > 0) {
                    current = current.right;

                } else {
                    return current.course;
                }
            }

            return null;
        }

        // -----------------------------------------
        // Update Credit
        // credit 必須 1 ~ 6
        // -----------------------------------------
        public boolean updateCredit(
                String courseCode,
                int newCredit) {

            if (newCredit < 1 || newCredit > 6) {
                return false;
            }

            Course course = find(courseCode);

            if (course == null) {
                return false;
            }

            course.setCredit(newCredit);

            return true;
        }

        // -----------------------------------------
        // Remove
        // -----------------------------------------
        public boolean remove(String courseCode) {

            if (find(courseCode) == null) {
                return false;
            }

            root = removeRecursive(root, courseCode);
            size--;

            return true;
        }

        private Node removeRecursive(
                Node node,
                String courseCode) {

            if (node == null) {
                return null;
            }

            int compare =
                    courseCode.compareTo(
                            node.course.getCourseCode()
                    );

            if (compare < 0) {

                node.left =
                        removeRecursive(
                                node.left,
                                courseCode
                        );

            } else if (compare > 0) {

                node.right =
                        removeRecursive(
                                node.right,
                                courseCode
                        );

            } else {

                // Case 1：沒有 child
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

                node.course = successor.course;

                node.right =
                        removeRecursive(
                                node.right,
                                successor.course.getCourseCode()
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
        // Code Range Query
        // 使用 BST 剪枝
        // -----------------------------------------
        public List<Course> rangeQuery(
                String low,
                String high) {

            List<Course> result =
                    new ArrayList<>();

            if (low == null || high == null) {
                return result;
            }

            if (low.compareTo(high) > 0) {
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
                String low,
                String high,
                List<Course> result) {

            if (node == null) {
                return;
            }

            String code =
                    node.course.getCourseCode();

            // code > low 時
            // 左邊才可能還有符合資料
            if (code.compareTo(low) > 0) {

                rangeRecursive(
                        node.left,
                        low,
                        high,
                        result
                );
            }

            // 目前節點在範圍內
            if (code.compareTo(low) >= 0
                    && code.compareTo(high) <= 0) {

                result.add(node.course);
            }

            // code < high 時
            // 右邊才可能還有符合資料
            if (code.compareTo(high) < 0) {

                rangeRecursive(
                        node.right,
                        low,
                        high,
                        result
                );
            }
        }

        // -----------------------------------------
        // Sorted Report
        // inorder = courseCode 排序
        // -----------------------------------------
        public void sortedReport() {

            System.out.println(
                    "=== Course Sorted Report ==="
            );

            if (root == null) {
                System.out.println("(empty)");
                return;
            }

            inorderRecursive(root);

            System.out.println(
                    "Total courses = " + size
            );
        }

        private void inorderRecursive(Node node) {

            if (node == null) {
                return;
            }

            inorderRecursive(node.left);

            System.out.println(node.course);

            inorderRecursive(node.right);
        }
    }

    // =========================================
    // Main Test
    // =========================================
    public static void main(String[] args) {

        CourseBST tree = new CourseBST();

        // =====================================
        // Add
        // =====================================
        System.out.println("=== Add ===");

        System.out.println(
                tree.add(
                        new Course(
                                "CS201",
                                "Data Structures",
                                3
                        )
                )
        );

        System.out.println(
                tree.add(
                        new Course(
                                "CS101",
                                "Programming",
                                3
                        )
                )
        );

        System.out.println(
                tree.add(
                        new Course(
                                "CS301",
                                "Algorithms",
                                3
                        )
                )
        );

        System.out.println(
                tree.add(
                        new Course(
                                "CS150",
                                "Java Programming",
                                2
                        )
                )
        );

        System.out.println(
                tree.add(
                        new Course(
                                "CS250",
                                "Database Systems",
                                3
                        )
                )
        );

        System.out.println(
                tree.add(
                        new Course(
                                "CS350",
                                "Operating Systems",
                                4
                        )
                )
        );


        System.out.println();

        // =====================================
        // Duplicate Code
        // =====================================
        System.out.println(
                "=== Duplicate Code ==="
        );

        System.out.println(
                "add duplicate CS201 = "
                        + tree.add(
                        new Course(
                                "CS201",
                                "Duplicate Course",
                                3
                        )
                )
        );


        System.out.println();

        // =====================================
        // Find
        // =====================================
        System.out.println("=== Find ===");

        System.out.println(
                "find CS250 = "
                        + tree.find("CS250")
        );

        System.out.println(
                "find CS999 = "
                        + tree.find("CS999")
        );


        System.out.println();

        // =====================================
        // Update Credit
        // =====================================
        System.out.println(
                "=== Update Credit ==="
        );

        System.out.println(
                "update CS150 to 4 = "
                        + tree.updateCredit(
                        "CS150",
                        4
                )
        );

        System.out.println(
                "after update = "
                        + tree.find("CS150")
        );

        // credit 太小
        System.out.println(
                "update credit to 0 = "
                        + tree.updateCredit(
                        "CS150",
                        0
                )
        );

        // credit 太大
        System.out.println(
                "update credit to 7 = "
                        + tree.updateCredit(
                        "CS150",
                        7
                )
        );

        // 不存在的課程
        System.out.println(
                "update missing = "
                        + tree.updateCredit(
                        "CS999",
                        3
                )
        );


        System.out.println();

        // =====================================
        // Code Range Query
        // =====================================
        System.out.println(
                "=== Range CS150 ~ CS301 ==="
        );

        List<Course> result =
                tree.rangeQuery(
                        "CS150",
                        "CS301"
                );

        for (Course course : result) {
            System.out.println(course);
        }


        System.out.println();

        // low > high
        System.out.println(
                "=== Range low > high ==="
        );

        System.out.println(
                tree.rangeQuery(
                        "CS350",
                        "CS100"
                )
        );


        System.out.println();

        // =====================================
        // Sorted Report
        // =====================================
        tree.sortedReport();


        System.out.println();

        // =====================================
        // Remove
        // =====================================
        System.out.println("=== Remove ===");

        System.out.println(
                "remove CS101 = "
                        + tree.remove("CS101")
        );

        System.out.println(
                "remove CS301 = "
                        + tree.remove("CS301")
        );

        System.out.println(
                "remove missing CS999 = "
                        + tree.remove("CS999")
        );


        System.out.println();

        // 刪除後報表
        tree.sortedReport();
    }
}