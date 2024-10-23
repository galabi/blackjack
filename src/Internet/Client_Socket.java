package Internet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client_Socket implements Runnable{
	Socket s;
	BufferedReader in;
	PrintWriter out;
	String str = "hi";


	@Override
	public void run() {
    	try {
        	s = new Socket("localhost",1234);  
        	in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        	out = new PrintWriter(s.getOutputStream(),true);
        	while(!str.equals("stop")){  
            	if(str != "") {
            		out.println(str);
            	} 
            	Thread.sleep(1000);
            	}  
            	  
            	s.close();  
		} catch (Exception e) {
			// TODO: handle exception
		}

		
	}
}
