package lol;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

public class AdvocateAppointmentSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("==============================");
            System.out.println("Advocate Appointment System");
            System.out.println("==============================");
            System.out.println("1. Customer");
            System.out.println("2. Advocate");
            System.out.println("3. Appointment");
            System.out.println("4. Service");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (mainChoice) {
                case 1:
                    customerMenu(scanner);
                    break;
                case 2:
                    advocateMenu(scanner);
                    break;
                case 3:
                    // Implement Appointment menu
                    appointmentMenu(scanner);
                    break;
                case 4:
                    serviceMenu();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }

        scanner.close();
    }

    public static void customerMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("==============================");
            System.out.println("         Customer Menu");
            System.out.println("==============================");
            System.out.println("1. Register Customer");
            System.out.println("2. Modify Customer Details");
            System.out.println("3. Delete Customer Record");
            System.out.println("4. View Single Record");
            System.out.println("5. View All Records");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter customer Id");
                    String id = scanner.nextLine();
                    System.out.println("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter customer address: ");

                    String address = scanner.nextLine();
                    // scanner.next();
                    System.out.println("Enter customer contact information: ");

                    String number = scanner.nextLine();
                    // scanner.next();
                    // System.out.println(name+" "+address+" "+number);

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");

                        // Prepare the SQL insert statement
                        String insertQuery = "INSERT INTO customer (id, name, address, phone) VALUES (?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, address);
                        preparedStatement.setString(4, number);

                        // Execute the insert statement
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Customer registered successfully!");
                        } else {
                            System.out.println("Failed to register customer.");
                        }

                        // Close the database connection and resources
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        if (e instanceof SQLIntegrityConstraintViolationException) {
                            System.out.println("Please Enter a Unique Customer Id");
                        } else {
                            e.printStackTrace();

                        }
                    }
                    break;
                case 2:
                    // Implement modifying customer details logic

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");

                        String query = "SELECT * FROM customer";
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                while (resultSet.next()) {
                                    int id1 = resultSet.getInt("id");
                                    String name1 = resultSet.getString("name");
                                    String address1 = resultSet.getString("address");
                                    String phone1 = resultSet.getString("phone");
                                    // ... Retrieve other columns

                                    System.out.println("id: " + id1);
                                    System.out.println("name: " + name1);
                                    System.out.println("address: " + address1);
                                    System.out.println("phone: " + phone1);
                                    System.out.println("======================\n");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Ask the user for the record ID
                        System.out.print("Enter the record ID to update: ");
                        int recordIdToUpdate = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        // Ask the user for the column to update
                        System.out.print("Enter the column name to update: ");
                        String columnToUpdate = scanner.nextLine();

                        // Ask the user for the new value
                        System.out.print("Enter the new value: ");
                        String newValue = scanner.nextLine();
                        // System.out.println(newValue+" "+recordIdToUpdate);
                        // Update the specified column for the specific record
                        String updateQuery = "UPDATE customer SET " + columnToUpdate + " = ? WHERE id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, newValue);
                            updateStatement.setInt(2, recordIdToUpdate);
                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Record updated successfully.");
                            } else {
                                System.out.println("Record not found for update.");
                                break;
                            }
                        }

                        // Display the updated record
                        String selectQuery = "SELECT * FROM customer WHERE id = ?";
                        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                            selectStatement.setInt(1, recordIdToUpdate);
                            try (ResultSet resultSet = selectStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    int id1 = resultSet.getInt("id");
                                    String name1 = resultSet.getString("name");
                                    String address4 = resultSet.getString("address");
                                    String phone4 = resultSet.getString("phone");
                                    // ... Retrieve other columns

                                    System.out.println("ID: " + id1);
                                    System.out.println("Name: " + name1);
                                    System.out.println("Address: " + address4);
                                    System.out.println("Phone: " + phone4);
                                    System.out.println("======================");
                                }
                            }
                        }

                        // Close resources

                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        while (true) {
                            System.out
                                    .println("Enter the Id of the Customer you want to delete if don't then press 0:");
                            int id3 = scanner.nextInt();
                            if (id3 == 0)
                                break;
                            String findQuery = "SELECT * FROM customer WHERE id = ?";
                            try (PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
                                findStatement.setInt(1, id3);
                                try (ResultSet resultSet = findStatement.executeQuery()) {
                                    if (resultSet.next()) {
                                        int id4 = resultSet.getInt("id");
                                        String name4 = resultSet.getString("name");
                                        String address4 = resultSet.getString("address");
                                        String phone4 = resultSet.getString("phone");
                                        // ... Retrieve other columns

                                        System.out.println("Found Record:");
                                        System.out.println("ID: " + id4);
                                        System.out.println("Name: " + name4);
                                        System.out.println("Address: " + address4);
                                        System.out.println("Phone: " + phone4);
                                        System.out.println("======================");
                                        System.out.println("Are you sure you want to delete this record (1/0)?");
                                        int ch = scanner.nextInt();
                                        if (ch == 1) {
                                            // Delete a record
                                            String deleteQuery = "DELETE FROM customer WHERE id = ?";
                                            try (PreparedStatement deleteStatement = connection
                                                    .prepareStatement(deleteQuery)) {
                                                deleteStatement.setInt(1, id3);
                                                int rowsAffected = deleteStatement.executeUpdate();
                                                if (rowsAffected > 0) {
                                                    System.out.println("Record deleted successfully.");
                                                    break;
                                                }
                                            }
                                        } else {
                                            break;
                                        }

                                    } else {
                                        System.out.println("Record not found. Please enter a valid Customer Id");
                                    }
                                }
                            }

                        }
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Enter the Id of the Customer you want to see details of");
                    int id2 = scanner.nextInt();

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        String query = "SELECT * FROM customer where id=" + id2;
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                if (resultSet.next()) {
                                    int id1 = resultSet.getInt("id");
                                    String name1 = resultSet.getString("name");
                                    String address4 = resultSet.getString("address");
                                    String phone4 = resultSet.getString("phone");
                                    // ... Retrieve other columns

                                    System.out.println("ID: " + id1);
                                    System.out.println("Name: " + name1);
                                    System.out.println("Address: " + address4);
                                    System.out.println("Phone: " + phone4);
                                    System.out.println("======================");
                                } else {
                                    System.out.println("No record found. Please enter a valid customer Id");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Close the connection
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        String query = "SELECT * FROM customer";
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                while (resultSet.next()) {
                                    int id1 = resultSet.getInt("id");
                                    String name1 = resultSet.getString("name");
                                    String address4 = resultSet.getString("address");
                                    String phone4 = resultSet.getString("phone");
                                    // ... Retrieve other columns

                                    System.out.println("ID: " + id1);
                                    System.out.println("Name: " + name1);
                                    System.out.println("Address: " + address4);
                                    System.out.println("Phone: " + phone4);
                                    System.out.println("======================");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Close the connection
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public static void advocateMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("==============================");
            System.out.println("Advocate Menu");
            System.out.println("==============================");
            System.out.println("1. Register Advocate");
            System.out.println("2. Modify Advocate Details");
            System.out.println("3. Delete Advocate Record");
            System.out.println("4. View Single Record");
            System.out.println("5. View All Records");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter Advocate Id");
                    String id = scanner.nextLine();
                    // scanner.nextLine();
                    System.out.println("Enter Advocate name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter Advocate Phone Number: ");
                    String phoneNumber = scanner.nextLine();
                    // scanner.next();
                    System.out.println("Enter Service Type: ");
                    String service = scanner.nextLine();
                    // scanner.next();
                    // System.out.println(name+" "+address+" "+number);

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");

                        // Prepare the SQL insert statement
                        String insertQuery = "INSERT INTO advocate(advocate_id, advocate_name,advocate_phone, service) VALUES (?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, phoneNumber);
                        preparedStatement.setString(4, service);

                        // Execute the insert statement
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Advocate registered successfully!");
                        } else {
                            System.out.println("Failed to register Advocate.");
                        }

                        // Close the database connection and resources
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        if (e instanceof SQLIntegrityConstraintViolationException) {
                            System.out.println("Please Enter a Unique Advocate Id");
                        } else {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    // Implement modifying customer details logic

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");

                        String query = "SELECT * FROM advocate";
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                while (resultSet.next()) {
                                    int id1 = resultSet.getInt("advocate_id");
                                    String name1 = resultSet.getString("advocate_name");
                                    String phone1 = resultSet.getString("advocate_phone");
                                    String service1 = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("advocate_id: " + id1);
                                    System.out.println("advocate_name: " + name1);
                                    System.out.println("advocate_phone: " + phone1);
                                    System.out.println("service: " + service1);
                                    System.out.println("======================\n");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Ask the user for the record ID
                        System.out.print("Enter the advocate ID to update: ");
                        int recordIdToUpdate = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        // Ask the user for the column to update
                        System.out.print("Enter the column name to update: ");
                        String columnToUpdate = scanner.nextLine();

                        // Ask the user for the new value
                        System.out.print("Enter the new value: ");
                        String newValue = scanner.nextLine();
                        // System.out.println(newValue+" "+recordIdToUpdate);
                        // Update the specified column for the specific record
                        String updateQuery = "UPDATE advocate SET " + columnToUpdate + " = ? WHERE advocate_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, newValue);
                            updateStatement.setInt(2, recordIdToUpdate);
                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Record updated successfully.\n");
                            } else {
                                System.out.println("Record not found for update.");
                            }
                        }

                        // Display the updated record
                        String selectQuery = "SELECT * FROM advocate WHERE advocate_id = ?";
                        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                            selectStatement.setInt(1, recordIdToUpdate);
                            try (ResultSet resultSet = selectStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    int id1 = resultSet.getInt("advocate_id");
                                    String name1 = resultSet.getString("advocate_name");
                                    String phone1 = resultSet.getString("advocate_phone");
                                    String service1 = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("advocate id: " + id1);
                                    System.out.println("advocate name: " + name1);
                                    System.out.println("advocate phone: " + phone1);
                                    System.out.println("service: " + service1);
                                    System.out.println("======================\n");
                                }
                            }
                        }

                        // Close resources

                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        while (true) {
                            System.out.println("Enter the Advocate Id you want to delete if don't then press 0:");
                            int id3 = scanner.nextInt();
                            if (id3 == 0)
                                break;
                            String findQuery = "SELECT * FROM advocate WHERE advocate_id = ?";
                            try (PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
                                findStatement.setInt(1, id3);
                                try (ResultSet resultSet = findStatement.executeQuery()) {
                                    if (resultSet.next()) {
                                        int id1 = resultSet.getInt("advocate_id");
                                        String name1 = resultSet.getString("advocate_name");
                                        String phone1 = resultSet.getString("advocate_phone");
                                        String service1 = resultSet.getString("service");
                                        // ... Retrieve other columns

                                        System.out.println("advocate id: " + id1);
                                        System.out.println("advocate name: " + name1);
                                        System.out.println("advocate phone: " + phone1);
                                        System.out.println("service: " + service1);
                                        System.out.println("======================\n");
                                        System.out.println("Are you sure you want to delete this record (1/0)?");
                                        int ch = scanner.nextInt();
                                        if (ch == 1) {
                                            // Delete a record
                                            String deleteQuery = "DELETE FROM advocate WHERE advocate_id = ?";
                                            try (PreparedStatement deleteStatement = connection
                                                    .prepareStatement(deleteQuery)) {
                                                deleteStatement.setInt(1, id3);
                                                int rowsAffected = deleteStatement.executeUpdate();
                                                if (rowsAffected > 0) {
                                                    System.out.println("Record deleted successfully.");
                                                    break;
                                                }
                                            }
                                        } else {
                                            break;
                                        }

                                    } else {
                                        System.out.println("Record not found. Please enter a valid Advocate Id");
                                    }
                                }
                            }

                        }
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Enter the Advocate Id you want to see details of");
                    int id2 = scanner.nextInt();

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        String query = "SELECT * FROM advocate where advocate_id=" + id2;
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                if (resultSet.next()) {
                                    int id1 = resultSet.getInt("advocate_id");
                                    String name1 = resultSet.getString("advocate_name");
                                    String phone1 = resultSet.getString("advocate_phone");
                                    String service1 = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("advocate id: " + id1);
                                    System.out.println("advocate name: " + name1);
                                    System.out.println("advocate phone: " + phone1);
                                    System.out.println("service: " + service1);
                                    System.out.println("======================\n");
                                } else {
                                    System.out.println("No record found. Please enter a valid Advocate Id");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Close the connection
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        String query = "SELECT * FROM advocate";
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                while (resultSet.next()) {
                                    int id1 = resultSet.getInt("advocate_id");
                                    String name1 = resultSet.getString("advocate_name");
                                    String phone1 = resultSet.getString("advocate_phone");
                                    String service1 = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("Advocate id: " + id1);
                                    System.out.println("Advocate name: " + name1);
                                    System.out.println("Advocate phone: " + phone1);
                                    System.out.println("Service: " + service1);
                                    System.out.println("======================\n");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Close the connection
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static boolean checkIDExistence(Connection connection, String tableName, String columnName, int id)
            throws SQLException {
        String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static void appointmentMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("==============================");
            System.out.println("       Appointment Menu");
            System.out.println("==============================");
            System.out.println("1. Book an Appointment");
            System.out.println("2. Modify Appointment Details");
            System.out.println("3. Delete Appointment Record");
            System.out.println("4. View Single Record");
            System.out.println("5. View All Records");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        try {
                            // Establish the database connection
                            String query = "SELECT * FROM customer";
                            // Create a statement object
                            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                                // Execute the query
                                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                    // Process the result set
                                    System.out.println("==============================");
                                    System.out.println("      Customer Records");
                                    System.out.println("==============================");
                                    while (resultSet.next()) {
                                        int id1 = resultSet.getInt("id");
                                        String name1 = resultSet.getString("name");
                                        String address4 = resultSet.getString("address");
                                        String phone4 = resultSet.getString("phone");
                                        // ... Retrieve other columns

                                        System.out.println("ID: " + id1);
                                        System.out.println("Name: " + name1);
                                        System.out.println("Address: " + address4);
                                        System.out.println("Phone: " + phone4);
                                        System.out.println("======================");
                                    }
                                }
                            } // PreparedStatement and ResultSet are automatically closed here

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        String query = "SELECT * FROM advocate";
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                System.out.println("==============================");
                                System.out.println("      Advocate Records");
                                System.out.println("==============================");
                                while (resultSet.next()) {
                                    int id1 = resultSet.getInt("advocate_id");
                                    String name1 = resultSet.getString("advocate_name");
                                    String phone1 = resultSet.getString("advocate_phone");
                                    String service1 = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("Advocate id: " + id1);
                                    System.out.println("Advocate name: " + name1);
                                    System.out.println("Advocate phone: " + phone1);
                                    System.out.println("Service: " + service1);
                                    System.out.println("======================\n");
                                }
                            }
                            // PreparedStatement and ResultSet are automatically closed here
                        } catch (SQLException e) {

                            e.printStackTrace();
                        }

                        // Get input from the user

                        System.out.print("Enter Advocate ID: ");
                        int advocateId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        System.out.print("Enter Customer ID: ");
                        int customerId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        boolean advocateExists = checkIDExistence(connection, "advocate", "advocate_id", advocateId);
                        // Check if Customer ID exists
                        boolean customerExists = checkIDExistence(connection, "customer", "id", customerId);

                        if (advocateExists && customerExists) {
                            // You can proceed with booking the appointment
                            System.out.println(
                                    "Both Advocate ID and Customer ID are valid. Proceed with booking the appointment.");
                        } else {
                            System.out
                                    .println("Advocate ID and/or Customer ID not found. Cannot proceed with booking.");
                            break;
                        }

                        System.out.print("Enter Date of Appointment (DD-MM-YYYY): ");
                        String dateOfAppointment = scanner.nextLine();

                        System.out.print("Enter Service Type: ");
                        String serviceType = scanner.nextLine();

                        // Insert appointment into the database
                        String insertQuery = "INSERT INTO appointment (customer_id, advocate_id, date, service) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setInt(1, advocateId);
                            insertStatement.setInt(2, customerId);
                            insertStatement.setString(3, dateOfAppointment);
                            insertStatement.setString(4, serviceType);
                            int rowsAffected = insertStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Appointment booked successfully.");
                            } else {
                                System.out.println("Appointment booking failed.");
                            }
                        }

                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    // Implement modifying appointment details logic

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");

                        String query = "SELECT * FROM appointment";
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                while (resultSet.next()) {
                                    int id1 = resultSet.getInt("appointment_id");
                                    int id2 = resultSet.getInt("customer_id");
                                    int id3 = resultSet.getInt("advocate_id");
                                    String date = resultSet.getString("date");
                                    String service = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("Appointment ID: " + id1);
                                    System.out.println("Customer ID: " + id2);
                                    System.out.println("Advocate ID: " + id3);
                                    System.out.println("Date: " + date);
                                    System.out.println("Service: " + service);
                                    System.out.println("======================\n");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Ask the user for the record ID
                        System.out.print("Enter the Appointment ID to update: ");
                        int recordIdToUpdate = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        // Ask the user for the column to update
                        System.out.print("Enter the column name to update(Except Appointment Id): ");
                        String columnToUpdate = scanner.nextLine();

                        // Ask the user for the new value
                        System.out.print("Enter the new value: ");
                        String newValue = scanner.nextLine();

                        // Update the specified column for the specific record
                        String updateQuery = "UPDATE appointment SET " + columnToUpdate
                                + " = ? WHERE appointment_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, newValue);
                            updateStatement.setInt(2, recordIdToUpdate);
                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Record updated successfully.");
                            } else {
                                System.out.println("Record not found for update.");
                            }
                        }

                        // Display the updated record
                        String selectQuery = "SELECT * FROM appointment WHERE appointment_id = ?";
                        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                            selectStatement.setInt(1, recordIdToUpdate);
                            try (ResultSet resultSet = selectStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    int id1 = resultSet.getInt("appointment_id");
                                    int id2 = resultSet.getInt("customer_id");
                                    int id3 = resultSet.getInt("advocate_id");
                                    String date = resultSet.getString("date");
                                    String service = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("Appointment ID: " + id1);
                                    System.out.println("Customer ID: " + id2);
                                    System.out.println("Advocate ID: " + id3);
                                    System.out.println("Date: " + date);
                                    System.out.println("Service: " + service);
                                    System.out.println("======================\n");
                                }
                            }
                        }

                        // Close resources

                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        while (true) {
                            System.out.println("Enter the Appointment ID you want to delete if don't then press 0:");
                            int id3 = scanner.nextInt();
                            if (id3 == 0)
                                break;
                            String findQuery = "SELECT * FROM appointment WHERE appointment_id = ?";
                            try (PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
                                findStatement.setInt(1, id3);
                                try (ResultSet resultSet = findStatement.executeQuery()) {
                                    if (resultSet.next()) {
                                        int id1 = resultSet.getInt("appointment_id");
                                        int id2 = resultSet.getInt("customer_id");
                                        int id4 = resultSet.getInt("advocate_id");
                                        String date = resultSet.getString("date");
                                        String service = resultSet.getString("service");
                                        // ... Retrieve other columns

                                        System.out.println("Appointment ID: " + id1);
                                        System.out.println("Customer ID: " + id2);
                                        System.out.println("Advocate ID: " + id4);
                                        System.out.println("Date: " + date);
                                        System.out.println("Service: " + service);
                                        System.out.println("======================\n");
                                        System.out.println("Are you sure you want to delete this record (1/0)?");
                                        int ch = scanner.nextInt();
                                        if (ch == 1) {
                                            // Delete a record
                                            String deleteQuery = "DELETE FROM appointment WHERE appointment_id = ?";
                                            try (PreparedStatement deleteStatement = connection
                                                    .prepareStatement(deleteQuery)) {
                                                deleteStatement.setInt(1, id3);
                                                int rowsAffected = deleteStatement.executeUpdate();
                                                if (rowsAffected > 0) {
                                                    System.out.println("Record deleted successfully.");
                                                    break;
                                                }
                                            }
                                        } else {
                                            break;
                                        }

                                    } else {
                                        System.out.println("Record not found. Please enter a valid Appointment Id");
                                    }
                                }
                            }

                        }
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Enter the Appointment Id you want to see details of");
                    int id2 = scanner.nextInt();

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        String query = "SELECT * FROM appointment where appointment_id=" + id2;
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                if (resultSet.next()) {
                                    int id1 = resultSet.getInt("appointment_id");
                                    int id4 = resultSet.getInt("customer_id");
                                    int id3 = resultSet.getInt("advocate_id");
                                    String date3 = resultSet.getString("date");
                                    String service = resultSet.getString("service");
                                    // ... Retrieve other columns

                                    System.out.println("Appointment ID: " + id1);
                                    System.out.println("Customer Id: " + id4);
                                    System.out.println("Advocate Id: " + id3);
                                    System.out.println("Appointment Date: " + date3);
                                    System.out.println("Service: " + service);
                                    System.out.println("======================\n");
                                } else {
                                    System.out.println("No record found. Please enter a valid Appointment Id");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Close the connection
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:

                    try {
                        // Establish the database connection
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash",
                                "root", "abinash");
                        String query = "SELECT * FROM appointment";
                        // Create a statement object
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                            // Execute the query
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                // Process the result set
                                System.out.println("==============================");
                                System.out.println("      All Appointments");
                                System.out.println("==============================");
                                while (resultSet.next()) {
                                    int id1 = resultSet.getInt("appointment_id");
                                    int id4 = resultSet.getInt("customer_id");
                                    int id3 = resultSet.getInt("advocate_id");
                                    String date3 = resultSet.getString("date");
                                    String service = resultSet.getString("service");

                                    // String email = resultSet.getString("email");
                                    // ... Retrieve other columns

                                    System.out.println("Appointment ID: " + id1);
                                    System.out.println("Customer Id: " + id4);
                                    System.out.println("Advocate Id: " + id3);
                                    System.out.println("Appointment Date: " + date3);
                                    System.out.println("Service: " + service);
                                    System.out.println("======================\n");
                                }
                            }
                        } // PreparedStatement and ResultSet are automatically closed here

                        // Close the connection
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public static void serviceMenu() {
        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abinash", "root",
                    "abinash");

            // Set to store unique services
            Set<String> uniqueServices = new HashSet<>();

            // Query to retrieve unique services from advocate table
            String selectQuery = "SELECT DISTINCT service FROM advocate";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    String serviceType = resultSet.getString("service");
                    String[] str1 = serviceType.split(",");
                    for (String splitString : str1) {
                        uniqueServices.add(splitString);
                    }
                }

                // Display unique services
                System.out.println("Unique Services:");
                System.out.println("================");

                for (String service : uniqueServices) {
                    System.out.println(service);
                }
                System.out.println("\n");

            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
