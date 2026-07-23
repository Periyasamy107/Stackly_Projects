package file;

import config.ApplicationConstants;
import model.Election;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ElectionFileManager {

    private File file;

    public ElectionFileManager() {
        file = new File(ApplicationConstants.ELECTION_FILE);

        try{
            File parent = file.getParentFile();

            if(!parent.exists() && parent != null) {
                parent.mkdirs();
            }

            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void save(List<Election> elections) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for(Election election : elections) {
                writer.write(election.toFileString());
                writer.newLine();
            }
            writer.flush();
        }catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<Election> load() {
        List<Election> elections = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    continue;
                }
                elections.add(Election.fromFileString(line));
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return elections;
    }

}
