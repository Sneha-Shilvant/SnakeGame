package snake.com;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;




public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY=75;
	final int x[]=new int[GAME_UNITS];
	final int Y[]=new int[GAME_UNITS];
	int bodyparts=6;
	int foodeaten;
	int foodx;
	int foody;
	char direction = 'R';
	Timer timer;
	Random random;
	boolean running=false;
	
	
	GamePanel(){
		random =new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newFood();
	    running = true;
		timer=new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
  public void draw(Graphics g) {
	  if(running) {
	   /*   for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
		     g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
		     g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
	    }
	    */
	    g.setColor(Color.red);
	    g.fillOval(foodx, foody, UNIT_SIZE, UNIT_SIZE);
	  
	    for(int i=0;i< bodyparts;i++) {
		  if(i==0) {
			  g.setColor(Color.green);
			  g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);
		  }else {
			  g.setColor(new Color(45,180,0));
			  g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);
		  }
	  }
	    g.setColor(Color.red);
		  g.setFont(new Font("Ink Free", Font.BOLD,75));
		  FontMetrics metrices=getFontMetrics(g.getFont());
		  g.drawString("Score: "+foodeaten, (SCREEN_WIDTH-metrices.stringWidth("Score: "+foodeaten))/2,g.getFont().getSize());
		  
		  
	  }else {
		  gameOver(g);
	  }
		
	}
  public void move() {
	  for(int i=bodyparts;i>0;i--) {
		  x[i]=x[i-1];
		  Y[i]=Y[i-1];
	  }
	  switch(direction) {
	  case 'U':
		  Y[0]=Y[0]-UNIT_SIZE;
		  break;
	  case 'D':
		  Y[0]=Y[0]+UNIT_SIZE;
		  break;
	  case 'L':
		  x[0]=x[0]-UNIT_SIZE;
		  break;
	  case 'R':
		  x[0]=x[0]+UNIT_SIZE;
		  break;
	  }
		
	}
  public void newFood() {
	  foodx =random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	  foody =random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	  
  }
  public void checkfood() {
	  if((x[0]==foodx)&&(Y[0]==foody)) {
		  bodyparts++;
		  foodeaten++;
		  newFood();
	  }
		
	}
  public void checkCollision() {
	  for(int i=bodyparts;i>0;i--) {
		  if((x[0]==x[i])&&(Y[0]==Y[i])) {
			  running =false;
			  
		  }
	  }
	  if(x[0]<0) {
		  running=false;
	  }
	  if(x[0]>SCREEN_WIDTH) {
		  running=false;
	  }
	  if(Y[0]<0) {
		  running=false;
	  }
	  if(Y[0]>SCREEN_HEIGHT) {
		  running=false;
	  }
		
	}
  public void gameOver(Graphics g) {
	  g.setColor(Color.red);
	  g.setFont(new Font("Ink Free", Font.BOLD,75));
	  FontMetrics metrices=getFontMetrics(g.getFont());
	  g.drawString("Score: "+foodeaten, (SCREEN_WIDTH-metrices.stringWidth("Score: "+foodeaten))/2,g.getFont().getSize());
	  
	  g.setColor(Color.red);
	  g.setFont(new Font("Ink Free", Font.BOLD,75));
	  FontMetrics metrices2=getFontMetrics(g.getFont());
	  g.drawString("Game Over", (SCREEN_WIDTH-metrices2.stringWidth("Game over"))/2, SCREEN_HEIGHT/2);
	  
	  
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkfood();
			checkCollision();
		}
		repaint();
		
	}
	public class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction='D';
				}
				break;
			}
			
		}
	}

}
