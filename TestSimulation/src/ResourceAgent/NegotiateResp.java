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
        
        for (int i = 0; i < ((ResourceAgent) myAgent).mySkills.size(); i++) {
            
            if (((ResourceAgent) myAgent).mySkills.get(i).equals("basic")){
                System.out.println("Modifying configuration for basic");
                
                
                reply.setContent("basic skill can be executed");
                
            }
            
            
        }
        
        
        
        
        
        if (((ResourceAgent) myAgent).mySkills.get(0).equals("basic")){
            block(5000);
            
            
            
            reply.setContent("basic skill can be executed");
            
        }
        
        if (((ResourceAgent) myAgent).mySkills.get(0).equals("cond")){
            block(5000);
            
            
            
            reply.setContent("cond skill can be executed");
            
        }
        
        if (((ResourceAgent) myAgent).mySkills.get(0).equals("stab")){
            block(5000);
            
            
            
            reply.setContent("stab skill can be executed");
            
        }
        
        
        if (((ResourceAgent) myAgent).mySkills.get(0).equals("param")){
            block(5000);
            
            
            
            reply.setContent("param skill can be executed");
            
        }
        
        if (((ResourceAgent) myAgent).mySkills.get(0).equals("fill")){
            block(5000);
            
            
            
            reply.setContent("fill skill can be executed");
            
        }
        
        if (((ResourceAgent) myAgent).mySkills.get(0).equals("iter")){
            block(5000);
            
            
            
            reply.setContent("iter skill can be executed");
            
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
