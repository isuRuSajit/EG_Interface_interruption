package com;

import java.sql.*;

public class Interruption {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/interruption", "root", "isuru12345");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertInterruption(String ariaCode, String ariaName, String date, String time) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into interruptions(`intID`,`intAriaCode`,`intAriaName`,`intDate`,`intTime`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ariaCode);
			preparedStmt.setString(3, ariaName);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, time);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newInterruptions = readInterruptions();
			output = "{\"status\":\"success\", \"data\": \"" + newInterruptions + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the interruptions.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readInterruptions() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Aria Code</th><th>Aria Name</th><th>Date</th>"
					+ "<th>Time  </th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from interruptions";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String intID = Integer.toString(rs.getInt("intID"));
				String intAriaCode = rs.getString("intAriaCode");
				String intAriaName = rs.getString("intAriaName");
				String intDate = rs.getString("intDate");
				String intTime = rs.getString("intTime");
				// Add into the html table
				output += "<tr><td><input id='hidInterruptionIDUpdate'name='hidInterruptionIDUpdate'type='hidden' value='"
						+ intID + "'>" + intAriaCode + "</td>";
				output += "<td>" + intAriaName + "</td>";
				output += "<td>" + intDate + "</td>";
				output += "<td>" + intTime + "</td>";
				// buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger' data-intid='"
						+ intID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the interruptions.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateInterruption(String ID, String ariaCode, String ariaName, String date, String time) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE interruptions SET intAriaCode=?,intAriaName=?,intDate=?,intTime=? WHERE intID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, ariaCode);
			preparedStmt.setString(2, ariaName);
			preparedStmt.setString(3, date);
			preparedStmt.setString(4, time);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newInterruptions = readInterruptions();
			output = "{\"status\":\"success\", \"data\": \"" + newInterruptions + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Interruptions.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteInterruption(String intID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from interruptions where intID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(intID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newInterruptions = readInterruptions();
			output = "{\"status\":\"success\", \"data\": \"" + newInterruptions + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the interruption.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}