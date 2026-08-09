package main;

import exception.*;
import util.DBConnection;
import util.DatabaseInitializer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import  helper.EmployeeMenuHelper;
import  helper.ManagerMenuHelper;

public class EmployeeMain {

    public static void main(String[] args) throws SQLException,
            ManagerNotFoundException, EmployeeNotFoundException, EmployeeAlreadyExistsException,
            InvalidLeaveRequestException, InsufficientLeaveBalanceException, LeaveRequestNotFoundException {

        System.out.println("==============================================");
        System.out.println("      EMPLOYEE LEAVE MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        DatabaseInitializer.initializeDatabase();

        System.out.println("\nApplication database initialization completed.");

        System.out.println("\n==============================================\n");


        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DBConnection.getConnection()) {

            while (true){

                System.out.println("1. Manager Menu");
                System.out.println("2. Employee Menu");
                System.out.println("3. Exit");
                System.out.print("Please enter the choice from 1 to 3 only : ");

                int choice;

                try{
                    choice = Integer.parseInt(scanner.nextLine());
                } catch(NumberFormatException e) {
                    System.out.println("======================================================");
                    System.out.println("\nPlease enter the number from 1 to 3 only.\n");
                    System.out.println("======================================================");
                    continue;
                }

                switch (choice) {
                    case 1:
                        ManagerMenuHelper.managerMenu(connection, scanner);
                        break;
                    case 2:
                        EmployeeMenuHelper.employeeMenu(connection, scanner);
                        break;
                    case 3:
                        System.out.println("=====================================================");
                        System.out.println("\nApplication closed, exit the program.....\n");
                        System.out.println("=====================================================");
                        return;
                    default:
                        System.out.println("=====================================================");
                        System.out.println("\nPlease the main option from 1 to 3.\n");
                        System.out.println("=====================================================");
                }
            }

        }


    }

}
