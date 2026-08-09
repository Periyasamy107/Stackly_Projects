package util;

import java.util.regex.Pattern;

public class ValidationUtilEmp {


    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }


    public static boolean isValidName(String name) {
        return name != null && name.matches("[A-Za-z ]+");
    }


    public static boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && Pattern.matches(emailPattern, email);
    }


    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("[0-9]{10}");
    }

}