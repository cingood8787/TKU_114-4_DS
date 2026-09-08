import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q10_UnweightedShortestPath {

    public static List<String> shortestPath(
            Map<String, List<String>> graph,
            String start,
            String target) {

        List<String> empty = new ArrayList<>();

        if (graph == null
                || start == null
                || target == null
                || !graph.containsKey(start)
                || !graph.containsKey(target)) {

            return empty;
        }

        if (start.equals(target)) {

            List<String> result = new ArrayList<>();
            result.add(start);

            return result;
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        Map<String, String> predecessor =
                new HashMap<>();

        queue.offer(start);
        visited.add(start);

        boolean found = false;

        while (!queue.isEmpty() && !found) {

            String current = queue.poll();

            List<String> neighbors = graph.get(current);

            if (neighbors == null) {
                continue;
            }

            for (String next : neighbors) {

                if (next == null
                        || !graph.containsKey(next)) {
                    continue;
                }

                if (visited.add(next)) {

                    predecessor.put(next, current);

                    if (next.equals(target)) {
                        found = true;
                        break;
                    }

                    queue.offer(next);
                }
            }
        }

        if (!found) {
            return empty;
        }

        List<String> path = new ArrayList<>();

        String current = target;

        while (current != null) {

            path.add(current);

            if (current.equals(start)) {
                break;
            }

            current = predecessor.get(current);
        }

        Collections.reverse(path);

        return path;
    }
}