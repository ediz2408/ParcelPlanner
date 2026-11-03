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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;

public class Address extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String un;
	private JTextField postcode;
	private JTextField xCoord;
	private JTextField yCoord;
	private JButton btnClearAddresses;

	public Address(String un) {
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
		JLabel lblNewLabel = new JLabel("Address");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel.setBounds(330, 67, 149, 33);
		contentPane.add(lblNewLabel);
		
		//postcode textfield
		postcode = new JTextField();
		postcode.setFont(new Font("SansSerif", Font.PLAIN, 10));
		postcode.setText("Postcode");
		postcode.setBounds(250, 140, 276, 27);
		postcode.setColumns(10);
		contentPane.add(postcode);
		
		//x-coordinate textfield
		xCoord = new JTextField();
		xCoord.setFont(new Font("SansSerif", Font.PLAIN, 10));
		xCoord.setText("X-Coordinate");
		xCoord.setColumns(10);
		xCoord.setBounds(250, 187, 276, 27);
		contentPane.add(xCoord);
		
		//y-coordinate textfield
		yCoord = new JTextField();
		yCoord.setFont(new Font("SansSerif", Font.PLAIN, 10));
		yCoord.setText("Y-Coordinate");
		yCoord.setColumns(10);
		yCoord.setBounds(250, 234, 276, 27);
		contentPane.add(yCoord);
		
		//back button
		JButton btnBackButton = new JButton("Back");
		btnBackButton.setForeground(new Color(0, 0, 0));
		btnBackButton.setBackground(new Color(240, 240, 240));
		btnBackButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnBackButton.setBounds(22, 403, 69, 39);
		contentPane.add(btnBackButton);
		
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Address.this.setVisible(false);
				DeliveryPlan dp=new DeliveryPlan(un); //return to delivery plan screen
				dp.run();
			}
		});
		
		//add button
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(240, 240, 240));
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnNewButton.setBounds(330, 281, 120, 33);
		contentPane.add(btnNewButton);
		
		//adding address details to address.csv
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter fw=new FileWriter("address.csv", true);
					String pc=postcode.getText();
					Double xc=Double.parseDouble(xCoord.getText());
					Double yc=Double.parseDouble(yCoord.getText());
					fw.write(pc+","+xc+","+yc+"\n");
					fw.close();
				} 
				catch (IOException e) {
					System.out.println(e.getMessage());
				}
				postcode.setText(""); //clear textfields
				xCoord.setText("");
				yCoord.setText("");
				JOptionPane.showMessageDialog( //success message
						Address.this, 
						"The address details have successfully been added to the file!", 
						"Address Added", 
						JOptionPane.INFORMATION_MESSAGE
				);
			}
		});
		
		//clear addresses button
		btnClearAddresses = new JButton("Clear Addresses");
		btnClearAddresses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter fw=new FileWriter("address.csv", false);
					fw.write(""); //clears all text in address.csv
					fw.close();
				}
				catch (IOException e) {
					System.out.println(e.getMessage());
				} //success message
				JOptionPane.showMessageDialog(Address.this, "The address file has successfully been cleared!", "File Cleared", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnClearAddresses.setForeground(Color.BLACK);
		btnClearAddresses.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnClearAddresses.setBackground(UIManager.getColor("Button.background"));
		btnClearAddresses.setBounds(633, 403, 130, 39);
		contentPane.add(btnClearAddresses);
	}
	
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}