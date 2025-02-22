package ResourceAgent;

import ResourceAgent.ResourceAgent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

@ClientEndpoint
public class ChatClientEndpoint  {
	private static CountDownLatch latch;
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println ("--- Connected " + session.getId());
		try {
			session.getBasicRemote().sendText("Sk_execute");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@OnMessage
	public String onMessage(String message, Session session) {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println ("--- Received " + message);
                        if (message.equals("0")){
                            System.out.println("Closing session...");
                            CloseReason closeReason = null;
                            onClose(session, closeReason );
                            session.close();
                        }else{
                           
                        }
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
            return null;
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println("Session " + session.getId() +
			" closed because " + closeReason);

	}
	
        
        
        
        
	public static void main(String[] args) {
		latch = new CountDownLatch(1);
		ClientManager client = ClientManager.createClient();
		try {
			URI uri = new URI("ws://192.168.1.10:3000/");
			client.connectToServer(ChatClientEndpoint.class, uri);
			latch.await();
		} catch (DeploymentException | URISyntaxException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}