package database;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        loadDriver();
        DatabaseManagerHotel databaseManagerHotel = new DatabaseManagerHotel();
        databaseManagerHotel.initialize();
    }

    private static void loadDriver() {
        try{
            System.out.println("\nLoading JDBC Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded succesfully...\n");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load JDBC Driver\n");
            System.out.println(e.getMessage());
        }
    }

}
