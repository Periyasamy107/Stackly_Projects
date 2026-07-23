package interfaces;

import java.util.List;

import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import model.Student;

public interface StudentService {

    boolean addStudent(Student student) throws UserAlreadyExistsException, InputValidationException;

    boolean updateStudent(Student student) throws UserNotFoundException;

    boolean deleteStudent(String studentId) throws UserNotFoundException;

    Student searchStudentById(String studentId) throws UserNotFoundException;

    List<Student> getAllStudents();

}