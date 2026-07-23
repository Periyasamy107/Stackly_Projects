package file;

import config.ApplicationConstants;
import model.Result;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResultFileManager {

    private File file;

    public ResultFileManager() {

        file = new File(ApplicationConstants.RESULT_FILE);

        try{

            File parent = file.getParentFile();

            if(!parent.exists() && parent != null) {
                parent.mkdirs();
            }

            if(!file.exists()) {
                file.createNewFile();
            }

        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public void save(List<Result> results) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for(Result result : results) {
                writer.write(result.toFileString());
                writer.newLine();
            }
            writer.flush();

        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public List<Result> load() {

        List<Result> results = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    continue;
                }
                results.add(Result.fromFileString(line));
            }

        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
        return results;
    }

}
