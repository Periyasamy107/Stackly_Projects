package util;

import java.util.Map;

public class ReportUtil {

    public static void printReport(
            String title,
            Map<String, Long> report) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println(title);
        System.out.println("==================================================");

        if (report.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        report.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        System.out.printf(
                                "%-25s : %d%n",
                                entry.getKey(),
                                entry.getValue()
                        )
                );

        System.out.println("==================================================");
    }

}
