/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import java.util.Vector;

/**
 *
 * @author hamoo
 */
public class Negotiate extends ContractNetInitiator{

    public Negotiate(Agent a, ACLMessage cfp) {
        super(a, cfp);
    }
    
   @Override
    protected void handleAllResponses(Vector responses, Vector acceptances) {
        
        if (((ProductAgent) myAgent).mySkills.get(0).equals("example")){
            System.out.println("Searching for best basic parameters");
            ACLMessage proposeToAccept = ((ACLMessage) responses.get(0)).createReply();
            proposeToAccept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            acceptances.add(proposeToAccept);
            for (int i = 1; i < responses.size(); i++) {
            ACLMessage proposeToReject = ((ACLMessage) responses.get(i)).createReply();
            proposeToReject.setPerformative(ACLMessage.REJECT_PROPOSAL);
            acceptances.add(proposeToReject);
            }
            
        }
    }
    
            
     @Override
    protected void handleInform(ACLMessage inform) {
            System.out.println("Agent "+inform.getSender().getName()+" informed" + inform.getContent());
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
            System.out.println("Agent "+refuse.getSender().getName()+" refused");
        
    }

    @Override
    protected void handlePropose(ACLMessage propose, Vector acceptances) {
            System.out.println("Agent "+propose.getSender().getName()+" proposed "+propose.getContent());

        
    }
    
}
    
    
        
        
        
        
        
        
        
             
    

