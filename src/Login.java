import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener {
	
	
	JLabel name = new JLabel("username : ");
	JTextField nameField = new JTextField();
	JLabel pass = new JLabel ("password : ");
	JPasswordField passField = new JPasswordField();
	JPanel panel = new JPanel();
	JPanel loginPanel = new JPanel(new GridLayout(3,2));
	JButton loginB = new JButton("login");
	JButton registerB = new JButton ("register");
	CardLayout cardL;
	Login() {
		setLayout(new CardLayout());
		loginPanel.add(name);
		loginPanel.add(nameField);
		loginPanel.add(pass);
		loginPanel.add(passField);
		loginB.addActionListener(this);
		registerB.addActionListener(this);
		
		loginPanel.add(loginB);
		loginPanel.add(registerB);
		panel.add(loginPanel);
		add(panel,"login");
		
		cardL = (CardLayout)getLayout();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== loginB) {
			try {
				BufferedReader in = new BufferedReader(new FileReader("resources/passwords.txt"));
				String pass1=null;
				String line = in.readLine();
				System.out.println(line);
				while(line!=null) {
				StringTokenizer st = new StringTokenizer(line);
				if(nameField.getText().equals(st.nextToken())) 
					pass1 =st.nextToken();
		
				line=in.readLine();
				
				}
				System.out.println(pass1);
				in.close();
				
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(new String(passField.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for(int i =0;i<byteData.length;i++) 
					sb.append(Integer.toString((byteData[i] & 0xFF)+ 0x100, 16).substring(1));
				if(pass1.equals(sb.toString()))
					//System.out.println("user logged in");
					add(new FileBrowser(nameField.getText()), "fb");
					cardL.show(this, "fb");
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
		
		if(e.getSource()== registerB) {
		add(new Register(), "register");
		cardL.show(this, "register");
		}
	}

		public static void main(String[] args) {
		// TODO Auto-generated method stub
			JFrame frame = new JFrame ("Text Editor");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(500,500);
			Login login = new Login();
			frame.add(login);
			frame.setVisible(true);
	}

	
}
