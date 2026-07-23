package file;

import config.ApplicationConstants;
import model.Candidate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateFileManager {

    private File file;

    public CandidateFileManager() {
        file = new File(ApplicationConstants.CANDIDATE_FILE);

        try{
            File parent = file.getParentFile();

            if(!parent.exists() && parent != null) {
                parent.mkdirs();
            }

            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }


    public void save(List<Candidate> candidates) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for(Candidate candidate : candidates) {
                writer.write(candidate.toFileString());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }


    public List<Candidate> load() {
        List<Candidate> candidates = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    continue;
                }
                candidates.add(Candidate.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return candidates;
    }

}
