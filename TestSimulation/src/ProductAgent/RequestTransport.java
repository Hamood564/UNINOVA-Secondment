/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

/**
 *
 * @author André Dionisio Rocha
 */
public class RequestTransport extends AchieveREInitiator {

    public RequestTransport(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleAgree(ACLMessage agree) {
        System.out.println("Transporting");
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println("Transport refused");
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println("Transport finished: Arrived " + inform.getSender().getLocalName());
    }
}
