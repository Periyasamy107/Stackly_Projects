package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IssueRecord {

    private Book book;
    private Student1 student1;

    private LocalDate issueDate;
    private LocalDate returnDate;

    private final double FINE_PER_DAY = 10;

    public IssueRecord(Book book, Student1 student1, LocalDate issueDate) {
        this.book = book;
        this.student1 = student1;
        this.issueDate = issueDate;
    }

    public Book getBook() {
        return book;
    }

    public Student1 getStudent() {
        return student1;
    }

    public void setReturnDate() {
        this.returnDate = LocalDate.now();
    }

    public double calculateFine() {
        if(returnDate==null) {
            return 0;
        }
        long days = ChronoUnit.DAYS.between(issueDate, returnDate);
        if(days>3) {
            return (days-3) * FINE_PER_DAY;
        }
        return 0;
    }

    @Override
    public String toString() {
        return book.getTitle() + " issued to " + student1.getStudentName();
    }

}
