package ProductAgent;

import static ProductAgent.RequestLocation.thislocation;
import ResourceAgent.ResourceAgent;
import static ResourceAgent.ResourceAgent.myLocation;
import Utilities.Constants;
import Utilities.DFInteraction;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author André
 */
public class ProductAgent extends Agent{
    
    public static LinkedList<String> mySkills;
    protected int productType;
    protected String source;
    protected String nextExecutor;
    protected String myLocation = "";
    protected AID myUber;
    

    @Override
    protected void setup() {
        Object[] args = getArguments();
        //Get skills to execute
        
        mySkills = (LinkedList<String>) args[0];        
        String SkillNone = "Transport";   
        
        
     
        //////////////////////////////////////////////////////////////////////////
        //Register in DF if you get a Skill , otherwise set a 'Transport' as a Skill
       
        try {
            if (mySkills.isEmpty()){
                DFInteraction.RegisterInDF(this, SkillNone, Constants.DFSERVICE_REQUEST);
               
            }else{
                DFInteraction.RegisterInDF(this, mySkills.get(0), Constants.DFSERVICE_REQUEST); 
            }

        } catch (FIPAException ex) {
            Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        //////////////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////////////
        ///See if the Service requested is Transport or A skill execution on resource//////
        
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
           

            DFAgentDescription dft = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(Constants.DFSERVICE_RESOURCE);
            sd.setName(mySkills.get(0));  /// see if not skill then set as Transport
            dft.addServices(sd);
            
            //Search for RAs that could provide the skill
            DFAgentDescription[] MRAsFound= null;
            MRAsFound = DFService.search(this, dft);
            DFAgentDescription[] TRAfound= DFInteraction.SearchInDF(Constants.DFSERVICE_TRANSPORT, this); //Transport RAs == TransportAgent
                       
            this.myUber = TRAfound[0].getName();
            msg.addReceiver(TRAfound[0].getName());
            System.out.println("Resource Agent: " + MRAsFound[0].getName());
            System.out.println("Transporting: "+TRAfound[0].getName());                
            msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
            // if no resource found or no skill required
            if ((mySkills.isEmpty()) || (MRAsFound.length == 0)){
                System.out.println("Skill to Execute: " + SkillNone);
                //Si coded previously in transport agent that instructs the TA to get PA out of system
                msg.setContent("Si");
                mySkills.clear();
            } else{
            //else provide location to move PA to RA location
                System.out.println("Skill to Execute: " + mySkills.get(0));
                msg.addReceiver(MRAsFound[0].getName());
                msg.setOntology(Constants.ONTOLOGY_LOCATION);
                msg.setContent("location");
                this.addBehaviour(new RequestLocation(this,msg)); 
                //introduce SLEEP behavior               
                //assigning the location recieved from RA
                

            }
                
             
                //Thread continued in RequestTransport
                
            
        }catch (FIPAException ex) {
            Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //////////////////////////////////////////////////////////////////////////////////////////
 
         //////////////////////////////////////////////////////////////////////////////////////////
    }
   
    
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////Program Derigister Behavior/////////////////////////////////////////////////

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException ex) {
            Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void setLocation(String loc){
            this.myLocation = loc;        
    }



}



