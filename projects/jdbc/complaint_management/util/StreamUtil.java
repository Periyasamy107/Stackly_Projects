package util;

import enums.ComplaintStatus;
import model.Complaint;
import model.Officer;
import model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamUtil {

    public static long activeUserCount(List<User> users) {
        return users.stream()
                .filter(User::isActive)
                .count();
    }

    public static long activeOfficerCount(List<Officer> officers) {
        return officers.stream()
                .filter(Officer::isActive)
                .count();
    }

    public static List<Complaint> getComplaintsByStatus(List<Complaint> complaints, ComplaintStatus complaintStatus) {
        return complaints.stream()
                .filter(complaint -> complaint.getStatus() == complaintStatus)
                .collect(Collectors.toList());
    }

    public static List<Complaint> sortComplaintsByDate(List<Complaint> complaints) {
        return complaints.stream()
                .sorted(Comparator.comparing(Complaint::getComplaintDate))
                .collect(Collectors.toList());
    }

    public static Map<ComplaintStatus, Long> complaintStatusReport(List<Complaint> complaints) {
        return complaints.stream()
                .collect(Collectors.groupingBy(Complaint::getStatus, Collectors.counting()));
    }

    public static Map<String, Long> officerComplaintCount(List<Complaint> complaints) {
        return complaints.stream()
                .filter(complaint -> complaint.getOfficerId() != null)
                .collect(Collectors.groupingBy(Complaint::getOfficerId, Collectors.counting()));
    }

}
