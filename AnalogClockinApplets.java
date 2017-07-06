import java.applet.*;
import java.awt.*;
import java.awt.event.*;
public class he extends Applet implements Runnable{
	Thread t;
	double x1,y1,x2,y2,x3,y3,angle1,angle2,angle3;
	public void init(){
		t = new Thread(this);
		t.start();
	}
	public void paint(Graphics g){
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		g.drawOval(50,50,200,200);
		g.setColor(Color.WHITE);
		g.drawLine(150,150,(int)(x1),(int)(y1));
		g.setColor(Color.YELLOW);
		g.drawLine(150,150,(int)(x2),(int)(y2));
		g.setColor(Color.GREEN);
		g.drawLine(150,150,(int)(x3),(int)(y3));
	}
	public void run(){
		angle1 = angle2 = angle3 = Math.acos(0);
		int cs = 0; //Count second
		int cm = 0; //Count minute
		x1 = x2 = x3 = 150;
		y1 = y2 = y3 = 80;
		repaint();
		while(true){
			x3 = 150.0+Math.cos(angle1)*80.0;
			y3 = 150.0-Math.sin(angle1)*80.0;
			try{Thread.sleep(100);}catch(InterruptedException e){}
			angle1 -= (3.14/180)*6;
			cs++;
			if(cs==60){
				cs=0;
				angle2 -= (3.14/180)*6;
				x2 = 150.0+Math.cos(angle2)*70.0;
				y2 = 150.0-Math.sin(angle2)*70.0;
				cm++;
				if(cm==60){
					cm=0;
					angle3 -= (3.14/180)*360/24;
					x1 = 150.0+Math.cos(angle3)*60.0;
					y1 = 150.0-Math.sin(angle3)*60.0;
				}
			}
			repaint();
		}
	}
}
/*
<applet code="he.class" height="300" width="300"></applet>
*/
