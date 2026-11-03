package parcelPlanner;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

import java.io.*;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	
	static Login frame=new Login();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		//title
		setTitle("Welcome to ParcelPlanner!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(565, 250, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 236, 215));
		contentPane.setForeground(new Color(0, 0, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(225, 70, 320, 290);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		//subtitle
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblNewLabel.setBounds(22, 10, 120, 33);
		panel_1.add(lblNewLabel);
		
		//information text
		JLabel lblNewLabel_1 = new JLabel("Log in by entering your username and password");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(22, 53, 286, 16);
		panel_1.add(lblNewLabel_1);
		
		//username textfield
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 10));
		txtUsername.setText("Username");
		txtUsername.setBounds(22, 88, 276, 27);
		panel_1.add(txtUsername);
		txtUsername.setColumns(10);
		
		//password textfield
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 10));
		txtPassword.setText("Password");
		txtPassword.setEchoChar('*');
		txtPassword.setColumns(10);
		txtPassword.setBounds(22, 125, 276, 27);
		panel_1.add(txtPassword);
		
		//sign in button
		JButton btnNewButton = new JButton("Sign In");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnNewButton.setBounds(110, 162, 90, 33);
		panel_1.add(btnNewButton);
		
		//check username and password with values in userpass.txt
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileReader fr=new FileReader("userpass.txt");
					BufferedReader br=new BufferedReader(fr);
					String un=txtUsername.getText();
					String pwd=txtPassword.getText();
					boolean flag=false;
					String line=br.readLine();
					while (line!=null) {
						String[] data=line.split(",");
						if(un.equals(data[0]) && pwd.equals(data[1])) {
							flag=true;
						}
						line=br.readLine();
					}
					if (flag) {
						frame.setVisible(false);
						DeliveryPlan dp=new DeliveryPlan(un);
						dp.run();
					}
					else { //error message
						JOptionPane.showMessageDialog(frame, "Username or password is incorrect. Please try again!", "Login Failed", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
		//sign up button
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnSignUp.setBounds(22, 225, 135, 27);
		panel_1.add(btnSignUp);
		
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Registration rg=new Registration(); //transition to registration screen
				rg.run();
			}
		});
		
		//guest button
		JButton btnSignInAs = new JButton("Sign In as Guest");
		btnSignInAs.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnSignInAs.setBounds(163, 225, 135, 27);
		panel_1.add(btnSignInAs);
		
		btnSignInAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				DeliveryPlan dp=new DeliveryPlan("Guest"); //transition to delivery plan screen
				dp.run();
			}
		});
	}
}