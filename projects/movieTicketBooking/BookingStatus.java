public enum BookingStatus {

    BOOKED("Ticket Booked"),
    CANCELLED("Ticket Cancelled");

    private String description;

    BookingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

}
