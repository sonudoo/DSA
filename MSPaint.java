import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class he extends JFrame{
	int brushSize = 10;
	Color brushColor;
	he(){
		setVisible(true);
		setLayout(null);
		setSize(500,500);
		JButton r = new JButton();
		JButton g = new JButton();
		JButton b = new JButton();
		JButton plus = new JButton();
		plus.setBounds(250,20,50,40);
		JButton minus = new JButton();
		plus.setText("+");
		minus.setText("-");
		minus.setBounds(320,20,50,40);
		plus.setVisible(true);
		minus.setVisible(true);
		add(plus);
		add(minus);
		r.setBackground(Color.RED);
		g.setBackground(Color.GREEN);
		b.setBackground(Color.BLUE);
		r.setBounds(100,20,40,40);
		g.setBounds(150,20,40,40);
		b.setBounds(200,20,40,40);
		r.setVisible(true);
		g.setVisible(true);
		b.setVisible(true);
		add(r);
		add(g);
		add(b);
		Canvas c = new Canvas();
		c.setBounds(0,70,500,400);
		setBackground(Color.GRAY);
		c.setBackground(Color.WHITE);
		add(c);
		c.setVisible(true);
		r.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				brushColor = Color.RED;
			}
		});
		g.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				brushColor = Color.GREEN;
			}
		});
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				brushColor = Color.BLUE;
			}
		});
		plus.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				brushSize++;
			}
		});
		minus.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(brushSize>1){
					brushSize--;
				}
			}
		});
		c.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e){
				Graphics _g = c.getGraphics();
				_g.setColor(brushColor);
				_g.fillOval(e.getX(),e.getY(),brushSize,brushSize);
			}
			public void mouseMoved(MouseEvent e){

			}
		});

	}
	public static void main(String[] args){
		he a = new he();
	}
}