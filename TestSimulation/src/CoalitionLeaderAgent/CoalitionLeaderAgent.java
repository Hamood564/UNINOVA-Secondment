/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoalitionLeaderAgent;

import jade.core.Agent;

/**
 *
 * @author Andre Rocha
 */
public class CoalitionLeaderAgent extends Agent{
    
    protected String myLocation;

    @Override
    protected void setup() {
        //Argumets [0]CLA location
        Object[] args = getArguments();
        //Get my location
        myLocation = (String) args[0];
     
        //Register in DF here
        
        //Launch Behaviours here
        
    }
    
    
    @Override
    protected void takeDown() {
        
    }

}
