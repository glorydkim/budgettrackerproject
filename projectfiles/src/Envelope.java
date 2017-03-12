/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectbudgettracker;

/**
 *
 * @author glorykim
 */
public class Envelope {
    
    String envName;
    double budget;
    double trxtotal;
     
    Envelope()
    {
    }
    
    Envelope(String envName, double budget, double trxtotal)
    {
        this.envName = envName;
        this.budget = budget;
        this.trxtotal = trxtotal;
    }
    
}
