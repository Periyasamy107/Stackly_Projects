import java.util.ArrayList;
import java.util.Scanner;

public class Theatre {

    private String theatreName;
    private String movieName;
    private double ticketPrice;
    private int totalRows;
    private int totalCols;
    private boolean[][] seats;
    private ArrayList<Ticket> bookedTickets;

    public Theatre(String theatreName, String movieName, double ticketPrice, int totalRows, int totalCols) {
        this.theatreName = theatreName;
        this.movieName = movieName;
        this.ticketPrice = ticketPrice;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
        seats = new boolean[totalRows][totalCols];
        bookedTickets = new ArrayList<>();
    }

    //-------------------------
    //  Display seat layout
    //-------------------------
    public void displaySeats() {
        System.out.println("\n======================================================");
        System.out.println("Theatre : " + theatreName);
        System.out.println("Movie   : " + movieName);
        System.out.println("Price   : Rs." + ticketPrice);
        System.out.println("======================================================");

        System.out.print("   ");

        for(int col=1; col<=totalCols; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();

        for(int row=0; row<totalRows; row++) {
            System.out.print((char) ('A' + row) + "  ");
            for(int col=0; col<totalCols; col++) {
                if(seats[row][col]){
                    System.out.print("X  ");
                } else {
                    System.out.print("O  ");
                }
            }
            System.out.println();
        }
        System.out.println("\nO = Available");
        System.out.println("X = Booked");
    }

    //---------------------------
    //  Book Ticket
    //---------------------------
    public void bookTicket(Scanner scanner) {
        System.out.println("\n=====================================");
        System.out.println("    Book Ticket");
        System.out.println("=====================================");

        System.out.print("Customer ID : ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Customer Name : ");
        String customerName = scanner.nextLine();

        System.out.print("Mobile Number : ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Seat Row (A-" + (char) ('A' + totalRows - 1) + ") : ");
        char rowChar = Character.toUpperCase(scanner.next().charAt(0));

        System.out.print("Seat Col (1-" + totalCols + ") : ");
        int col = scanner.nextInt();

        int row = rowChar - 'A';
        col--;

        if(row<0 || row>=totalRows || col<0 || col>=totalCols) {
            System.out.println("\nInvalid seat number.");
            return;
        }

        if(seats[row][col]) {
            System.out.println("\nSeat already booked.");
            return;
        }

        seats[row][col] = true;

        Customer customer = new Customer(customerId, customerName, mobileNumber);
        Ticket ticket = new Ticket(customer, row, col, BookingStatus.BOOKED);

        bookedTickets.add(ticket);

        System.out.println("\nTicket booked successfully.");

        ticket.displayTicket();

    }

    //---------------------------
    // Cancel Ticket
    //---------------------------
    public void cancelTicket(Scanner scanner) {
        if(bookedTickets.isEmpty()) {
            System.out.println("\nNo tickets available");
            return;
        }
        System.out.print("Enter ticket number : ");
        String ticketNumber = scanner.next();

        Ticket foundTicket = null;

        for(Ticket ticket : bookedTickets) {
            if(ticket.getTicketNumber().equalsIgnoreCase(ticketNumber)) {
                foundTicket = ticket;
                break;
            }
        }

        if(foundTicket == null) {
            System.out.println("\nTicket not found");
            return;
        }

        seats[foundTicket.getRow()][foundTicket.getCol()] = false;
        foundTicket.setBookingStatus(BookingStatus.CANCELLED);
        bookedTickets.remove(foundTicket);
        System.out.println("\nTicket Cancelled Successfully.");

    }

    //-----------------------------------
    // Display all booked tickets
    //-----------------------------------
    public void displayBookedTickets() {
        if(bookedTickets.isEmpty()) {
            System.out.println("\nNo bookings found");
            return;
        }
        System.out.println("\n========== Booked Tickets ==============");
        for(Ticket ticket : bookedTickets) {
            ticket.displayTicket();
        }
    }


    //---------------------------------
    // Total booked seats
    //---------------------------------
    public int getTotalBookedSeats() {
        int count = 0;
        for(int i=0; i<totalRows; i++) {
            for(int j=0; j<totalCols; j++) {
                if(seats[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }


    //-----------------------------
    // Total available seats
    //-----------------------------
    public int getTotalAvailableSeats() {
        return (totalRows * totalCols) - getTotalBookedSeats();
    }


    //------------------------------
    // Display seat summary
    //------------------------------
    public void displaySummary() {
        System.out.println("\n================ Summary ============");
        System.out.println("Total Seats : " + (totalRows * totalCols));
        System.out.println("Booked Seats : " + getTotalBookedSeats());
        System.out.println("Available Seats : " + getTotalAvailableSeats());
    }

}
