//This was my contribution to my final project in my final class for college.
//The other members of my group built a User Interface that took information from a user.
//This stores that stores and updates that information in a MySQL database, calculates the BMR, and returns the information which displayed in the UI.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MySQL {
	
	private boolean newUser = false;
	private String name = "";
	private String dob = "";
	private double weight = 0.0;
	private double height = 0.0;
	private String date = "";
	private String gender = "";
	private String excerciseAmount = "";
	private double calories = 0.0;
	
	String url       = "jdbc:mysql://localhost:3306/cmsc495";
    String user      = "root";
    String password  = "password";
	
	public MySQL (boolean newUser, String name, String dob, double weight, double height, String date, String gender, String excerciseAmount) {
		this.newUser = newUser;
		this.name = name;
		this.dob = dob;
		this.weight = weight;
		this.height = height;
		this.date = date;
		this.gender = gender;
		this.excerciseAmount = excerciseAmount;
	}
	

	public void connect () {
        
//       try {
//test		Connection conn = DriverManager.getConnection(url, user, password);
//			System.out.println(String.format("Connected to database %s "
//                    + "successfully.", conn.getCatalog()));
			if(newUser = true) {
				insert();
			} else {
				update();
			}		
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
	}
	
	
	public void insert() {
		int keyId = 0;
		
		ResultSet rs = null;
		
        String sql = "INSERT INTO weightmonitor(name, dateOfBirth, weight, height, date, gender) "
                   + "VALUES(?,?,?,?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
            
            // set parameters for statement
            pstmt.setString(1, name);
            pstmt.setString(2, dob);
            pstmt.setDouble(3, weight);
            pstmt.setDouble(4, height);
            pstmt.setString(5, date);
            pstmt.setString(6, gender);
 
            int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1)
            {
                
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    keyId = rs.getInt(1);
 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if(rs != null)  rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
	}
	
	public void update() {
		String sqlUpdate = "UPDATE weightmonitor "
                + "SET weight = ? date = ? "
                + "WHERE id = ?";
 
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
 
            // prepare data for update
            pstmt.setDouble(1, weight);
            pstmt.setString(2, date);
 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
	

/*
 *  If you are sedentary (little or no exercise) = BMR x 1.2
    If you are lightly active (light exercise/sports 1-3 days/week) = BMR x 1.375
    If you are moderately active (moderate exercise/sports 3-5 days/week) = BMR x 1.55
    If you are very active (hard exercise/sports 6-7 days a week) = BMR x 1.725
    If you are extra active (very hard exercise/sports & physical job or 2x training) 
    = BMR x 1.9
 */
	public double calculate () {
		double temp = 0;
		if(gender == "male") {										  //age input or calc where?	
			temp = 66 + ( 6.23 * weight) + ( 12.7 * height) - ( 6.8 * 20);
		}
		if(gender == "female") {
			temp = 655 + (4.35 * weight) + ( 4.7 * height) - ( 4.7 * 20);
		}
		return temp;
	}
}
