/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

import static ProductAgent.ProductAgent.mySkills;
import Utilities.Constants;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import java.util.Vector;

/**
 *
 * @author hamoo
 */
public class Negotiate extends ContractNetInitiator{
    
    protected AID selectedTargetRA;
    public Negotiate(Agent a, ACLMessage cfp) {
        super(a, cfp);
        
    }
    
   @Override
    protected void handleAllResponses(Vector responses, Vector acceptances) {
            
        
    }
    
            
     @Override
    protected void handleInform(ACLMessage inform) {
            //Prepare the message to send to the ResourceAgent
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

        //Try to find the resource who have the skill that the product agent want
        msg.addReceiver(selectedTargetRA);
        msg.setOntology(Constants.ONTOLOGY_LOCATION);
            //Send message is included
            ((ProductAgent) myAgent).addBehaviour(new RequestLocation((ProductAgent) myAgent, msg));
        }
        
    

    @Override
    protected void handleRefuse(ACLMessage refuse) {
            System.out.println("Agent "+refuse.getSender().getName()+" refused");
        
    }

    @Override
    protected void handlePropose(ACLMessage propose, Vector acceptances) {
        String ReceivedMessage = propose.getContent();

        ACLMessage reply = ((ACLMessage) propose).createReply();
        System.out.println(reply.getContent());

        if (ReceivedMessage.equals("1")) {
            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            System.out.println(getAgent().getLocalName() + ": Proposal of " + propose.getSender().getLocalName() + "is accepted");
            selectedTargetRA = propose.getSender();
            acceptances.add(reply);
            
        } else {
            reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
            System.out.println(getAgent().getLocalName() + ": Proposal of " + propose.getSender().getLocalName() + "is declined");
            selectedTargetRA = ((ProductAgent) myAgent).myUber;
            mySkills.clear();
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            acceptances.add(reply);
            System.out.println("Moving Agent out of the system");
            msg.addReceiver(selectedTargetRA);
            msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
            msg.setContent("Si");
           ((ProductAgent) myAgent).addBehaviour(new RequestTransport(((ProductAgent) myAgent), msg));
            
        }
 
    }
    
}
    
    
        
        
        
        
        
        
        
             
    

