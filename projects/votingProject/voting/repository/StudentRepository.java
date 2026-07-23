package repository;


import interfaces.Repository;
import model.Student;

public interface StudentRepository extends Repository<Student> {

    Student findByEmail(String email);

    boolean existsById(String studentId);

    boolean existsByEmail(String email);

}
