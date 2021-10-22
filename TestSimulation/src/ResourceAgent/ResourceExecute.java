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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;



/**
 *
 * @author hamoo
 */
public class ResourceExecute extends AchieveREResponder {
    private static  ClientManager client;
   
    
    public ResourceExecute(Agent a, MessageTemplate mt) {
		super(a, mt);       
	}
    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        System.out.println(request.getSender().getLocalName() + ":" + "Execute Skill\" "+request.getContent() + "\" request from " 
         + getAgent().getLocalName());
        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.AGREE);
        return reply;
    
    }
    
    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
        System.out.println(request.getSender().getLocalName() + ": Starting Execution of Skill \"" + request.getContent() + "\" requested from "
            + getAgent().getLocalName());

        
        
        client = ClientManager.createClient();
                   ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
                    URI uri;           
        ACLMessage reply = request.createReply(); 
        try {
            uri = new URI("ws://192.168.1.10:3000/");
            Session session = client.connectToServer(new ChatClientEndpoint(), cec,  uri);
            System.out.println("the session id is: " + session.getId());
            ((ResourceAgent)myAgent).executeover = session.getId();
            while(true){
                //add timestamp +10 if the session.close is not grabbed
                if(session.isOpen() == false && session.getId().equals(((ResourceAgent)myAgent).executeover)){
                   System.out.println(request.getSender().getLocalName() + ": Finish Execution of Skill \"" + request.getContent() + "\" requested from "
       + getAgent().getLocalName());
                reply.setPerformative(ACLMessage.INFORM);
                break;
                }
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResourceExecute.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeploymentException ex) {
            Logger.getLogger(ResourceExecute.class.getName()).log(Level.SEVERE, null, ex);
        }
           return reply;  
    }
}
        
    
    

    
    

