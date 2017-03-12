/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectbudgettracker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Travis
 */
public class UploadCSV {

    //variables for use by the methods
    String fileName;
    Connection conn;
    SQLClass sql = new SQLClass();

    //constructor for the class to create objects
    public UploadCSV() {
    }

    //method to upload the CSV file to the database for use
    public void uploadCSVfile(String fileName) throws SQLException, ClassNotFoundException, IOException {
        this.fileName = fileName;
        BufferedReader br = null;
        String line = "";
        String csvSplit = ",";
        String[] data = null;
        String date = null;
        String desc = null;
        double amount;
        String dateSplit = "/";
        String dateParts[] = null;
        String month = null;
        int year;
        int day;
        int envID = 0;
        int userID = 1;
        //add functionality later to determine the transaction type
        //for now, set all transaction types to blank
        String trxType = null;

        try {

            PreparedStatement stmt = conn.prepareStatement("insert into manager.transactions "
                    + "(USERID, MONTH, TRXYEAR, DAY, ENVID, TRXTYPE, TRXAMOUNT, TRXDESCRIPTION "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?)");
            br = new BufferedReader(new FileReader(fileName));
            // CSV file needs to be in the format DATE, DESCRIPTIONS, AMOUNT
            while ((line = br.readLine()) != null) {
                data = line.split(csvSplit);
                date = data[0];
                desc = data[1];
                amount = Double.parseDouble(data[2]);
                dateParts = date.split(dateSplit);
                //Month is stored as a VARCHAR in data base, so saved as string here
                month = dateParts[0];
                //day and year are INTEGER in database, so parsed to int here
                day = Integer.parseInt(dateParts[1]);
                year = Integer.parseInt(dateParts[2]);
                //Now that we have all the parts we can insert into the transaction
                stmt.setInt(1, userID);
                stmt.setString(2, month);
                stmt.setInt(3, year);
                stmt.setInt(4, day);
                stmt.setInt(5, envID);
                stmt.setString(6, trxType);
                stmt.setDouble(7, amount);
                stmt.setString(8, desc);
                stmt.executeUpdate();

            }
            br.close();
            stmt.close();
            conn.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
