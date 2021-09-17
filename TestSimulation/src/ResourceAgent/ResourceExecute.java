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
import jade.proto.AchieveREResponder;

/**
 *
 * @author hamoo
 */
public class ResourceExecute extends AchieveREResponder {
    
    public ResourceExecute(Agent a, MessageTemplate mt) {
		super(a, mt);       
	}
    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        System.out.println(getAgent().getLocalName() + ":" + "Execute Skill\" "+request.getContent() + "\" request from " 
         + request.getSender().getLocalName());
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.AGREE);
        return reply;
    
    }
    
    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        System.out.println(getAgent().getLocalName() + ": Starting Execution of Skill \"" + request.getContent() + "\" requested from "
            + request.getSender().getLocalName());
    
        System.out.println(getAgent().getLocalName() + ": Finish Execution of Skill \"" + request.getContent() + "\" requested from "
            + request.getSender().getLocalName());
        
        if(request.getContent().equals("conddo"))
        {
            
        }
                
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        
              
        return reply;
    }
    
}
