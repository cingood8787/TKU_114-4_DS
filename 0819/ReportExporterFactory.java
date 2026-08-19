import java.util.Arrays;

public class ReportExporterFactory {

    // ReportExporter Interface
    interface ReportExporter {
        void export(String title, int[] values);
    }

    // CSV 輸出
    static class CsvExporter implements ReportExporter {

        @Override
        public void export(String title, int[] values) {

            if (values == null) {
                values = new int[0];
            }

            System.out.println("=== CSV Report ===");
            System.out.println("title," + title);

            System.out.print("values");

            for (int value : values) {
                System.out.print("," + value);
            }

            System.out.println();
        }
    }

    // JSON 輸出
    static class JsonExporter implements ReportExporter {

        @Override
        public void export(String title, int[] values) {

            if (values == null) {
                values = new int[0];
            }

            System.out.println("=== JSON Report ===");

            System.out.println(
                    "{ \"title\": \"" + title
                    + "\", \"values\": "
                    + Arrays.toString(values)
                    + " }"
            );
        }
    }

    // Text 輸出
    static class TextExporter implements ReportExporter {

        @Override
        public void export(String title, int[] values) {

            if (values == null) {
                values = new int[0];
            }

            System.out.println("=== Text Report ===");
            System.out.println("標題：" + title);
            System.out.println(
                    "資料：" + Arrays.toString(values)
            );
        }
    }

    // Factory Method
    public static ReportExporter createExporter(String format) {

        if (format == null) {
            return new TextExporter();
        }

        if (format.equalsIgnoreCase("csv")) {
            return new CsvExporter();
        }

        if (format.equalsIgnoreCase("json")) {
            return new JsonExporter();
        }

        if (format.equalsIgnoreCase("text")) {
            return new TextExporter();
        }

        // 不支援的格式預設 TextExporter
        return new TextExporter();
    }

    // 只依賴 ReportExporter Interface
    public static void exportReport(
            ReportExporter exporter,
            String title,
            int[] values) {

        if (exporter == null) {
            exporter = new TextExporter();
        }

        exporter.export(title, values);
    }

    public static void main(String[] args) {

        int[] data = {
                10, 20, 30, 40, 50
        };

        // CSV
        ReportExporter csv =
                createExporter("csv");

        exportReport(
                csv,
                "銷售報表",
                data
        );

        // JSON
        System.out.println();

        ReportExporter json =
                createExporter("json");

        exportReport(
                json,
                "成績報表",
                data
        );

        // Text
        System.out.println();

        ReportExporter text =
                createExporter("text");

        exportReport(
                text,
                "庫存報表",
                data
        );

        // 測試不支援格式
        System.out.println();

        ReportExporter unknown =
                createExporter("pdf");

        exportReport(
                unknown,
                "未知格式測試",
                data
        );

        // 測試 null values
        System.out.println();

        ReportExporter nullTest =
                createExporter("json");

        exportReport(
                nullTest,
                "Null 測試",
                null
        );
    }
}