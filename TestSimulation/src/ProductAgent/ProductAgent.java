package ProductAgent;

import Utilities.Constants;
import Utilities.DFInteraction;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author André
 */
public class ProductAgent extends Agent{
    
    protected LinkedList<String> mySkills;
    protected int productType;
    protected String source;
    protected String nextExecutor;
    protected String myLocation;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        //Get skills to execute
        mySkills = (LinkedList<String>) args[0];        
        String Skilltype = "Skill";     
        //Register in DF here
        try {
            DFInteraction.RegisterInDF(this, mySkills.get(0), Constants.DFSERVICE_REQUEST);
        } catch (FIPAException ex) {
            Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
           
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
         
            
            
            DFAgentDescription[] TRAfound= DFInteraction.SearchInDF(Constants.DFSERVICE_TRANSPORT, this);
             //Launch Behaviour to manage Product Execution here
            msg.setContent(myLocation);
            System.out.println(myLocation);
            msg.addReceiver(TRAfound[0].getName());
            System.out.println(TRAfound[0].getName());
            msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
            this.addBehaviour(new RequestTransport(this,msg));
            
            
           
            DFAgentDescription[] MRAfound= DFInteraction.SearchInDF(Constants.DFSERVICE_RESOURCE, this);
            //Launch Behaviour to manage Product Execution here
            msg.setContent(mySkills.get(0));
            System.out.println("1st skill: " + mySkills.get(0));
            msg.addReceiver(MRAfound[0].getName());
            System.out.println("1st skill rsc name: "+MRAfound[0].getName());
            msg.setOntology(Constants.ONTOLOGY_RESOURCE);
            this.addBehaviour(new RequestResource(this,msg)); 
        } catch (FIPAException ex) {
            Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException ex) {
            Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
