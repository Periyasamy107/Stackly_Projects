package menu;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import interfaces.ElectionService;
import model.Election;
import service.ElectionServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ElectionManagementMenu {

    private Scanner scanner;
    private ElectionService electionService;

    public ElectionManagementMenu() {
        scanner = new Scanner(System.in);
        electionService = new ElectionServiceImpl();
    }

    public void showMenu() {

        int choice;

        do{

            System.out.println();
            System.out.println("============== Election Management =================");
            System.out.println("1. Create Election");
            System.out.println("2. Update Election");
            System.out.println("3. Delete Election");
            System.out.println("4. Start Election");
            System.out.println("5. Close Election");
            System.out.println("6. Search Election");
            System.out.println("7. Display Elections");
            System.out.println("8. Back");

            System.out.print("Please enter the choice : ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createElection();
                    break;
                case 2:
                    updateElection();
                    break;
                case 3:
                    deleteElection();
                    break;
                case 4:
                    startElection();
                    break;
                case 5:
                    closeElection();
                    break;
                case 6:
                    searchElection();
                    break;
                case 7:
                    displayElections();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }while(choice != 8);
    }


    private void createElection() {
        try{
            Election election = new Election();

            System.out.print("Election Name : ");
            election.setElectionName(scanner.nextLine());

            System.out.print("Position : ");
            election.setPosition(scanner.nextLine());

            election.setStatus("CREATED");

            electionService.createElection(election);

            System.out.println("Election created successfully.");
        } catch (UserAlreadyExistsException | InputValidationException exception) {
            System.out.println(exception.getMessage());
        }
    }


    private void updateElection() {
        try{
            System.out.print("Election ID : ");
            String id = scanner.nextLine();

            Election election = electionService.searchElection(id);

            System.out.print("New Election Name : ");
            election.setElectionName(scanner.nextLine());

            System.out.print("New Position : ");
            election.setPosition(scanner.nextLine());

            electionService.updateElection(election);

            System.out.println("Election updated successfully.");
        } catch (UserNotFoundException | InputValidationException exception) {
            System.out.println(exception.getMessage());
        }
    }


    private void deleteElection() {
        try{
            System.out.print("Election ID : ");
            String id = scanner.nextLine();

            electionService.deleteElection(id);

            System.out.println("Election deleted successfully.");
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }


    private void startElection() {
        try {
            System.out.print("Election ID : ");
            String id = scanner.nextLine();

            electionService.startElection(id);

            System.out.println("Election started successfully.");
        } catch (UserNotFoundException exception) {
            System.out.println(exception.toString());
        }
    }


    private void closeElection() {
        try{
            System.out.print("Election ID : ");
            String id = scanner.nextLine();

            electionService.closeElection(id);

            System.out.println("Election Closed Successfully.");
        } catch (UserNotFoundException exception) {
            System.out.println(exception.toString());
        }
    }


    private void searchElection() {
        try{
            System.out.print("Election ID : ");
            String id = scanner.nextLine();

            Election election = electionService.searchElection(id);

            System.out.println(election);
        } catch (UserNotFoundException e) {
            System.out.println(e.toString());
        }
    }


    private void displayElections() {
        List<Election> elections = electionService.getallElections();
        if(elections.isEmpty()) {
            System.out.println("No Election Found.");
            return;
        }
        for(Election election : elections) {
            System.out.println("-----------------------------------");
            System.out.println(election);
        }
    }

}
