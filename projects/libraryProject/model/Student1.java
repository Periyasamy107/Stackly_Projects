package model;

public class Student1 {

    private String studentId;
    private String studentName;
    private String email;

    public Student1(String studentId, String studentName, String email) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return studentId + " - " + studentName;
    }

}
