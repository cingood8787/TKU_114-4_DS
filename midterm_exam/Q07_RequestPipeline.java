import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {

    public static boolean isBalanced(String text) {

        if (text == null) {
            return false;
        }

        Deque<Character> stack =
                new ArrayDeque<>();

        for (char c : text.toCharArray()) {

            if (c == '(' || c == '[' || c == '{') {

                stack.push(c);

            } else if (
                    c == ')' || c == ']' || c == '}') {

                if (stack.isEmpty()) {
                    return false;
                }

                char open = stack.pop();

                if ((c == ')' && open != '(')
                        || (c == ']' && open != '[')
                        || (c == '}' && open != '{')) {

                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static List<String> process(
            String[] commands) {

        List<String> result =
                new ArrayList<>();

        if (commands == null) {
            return result;
        }

        Deque<String> urgent =
                new ArrayDeque<>();

        Deque<String> normal =
                new ArrayDeque<>();

        for (String command : commands) {

            if (command == null
                    || command.isBlank()) {

                continue;
            }

            String trimmed =
                    command.trim();

            if (trimmed.startsWith("NORMAL ")) {

                String id =
                        trimmed.substring(7).trim();

                if (!id.isEmpty()) {
                    normal.addLast(id);
                }

            } else if (
                    trimmed.startsWith("URGENT ")) {

                String id =
                        trimmed.substring(7).trim();

                if (!id.isEmpty()) {
                    urgent.addLast(id);
                }

            } else if (
                    trimmed.equals("PROCESS")) {

                if (!urgent.isEmpty()) {

                    result.add(
                            urgent.removeFirst()
                    );

                } else if (!normal.isEmpty()) {

                    result.add(
                            normal.removeFirst()
                    );

                } else {

                    result.add("EMPTY");
                }
            }
        }

        return result;
    }
}