package ibm.irl.internship.traffic;


///Users/Shared/Datasetmicrosoft/Snapshotsfilteredstretchremoved

//import ibm.irl.internship.climate.GiniComparator41;
//import ibm.irl.internship.climate.climategridginineighbor;

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

public class Ginineighborgridbeijing {
	
	static Double[][] geo=new Double[11000][3];
	
	static Double[][] grid;
	
	static Integer[][] ngrid;
	
	static Integer[][] ginied;
	
	static Integer[][] whichginiarea;
	
	static Integer idgini;
	
	static int n,sum,px;
	
	static String infilepath;
	
	static ArrayList<Integer> members;
	
	static CopyOnWriteArrayList<String> ginineighbors = new CopyOnWriteArrayList();
	
	static CopyOnWriteArrayList<Double> ginindices = new CopyOnWriteArrayList();
	
	static CopyOnWriteArrayList<Double> bestginiofneighbor;
	
	public static Double bestginiofneighbordouble[];
	
	public static Double ginindicesarray[];
	
	public static void main(String args[]) throws IOException{
		
		infilepath = args[0];
		String outfilepath = args[1];
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"4day10hourcompactfilteredstretchremoved.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		File newfile = new File(outfilepath+"/4day10hourbeijingtrafficgridaverages.txt");
		boolean exist = newfile.createNewFile();
		
		while(!exist){
			
			boolean success= newfile.delete();
			if(success)
				exist = newfile.createNewFile();
		}
		
		FileWriter fstream1 = new FileWriter(newfile);
		BufferedWriter out = new BufferedWriter(fstream1);
		
		File newfile1 = new File(outfilepath+"/trafficbeijingablines.txt");
		boolean exist1 = newfile1.createNewFile();
		
		while(!exist1){
			
			boolean success= newfile1.delete();
			if(success)
				exist1 = newfile1.createNewFile();
		}
		
		FileWriter fstream2 = new FileWriter(newfile1);
		BufferedWriter out1 = new BufferedWriter(fstream2);
		
		
		String currentline;
		Integer i=0,j=0,p=0;
		
		Double left=4185.0,right=-455.0,top=0.0,bottom=5000.0;
		
		while((currentline=br.readLine())!=null){
			
			String current[]=currentline.split(",");
			
			//geo[i][0]=Double.parseDouble(current[5]);
			geo[i][0]=Double.parseDouble(current[3]) * 180 / Math.PI;
			//geo[i][1]=Double.parseDouble(current[6]);
			geo[i][1]=Double.parseDouble(current[4]) * 180 / Math.PI;
			geo[i][2]=Double.parseDouble(current[7]);
			
			if(geo[i][0] > right.doubleValue())
				right = geo[i][0];
			if(geo[i][0] < left.doubleValue())
				left = geo[i][0];
			if(geo[i][1] > top.doubleValue())
				top = geo[i][1];
			if(geo[i][1] < bottom.doubleValue())
				bottom = geo[i][1];
			
			i++;
		}	
		
		System.out.println("Left:"+left+"	Right:"+right+"	Top:"+top+"	Bottom:"+bottom);
		
		if(left > right || bottom > top){
			
			System.out.println("Error in left, right, bottom and top values");
			return;
		}
		else
			System.out.println("Left "+left+" Right "+right+" Top "+top+" Bottom "+bottom);
		
		Integer gran =100;
		Double rightincrement = (right -left)/gran;
		Double upincrement = (top - bottom)/gran;
		
		n = i;
		
		System.out.println("Total number of points: "+n);
		
		//Integer neighbor[];
		
		Double radius,giniindex=0.0,variance=0.0;
		
		Integer count =0,ncount=0,maxmembers=0;
		
		out1.write(left +"	"+ bottom+"\n");
		
		grid = new Double[gran][gran];
		ngrid = new Integer[gran][gran];
		ginied = new Integer[gran][gran];
		whichginiarea = new Integer[gran][gran];
				
		for(j=0;j<gran;j++){
			
			for (p=0; p <gran;p++){
								
				members=new ArrayList<Integer>();	
				
				members.clear();
				
				findlocalneighborsingrid(left+p*rightincrement,left+(p+1)*rightincrement,bottom+j*upincrement,bottom+(j+1)*upincrement);
				
				//if(j==70 && p==47)
					//System.out.println("Why not gini?");
				
				Double ablinex= left+(p+1)*rightincrement;
				Double abliney = bottom+(j+1)*upincrement;
				
				out1.write(ablinex +"	"+ abliney+"\n");
									
				Integer neighbornum = members.size();
			
				Integer k =0,index;
			
				Double sum=0.0,px=0.0;
			
				for(index=0;index < members.size();index++){
				
					k= members.get(index);
				
					sum += geo[k][2].doubleValue();
								
				}
				
				if(neighbornum !=0 && sum == 0)
					System.out.println("Neighbornum: "+neighbornum+" Sum:"+sum+" Non-zero num of points and zero average velocity");
			
			
				System.out.println("Number of neighbors is: "+index);
				
				Double u;
				
				if(neighbornum!=0){
			
					u = sum / (neighbornum) ;
					count++;
				}
				else
					u = 0.0;
				
				if(neighbornum > 1)
					ncount++;
				if(neighbornum > maxmembers)
					maxmembers = neighbornum;
				
				Double X = left + (p*rightincrement) + (rightincrement /2);
				
				Double Y = bottom + (j*upincrement) + (upincrement/2);
				
				System.out.println(u);
				
				if(u!=0)
					out.write(X+"	"+Y+"	"+u*1000+"\n");
				
				if(neighbornum.intValue()==0)
					grid[j][p]=-5000.0;
				else
					grid[j][p]=u *1000;
				
				ngrid[j][p]=neighbornum;
				
			}
			
		}
		
		System.out.println("No of grid cells with non zero members:"+count+" greater than 1:"+ncount+" Max-members"+maxmembers);
		
		out.close();
		
		out1.close();
		
		ginineighbors.clear();
		ginindices.clear();
		
		for(j=0;j<gran;j++){
			
			for(p=0;p<gran;p++){		
				Integer arraynum = j*gran+p;
				ginineighbors.add(arraynum.toString());
				ginied[j][p]=1;
				ginindices.add(0.0);
				whichginiarea[j][p]=arraynum;
			}			
		}
		

		Integer iteration=0;
		
		//ListIterator<String> it = ginineighbors.listIterator();
	
		Double mingini=2.0,maxgini=0.0;
		
		//ArrayList<String> copyginineighbors = new ArrayList<String>(ginineighbors);
		
		//ListIterator<String> it = ginineighbors.listIterator();
		
		CopyOnWriteArrayList<Integer> bestginineighbor = new CopyOnWriteArrayList();
		
		bestginiofneighbor = new CopyOnWriteArrayList();
		
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
					Integer rownum = (arraynum) / gran;
					
					Integer colnum = arraynum - rownum * gran;
					
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
				
				if(element.equals("5142,5242"))
					System.out.println("Note");
			
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
				
					rownum = (indices[q]) / gran;
				
					colnum = indices[q] - rownum * gran;
					
					//if((rownum == 51 && colnum == 42) || (rownum == 52 && colnum == 42) )
						//System.out.println("Wny no gini area ?");
					
					String arrayindex;
					Integer arraynum;
					
					/*Integer upcell = (rownum + 1) * gran + colnum;
					Integer downcell = (rownum -1) * gran + colnum;
					Integer leftcell = rownum*gran + colnum -1;
					Integer rightcell = rownum * gran + colnum + 1;*/
					
				    if(rownum <99){
				    	String gots = ginineighbors.get(whichginiarea[rownum+1][colnum]);
				    	//if(gots.length() == ginied[rownum+1][colnum])
				    	if(!(element.equals(gots)))
				    		nearestneighbors.add(gots);
				    }
					
				    if(colnum <99 ){
				    	String gots = ginineighbors.get(whichginiarea[rownum][colnum+1]);
				    	//if(gots.length() == ginied[rownum][colnum+1])
				    	if(!(element.equals(gots)))
				    		nearestneighbors.add(gots);
				    }
					
					if(rownum > 0 ){
				    	String gots = ginineighbors.get(whichginiarea[rownum-1][colnum]);
				    	//if(gots.length() == ginied[rownum-1][colnum])
				    	if(!(element.equals(gots)))
				    		nearestneighbors.add(gots);
					}
				
					if(colnum > 0 ){
				    	String gots = ginineighbors.get(whichginiarea[rownum][colnum-1]);
				    	//if(gots.length() == ginied[rownum][colnum-1])
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
						
						Integer rownum1 = index1 / gran;
						
						Integer colnum1 = index1 - rownum1 * gran;
						
						sum += grid[rownum1][colnum1].doubleValue() ;
						
						num += 1;

						evalset.add(grid[rownum1][colnum1].doubleValue());	

					}	
			
					for (q=0;q<cells.length ; q++){
				
						rownum = (Integer.parseInt(cells[q])) / gran;
				
						colnum = Integer.parseInt(cells[q]) - rownum * gran;
				
						num += 1;
				
						sum += grid[rownum][colnum].doubleValue();
					
						evalset.add(grid[rownum][colnum].doubleValue());
				
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
				
					if (u.doubleValue() >= 0.0 && num.intValue() > 1){
					
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
		    
		    for(int q=0;q< cells.length;q++){
		    	
		    	indices[q]=Integer.parseInt(cells[q]);
		    	
		    	rownum = indices[q] / gran;
		    	
		    	colnum = indices[q] - rownum * gran;
		    	
		    	ginied[rownum][colnum]=cells.length;
		    	
		    //	System.out.println("Cell updated for :"+rownum+","+colnum+":"+cells.length);
		    	
		    }
		    
		    System.out.println(mingini);
		    
		    System.out.println(ginineighbors.size()+","+ginindices.size());
		
		}while(mingini < 0.30);
		
		
		File newfile5 = new File(outfilepath+"/4day10hourgridginirectanglescombined.txt");
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
		
		ListIterator<Double> itbestginneighb = bestginiofneighbor.listIterator();
		
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
		
		while(itbestginneighb.hasPrevious()){
			
			itgini.previous();
		}
		
		Integer count1=0,biggest=0;
		
		while(it.hasNext()){
			
			String cells[] = it.next().split(",");
			
			//System.out.println(count1+" group of cells: ");
			
			Double gini = itgini.next();
			
			Double bestginofneighb = itbestginneighb.next();
			
			Double diff=bestginofneighb - gini;
			
			if(gini == 0.15182219930876828)
				System.out.println("Note");
			
			//if((cells.length) > 1){
			
				for (int q=0; q < cells.length;q++){
				
					Integer index = Integer.parseInt(cells[q]);
				
					Integer rownum = index / gran;
				
					Integer colnum = index - rownum * gran;
				
					System.out.println("("+rownum+","+colnum+")");
				
					Double leftx= left+(colnum)*rightincrement;
					Double lefty = bottom+(rownum)*upincrement;
					Double rightx = left+(colnum+1)*rightincrement;
					Double righty = bottom + (rownum+1)*upincrement;
	
				
					//out5.write(leftx+"	"+lefty+"	"+rightx+"	"+righty+"	"+(count1.intValue()+1)+"	"+gini+"	"+grid[rownum][colnum]+"	"+ rownum+"	"+colnum+"	"+ngrid[rownum][colnum] +"	"+cells.length+"\n");
					out5.write(leftx.toString()+"	"+lefty.toString()+"	"+rightx.toString()+"	"+righty.toString()+"	"+gini.toString()+"	"+grid[rownum][colnum].toString()+"	"+ rownum.toString()+"	"+colnum.toString()+"	"+ngrid[rownum][colnum].toString()+"	"+ginied[rownum][colnum].toString()+"	"+count1.toString()+"	"+diff+"\n");

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
		
		System.out.println("Biggest gini cluster has size: "+biggest);
		
		String giniareaarraystring[] = ginineighbors.toArray(new String[ginineighbors.size()]);

		
		bestginiofneighbordouble = bestginiofneighbor.toArray(new Double[bestginiofneighbor.size()]);
				
		ginindicesarray = ginindices.toArray(new Double[ginindices.size()]);
		
		Arrays.sort(giniareaarraystring, new GiniComparatortraffic());// sorts according to increase in gini index when combined with best neighbor
	
		
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
	
}

class GiniComparatortraffic implements Comparator<String> {  // Sorts(decreasing) according to difference in gini when added to best neighbor
	
	public int compare(String s, String t) {
		
		Integer indexs = Ginineighborgridbeijing.ginineighbors.indexOf(s);
		Integer indext = Ginineighborgridbeijing.ginineighbors.indexOf(t);
		
		Double ginidiffs = (Ginineighborgridbeijing.bestginiofneighbordouble[indexs] - Ginineighborgridbeijing.ginindicesarray[indexs]);
		Double ginidifft = (Ginineighborgridbeijing.bestginiofneighbordouble[indext] - Ginineighborgridbeijing.ginindicesarray[indext]);
		
		Double diff = (ginidifft-ginidiffs) * 10000;
		
		if(!ginidiffs.equals(ginidifft)){
			
			//System.out.println(diff.toString());
	        return diff.intValue();}
	    else
	        return 0;
	}
}

