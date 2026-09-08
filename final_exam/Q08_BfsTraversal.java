import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q08_BfsTraversal {

    public static List<String> bfs(
            Map<String, List<String>> graph,
            String start) {

        List<String> result = new ArrayList<>();

        if (graph == null
                || start == null
                || !graph.containsKey(start)) {
            return result;
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {

            String current = queue.poll();
            result.add(current);

            List<String> neighbors = graph.get(current);

            if (neighbors == null) {
                continue;
            }

            for (String next : neighbors) {

                if (next != null
                        && graph.containsKey(next)
                        && visited.add(next)) {

                    queue.offer(next);
                }
            }
        }

        return result;
    }

    public static Map<String, Integer> distanceFrom(
            Map<String, List<String>> graph,
            String start) {

        Map<String, Integer> distance = new HashMap<>();

        if (graph == null
                || start == null
                || !graph.containsKey(start)) {
            return distance;
        }

        Queue<String> queue = new ArrayDeque<>();

        queue.offer(start);
        distance.put(start, 0);

        while (!queue.isEmpty()) {

            String current = queue.poll();

            List<String> neighbors = graph.get(current);

            if (neighbors == null) {
                continue;
            }

            for (String next : neighbors) {

                if (next != null
                        && graph.containsKey(next)
                        && !distance.containsKey(next)) {

                    distance.put(
                            next,
                            distance.get(current) + 1);

                    queue.offer(next);
                }
            }
        }

        return distance;
    }
}