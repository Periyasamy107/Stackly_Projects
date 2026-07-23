package service;

import enums.AuditAction;
import enums.AuditModule;
import file.AuditFileManager;
import interfaces.AuditService;
import model.AuditLog;
import util.IDGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class AuditServiceImpl implements AuditService {

    private AuditFileManager auditFileManager;
    private List<AuditLog> logs;

    public AuditServiceImpl() {
        auditFileManager = new AuditFileManager();
        logs = auditFileManager.load();
    }

    @Override
    public void createLog(AuditLog auditLog) {

        if(auditLog.getAuditId() == null) {
            auditLog.setAuditId(IDGenerator.generateId("AUDIT"));
        }

        if(auditLog.getDateTime() == null) {
            auditLog.setDateTime(LocalDateTime.now());
        }

        logs.add(auditLog);

        auditFileManager.save(auditLog);
    }


    @Override
    public void log(AuditModule module,
                    AuditAction action,
                    String description) {

        AuditLog audit = new AuditLog();

        audit.setAuditId(IDGenerator.generateId("AUDIT"));
        audit.setModule(module.name());
        audit.setAction(action.name());
        audit.setDateTime(LocalDateTime.now());
        audit.setDescription(description);

        createLog(audit);
    }

}
