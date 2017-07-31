import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class Data{
	int[] buffer = new int[5];
	boolean mutex = false;
	int full = 0;
	public String produce(){
		while(full==5){
			try{Thread.sleep(100);}catch(InterruptedException e){}
		}
		while(mutex){
			try{Thread.sleep(100);}catch(InterruptedException e){}
		}
		mutex = true;
		int data = 0;
		String s = "";
		for(int i=0;i<8;i++){
			int x = (int)(Math.random()*20);
			if(x%2!=0){
				data += (1<<i);
				s += "1";
			}
			else{
				s += "0";
			}
		}
		buffer[full] = data;
		try{Thread.sleep(100);}catch(InterruptedException e){}
		full++;
		mutex = false;
		return s;
	}
	public int consume(){
		while(full==0){
			try{Thread.sleep(100);}catch(InterruptedException e){}
		}
		while(mutex){
			try{Thread.sleep(100);}catch(InterruptedException e){}
		}
		mutex = true;
		int ret = buffer[0];
		for(int i=0;i<full-1;i++){
			buffer[i] = buffer[i+1];
		}
		try{Thread.sleep(100);}catch(InterruptedException e){}
		full--;
		mutex = false;
		return ret;
	}
}
class DSGenerator extends Thread{
	Data d;
	JFrame f;
	JLabel l;
	DSGenerator(Data obj,JFrame f){
		super();
		d = obj;
		this.f = f;
		l = new JLabel("");
		l.setBounds(500,500,200,100);
		f.add(l);
	}
	public void run(){
		while(true){
			String s = d.produce();
			l.setText(s);
			try{Thread.sleep(1000);}catch(InterruptedException e){}
		}
	}
}
class Converter extends Thread{
	Data d;
	JFrame f;
	int x,y;
	Converter(Data obj,JFrame f){
		super();
		d = obj;
		this.f = f;
		y = 400;
		x = 50;
	}
	public void run(){
		Graphics g = f.getGraphics();
		while(true){
			int data = d.consume();
			g.setColor(Color.BLUE);
			g.fillRect(x,y-data,20,data);
			x += 21;
			try{Thread.sleep(1000);}catch(InterruptedException e){}
		}
	}
}
class DAC{
	public static void main(String args[]){
		Data d = new Data();
		JFrame f = new JFrame();
		f.setSize(1000,700);
		f.setVisible(true);
		f.setLayout(null);
		DSGenerator dsg = new DSGenerator(d,f);
		Converter c = new Converter(d,f);
		dsg.start();
		try{Thread.sleep(2000);}catch(InterruptedException e){}
		c.start();
	}
}
