import java.util.Objects;

public class MemberEqualityPractice {

    static class LibraryMember {
        private String memberId;
        private String name;
        private String email;

        public LibraryMember(String memberId, String name, String email) {
            this.memberId = memberId;
            this.name = name;
            this.email = email;
        }

        // 1. Override toString()
        @Override
        public String toString() {
            return "會員編號：" + memberId
                    + "，姓名：" + name
                    + "，Email：" + email;
        }

        // 2. Override equals()，只用 memberId 判斷
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            LibraryMember other = (LibraryMember) obj;
            return Objects.equals(memberId, other.memberId);
        }

        // 2. Override hashCode()，只使用 memberId
        @Override
        public int hashCode() {
            return Objects.hash(memberId);
        }
    }

    public static void main(String[] args) {

        // 3. 建立兩個 id 相同，但 email 不同的會員
        LibraryMember member1 = new LibraryMember(
                "M001",
                "王小明",
                "ming@gmail.com"
        );

        LibraryMember member2 = new LibraryMember(
                "M001",
                "王小明",
                "ming123@gmail.com"
        );

        System.out.println("=== 會員資料 ===");
        System.out.println(member1);
        System.out.println(member2);

        // 4. 比較 == 與 equals()
        System.out.println("\n=== 身分比較 ===");
        System.out.println("member1 == member2："
                + (member1 == member2));

        System.out.println("member1.equals(member2)："
                + member1.equals(member2));

        // 測試 null
        System.out.println("member1.equals(null)："
                + member1.equals(null));
    }
}