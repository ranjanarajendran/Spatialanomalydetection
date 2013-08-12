
/*Ranjana Rajendran.
 
 */

package ibm.irl.internship.climate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

public class precisionrecallsatscanNA {
	
	static Double left, right, top, bottom, mintemp, rightincrement, upincrement, tempshift;
	
	static int numpoints, nrows, ncols;
	
	static Double[][] geo=new Double[1000000][3];
	
	//static ArrayList<String> giniareas;
	
	static Set<Integer> giniareas;
	
	public static void main(String args[]) throws IOException{
		
		String infilepath = args[0];
		String outfilepath = args[1];
		Integer maxnumcirc = Integer.parseInt(args[2]);
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"tempNA.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String linesXs = "/ablineXs.txt";
		String linesYs = "/ablineYs.txt";
		
		File newfile = new File(outfilepath+linesXs);
		boolean exist = newfile.createNewFile();
		
		while(!exist){
			
			boolean success= newfile.delete();
			if(success)
				exist = newfile.createNewFile();
		}
		
		FileWriter fstream1 = new FileWriter(newfile);
		BufferedWriter lineXwriter = new BufferedWriter(fstream1);
		
		File newfile1 = new File(outfilepath+linesYs);
		boolean exist1 = newfile1.createNewFile();
		
		while(!exist1){
			
			boolean success= newfile1.delete();
			if(success)
				exist1 = newfile1.createNewFile();
		}
		
		FileWriter fstream2 = new FileWriter(newfile1);
		BufferedWriter lineYwriter = new BufferedWriter(fstream2);
		
		
		String currentline;
		Integer i=0,j=0,p=0;
		
		
		
		left=4185.0;right=-5000.0;top=0.0;bottom=5000.0;
		
		mintemp =0.0;
        //Load data points to 2 d array
		
		while((currentline=br.readLine())!=null){
			
			currentline.trim();
			
			String current[]=currentline.split("\\s+");
			
			Double longitude = Double.parseDouble(current[3]);
			Double latitude = Double.parseDouble(current[4]);
			
	//		if(longitude >= -125.00 && longitude <= -100.0 && latitude >= 40.0 && latitude <= 50.0){
			
			geo[i][0]=Double.parseDouble(current[3]);
			geo[i][1]=Double.parseDouble(current[4]);
			geo[i][2]=Double.parseDouble(current[2]);
			
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
			
	//		}
		}	
		
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
		
		Double leftline = left - rightincrement / 2;
		
		Double bottomline = bottom - upincrement / 2 ;

		//FileInputStream fstream3 = new FileInputStream(infilepath+"/"+"manualanomaly.txt");
		FileInputStream fstream3 = new FileInputStream(infilepath+"/"+"groundtruthseattle.txt");
		DataInputStream in1 = new DataInputStream(fstream3);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
		//NAtempnormhighlow.col
		//FileInputStream fstream4 = new FileInputStream(infilepath+"/"+"giniareastringsginiseattle2.txt");
		FileInputStream fstream4 = new FileInputStream(infilepath+"/"+"output.col.txt");
		DataInputStream in2 = new DataInputStream(fstream4);
		BufferedReader br01 = new BufferedReader(new InputStreamReader(in2));
		
		//ArrayList<Integer> groundtruth = new ArrayList<Integer>();
		
		Set<Integer> groundtruth = new HashSet();
		
		String input;
		
		Integer ct=0;
        
        // Read ground truth
	
		while((input=br1.readLine())!=null){
			
			input.trim();
			
			String stringarray[] = input.split(",");
			
			System.out.println(input);
			
			if(stringarray.length > 1){
				
			Integer rownum = Integer.parseInt(stringarray[0].trim());
			Integer colnum = Integer.parseInt(stringarray[1].trim());
		
			Integer arrayindex = rownum * ncols + colnum;
		
			groundtruth.add(arrayindex);
			
			ct++;
			}
		}
		
		File newfilepr = new File(outfilepath+"/precisionrecallsatscan"+maxnumcirc+".txt");
		boolean existpr = newfilepr.createNewFile();
		
		while(!existpr){
			
			boolean success= newfilepr.delete();
			if(success)
				existpr = newfilepr.createNewFile();
		}
		
		FileWriter fstreampr = new FileWriter(newfilepr);
		BufferedWriter precrecallwriter = new BufferedWriter(fstreampr);
		
		File newfilec = new File(outfilepath+"/relevantcircles.txt");
		boolean existc = newfilec.createNewFile();
	
		while(!existc){
		
			boolean success= newfilec.delete();
			if(success)
				existc = newfilec.createNewFile();
		}
	
		FileWriter fstreamc = new FileWriter(newfilec);
		BufferedWriter relevantcircleids = new BufferedWriter(fstreamc);
		
		ArrayList<Integer> relevantcirids = new ArrayList<Integer>();
		
		
		ArrayList<String> satscancircles = new ArrayList<String>();
		
		while((input = br01.readLine())!=null){
			
			satscancircles.add(input);
			
		}
		
		ListIterator<String> itc = satscancircles.listIterator();
		
		System.out.println("Total number of circles: "+satscancircles.size());
		
		if(maxnumcirc.intValue() == 0)
			maxnumcirc = satscancircles.size();
		
		Double recall = 0.0;
        
        // Find first k circles, calculate precision and  recall then k=k+10 and so on
		
		for(int k = 10; k <= maxnumcirc && recall < 1.0; k+=10){
			
			relevantcirids.clear();
			
			
			while(itc.hasPrevious()){
				itc.previous();
			}
		
			giniareas = new HashSet();
		
			giniareas.clear();
		
			Integer ct1=0, rank =0;
		
			while(itc.hasNext() && ct1 < k){
			
				input = itc.next();
				String cells[] = input.split("\\s+");
			
				Double centrey = Double.parseDouble(cells[2]);
				Double centrex = Double.parseDouble(cells[3]);
				Double radius = Double.parseDouble(cells[4]);
			
				Double pvalue = Double.parseDouble(cells[9]);
			
				if(pvalue.doubleValue() <= 0.05){
					int num = findseattleneighbors(centrex, centrey,radius);
					if(num !=0){
						ct1++;
						relevantcirids.add(rank+1);
					}
				}
				rank++;
			
			}
		
			System.out.println("Number of circles:"+ct1);
			
			System.out.println("Rank of the "+ct1.intValue()+"th circle overall :"+rank.intValue());
	
			Integer giniareaarray[] = giniareas.toArray(new Integer[giniareas.size()]);
		
			Integer tp =0, fp =0, tn = 0, fn = 0;
		
			for(i=0; i < giniareaarray.length; i++){
				
				if(groundtruth.contains(giniareaarray[i])){
					tp ++;
				}
				else
					fp ++;		
			}
		
			Double precision = tp.doubleValue() / (tp.doubleValue() + fp.doubleValue());
		
			fn = groundtruth.size() - tp;
		
			recall = tp.doubleValue() / (tp.doubleValue() + fn.doubleValue());
			
			String precmeasure = ct1 +"		"+precision.doubleValue()+"		"+recall.doubleValue()+"	"+giniareaarray.length+"	"+rank ;
			
			precrecallwriter.write(precmeasure+"\n");
		
			System.out.println("Precision:"+precision + " Recall:"+recall+" for the ground truth of "+groundtruth.size()+" number of cells and satscan result cells"+ giniareaarray.length);
		
			File newfile4 = new File(outfilepath+"/seattlesatscanhighlowrectangles"+k);
			boolean exist4 = newfile4.createNewFile();
		
			while(!exist4){
			
				boolean success= newfile4.delete();
				if(success)
					exist4 = newfile4.createNewFile();
			}
		
			FileWriter fstream6 = new FileWriter(newfile4);
			BufferedWriter top10giniwriter = new BufferedWriter(fstream6);
		
			for(i=0;i < giniareaarray.length ; i++){
				
				Integer rownum = ((giniareaarray[i])) / ncols;
				Integer colnum = giniareaarray[i] - rownum * ncols;
				
				if(rownum.intValue() < 29)
					System.out.println("Check");
				
				Double xleft = leftline + colnum * rightincrement;
				Double yleft = bottomline + rownum * upincrement;
				
				Double xright = xleft + 0.5;
				Double yright = yleft + 0.5;
				
				top10giniwriter.write(xleft+"  "+yleft+"  "+xright+"  "+yright+"\n");

			}
		
			top10giniwriter.close();
		}
		
		precrecallwriter.close();
		
		ListIterator<Integer> itcirc = relevantcirids.listIterator();
		
		while(itcirc.hasNext()){
			int circlid = itcirc.next();
			relevantcircleids.write(circlid+"\n");
		}
		
		relevantcircleids.close();

		File newfile5 = new File(outfilepath+"/groundtruthseattlerectangles");
		boolean exist5 = newfile5.createNewFile();
		
		while(!exist5){
			
			boolean success= newfile5.delete();
			if(success)
				exist5 = newfile5.createNewFile();
		}
		
		FileWriter fstream5 = new FileWriter(newfile5);
		BufferedWriter gtruthwriter = new BufferedWriter(fstream5);
		
		Iterator gtitr = groundtruth.iterator();
		
		while(gtitr.hasNext()){
			
			Integer groundtruthindex = (Integer)gtitr.next();
			
			Integer rownum = groundtruthindex / ncols;
			Integer colnum = groundtruthindex - rownum * ncols;
			
			Double xleft = leftline + colnum * rightincrement;
			Double yleft = bottomline + rownum * upincrement;
			
			Double xright = xleft + 0.5;
			Double yright = yleft + 0.5;
			
			gtruthwriter.write(xleft+"  "+yleft+"  "+xright+"  "+yright+"\n");
			
		}
		
		gtruthwriter.close();
				
	}
    
    /* Find neighbor grid cells by Euclidean distance.
     
     */
	
	public static Integer findseattleneighbors(Double centrex, Double centrey,Double radius){
		
		//String giniareastring="";
		
		Integer count = 0;
		
		Double earthR = 6378.1;
		
		System.out.println("Radius: "+radius);
		
		for(int i =0; i < numpoints; i++){
			
			
			Double objX1= geo[i][0].doubleValue();
			Double objY1 = geo[i][1].doubleValue();
			Double objX2 = centrex.doubleValue();
			Double objY2 = centrey.doubleValue();
			Double dobjX = Math.abs(objX1 - objX2);
			Double dobjY = Math.abs(objY2 - objY1);
			
			Double distance = Math.abs(Math.sqrt(Math.pow(dobjX,2) + Math.pow(dobjY, 2)));
			
			//System.out.println("Distance: "+distance);
			
			Integer rownum = Math.abs((int)Math.floor((geo[i][1] - bottom) / upincrement)) ;
			Integer colnum = Math.abs((int)Math.floor((geo[i][0] - left)/ rightincrement));
			
			if(rownum.intValue() == 37 && colnum.intValue() == 45)
				System.out.println("Abnormal rownum and colnum");
			
			if (distance.doubleValue() <= radius && geo[i][0].doubleValue() >= -125.00 && geo[i][0].doubleValue() <= -100.0 && geo[i][1].doubleValue() >= 40.0 && geo[i][1].doubleValue() <= 50.0){
				
			//	if(count!=0)
			//		giniareastring+=",";
								
				Integer arrayindex = rownum * ncols + colnum;
				
				if(colnum.equals(16) && rownum.intValue() < 29)
					System.out.println("Colnum 16 hit");
				
				//giniareastring = arrayindex.toString();
				
				giniareas.add(arrayindex);
				
				count++;
				
			}
			
		}
		
		return count;
		
	}
}
