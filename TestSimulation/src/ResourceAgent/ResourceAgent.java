package ResourceAgent;

import Utilities.Constants;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.client.ClientManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author André
 */
public class ResourceAgent extends Agent{
    
   protected LinkedList<String> mySkills;
   protected String myLocation;
   protected String executeover;
  

    @Override
    public void setup() {
        //Argumets [0]resource location [1]skills taht resource can perform
        Object[] args = getArguments();
        //Get my location
        myLocation = (String) args[0];
        //Get my skills
        mySkills = (LinkedList<String>) args[1];        
  
        //Register in DF here
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(this.getAID());
            for (Iterator<String> iterator = mySkills.iterator(); iterator.hasNext();) {
                String Skill = iterator.next();
                ServiceDescription sd = new ServiceDescription();
                sd.setName(Skill);
                sd.setType(Constants.DFSERVICE_RESOURCE);
                dfd.addServices(sd);
            }
            DFService.register(this, dfd);
        } catch (FIPAException ex) {
            Logger.getLogger(ResourceAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Launch Behaviours here     
        this.addBehaviour(new NegotiateResp(this, MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CFP), MessageTemplate.MatchOntology(Constants.ONTOLOGY_RESOURCE))));
        this.addBehaviour(new ResourceLocation(this, MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology(Constants.ONTOLOGY_LOCATION))));
        this.addBehaviour(new ResourceExecute(this, MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology(Constants.ONTOLOGY_RESOURCE))));

    }

    @Override
    protected void takeDown() {
       try {
           DFService.deregister(this);
       } catch (FIPAException ex) {
           Logger.getLogger(ResourceAgent.class.getName()).log(Level.SEVERE, null, ex);
       }
  
    }
}


   