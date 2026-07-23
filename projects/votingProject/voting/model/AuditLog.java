package model;

import java.time.LocalDateTime;

public class AuditLog {

    private String auditId;
    private String action;
    private String module;
    private LocalDateTime dateTime;
    private String description;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toFileString() {
        return auditId + "|" +
                action + "|" +
                module + "|" +
                dateTime + "|" +
                description;
    }

    public static AuditLog fromFileString(String line) {

        String[] data = line.split("\\|");

        AuditLog audit = new AuditLog();

        audit.setAuditId(data[0]);
        audit.setAction(data[1]);
        audit.setModule(data[2]);
        audit.setDateTime(LocalDateTime.parse(data[3]));
        audit.setDescription(data[4]);

        return audit;

    }


    @Override
    public String toString() {
        return "Audit ID : " + auditId +
                "\nAction : " + action +
                "\nModule : " + module +
                "\nDate Time : " + dateTime +
                "\nDescription : " + description;
    }

}
