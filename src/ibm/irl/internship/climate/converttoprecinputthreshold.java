/*Ranjana Rajendran
 
 */

package ibm.irl.internship.climate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ListIterator;

public class converttoprecinputthreshold {
	
	public static Double left=4185.0,right=-5000.0,top=0.0,bottom=5000.0,upincrement = 0.0,rightincrement = 0.0, tempshift =0.0;
	
	public static Integer numpoints = 0, ncols =0, nrows =0;

	public static Double[][] geo = new Double[100000][3];
	
	public static Double[][] grid ;
	public static Integer[][] ngrid ;
	
	public static void main(String args[]) throws IOException{
		
		String infilepath = args[0];
		String outfilepath = args[1];
		Integer algomod = Integer.parseInt(args[2]);
		Integer limit=0;
		try{
			limit = Integer.parseInt(args[3]);
			
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("4th argument not given-so all will be calculated");
		}
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"tempNA.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));	
		
		String currentline;
		Integer i=0,j=0,p=0;	
		
		Double mintemp =0.0;
		
		while((currentline=br.readLine())!=null){
			
			currentline.trim();
			
			String current[]=currentline.split("\\s+");
			
			Double longitude = Double.parseDouble(current[3]);
			Double latitude = Double.parseDouble(current[4]);
			
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
			return;
		}
		else
			System.out.println("Left "+left+" Right "+right+" Top "+top+" Bottom "+bottom);
		
		
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
		
		Double radius,giniindex=0.0,variance=0.0;
		
		Integer count =0,ncount=0,maxmembers=0;
		
		grid = new Double[nrows][ncols];
		ngrid = new Integer[nrows][ncols];
		
		for(j=0; j < nrows; j++){
			
			for(p=0;p < ncols;p++){
				
					grid[j][p]= - 5000.0;
					ngrid[j][p]=0;
			}
		}
		
		for(j=0;j<nrows;j++){
			
			for(p=0;p<ncols;p++){	
				
				findgridvalues(j,p);
			}			
		}
		
		FileInputStream fstream1 = new FileInputStream(infilepath+"/"+"giniareastrings.txt");//Without sorting
		
		if(algomod.intValue()==1)
			fstream1 = new FileInputStream(infilepath+"/"+"giniareastringsgini.txt");// Sorted by gini index
		else if(algomod.intValue()==2)
			fstream1 = new FileInputStream(infilepath+"/"+"giniareastringsabswithneighbors.txt"); // Sorted by sum of abs value of difference between its mean and each neighbor
		else if(algomod.intValue()==3)
			fstream1 = new FileInputStream(infilepath+"/"+"giniareastringswithneighborsavg.txt");// Sorted by diff in average between itself and average of neighbors
		else if(algomod.intValue()==4)
			fstream1 = new FileInputStream(infilepath+"/"+"giniareastringsdiff.txt");// Sorted by difference in gini index by adding best neighbor
		
		DataInputStream in1 = new DataInputStream(fstream1);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
		
		ArrayList<String> giniareas = new ArrayList<String>();
		
		String currentline1;
		
		int countgini=0;
		
		while((currentline1=br1.readLine())!=null && countgini  < 120){
			giniareas.add(currentline1);
		}	
		File newfilep;
		if(limit.intValue() !=0)
			newfilep = new File(outfilepath+"/"+"precisionandrecall"+algomod+"withlimit"+limit+".txt");
		else
			newfilep = new File(outfilepath+"/"+"precisionandrecall"+algomod+".txt");
		boolean existp = newfilep.createNewFile();
	
		while(!existp){
		
			boolean success= newfilep.delete();
			if(success)
				existp = newfilep.createNewFile();
		}
	
		FileWriter fstream11p = new FileWriter(newfilep);
		BufferedWriter precrecall = new BufferedWriter(fstream11p);
		
		boolean if4tharg=false;
		
		Double recall =0.0;
		int numareas2=10;
		
		do{
			
			ListIterator<String> it = giniareas.listIterator();
			
			while(it.hasPrevious()){
				it.previous();
			}
		
			File newfile = new File(outfilepath+"/"+"ginineighboranomalies.txt");
			boolean exist = newfile.createNewFile();
		
			while(!exist){
			
				boolean success= newfile.delete();
				if(success)
					exist = newfile.createNewFile();
			}
		
			FileWriter fstream11 = new FileWriter(newfile);
			BufferedWriter anomwriter = new BufferedWriter(fstream11);
		
			Integer count1=0,numginiarea=0,numcells=0;
		
			boolean newvalidginiarea = true;
		
			while(it.hasNext() && numginiarea  < numareas2){
				
				if(!limit.equals(0)){
					if(numginiarea >= limit){
						if4tharg=true;
						break;
					}
				}
			
				//String[] cells = currentline.split("\\s+");
				String giniarea = it.next();
				String[] cells = giniarea.split(",");
				newvalidginiarea = true;
			
				for(int i1 =0; i1 < cells.length; i1++){
				
					Integer arraynum = Integer.parseInt(cells[i1]);
					Integer rownum = arraynum/ncols;
					Integer colnum = arraynum - rownum * ncols;
					//Integer index = rownum * 117 + colnum;
					if(rownum >=29 && colnum <=49 && !(grid[rownum][colnum].equals(-5000.0))){
						anomwriter.write(rownum+","+colnum+"\n");
						numcells++;
						if(newvalidginiarea==true){
							numginiarea++;
							newvalidginiarea=false;
						}
					}
					//count = Integer.parseInt(cells[1]);
			
				}
			
				count1 ++;
			
			}
		
			anomwriter.close();
		
			System.out.println("Number of gini area:"+numginiarea.intValue());
			System.out.println("Number of cells: "+ numcells);
			System.out.println("Rank in overall gini areas: "+ count1);
			//System.out.println("Total number");
		
			precisionrecallthreshold precrecallclass = new precisionrecallthreshold();
			String measures = precrecallclass.main(args);
			precrecall.write(numareas2+"	"+measures+"	"+numginiarea+"	 "+numcells+"	"+count1+"\n");
		
			System.out.println(measures);
		
			String meas[] = measures.split("\\s+");
			recall = Double.parseDouble(meas[1]);
		
			numareas2 = numareas2 + 10;
		
		}while(recall.doubleValue() < 1.0 && !(if4tharg));
		
		precrecall.close();
	}
	
	static void findgridvalues(Integer i, Integer j){
		
		Double longitude = left.doubleValue() + j * rightincrement.doubleValue();
		Double latitude = bottom.doubleValue() + i * upincrement.doubleValue();
		
		//System.out.println(numpoints);
		
		for(int k=0;k < numpoints;k++){
			if(geo[k][0].equals(longitude) && geo[k][1].equals(latitude)){
				grid[i][j]=geo[k][2].doubleValue() + tempshift + 1.0;
				ngrid[i][j]=1;
				System.out.println(geo[k][2].toString());
			}
		}
		
	}
	

}
