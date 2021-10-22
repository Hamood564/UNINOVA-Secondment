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
public class ResourceLocation extends AchieveREResponder {
    
     public ResourceLocation(Agent a, MessageTemplate mt) {
		super(a, mt);    
                
	}
    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        System.out.println(request.getSender().getLocalName()+ ":" + "Requesting Skill location 2\" "+request.getContent() + "\" request from " 
         + getAgent().getLocalName());
        ACLMessage reply = request.createReply();
        System.out.println("RL......" +((ResourceAgent)myAgent).getAID() + ((ResourceAgent)myAgent).myLocation);
        reply.setContent(((ResourceAgent)myAgent).myLocation);
        reply.setPerformative(ACLMessage.INFORM);
        return reply;
    
    }
    
    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        System.out.println(request.getSender().getLocalName() + ": Requesting Skill location 1\"" + request.getContent() + "\" requested from "
            + getAgent().getLocalName());
        System.out.println(request.getSender().getLocalName()+ ": Finish Request \"" + request.getContent() + "\" requested from "
            + getAgent().getLocalName());
         ACLMessage reply = request.createReply();
         return reply;
        
        
    }
    
}
