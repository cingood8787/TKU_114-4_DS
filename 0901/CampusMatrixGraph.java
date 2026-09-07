import java.util.ArrayList;
import java.util.List;

public class CampusMatrixGraph {

    private final boolean[][] matrix;
    private final String[] vertices;
    private int edgeCount;

    public CampusMatrixGraph(String[] vertices) {
        this.vertices = vertices;
        this.matrix = new boolean[vertices.length][vertices.length];
        this.edgeCount = 0;
    }

    // 找頂點索引
    private int indexOf(String vertex) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }

        return -1;
    }

    // 新增無向 edge
    public boolean addEdge(String a, String b) {
        int i = indexOf(a);
        int j = indexOf(b);

        if (i == -1 || j == -1) {
            return false;
        }

        // 不允許 self-loop
        if (i == j) {
            return false;
        }

        // 重複 edge 不重複計數
        if (matrix[i][j]) {
            return false;
        }

        matrix[i][j] = true;
        matrix[j][i] = true;

        edgeCount++;
        return true;
    }

    // 移除無向 edge
    public boolean removeEdge(String a, String b) {
        int i = indexOf(a);
        int j = indexOf(b);

        if (i == -1 || j == -1) {
            return false;
        }

        if (!matrix[i][j]) {
            return false;
        }

        matrix[i][j] = false;
        matrix[j][i] = false;

        edgeCount--;
        return true;
    }

    // 查詢 degree
    public int degree(String vertex) {
        int index = indexOf(vertex);

        if (index == -1) {
            return -1;
        }

        int count = 0;

        for (int i = 0; i < vertices.length; i++) {
            if (matrix[index][i]) {
                count++;
            }
        }

        return count;
    }

    // 查詢 neighbors
    public List<String> neighbors(String vertex) {
        List<String> result = new ArrayList<>();

        int index = indexOf(vertex);

        if (index == -1) {
            return result;
        }

        for (int i = 0; i < vertices.length; i++) {
            if (matrix[index][i]) {
                result.add(vertices[i]);
            }
        }

        return result;
    }

    // 查詢 edge count
    public int edgeCount() {
        return edgeCount;
    }

    // 顯示 adjacency matrix
    public void printMatrix() {
        System.out.println("=== Adjacency Matrix ===");

        System.out.print("        ");

        for (String vertex : vertices) {
            System.out.printf("%10s", vertex);
        }

        System.out.println();

        for (int i = 0; i < vertices.length; i++) {

            System.out.printf("%8s", vertices[i]);

            for (int j = 0; j < vertices.length; j++) {
                System.out.printf(
                        "%10d",
                        matrix[i][j] ? 1 : 0
                );
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {

        String[] campuses = {
                "Library",
                "Gym",
                "Cafeteria",
                "Dorm",
                "Classroom"
        };

        CampusMatrixGraph graph =
                new CampusMatrixGraph(campuses);

        // 新增 edges
        graph.addEdge("Library", "Gym");
        graph.addEdge("Library", "Cafeteria");
        graph.addEdge("Gym", "Dorm");
        graph.addEdge("Dorm", "Classroom");
        graph.addEdge("Cafeteria", "Classroom");

        // 測試重複 edge
        boolean duplicate =
                graph.addEdge("Library", "Gym");

        System.out.println(
                "Duplicate edge added = " + duplicate
        );

        System.out.println(
                "Edge count = " + graph.edgeCount()
        );

        System.out.println(
                "Library degree = "
                        + graph.degree("Library")
        );

        System.out.println(
                "Library neighbors = "
                        + graph.neighbors("Library")
        );

        System.out.println();

        graph.printMatrix();

        // 移除 edge
        System.out.println();
        System.out.println("=== Remove Edge ===");

        graph.removeEdge("Library", "Gym");

        System.out.println(
                "Edge count = " + graph.edgeCount()
        );

        System.out.println(
                "Library degree = "
                        + graph.degree("Library")
        );

        System.out.println(
                "Library neighbors = "
                        + graph.neighbors("Library")
        );
    }
}