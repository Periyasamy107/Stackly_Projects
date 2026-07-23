package model;


import enums.Gender;
import enums.Role;

public class Student extends Person {

    private String department;
    private int year;
    private boolean voted;
    private Role role;

    public Student() {

        role = Role.STUDENT;
        voted = false;

    }

    public Student(String id,
                   String name,
                   Gender gender,
                   String email,
                   String mobileNumber,
                   String password,
                   String department,
                   int year) {

        super(id,
                name,
                gender,
                email,
                mobileNumber,
                password);

        this.department = department;
        this.year = year;
        this.role = Role.STUDENT;
        this.voted = false;

    }

    public String getDepartment() {

        return department;

    }

    public void setDepartment(String department) {

        this.department = department;

    }

    public int getYear() {

        return year;

    }

    public void setYear(int year) {

        this.year = year;

    }

    public boolean isVoted() {

        return voted;

    }

    public void setVoted(boolean voted) {

        this.voted = voted;

    }

    public Role getRole() {

        return role;

    }

    public void setRole(Role role) {

        this.role = role;

    }

    public String toFileString() {

        return getId() + "|" +
                getName() + "|" +
                getGender() + "|" +
                getEmail() + "|" +
                getMobileNumber() + "|" +
                getPassword() + "|" +
                department + "|" +
                year + "|" +
                voted + "|" +
                role;

    }

    public static Student fromFileString(String line) {

        String[] data = line.split("\\|");

        Student student = new Student();

        student.setId(data[0]);
        student.setName(data[1]);
        student.setGender(
                Gender.valueOf(data[2])
        );
        student.setEmail(data[3]);
        student.setMobileNumber(data[4]);
        student.setPassword(data[5]);
        student.setDepartment(data[6]);
        student.setYear(
                Integer.parseInt(data[7])
        );
        student.setVoted(
                Boolean.parseBoolean(data[8])
        );
        student.setRole(
                Role.valueOf(data[9])
        );

        return student;

    }

    @Override
    public String toString() {

        return "Student ID      : " + getId() +
                "\nName            : " + getName() +
                "\nGender          : " + getGender() +
                "\nDepartment      : " + department +
                "\nYear            : " + year +
                "\nEmail           : " + getEmail() +
                "\nMobile Number   : " + getMobileNumber() +
                "\nRole            : " + role +
                "\nAlready Voted   : " + voted;

    }

}
