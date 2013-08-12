package ibm.irl.internship.climate;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

public class Climateginicircles {
	
	static Double geo[][]=new Double[10000][3];
	static String infilepath;
	
	public static Double left,right,top,bottom, mintemp, rightincrement, upincrement, tempshift;
	
	public static Integer numpoints, nrows, ncols;
	
	public static Double radiusmult;
	
	static ArrayList<Integer> neighbors;
	
	static ArrayList<Integer> prevneighbors;
	
	public static void main(String args[]) throws IOException{
		
		infilepath = args[0];
		String outfilepath = args[1];
		//radiusmult = Double.parseDouble(args[2]);
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"tempNA.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String currentline;
		Integer i=0,j=0,p=0;	
		
		left=4185.0;right=-5000.0;top=0.0;bottom=5000.0;
		
		mintemp =0.0;
		
		Double sum =0.0;
		
		while((currentline=br.readLine())!=null){
			
			currentline.trim();
			
			String current[]=currentline.split("\\s+");
			
			Double longitude = Double.parseDouble(current[3]);
			Double latitude = Double.parseDouble(current[4]);
			
			geo[i][0]=Double.parseDouble(current[3]);
			geo[i][1]=Double.parseDouble(current[4]);
			geo[i][2]=Double.parseDouble(current[2]);
			
			sum+= geo[i][2];
			
			if(geo[i][2] < mintemp)
				mintemp = geo[i][2].doubleValue();
			
			if(geo[i][0] > right.doubleValue())
				right = geo[i][0];
			if(geo[i][0] < left.doubleValue())
				left = geo[i][0];
			if(geo[i][1] > top.doubleValue())
				top = geo[i][1];
			if(geo[i][1] < bottom.doubleValue())
				bottom = geo[i][1];
			
			if(i>1 && geo[i][0].equals(geo[i-1][0]))
				upincrement = Math.abs(geo[i][1]-geo[i-1][1]);
			else if (i > 1)
				rightincrement = Math.abs(geo[i][0]-geo[i-1][0]);
			
			i++;

		}	
		
		br.close();
		
		if(mintemp < 0)
			tempshift = Math.abs(mintemp);
		else tempshift = 0.0;
		
		numpoints = i;
		
		System.out.println("Left:"+left+"	Right:"+right+"	Top:"+top+"	Bottom:"+bottom);
		
		System.out.println("Rightincrement:"+rightincrement+" Upincrement:"+upincrement);
		
		if(left > right || bottom > top){
			
			System.out.println("Error in left, right, bottom and top values");
			return;
		}
		else
			System.out.println("Left "+left+" Right "+right+" Top "+top+" Bottom "+bottom);
		
		nrows = 0;
		ncols = 0;
		
		i = 0;
		
		Double rightlimit = left + i * rightincrement;
		ncols++;
		
		for (j = 1; rightlimit <= right; j++){
			
			ncols ++;
			rightlimit = left + j * rightincrement;
			
		}
		
		nrows++;
		Double toplimit = bottom + i * upincrement;
		
		for (j = 1; toplimit <= top; j++){
			
			nrows++;
			toplimit = bottom + j * upincrement;
			
		}
		
		System.out.println("Total number of points: "+numpoints);		
		System.out.println("Nrows:"+nrows+" NCols"+ncols);
	
		String caseline;
		Double radiusmax=0.0;
		Integer nzmax=0,muzmax=0,ngmax=0,mugmax=0;
			
		ArrayList<String> giniindexstrings = new ArrayList<String>();
		
		Double prevradius,giniindex=0.0,variance=0.0,prevginiindex = 0.0,radius,prevaverage=0.0,average=0.0;
		
		Integer prevnum=1;
		
		//prevradius=0.5;
		
		for(j=0;j<numpoints;j++){
			
			prevradius=0.5;
			
			prevneighbors = new ArrayList<Integer>();
			
			prevneighbors.clear();
			
			neighbors=new ArrayList<Integer>();	
			neighbors.clear();
			
			Double averagediff=0.0;
			
			findlocalneighbors(j,prevradius);
			
			prevnum = neighbors.size();
			
		    prevneighbors = neighbors;
			
			
			Integer k =0,index;
						
			Double sum1=0.0,px=0.0;
			
			for(index=0;index < prevneighbors.size();index++){
				
				k= prevneighbors.get(index);
			
				sum1 += geo[k][2];
				
				px += (prevnum-index) * geo[k][2];
								
			}
			
			System.out.println("Number of neighbors is: "+index);
			
			prevaverage = sum1 / (prevnum) ;
			
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
			
			radius=prevradius+0.5;
			
			findlocalneighbors(j,radius);
			
			Integer neighbornum = neighbors.size();
			
			Double absdiff=0.0;
			
			for(index=0;index < neighbors.size();index++){
				
				k= neighbors.get(index);
				
				sum1 += geo[k][2];
				
				px += (neighbornum-index) * geo[k][2];
				
				if(!prevneighbors.contains(k)){
					absdiff+=Math.abs(geo[k][2] - prevaverage);
				}
								
			}
			
			System.out.println("Number of neighbors is: "+index);
			
			Double u = sum1 / (neighbornum) ;
			
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
			
				k=0;sum1=0.0;
			
				System.out.println("Variance:");
			
				for(index = 0; index < neighbors.size();index++){
				
					sum1 += Math.pow(geo[neighbors.get(index)][2]-u,2);
				
					System.out.println(sum1+" = "+geo[neighbors.get(index)][2]+"	"+u);
				
				}
			
				variance = sum1 / neighbornum ;
			
				Double diff = giniindex.doubleValue() - prevginiindex.doubleValue();
			
				if(neighbornum!=prevnum)
			
					averagediff = Math.abs((sum1 - prevsumofu)/(neighbornum-prevnum) - prevaverage);
			
				if(giniindex.doubleValue() <= 0.30){
			
					System.out.println(j+"		"+geo[j][0]+"	"+geo[j][1]+"	"+prevradius+"	"+prevnum+"		"+prevginiindex+"	"+absdiff.doubleValue()+"	"+averagediff.doubleValue()+"	"+diff.doubleValue()+"	"+giniindex.doubleValue()+"		"+neighbornum+"\n");
	
				}
			
				else{
					giniindexstrings.add(j+"		"+geo[j][0]+"	"+geo[j][1]+"	"+prevradius+"	"+prevnum+"		"+prevginiindex.doubleValue()*100+"	"+absdiff.doubleValue()*100+"	"+averagediff.doubleValue()*100+"	"+diff.doubleValue()*100+"		"+giniindex.doubleValue()+"		"+neighbornum);

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
			
			}while(giniindex <=0.4 && prevnum <= numpoints/2);
			
		}
			
		File resultfile1 = new File(outfilepath+"/"+"NAginicirclescase1.txt");
		boolean exist = resultfile1.createNewFile();

		while(!exist){
				
			boolean success= resultfile1.delete();
			if(success)
				exist = resultfile1.createNewFile();
		}
			
		FileWriter fstream1 = new FileWriter(resultfile1);		
		BufferedWriter giniwriter1 = new BufferedWriter(fstream1);
			
		Collections.sort(giniindexstrings, new StringComparatorgini());
			
		ListIterator<String> it = giniindexstrings.listIterator();
			
		while(it.hasNext()){
			giniwriter1.write(it.next()+"\n");
		}
		giniwriter1.close();
		
		File resultfile2 = new File(outfilepath+"/"+"NAginicirclescase2.txt");
		exist = resultfile2.createNewFile();

		while(!exist){
				
			boolean success= resultfile2.delete();
			if(success)
				exist = resultfile2.createNewFile();
		}
			
		FileWriter fstream2 = new FileWriter(resultfile2);		
		BufferedWriter giniwriter2 = new BufferedWriter(fstream2);
			
		Collections.sort(giniindexstrings, new StringComparatorabssum());
			
		it = giniindexstrings.listIterator();
			
		while(it.hasNext()){
			giniwriter2.write(it.next()+"\n");
		}
		giniwriter2.close();
		
		File resultfile3 = new File(outfilepath+"/"+"NAginicirclescase3.txt");
		exist = resultfile3.createNewFile();

		while(!exist){
				
			boolean success= resultfile3.delete();
			if(success)
				exist = resultfile3.createNewFile();
		}
			
		FileWriter fstream3 = new FileWriter(resultfile3);		
		BufferedWriter giniwriter3 = new BufferedWriter(fstream3);
			
		Collections.sort(giniindexstrings, new StringComparatoravgsum());
			
		it = giniindexstrings.listIterator();
			
		while(it.hasNext()){
			giniwriter3.write(it.next()+"\n");
		}
		giniwriter3.close();
		
		File resultfile4 = new File(outfilepath+"/"+"NAginicirclescase4.txt");
		exist = resultfile4.createNewFile();

		while(!exist){
				
			boolean success= resultfile4.delete();
			if(success)
				exist = resultfile4.createNewFile();
		}
			
		FileWriter fstream4 = new FileWriter(resultfile4);		
		BufferedWriter giniwriter4 = new BufferedWriter(fstream4);
			
		Collections.sort(giniindexstrings, new StringComparatordiffgini());
			
		it = giniindexstrings.listIterator();
			
		while(it.hasNext()){
			giniwriter4.write(it.next()+"\n");
		}
		giniwriter4.close();
	}			
	

	static void findlocalneighbors(Integer i,Double radius){
	
	Double centerX = geo[i][0];
	Double centerY = geo[i][1];
	
	Integer j;
	
	for(j=0;j<numpoints;j++){
		
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

class StringComparatorgini implements Comparator<String>{
	
	public int compare(String s, String t) {
		
		//Integer indexs = giniareathresholdcombined.ginineighbors.indexOf(s);
		//Integer indext = giniareathresholdcombined.ginineighbors.indexOf(t);
		
		String a[] = s.split("\\s+");
		String b[] = t.split("\\s+");
		
		Double ginis = Double.parseDouble(a[5]);
		Double ginit = Double.parseDouble(b[5]);
		
		Integer diff = (int)Math.floor((ginis-ginit)*1000000000);
		
		if(!ginis.equals(ginit.doubleValue()))
	        return diff;
	    else
	        return 0;
		}
	 
 }

class StringComparatorabssum implements Comparator<String>{
	
	public int compare(String s, String t) {
		
		//Integer indexs = giniareathresholdcombined.ginineighbors.indexOf(s);
		//Integer indext = giniareathresholdcombined.ginineighbors.indexOf(t);
		
		String a[] = s.split("\\s+");
		String b[] = t.split("\\s+");
		
		Double ginis = Double.parseDouble(a[6]);
		Double ginit = Double.parseDouble(b[6]);
		
		Integer diff = (int)Math.floor((ginit-ginis)*1000000000);
		
		if(!ginis.equals(ginit.doubleValue()))
	        return diff;
	    else
	        return 0;
		}
	 
 }

class StringComparatoravgsum implements Comparator<String>{
	
	public int compare(String s, String t) {
		
		//Integer indexs = giniareathresholdcombined.ginineighbors.indexOf(s);
		//Integer indext = giniareathresholdcombined.ginineighbors.indexOf(t);
		
		String a[] = s.split("\\s+");
		String b[] = t.split("\\s+");
		
		Double ginis = Double.parseDouble(a[7]);
		Double ginit = Double.parseDouble(b[7]);
		
		Integer diff = (int)Math.floor((ginit-ginis)*1000000000);
		
		if(!ginis.equals(ginit.doubleValue()))
	        return diff;
	    else
	        return 0;
		}
	 
 }

class StringComparatordiffgini implements Comparator<String>{
	
	public int compare(String s, String t) {
		
		//Integer indexs = giniareathresholdcombined.ginineighbors.indexOf(s);
		//Integer indext = giniareathresholdcombined.ginineighbors.indexOf(t);
		
		String a[] = s.split("\\s+");
		String b[] = t.split("\\s+");
		
		Double ginis = Double.parseDouble(a[8]);
		Double ginit = Double.parseDouble(b[8]);
		
		Integer diff = (int)Math.floor((ginit-ginis)*1000000000);
		
		if(!ginis.equals(ginit.doubleValue()))
	        return diff;
	    else
	        return 0;
		}
	 
 }

