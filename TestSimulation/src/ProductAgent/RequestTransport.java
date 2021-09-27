/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

import static ProductAgent.ProductAgent.mySkills;
import static ResourceAgent.ResourceAgent.myLocation;
import Utilities.Constants;
import Utilities.DFInteraction;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author André Dionisio Rocha
 */
public class RequestTransport extends AchieveREInitiator {
         
        String SkillNone = "Transport";   
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
        
//        
//        
//        ACLMessage t_msg = new ACLMessage(ACLMessage.REQUEST);
//            
//            //Launch Behaviour to manage Product Execution here
//            t_msg.setContent(myLocation);
//            System.out.println(myLocation);
//            t_msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
            
        
        System.out.println("Transport finished: Arrived " + inform.getSender().getLocalName());
        System.out.println("Executing next....");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        DFAgentDescription dft = new DFAgentDescription();
        ServiceDescription test = new ServiceDescription();
        
        if (!((ProductAgent) myAgent).mySkills.isEmpty()){
            test.setType(Constants.DFSERVICE_RESOURCE);
            String nextSkill = ((ProductAgent) myAgent).mySkills.remove(0);
            test.setName(nextSkill);
            dft.addServices(test);
            DFAgentDescription[] MRAFound = null;
                try {
                    MRAFound = DFService.search(((ProductAgent) myAgent), dft);
                    msg.setContent(mySkills.get(0));
                    System.out.println("1st skill: " + mySkills.get(0));
                    msg.addReceiver(MRAFound[0].getName());
                    msg.setOntology(Constants.ONTOLOGY_RESOURCE);
                    ((ProductAgent) myAgent).addBehaviour(new RequestResource(((ProductAgent) myAgent), msg)); 
                } catch (FIPAException ex) {
                    Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
                }
         }else{
            DFAgentDescription[] TRAfound;
            DFAgentDescription dftt = new DFAgentDescription();
            ServiceDescription tr = new ServiceDescription();
            tr.setType(Constants.DFSERVICE_TRANSPORT);
            tr.setName("TransportAgent");
            dftt.addServices(tr);
            try {
                TRAfound = DFService.search(((ProductAgent) myAgent), dftt);
                msg.setContent(SkillNone);
                System.out.println("1st skill: " + SkillNone);
                msg.addReceiver(TRAfound[0].getName());
                System.out.println("Transporting: "+TRAfound[0].getName());
                msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
                msg.setContent("Si");
                ((ProductAgent) myAgent).addBehaviour(new RequestTransport(((ProductAgent) myAgent), msg)); 
            } catch (FIPAException ex) {
                Logger.getLogger(RequestTransport.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
        
        
        
        
            
                
        
        
    }
}
