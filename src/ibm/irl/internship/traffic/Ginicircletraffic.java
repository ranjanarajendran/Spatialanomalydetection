package ibm.irl.internship.traffic;

import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class Ginicircletraffic {
	
	static Double[][] geo=new Double[11000][3];
	
	static int n;
	
	static String infilepath;
	
	static ArrayList<Integer> neighbors;
	
	static ArrayList<Integer> prevneighbors;
	
	public static void main(String args[]) throws IOException{
		
		infilepath = args[0];
		String outfilepath = args[1];
		
		//FileInputStream fstream = new FileInputStream(infilepath+"/"+"4day10hoursmallfilteredstretchremoved");
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"4day10hourcompactfilteredstretchremoved.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//File newfile = new File(outfilepath+"/4day10hoursginismallerfilteredstretchremovedallpointsnot1");
		File newfile = new File(outfilepath+"/4day10hourginicircles.txt");
		boolean exist = newfile.createNewFile();
		
		while(!exist){
			
			boolean success= newfile.delete();
			if(success)
				exist = newfile.createNewFile();
		}
		
		FileWriter fstream1 = new FileWriter(newfile);
		BufferedWriter out = new BufferedWriter(fstream1);
		
		String currentline;
		Integer i=0,j=0;
		
		while((currentline=br.readLine())!=null){
			
			currentline.trim();
			
			String current[]=currentline.split(",");
			
			Double longitude = Double.parseDouble(current[3]);
			Double latitude = Double.parseDouble(current[4]);
			
			geo[i][0]=Double.parseDouble(current[3]) * 180 / Math.PI;
			geo[i][1]=Double.parseDouble(current[4]) * 180 / Math.PI;
			geo[i][2]=Double.parseDouble(current[7]) * 1000+1.0;
			
			i++;
		}	
		
		n = i;
		
		System.out.println("Total number of points: "+n);
		
		Double prevradius,giniindex=0.0,variance=0.0,prevginiindex = 0.0,radius,prevaverage=0.0,average=0.0;
		
		Integer prevnum=1;
		
		prevradius=0.01;
		
		
		for(j=0;j<n;j++){
			
			prevradius=0.001;
			
			prevneighbors = new ArrayList<Integer>();
			
			prevneighbors.clear();
			
			neighbors=new ArrayList<Integer>();	
			neighbors.clear();
			
			Double averagediff=0.0;
			
			findlocalneighbors(j,prevradius);
			
			prevnum = neighbors.size();
			
		    prevneighbors = neighbors;
			
			
			Integer k =0,index;
						
			Double sum=0.0,px=0.0;
			
			for(index=0;index < prevneighbors.size();index++){
				
				k= prevneighbors.get(index);
			
				sum += geo[k][2];
				
				px += (prevnum-index) * geo[k][2];
								
			}
			
			System.out.println("Number of neighbors is: "+index);
			
			prevaverage = sum / (prevnum) ;
			
			if(prevaverage > 0.0 && prevnum > 1)
			{	
				
			Double neighborratio = (prevnum.doubleValue() + 1)/(prevnum.doubleValue()-1);
			
			prevginiindex = neighborratio - (2 * px)/(prevnum*(prevnum-1)*prevaverage);
			
			if(prevginiindex < 0){
				
				System.out.println("Neighborratio "+neighborratio);
				System.out.println((2 * px)/(prevnum*(prevnum-1)*prevaverage));
				System.out.println(px+"	"+prevaverage);
				
			}
			}
			
			do{
				
			neighbors=new ArrayList<Integer>();	
			
			neighbors.clear();
			
			if(neighbors.size() == prevneighbors.size())
				System.out.println("Copy of ArrayList errored");
			
			radius=prevradius+0.01;
			
			findlocalneighbors(j,radius);
			
			Integer neighbornum = neighbors.size();
			
			Double absdiff=0.0;
			
			for(index=0;index < neighbors.size();index++){
				
				k= neighbors.get(index);
				
				sum += geo[k][2];
				
				px += (neighbornum-index) * geo[k][2];
				
				if(!prevneighbors.contains(k)){
					absdiff+=Math.abs(geo[k][2] - prevaverage);
				}
								
			}
			
			System.out.println("Number of neighbors is: "+index);
			
			Double u = sum / (neighbornum) ;
			
			Double prevsumofu = prevaverage * prevnum;
			
			average = u.doubleValue();
			
			Double neighborratio;
			
			if(u!=0 && neighbornum > 1)
			{	
				
				neighborratio = (neighbornum.doubleValue() + 1)/(neighbornum.doubleValue()-1);
			
				giniindex = neighborratio - (2 * px)/(neighbornum*(neighbornum-1)*u);
			
				if(giniindex < 0){
				
					System.out.println("Neighborratio "+neighborratio);
					System.out.println((2 * px)/(neighbornum*(neighbornum-1)*u));
					System.out.println(px+"	"+u);
				
				}
			
				ListIterator<Integer> it = neighbors.listIterator();
			
				while(it.hasPrevious()){
				
					it.previous();
				}
			
				k=0;sum=0.0;
			
				System.out.println("Variance:");
			
				for(index = 0; index < neighbors.size();index++){
				
					sum += Math.pow(geo[neighbors.get(index)][2]-u,2);
				
					System.out.println(sum+" = "+geo[neighbors.get(index)][2]+"	"+u);
				
				}
			
				variance = sum / neighbornum ;
			
				Double diff = giniindex.doubleValue() - prevginiindex.doubleValue();
			
				if(neighbornum!=prevnum)
			
					averagediff = Math.abs((sum - prevsumofu)/(neighbornum-prevnum) - prevaverage);
			
				if(giniindex.doubleValue() <= 0.30){
			
					System.out.println(j+"		"+geo[j][0]+"	"+geo[j][1]+"	"+prevradius+"	"+prevnum+"		"+prevginiindex+"	"+absdiff.doubleValue()+"	"+averagediff.doubleValue()+"	"+diff.doubleValue()+"	"+giniindex.doubleValue()+"		"+neighbornum+"\n");
	
				}
			
				else{
					out.write(j+"		"+geo[j][0]+"	"+geo[j][1]+"	"+prevradius+"	"+prevnum+"		"+prevginiindex.doubleValue()*100+"	"+absdiff.doubleValue()*100+"	"+averagediff.doubleValue()*100+"	"+diff.doubleValue()*100+"		"+giniindex.doubleValue()+"		"+neighbornum+"\n");

					break;
				}
					
			}
			
			else
				System.out.println("Mean zero or number of neighbors is 1");
			
			prevginiindex=giniindex.doubleValue();
			prevneighbors=neighbors;
			prevaverage=average;
			prevnum=neighbornum;
			prevradius=radius;
			
			}while(giniindex <=0.30 && prevnum <= n/2);
			
		}
		out.close();
	
	}
	
	static void findlocalneighbors(Integer i,Double radius){
		
		//ArrayList<Integer> neighbors=new ArrayList<Integer>();
		
		Double centerX = geo[i][0];
		Double centerY = geo[i][1];
		
		Integer j;
		
		for(j=0;j<n;j++){
			
			Double X = geo[j][0];
			Double Y = geo[j][1];
			Double speed1 = geo[j][2];
			
			Double edistance = Math.abs(Math.sqrt(Math.pow(X - centerX, 2) + Math.pow(Y - centerY, 2)));
			
			if(edistance <= radius){
				
				int index=0;
				
				int size = neighbors.size();
			
				for(index=0;index < size ;index++){
					
					Double speed2 = geo[neighbors.get(index)][2];
					
					if(speed1 <= speed2){
						
						//System.out.println(i+" added"+" neighbor "+j+" neighbor size :"+neighbors.size());
						
						neighbors.add(index,j);
						
						break;
						
					}
					
				}
				if((index == neighbors.size()) && !(neighbors.contains(j))){
					
					neighbors.add(j);
					
				}
				
				
			}
		}
		
	}

}
