import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q06_AdjacencyMatrixGraph {

    private final List<String> vertices = new ArrayList<>();
    private final Map<String, Integer> indexMap = new HashMap<>();
    private boolean[][] matrix;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {

        if (vertices == null) {
            this.matrix = new boolean[0][0];
            return;
        }

        for (String vertex : vertices) {

            if (vertex != null && !indexMap.containsKey(vertex)) {

                indexMap.put(vertex, this.vertices.size());
                this.vertices.add(vertex);
            }
        }

        matrix =
                new boolean[this.vertices.size()]
                           [this.vertices.size()];
    }

    public boolean addEdge(String first, String second) {

        Integer a = indexMap.get(first);
        Integer b = indexMap.get(second);

        if (a == null || b == null || a.equals(b)) {
            return false;
        }

        if (matrix[a][b]) {
            return false;
        }

        matrix[a][b] = true;
        matrix[b][a] = true;

        return true;
    }

    public boolean removeEdge(String first, String second) {

        Integer a = indexMap.get(first);
        Integer b = indexMap.get(second);

        if (a == null || b == null) {
            return false;
        }

        if (!matrix[a][b]) {
            return false;
        }

        matrix[a][b] = false;
        matrix[b][a] = false;

        return true;
    }

    public boolean hasEdge(String first, String second) {

        Integer a = indexMap.get(first);
        Integer b = indexMap.get(second);

        if (a == null || b == null) {
            return false;
        }

        return matrix[a][b];
    }

    public int degree(String vertex) {

        Integer index = indexMap.get(vertex);

        if (index == null) {
            return 0;
        }

        int degree = 0;

        for (boolean edge : matrix[index]) {
            if (edge) {
                degree++;
            }
        }

        return degree;
    }

    public List<String> neighbors(String vertex) {

        List<String> result = new ArrayList<>();

        Integer index = indexMap.get(vertex);

        if (index == null) {
            return result;
        }

        for (int i = 0; i < vertices.size(); i++) {

            if (matrix[index][i]) {
                result.add(vertices.get(i));
            }
        }

        return result;
    }
}
