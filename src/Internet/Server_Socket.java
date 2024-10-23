package Internet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Socket implements Runnable{
	ServerSocket ss;
	Socket s;
	BufferedReader in;
	PrintWriter out;
	String str="";
	boolean serverUP = false;
	

	@Override
	public void run() {
		serverUP = true;
		try {
			ss = new ServerSocket(1234);  
			s = ss.accept();  
        	out = new PrintWriter(s.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(!str.equals("stop")){  
				str = in.readLine();  
				if(str != null && str != "") {
					System.out.println("[server]"+str);
					
				}
            	Thread.sleep(1000);
				}  
				s.close();  
				ss.close();  
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public boolean isServerUp() {
		return serverUP;
	}
}
