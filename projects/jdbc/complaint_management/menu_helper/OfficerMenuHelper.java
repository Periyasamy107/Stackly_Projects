package menu_helper;

import menu.OfficerMenu;
import model.Officer;
import service.OfficerService;

import java.util.List;
import java.util.Scanner;

public class OfficerMenuHelper {

    private static final OfficerService officerService =
            new OfficerService();

    public static void officerMenu(Scanner scanner) {

        boolean back = false;

        while (!back) {

            try {

                switch (OfficerMenu.showOfficerMenu(scanner)) {

                    case 1:
                        registerOfficer(scanner);
                        break;

                    case 2:
                        displayOfficers(
                                officerService.viewAllOfficers()
                        );
                        break;

                    case 3:
                        searchOfficer(scanner);
                        break;

                    case 4:
                        updateOfficer(scanner);
                        break;

                    case 5:
                        deactivateOfficer(scanner);
                        break;

                    case 6:
                        displayOfficers(
                                officerService.viewActiveOfficers()
                        );
                        break;

                    case 7:
                        back = true;
                        break;

                    default:
                        System.out.println("\nInvalid choice from officer manu.\n");
                }

            } catch (Exception e) {

                System.out.println("\nError from officer menu : " + e.getMessage());
            }
        }
    }


    private static void registerOfficer(Scanner scanner) {

        Officer officer = new Officer();

        System.out.print("Enter Officer ID (String) : ");
        officer.setOfficerId(scanner.nextLine());

        System.out.print("Enter Name (String) : ");
        officer.setName(scanner.nextLine());

        System.out.print("Enter Department (String) : ");
        officer.setDepartment(scanner.nextLine());

        System.out.print("Enter Phone (String) (10 digits): ");
        officer.setPhone(scanner.nextLine());

        System.out.print("Enter Email (String) : ");
        officer.setEmail(scanner.nextLine());

        officer.setActive(true);

        if (officerService.registerOfficer(officer)) {
            System.out.println("\nOfficer registered successfully.\n");
        } else {
            System.out.println("\nOfficer registration failed.\n");
        }
    }


    private static void searchOfficer(Scanner scanner) {

        System.out.print("Enter Officer ID (String) : ");
        String officerId = scanner.nextLine();

        Officer officer =
                officerService.searchOfficerById(officerId);

        if (officer == null) {
            System.out.println("\nOfficer not found.\n");
            return;
        }

        printOfficer(officer);
    }

    private static void updateOfficer(Scanner scanner) {

        System.out.print("Enter Officer ID (String) : ");
        String officerId = scanner.nextLine();

        Officer officer =
                officerService.searchOfficerById(officerId);

        if (officer == null) {
            System.out.println("\nOfficer not found.\n");
            return;
        }

        System.out.print("Enter New Name (String) : ");
        officer.setName(scanner.nextLine());

        System.out.print("Enter New Department (String) : ");
        officer.setDepartment(scanner.nextLine());

        System.out.print("Enter New Phone (String) : ");
        officer.setPhone(scanner.nextLine());

        System.out.print("Enter New Email (String) : ");
        officer.setEmail(scanner.nextLine());

        if (officerService.updateOfficer(officer)) {
            System.out.println("\nOfficer updated successfully.\n");
        } else {
            System.out.println("\nOfficer update failed.\n");
        }
    }

    private static void deactivateOfficer(Scanner scanner) {

        System.out.print("Enter Officer ID (String) : ");
        String officerId = scanner.nextLine();

        if (officerService.deactivateOfficer(officerId)) {
            System.out.println("\nOfficer deactivated successfully.\n");
        } else {
            System.out.println("\nOfficer deactivation failed.\n");
        }
    }

    private static void displayOfficers(List<Officer> officers) {

        if (officers.isEmpty()) {
            System.out.println("\nNo officers found.\n");
            return;
        }

        officers.forEach(OfficerMenuHelper::printOfficer);
    }

    private static void printOfficer(Officer officer) {

        System.out.println("------------------------------------------");
        System.out.println("Officer ID  : " + officer.getOfficerId());
        System.out.println("Name        : " + officer.getName());
        System.out.println("Department  : " + officer.getDepartment());
        System.out.println("Phone       : " + officer.getPhone());
        System.out.println("Email       : " + officer.getEmail());
        System.out.println("Active      : " + officer.isActive());
        System.out.println("------------------------------------------");
    }


}
