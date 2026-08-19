public class TransportFareSystem {

    // 抽象父類別
    static abstract class Transport {
        private String routeName;

        public Transport(String routeName) {
            this.routeName = routeName;
        }

        public String getRouteName() {
            return routeName;
        }

        // 抽象方法
        public abstract int calculateFare(int distance);
    }

    // Bus 子類別
    static class Bus extends Transport {

        public Bus(String routeName) {
            super(routeName);
        }

        @Override
        public int calculateFare(int distance) {
            if (distance <= 0) {
                return 0;
            }

            // 公車：基本票價 15 元
            // 超過 10 公里，每公里加 2 元
            if (distance <= 10) {
                return 15;
            }

            return 15 + (distance - 10) * 2;
        }
    }

    // Taxi 子類別
    static class Taxi extends Transport {

        public Taxi(String routeName) {
            super(routeName);
        }

        @Override
        public int calculateFare(int distance) {
            if (distance <= 0) {
                return 0;
            }

            // 計程車：起跳 85 元
            // 每公里加 20 元
            return 85 + distance * 20;
        }
    }

    public static void main(String[] args) {

        // 使用 Transport[] 保存不同子類別物件
        Transport[] transports = {
                new Bus("公車 307"),
                new Taxi("台北車站 → 淡水"),
                new Bus("公車 756"),
                new Taxi("板橋 → 台北")
        };

        int[] distances = {
                8,
                10,
                15,
                20
        };

        System.out.println("=== 交通票價 ===");

        for (int i = 0; i < transports.length; i++) {

            System.out.println(
                    "路線：" + transports[i].getRouteName()
                    + "，距離：" + distances[i] + " 公里"
                    + "，票價："
                    + transports[i].calculateFare(distances[i])
                    + " 元"
            );
        }
    }
}