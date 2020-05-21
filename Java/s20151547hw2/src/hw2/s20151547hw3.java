package hw2;
import java.awt.*;
import java.awt.event.*;
import java.math.*;
//import javax.swing.*;
public class  s20151547hw3 extends Frame implements ActionListener{
	private Button bu[];
	private Frame f = new Frame("계산기");
	private Label in = new Label("",Label.RIGHT);//입력한 숫자들이 나올 공간
	private Label out = new Label("0",Label.RIGHT);//결과값이 출력될 공간
	private Panel pad = new Panel();//숫자패드가 놓일 공간
	private String curValString= new String("");
	private String[] last_string = new String[100];
	private String temp="";
	private int dot=0;
	private double curVal=0.0;
	private double lastVal=0.0;
	private int operatormode=0;
	static boolean number=false; 
	private String before=new String("");
	static double last[] = new double[100];
	static int gual=0;
	static int last1=0;
	static String s=new String("");
	boolean doublemode=  false;
	public s20151547hw3(String str) {
		super(str);
		init();
		WindowDestroyer listener = new WindowDestroyer();
		f.addWindowListener(listener);
		for(int i=0;i<100;i++) {
			last_string[i] = "";
		}
	}
	public void init() {
		bu = new Button[20];
		pad.setLayout(new GridLayout(4,5,10,10));
		out.setSize(100,30);
		in.setSize(100,30);
		int i=0;
		for(i=0;i<10;i++) {
				bu[i] = new Button(String.valueOf(i));
		}
		bu[10] = new Button("/");
		bu[11] = new Button("-");
		bu[12] = new Button("*");
		bu[13] = new Button("+");
		bu[14] = new Button("C");
		bu[15] = new Button("(");
		bu[16] = new Button(".");
		bu[17] = new Button("=");
		bu[18] = new Button("<-");
		bu[19] = new Button(")");
		for(i=7;i<10;i++) {
			pad.add(bu[i]);
		}
		pad.add(bu[10]);
		pad.add(bu[14]);
		for(i=4;i<7;i++) {
			pad.add(bu[i]);
		}
		pad.add(bu[12]);
		pad.add(bu[18]);
		
		for(i=1;i<4;i++) {
			pad.add(bu[i]);
		}
		pad.add(bu[11]);
		pad.add(bu[15]);
		pad.add(bu[0]);
		pad.add(bu[16]);
		pad.add(bu[17]);
		pad.add(bu[13]);
		pad.add(bu[19]);
		
		for(i=0;i<10;i++) {
			bu[i].setForeground(Color.BLUE);
		}
		for(i=10;i<20;i++) {
			bu[i].setForeground(Color.RED);
		}
		f.add(in,BorderLayout.NORTH);
		f.add(out,BorderLayout.CENTER);
		f.add(pad,BorderLayout.SOUTH);
		f.setSize(400, 400);
		f.setVisible(true);
		for(i=0;i<20;i++) {
			bu[i].addActionListener(this);
		}
		clear();
	}
	public void actionPerformed(ActionEvent e) {
		int i=0;
		temp="";//입력한 전체 수식
		for(i=0;i<20;i++) {
			if(e.getSource()==bu[i]) {
				if(s.equals("=")==true) {
					temp = before;
					in.setText(before);
				}
				else{
					last_string[last1] = out.getText();
					last1++;
				}
				if(i>=0&&i<10||i==16) {//숫자가 들어온 경우
					if(number==false) {
						temp="";
					}
					number = true;
					if(i==16) {// "."입력
						if(dot==0) {
							curValString+=".";
							temp +=".";
							dot=1;
						}
					}
					else{
						curValString +=i;
						temp+=i;
					}
					before = temp;
				}
				else if(i>=10&&i<=13||i==17){
					dot=0;
					System.out.println("연산자 받았음"+e.getSource());
					if(number==false) {
						clear();
						temp ="숫자 먼저 입력하세요";
					}
					else {
						boolean divide = true;
						if(operatormode==0) {//연산자가 처음 들어온경우
							operatormode =1;
							curVal = Double.parseDouble(curValString);
							lastVal = curVal;
							System.out.println("0일때 last Val: "+lastVal);
						}
						else if(operatormode==1) {//A something B 이후의 경우
							if(s.equals("=")==true) {
								curVal = lastVal;
							}
							else {
								curVal = Double.parseDouble(curValString);	
							}
							if(s.equals("/")==true) {
								if(curVal==0.0) {
									divide = false;
								}
								else {
								lastVal = lastVal/curVal;
								}
							}
							else if(s.equals("*")==true) {
								lastVal = lastVal*curVal;
							}
							else if(s.equals("+")==true) {
								lastVal = lastVal+curVal;
							}
							else if(s.equals("-")==true) {
							/*	BigDecimal la1 = new BigDecimal(String.valueOf(lastVal));
								BigDecimal la2 = new BigDecimal(String.valueOf(curVal));
								la1=la1.subtract(la2);
								String check="";
								check = la1.toString();
								lastVal = Double.parseDouble(check);*/
								lastVal = lastVal-curVal;
							}
						}
						s="";
						switch(i) {
						case 10:s+="/";break;
						case 11:s+="-";break;
						case 12:s+="*";break;
						case 13:s+="+";break;
						}
						if(divide == true) {
							temp = before+"  "+s+"  ";
						}
						else {
							clear();
							temp = "0으로 나눌 수 없습니다.";
						}
						if(i==17) {//"="눌렀을 때
							s = "=";
							temp+="  "+s;
							dot =0;
							String outp="";
						/*	if(lastVal%1<0.00000000000001) {
								outp+=""+(int)lastVal;
								before=""+(int)lastVal;
								System.out.println("1 before:" +before);
							}
							else if(lastVal%1>0.9999999999) {
								outp+=""+((int)(lastVal)+1);
								before=""+((int)(lastVal)+1);
								System.out.println("2 before:" +before);
							}
							else {
							outp+=""+lastVal;
							before+=""+lastVal;
							System.out.println("3 before:" +before);
							}*/
							outp+=lastVal;
							before+=lastVal;
							out.setText(outp);
						}
						curValString = "";
					}
				}
				else if(i==14) {//C입력
					temp="";
					clear();
				}
			}
			
		}
		in.setText(temp);
	}
	public void clear(){
		lastVal=0.0;
		curVal=0.0;
		operatormode=0;
		s="";
		before ="";
		for(int i =0;i<100;i++) {
			last[i]=0;
		}
		gual =0;
		last1=0;
		out.setText("");
	}
	public double calculate(double d,double e,String op) {
		if(op.equals("+")==true) {
			return d+e;
		}
		else if(op.equals("-")==true) {
			BigDecimal la1 = new BigDecimal(String.valueOf(d));
			BigDecimal la2 = new BigDecimal(String.valueOf(e));
			la1=la1.subtract(la2);
			String check="";
			check = la1.toString();
			return Double.parseDouble(check);
		}
		else if(op.equals("*")==true) {
			return d*e;
		}
		else if(op.equals("/")==true) {
			if (e==0.0) {
				temp ="0으로 나눌 수 없습니다.";
				
			}
			else {
				return d/e;
			}
		}
		return 0.0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		s20151547hw3 w = new s20151547hw3("dldl");
	}
	
}