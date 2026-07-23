package file;

import config.ApplicationConstants;
import model.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminFileManager {

    private File file;

    public AdminFileManager() {

        file = new File(ApplicationConstants.ADMIN_FILE);
        System.out.println(
                "Admin file location : " + file.getAbsolutePath()
        );

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


    public void save(List<Admin> admins) {

        try(
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(file))
        ) {


            for(Admin admin : admins) {

                writer.write(
                        admin.toFileString()
                );

                writer.newLine();

            }


            writer.flush();


        }
        catch(IOException exception) {

            exception.printStackTrace();

        }

    }



    public List<Admin> load() {

        List<Admin> admins =
                new ArrayList<>();


        try(
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file))
        ) {


            String line;


            while((line = reader.readLine()) != null) {


                if(line.trim().isEmpty()) {

                    continue;

                }


                admins.add(
                        Admin.fromFileString(line)
                );


            }


        }
        catch(IOException exception) {

            exception.printStackTrace();

        }


        return admins;

    }


    public static void clearAdminData() {

        File file = new File(ApplicationConstants.ADMIN_FILE);

        if(file.exists()) {
            file.delete();
        }

    }

}