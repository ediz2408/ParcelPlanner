package parcelPlanner;
import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;

public class Driver extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String un;
	private JTextField fName;
	private JTextField lName;

	public Driver(String un) {
		this.un=un;
		//title
		setTitle("Welcome to ParcelPlanner, "+un);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(565, 250, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 236, 215));
		contentPane.setForeground(new Color(0, 0, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//subtitle
		JLabel lblNewLabel = new JLabel("Driver");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel.setBounds(340, 67, 94, 33);
		contentPane.add(lblNewLabel);
		
		//first name textfield
		fName = new JTextField();
		fName.setFont(new Font("SansSerif", Font.PLAIN, 10));
		fName.setText("First Name(s)");
		fName.setColumns(10);
		fName.setBounds(245, 157, 276, 27);
		contentPane.add(fName);
		
		//last name textfield
		lName = new JTextField();
		lName.setFont(new Font("SansSerif", Font.PLAIN, 10));
		lName.setText("Last Name");
		lName.setColumns(10);
		lName.setBounds(245, 204, 276, 27);
		contentPane.add(lName);
		
		//back button
		JButton btnBackButton = new JButton("Back");
		btnBackButton.setForeground(new Color(0, 0, 0));
		btnBackButton.setBackground(new Color(240, 240, 240));
		btnBackButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnBackButton.setBounds(26, 401, 70, 39);
		contentPane.add(btnBackButton);
		
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Driver.this.setVisible(false);
				DeliveryPlan dp=new DeliveryPlan(un);
				dp.run();
			}
		});
		
		//add button
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnNewButton.setBounds(329, 250, 120, 33);
		contentPane.add(btnNewButton);
		
		//adding driver details to driver.csv
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter fw=new FileWriter("driver.csv", true);
					String fn=fName.getText();
					String ln=lName.getText();
					fw.write(fn+","+ln+"\n");
					fw.close();
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
				fName.setText(""); //clear textfields
				lName.setText("");
				JOptionPane.showMessageDialog(Driver.this, "The driver details have successfully been added to the file!", "Driver Added", JOptionPane.INFORMATION_MESSAGE);
			}	//success message
		});
		
		//clear drivers button
		JButton btnBackButton_1 = new JButton("Clear Drivers");
		btnBackButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter fw=new FileWriter("driver.csv", false);
					fw.write(""); //clears all text in driver.csv
					fw.close();
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				} //success message
				JOptionPane.showMessageDialog(Driver.this, "The driver file has successfully been cleared!", "File Cleared", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
		btnBackButton_1.setForeground(Color.BLACK);
		btnBackButton_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnBackButton_1.setBackground(UIManager.getColor("Button.background"));
		btnBackButton_1.setBounds(644, 401, 120, 39);
		contentPane.add(btnBackButton_1);
	}
	
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}