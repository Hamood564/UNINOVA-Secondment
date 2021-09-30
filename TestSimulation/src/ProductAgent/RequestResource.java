/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

import ResourceAgent.ResourceAgent;
import Utilities.Constants;
import Utilities.DFInteraction;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hamoo
 */
public class RequestResource extends  AchieveREInitiator{
    
    public RequestResource(Agent a, ACLMessage msg) {
        super(a, msg);
    }
    
    @Override
    protected void handleAgree(ACLMessage agree) {
       System.out.println(getAgent().getLocalName() + ": " + agree.getSender().getLocalName()
				+ " replied with an agree: Skill can be succesfully executed");
    }

    @Override
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println(getAgent().getLocalName() + ": " + refuse.getSender().getLocalName()
				+ " replied with an agree: Skill cannot be executed");
    }
    
     @Override
    protected void handleInform(ACLMessage inform) {
              
        //executed the skill        
	System.out.println("Executed the skill:" + "\"" + ((ProductAgent)
				myAgent).mySkills.remove(0) + "\"");
        
        //searching for the next skill to execute for PA
        String nextSkill;
        if (((ProductAgent) myAgent).mySkills.isEmpty()){
           nextSkill = null;
        } else{
            nextSkill = ((ProductAgent) myAgent).mySkills.get(0);
        }
        String Location = ResourceAgent.myLocation;


        DFAgentDescription[] MRAsFound = null;
        try {
            MRAsFound = DFInteraction.SearchInDF(Constants.DFSERVICE_RESOURCE, ((ProductAgent) myAgent));
        } catch (FIPAException ex) {
            Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        if (MRAsFound.length == 0 || nextSkill == null){

                // PA leaves the platform
                ((ProductAgent) myAgent).mySkills.clear();


                DFAgentDescription dft_leave= new DFAgentDescription();
                ServiceDescription tr_leave = new ServiceDescription();
                tr_leave.setType(Constants.DFSERVICE_TRANSPORT);
                tr_leave.setName("TransportAgent");
                dft_leave.addServices(tr_leave);
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

                DFAgentDescription[] TRAsFound = null;
            try {
                TRAsFound = DFService.search(((ProductAgent) myAgent), dft_leave);
                 AID receiverAID = TRAsFound[0].getName();
                System.out.println("Transporter" +receiverAID);
                msg.addReceiver(receiverAID);
                msg.setContent("Si");
                msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
                
                ((ProductAgent) myAgent).myLocation = null;
                
                
                ((ProductAgent) myAgent).addBehaviour(new RequestTransport(((ProductAgent) myAgent), msg));  
            } catch (FIPAException ex) {
                Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
            }


    }  else{


            System.out.println(MRAsFound[0].getName());
            //HERE...............
            // create Request message
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            // Lest assume that we always find at least one MRA
            DFAgentDescription dftt = new DFAgentDescription();
            ServiceDescription tr = new ServiceDescription();
            tr.setType(Constants.DFSERVICE_TRANSPORT);
            tr.setName("TransportAgent");
            dftt.addServices(tr);
            try {
                DFAgentDescription[] TRAsFound = DFService.search(((ProductAgent) myAgent), dftt);
                AID receiverAID = TRAsFound[0].getName();
                System.out.println("Transporter" +receiverAID);
                msg.addReceiver(receiverAID);
                msg.setContent(Location);
                msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
                System.out.println(Location);
            } catch (FIPAException ex) {
                Logger.getLogger(RequestResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Add Transport behaviour
            ((ProductAgent) myAgent).addBehaviour(new RequestTransport(((ProductAgent) myAgent), msg));
        }


        }  

                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                           
    
}


        
