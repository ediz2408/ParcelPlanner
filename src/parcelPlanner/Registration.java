package parcelPlanner;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Registration extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtRePassword;
	
	static Registration frame=new Registration();
	
	public Registration() {
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
		panel_1.setBounds(225, 100, 320, 225);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		//subtitle
		JLabel lblNewLabel = new JLabel("Register");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblNewLabel.setBounds(22, 10, 120, 33);
		panel_1.add(lblNewLabel);
		
		//username textfield
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 10));
		txtUsername.setText("Username");
		txtUsername.setBounds(22, 60, 276, 27);
		panel_1.add(txtUsername);
		txtUsername.setColumns(10);
		
		//password textfield
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 10));
		txtPassword.setText("Password");
		txtPassword.setEchoChar('*'); //cover with asterisks for extra security
		txtPassword.setColumns(10);
		txtPassword.setBounds(22, 97, 276, 27);
		panel_1.add(txtPassword);

		JPasswordField txtRePassword = new JPasswordField();
		txtRePassword.setEchoChar('*');
		txtRePassword.setFont(new Font("SansSerif", Font.PLAIN, 10));
		txtRePassword.setText("Re-enter password");
		txtPassword.setEchoChar('*');
		txtRePassword.setColumns(10);
		txtRePassword.setBounds(22, 134, 276, 27);
		panel_1.add(txtRePassword);
		
		//create account button
		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnNewButton.setBounds(102, 171, 120, 33);
		panel_1.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileReader fr=new FileReader("userpass.txt");
					BufferedReader br=new BufferedReader(fr);
					String un=txtUsername.getText();
					boolean flag1=false;
					String line=br.readLine();
					while (line!=null) {
						String[] data=line.split(",");
						if(un.equals(data[0])) {
							flag1=true; //the username is already taken
						}
						line=br.readLine();
					}
					
					boolean flag2=true; //assume the passwords do not match
					String pw1=txtPassword.getText();
					String pw2=txtRePassword.getText();
					if (pw1.equals(pw2)) {
						flag2=false; //the passwords do match
					}
					
					if (flag1) { //relevant error messages
						JOptionPane.showMessageDialog(null, "Username is already in use. Please try again!", "Login Failed", JOptionPane.ERROR_MESSAGE);
					}
					else if (flag2) {
						JOptionPane.showMessageDialog(null, "The passwords do not match. Please try again!", "Login Failed", JOptionPane.ERROR_MESSAGE);
					}
					else { //add details to userpass.txt if there are no issues
						FileWriter fw = new FileWriter("userpass.txt", true);
				        fw.write(un + "," + pw1 + "\n");
				        fw.close();
				        JOptionPane.showMessageDialog(
				        		Registration.this, 
				        		"The account has successfully been created!", 
				        		"Account Created", 
				        		JOptionPane.INFORMATION_MESSAGE
				        );
				        frame.setVisible(false);
						DeliveryPlan dp=new DeliveryPlan(un);
						dp.run();
					}
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}
	
	public void run() {
        try {
        	frame.setVisible(true);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }

}