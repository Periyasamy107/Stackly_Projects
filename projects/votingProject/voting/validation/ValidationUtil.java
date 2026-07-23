package validation;

public class ValidationUtil {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


    public static boolean isValidEmail(String email) {
        if(isEmpty(email)) {
            return false;
        }

        return email.contains("@") && email.contains(".");
    }


    public static boolean isValidMobile(String mobile) {
        if(isEmpty(mobile)) {
            return  false;
        }
        return mobile.matches("[0-9]{10}");
    }


    public static boolean isValidPassword(String password) {
        if(isEmpty(password)) {
            return false;
        }
        return password.length() >= 3;
    }


}
