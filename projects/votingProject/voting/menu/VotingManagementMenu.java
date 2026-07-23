package menu;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import interfaces.VotingService;
import model.Vote;
import service.VotingServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class VotingManagementMenu {

    private Scanner scanner;
    private VotingService votingService;

    public VotingManagementMenu() {
        scanner = new Scanner(System.in);
        votingService = new VotingServiceImpl();
    }

    public void showMenu() {
        int choice;
        do{
            System.out.println();
            System.out.println("=============== Vote Management ================");
            System.out.println("1. Cast Vote");
            System.out.println("2. Display Votes");
            System.out.println("3. Check Student Vote");
            System.out.println("4. Back");

            System.out.print("Enter the choice : ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> { castVote(); }
                case 2 -> { displayVotes(); }
                case 3 -> { checkVote(); }
                case 4 -> { break; }
                default -> System.out.println("Invalid Choice");
            }
        } while(choice != 4);
    }


    private void castVote() {
        try{

            Vote vote = new Vote();
            System.out.print("Vote ID : ");
            vote.setVoteId(scanner.nextLine());

            System.out.print("Election ID : ");
            vote.setElectionId(scanner.nextLine());

            System.out.print("Student ID : ");
            vote.setStudentId(scanner.nextLine());

            System.out.print("Candidate ID : ");
            vote.setCandidateId(scanner.nextLine());

            vote.setVoteDateTime(LocalDateTime.now());

            vote.setStatus("CASTED");

            votingService.castVote(vote);

            System.out.println("Vote Casted Successfully.");

        } catch(UserAlreadyExistsException | InputValidationException | UserNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }


    private void displayVotes() {
        List<Vote> votes = votingService.getAllVotes();
        if(votes.isEmpty()) {
            System.out.println("No Votes Found.");
            return;
        }
        for(Vote vote : votes) {
            System.out.println("-----------------------------------");
            System.out.println(vote);
        }
    }


    private void checkVote() {
        System.out.print("Student ID : ");
        String studentId = scanner.nextLine();

        System.out.print("Election ID : ");
        String electionId = scanner.nextLine();

        boolean result = votingService.checkStudentVoted(studentId, electionId);

        if(result) {
            System.out.println("Student already voted.");
        } else {
            System.out.println("Student has not voted.");
        }
    }

}
