import java.util.Scanner;

public class MovieTicketBooking {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Theatre theatre = new Theatre(
                "Samy Theatre",
                "Jana Nayakan",
                200.00,
                5,
                5
        );

        int choice;

        do{

            System.out.println("\n=====================================");
            System.out.println("   MOVIE TICKET BOOKING SYSTEM");
            System.out.println("=====================================");
            System.out.println("1. Show Available seats");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Display Booked Tickets");
            System.out.println("5. Display Summary");
            System.out.println("6. Exit ");
            System.out.println("=====================================");

            System.out.print("Enter your choice : ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    theatre.displaySeats();
                    break;
                case 2:
                    theatre.bookTicket(scanner);
                    break;
                case 3:
                    theatre.cancelTicket(scanner);
                    break;
                case 4:
                    theatre.displayBookedTickets();
                    break;
                case 5:
                    theatre.displaySummary();
                    break;
                case 6:
                    System.out.println("\nThank you.");
                    System.out.println("Visit Again");
                    break;
                default:
                    System.out.println("\nInvalid Choice.");
            }

        } while(choice != 6);

        scanner.close();
    }

}
