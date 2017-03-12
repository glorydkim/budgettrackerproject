/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectbudgettracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Travis
 */
public class Report {

    //varibale for the methods
    String startDate;
    String endDate;
    int envID;
    int trxID;
    String title;
    String description;
    Connection conn;
    SQLClass sql = new SQLClass();
    String startMonth;
    int startDay;
    int startYear;
    String dateSplit = "/";
    String startDateParts[] = null;
    String endMonth = null;
    int endDay;
    int endYear;
    String endDateParts[] = null;

    //Contructor for the class
    public Report() {
    }

    //method to create a report by date
    //date must be in format MM/DD/YYYY
    //returns an array list of String arrays containing the line data as read from database
    public ArrayList<String[]> reportByDate(int envID, String startDate, String endDate) throws SQLException, ClassNotFoundException, IOException {
        this.envID = envID;
        this.startDate = startDate;
        this.endDate = endDate;
      
        //change the dates into formats that match the database
        startDateParts = startDate.split(dateSplit);
            //Month is stored as a VARCHAR in data base, so saved as string here
            startMonth = startDateParts[0];
            //day and year are INTEGER in database, so parsed to int here
            startDay = Integer.parseInt(startDateParts[1]);
            startYear = Integer.parseInt(startDateParts[2]);
        endDateParts = endDate.split(dateSplit);
            endMonth = endDateParts[0];
            endDay = Integer.parseInt(endDateParts[1]);
            endYear = Integer.parseInt(endDateParts[2]);
            
        ArrayList<String[]> report = null;
        String[] line = null;
        try {
          
            PreparedStatement stmt = conn.prepareStatement("select * from manager.transactions "
                    + "where (envID = ?) AND "
                    + "(month between ? AND ?) AND "
                    + "(trxyear between ? AND ?) AND"
                    + "(day between ? AND ?");
            stmt.setInt(1, envID);
            stmt.setString(2, startMonth);
            stmt.setString(3, endMonth);
            stmt.setInt(4, startYear);
            stmt.setInt(5, endYear);
            stmt.setInt(6, startDay);
            stmt.setInt(7, endDay);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                line[0] = Integer.toString(rs.getInt("trxID")); 
                line[1] = Integer.toString(rs.getInt("userid"));
                line[2] = rs.getString("month");
                line[3] = Integer.toString(rs.getInt("trxyear"));
                line[4] = Integer.toString(rs.getInt("day"));
                line[5] = Integer.toString(rs.getInt("envid"));
                line[6] = rs.getString("trxtype");
                line[7] = Double.toString(rs.getDouble("trxamount"));
                line[8] = rs.getString("trxdescription");
                report.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    //method to create a report by envelope 
    public ArrayList<String[]> reportByEnvelope(int envID) throws SQLException, ClassNotFoundException, IOException {
        this.envID = envID;
        ArrayList<String[]> report = null;
        String[] line = null;
        try {
            
               PreparedStatement stmt = conn.prepareStatement("select * from manager.transactions "
                       + "where (envid = ?)");
               stmt.setInt(1, envID);
             ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                line[0] = Integer.toString(rs.getInt("trxID")); 
                line[1] = Integer.toString(rs.getInt("userid"));
                line[2] = rs.getString("month");
                line[3] = Integer.toString(rs.getInt("trxyear"));
                line[4] = Integer.toString(rs.getInt("day"));
                line[5] = Integer.toString(rs.getInt("envid"));
                line[6] = rs.getString("trxtype");
                line[7] = Double.toString(rs.getDouble("trxamount"));
                line[8] = rs.getString("trxdescription");
                report.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    //method to create a report by transaction type
    public ArrayList<String[]> reportByType(String description) throws SQLException, ClassNotFoundException, IOException {
        this.description = description;
        ArrayList<String[]> report = null;
        String[] line = null;

        try {
  
            PreparedStatement stmt = conn.prepareStatement("select * from manager.transactions "
                       + "where (trxtype = ?)");
               stmt.setInt(1, envID);
             ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                line[0] = Integer.toString(rs.getInt("trxID")); 
                line[1] = Integer.toString(rs.getInt("userid"));
                line[2] = rs.getString("month");
                line[3] = Integer.toString(rs.getInt("trxyear"));
                line[4] = Integer.toString(rs.getInt("day"));
                line[5] = Integer.toString(rs.getInt("envid"));
                line[6] = rs.getString("trxtype");
                line[7] = Double.toString(rs.getDouble("trxamount"));
                line[8] = rs.getString("trxdescription");
                report.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

}
