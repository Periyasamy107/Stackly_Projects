package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import config.ApplicationConstants;
import model.Student;

public class StudentFileManager {

    private File file;

    public StudentFileManager() {

        file = new File(ApplicationConstants.STUDENT_FILE);

        try {

            File parent = file.getParentFile();

            if(parent != null && !parent.exists()) {

                parent.mkdirs();

            }

            if(!file.exists()) {

                file.createNewFile();

            }

        }

        catch(IOException exception) {

            exception.printStackTrace();

        }

    }

    public List<Student> load() {

        List<Student> students =
                new ArrayList<>();

        try(BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file))) {

            String line;

            while((line = reader.readLine()) != null) {

                if(line.trim().isEmpty()) {

                    continue;

                }

                students.add(
                        Student.fromFileString(line)
                );

            }

        }

        catch(IOException exception) {

            exception.printStackTrace();

        }

        return students;

    }

    public void save(
            List<Student> students) {

        try(BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(file))) {

            for(Student student : students) {

                writer.write(
                        student.toFileString()
                );

                writer.newLine();

            }

            writer.flush();

        }

        catch(IOException exception) {

            exception.printStackTrace();

        }

    }


    public static void clearStudentData() {

        File file = new File(ApplicationConstants.STUDENT_FILE);

        if(file.exists()) {
            file.delete();
        }

    }


}