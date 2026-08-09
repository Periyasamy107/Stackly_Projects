package util;


import model.Complaint;

import java.util.List;

public class ComplaintDisplayUtil {

    public static void displayComplaints(
            List<Complaint> complaints) {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("              COMPLAINT REPORT");
        System.out.println("==================================================");

        if (complaints.isEmpty()) {
            System.out.println("No complaints found.");
            return;
        }

        complaints.stream()
                .forEach(complaint -> {

                    System.out.println(
                            "Complaint ID : "
                                    + complaint.getComplaintId());

                    System.out.println(
                            "User ID      : "
                                    + complaint.getUserId());

                    System.out.println(
                            "Officer ID   : "
                                    + complaint.getOfficerId());

                    System.out.println(
                            "Category     : "
                                    + complaint.getCategory());

                    System.out.println(
                            "Description  : "
                                    + complaint.getDescription());

                    System.out.println(
                            "Date         : "
                                    + complaint.getComplaintDate());

                    System.out.println(
                            "Status       : "
                                    + complaint.getStatus());

                    System.out.println(
                            "Resolution   : "
                                    + complaint.getResolution());

                    System.out.println(
                            "Resolved Date: "
                                    + complaint.getResolvedDate());

                    System.out.println(
                            "--------------------------------------------------");
                });
    }

}
