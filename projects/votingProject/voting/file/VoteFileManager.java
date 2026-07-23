package file;

import config.ApplicationConstants;
import model.Vote;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VoteFileManager {

    private File file;

    public VoteFileManager() {
        file = new File(ApplicationConstants.VOTE_FILE);

        try{

            File parent = file.getParentFile();

            if(parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if(!file.exists()) {
                file.createNewFile();
            }

        }catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public void save(List<Vote> votes) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for(Vote vote : votes) {
                writer.write(vote.toFileString());
                writer.newLine();
            }
            writer.flush();
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<Vote> load() {
        List<Vote> votes = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    continue;
                }
                votes.add(Vote.fromFileString(line));
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return votes;
    }

}
