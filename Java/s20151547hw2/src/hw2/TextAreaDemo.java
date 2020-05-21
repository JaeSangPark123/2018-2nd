package hw2;
import java.awt.*;
import java.awt.event.*;
public class TextAreaDemo {
	private Frame f;
	private TextArea tf;
	private String total ;
	public TextAreaDemo() {
		f = new Frame("TextArea");
		tf = new TextArea("Welcome to Sogang!");
		tf.addKeyListener(new NameHandler());
		tf.addTextListener(new TextHandler());
		f.add(tf, BorderLayout.CENTER);
		
		f.pack();
		WindowDestroyer listener = new WindowDestroyer();
		f.addWindowListener(listener);
		f.setVisible(true);
	}
	class NameHandler extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			char c = e.getKeyChar();
			if(Character.isDigit(c)) {
				e.consume();
			}
		}
	}
	class TextHandler implements TextListener{
		public void textValueChanged(TextEvent e) {
			System.out.println(tf.getText());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextAreaDemo tfd = new TextAreaDemo();
	}

}
