package menu;

import interfaces.BackupService;
import service.BackupServiceImpl;

import java.util.Scanner;

public class BackupManagementMenu {

    private Scanner scanner;

    private BackupService backupService;

    public BackupManagementMenu() {

        scanner = new Scanner(System.in);

        backupService = new BackupServiceImpl();

    }

    public void showMenu() {

        int choice;

        do {

            System.out.println();

            System.out.println("========== BACKUP MANAGEMENT ==========");

            System.out.println("1. Create Backup");

            System.out.println("2. Restore Backup");

            System.out.println("3. Back");

            System.out.print("Enter Choice : ");

            choice = scanner.nextInt();

            switch(choice) {

                case 1 -> {

                    if(backupService.createBackUp()) {

                        System.out.println(
                                "Backup Created Successfully."
                        );

                    }
                    else {

                        System.out.println(
                                "Backup Failed."
                        );

                    }

                }

                case 2 -> {

                    if(backupService.restoreBackUp()) {

                        System.out.println(
                                "Restore Successful."
                        );

                    }
                    else {

                        System.out.println(
                                "Restore Failed."
                        );

                    }

                }

                case 3 -> {
                }

                default -> {

                    System.out.println(
                            "Invalid Choice."
                    );

                }

            }

        } while(choice != 3);

    }

}