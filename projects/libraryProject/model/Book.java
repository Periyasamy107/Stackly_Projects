package model;

import enums.BookCategory;

public class Book implements Comparable<Book>{

    private String bookId;
    private String title;
    private String author;
    private BookCategory category;
    private boolean isAvailable;

    private static int totalBooks = 0;

    private final String LIBRARY_NAME = "Kamaraj Library";

    public Book() {}

    public Book(String bookId, String title, String author, BookCategory category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;

        totalBooks++;

    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public static int getTotalBooks() {
        return totalBooks;
    }

    @Override
    public int compareTo(Book book) {
        return this.title.compareTo(book.title);
    }

    @Override
    public String toString() {
        return "Book ID : " + bookId +
                "\nTitle : " + title +
                "\nAuthor : " + author +
                "\nCategory : " + category +
                "\nAvaliable : " + isAvailable;
    }

}
