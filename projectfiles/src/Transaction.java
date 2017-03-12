/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectbudgettracker;

import java.text.DecimalFormat;

/**
 *
 * @author glorykim
 */
public class Transaction {
    
    String category;
    String description;
    String date;
    double amount;
    
    Transaction()
    {
        
    }
    
    Transaction(String category, String description,String date, double amount)
    {
        this.category = category;
        this.description = description;
        this.date = date;
        this.amount = amount;
        
        
    }
    
}
