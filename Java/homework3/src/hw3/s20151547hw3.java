package hw3;
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
	private String[] last_string = new String[1000];
	private String temp="";
	private String curString="";
	private int dot=0;
	private double val[] = new double[1000];
	private String op[] = new String[1000];
	static boolean number=false; 
	private String before=new String("");
	static double lastnum[] = new double[100];
	static int lr=0;
	static int last1=0;
	static int num=0;
	static int right=0;
	static int op_num=0;
	static int sw=0;
	
	public s20151547hw3(String str) {
		super(str);
		for(int i=0;i<1000;i++) {
			val[i] = 0.0;
			op[i]="";
		}
		for(int i=0;i<100;i++) {
			lastnum[i] = 0.0;
		}
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
		bu[16].setForeground(Color.BLUE);
		f.add(in,BorderLayout.NORTH);
		f.add(out,BorderLayout.CENTER);
		f.add(pad,BorderLayout.SOUTH);
		f.setSize(700, 700);
		f.setVisible(true);
		for(i=0;i<20;i++) {
			bu[i].addActionListener(this);
		}
		clear();
	}
	public void actionPerformed(ActionEvent e) {
		int i=0;
		for(i=0;i<20;i++) {
			if(e.getSource()==bu[i]) {
				if(i>=0&&i<10||i==16) {//숫자가 들어온 경우
					if(op[op_num].equals("")==false) {
						if(op[op_num].equals("(")==true) {
							;
						}
						else {
							before +="  "+ op[op_num];
							sw=0;
							op_num++;
						}
					}
					if(last1!=0) {
						last_string[last1]=last_string[last1-1];
					}
					if(i==16) {
						if(dot==0) {
							//dot 추가
							if(last1==0) {
								last_string[last1] +="0.";	
							}
							else{
								last_string[last1] +=".";
							}
							dot=1;
						}
					}
					else {//숫자 추가
						last_string[last1]+=i;
					}
					last1++;
					out.setText(last_string[last1-1]);
				}
				else if(i>=10&&i<=13) {//연산자가 들어온 경우 / - * + 순서
						if(last1==0) {
						}
						else {
							val[num]=Double.parseDouble(last_string[last1-1]); //숫자값 저장
							before+="  "+val[num];
							sw=1;
							dot=0;
							num++;
							for(i=0;i<last1;i++) {//오류 가능성 있음
								last_string[i]="";
							}
							last1=0;
						}
							
					switch(i) {
					case 10: op[op_num]="/";break;
					case 11: op[op_num]="-";break;
					case 12: op[op_num]="*";break;
					case 13: op[op_num]="+";break;
					}
					in.setText(before+op[op_num]);
				}
				else if(i==14) {// "C"들어온 경우
					clear();
					in.setText(before);
					out.setText("");
				}
				else if(i==15) {// "("들어간 경우
					before+=op[op_num]+"(";
					sw=0;
					op_num++;
					op[op_num]="(";
					op_num++;
					lr++;
					in.setText(before);
				}
				else if(i==17) {//등호 대입된 경우
					if(sw==0) {//숫자 저장하고 "="대입
						val[num]=Double.parseDouble(last_string[last1-1]);
						before += String.valueOf(val[num]);
						dot=0;
						num++;//숫자값 저장
						if(lr>0) {
							for (int k=0;k<lr;k++) {
								while(true) {
									if(op[op_num-1].equals("(")==true) {
										op[op_num-1]="";
										op_num--;
										break;
									}
									else {
										val[num-2]=calculate(val[num-2],val[num-1],op_num-1);
										val[num-1]=0;
										op[op_num-1]="";
										num--;
										op_num--;
									}
								}
								before+=")";//모자란 ) 채우기
							}
						}
						before =before + " = ";
					}
					else if(sw==1) {//바로 "="대입
						if(lr!=0) {
							for (int k=0;k<lr;k++) {
								while(true) {
									if(op[op_num-1].equals("(")==true) {
										op[op_num-1]="";
										op_num--;
										break;
									}
									else {
										val[num-2]=calculate(val[num-2],val[num-1],op_num-1);
										val[num-1]=0;
										op[op_num-1]="";
										num--;
										op_num--;
									}
								}
								before+=")";//모자란 ) 채우기
							}
						}
						before+=" = ";
					}
					if(num==1) {			
						out.setText(String.valueOf(val[num-1]));
					}
					else {
						for(int k=0;k<op_num;k++) {
							val[k+1]=calculate(val[k],val[k+1],k);
						}
						in.setText(before);
						if(val[op_num]%1==0) {
							out.setText(String.valueOf((int)val[op_num]));
						}
						else if(val[op_num]%1>0.999999999999){
							out.setText(String.valueOf((int)val[op_num]+1));
						}
						else if(val[op_num]%1<-0.999999999999){
							out.setText(String.valueOf((int)val[op_num]-1));
						}
						else {
							out.setText(String.valueOf(val[op_num]));
						}
					}
					double tempo=val[op_num];
					clear();					
					before+=tempo;
					val[0] = tempo;
					num++;
					
				}
				else if(i==18) {// "<-"대입된 경우
					if(last1>1) {
						last_string[last1-1]="";
						last1--;
						out.setText(last_string[last1-1]);
					}
					else if(last1==1) {
						last_string[0]="";
						last1--;
						out.setText(last_string[0]);
					}
					else {
						out.setText(last_string[last1]);
					}
					in.setText(before);
				}
				else if(i==19) { //")"대입된 경우
					val[num]=Double.parseDouble(last_string[last1-1]); //숫자값 저장
					dot=0;
					before+="  "+val[num];
					sw=1;
					num++;
					lr--;
					if(lr!=-1) {
						while(true) {
							if(op[op_num-1].equals("(")==true) {
								op[op_num-1]="";
								op_num--;
								break;
							}
							else {
								val[num-2]=calculate(val[num-2],val[num-1],op_num-1);
								val[num-1]=0;
								op[op_num-1]="";
								num--;
								op_num--;
							}
						}
						lr=0;
					}
					before+="  "+")";
					in.setText(before);
				}
			}
		}
	}
	public void valclear() {
		int i=0;
		for(i=0;i<last1;i++) {
			last_string[i]="";
		}
		for(i=0;i<op_num;i++) {
			op[i]="";
		}
		for(i=0;i<num;i++) {
			val[i]=0.0;
		}
		lr=0;
		op_num=0;
		num=0;
		last1=0;
		dot =0;
	}
	public void clear(){
		int i=0;
		for(i=0;i<last1;i++) {
			last_string[i]="";
		}
		for(i=0;i<op_num;i++) {
			op[i]="";
		}
		for(i=0;i<num;i++) {
			val[i]=0.0;
		}
		lr=0;
		op_num=0;
		num=0;
		last1=0;
		before ="";
		dot =0;
		}
public double calculate(double d,double e,int check) {
	if(op[check].equals("+")==true) { //op="+";
		BigDecimal la1 = new BigDecimal(String.valueOf(d));
		BigDecimal la2 = new BigDecimal(String.valueOf(e));
		la1=la1.add(la2);
		String check1="";
		check1 = la1.toString();
		return Double.parseDouble(check1);
	}
	else if(op[check].equals("-")==true) {//op="-";
		BigDecimal la1 = new BigDecimal(String.valueOf(d));
		BigDecimal la2 = new BigDecimal(String.valueOf(e));
		la1=la1.subtract(la2);
		String check1="";
		check1 = la1.toString();
		return Double.parseDouble(check1);
	}
	else if(op[check].equals("*")==true) {//op="*";
		BigDecimal la1 = new BigDecimal(String.valueOf(d));
		BigDecimal la2 = new BigDecimal(String.valueOf(e));
		la1=la1.multiply(la2);
		String check1="";
		check1 = la1.toString();
		return Double.parseDouble(check1);
	}
	else if(op[check].equals("/")==true) {//op="/";
		if (e==0.0) {
			temp ="0으로 나눌 수 없습니다.";
		}
		else {
			BigDecimal la1 = new BigDecimal(String.valueOf(d));
			BigDecimal la2 = new BigDecimal(String.valueOf(e));
			la1=la1.divide(la2);
			String check1="";
			check1 = la1.toString();
			return Double.parseDouble(check1);
		}
	}
	return 1111111111111.0;//default값
}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		s20151547hw3 w = new s20151547hw3("dldl");
	}
	
}