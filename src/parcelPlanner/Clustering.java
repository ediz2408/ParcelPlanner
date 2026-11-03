package parcelPlanner;
import java.util.*;
import java.io.*;

public class Clustering {
	public static class ClusterResult {
        public LinkedList<AddressEntry> addresses;
        public int[] assignments;

        public ClusterResult(LinkedList<AddressEntry> addresses, int[] assignments) {
            this.addresses = addresses;
            this.assignments = assignments;
        }
        
        public int[] getAssignments() {
        	return assignments;
        }
    }
	
	public static ClusterResult performClustering(String addressFile, String driverFile) {
		//add all addresses and drivers into linked lists
		LinkedList<AddressEntry> allAddresses=new LinkedList<>();
		LinkedList<DriverEntry> allDrivers=new LinkedList<>();
		try {	
			FileReader fr=new FileReader(addressFile);
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			while (line!=null) {
				String[] address=line.split(",");
				allAddresses.add(new AddressEntry(address[0], Double.parseDouble(address[1]), Double.parseDouble(address[2])));
				line=br.readLine();
			}
			
			FileReader fr1=new FileReader(driverFile);
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
        }
		
		//assign K
		int K=allDrivers.size();
		int[] bestAssignments=new int[allAddresses.size()];
        
        //repeat clustering process 100 times
		for (int a=0;a<100;a++) {
			int[] addressno=new int[K];
	        
	        //select K random points and assign them as centroids
			for (int i=0;i<K;i++) {
	        	addressno[i] = (int)(Math.random() * allAddresses.size());
	        	for (int j=0;j<i;j++) {
	        		while (addressno[i]==addressno[j]) {
	        			addressno[i] = (int)(Math.random() * allAddresses.size());
	        		}
	        	}
	        }
	        
	        //add centroid coordinates into centroids array
	        double[][] centroids=new double[K][2];
	        for (int i=0;i<K;i++) {
	        	centroids[i][0]=allAddresses.get(addressno[i]).getXCoord();
	        	centroids[i][1]=allAddresses.get(addressno[i]).getYCoord();
	        }
	
	        int[] assignments = new int[allAddresses.size()];
	        boolean changed = true;
	        //loop until there is no changes to cluster assignments
	        while (changed) {
	        	//assume no changes have been made at the start
	        	changed = false;
	            //loop through all addresses
	        	for (int i = 0; i < allAddresses.size(); i++) {
	                double minDist = Double.MAX_VALUE;
	                int closestCluster = -1;

	                AddressEntry current = allAddresses.get(i);

	                //calculate distance from each centroid and assign to closest centroid
	                for (int k=0;k<K;k++) {
	                    double dx=current.getXCoord() - centroids[k][0];
	                    double dy=current.getYCoord() - centroids[k][1];
	                    double dist=Math.sqrt(dx * dx + dy * dy);

	                    if (dist < minDist) {
	                        minDist = dist;
	                        closestCluster = k;
	                    }
	                }

	                if (assignments[i] != closestCluster) {
	                    assignments[i] = closestCluster;
	                    changed = true;
	                }
	            }

	            //calculate centre of each cluster and make that point the new centroid
	        	double[] sumX = new double[K];
	            double[] sumY = new double[K];
	            int[] count = new int[K];
	            for (int i = 0; i < allAddresses.size(); i++) {
	                int cluster = assignments[i];
	                sumX[cluster] += allAddresses.get(i).getXCoord();
	                sumY[cluster] += allAddresses.get(i).getYCoord();
	                count[cluster]++;
	            }
	            for (int k = 0; k < K; k++) {
	                if (count[k] > 0) {
	                    centroids[k][0] = sumX[k] / count[k];
	                    centroids[k][1] = sumY[k] / count[k];
	                }
	            }
	        }
	
	        //calculate within-cluster mean
	        double bestClusterMean = Double.MAX_VALUE;
	        double clusterMean=0;
	        double distance=0;
	        for (int i=0;i<allAddresses.size();i++) {
	        	AddressEntry current=allAddresses.get(i);
	        	int cluster = assignments[i];
	        	double dx = current.getXCoord() - centroids[cluster][0];
	            double dy = current.getYCoord() - centroids[cluster][1];
	            distance = Math.sqrt(dx * dx + dy * dy); // Euclidean distance
	            clusterMean=clusterMean+distance;
	        }
	        clusterMean=clusterMean/allAddresses.size();
	        
	        //assign as best clustering if it has lowest within-cluster mean
	        if (clusterMean<bestClusterMean) {
	        	bestClusterMean=clusterMean;
	        	bestAssignments=assignments.clone();
	        }
	    }
	    return new ClusterResult(allAddresses, bestAssignments);
	}	
}