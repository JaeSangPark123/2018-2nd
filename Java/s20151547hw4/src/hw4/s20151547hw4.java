package hw4;
import java.awt.*;
import java.awt.event.*;
public class s20151547hw4 extends Frame implements ActionListener {
	private Canvas canvas= new Canvas();
	private Ball[] B = new Ball[5];
	public s20151547hw4(String title) {
		super(title);
	
		add("Center", canvas);
		Panel p = new Panel();
		B[0] = new Ball(canvas,500,20,128,1,-1,1);
		B[1] = new Ball(canvas,250,220,128,-1,-1,2);
		B[2] = new Ball(canvas,1100,570,128,2,2,3);
		B[3] = new Ball(canvas,900,620,128,-1,-2,4);
		B[4] = new Ball(canvas,560,800,128,2,-2,5);
		Button s = new Button("Start");
		Button c = new Button("Close");
		p.add(s); p.add(c);
		s.addActionListener(this);
		c.addActionListener(this);
		add("South", p); 
		}
	public void actionPerformed(ActionEvent evt) {
	if (evt.getActionCommand() == "Start") {
		B[0].start();
		B[1].start();
		B[2].start();
		B[3].start();
		B[4].start();
	}
	else if (evt.getActionCommand() == "Close")
		System.exit(0); 
	}
	public static void main(String[] args) {
		Frame f = new s20151547hw4("Bounce Thread");
		f.setSize(1200, 900);
		WindowDestroyer listener = new WindowDestroyer();
		f.addWindowListener(listener);
		f.setVisible(true); 
	}

	class Ball extends Thread implements Runnable{
		private Canvas box;
		private int number=0;
		private int[] x = new int [9];
		private int[] y = new int [9];
		private double[] SIZE=new double [9];
		private int[] dx = new int [9];
		private int[] dy = new int [9];
		private int[] flag =new int [9];
		private int gaesu =1;
		public Ball(Canvas c,int a,int b,double SI,int d1,int d2,int num) {
			box = c;
			number = num;
			this.x[0] = a;
			this.y[0] = b;
			this.SIZE[0] = SI;
			this.dx[0] = d1;
			this.dy[0] = d2;
			for(int i=1;i<6;i++) {

				this.x[i] = 0;
				this.y[i] = 0;
				this.SIZE[i] = 0.0;
				this.dx[i] = 0;
				this.dy[i] = 0;
			}
		}
		public void draw() {
			Graphics g = box.getGraphics();
			for (int i=0;i<this.gaesu;i++) {
				g.fillOval(x[i], y[i], (int)SIZE[i], (int)SIZE[i]);
				g.dispose(); 
			}
		}
		public  void move() {
			int temp = this.gaesu;
			for(int i=0;i<temp;i++) {
				Graphics g = this.box.getGraphics();
				g.setXORMode(box.getBackground());//현재 그림 지우기
				g.fillOval(this.x[i], this.y[i], (int)this.SIZE[i],(int)this.SIZE[i]);
				if(this.flag[i]==1&& this.gaesu!=6) {
						x[gaesu] = this.x[i]-this.dx[i];
						y[gaesu] = this.y[i]+this.dy[i];
						dx[gaesu] = -this.dx[i];
						dy[gaesu] = this.dy[i];
						SIZE[gaesu] = this.SIZE[i]/2;
						Graphics g1 = this.box.getGraphics();
						g1.setXORMode(box.getBackground());
						g1.fillOval(this.x[gaesu], this.y[gaesu], (int)this.SIZE[gaesu],(int)this.SIZE[gaesu]);//새로생긴 Ball그리기
						this.flag[gaesu]=0;
						gaesu++;
						this.dx[i]=-this.dx[i];
						this.dy[i]=-this.dy[i];
						this.SIZE[i]/=2;
						this.flag[i]=0;
						
				}			//충돌시 새로운 공 크기 및 위치 지정, 새로운 공 그리기, 방향 전환
				this.x[i] += this.dx[i]; this.y[i] += this.dy[i];//이동
				Dimension d = box.getSize();
				if (this.x[i] < 0) { this.x[i] = 0; this.dx[i] = -this.dx[i]; }
				if (this.x[i] + this.SIZE[i] >= d.width) { this.x[i] = d.width - (int)this.SIZE[i]; this.dx[i] = -this.dx[i]; }
				if (this.y[i] < 0) { this.y[i] = 0; this.dy[i] = -this.dy[i]; }
				if (this.y[i] + this.SIZE[i] >= d.height) { this.y[i] = d.height - (int)this.SIZE[i]; this.dy[i] = -this.dy[i]; }
				g.fillOval(this.x[i], this.y[i],(int) this.SIZE[i], (int)this.SIZE[i]);
				g.dispose();
				if(this.gaesu==9) {
					System.out.println(this.number);
					System.exit(0);
				}
				
			}
		}
		public void run() {
			draw();
			for(int i=0;;i++) {
				doit();
				try { Thread.sleep(5); }
				catch(InterruptedException e) {}
			}
		}
		public void doit() {
			move();
			distance();
		}
		
	}
	public void distance() {
		double distance =0.0;
		int i,j,k,l;
		for(i=0;i<5;i++) {
			for(j=i+1;j<5;j++) {
				for(k=0;k< B[i].gaesu;k++) {
					for(l=0;l<B[j].gaesu;l++) {
						distance = (double)((B[i].x[k]-B[j].x[l])*(B[i].x[k]-B[j].x[l])+(B[i].y[k]-B[j].y[l])*(B[i].y[k]-B[j].y[l]))-
								(B[i].SIZE[k]+B[j].SIZE[l])*(B[i].SIZE[k]+B[j].SIZE[l]);
						if(distance <=0.0) {
							B[i].flag[k]=1;
							B[j].flag[l]=1;
						}
					}
				}
			}
		}
	}
	
}



