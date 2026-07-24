import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {

    private static int ticketCounter = 1000;
    private String ticketNumber;
    private Customer customer;
    private int row;
    private int col;
    private BookingStatus bookingStatus;
    private LocalDateTime bookingTime;

    public Ticket() {}

    public Ticket(Customer customer, int row, int col, BookingStatus bookingStatus) {
        this.ticketNumber = "T" + (++ticketCounter);
        this.customer = customer;
        this.row = row;
        this.col = col;
        this.bookingStatus = bookingStatus;
        this.bookingTime = LocalDateTime.now();
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void displayTicket() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("\n---------------------------------");
        System.out.println("         Movie Ticket");
        System.out.println("---------------------------------");
        System.out.println("Ticket Number : " + ticketNumber);
        System.out.println("Customer Name : " + customer.getCustomerName());
        System.out.println("Seat          : " + (char) ('A' + row) + (col + 1));
        System.out.println("Booking Time  : " + bookingTime.format(formatter));
        System.out.println("Status        : " + bookingStatus);
        System.out.println("---------------------------------");
    }

    @Override
    public String toString() {
        return "Ticket {" +
                "ticket number = " + ticketNumber +
                ", customer = " + customer +
                ", row = " + row +
                ", col = " + col +
                ", booking status = " + bookingStatus +
                ", booking time = " + bookingTime + " }";
    }

}
