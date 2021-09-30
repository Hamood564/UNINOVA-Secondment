/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

import static ProductAgent.ProductAgent.mySkills;
import ResourceAgent.ResourceAgent;
import static ResourceAgent.ResourceAgent.myLocation;
import Utilities.Constants;
import Utilities.DFInteraction;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author André Dionisio Rocha
 */
public class RequestTransport extends AchieveREInitiator {
         
        String SkillNone = "Transport";   
    public RequestTransport(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
        System.out.println("Transporting");
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println("Transport refused");
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        try {
            //PA arrives at RA location
            System.out.println("Transport finished: Arrived at Location " + ((ProductAgent) myAgent).myLocation );
            //checks if location of RA is not null
            if (((ProductAgent) myAgent).myLocation != null) {

            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            DFAgentDescription[] MRAfound= DFInteraction.SearchInDF(Constants.DFSERVICE_RESOURCE, ((ProductAgent) myAgent));
            
            //checking if no skill left to execute or no RA providing the skill == system exit
            if ((mySkills.isEmpty()) || (MRAfound.length == 0)){
                System.out.println("1st skill: " + SkillNone);
                msg.addReceiver(((ProductAgent) myAgent).myUber);
                //assign Si to exit the system
                msg.setContent("Si");
                msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
                mySkills.clear();
                //assign location to null so that PA should not randomly assign location or loop back
               ((ProductAgent) myAgent).myLocation = null;
               ((ProductAgent) myAgent).addBehaviour(new RequestTransport(((ProductAgent) myAgent), msg));
            } else{
                //otherwise move the PA to next RA location
                System.out.println("1st skill: " + mySkills.get(0));
                String Location = ResourceAgent.myLocation;
                msg.addReceiver(MRAfound[0].getName());
                msg.setContent(Location);
                msg.setOntology(Constants.ONTOLOGY_RESOURCE);
                ((ProductAgent) myAgent).addBehaviour(new RequestResource(((ProductAgent) myAgent), msg));
            }

            }
        } catch (FIPAException ex) {
            Logger.getLogger(RequestTransport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
