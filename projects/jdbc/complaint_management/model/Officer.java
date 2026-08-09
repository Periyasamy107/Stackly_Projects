package model;

public class Officer {

    private String officerId;
    private String name;
    private String department;
    private String phone;
    private String email;
    private boolean active;

    public Officer() {
    }

    public Officer(String officerId, String name, String department, String phone, String email, boolean active) {
        this.officerId = officerId;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.active = active;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Officer{" +
                "officerId='" + officerId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
