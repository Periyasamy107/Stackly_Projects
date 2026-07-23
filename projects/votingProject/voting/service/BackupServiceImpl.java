package service;

import enums.AuditAction;
import enums.AuditModule;
import interfaces.AuditService;
import interfaces.BackupService;
import util.BackupManager;

public class BackupServiceImpl implements BackupService {

    private AuditService auditService;

    public BackupServiceImpl() {
        auditService = new AuditServiceImpl();
    }

    @Override
    public boolean createBackUp() {

        auditService.log(
                AuditModule.BACKUP,
                AuditAction.TAKE_BACKUP,
                "Back Up taken successfully"
        );

        return BackupManager.backupFiles();

    }

    @Override
    public boolean restoreBackUp() {

        auditService.log(
                AuditModule.BACKUP,
                AuditAction.RESTORE,
                "Back Up taken successfully"
        );

        return BackupManager.restoreFiles();

    }

}
