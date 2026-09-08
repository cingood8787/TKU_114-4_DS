import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q09_DfsPathSearch {

    public static List<String> dfs(
            Map<String, List<String>> graph,
            String start) {

        List<String> result = new ArrayList<>();

        if (graph == null
                || start == null
                || !graph.containsKey(start)) {
            return result;
        }

        Set<String> visited = new HashSet<>();

        dfsRecursive(graph, start, visited, result);

        return result;
    }

    private static void dfsRecursive(
            Map<String, List<String>> graph,
            String current,
            Set<String> visited,
            List<String> result) {

        if (!visited.add(current)) {
            return;
        }

        result.add(current);

        List<String> neighbors = graph.get(current);

        if (neighbors == null) {
            return;
        }

        for (String next : neighbors) {

            if (next != null
                    && graph.containsKey(next)
                    && !visited.contains(next)) {

                dfsRecursive(
                        graph,
                        next,
                        visited,
                        result);
            }
        }
    }

    public static boolean reachable(
            Map<String, List<String>> graph,
            String start,
            String target) {

        if (graph == null
                || start == null
                || target == null
                || !graph.containsKey(start)
                || !graph.containsKey(target)) {
            return false;
        }

        if (start.equals(target)) {
            return true;
        }

        Set<String> visited = new HashSet<>();

        return reachableRecursive(
                graph,
                start,
                target,
                visited);
    }

    private static boolean reachableRecursive(
            Map<String, List<String>> graph,
            String current,
            String target,
            Set<String> visited) {

        if (current.equals(target)) {
            return true;
        }

        if (!visited.add(current)) {
            return false;
        }

        List<String> neighbors = graph.get(current);

        if (neighbors == null) {
            return false;
        }

        for (String next : neighbors) {

            if (next != null
                    && graph.containsKey(next)
                    && !visited.contains(next)) {

                if (reachableRecursive(
                        graph,
                        next,
                        target,
                        visited)) {

                    return true;
                }
            }
        }

        return false;
    }
}