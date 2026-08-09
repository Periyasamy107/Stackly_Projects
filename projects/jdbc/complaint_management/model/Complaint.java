package model;

import enums.ComplaintCategory;
import enums.ComplaintStatus;

import java.time.LocalDate;

public class Complaint {

    private String complaintId;
    private String userId;
    private String officerId;
    private ComplaintCategory category;
    private String description;
    private LocalDate complaintDate;
    private ComplaintStatus status;
    private String resolution;
    private LocalDate resolvedDate;

    public Complaint() {
    }

    public Complaint(String complaintId, String userId, String officerId, ComplaintCategory category,
                     String description, LocalDate complaintDate, ComplaintStatus status,
                     String resolution, LocalDate resolvedDate) {
        this.complaintId = complaintId;
        this.userId = userId;
        this.officerId = officerId;
        this.category = category;
        this.description = description;
        this.complaintDate = complaintDate;
        this.status = status;
        this.resolution = resolution;
        this.resolvedDate = resolvedDate;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public ComplaintCategory getCategory() {
        return category;
    }

    public void setCategory(ComplaintCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public LocalDate getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDate resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId='" + complaintId + '\'' +
                ", userId='" + userId + '\'' +
                ", officerId='" + officerId + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", complaintDate=" + complaintDate +
                ", status=" + status +
                ", resolution='" + resolution + '\'' +
                ", resolvedDate=" + resolvedDate +
                '}';
    }
}
