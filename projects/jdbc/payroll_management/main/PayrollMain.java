package main;


import database.DatabaseManagerPayroll;
import menu.PayrollMenu;

public class PayrollMain {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println("        PAYROLL MANAGEMENT SYSTEM");
        System.out.println("==================================================");
        System.out.println("Starting application...");
        System.out.println();

        // Create database
        DatabaseManagerPayroll.createPayrollDatabase();

        // Create required tables
        DatabaseManagerPayroll.createPayrollTables();

        System.out.println();
        System.out.println("Database initialization completed.");
        System.out.println();

        // Start application menu
        PayrollMenu payrollMenu = new PayrollMenu();

        payrollMenu.start();
    }
}
