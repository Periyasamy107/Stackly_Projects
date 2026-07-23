package service;

import exception.BookAlreadyIssuedException;
import exception.BookNotFoundException;
import model.Book;
import model.IssueRecord;
import model.Student1;
import repository.LibraryRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryServiceImpl implements LibraryService{

    private LibraryRepository repository;
    public LibraryServiceImpl(LibraryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addBook(Book book) {
        repository.getBooks().add(book);
    }

    @Override
    public List<Book> searchBook(String keyword) {
        return repository.getBooks()
                .stream()
                .filter(book -> book.getTitle().contains(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public void issueBook(String bookId, Student1 student) throws BookNotFoundException, BookAlreadyIssuedException {

        Book book = repository.getBooks()
                .stream()
                .filter(b -> b.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));

        if(!book.isAvailable()) {
            throw new BookAlreadyIssuedException("Book already issued.");
        }

        IssueRecord record = new IssueRecord(book, student, LocalDate.now().minusDays(30));

        repository.getIssuedBooks().put(bookId, record);

        book.setAvailable(false);

    }

    @Override
    public double returnBook(String bookId) throws BookNotFoundException {

        IssueRecord record = repository.getIssuedBooks().remove(bookId);

        if(record == null) {
            throw new BookNotFoundException("Issue record not found");
        }

        record.setReturnDate();

        record.getBook().setAvailable(true);

        return record.calculateFine();

    }


    @Override
    public void displayBooks() {
        List<Book> books = repository.getBooks();
        Collections.sort(books);
        books.forEach(book -> {
            System.out.println(book);
            System.out.println("---------------------------------");
        });
    }


}
