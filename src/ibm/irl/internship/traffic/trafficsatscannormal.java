package ibm.irl.internship.traffic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

public class trafficsatscannormal {
	
	static Double geo[][]=new Double[100000][3];
	static String infilepath;
	
	public static Double left,right,top,bottom, mintemp, rightincrement, upincrement, tempshift;
	
	public static Integer numpoints, nrows, ncols;
	
	public static Double radiusmult;
	
	//int findlocalneighbors(int,int,Double);
	
	public static void main(String args[]) throws IOException{
		
		infilepath = args[0];
		String outfilepath = args[1];
		radiusmult = Double.parseDouble(args[2]);
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"4day10hourcompactfilteredstretchremoved.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String currentline;
		Integer i=0,j=0,p=0;	
		
		left=4185.0;right=-5000.0;top=0.0;bottom=5000.0;
		
		mintemp =0.0;
		
		Double sum =0.0;
		
		while((currentline=br.readLine())!=null){
			
			currentline.trim();
			
			String current[]=currentline.split(",");
			
			Double longitude = Double.parseDouble(current[3]);
			Double latitude = Double.parseDouble(current[4]);
			
			geo[i][0]=Double.parseDouble(current[3]) * 180 / Math.PI;
			geo[i][1]=Double.parseDouble(current[4]) * 180 / Math.PI;
			geo[i][2]=Double.parseDouble(current[7]) * 1000;
			
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
		/*
		nrows = 0;
		ncols = 0;
		
		i = 0;
		
		//Double rightlimit = left + i * rightincrement;
		ncols++;
		
		for (j = 1; rightlimit <= right; j++){
			
			ncols ++;
			//rightlimit = left + j * rightincrement;
			
		}
		
		nrows++;
		Double toplimit = bottom + i * upincrement;
		
		for (j = 1; toplimit <= top; j++){
			
			nrows++;
			//toplimit = bottom + j * upincrement;
			
		}*/
		
		System.out.println("Total number of points: "+numpoints);		
		//System.out.println("Nrows:"+nrows+" NCols"+ncols);
	
		String caseline;
		Double radiusmax=0.0;
		Integer nzmax=0,muzmax=0,ngmax=0,mugmax=0;
		
		//Double mug = sum / numpoints;
		
		//Double sigmag=0.0, sigmasqdiff=0.0;
		
		//for(i =0; i < numpoints; i++){
		//	sigmasqdiff = Math.pow(Math.abs(geo[i][2] - mug), 2);
		//}
		
		//sigmag = sigmasqdiff / numpoints;
		
		//Double common = numpoints * Math.log(sigmag) + sigmasqdiff/(2 * sigmag * sigmag) - numpoints / 2 ;
		
		while(radiusmult <=3.0){
			
			//Double lratiomax=0.0;
			
			ArrayList<String> llratiostrings = new ArrayList<String>();
			
			File resultfile = new File(outfilepath+"/"+"Trafficsatscanneighborresult"+radiusmult.floatValue()+".txt");
			boolean exist = resultfile.createNewFile();

			while(!exist){
				
				boolean success= resultfile.delete();
				if(success)
					exist = resultfile.createNewFile();
			}
			
			FileWriter fstream2 = new FileWriter(resultfile);		
			BufferedWriter lratiowriter = new BufferedWriter(fstream2);
		
			for(i=0; i < numpoints; i++){
						
				Double radius = 0.01;
				
				Double numz=0.0, numzmax = 0.0;
				Double llratio=0.0;
				Double maxllratio = -50.0;
				
				Double radiusumax = 0.0;

				Integer maxcaseloc = -1;
				numz = findlocalneighbors(i,radius,0);
				Double numzg = findlocalneighbors(i, radius * radiusmult, 0);
				System.out.println(numz+","+numzg);
				
				boolean calc =false;
				
				while( numz.intValue() < numzg.intValue()/2 && numz.intValue() !=0 && numzg.intValue() < numpoints/20){
					
					//Double commonzg = findlocalneighbors(i, radius * radiusmult, 4);
					
					
					
					Double muz = findlocalneighbors(i, radius,2 );
					
					Double lambdaz = findlocalneighbors(i, radius, 3);
					
					Double sigmaz = findlocalneighbors(i, radius,1 );
					
					if((muz < lambdaz || muz > lambdaz) && !(numzg.equals(0.0)) && !(sigmaz.equals(0.0))){
						
						Double commonzg = findlocalneighbors(i, radius * radiusmult, 4);
				
						llratio = commonzg - numzg * Math.log(Math.sqrt(sigmaz));
					
					}
					else
						llratio = 0.0;
					//System.out.println(llratio);
					calc=true;
					
					if(maxllratio.doubleValue() < llratio.doubleValue()){
						maxcaseloc = i;
						maxllratio = llratio;
						radiusmax = radius;
						numzmax = numz;
						
					}
					radius += 0.01;
					
					numz = findlocalneighbors(i,radius,0);
					
					numzg = findlocalneighbors(i, radius * radiusmult, 0);
					
				}	
				if(calc == true){
				String maxlratiocircle = maxcaseloc.intValue()+"	"+geo[maxcaseloc.intValue()][0]+"	"+geo[maxcaseloc.intValue()][1]+"	"+radiusmult+"	"+radiusmax+"	"+maxllratio+"	"+numzmax.intValue();
				//lratiowriter.write(maxlratiocircle+"\n");
				System.out.println(maxlratiocircle);
				llratiostrings.add(maxlratiocircle);}
			}
			
			//String maxcirclearray[] = llratiostrings.toArray(new String[llratiostrings.size()]);
			
			Collections.sort(llratiostrings, new StringComparator());
			
			ListIterator<String> it = llratiostrings.listIterator();
			
			while(it.hasNext()){
				lratiowriter.write(it.next()+"\n");
			}
			
			radiusmult += 0.1;
			lratiowriter.close();
		}
		
		
	}
	
	static Double findlocalneighbors(int caselocationid,Double radius, int sigmaornot) throws IOException{
		Double ncount = 1.0;
		Double numlambda = 0.0;//Num points outside the circle
		
		Double sum = geo[caselocationid][2];
		Double sumlambda = 0.0; // Sum outside the circle
		
		Double centerX = geo[caselocationid][0];
		Double centerY = geo[caselocationid][1];
		
		ArrayList<Integer> zonepoints = new ArrayList<Integer>();
		
		zonepoints.add(caselocationid);
		
		for(int i=0; i < numpoints; i++){
			
			//System.out.println(i);
			
			if(i != caselocationid){
				
				Double X = geo[i][0];
				Double Y = geo[i][1];
				
				Double edistance = Math.abs(Math.sqrt(Math.pow(X - centerX, 2) + Math.pow(Y - centerY, 2)));
				
				Double largecircle = radius.doubleValue() * radiusmult.doubleValue();

				if(edistance.doubleValue() <= radius.doubleValue()){
					
					ncount++;
					
					sum += geo[i][2];
					
					zonepoints.add(i);
					
				}
				else if(edistance.doubleValue() <= (largecircle)){
					sumlambda += geo[i][2];
					numlambda++;
				}
			}
			
		}
		
		Double muz = sum/ncount;
		
		Double lambdaz= sumlambda / numlambda;
		
		Double sdsum = 0.0;
		
		ListIterator<Integer> it = zonepoints.listIterator();
		
		while(it.hasNext()){
			
			Integer i = it.next();
			
			sdsum += Math.pow(Math.abs(geo[i][2] - muz),2);
			
		}
		
		Double sigmasd = sdsum /ncount;
		
		Double common = ncount * Math.log(Math.sqrt(sigmasd)) + sdsum / 2* sigmasd - ncount/2 ;
			
			
		if(sigmaornot == 1)
			return sigmasd;
		else if(sigmaornot == 2)
			return muz;
		else if(sigmaornot == 3)
			return lambdaz;
		else if(sigmaornot == 4)
			return common;
		else
			return ncount;
		}
	}


class StringComparator implements Comparator<String>{
	
	public int compare(String s, String t) {
		
		//Integer indexs = giniareathresholdcombined.ginineighbors.indexOf(s);
		//Integer indext = giniareathresholdcombined.ginineighbors.indexOf(t);
		
		String a[] = s.split("\\s+");
		String b[] = t.split("\\s+");
		
		Double ginis = Double.parseDouble(a[5]);
		Double ginit = Double.parseDouble(b[5]);
		
		Integer diff = (int)Math.floor((ginit-ginis)*1000000000);
		
		if(!ginis.equals(ginit.doubleValue()))
	        return diff;
	    else
	        return 0;
		}
	 
 }

