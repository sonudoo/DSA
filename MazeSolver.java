import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class MazeSolver{
	public static void main(String[] args){
		int n,m,x1,y1,x2,y2;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the size of grid (n m): ");
		n = sc.nextInt();
		m = sc.nextInt();
		System.out.println("Enter the grid: ");
		int[][] grid = new int[n][m];
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				grid[i][j] = sc.nextInt();
			}
		}
		System.out.print("Enter the starting and ending coordinates (x1,y1),(x2,y2): ");
		x1 = sc.nextInt();
		y1 = sc.nextInt();
		x2 = sc.nextInt();
		y2 = sc.nextInt();
		System.out.println("Generating grid. Please wait...");
		JFrame f = new JFrame();
		f.setSize(70+11*m,70+11*n);
		f.setLayout(null);
		JPanel[][] p = new JPanel[n][m];
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				p[i][j] = new JPanel();
				p[i][j].setBounds(10+11*j,10+11*i,10,10);
				if(grid[i][j]==1)
					p[i][j].setBackground(Color.GREEN);
				else
					p[i][j].setBackground(Color.RED);
				f.add(p[i][j]);
				p[i][j].setVisible(true);
			}
		}
		f.setVisible(true);
		System.out.println("Generation successful. Solving grid..");
		class Pair{
			int x;
			int y;
			Pair(int x,int y){
				this.x = x;
				this.y = y;
			}
		}
		x1--;y1--;x2--;y2--;
		Stack <Pair> st = new <Pair> Stack();
		boolean[][] visited = new boolean[n][m];
		boolean[][] no_way = new boolean[n][m];
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				visited[i][j] = false;
				no_way[i][j] = false;
			}
		}
		st.push(new Pair(x1,y1));
		while(!st.empty()){
			try{Thread.sleep(50);}catch(InterruptedException e){}
			int x = ((Pair)st.peek()).x;
			int y = ((Pair)st.peek()).y;
			if(visited[x][y]==true){
				visited[x][y] = false;
				no_way[x][y] = true;
				p[x][y].setBackground(Color.GREEN);
				st.pop();
				continue;
			}
			visited[x][y] = true;
			p[x][y].setBackground(Color.BLUE);
			if(x==x2 && y==y2){
				break;
			}
			if(x+1<n && grid[x+1][y]==1 && visited[x+1][y]==false && no_way[x+1][y]==false){
				st.push(new Pair(x+1,y));
			}
			if(x-1>=0 && grid[x-1][y]==1 && visited[x-1][y]==false && no_way[x-1][y]==false){
				st.push(new Pair(x-1,y));
			}
			if(y+1<m && grid[x][y+1]==1 && visited[x][y+1]==false && no_way[x][y+1]==false){
				st.push(new Pair(x,y+1));
			}
			if(y-1>=0 && grid[x][y-1]==1 && visited[x][y-1]==false && no_way[x][y-1]==false){
				st.push(new Pair(x,y-1));
			}
		}
	}
}
