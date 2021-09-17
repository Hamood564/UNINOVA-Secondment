/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductAgent;

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
        if (((ProductAgent)myAgent).mySkills.isEmpty()){
            System.out.println(getAgent().getLocalName() + ": " + inform.getSender().getLocalName()
					+ " replied with an inform: Skill was succesfully executed and now exiting..." + "\n");
			
              ((ProductAgent) myAgent).mySkills.clear();
              ((ProductAgent) myAgent).mySkills.add("Si");
	    return;
            
            
        }else {
			System.out.println(getAgent().getLocalName() + ": " + inform.getSender().getLocalName()
					+ " replied with an inform: Skill is being executed ");
		}
        
        
        boolean condition = true;
        
        if (((ProductAgent) myAgent).mySkills.get(0).equals("")) {
			if (condition) {

			} else {
				((ProductAgent) myAgent).mySkills.clear();
				((ProductAgent) myAgent).mySkills.add("Si");
			}
		}
	System.out.println("Executing the skill:" + "\"" + ((ProductAgent)
				myAgent).mySkills.get(0) + "\"");
        
        if (!((ProductAgent) myAgent).mySkills.isEmpty()) {
			// get next skill
                        System.out.println(((ProductAgent) myAgent).mySkills);

			String nextSkill = ((ProductAgent) myAgent).mySkills.get(0);
                        System.out.println(nextSkill);
			// Search in DF for the agents that execute the Skill PA desires
			
                        DFAgentDescription dft = new DFAgentDescription();
			ServiceDescription test = new ServiceDescription();
			test.setType(Constants.DFSERVICE_RESOURCE);
			test.setName(nextSkill);
			dft.addServices(test);
                        

			//
			// print next skill
			// System.out.println("Here goes the skill: "+nextSkill);
			//

			DFAgentDescription[] MRAsFound = null;
			try {
		        MRAsFound = DFService.search(((ProductAgent) myAgent), dft);
                        System.out.println(MRAsFound[0].getName());
//                      MRAsFound = DFInteraction.SearchInDF(nextSkill, ((ProductAgent) myAgent));
			} catch (FIPAException ex) {
				Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
			}
                        
                        
                        //HERE...............
                        // create Request message
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			// Lest assume that we always find at least one MRA
			AID receiverAID = MRAsFound[0].getName();
                        System.out.println(receiverAID);
			msg.addReceiver(receiverAID);
			msg.setContent("location");
			// Add ExecuteSkillInitiator behaviour
			((ProductAgent) myAgent).addBehaviour(new RequestResource(((ProductAgent) myAgent), msg));
		} else {
			// PA leaves the platform
			((ProductAgent) myAgent).doDelete();
		}
    
}
}

        
