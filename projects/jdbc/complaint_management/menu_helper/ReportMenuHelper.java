package menu_helper;

import menu.ReportMenu;
import service.ComplaintReportService;
import util.ComplaintDisplayUtil;
import util.ReportUtil;

import java.util.Map;
import java.util.Scanner;

public class ReportMenuHelper {

    private static final ComplaintReportService reportService =
            new ComplaintReportService();

    public static void reportMenu(Scanner scanner) {

        boolean back = false;

        while (!back) {
            try {
                switch (ReportMenu.showReportMenu(scanner)) {

                    case 1:
                        Map<String, Long> statusReport = reportService.getComplaintCountByStatus();
                        ReportUtil.printReport("COMPLAINT COUNT BY STATUS", statusReport);
                        break;
                    case 2:
                        Map<String, Long> categoryReport = reportService.getComplaintCountByCategory();
                        ReportUtil.printReport("COMPLAINT COUNT BY CATEGORY", categoryReport);
                        break;
                    case 3:
                        Map<String, Long> officerReport = reportService.getComplaintCountByOfficer();
                        ReportUtil.printReport("COMPLAINT COUNT BY OFFICER", officerReport);
                        break;
                    case 4:
                        ComplaintDisplayUtil.displayComplaints(reportService.getUnresolvedComplaints());
                        break;
                    case 5:
                        ComplaintDisplayUtil.displayComplaints(reportService.getResolvedComplaints());
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }
    }

}
