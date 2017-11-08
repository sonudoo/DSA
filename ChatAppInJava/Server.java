import java.io.*;
import java.net.*;
import java.util.*;
class HandleClient extends Thread{
	static Socket clients[] = new Socket[1000];
	static int i = 0;
	int curr = -1;
	HandleClient(Socket s){
		super();
		clients[i] = s;
		curr = i;
		i++;
		System.out.println("Client "+curr+" connected");
	}
	public void run(){
		while(true){
			DataInputStream dis;
			String s;
			try{
				dis = new DataInputStream(HandleClient.clients[curr].getInputStream());
				s = (String)dis.readUTF();
			}
			catch(Exception e){
				continue;
			}
			s = "Client "+curr+": "+s;
			System.out.println(s);
			for(int j=0;j<HandleClient.i;j++){
				if(j!=curr){
					try{
						DataOutputStream dout = new DataOutputStream(HandleClient.clients[j].getOutputStream());
						dout.writeUTF(s);
					}
					catch(Exception e){
						System.out.println("Error occured in writing");
					}
				}
			}
		}
	}
}
class Server{
	public static void main(String args[]){
		try{
			ServerSocket ss = new ServerSocket(6666);
			while(true){
				HandleClient hc = new HandleClient(ss.accept());
				hc.start();
			}
		}
		catch(Exception e){

		}
	}

}
