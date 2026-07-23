package main;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import menu.*;

import java.util.Scanner;

public class ApplicationController {

    private Scanner scanner;
    private StudentManagementMenu studentMenu;
    private AdminManagementMenu adminMenu;
    private CandidateManagementMenu candidateMenu;
    private ElectionManagementMenu electionMenu;
    private VotingManagementMenu votingMenu;
    private ResultManagementMenu resultMenu;
    private BackupManagementMenu backupMenu;

    public ApplicationController() {
        scanner = new Scanner(System.in);
        studentMenu = new StudentManagementMenu();
        adminMenu = new AdminManagementMenu();
        candidateMenu = new CandidateManagementMenu();
        electionMenu = new ElectionManagementMenu();
        votingMenu = new VotingManagementMenu();
        resultMenu = new ResultManagementMenu();
        backupMenu = new BackupManagementMenu();
    }

    public void start() throws UserNotFoundException, UserAlreadyExistsException, InputValidationException {

        int choice;

        do {

            System.out.println();
            System.out.println("============= ONLINE VOTING SYSTEM ================");
            System.out.println("1. Student Management");
            System.out.println("2. Admin Management");
            System.out.println("3. Candidate Management");
            System.out.println("4. Election Management");
            System.out.println("5. Voting Management");
            System.out.println("6. Result Management");
            System.out.println("7. Backup Management");
            System.out.println("8. Exit the program");
            System.out.print("Enter the choice : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> { studentMenu.showMenu(); }
                case 2 -> { adminMenu.showMenu(); }
                case 3 -> { candidateMenu.showMenu(); }
                case 4 -> { electionMenu.showMenu(); }
                case 5 -> { votingMenu.showMenu(); }
                case 6 -> { resultMenu.showMenu(); }
                case 7 -> { backupMenu.showMenu(); }
                case 8 -> {
                    System.out.println("Application Closed.");
                }
                default -> {
                    System.out.println("Invalid Choice.");
                }
            }

        } while (choice != 8);

    }

}
