package model;

import enums.Gender;

import java.time.LocalDate;

public class Admin extends Person {

    private String adminId;
    private String username;
    private String passwordHash;
    private String role;
    private LocalDate joiningDate;
    private String address;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toFileString() {

        return adminId + "|" +
                getName() + "|" +
                getGender() + "|" +
                getEmail() + "|" +
                getMobileNumber() + "|" +
                getAddress() + "|" +
                username + "|" +
                passwordHash + "|" +
                role + "|" +
                joiningDate;

    }


    public static Admin fromFileString(String line) {

        String[] data = line.split("\\|");

        Admin admin = new Admin();

        admin.setAdminId(data[0]);
        admin.setName(data[1]);
        admin.setGender(Gender.valueOf(data[2]));
        admin.setEmail(data[3]);
        admin.setMobileNumber(data[4]);
        admin.setAddress(data[5]);

        admin.setUsername(data[6]);
        admin.setPasswordHash(data[7]);
        admin.setRole(data[8]);
        if(!data[9].equals("null")) {

            admin.setJoiningDate(
                    LocalDate.parse(data[9])
            );

        }

        return admin;

    }


    @Override
    public String toString() {

        return "Admin ID        : " + adminId +
                "\nName            : " + getName() +
                "\nGender          : " + getGender() +
                "\nEmail           : " + getEmail() +
                "\nMobile Number   : " + getMobileNumber() +
                "\nUsername        : " + username +
                "\nRole            : " + role +
                "\nJoining Date    : " + joiningDate +
                "\nAddress         : " + getAddress();

    }

}
