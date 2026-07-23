package util;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BackupManager {

    private static final String DATA_FOLDER = "data/";
    private static final String BACKUP_FOLDER = "backup/";

    public static boolean backupFiles() {
        try {
            File dataFolder = new File(DATA_FOLDER);
            File backupFolder = new File(BACKUP_FOLDER);

            if(!backupFolder.exists()) {
                backupFolder.mkdirs();
            }

            File[] files = dataFolder.listFiles();

            if(files == null) {
                return false;
            }

            for(File file : files) {
                if(file.isFile()) {
                    Files.copy(file.toPath(), new File(backupFolder, file.getName()).toPath(),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                }
            }
            return true;
        }
        catch(IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }



    public static boolean restoreFiles() {
        try {
            File dataFolder = new File(DATA_FOLDER);
            File backupFolder = new File(BACKUP_FOLDER);
            File[] files = backupFolder.listFiles();

            if(files == null) {
                return false;
            }

            for(File file : files) {
                if(file.isFile()) {
                    Files.copy(file.toPath(), new File(dataFolder, file.getName()).toPath(),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                }
            }
            return true;
        }
        catch(IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }


}
