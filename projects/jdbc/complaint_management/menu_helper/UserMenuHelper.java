package menu_helper;

import menu.UserMenu;
import model.User;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class UserMenuHelper {

    private static final UserService userService =
            new UserService();

    public static void userMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            try{
                switch (UserMenu.showUserMenu(scanner)) {
                    case 1:
                        registerUser(scanner);
                        break;
                    case 2:
                        displayUsers(userService.viewAllUsers());
                        break;
                    case 3:
                        searchUser(scanner);
                        break;
                    case 4:
                        updateUser(scanner);
                        break;
                    case 5:
                        deactivateUser(scanner);
                        break;
                    case 6:
                        displayUsers(userService.viewActiveUsers());
                        break;
                    case 7:
                        displayUsers(userService.viewDeactivatedUsers());
                        break;
                    case 8:
                        back = true;
                        break;
                }
            } catch (Exception e) {
                System.out.println("\nError in user menu : " + e.getMessage());
            }
        }
    }


    private static void registerUser(Scanner scanner) {

        User user = new User();

        System.out.print("Enter User ID (String) : ");
        user.setUserId(scanner.nextLine());

        System.out.print("Enter Name (String) : ");
        user.setName(scanner.nextLine());

        System.out.print("Enter Phone (String) (10 digits) : ");
        user.setPhone(scanner.nextLine());

        System.out.print("Enter Email (String) : ");
        user.setEmail(scanner.nextLine());

        System.out.print("Enter Address (String) : ");
        user.setAddress(scanner.nextLine());

        user.setActive(true);

        if (userService.registerUser(user)) {
            System.out.println("\nUser registered successfully.\n");
        } else {
            System.out.println("\nUser registration failed.\n");
        }
    }

    private static void searchUser(Scanner scanner) {

        System.out.print("Enter User ID (String) : ");
        String userId = scanner.nextLine();

        User user = userService.searchUserById(userId);

        if (user == null) {
            System.out.println("\nUser not found.\n");
            return;
        }

        System.out.println("------------------------------------------");
        System.out.println("User ID  : " + user.getUserId());
        System.out.println("Name     : " + user.getName());
        System.out.println("Phone    : " + user.getPhone());
        System.out.println("Email    : " + user.getEmail());
        System.out.println("Address  : " + user.getAddress());
        System.out.println("Active   : " + user.isActive());
        System.out.println("------------------------------------------");
    }

    private static void updateUser(Scanner scanner) {

        System.out.print("Enter User ID (String) : ");
        String userId = scanner.nextLine();

        User user = userService.searchUserById(userId);

        if (user == null) {
            System.out.println("\nUser not found.\n");
            return;
        }

        System.out.print("Enter New Name (String) : ");
        user.setName(scanner.nextLine());

        System.out.print("Enter New Phone (String) (10 digits) : ");
        user.setPhone(scanner.nextLine());

        System.out.print("Enter New Email (String) : ");
        user.setEmail(scanner.nextLine());

        System.out.print("Enter New Address (String) : ");
        user.setAddress(scanner.nextLine());

        if (userService.updateUser(user)) {
            System.out.println("\nUser updated successfully.\n");
        } else {
            System.out.println("\nUser update failed.\n");
        }
    }

    private static void deactivateUser(Scanner scanner) {

        System.out.print("Enter User ID (String) : ");
        String userId = scanner.nextLine();

        if (userService.deactivateUser(userId)) {
            System.out.println("\nUser deactivated successfully.\n");
        } else {
            System.out.println("\nUser deactivation failed.\n");
        }
    }

    private static void displayUsers(List<User> users) {

        if (users.isEmpty()) {
            System.out.println("\nNo users found.\n");
            return;
        }

        users.forEach(user -> {

            System.out.println("------------------------------------------");
            System.out.println("User ID  : " + user.getUserId());
            System.out.println("Name     : " + user.getName());
            System.out.println("Phone    : " + user.getPhone());
            System.out.println("Email    : " + user.getEmail());
            System.out.println("Address  : " + user.getAddress());
            System.out.println("Active   : " + user.isActive());
        });

        System.out.println("------------------------------------------");
    }



}
