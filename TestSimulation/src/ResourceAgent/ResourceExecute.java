/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceAgent;

import ProductAgent.ProductAgent;
import static ResourceAgent.ResourceAgent.myLocation;
import Utilities.Constants;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import jade.core.Agent;
import jade.core.Location;
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
        
        

        block(5000);
        System.out.println("I AM BLOCKING HEREEEEEEEEEEEEEEEEEEEEE");
    
        System.out.println(getAgent().getLocalName() + ": Finish Execution of Skill \"" + request.getContent() + "\" requested from "
            + request.getSender().getLocalName());
        
      
        ACLMessage reply = request.createReply();
        
        reply.setPerformative(ACLMessage.INFORM);
       return reply;
    }
    
}
