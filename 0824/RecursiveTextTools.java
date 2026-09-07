public class RecursiveTextTools {

    // =========================
    // reverse
    // 遞迴反轉字串
    // =========================
    public static String reverse(String text) {

        if (text == null) {
            throw new IllegalArgumentException(
                    "text cannot be null"
            );
        }

        return reverseHelper(text, text.length() - 1);
    }

    private static String reverseHelper(
            String text,
            int index) {

        if (index < 0) {
            return "";
        }

        return text.charAt(index)
                + reverseHelper(text, index - 1);
    }


    // =========================
    // isPalindrome
    // 忽略英文大小寫與空白
    // =========================
    public static boolean isPalindrome(String text) {

        if (text == null) {
            throw new IllegalArgumentException(
                    "text cannot be null"
            );
        }

        return isPalindromeHelper(
                text,
                0,
                text.length() - 1
        );
    }

    private static boolean isPalindromeHelper(
            String text,
            int left,
            int right) {

        // 左右指標交錯，代表檢查完成
        if (left >= right) {
            return true;
        }

        // 忽略左邊空白
        if (Character.isWhitespace(
                text.charAt(left))) {

            return isPalindromeHelper(
                    text,
                    left + 1,
                    right
            );
        }

        // 忽略右邊空白
        if (Character.isWhitespace(
                text.charAt(right))) {

            return isPalindromeHelper(
                    text,
                    left,
                    right - 1
            );
        }

        char leftChar =
                Character.toLowerCase(
                        text.charAt(left)
                );

        char rightChar =
                Character.toLowerCase(
                        text.charAt(right)
                );

        if (leftChar != rightChar) {
            return false;
        }

        return isPalindromeHelper(
                text,
                left + 1,
                right - 1
        );
    }


    // =========================
    // countCharacter
    // 計算指定字元出現次數
    // =========================
    public static int countCharacter(
            String text,
            char target) {

        if (text == null) {
            throw new IllegalArgumentException(
                    "text cannot be null"
            );
        }

        return countCharacterHelper(
                text,
                target,
                0
        );
    }

    private static int countCharacterHelper(
            String text,
            char target,
            int index) {

        if (index == text.length()) {
            return 0;
        }

        int count =
                text.charAt(index) == target
                        ? 1
                        : 0;

        return count
                + countCharacterHelper(
                        text,
                        target,
                        index + 1
                );
    }


    // =========================
    // 測試
    // =========================
    public static void main(String[] args) {

        // Empty String
        System.out.println(
                "=== Empty String ==="
        );

        System.out.println(
                "reverse = \""
                        + reverse("")
                        + "\""
        );

        System.out.println(
                "palindrome = "
                        + isPalindrome("")
        );

        System.out.println(
                "count a = "
                        + countCharacter("", 'a')
        );


        // Single Character
        System.out.println();
        System.out.println(
                "=== Single Character ==="
        );

        System.out.println(
                "reverse = "
                        + reverse("A")
        );

        System.out.println(
                "palindrome = "
                        + isPalindrome("A")
        );

        System.out.println(
                "count A = "
                        + countCharacter("A", 'A')
        );


        // Level
        System.out.println();
        System.out.println(
                "=== Level ==="
        );

        System.out.println(
                "reverse = "
                        + reverse("Level")
        );

        System.out.println(
                "palindrome = "
                        + isPalindrome("Level")
        );

        System.out.println(
                "count e = "
                        + countCharacter("Level", 'e')
        );


        // 一般字串
        System.out.println();
        System.out.println(
                "=== Normal String ==="
        );

        String text = "Hello World";

        System.out.println(
                "Original = " + text
        );

        System.out.println(
                "reverse = "
                        + reverse(text)
        );

        System.out.println(
                "palindrome = "
                        + isPalindrome(text)
        );

        System.out.println(
                "count l = "
                        + countCharacter(text, 'l')
        );


        // 測試忽略大小寫與空白
        System.out.println();
        System.out.println(
                "=== Ignore Case And Space ==="
        );

        String palindromeText =
                "Never odd or even";

        System.out.println(
                palindromeText
                        + " -> "
                        + isPalindrome(palindromeText)
        );
    }
}