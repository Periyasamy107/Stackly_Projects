package interfaces;

import enums.AuditAction;
import enums.AuditModule;
import model.AuditLog;

import java.util.List;

public interface AuditService {

    void createLog(AuditLog auditLog);

    void log(AuditModule module, AuditAction action, String description);

}
