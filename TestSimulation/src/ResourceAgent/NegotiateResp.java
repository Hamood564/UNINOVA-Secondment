/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceAgent;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;


/**
 *
 * @author pi
 */
public class NegotiateResp extends ContractNetResponder{
    

    
    public NegotiateResp(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        ACLMessage reply = cfp.createReply();
         if (((ResourceAgent) myAgent).myLocation.equals("D_4")) {
            reply.setContent("1");
        } else {
            reply.setContent("2");
        }
        reply.setPerformative(ACLMessage.PROPOSE);
        return reply;
    }
    
    
    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
       ACLMessage reply = accept.createReply();
       System.out.println("Thanks for accepting");
       reply.setPerformative(ACLMessage.INFORM);
       return reply;
    }

    
    
    
    
    
    
    
    
}
