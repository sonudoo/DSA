import java.io.*;
import java.util.*;
import java.net.*;
class ServerHandler extends Thread{
	Socket s;
	ServerHandler(Socket s){
		super();
		this.s = s;
	}
	public void run(){
		while(true){
			String msg;
			try{
				DataInputStream dis = new DataInputStream(s.getInputStream());
				msg = (String)dis.readUTF();
			}
			catch(Exception e){
				continue;
			}
			for(int i=0;i<5;i++){
				System.out.print("\b");
			}
			System.out.println(msg);
			System.out.print("You: ");
		}
	}
}
class Client{
	public static void main(String args[]) throws IOException{
		Scanner sc = new Scanner(System.in);
		Socket s = new Socket("localhost",6666);
		ServerHandler sh = new ServerHandler(s);
		sh.start();
		while(true){
			System.out.print("You: ");
			String msg = sc.nextLine();
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			dout.writeUTF(msg);
		}
	}
}
