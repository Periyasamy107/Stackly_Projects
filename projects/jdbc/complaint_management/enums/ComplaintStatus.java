package enums;

public enum ComplaintStatus {

    REGISTERED("Complaint Registered"),
    ASSIGNED("Officer Assigned"),
    IN_PROGRESS("Work In Progress"),
    ON_HOLD("On Hold"),
    RESOLVED("Resolved"),
    CLOSED("Closed");

    private final String description;

    ComplaintStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
