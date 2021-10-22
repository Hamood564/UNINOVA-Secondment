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
    public static String SkillNone;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        //Get skills to execute
        
        mySkills = (LinkedList<String>) args[0];        
        SkillNone = "Transport";   
        
        
     
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
             if (mySkills.isEmpty()){
                 sd.setName(SkillNone);
             }else{
                 sd.setName(mySkills.get(0));
                 System.out.println(("PA......." +mySkills.get(0)));
             }
            
            dft.addServices(sd);
            
            //Search for RAs that could provide the skill
            DFAgentDescription[] MRAsFound= null;
            MRAsFound = DFService.search(this, dft);
            DFAgentDescription[] TRAfound= DFInteraction.SearchInDF(Constants.DFSERVICE_TRANSPORT, this); //Transport RAs == TransportAgent
            
            
            
            this.myUber = TRAfound[0].getName();
            msg.addReceiver(TRAfound[0].getName());
           
            msg.setOntology(Constants.ONTOLOGY_TRANSPORT);
            // if no resource found or no skill required
            if ((mySkills.isEmpty()) || (MRAsFound.length == 0)){
                System.out.println("Skill to Execute: " + SkillNone);
                //Si coded previously in transport agent that instructs the TA to get PA out of system
                msg.setContent("Si");
                mySkills.clear();
                this.addBehaviour(new RequestTransport(this, msg));
            } else{
                
                ACLMessage[] cfp = new ACLMessage[MRAsFound.length];
                for (int i = 0; i < MRAsFound.length; i++) {
                    //build the object, very important.
                    cfp[i] = new ACLMessage(ACLMessage.CFP);
                    //cfp[i].setPerformative(ACLMessage.CFP);
                    cfp[i].addReceiver(MRAsFound[i].getName());
                    cfp[i].setOntology(Constants.ONTOLOGY_RESOURCE);
                    this.addBehaviour(new Negotiate(this, cfp[i]));


            }
            
        }
 
         //////////////////////////////////////////////////////////////////////////////////////////
    }   catch (FIPAException ex) {
            Logger.getLogger(ProductAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
   
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
    /////////////////////////////////////////////////////////////////////////////////////////////    /////////////////////////////////////////////////////////////////////////////////////////////

    protected void setLocation(String loc){
            this.myLocation = loc;        
    }



}



