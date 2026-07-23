package service;

import exception.BookAlreadyIssuedException;
import exception.BookNotFoundException;
import model.Book;
import model.Student1;

import java.util.List;

public interface LibraryService {

    void addBook(Book book);
    List<Book> searchBook(String keyword);
    void issueBook(String bookId, Student1 student) throws BookNotFoundException, BookAlreadyIssuedException;
    double returnBook(String bookId) throws BookNotFoundException;
    void displayBooks();

}
