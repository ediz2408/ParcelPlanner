package parcelPlanner;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYItemRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JLabel;

public class Graph extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String un;
	private Clustering.ClusterResult clusterResult;
	
	public static void createPDF(Clustering.ClusterResult clusterResult, LinkedList<DriverEntry> allDrivers, LinkedList<AddressEntry> allAddresses) throws FileNotFoundException, DocumentException {
		//assign path of PDF 
		String path="C:\\Users\\obass\\Downloads\\delivery.pdf";
		//create PDF document
		Document document=new Document();
		PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		for (int driverIndex = 0; driverIndex < allDrivers.size(); driverIndex++) {
	        DriverEntry driver = allDrivers.get(driverIndex);

	        //write driver names
	        Paragraph driverName = new Paragraph(driver.getFname() + " " + driver.getLname() + ":");
	        document.add(driverName);

	        //loop through addresses and check if they belong to this driver's cluster
	        for (int addrIndex = 0; addrIndex < allAddresses.size(); addrIndex++) {
	            if (clusterResult.assignments[addrIndex] == driverIndex) {
	                AddressEntry address = allAddresses.get(addrIndex);
	                Paragraph addressPara = new Paragraph(" - " + address.getPostcode());
	                document.add(addressPara);
	            }
	        }

	        //add a blank line between drivers
	        document.add(new Paragraph("\n"));
	    }
		document.close();
	}
	
	public Graph(String un, Clustering.ClusterResult clusterResult) {
		this.un=un;
		this.clusterResult = clusterResult;
		
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
        JLabel lblNewLabel = new JLabel("Graph");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel.setBounds(347, 10, 149, 33);
		contentPane.add(lblNewLabel);
		
		//scatter graph
		ChartPanel chartPanel = new ChartPanel(createClusterChart(clusterResult));
		chartPanel.setBounds(10, 44, 300, 360);
        contentPane.add(chartPanel);
        chartPanel.setLayout(null);
        
        //back button
        JButton btnBackButton = new JButton("Back");
		btnBackButton.setForeground(new Color(0, 0, 0));
		btnBackButton.setBackground(new Color(240, 240, 240));
		btnBackButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnBackButton.setBounds(10, 414, 69, 39);
		contentPane.add(btnBackButton);
		
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Graph.this.setVisible(false);
				DeliveryPlan dp=new DeliveryPlan(un);
				dp.run();
			}
		});
		
		//add all addresses and drivers into linked lists
		LinkedList<AddressEntry> allAddresses=new LinkedList<>();
		LinkedList<DriverEntry> allDrivers=new LinkedList<>();
		try {	
			FileReader fr=new FileReader("address.csv");
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			while (line!=null) {
				String[] address=line.split(",");
				allAddresses.add(new AddressEntry(address[0], Double.parseDouble(address[1]), Double.parseDouble(address[2])));
				line=br.readLine();
			}
			
			FileReader fr1=new FileReader("driver.csv");
			BufferedReader br1=new BufferedReader(fr1);
			String line1=br1.readLine();
			while (line1!=null) {
				String[] driver=line1.split(",");
				allDrivers.add(new DriverEntry(driver[0], driver[1]));
				line1=br1.readLine();
			}
			br.close();
			br1.close();
		}
        catch (IOException e) {
        	System.out.println(e.getMessage());
        }
		
		//print all driver names on the right side of the screen
		for (int i=0;i<allDrivers.size();i++) {
			DriverEntry driver = allDrivers.get(i);
			JLabel lblNewLabel_1 = new JLabel(driver.getFname()+" "+driver.getLname());
			lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 17));
			lblNewLabel_1.setBounds(557, 80+(i*24), 122, 13);
			lblNewLabel_1.setForeground(clusterColors[i]);
			contentPane.add(lblNewLabel_1);
		}
		
		//export delivery plan button
		JButton btnNewButton = new JButton("Export Delivery Plan");
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnNewButton.setBounds(528, 357, 179, 47);
		contentPane.add(btnNewButton);
		
		//call PDF method
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					createPDF(clusterResult, allDrivers, allAddresses);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static final Color[] clusterColors = {
		    Color.RED,
		    Color.BLUE,
		    Color.GREEN,
		    Color.ORANGE,
		    Color.BLACK,
		    Color.MAGENTA,       
		    Color.GRAY,
		    new Color(128, 0, 128), //dark Purple
		    new Color(165, 42, 42), //brown
		    new Color(0, 100, 0)    //dark Green
	};
	
	//create scatter chart
	private JFreeChart createClusterChart(Clustering.ClusterResult clusterResult) {
		XYDataset dataset = createDataset();

	    JFreeChart chart = ChartFactory.createScatterPlot(
	        "Clustered Addresses",
	        "X Coordinate",
	        "Y Coordinate",
	        dataset,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );

	    XYPlot plot = (XYPlot) chart.getPlot();
	    XYItemRenderer renderer = plot.getRenderer();
	    for (int clusterId = 0; clusterId < clusterColors.length; clusterId++) {
	        renderer.setSeriesPaint(clusterId, clusterColors[clusterId]);
	    }
	    plot.setBackgroundPaint(Color.WHITE);
	    plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
	    plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

	    return chart;
    }
	
	//create dataset for scatter chart
	private XYDataset createDataset() {
	    XYSeriesCollection dataset = new XYSeriesCollection();

	    //create one series per cluster
	    int K = Arrays.stream(clusterResult.assignments).max().getAsInt() + 1;
	    XYSeries[] seriesArray = new XYSeries[K];
	    for (int i = 0; i < K; i++) {
	        seriesArray[i] = new XYSeries("Cluster " + (i+1));
	    }

	    //add each address to its cluster series
	    for (int i = 0; i < clusterResult.addresses.size(); i++) {
	        AddressEntry address = clusterResult.addresses.get(i);
	        int clusterId = clusterResult.assignments[i];
	        seriesArray[clusterId].add(address.getXCoord(), address.getYCoord());
	    }

	    //add series to dataset
	    for (XYSeries series : seriesArray) {
	        dataset.addSeries(series);
	    }

	    return dataset;
	}
	
	public void run() {
		try {
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}