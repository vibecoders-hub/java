import java.sql.*;
import java.util.Scanner;

public class PartB2_Bank {

    static Connection con;

    public static void main(String args[]) throws ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        int choice;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydatabase", "root", "");

            do {
                System.out.println("\n*** MENU ***");
                System.out.println("1. Add New Account Holder Information");
                System.out.println("2. Amount Deposit");
                System.out.println("3. Amount Withdrawal (Minimum balance 500 Rs)");
                System.out.println("4. Display All Information");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1: addAccount(sc); break;
                    case 2: deposit(sc);    break;
                    case 3: withdraw(sc);   break;
                    case 4: display();      break;
                    case 5: System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid choice");
                }

            } while (choice != 5);

            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    static void addAccount(Scanner sc) {
        try {
            System.out.print("Enter Account Number: ");
            int accNo = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Account Holder Name: ");
            String accName = sc.nextLine();

            System.out.print("Enter Address: ");
            String accAddress = sc.nextLine();

            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            String query = "INSERT INTO Bank VALUES(?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accNo);
            pst.setString(2, accName);
            pst.setString(3, accAddress);
            pst.setDouble(4, balance);
            pst.executeUpdate();

            System.out.println("Account Holder Added Successfully");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    static void deposit(Scanner sc) {
        try {
            System.out.print("Enter Account Number: ");
            int accNo = sc.nextInt();

            System.out.print("Enter Deposit Amount: ");
            double amount = sc.nextDouble();

            String checkQuery = "SELECT Balance FROM Bank WHERE ACC_NO = ?";
            PreparedStatement checkPst = con.prepareStatement(checkQuery);
            checkPst.setInt(1, accNo);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                double newBalance = rs.getDouble(1) + amount;

                String query = "UPDATE Bank SET Balance = ? WHERE ACC_NO = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setDouble(1, newBalance);
                pst.setInt(2, accNo);
                pst.executeUpdate();

                System.out.println("Amount Deposited Successfully");
                System.out.println("Updated Balance: Rs " + newBalance);
            } else {
                System.out.println("Account Not Found");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    static void withdraw(Scanner sc) {
        try {
            System.out.print("Enter Account Number: ");
            int accNo = sc.nextInt();

            System.out.print("Enter Withdrawal Amount: ");
            double amount = sc.nextDouble();

            String checkQuery = "SELECT Balance FROM Bank WHERE ACC_NO = ?";
            PreparedStatement checkPst = con.prepareStatement(checkQuery);
            checkPst.setInt(1, accNo);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble(1);

                if ((currentBalance - amount) < 500) {
                    System.out.println("Withdrawal Failed! Minimum balance of Rs 500 must be maintained.");
                    System.out.println("Current Balance: Rs " + currentBalance);
                } else {
                    double newBalance = currentBalance - amount;

                    String query = "UPDATE Bank SET Balance = ? WHERE ACC_NO = ?";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setDouble(1, newBalance);
                    pst.setInt(2, accNo);
                    pst.executeUpdate();

                    System.out.println("Amount Withdrawn Successfully");
                    System.out.println("Remaining Balance: Rs " + newBalance);
                }
            } else {
                System.out.println("Account Not Found");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    static void display() {
        try {
            String qry = "SELECT * FROM Bank";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            System.out.println("\nBank Account Details");
            System.out.println("ACC_NO\tACC_NAME\tACC_ADDRESS\tBALANCE");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" +
                        rs.getString(3) + "\t\t" + rs.getDouble(4));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
