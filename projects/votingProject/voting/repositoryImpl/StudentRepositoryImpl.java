package repositoryImpl;


import file.StudentFileManager;
import model.Student;
import repository.StudentRepository;

import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {

    private StudentFileManager fileManager;

    public StudentRepositoryImpl() {
        fileManager = new StudentFileManager();
    }

    @Override
    public boolean save(Student student) {
        List<Student> students = fileManager.load();
        students.add(student);
        fileManager.save(students);
        return true;
    }

    @Override
    public boolean update(Student student) {
        List<Student> students = fileManager.load();
        for(int i = 0; i < students.size(); i++) {
            if(students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                fileManager.save(students);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String studentId) {
        List<Student> students = fileManager.load();
        boolean removed = students.removeIf(student -> student.getId().equals(studentId));
        if(removed) {
            fileManager.save(students);
        }
        return removed;
    }

    @Override
    public Student findById(String studentId) {
        List<Student> students = fileManager.load();
        for(Student student : students) {
            if(student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public Student findByEmail(String email) {
        List<Student> students = fileManager.load();
        for(Student student : students) {
            if(student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public List<Student> findAll() {
        return fileManager.load();
    }

    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public boolean existsById(String studentId) {
        return findById(studentId) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

}
