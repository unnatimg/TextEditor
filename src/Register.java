import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener{
	
	JLabel name = new JLabel("enter a username : ");
	JTextField nameField = new JTextField();
	JLabel pass = new JLabel("enter a password : ");
	JPasswordField passField = new JPasswordField();
	JLabel passC = new JLabel("Confirm your password : ");
	JPasswordField passCField = new JPasswordField();
	JButton register = new JButton("register");
	JButton back = new JButton("back");
	
	public Register() {
		
		JPanel loginPanel=new JPanel();
		loginPanel.setLayout(new GridLayout(4,2));
		loginPanel.add(name);
		loginPanel.add(nameField);
		loginPanel.add(pass);
		loginPanel.add(passField);
		loginPanel.add(passC);
		loginPanel.add(passCField);
		loginPanel.add(register);
		loginPanel.add(back);
		register.addActionListener(this);
		back.addActionListener(this);
		add(loginPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== register && nameField.getText().length() >0 && passField.getPassword().length>0) {
			String pass1 = new String(passField.getPassword());
			String pass2 = new String(passCField.getPassword());
			if(pass1.equals(pass2)) {
				try {
					
					BufferedReader in = new BufferedReader( new FileReader("resources/passwords.txt")) ;

					String line = in.readLine();
					while(line!=null) {
						StringTokenizer st = new StringTokenizer(line);
						if(nameField.getText().equals(st.nextToken())) {
							System.out.println("User already exists!");
							return;
						}
						line= in.readLine();
						
					}
					in.close();
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update(pass1.getBytes());
					byte byteData[] = md.digest();
					StringBuffer sb = new StringBuffer();
					for(int i =0;i<byteData.length;i++) 
						sb.append(Integer.toString((byteData[i] & 0xFF)+ 0x100, 16).substring(1));
						BufferedWriter op = new BufferedWriter(new FileWriter("resources/passwords.txt",true));
						op.write(nameField.getText()+ " "+ sb.toString()+ "\n" );
						op.close();
						Login login = (Login)getParent();
						login.cardL.show(login,"login");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}
		
		if(e.getSource()==back) {
			Login login = (Login)getParent();
			login.cardL.show(login,"login");
			
		}
	}
	
	
}
