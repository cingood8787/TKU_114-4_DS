import java.util.ArrayList;
import java.util.List;

public class MetroMatrixGraph {

    private final String[] stations;
    private final boolean[][] matrix;
    private int edgeCount;

    public MetroMatrixGraph(String[] stations) {
        this.stations = stations;
        this.matrix = new boolean[stations.length][stations.length];
        this.edgeCount = 0;
    }

    // 找站點索引
    private int indexOf(String station) {
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].equals(station)) {
                return i;
            }
        }

        return -1;
    }

    // 新增無向連線
    public boolean addEdge(String stationA, String stationB) {
        int i = indexOf(stationA);
        int j = indexOf(stationB);

        if (i == -1 || j == -1) {
            return false;
        }

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

    // 查詢鄰站
    public List<String> neighbors(String station) {
        List<String> result = new ArrayList<>();

        int index = indexOf(station);

        if (index == -1) {
            return result;
        }

        for (int i = 0; i < stations.length; i++) {
            if (matrix[index][i]) {
                result.add(stations[i]);
            }
        }

        return result;
    }

    // 查詢 degree
    public int degree(String station) {
        int index = indexOf(station);

        if (index == -1) {
            return -1;
        }

        int count = 0;

        for (int i = 0; i < stations.length; i++) {
            if (matrix[index][i]) {
                count++;
            }
        }

        return count;
    }

    // 查詢 edge count
    public int edgeCount() {
        return edgeCount;
    }

    // Matrix Report
    public void matrixReport() {
        System.out.println("=== Metro Matrix Report ===");

        System.out.printf("%12s", "");

        for (String station : stations) {
            System.out.printf("%12s", station);
        }

        System.out.println();

        for (int i = 0; i < stations.length; i++) {

            System.out.printf("%12s", stations[i]);

            for (int j = 0; j < stations.length; j++) {
                System.out.printf(
                        "%12d",
                        matrix[i][j] ? 1 : 0
                );
            }

            System.out.println();
        }
    }

    // 完整站點報告
    public void stationReport() {
        System.out.println("=== Station Report ===");

        for (String station : stations) {
            System.out.println(
                    station
                            + " | neighbors = "
                            + neighbors(station)
                            + " | degree = "
                            + degree(station)
            );
        }

        System.out.println(
                "Edge count = " + edgeCount()
        );
    }

    public static void main(String[] args) {

        String[] stations = {
                "Taipei",
                "Zhongshan",
                "Shuanglian",
                "Minquan",
                "Yuanshan"
        };

        MetroMatrixGraph metro =
                new MetroMatrixGraph(stations);

        // 建立捷運站連線
        metro.addEdge("Taipei", "Zhongshan");
        metro.addEdge("Zhongshan", "Shuanglian");
        metro.addEdge("Shuanglian", "Minquan");
        metro.addEdge("Minquan", "Yuanshan");

        // 測試重複 edge
        boolean duplicate =
                metro.addEdge(
                        "Taipei",
                        "Zhongshan"
                );

        System.out.println(
                "Duplicate edge added = "
                        + duplicate
        );

        System.out.println();

        metro.stationReport();

        System.out.println();

        metro.matrixReport();
    }
}