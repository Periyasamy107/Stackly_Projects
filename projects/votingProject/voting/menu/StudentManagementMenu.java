package menu;

import java.util.List;
import java.util.Scanner;

import enums.Gender;
import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import interfaces.StudentService;
import model.Student;
import service.StudentServiceImpl;

public class StudentManagementMenu {

    private Scanner scanner;

    private StudentService studentService;

    public StudentManagementMenu() {

        scanner = new Scanner(System.in);

        studentService = new StudentServiceImpl();

    }

    public void showMenu() throws UserAlreadyExistsException, UserNotFoundException, InputValidationException {

        int choice;

        do {

            System.out.println("\n==========================================");
            System.out.println("      STUDENT MANAGEMENT");
            System.out.println("==========================================");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Back");
            System.out.print("Enter Choice : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch(choice) {

                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    searchStudent();
                    break;
                case 5:
                    displayStudents();
                    break;
                case 6:
                    break;

                default:

                    System.out.println("Invalid Choice");
            }
        }
        while(choice != 6);
    }


    private void addStudent() throws UserAlreadyExistsException, InputValidationException {

        Student student = new Student();

        System.out.print("Student Name : ");
        student.setName(scanner.nextLine());

        System.out.print("Gender (MALE/FEMALE/OTHER) : ");
        student.setGender(Gender.valueOf(scanner.nextLine().toUpperCase()));

        System.out.print("Email : ");
        student.setEmail(scanner.nextLine());

        System.out.print("Mobile Number : ");
        student.setMobileNumber(scanner.nextLine());

        System.out.print("Password : ");
        student.setPassword(scanner.nextLine());

        System.out.print("Department (CSE/IT/ECE/EEE/MECH/CIVIL) : ");
        student.setDepartment(scanner.nextLine());

        System.out.print("Year : ");
        student.setYear(Integer.parseInt(scanner.nextLine()));

        if(studentService.addStudent(student)) {
            System.out.println("Student Added Successfully.");
        }
    }


    private void updateStudent() throws UserNotFoundException {

        System.out.print("Student ID : ");

        String id = scanner.nextLine();

        Student student = studentService.searchStudentById(id);

        System.out.print("New Name : ");
        student.setName(scanner.nextLine());

        System.out.print("New Email : ");
        student.setEmail(scanner.nextLine());

        System.out.print("New Mobile Number : ");
        student.setMobileNumber(scanner.nextLine());

        System.out.print("New Department (CSE/IT/ECE/EEE/MECH/CIVIL) : ");
        student.setDepartment(scanner.nextLine());

        System.out.print("New Year : ");
        student.setYear(Integer.parseInt(scanner.nextLine()));

        studentService.updateStudent(student);

        System.out.println("Student Updated Successfully.");
    }


    private void deleteStudent() throws UserNotFoundException {
        System.out.print("Student ID : ");
        String id = scanner.nextLine();
        studentService.deleteStudent(id);
        System.out.println("Student Deleted Successfully.");
    }


    private void searchStudent() throws UserNotFoundException {

        System.out.print("Student ID : ");
        String id = scanner.nextLine();
        Student student = studentService.searchStudentById(id);
        System.out.println();
        System.out.println(student);

    }


    private void displayStudents() {

        List<Student> students = studentService.getAllStudents();

        if(students.isEmpty()) {
            System.out.println("No Students Found.");
            return;
        }

        for(Student student : students) {
            System.out.println("-----------------------------------");
            System.out.println(student);
        }
    }

}