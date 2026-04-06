import java.sql.*;
import java.util.Scanner;

public class B1 {
    static Connection con;
    public static void main(String args[]) throws ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        int choice;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydatabase","root","");
            do {
                System.out.println("\n*** MENU ***");
                System.out.println("1. Add New Student");
                System.out.println("2. Delete Student Record");
                System.out.println("3. Update Student Address");
                System.out.println("4. Search Student");
                System.out.println("5. Display Data ");
                System.out.println("6. Exit");

                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) 
                {
                    case 1:	
                        addStudent(sc);	
                        break;
                    case 2:	
                        deleteStudent(sc);	
                        break;
                    case 3:	
                        updateStudent(sc);	
                        break;
                    case 4:	
                        searchStudent(sc);	
                        break;
                    case 5 : 	
                        display(); 	
                        break;
                    case 6:	
                        System.out.println("Exiting...");	
                        break;
                    default:	
                        System.out.println("Invalid choice");
                }
            } while (choice != 6);
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

 
    static void addStudent(Scanner sc) {
        try {
            int reg; Date dob;
            String name,address, stclass, course;             Date dt ;

            System.out.print("Enter Reg No: ");
            reg = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            name = sc.nextLine();

            System.out.print("Enter DOB (YYYY-MM-DD): ");
            dt=Date.valueOf(sc.next());
            sc.nextLine();
            
            System.out.print("Enter Address: ");
            address = sc.nextLine();
            
            System.out.print("Enter Class: ");
            stclass = sc.nextLine();
            System.out.print("Enter Course: ");
            course = sc.nextLine();
            String query = "INSERT INTO Student VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, reg);
            pst.setString(2, name);
            pst.setDate(3, dt);
            pst.setString(4, address);
            pst.setString(5, stclass);
            pst.setString(6, course);

            pst.executeUpdate();

            System.out.println("Student Added Successfully");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
 

    static void deleteStudent(Scanner sc) {
        try {
            System.out.print("Enter Reg No to delete: ");
            int reg = sc.nextInt();
            String query = "DELETE FROM Student WHERE StReg_No=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, reg);
            int rows = pst.executeUpdate();

            if (rows > 0)
                System.out.println("Record Deleted");
            else
                System.out.println("Student Not Found");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    static void updateStudent(Scanner sc) {
        try {

            System.out.print("Enter Reg No: ");
            int reg = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Address: ");
            String address = sc.nextLine();

            String query = "UPDATE Student SET StAddress=? WHERE StReg_No=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, address);
            pst.setInt(2, reg);

            int rows = pst.executeUpdate();

            if (rows > 0)
                System.out.println("Address Updated");
            else
                System.out.println("Student Not Found");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    static void searchStudent(Scanner sc) {
        try {
            System.out.print("Enter Reg No to search: ");
            int reg = sc.nextInt();
            String query = "SELECT * FROM Student WHERE StReg_No=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, reg);
            ResultSet rs = pst.executeQuery();
       
  
            if (rs.next()) {
                   System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + 
                   rs.getString(3)  + "\t" + rs.getString(4) + "\t" +
                   rs.getString(5) + "\t" + rs.getString(6));
            } else {
                System.out.println("Student Not Found");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    static void display()
    {
        try
        {
            String qry="Select * from Student";
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(qry);
                System.out.println("\nStudent Details");
                System.out.println("Reg.No   Name    DOB          Address       Class    Course");
         
            while(rs.next())
            {
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + 
                        rs.getString(3)  + "\t" + rs.getString(4) + "\t" +
                         rs.getString(5) + "\t" + rs.getString(6));
            }
        } catch (SQLException e) {
            System.out.println(e);
            }
    }
}
