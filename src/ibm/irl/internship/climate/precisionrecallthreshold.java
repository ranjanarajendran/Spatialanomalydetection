/*
 Ranjana Rajendran
 */

package ibm.irl.internship.climate;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class precisionrecallthreshold {
	
	static Double left, right, top, bottom, mintemp, rightincrement, upincrement, tempshift;
	
	static int numpoints, nrows, ncols;
	
	static Double[][] geo=new Double[1000000][3];
	
	public static String main(String args[]) throws IOException{
		
		String infilepath = args[0];
		String outfilepath = args[1];
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"tempNA.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String currentline;
		Integer i=0,j=0,p=0;

		left=4185.0;right=-5000.0;top=0.0;bottom=5000.0;
		
		mintemp =0.0;
		
		while((currentline=br.readLine())!=null){
			
			currentline.trim();
			
			String current[]=currentline.split("\\s+");
			
			Double longitude = Double.parseDouble(current[3]);
			Double latitude = Double.parseDouble(current[4]);
			
		//	if(longitude >= -125.00 && longitude <= -100.0 && latitude >= 40.0 && latitude <= 50.0){
			
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
			
		}	
		
		if(mintemp < 0)
			tempshift = Math.abs(mintemp);
		else tempshift = 0.0;
		
		numpoints = i;
		
		System.out.println("Left:"+left+"	Right:"+right+"	Top:"+top+"	Bottom:"+bottom);
		
		System.out.println("Rightincrement:"+rightincrement+" Upincrement:"+upincrement);
		
		if(left > right || bottom > top){
			
			System.out.println("Error in left, right, bottom and top values");
			return "";
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
		
		//FileInputStream fstream3 = new FileInputStream(infilepath+"/"+"adityagroundtruth");
		DataInputStream in1 = new DataInputStream(fstream3);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
		
		FileInputStream fstream4 = new FileInputStream(infilepath+"/"+"ginineighboranomalies.txt");
		DataInputStream in2 = new DataInputStream(fstream4);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
		
		//ArrayList<Integer> groundtruth = new ArrayList<Integer>();
		
		Set<Integer> groundtruth = new HashSet();
		
		String input;
		
		Integer ct=0;
	
		while((input=br1.readLine())!=null){
			
			input.trim();
			
			String stringarray[] = input.split(",");
			
			System.out.println("Groundtruth :"+input);
			
			if(stringarray.length > 1){
				
			Integer rownum = Integer.parseInt(stringarray[0].trim());
			Integer colnum = Integer.parseInt(stringarray[1].trim());
		
			Integer arrayindex = rownum * ncols + colnum;
		
			groundtruth.add(arrayindex);
			
			ct++;
			}
		}
		
		Set<Integer> giniareas = new HashSet();
		//ArrayList<String> giniareas = new ArrayList<String>();
		
		Integer ct1=0;
		
		while((input = br2.readLine())!=null){
			
			String stringarray[] = input.split(",");
			
			System.out.println("Giniarea :"+input);
			
			if(stringarray.length > 1){
				
			Integer rownum = Integer.parseInt(stringarray[0].trim());
			Integer colnum = Integer.parseInt(stringarray[1].trim());
		
			Integer arrayindex = rownum * ncols + colnum;
		
			giniareas.add(arrayindex);
			
			ct1++;
			}
		}
			
		//Object[] giniareasarray = giniareas.toArray();
		
		Integer tp =0, fp =0, tn = 0, fn = 0;
		
		Iterator<Integer> it = giniareas.iterator();
	
		for(i=0; i < giniareas.size(); i++){
			
			Integer arraynum = it.next();
				
			if(groundtruth.contains(arraynum)){
				tp ++;
			}
			else
				fp ++;
						
		}
		
		Double precision = tp.doubleValue() / (tp.doubleValue() + fp.doubleValue());
		
		fn = groundtruth.size() - tp;
		
		Double recall = tp.doubleValue() / (tp.doubleValue() + fn.doubleValue());
		
		Double fmeasure = 2 * precision.doubleValue() * recall.doubleValue() / (precision.doubleValue() + recall.doubleValue());
		
		String result = precision.toString()+"	"+recall.toString()+"	"+ fmeasure.doubleValue();
		
		System.out.println("Precision:"+precision + " Recall:"+recall+" for the ground truth of "+groundtruth.size()+" number of cells and gini area cells"+giniareas.size());

		File newfile5 = new File(outfilepath+"/groundtruthseattlelatlongrectangles");
		boolean exist5 = newfile5.createNewFile();
		
		while(!exist5){
			
			boolean success= newfile5.delete();
			if(success)
				exist5 = newfile5.createNewFile();
		}
		
		FileWriter fstream5 = new FileWriter(newfile5);
		BufferedWriter gtruthwriter = new BufferedWriter(fstream5);
		
		Iterator<Integer> gtitr = groundtruth.iterator();
		
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
		
		
		File newfile4 = new File(outfilepath+"/giniareacellsseattlerectangles1");
		boolean exist4 = newfile4.createNewFile();
		
		while(!exist4){
			
			boolean success= newfile4.delete();
			if(success)
				exist4 = newfile4.createNewFile();
		}
		
		FileWriter fstream6 = new FileWriter(newfile4);
		BufferedWriter top10giniwriter = new BufferedWriter(fstream6);
		
		it = giniareas.iterator();
		
		for(i=0;i < giniareas.size(); i++){
			
			Integer arraynum = it.next();
		
			Integer rownum = arraynum / ncols;
			Integer colnum = arraynum - rownum * ncols;
				
			Double xleft = leftline + colnum * rightincrement;
			Double yleft = bottomline + rownum * upincrement;
				
			Double xright = xleft + 0.5;
			Double yright = yleft + 0.5;
				
			top10giniwriter.write(xleft+"  "+yleft+"  "+xright+"  "+yright+"\n");

		}
		
		top10giniwriter.close();
		
		return result;
				
	}
	
}



