import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogisticsWeightedGraph {

    // from -> (to -> cost)
    private final Map<String, Map<String, Integer>> graph =
            new HashMap<>();

    // 新增 vertex
    public void addVertex(String vertex) {
        if (vertex != null) {
            graph.putIfAbsent(vertex, new HashMap<>());
        }
    }

    // 新增 weighted directed edge
    public boolean addEdge(
            String from,
            String to,
            int weight) {

        // 拒絕負權重
        if (weight < 0) {
            return false;
        }

        // 拒絕不存在 vertex
        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return false;
        }

        // 已存在就不新增
        if (graph.get(from).containsKey(to)) {
            return false;
        }

        graph.get(from).put(to, weight);
        return true;
    }

    // 更新 edge 權重
    public boolean updateEdge(
            String from,
            String to,
            int newWeight) {

        if (newWeight < 0) {
            return false;
        }

        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return false;
        }

        if (!graph.get(from).containsKey(to)) {
            return false;
        }

        graph.get(from).put(to, newWeight);
        return true;
    }

    // 移除 edge
    public boolean removeEdge(
            String from,
            String to) {

        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return false;
        }

        if (!graph.get(from).containsKey(to)) {
            return false;
        }

        graph.get(from).remove(to);
        return true;
    }

    // 查詢 edge 權重
    public Integer getWeight(
            String from,
            String to) {

        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return null;
        }

        return graph.get(from).get(to);
    }

    // 判斷 edge 是否存在
    public boolean hasEdge(
            String from,
            String to) {

        if (!graph.containsKey(from)
                || !graph.containsKey(to)) {
            return false;
        }

        return graph.get(from).containsKey(to);
    }

    // 查詢某 vertex 的 outgoing edges
    public Map<String, Integer> outgoingEdges(
            String vertex) {

        if (!graph.containsKey(vertex)) {
            return new HashMap<>();
        }

        return new HashMap<>(graph.get(vertex));
    }

    // 輸出完整報告
    public void report() {

        List<String> vertices =
                new ArrayList<>(graph.keySet());

        vertices.sort(String::compareTo);

        System.out.println(
                "=== Logistics Weighted Graph ==="
        );

        for (String from : vertices) {

            System.out.print(from + " -> ");

            List<String> destinations =
                    new ArrayList<>(
                            graph.get(from).keySet()
                    );

            destinations.sort(String::compareTo);

            if (destinations.isEmpty()) {
                System.out.println("[]");
                continue;
            }

            System.out.print("[");

            for (int i = 0; i < destinations.size(); i++) {

                String to = destinations.get(i);
                int weight =
                        graph.get(from).get(to);

                System.out.print(
                        to + "(" + weight + ")"
                );

                if (i < destinations.size() - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println("]");
        }
    }

    public static void main(String[] args) {

        LogisticsWeightedGraph graph =
                new LogisticsWeightedGraph();

        // 建立物流節點
        graph.addVertex("Taipei");
        graph.addVertex("Taoyuan");
        graph.addVertex("Taichung");
        graph.addVertex("Tainan");
        graph.addVertex("Kaohsiung");

        // 新增 directed weighted edges
        graph.addEdge(
                "Taipei",
                "Taoyuan",
                100
        );

        graph.addEdge(
                "Taipei",
                "Taichung",
                300
        );

        graph.addEdge(
                "Taoyuan",
                "Taichung",
                180
        );

        graph.addEdge(
                "Taichung",
                "Tainan",
                220
        );

        graph.addEdge(
                "Tainan",
                "Kaohsiung",
                120
        );

        System.out.println(
                "=== Initial Graph ==="
        );

        graph.report();

        System.out.println();

        // 查詢
        System.out.println(
                "Taipei -> Taichung cost = "
                        + graph.getWeight(
                                "Taipei",
                                "Taichung"
                        )
        );

        // 更新
        boolean updated =
                graph.updateEdge(
                        "Taipei",
                        "Taichung",
                        250
                );

        System.out.println(
                "Update Taipei -> Taichung = "
                        + updated
        );

        System.out.println(
                "New cost = "
                        + graph.getWeight(
                                "Taipei",
                                "Taichung"
                        )
        );

        System.out.println();

        // 移除
        boolean removed =
                graph.removeEdge(
                        "Taoyuan",
                        "Taichung"
                );

        System.out.println(
                "Remove Taoyuan -> Taichung = "
                        + removed
        );

        // 測試負權重
        boolean negative =
                graph.addEdge(
                        "Taipei",
                        "Kaohsiung",
                        -50
                );

        System.out.println(
                "Add negative weight = "
                        + negative
        );

        // 測試不存在 vertex
        boolean invalidVertex =
                graph.addEdge(
                        "Taipei",
                        "Hualien",
                        200
                );

        System.out.println(
                "Add nonexistent vertex = "
                        + invalidVertex
        );

        System.out.println();
        System.out.println(
                "=== Final Graph ==="
        );

        graph.report();
    }
}