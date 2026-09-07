import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginActivityReport {

    static class LoginRecord {
        String account;
        String ip;

        LoginRecord(String account, String ip) {
            this.account = account;
            this.ip = ip;
        }
    }

    // 輸出登入分析報告
    public static void generateReport(
            List<LoginRecord> records,
            int abnormalThreshold) {

        // 統計每個帳號登入次數
        Map<String, Integer> loginCount =
                new HashMap<>();

        // 記錄每個帳號使用過的不同 IP
        Map<String, Set<String>> accountIps =
                new HashMap<>();

        if (records != null) {
            for (LoginRecord record : records) {

                if (record == null
                        || record.account == null
                        || record.ip == null) {
                    continue;
                }

                // 登入次數 +1
                loginCount.put(
                        record.account,
                        loginCount.getOrDefault(
                                record.account, 0
                        ) + 1
                );

                // HashSet 自動排除重複 IP
                accountIps
                        .computeIfAbsent(
                                record.account,
                                k -> new HashSet<>()
                        )
                        .add(record.ip);
            }
        }

        // 排序帳號，讓輸出固定
        List<String> accounts =
                new ArrayList<>(loginCount.keySet());

        accounts.sort(String::compareTo);

        System.out.println(
                "=== Login Activity Report ==="
        );

        for (String account : accounts) {

            int count = loginCount.get(account);
            int uniqueIpCount =
                    accountIps.get(account).size();

            System.out.println(
                    account
                            + " | login count = "
                            + count
                            + " | unique IP = "
                            + uniqueIpCount
            );
        }

        // 異常重複登入
        System.out.println();
        System.out.println(
                "=== Abnormal Repeated Login Report ==="
        );

        boolean found = false;

        for (String account : accounts) {

            int count = loginCount.get(account);

            if (count >= abnormalThreshold) {

                found = true;

                System.out.println(
                        account
                                + " | login count = "
                                + count
                                + " | IPs = "
                                + accountIps.get(account)
                );
            }
        }

        if (!found) {
            System.out.println(
                    "No abnormal repeated login."
            );
        }
    }

    public static void main(String[] args) {

        List<LoginRecord> records =
                new ArrayList<>();

        records.add(
                new LoginRecord(
                        "user01",
                        "192.168.1.10"
                )
        );

        records.add(
                new LoginRecord(
                        "user01",
                        "192.168.1.10"
                )
        );

        records.add(
                new LoginRecord(
                        "user01",
                        "192.168.1.20"
                )
        );

        records.add(
                new LoginRecord(
                        "user01",
                        "192.168.1.30"
                )
        );

        records.add(
                new LoginRecord(
                        "user02",
                        "10.0.0.1"
                )
        );

        records.add(
                new LoginRecord(
                        "user02",
                        "10.0.0.1"
                )
        );

        records.add(
                new LoginRecord(
                        "user03",
                        "172.16.0.5"
                )
        );

        records.add(
                new LoginRecord(
                        "user03",
                        "172.16.0.6"
                )
        );

        records.add(
                new LoginRecord(
                        "user03",
                        "172.16.0.7"
                )
        );

        records.add(
                new LoginRecord(
                        "user03",
                        "172.16.0.8"
                )
        );

        records.add(
                new LoginRecord(
                        "user03",
                        "172.16.0.9"
                )
        );

        // 登入 4 次以上視為異常
        int abnormalThreshold = 4;

        generateReport(
                records,
                abnormalThreshold
        );
    }
}