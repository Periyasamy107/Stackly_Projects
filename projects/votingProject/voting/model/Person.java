package model;

import enums.Gender;

public class Person {

    private String id;
    private String name;
    private Gender gender;
    private String email;
    private String mobileNumber;
    private String password;

    public Person() {

    }

    public Person(String id,
                  String name,
                  Gender gender,
                  String email,
                  String mobileNumber,
                  String password) {

        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;

    }

    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public Gender getGender() {

        return gender;

    }

    public void setGender(Gender gender) {

        this.gender = gender;

    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public String getMobileNumber() {

        return mobileNumber;

    }

    public void setMobileNumber(String mobileNumber) {

        this.mobileNumber = mobileNumber;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

}