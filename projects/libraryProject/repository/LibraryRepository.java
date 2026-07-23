package repository;

import model.Book;
import model.IssueRecord;
import model.Student1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LibraryRepository {

    private ArrayList<Book> books = new ArrayList<>();
    private HashMap<String, IssueRecord> issuedBooks = new HashMap<>();
    private HashSet<Student1> students = new HashSet<>();

    public ArrayList<Book> getBooks() {
        return books;
    }

    public HashMap<String, IssueRecord> getIssuedBooks() {
        return issuedBooks;
    }

    public HashSet<Student1> getStudents() {
        return students;
    }

}
