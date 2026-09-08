import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q07_AdjacencyListGraph {

    private final Map<String, Set<String>> graph =
            new LinkedHashMap<>();

    public boolean addVertex(String vertex) {

        if (vertex == null || graph.containsKey(vertex)) {
            return false;
        }

        graph.put(vertex, new LinkedHashSet<>());

        return true;
    }

    public boolean addEdge(String from, String to) {

        if (from == null || to == null) {
            return false;
        }

        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return false;
        }

        if (from.equals(to)) {
            return false;
        }

        return graph.get(from).add(to);
    }

    public boolean removeEdge(String from, String to) {

        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return false;
        }

        return graph.get(from).remove(to);
    }

    public List<String> outgoing(String vertex) {

        if (!graph.containsKey(vertex)) {
            return new ArrayList<>();
        }

        return new ArrayList<>(graph.get(vertex));
    }

    public int inDegree(String vertex) {

        if (!graph.containsKey(vertex)) {
            return 0;
        }

        int count = 0;

        for (Set<String> edges : graph.values()) {

            if (edges.contains(vertex)) {
                count++;
            }
        }

        return count;
    }

    public int edgeCount() {

        int count = 0;

        for (Set<String> edges : graph.values()) {
            count += edges.size();
        }

        return count;
    }
}