public class MemberBstIndex {

    // =========================================
    // Member
    // =========================================
    static class Member {
        private int memberId;
        private String name;
        private String email;

        public Member(int memberId, String name, String email) {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be blank");
            }

            this.memberId = memberId;
            this.name = name;
            this.email = email;
        }

        public int getMemberId() {
            return memberId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be blank");
            }

            this.email = email;
        }

        @Override
        public String toString() {
            return "Member{id=" + memberId
                    + ", name='" + name + '\''
                    + ", email='" + email + '\''
                    + "}";
        }
    }

    // =========================================
    // Node
    // =========================================
    static class Node {
        Member member;
        Node left;
        Node right;

        public Node(Member member) {
            this.member = member;
        }
    }

    // =========================================
    // BST
    // =========================================
    static class MemberBST {

        private Node root;
        private int size;

        // -----------------------------------------
        // add
        // memberId 不可重複
        // -----------------------------------------
        public boolean add(Member member) {

            if (member == null) {
                return false;
            }

            if (root == null) {
                root = new Node(member);
                size++;
                return true;
            }

            Node current = root;

            while (true) {

                int newId = member.getMemberId();
                int currentId = current.member.getMemberId();

                if (newId < currentId) {

                    if (current.left == null) {
                        current.left = new Node(member);
                        size++;
                        return true;
                    }

                    current = current.left;

                } else if (newId > currentId) {

                    if (current.right == null) {
                        current.right = new Node(member);
                        size++;
                        return true;
                    }

                    current = current.right;

                } else {
                    // duplicate memberId
                    return false;
                }
            }
        }

        // -----------------------------------------
        // find
        // -----------------------------------------
        public Member find(int memberId) {

            Node current = root;

            while (current != null) {

                int currentId = current.member.getMemberId();

                if (memberId < currentId) {
                    current = current.left;

                } else if (memberId > currentId) {
                    current = current.right;

                } else {
                    return current.member;
                }
            }

            return null;
        }

        // -----------------------------------------
        // updateEmail
        // -----------------------------------------
        public boolean updateEmail(int memberId, String newEmail) {

            // email 不得 blank
            if (newEmail == null || newEmail.trim().isEmpty()) {
                return false;
            }

            Member member = find(memberId);

            if (member == null) {
                return false;
            }

            member.setEmail(newEmail);

            return true;
        }

        // -----------------------------------------
        // remove
        // -----------------------------------------
        public boolean remove(int memberId) {

            if (find(memberId) == null) {
                return false;
            }

            root = removeRecursive(root, memberId);
            size--;

            return true;
        }

        private Node removeRecursive(Node node, int memberId) {

            if (node == null) {
                return null;
            }

            int currentId = node.member.getMemberId();

            if (memberId < currentId) {

                node.left =
                        removeRecursive(node.left, memberId);

            } else if (memberId > currentId) {

                node.right =
                        removeRecursive(node.right, memberId);

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
                Node successor = findMin(node.right);

                node.member = successor.member;

                node.right =
                        removeRecursive(
                                node.right,
                                successor.member.getMemberId()
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
        // inorder report
        // -----------------------------------------
        public void inorderReport() {

            System.out.println("=== Member Inorder Report ===");

            if (root == null) {
                System.out.println("(empty)");
                return;
            }

            inorderRecursive(root);

            System.out.println("Total members = " + size);
        }

        private void inorderRecursive(Node node) {

            if (node == null) {
                return;
            }

            inorderRecursive(node.left);

            System.out.println(node.member);

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

        MemberBST tree = new MemberBST();

        // -----------------------------------------
        // Add
        // -----------------------------------------
        System.out.println("=== Add Members ===");

        System.out.println(
                "add 50 = "
                        + tree.add(
                        new Member(
                                50,
                                "Amy",
                                "amy@gmail.com"
                        )
                )
        );

        System.out.println(
                "add 30 = "
                        + tree.add(
                        new Member(
                                30,
                                "Ben",
                                "ben@gmail.com"
                        )
                )
        );

        System.out.println(
                "add 70 = "
                        + tree.add(
                        new Member(
                                70,
                                "Cindy",
                                "cindy@gmail.com"
                        )
                )
        );

        System.out.println(
                "add 20 = "
                        + tree.add(
                        new Member(
                                20,
                                "David",
                                "david@gmail.com"
                        )
                )
        );

        System.out.println(
                "add 40 = "
                        + tree.add(
                        new Member(
                                40,
                                "Eric",
                                "eric@gmail.com"
                        )
                )
        );

        System.out.println(
                "add 60 = "
                        + tree.add(
                        new Member(
                                60,
                                "Fiona",
                                "fiona@gmail.com"
                        )
                )
        );

        System.out.println(
                "add 80 = "
                        + tree.add(
                        new Member(
                                80,
                                "George",
                                "george@gmail.com"
                        )
                )
        );


        System.out.println();

        // -----------------------------------------
        // Duplicate ID
        // -----------------------------------------
        System.out.println("=== Duplicate ID ===");

        System.out.println(
                "add duplicate 30 = "
                        + tree.add(
                        new Member(
                                30,
                                "Other",
                                "other@gmail.com"
                        )
                )
        );


        System.out.println();

        // -----------------------------------------
        // Find
        // -----------------------------------------
        System.out.println("=== Find ===");

        System.out.println(
                "find 40 = "
                        + tree.find(40)
        );

        System.out.println(
                "find 999 = "
                        + tree.find(999)
        );


        System.out.println();

        // -----------------------------------------
        // Update Email
        // -----------------------------------------
        System.out.println("=== Update Email ===");

        System.out.println(
                "update 40 = "
                        + tree.updateEmail(
                        40,
                        "eric_new@gmail.com"
                )
        );

        System.out.println(
                "after update = "
                        + tree.find(40)
        );


        // blank email
        System.out.println(
                "blank email = "
                        + tree.updateEmail(
                        40,
                        "   "
                )
        );


        // missing member
        System.out.println(
                "update missing = "
                        + tree.updateEmail(
                        999,
                        "test@gmail.com"
                )
        );


        System.out.println();

        // -----------------------------------------
        // Inorder Report
        // -----------------------------------------
        tree.inorderReport();


        System.out.println();

        // -----------------------------------------
        // Remove
        // -----------------------------------------
        System.out.println("=== Remove ===");

        System.out.println(
                "remove 20 = "
                        + tree.remove(20)
        );

        System.out.println(
                "remove 70 = "
                        + tree.remove(70)
        );

        System.out.println(
                "remove missing 999 = "
                        + tree.remove(999)
        );


        System.out.println();

        // 刪除後再輸出
        tree.inorderReport();
    }
}