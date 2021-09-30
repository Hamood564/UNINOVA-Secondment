/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

import Utilities.Constants;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

/**
 *
 * @author hamoo
 */
public class RequestLocation extends AchieveREInitiator {
    public static String thislocation;

    public RequestLocation(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
//         thislocation = agree.getContent();
//        System.out.println("RL....:" + thislocation);
//       ((ProductAgent) myAgent).setLocation(thislocation); 
//        System.out.println("ProductAgent.RequestLocation.handleAgree() " + ((ProductAgent) myAgent).myLocation);
//       ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
//       msg.addReceiver(((ProductAgent) myAgent).myUber);
//       msg.setContent(((ProductAgent) myAgent).myLocation );
//      msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
//      ((ProductAgent) myAgent).addBehaviour(new RequestTransport(((ProductAgent) myAgent),msg)); 
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println("Transport refused");
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        thislocation = inform.getContent();
        System.out.println("RL....:" + thislocation);
      System.out.println("RL....:" + thislocation);
       ((ProductAgent) myAgent).setLocation(thislocation); 
        System.out.println("ProductAgent.RequestLocation.handleAgree() " + ((ProductAgent) myAgent).myLocation);
       ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
       msg.addReceiver(((ProductAgent) myAgent).myUber);
       msg.setContent(((ProductAgent) myAgent).myLocation );
      msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
      ((ProductAgent) myAgent).addBehaviour(new RequestTransport(((ProductAgent) myAgent),msg)); 
       
}
    
}

