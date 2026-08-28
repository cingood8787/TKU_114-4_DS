class Student {
    String studentId;
    String name;

    Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return studentId + " - " + name;
    }
}

class StudentNode {
    Student student;
    StudentNode left;
    StudentNode right;

    StudentNode(Student student) {
        this.student = student;
    }
}

class StudentBst {
    private StudentNode root;

    // 新增學生
    public boolean insert(Student student) {
        if (student == null || student.studentId == null) {
            return false;
        }

        if (root == null) {
            root = new StudentNode(student);
            return true;
        }

        StudentNode current = root;

        while (true) {
            int cmp = student.studentId.compareTo(current.student.studentId);

            // 重複 id 不得加入
            if (cmp == 0) {
                return false;
            }

            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new StudentNode(student);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new StudentNode(student);
                    return true;
                }
                current = current.right;
            }
        }
    }

    // 搜尋學生
    public Student search(String studentId) {
        StudentNode current = root;

        while (current != null) {
            int cmp = studentId.compareTo(current.student.studentId);

            if (cmp == 0) {
                return current.student;
            }

            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    // 刪除學生
    public boolean delete(String studentId) {
        if (search(studentId) == null) {
            return false;
        }

        root = delete(root, studentId);
        return true;
    }

    private StudentNode delete(StudentNode node, String studentId) {
        if (node == null) {
            return null;
        }

        int cmp = studentId.compareTo(node.student.studentId);

        if (cmp < 0) {
            node.left = delete(node.left, studentId);
        } else if (cmp > 0) {
            node.right = delete(node.right, studentId);
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
            StudentNode successor = findMin(node.right);

            node.student = successor.student;

            node.right = delete(
                node.right,
                successor.student.studentId
            );
        }

        return node;
    }

    private StudentNode findMin(StudentNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    // Inorder 顯示
    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(StudentNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.println(node.student);
        inorder(node.right);
    }
}

public class StudentBstIndex {

    public static void main(String[] args) {

        StudentBst tree = new StudentBst();

        System.out.println("===== Insert =====");

        System.out.println(
            tree.insert(new Student("A003", "Amy"))
        );

        System.out.println(
            tree.insert(new Student("A001", "Ben"))
        );

        System.out.println(
            tree.insert(new Student("A005", "Cindy"))
        );

        System.out.println(
            tree.insert(new Student("A002", "David"))
        );

        System.out.println(
            tree.insert(new Student("A004", "Eric"))
        );

        // 測試重複 studentId
        System.out.println(
            "Duplicate A003 = "
            + tree.insert(new Student("A003", "Frank"))
        );

        System.out.println();

        System.out.println("===== Inorder =====");
        tree.inorder();

        System.out.println("===== Search =====");

        Student found = tree.search("A004");

        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Not found");
        }

        Student missing = tree.search("A999");

        if (missing != null) {
            System.out.println("Found: " + missing);
        } else {
            System.out.println("A999 not found");
        }

        System.out.println();

        System.out.println("===== Delete =====");

        System.out.println(
            "Delete A003 = " + tree.delete("A003")
        );

        System.out.println(
            "Delete A999 = " + tree.delete("A999")
        );

        System.out.println();

        System.out.println("===== After Delete =====");
        tree.inorder();
    }
}