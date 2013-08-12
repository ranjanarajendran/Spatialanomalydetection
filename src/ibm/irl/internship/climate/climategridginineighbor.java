package ibm.irl.internship.climate;


// Only best pair combined in one iteration

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class climategridginineighbor {
	
	static Double[][] geo=new Double[1000000][3];
	
	static Double[][] grid;
	
	static Integer[][] ngrid;
	
	static Integer[][] ginied;
	
	static Integer[][] whichginiarea;
	
	static Integer idgini;
	
	static Double rightincrement, upincrement, left, right, top,bottom,mintemp, tempshift;
	
	static int n,sum,px, numpoints, nrows,ncols;
	
	static String infilepath;
	
	static ArrayList<Integer> members;
	
	static CopyOnWriteArrayList<String> ginineighbors = new CopyOnWriteArrayList();
	
	static CopyOnWriteArrayList<Double> ginindices = new CopyOnWriteArrayList();
	
	public static Double bestginiofneighbordouble[];
	
	public static Double bestpreviousginidouble[];
	
	public static Double ginindicesarray[];
	
	public static void main(String args[]) throws IOException{
		
		infilepath = args[0];
		String outfilepath = args[1];
		
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
		
		while((currentline=br.readLine())!=null){
			
			currentline.trim();
			
			String current[]=currentline.split("\\s+");
			
			Double longitude = Double.parseDouble(current[3]);
			Double latitude = Double.parseDouble(current[4]);
			
			//if(longitude >= -125.00 && longitude <= -100.0 && latitude >= 30.0 && latitude <= 45.0){
			
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
			
		//	}
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
		
		lineXwriter.write(leftline + "\n");
		
		for(i = 1; i <= ncols; i++){
			
			Double leftlinenew = leftline + i * rightincrement;
			
			lineXwriter.write(leftlinenew+"\n");
			
		}
		
		Double bottomline = bottom - upincrement / 2 ;
		
		lineYwriter.write(bottomline + "\n");
		
		for(i=0;i <= nrows; i++){
			
			Double bottomlinenew = bottomline + i * upincrement;
			
			lineYwriter.write(bottomlinenew + "\n");
		}
		
		lineXwriter.close();
		lineYwriter.close();
		
		
		Double radius,giniindex=0.0,variance=0.0;
		
		Integer count =0,ncount=0,maxmembers=0;
		
		ginineighbors.clear();
		ginindices.clear();
		ginied = new Integer[nrows][ncols];
		whichginiarea = new Integer[nrows][ncols];
		grid = new Double[nrows][ncols];
		ngrid = new Integer[nrows][ncols];
		ginied = new Integer[nrows][ncols];
		
		for(j=0; j < nrows; j++){
			
			for(p=0;p < ncols;p++){
				
					grid[j][p]= - 5000.0;
					ngrid[j][p]=0;
			}
		}
		
		for(j=0;j<nrows;j++){
			
			for(p=0;p<ncols;p++){	
				
				findgridvalues(j,p);
				
				Integer arraynum = j*ncols+p;
				ginineighbors.add(arraynum.toString());
				ginied[j][p]=1;
				ginindices.add(0.0);
				whichginiarea[j][p]=arraynum;
			}			
		}
		
		Integer n=0;
		for(j=0; j < nrows; j++){
			
			for(p=0;p < ncols;p++){
				
					if(ngrid[j][p]!=0)
						n++;
			}
		}	
		
		if(n==0)
			System.out.println("Grid not being set");
		else
			System.out.println("Ngrid points:"+n);

		Integer iteration=0;
		
		//ListIterator<String> it = ginineighbors.listIterator();
	
		Double mingini=2.0,maxgini=0.0;
		
		//ArrayList<String> copyginineighbors = new ArrayList<String>(ginineighbors);
		
		//ListIterator<String> it = ginineighbors.listIterator();
		
		CopyOnWriteArrayList<Integer> bestginineighbor = new CopyOnWriteArrayList();
		
		CopyOnWriteArrayList<Double> bestginiofneighbor = new CopyOnWriteArrayList();
		
		CopyOnWriteArrayList<Double> bestginiaverage = new CopyOnWriteArrayList();
		
		
		
		do{
			ListIterator<String> it = ginineighbors.listIterator();
			
			ListIterator<Double> itgini = ginindices.listIterator();
			
			mingini = 2.0;
			iteration++;
				
			bestginineighbor.clear();
			bestginiofneighbor.clear();
			bestginiaverage.clear();
			//ginindices.clear();
		
			while(it.hasPrevious()){
			
				it.previous();
			}
		
			while(it.hasNext()){
				bestginineighbor.add(0);
				bestginiofneighbor.add(2.0);
				bestginiaverage.add(0.0);
				//ginindices.add(0.0);
				Integer index = it.nextIndex();
				String cells[] = it.next().split(",");
				
				Integer size = cells.length;
				
				for(int s = 0; s < size; s++){
					
					Integer arraynum = Integer.parseInt(cells[s]);
					Integer rownum = (arraynum) / ncols;
					
					Integer colnum = arraynum - rownum * ncols;
					
					
					whichginiarea[rownum][colnum]=index;
				      

					
				}
			}
			
			while(it.hasPrevious()){
				
				it.previous();
			}
			
			while(itgini.hasPrevious()){
				
				itgini.previous();
				
			}
			
			Integer towhichtoaddto=0;
		
			while(it.hasNext()){
				
				towhichtoaddto = it.nextIndex();
				String element = it.next().toString();
				
				//if(element.equals("5142,5242"))
					//System.out.println("Note");
			
				String cells[] = element.split(",");
			
				Integer indices[] = new Integer[cells.length];
			
				Integer rownum,colnum;
				Double value;
			
				Set<String> nearestneighbors = new HashSet();
			
				nearestneighbors.clear();
			
				int q;
			
				for(q = 0; q < cells.length;q++){
					indices[q]=Integer.parseInt(cells[q]);
				
					nearestneighbors.add(cells[q]);
				
					rownum = (indices[q]) / ncols;
				
					colnum = indices[q] - rownum * ncols;
					

					
					//if((rownum == 51 && colnum == 42) || (rownum == 52 && colnum == 42) )
					//	System.out.println("Wny no gini area ?");
					
					String arrayindex;
					Integer arraynum;
					
					/*Integer upcell = (rownum + 1) * gran + colnum;
					Integer downcell = (rownum -1) * gran + colnum;
					Integer leftcell = rownum*gran + colnum -1;
					Integer rightcell = rownum * gran + colnum + 1;*/
					
					//String gots="";
					
				    if(rownum < nrows-1){
				 //   	if(ginied[rownum+1][colnum] > 0 )
				    	String	gots = ginineighbors.get(whichginiarea[rownum+1][colnum]);
				    	//if(gots.length() == ginied[rownum+1][colnum])
				    	//if(!(element.equals(gots)) && ngrid[rownum+1][colnum].intValue() > 0 )
				    	if(!(element.equals(gots)))
				    		nearestneighbors.add(gots);
				    }
					
				    if(colnum < ncols-1){
				    	String gots = ginineighbors.get(whichginiarea[rownum][colnum+1]);
				    	//if(gots.length() == ginied[rownum][colnum+1])
				    	//if(!(element.equals(gots)) && ngrid[rownum][colnum+1].intValue() > 0)
				    	if(!(element.equals(gots)))
				    		nearestneighbors.add(gots);
				    }
					
					if(rownum > 0 ){
				    	String gots = ginineighbors.get(whichginiarea[rownum-1][colnum]);
				    	//if(gots.length() == ginied[rownum-1][colnum])
				    	//if(!(element.equals(gots)) && ngrid[rownum-1][colnum].intValue() > 0)
				    	if(!(element.equals(gots)))
				    		nearestneighbors.add(gots);
					}
				
					if(colnum > 0 ){
				    	String gots = ginineighbors.get(whichginiarea[rownum][colnum-1]);
				    	//if(gots.length() == ginied[rownum][colnum-1])
				    	//if(!(element.equals(gots)) && ngrid[rownum][colnum-1].intValue() > 0)
				    	if(!(element.equals(gots)))
				    		nearestneighbors.add(gots);
					}	
				}
			
				for (q=0;q < cells.length; q++){
				
					nearestneighbors.remove(cells[q]);
				
				}
			
				Double sum=0.0,px=0.0;
			
				Integer  bestindex=0,num=0;
			
				Double bestgini=2.0, bestginaverage=0.0;
			
				Iterator<String> itr = nearestneighbors.iterator();
			
				while(itr.hasNext()){
				
					sum=0.0;
					px=0.0;
					num=0;
					
					ArrayList<Double> evalset = new ArrayList();
					
					evalset.clear();
					
					String indexstring = itr.next();
					

					
					Integer index = ginineighbors.indexOf(indexstring);
				
					String indexstrings[] = indexstring.split(",");
					
					for(int s=0; s < indexstrings.length ; s++){
						
						Integer index1 = Integer.parseInt(indexstrings[s]);
						
						Integer rownum1 = index1 / ncols;
						
						Integer colnum1 = index1 - rownum1 * ncols;
						
						//if(colnum1 == 9 && rownum1 == 17){
							
						//	System.out.println("Stop and study");
						//}
						
					//	if(ngrid[rownum1][colnum1] > 0){
							sum += grid[rownum1][colnum1].doubleValue() ;
						
							num += 1;

							evalset.add(grid[rownum1][colnum1].doubleValue());	
					//	}
					}	
			
					for (q=0;q<cells.length ; q++){
				
						rownum = (Integer.parseInt(cells[q])) / ncols;
				
						colnum = Integer.parseInt(cells[q]) - rownum * ncols;
						
						//if(colnum == 9 && rownum == 17){
							
						//	System.out.println("Stop and study");
						//}
						
					//	if(ngrid[rownum][colnum] > 0){
				
							num += 1;
				
							sum += grid[rownum][colnum].doubleValue();
					
							evalset.add(grid[rownum][colnum].doubleValue());
					//	}
				
					}
				
					Collections.sort(evalset);
				
					//Iterator itreval = evalset.iterator();
					
					Integer evalsize=evalset.size();
				
					for(q=0;q<evalsize;q++){
					
						px += (evalsize.doubleValue() -q)*evalset.get(q);
					}
				
					Double u = sum / num;
					
					//if(u == 0.003592511003389872)
					//	System.out.println("Notice");
				
					Double neighborratio=0.0,ginivalue=0.0;
				
					if ( u != -5000.0 && u > 0.0 && num > 1){
					
						neighborratio = (num.doubleValue() + 1)/(num.doubleValue()-1);
					
						ginivalue = neighborratio - ((2 * px)/(num*(num-1)*u));
						
						//ginivalue=ginivalue.doubleValue();
						
						if(ginivalue < 0){
							ginivalue = 0.0;
						}
					
						if(ginivalue < bestgini){
						
							bestgini = ginivalue;
						
							bestindex = index;
							
							bestginaverage = u;
						}
						
					
					}
			
				}
			
				//Integer listindex = ginineighbors.indexOf(element);
				
				//if(listindex <0)
				//	System.out.println("Out of bounds");
				
				bestginineighbor.set(towhichtoaddto,bestindex);
			    //itbestginineighbor.next();
				bestginiofneighbor.set(towhichtoaddto,bestgini);
				bestginiaverage.set(towhichtoaddto,bestginaverage);
				//ginindices.set(listindex,bestgini);
				//System.out.println(bestginaverage);
			    //itbestginiofneighbor.next();
				
				
			
			}
		
			ListIterator<Double> itbestginiofneighbor = bestginiofneighbor.listIterator();
			ListIterator<Integer> itbestginineighbor = bestginineighbor.listIterator();
			
			Integer minginiindex=0,minclusterindex=0,count3=0;
		
			while(itbestginiofneighbor.hasNext()){
			
				Double ginival = itbestginiofneighbor.next();
				Integer ginivalindex = itbestginineighbor.next();
			
				if( ginival < mingini.doubleValue()){
				
					mingini = ginival;
					minginiindex = ginivalindex;
					minclusterindex=count3;
				
				}
				
				count3++;
				
			}
		
			String neighbor1 = ginineighbors.get(minginiindex);
			
			String neighbor2 = ginineighbors.get(minclusterindex);
			
			String neighbor3 = neighbor2;
			
			neighbor2 = neighbor2 + "," +neighbor1;
			
			//ginineighbors.add(index, element)
			
			ginineighbors.set(minclusterindex, neighbor2);
			
			ginindices.set(minclusterindex,mingini);
			
			//if(minginiindex > minclusterindex)
			//	minginiindex=minginiindex+1;
			
			
			ginineighbors.remove(neighbor1);
			Double obj = ginindices.remove(minginiindex.intValue());
			System.out.println(obj.toString()+" removed");
			
			System.out.println("Ginineighbors resized: "+ginineighbors.size() + "Neighbor1:"+ neighbor1+" Neighbor 2:"+neighbor2);
			
		    String cells[] = neighbor2.split(",");
		    
		    Integer indices[] = new Integer[cells.length];
		    
		    Integer rownum,colnum;
		    
		    Integer cellsize = cells.length;
		    
		    for(int q=0;q< cells.length;q++){
		    	
		    	indices[q]=Integer.parseInt(cells[q]);
		    	
		    	rownum = indices[q] / ncols;
		    	
		    	colnum = indices[q] - rownum * ncols;
		    	
		    	//Integer cellsize = cells.length;
		    	
		    	ginied[rownum][colnum]=cellsize.intValue();
		    	
		    	if(colnum > 50 && ginied[rownum][colnum]>1){
		    		System.out.println("["+rownum+"]"+"["+colnum+"]"+ " ginied");
		    	}
		    	
		    //	System.out.println("Cell updated for :"+rownum+","+colnum+":"+cells.length);
		    	
		    }
		    
		    System.out.println(mingini);
		    
		    System.out.println(ginineighbors.size()+","+ginindices.size());
		    
		    System.out.println(iteration+" Number iteration");
		
		}while(mingini < 0.05);
		
		
		File newfile5 = new File(outfilepath+"/climateginiarearectangles");
		boolean exist5 = newfile5.createNewFile();
		
		while(!exist5){
			
			boolean success= newfile5.delete();
			if(success)
				exist5 = newfile5.createNewFile();
		}
		
		FileWriter fstream5 = new FileWriter(newfile5);
		BufferedWriter out5 = new BufferedWriter(fstream5);
		
		ListIterator<String> it = ginineighbors.listIterator();
		
		ListIterator<Double> itgini = ginindices.listIterator();
		
		if(ginineighbors.size()!=ginindices.size()){
			System.out.println("Error - in algorithm");
			return;
		}
		
		while(it.hasPrevious()){
			
			it.previous();
		}
		
		while(itgini.hasPrevious()){
			
			itgini.previous();
		}
		
		Integer count1=0,biggest=0,countgini=0;
		
		for(i=0;i < nrows; i++)
			for(j=0;j< ncols; j++)
				if(!ginied[i][j].equals(1))
					countgini++;
		
		if(countgini==0)
			System.out.println("ginied not set:"+ginied[nrows-2][ncols-2]);
		
		Integer countsingleginiareas =0;
		
		while(it.hasNext()){
			
			String cells[] = it.next().split(",");
			
			//System.out.println(count1+" group of cells: ");
			
			Double gini = itgini.next();
			
			if(cells.length==1)
				countsingleginiareas++;
			
			//if(gini == 0.15182219930876828)
			//	System.out.println("Note");
			
			//if((cells.length) > 1){
			
				for (int q=0; q < cells.length;q++){
				
					Integer index = Integer.parseInt(cells[q]);
				
					Integer rownum = index / ncols;
				
					Integer colnum = index - rownum * ncols;
				
					System.out.println("("+rownum+","+colnum+")");
				
					Double leftx= leftline+(colnum)*rightincrement;
					Double lefty = bottomline+(rownum)*upincrement;
					Double rightx = leftline+(colnum+1)*rightincrement;
					Double righty = bottomline + (rownum+1)*upincrement;
				
					out5.write(leftx.toString()+"	"+lefty.toString()+"	"+rightx.toString()+"	"+righty.toString()+"	"+gini.toString()+"	"+grid[rownum][colnum].toString()+"	"+ rownum.toString()+"	"+colnum.toString()+"	"+ngrid[rownum][colnum].toString()+"	"+ginied[rownum][colnum].toString()+"	"+count1.toString()+"\n");
				
				}
				
				out5.write("\n");
			//}
			
			
			
			if(biggest < cells.length)
				biggest = cells.length;
			
			
			count1++;
			
		}
		
		out5.close();
		
		System.out.println("mingini:"+mingini);
		
		System.out.println("Number of iterations:"+ iteration);
		
		System.out.println("Number of gini areas: "+ginineighbors.size());
		
		System.out.println("Single cell gini areas :"+ countsingleginiareas);
		
		System.out.println("Biggest gini cluster has size: "+biggest);
		
		while(it.hasPrevious()){
			it.previous();
		}
		
		File newfile6 = new File(outfilepath+"/giniareastrings.txt");
		boolean exist6 = newfile6.createNewFile();
		
		while(!exist6){
			
			boolean success= newfile6.delete();
			if(success)
				exist6 = newfile6.createNewFile();
		}
		
		FileWriter fstream6 = new FileWriter(newfile6);
		BufferedWriter out6 = new BufferedWriter(fstream6);
		
		
		while(it.hasNext()){
			out6.write(it.next()+"\n");
		}
		
		out6.close();
		
		String giniareaarraystring[] = ginineighbors.toArray(new String[ginineighbors.size()]);
		
		bestginiofneighbordouble = bestginiofneighbor.toArray(new Double[bestginiofneighbor.size()]);
		
		//bestpreviousginidouble = bestpreviousgini.toArray(new Double[bestpreviousgini.size()]);
		
		ginindicesarray = ginindices.toArray(new Double[ginindices.size()]);
		
		//if(ginineighbors.size()!=bestginiofneighbor.size() || bestginiofneighbor.size()!= bestpreviousgini.size()){
		//	System.out.println("Arrays not of equal size");
		//}
		
		Arrays.sort(giniareaarraystring, new GiniComparator41());// sorts according to increase in gini index when combined with best neighbor
	
		
		File newfile61 = new File(outfilepath+"/giniareastringsdiff.txt");
		boolean exist61 = newfile61.createNewFile();
		
		while(!exist61){
			
			boolean success= newfile61.delete();
			if(success)
				exist61 = newfile61.createNewFile();
		}
		
		FileWriter fstream61 = new FileWriter(newfile61);
		BufferedWriter out61 = new BufferedWriter(fstream61);
		
		
		for(int h=0;h < giniareaarraystring.length;h++){
			out61.write(giniareaarraystring[h]+"\n");
		}
		
		out61.close();
		
		Arrays.sort(giniareaarraystring, new GiniComparatorgini41());// Sorts according to gini index
	
		
		File newfile7 = new File(outfilepath+"/giniareastringsgini.txt");
		boolean exist7 = newfile7.createNewFile();
		
		while(!exist7){
			
			boolean success= newfile7.delete();
			if(success)
				exist7 = newfile7.createNewFile();
		}
		
		FileWriter fstream7 = new FileWriter(newfile7);
		BufferedWriter out7 = new BufferedWriter(fstream7);
		
		
		for(int h=0;h < giniareaarraystring.length;h++){
			out7.write(giniareaarraystring[h]+"\n");
		}
		
		Arrays.sort(giniareaarraystring, new GiniComparatorbyneighbor41());// Sorts in decreasing by sum of absolute value of difference between each neighbor and mean
		
		File newfile9 = new File(outfilepath+"/giniareastringsabswithneighbors.txt");
		boolean exist9 = newfile9.createNewFile();
		
		while(!exist9){
			
			boolean success= newfile9.delete();
			if(success)
				exist9 = newfile9.createNewFile();
		}
		
		FileWriter fstream9 = new FileWriter(newfile9);
		BufferedWriter out9 = new BufferedWriter(fstream9);
		
		
		for(int h=0;h < giniareaarraystring.length;h++){
			out9.write(giniareaarraystring[h]+"\n");
		}
		
		Arrays.sort(giniareaarraystring, new GiniComparatorbyneighboravg41());// Sorts in decreasing order of average of neighbors and inside gini
		
		File newfile10 = new File(outfilepath+"/giniareastringswithneighborsavg.txt");
		boolean exist10 = newfile10.createNewFile();
		
		while(!exist10){
			
			boolean success= newfile10.delete();
			if(success)
				exist10 = newfile10.createNewFile();
		}
		
		FileWriter fstream10 = new FileWriter(newfile10);
		BufferedWriter out10 = new BufferedWriter(fstream10);
		
		
		for(int h=0;h < giniareaarraystring.length;h++){
			out10.write(giniareaarraystring[h]+"\n");
		}
		
		out7.close();
		//out8.close();
		out9.close();
		out10.close();
	
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
	

	static void findlocalneighborsingrid(Double left,Double right,Double bottom,Double top){
		
		Integer j;
		
		for(j=0;j<n;j++){
			
			Double X = geo[j][0];
			Double Y = geo[j][1];
			Double speed1 = geo[j][2];
			
			if(X >= left && X < right && Y >= bottom && Y < top){
				
				//int index=0;
				
				//int size = members.size();
			
				//for(index=0;index < size ;index++){
					
				//	Double speed2 = geo[members.get(index)][2];
					
				//	if(speed1 <= speed2){
						
						//System.out.println(i+" added"+" neighbor "+j+" neighbor size :"+neighbors.size());
						
				//		members.add(index,j);
						
				//		break;
						
				//	}
					
				//}
				//if((index == members.size()) && !(members.contains(j))){
					
					members.add(j);
					
				//}
				
				
			}
			
		}	
	}
	
}
	class GiniComparator41 implements Comparator<String> {  // Sorts(decreasing) according to difference in gini when added to best neighbor
		
		public int compare(String s, String t) {
			
			Integer indexs = climategridginineighbor.ginineighbors.indexOf(s);
			Integer indext = climategridginineighbor.ginineighbors.indexOf(t);
			
			Double ginidiffs = Math.abs(climategridginineighbor.bestginiofneighbordouble[indexs] - climategridginineighbor.ginindicesarray[indexs]);
			Double ginidifft = Math.abs(climategridginineighbor.bestginiofneighbordouble[indext] - climategridginineighbor.ginindicesarray[indext]);
			
			Double diff = (ginidifft-ginidiffs) * 10000;
			
			if(!ginidiffs.equals(ginidifft)){
				
				//System.out.println(diff.toString());
		        return diff.intValue();}
		    else
		        return 0;
		}
	}



	class GiniComparatorgini41 implements Comparator<String> { // Sorts according to gini 
			
		public int compare(String s, String t) {
			
			Integer indexs = climategridginineighbor.ginineighbors.indexOf(s);
			Integer indext = climategridginineighbor.ginineighbors.indexOf(t);
			
			Double ginis = climategridginineighbor.ginindicesarray[indexs];
			Double ginit = climategridginineighbor.ginindicesarray[indext];
			
			Integer diff = (int)Math.floor((ginis-ginit)*1000000000);
			
			if(!ginis.equals(ginit))
		        return diff;
		    else
		        return 0;
		}
	}

	class GiniComparatorbyneighbor41 implements Comparator<String> { // Sorts according to sum of absolute difference between mean of giniarea and each of its neighbors
			
		public int compare(String s, String t) {
			
			String cells[] = s.split(",");
			String cellt[] = t.split(",");
			
			Set<Integer> nearestneighbors = new HashSet();
			
			nearestneighbors.clear();
			
			Double means = 0.0;
			
			for(int i =0; i < cells.length; i++){
				
				Integer arrayindex = Integer.parseInt(cells[i]);
				
				nearestneighbors.add(arrayindex);
				
				Integer rownum = arrayindex / climategridginineighbor.ncols;
				
				Integer colnum = arrayindex - rownum * climategridginineighbor.ncols;
				
				means += climategridginineighbor.grid[rownum][colnum];
				
			    if(rownum < climategridginineighbor.nrows-1){
			    	if(climategridginineighbor.grid[rownum+1][colnum] >= 0.0){
			    	Integer narrayindex = (rownum + 1) * climategridginineighbor.ncols + colnum;
			    	nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(colnum < climategridginineighbor.ncols -1 ){
			    	if(climategridginineighbor.grid[rownum][colnum+1] >= 0.0){
			    	Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum+1;
			    	nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(rownum > 0){
			    	if(climategridginineighbor.grid[rownum-1][colnum] >= 0.0){
			    	Integer narrayindex = (rownum - 1) * climategridginineighbor.ncols + colnum;
			    	nearestneighbors.add(narrayindex);
			    	}
				}
			
				if(colnum > 0 ){
					if(climategridginineighbor.grid[rownum][colnum-1] >= 0.0){
			    	Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum - 1;
			    	nearestneighbors.add(narrayindex);
					}
				}	
				
			}
			
			means = means / cells.length;
			
			for(int i=0; i < cells.length; i++){
				Integer arrayindex = Integer.parseInt(cells[i]);
				nearestneighbors.remove(arrayindex);
			}
			
			Double sumabs = 0.0;
			
			Iterator<Integer> it = nearestneighbors.iterator();
			
			while(it.hasNext()){
				Integer narrayindex = it.next();
				
				Integer rownum = narrayindex / climategridginineighbor.ncols;
				
				Integer colnum = narrayindex - rownum * climategridginineighbor.ncols;
				
				sumabs = Math.abs(climategridginineighbor.grid[rownum][colnum] - means);
				
			}
			
			
			nearestneighbors.clear();
			
			Double meant = 0.0;
			
			for(int i =0; i < cellt.length; i++){
				
				Integer arrayindex = Integer.parseInt(cellt[i]);
				
				nearestneighbors.add(arrayindex);
				
				Integer rownum = arrayindex / climategridginineighbor.ncols;
				
				Integer colnum = arrayindex - rownum * climategridginineighbor.ncols;
				
				meant += climategridginineighbor.grid[rownum][colnum];
				
			    if(rownum < climategridginineighbor.nrows-1){
			    	if(climategridginineighbor.grid[rownum+1][colnum] >= 0.0){

			    	Integer narrayindex = (rownum + 1) * climategridginineighbor.ncols + colnum;
			    	nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(colnum < climategridginineighbor.ncols -1 ){
			    	if(climategridginineighbor.grid[rownum][colnum+1] >= 0.0){

			    	Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum+1;
			    	nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(rownum > 0){
			    	if(climategridginineighbor.grid[rownum-1][colnum] >= 0.0){

			    	Integer narrayindex = (rownum - 1) * climategridginineighbor.ncols + colnum;
			    	nearestneighbors.add(narrayindex);
			    	}
				}
			
				if(colnum > 0 ){
			    	if(climategridginineighbor.grid[rownum][colnum-1] >= 0.0){

			    	Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum - 1;
			    	nearestneighbors.add(narrayindex);
			    	}
				}	
				
			}
			
			meant = meant / cellt.length;
			
			for(int i=0; i < cellt.length; i++){
				Integer arrayindex = Integer.parseInt(cellt[i]);
				nearestneighbors.remove(arrayindex);
			}
			
			Double sumabt = 0.0;
			
			it = nearestneighbors.iterator();
			
			while(it.hasNext()){
				Integer narrayindex = it.next();
				
				Integer rownum = narrayindex / climategridginineighbor.ncols;
				
				Integer colnum = narrayindex - rownum * climategridginineighbor.ncols;
				
				sumabt = Math.abs(climategridginineighbor.grid[rownum][colnum] - meant);
				
			}
			
			
			Integer diff = (int)Math.floor((sumabt-sumabs)*1000000000);
			
			if(!sumabs.equals(sumabt))
		        return diff;
		    else
		        return 0;
		}
	}


	class GiniComparatorbyneighboravg41 implements Comparator<String> { // Sorts according to diff between mean inside and outside
			
		public int compare(String s, String t) {
			
			String cells[] = s.split(",");
			String cellt[] = t.split(",");
			
			Set<Integer> nearestneighbors = new HashSet();
			
			nearestneighbors.clear();
			
			Double means = 0.0;
			
			for(int i =0; i < cells.length; i++){
				
				Integer arrayindex = Integer.parseInt(cells[i]);
				
				nearestneighbors.add(arrayindex);
				
				Integer rownum = arrayindex / climategridginineighbor.ncols;
				
				Integer colnum = arrayindex - rownum * climategridginineighbor.ncols;
				
				means += climategridginineighbor.grid[rownum][colnum];
				
			    if(rownum < climategridginineighbor.nrows-1){
			    	
			    	if(climategridginineighbor.grid[rownum+1][colnum] >= 0.0){
			    		Integer narrayindex = (rownum + 1) * climategridginineighbor.ncols + colnum;
			    		nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(colnum < climategridginineighbor.ncols -1 ){
			    	if(climategridginineighbor.grid[rownum][colnum+1] >= 0.0){
			    		Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum+1;
			    		nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(rownum > 0){
			    	if(climategridginineighbor.grid[rownum-1][colnum] >= 0.0){

			    		Integer narrayindex = (rownum - 1) * climategridginineighbor.ncols + colnum;
			    		nearestneighbors.add(narrayindex);
			    	}
				}
			
				if(colnum > 0 ){
			    	if(climategridginineighbor.grid[rownum][colnum-1] >= 0.0){

			    		Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum - 1;
			    		nearestneighbors.add(narrayindex);
			    	}
				}	
				
			}
			
			means = means / cells.length;
			
			for(int i=0; i < cells.length; i++){
				Integer arrayindex = Integer.parseInt(cells[i]);
				nearestneighbors.remove(arrayindex);
			}
			
			Double meanneighbos = 0.0;
			
			Integer cs=0;
			
			Iterator<Integer> it = nearestneighbors.iterator();
			
			while(it.hasNext()){
				Integer narrayindex = it.next();
				
				cs++;
				
				Integer rownum = narrayindex / climategridginineighbor.ncols;
				
				Integer colnum = narrayindex - rownum * climategridginineighbor.ncols;
				
				meanneighbos += climategridginineighbor.grid[rownum][colnum];
				
			}
			
			Double diffs = Math.abs(means.doubleValue() - meanneighbos.doubleValue()/cs);
			
			
			nearestneighbors.clear();
			
			Double meant = 0.0;
			
			for(int i =0; i < cellt.length; i++){
				
				Integer arrayindex = Integer.parseInt(cellt[i]);
				
				nearestneighbors.add(arrayindex);
				
				Integer rownum = arrayindex / climategridginineighbor.ncols;
				
				Integer colnum = arrayindex - rownum * climategridginineighbor.ncols;
				
				meant += climategridginineighbor.grid[rownum][colnum];
				
			    if(rownum < climategridginineighbor.nrows-1){
			    	if(climategridginineighbor.grid[rownum+1][colnum] >= 0.0){

			    	Integer narrayindex = (rownum + 1) * climategridginineighbor.ncols + colnum;
			    	nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(colnum < climategridginineighbor.ncols -1 ){
			    	if(climategridginineighbor.grid[rownum][colnum+1] >= 0.0){

			    	Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum+1;
			    	nearestneighbors.add(narrayindex);
			    	}
			    }
				
			    if(rownum > 0){
			    	if(climategridginineighbor.grid[rownum-1][colnum] >= 0.0){

			    	Integer narrayindex = (rownum - 1) * climategridginineighbor.ncols + colnum;
			    	nearestneighbors.add(narrayindex);
			    	}
				}
			
				if(colnum > 0 ){
			    	if(climategridginineighbor.grid[rownum][colnum-1] >= 0.0){

			    	Integer narrayindex = (rownum) * climategridginineighbor.ncols + colnum - 1;
			    	nearestneighbors.add(narrayindex);
			    	}
				}	
				
			}
			
			meant = meant / cellt.length;
			
			for(int i=0; i < cellt.length; i++){
				Integer arrayindex = Integer.parseInt(cellt[i]);
				nearestneighbors.remove(arrayindex);
			}
			
			Double meanneighbot = 0.0;
			
			Integer ct=0;
			
			it = nearestneighbors.iterator();
			
			while(it.hasNext()){
				Integer narrayindex = it.next();
				
				ct++;
				
				Integer rownum = narrayindex / climategridginineighbor.ncols;
				
				Integer colnum = narrayindex - rownum * climategridginineighbor.ncols;
				
				meanneighbot += climategridginineighbor.grid[rownum][colnum];
				
			}
			
			Double difft = meant - meanneighbot/ct ;
			
			
			Integer diff = (int)Math.floor((difft-diffs)*1000000000);
			
			if(!diffs.equals(difft))
		        return diff;
		    else
		        return 0;
		}
	}



	
	
/*	static void findlocalneighbors(Integer i,Double radius){
		
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
		
		//return neighbors;
		
	}
	
	static void fastconvexhull(){
		
		
		
	}
	
	
	static Double findlocalneighborsandgini(int innerorouter, Double radius, int muornot) throws IOException{
		Double ncount = 0.0;
		Double nglobal = 0.0;
		Double mucount=0.0;
		Double muglobal=0.0;
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"4day10hours.cas");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		if(innerorouter==0){ // Refers to global

			String caseline;
			
			while((caseline =br.readLine())!=null){
				
				String caseattr[]=caseline.split("\\s+");
				
				if(Integer.parseInt(caseattr[1])==1)
					nglobal++;
				muglobal++;
			}
			
			br.close();
			
			if(muornot==1)
				return muglobal;
			else
				return nglobal;
		}
		else{
			String caseline;
			
			Double centerX = geo[innerorouter-1][0];
			Double centerY = geo[innerorouter-1][1];
			
			while((caseline =br.readLine())!=null){
				
				String caseattr[]=caseline.split("\\s+");
				
				Double X = geo[Integer.parseInt(caseattr[0])-1][0];
				Double Y = geo[Integer.parseInt(caseattr[0])-1][1];
				
				Double edistance = Math.abs(Math.sqrt(Math.pow(X - centerX, 2) + Math.pow(Y - centerY, 2)));
				
				if(edistance <= radius){
				
					if(Integer.parseInt(caseattr[1])==1){
					
						ncount++;
						
					}
					
					mucount++;
					
				}
				
			}
			
			if(muornot == 1)
				return mucount;
			else
				return ncount;
		}
	}

	*/
	




