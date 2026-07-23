package menu;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import interfaces.CandidateService;
import model.Candidate;
import service.CandidateServiceImpl;

import java.util.List;
import java.util.Scanner;

public class CandidateManagementMenu {

    private Scanner scanner;
    private CandidateService candidateService;

    public CandidateManagementMenu() {

        scanner = new Scanner(System.in);

        candidateService =
                new CandidateServiceImpl();

    }

    public void showMenu() {

        int choice;

        do {

            System.out.println();
            System.out.println("======= CANDIDATE MANAGEMENT =======");
            System.out.println("1. Add Candidate");
            System.out.println("2. Update Candidate");
            System.out.println("3. Delete Candidate");
            System.out.println("4. Search Candidate");
            System.out.println("5. Display Candidates");
            System.out.println("6. Back");
            System.out.print("Enter Choice : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {

                case 1:
                    addCandidate();
                    break;

                case 2:
                    updateCandidate();
                    break;

                case 3:
                    deleteCandidate();
                    break;

                case 4:
                    searchCandidate();
                    break;

                case 5:
                    displayCandidates();
                    break;

                case 6:
                    break;

                default:
                    System.out.println("Invalid Choice.");

            }

        } while(choice != 6);

    }


    private void addCandidate() {

        try {

            Candidate candidate =
                    new Candidate();

            System.out.print("Student ID : ");
            candidate.setStudentId(
                    scanner.nextLine()
            );

            System.out.print("Candidate Name : ");
            candidate.setCandidateName(
                    scanner.nextLine()
            );

            System.out.print("Election ID : ");
            candidate.setElectionId(
                    scanner.nextLine()
            );

            System.out.print("Department (CSE/IT/ECE/EEE/MECH/CIVIL) : ");
            candidate.setDepartment(
                    scanner.nextLine()
            );

            System.out.print("Position : ");
            candidate.setPosition(
                    scanner.nextLine()
            );

            System.out.print("Manifesto : ");
            candidate.setManifesto(
                    scanner.nextLine()
            );

            candidateService.addCandidate(candidate);

            System.out.println(
                    "Candidate Added Successfully."
            );

        }
        catch(UserAlreadyExistsException |
              InputValidationException exception) {

            System.out.println(
                    exception.getMessage()
            );

        }

    }


    private void updateCandidate() {

        try {

            System.out.print(
                    "Candidate ID : "
            );

            String id =
                    scanner.nextLine();

            Candidate candidate =
                    candidateService.searchCandidate(id);

            System.out.print(
                    "New Manifesto : "
            );

            candidate.setManifesto(
                    scanner.nextLine()
            );

            candidateService.updateCandidate(candidate);

            System.out.println(
                    "Candidate Updated Successfully."
            );

        }
        catch(UserNotFoundException |
              InputValidationException exception) {

            System.out.println(
                    exception.getMessage()
            );

        }

    }


    private void deleteCandidate() {

        try {

            System.out.print(
                    "Candidate ID : "
            );

            String id =
                    scanner.nextLine();

            candidateService.deleteCandidate(id);

            System.out.println(
                    "Candidate Deleted Successfully."
            );

        }
        catch(UserNotFoundException exception) {

            System.out.println(
                    exception.getMessage()
            );

        }

    }


    private void searchCandidate() {

        try {

            System.out.print(
                    "Candidate ID : "
            );

            String id =
                    scanner.nextLine();

            Candidate candidate =
                    candidateService.searchCandidate(id);

            System.out.println(candidate);

        }
        catch(UserNotFoundException exception) {

            System.out.println(
                    exception.getMessage()
            );

        }

    }


    private void displayCandidates() {

        List<Candidate> candidates =
                candidateService.getAllCandidates();

        if(candidates.isEmpty()) {

            System.out.println("No Candidates Found.");

            return;

        }

        for(Candidate candidate : candidates) {

            System.out.println("-----------------------------------");

            System.out.println(candidate);

        }

    }

}