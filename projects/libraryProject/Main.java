import enums.BookCategory;
import model.Book;
import model.Student1;
import repository.LibraryRepository;
import service.LibraryService;
import service.LibraryServiceImpl;

public class Main {

    public static void main(String[] args) throws Exception {

        LibraryRepository repository = new LibraryRepository();
        LibraryService service = new LibraryServiceImpl(repository);

        Book book1 = new Book("B01", "Java Programming", "Sam", BookCategory.JAVA);
        Book book2 = new Book("B02", "RDBMS Learning", "Karthi", BookCategory.DATABASE);
        Book book3 = new Book("B03", "Maths", "Mari", BookCategory.MATHEMATICS);
        Book book4 = new Book("B04", "Human Body Study", "Himal", BookCategory.SCIENCE);
        Book book5 = new Book("B05", "Chola History", "Gambhair" , BookCategory.HISTORY);
        Book book6 = new Book("B06", "Advanced Java", "Suresh" , BookCategory.JAVA);


        service.addBook(book1);
        service.addBook(book2);
        service.addBook(book3);
        service.addBook(book4);
        service.addBook(book5);
        service.addBook(book6);

        System.out.println();
        System.out.println("============ BOOKS LIST =============");
        service.displayBooks();
        System.out.println();

        Student1 student1 = new Student1("S01", "Sasthri", "sasthri12@gmail.com");

        service.issueBook("B02", student1);

        double fine = service.returnBook("B02");

        System.out.println("Fine : " + fine);

        System.out.println("Total Books : " + Book.getTotalBooks());

        System.out.println();
        System.out.println("========== Searching books ==========");
        for(Book book : service.searchBook("Java")) {
            System.out.println(book);
            System.out.println("---------------------------------");
        }

    }

}
