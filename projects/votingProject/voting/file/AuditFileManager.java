package file;

import config.ApplicationConstants;
import model.AuditLog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuditFileManager {

    private File file;

    public AuditFileManager(){
        file = new File(ApplicationConstants.AUDIT_FILE);

        try{
            File parent = file.getParentFile();
            if(parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public void save(AuditLog auditLog) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writer.write(auditLog.toFileString());
            writer.newLine();
            writer.flush();

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public List<AuditLog> load() {

        List<AuditLog> logs = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    continue;
                }
                logs.add(AuditLog.fromFileString(line));
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return logs;

    }


}
