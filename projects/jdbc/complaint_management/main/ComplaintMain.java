package main;

import database.DatabaseManager;
import menu.MainMenu;

import java.util.Scanner;

import menu_helper.*;

public class ComplaintMain {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        DatabaseManager.initializeDatabase();

        boolean exit = false;

        while (!exit) {
            try{
                switch (MainMenu.showMainMenu(scanner)) {
                    case 1:
                        UserMenuHelper.userMenu(scanner);
                        break;
                    case 2:
                        OfficerMenuHelper.officerMenu(scanner);
                        break;
                    case 3:
                        ComplaintMenuHelper.complaintMenu(scanner);
                        break;
                    case 4:
                        AssignmentMenuHelper.assignmentMenu(scanner);
                        break;
                    case 5:
                        StatusMenuHelper.statusMenu(scanner);
                        break;
                    case 6:
                        ResolutionMenuHelper.resolutionMenu(scanner);
                        break;
                    case 7:
                        ReportMenuHelper.reportMenu(scanner);
                        break;
                    case 8:
                        exit = true;
                        break;
                    default:
                       System.out.println("\nInvalid choice for Menu.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number for main menu.\n");
            } catch (Exception e) {
                System.out.println("\nError from ComplaintMain : " + e.getMessage());
            }
        }
        scanner.close();
    }





}
