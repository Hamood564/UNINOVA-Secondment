/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceAgent;

import ProductAgent.ProductAgent;
import static ResourceAgent.ResourceAgent.myLocation;
import Utilities.Constants;
import jade.core.AID;
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
        System.out.println(getAgent().getLocalName() + ":" + "Requesting Skill location 2\" "+request.getContent() + "\" request from " 
         + request.getSender().getLocalName());
        ACLMessage reply = request.createReply();
        reply.setContent(myLocation);
        reply.setPerformative(ACLMessage.INFORM);
        return reply;
    
    }
    
    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        System.out.println(getAgent().getLocalName() + ": Requesting Skill location 1\"" + request.getContent() + "\" requested from "
            + request.getSender().getLocalName());
        
        
//        
//        ACLMessage reply = request.createReply();
//        request.getSender();
//        reply.setContent(myLocation);
//        reply.setPerformative(ACLMessage.INFORM);
//        send(reply);
        
              
        
        System.out.println(getAgent().getLocalName() + ": Finish Request \"" + request.getContent() + "\" requested from "
            + request.getSender().getLocalName());
        
         ACLMessage reply = request.createReply();
          return reply;
        
        
    }
    
}
