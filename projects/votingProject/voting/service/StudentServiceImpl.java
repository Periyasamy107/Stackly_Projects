package service;


import enums.AuditAction;
import enums.AuditModule;
import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import interfaces.AuditService;
import interfaces.StudentService;
import model.Student;
import repository.StudentRepository;
import repositoryImpl.StudentRepositoryImpl;
import util.IDGenerator;
import validation.ValidationUtil;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private StudentRepository repository;
    private AuditService auditService;

    public StudentServiceImpl() {
        repository = new StudentRepositoryImpl();
        auditService = new AuditServiceImpl();
    }


    @Override
    public boolean addStudent(Student student) throws UserAlreadyExistsException, InputValidationException {

        student.setId(IDGenerator.generateId("S"));

        if(ValidationUtil.isValidMobile(student.getMobileNumber())) {
            student.setMobileNumber(student.getMobileNumber());
        } else {
            throw new InputValidationException("Please enter a valid mobile number!!");
        }

        if(ValidationUtil.isValidPassword(student.getPassword())) {
            student.setPassword(student.getPassword());
        } else {
            throw new InputValidationException("Password minimum length is 3.");
        }

        if(repository.existsById(student.getId())) {
            throw new UserAlreadyExistsException("Student ID already exists.");
        }

        if(repository.existsByEmail(student.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists.");
        }

        auditService.log(
                AuditModule.STUDENT,
                AuditAction.ADD,
                "Added Student : " + student.getId()
        );
        return repository.save(student);
    }

    @Override
    public boolean updateStudent(Student student) throws UserNotFoundException {
        if(!repository.existsById(student.getId())) {
            throw new UserNotFoundException("Student not found.");
        }

        auditService.log(
                AuditModule.STUDENT,
                AuditAction.UPDATE,
                "Updated Student : " + student.getId()
        );
        return repository.update(student);
    }

    @Override
    public boolean deleteStudent(String studentId) throws UserNotFoundException {
        if(!repository.existsById(studentId)) {
            throw new UserNotFoundException("Student not found.");
        }

        auditService.log(
                AuditModule.STUDENT,
                AuditAction.DELETE,
                "Deleted Student : " + studentId
        );
        return repository.delete(studentId);
    }

    @Override
    public Student searchStudentById(String studentId) throws UserNotFoundException {
        Student student = repository.findById(studentId);
        if(student == null) {
            throw new UserNotFoundException("Student not found.");
        }
        return student;
    }


    @Override
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

}