package parcelPlanner;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class DeliveryPlan extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String un;

	public DeliveryPlan(String un) {
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
		JLabel lblNewLabel5 = new JLabel("Delivery Plan");
		lblNewLabel5.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel5.setBounds(298, 23, 195, 33);
		contentPane.add(lblNewLabel5);
		
		//address button
		JButton btnNewButton = new JButton("Address Input");
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(UIManager.getColor("Button.background"));
		btnNewButton.setBounds(10, 77, 250, 273);
		contentPane.add(btnNewButton);
		
		//driver button
		JButton btnNewButton_1 = new JButton("Driver Registration");
		btnNewButton_1.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(UIManager.getColor("Button.background"));
		btnNewButton_1.setBounds(270, 77, 250, 273);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeliveryPlan.this.setVisible(false);
				Driver dr=new Driver(un);
				dr.run();
			}
		});
		
		//graph button
		JButton btnNewButton_2 = new JButton("Produce Graph");
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnNewButton_2.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setBackground(UIManager.getColor("Button.background"));
		btnNewButton_2.setBounds(530, 77, 250, 273);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExecutorService executor = Executors.newSingleThreadExecutor();

		        Future<Clustering.ClusterResult> future = executor.submit(() -> {
		            //run clustering
		            return Clustering.performClustering("address.csv", "driver.csv");
		        });

		        try {
		            //wait at most 5 seconds
		            Clustering.ClusterResult result = future.get(5, TimeUnit.SECONDS);

		            //if clustering succeeds
		            DeliveryPlan.this.setVisible(false);
		            Graph gr = new Graph(un, result);
		            gr.run();

		        } catch (TimeoutException e) { //error messages if clustering fails
		            future.cancel(true); //cancel the task
		            JOptionPane.showMessageDialog(
		            		DeliveryPlan.this, 
		            		"You have entered too many addresses or drivers. Please go back and change this!", 
		            		"Clustering Failed", 
		            		JOptionPane.ERROR_MESSAGE
		            );
		            
		        } catch (InterruptedException | ExecutionException e) {
		            future.cancel(true);
		            JOptionPane.showMessageDialog(
		            		DeliveryPlan.this, 
		            		"An error occurred while clustering: " + e.getMessage(), 
		            		"Clustering Failed", 
		            		JOptionPane.ERROR_MESSAGE
		            );
		        } finally {
		        	executor.shutdown();
		        }
			}
		});
		
		//address button image
		JLabel lblNewLabel = new JLabel("");
		ImageIcon originalIcon = new ImageIcon("G:\\\\My Drive\\\\Year 12 Java Programs\\\\ParcelPlanner_CSIA\\\\location_button.png");
		Image originalImage = originalIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(225, 225, Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(scaledImage));
		lblNewLabel.setBounds(26, 87, 225, 230);
		btnNewButton.add(lblNewLabel);
		
		//driver button image
		JLabel lblNewLabel_1 = new JLabel("");
		ImageIcon originalIcon1 = new ImageIcon("G:\\\\My Drive\\\\Year 12 Java Programs\\\\ParcelPlanner_CSIA\\\\driver_button.png");
		Image originalImage1 = originalIcon1.getImage();
		Image scaledImage1 = originalImage1.getScaledInstance(220, 220, Image.SCALE_SMOOTH);
		lblNewLabel_1.setIcon(new ImageIcon(scaledImage1));
		lblNewLabel_1.setBounds(286, 92, 220, 235);
		btnNewButton_1.add(lblNewLabel_1);
		
		//graph button image
		JLabel lblNewLabel_2 = new JLabel("");
		ImageIcon originalIcon2 = new ImageIcon("G:\\\\My Drive\\\\Year 12 Java Programs\\\\ParcelPlanner_CSIA\\\\graph_button.png");
		Image originalImage2 = originalIcon2.getImage();
		Image scaledImage2 = originalImage2.getScaledInstance(210, 210, Image.SCALE_SMOOTH);
		lblNewLabel_2.setIcon(new ImageIcon(scaledImage2));
		lblNewLabel_2.setBounds(546, 77, 227, 250);
		btnNewButton_2.add(lblNewLabel_2);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				DeliveryPlan.this.setVisible(false);
				Address ad=new Address(un);
				ad.run();
			}
		});
	}
	
	public void run() {
        try {
        	this.setVisible(true);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
}