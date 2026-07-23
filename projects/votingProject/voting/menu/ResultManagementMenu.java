package menu;

import exception.UserNotFoundException;
import interfaces.ResultService;
import model.Result;
import service.ResultServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ResultManagementMenu {

    private Scanner scanner;
    private ResultService resultService;

    public ResultManagementMenu() {
        scanner = new Scanner(System.in);
        resultService = new ResultServiceImpl();
    }

    public void showMenu() {

        int choice;

        do{

            System.out.println();
            System.out.println("============= Result Management ==============");
            System.out.println("1. Generate Result");
            System.out.println("2. Display Winner");
            System.out.println("3. Back");

            System.out.print("Enter the choice : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    generateResult();
                    break;
                case 2:
                    displayWinner();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 3);
    }


    private void generateResult() {
        try{
            System.out.print("Election ID : ");
            String electionId = scanner.nextLine();

            List<Result> results  = resultService.generateResult(electionId);
            for(Result result : results) {
                System.out.println("--------------------------------");
                System.out.println(result);
            }
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }


    private void displayWinner() {
        try{
            System.out.print("Election ID : ");
            String electionId = scanner.nextLine();
            Result winner = resultService.getWinner(electionId);
            System.out.println(winner);
        } catch (UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
