package menu;


import enums.Gender;
import exception.InputValidationException;
import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import interfaces.AdminService;
import model.Admin;
import service.AdminServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class AdminManagementMenu {

    private Scanner scanner;
    private AdminService adminService;

    public AdminManagementMenu() {
        scanner = new Scanner(System.in);
        adminService = new AdminServiceImpl();
    }

    public void showMenu() {

        int choice;

        do {

            System.out.println();
            System.out.println("\n========== ADMIN MANAGEMENT ==========");
            System.out.println("1. Register Admin");
            System.out.println("2. Update Admin");
            System.out.println("3. Delete Admin");
            System.out.println("4. Search Admin");
            System.out.println("5. Display All Admins");
            System.out.println("6. Back");
            System.out.print("Enter Choice : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {

                case 1:
                    registerAdmin();
                    break;

                case 2:
                    updateAdmin();
                    break;

                case 3:
                    deleteAdmin();
                    break;

                case 4:
                    searchAdmin();
                    break;

                case 5:
                    displayAdmins();
                    break;

                case 6:
                    break;

                default:
                    System.out.println("Invalid Choice.");

            }

        } while(choice != 6);

    }

    private void registerAdmin() {

        try {

            Admin admin = new Admin();

            System.out.print("Admin Name : ");
            admin.setName(scanner.nextLine());

            System.out.print("Gender (MALE/FEMALE/OTHER) : ");
            admin.setGender(
                    Gender.valueOf(
                            scanner.nextLine()
                                    .toUpperCase()
                    )
            );

            System.out.print("Email : ");
            admin.setEmail(scanner.nextLine());

            System.out.print("Mobile : ");
            admin.setMobileNumber(scanner.nextLine());

            System.out.print("Address : ");
            admin.setAddress(scanner.nextLine());

            System.out.print("Username : ");
            admin.setUsername(scanner.nextLine());

            System.out.print("Password : ");
            admin.setPasswordHash(scanner.nextLine());

            System.out.print("Role (ADMIN/STUDENT) : ");
            admin.setRole(scanner.nextLine());

            adminService.registerAdmin(admin);

            System.out.println("Admin Registered Successfully.");

        }
        catch(UserAlreadyExistsException | InputValidationException | NoSuchAlgorithmException exception) {

            System.out.println(exception.getMessage());

        }

    }

    private void updateAdmin() {

        try {

            System.out.print("Enter Admin ID : ");

            String id = scanner.nextLine();

            Admin admin = adminService.searchAdmin(id);

            System.out.print("New Email : ");

            admin.setEmail(scanner.nextLine());

            System.out.print("New Mobile : ");

            admin.setMobileNumber(scanner.nextLine());

            adminService.updateAdmin(admin);

            System.out.println("Admin Updated Successfully.");

        }
        catch(UserNotFoundException | InputValidationException exception) {

            System.out.println(exception.getMessage());

        }

    }

    private void deleteAdmin() {

        try {

            System.out.print("Enter Admin ID : ");

            String id = scanner.nextLine();

            adminService.deleteAdmin(id);

            System.out.println("Admin Deleted Successfully.");

        }
        catch(UserNotFoundException exception) {

            System.out.println(exception.getMessage());

        }

    }

    private void searchAdmin() {

        try {

            System.out.print("Enter Admin ID : ");

            String id = scanner.nextLine();

            Admin admin = adminService.searchAdmin(id);

            System.out.println(admin);

        }
        catch(UserNotFoundException exception) {

            System.out.println(exception.getMessage());

        }

    }

    private void displayAdmins() {

        List<Admin> admins = adminService.getAllAdmins();

        if(admins.isEmpty()) {

            System.out.println("No Admins Found.");

            return;

        }

        for(Admin admin : admins) {

            System.out.println("-----------------------------------");

            System.out.println(admin);

        }

    }

}
