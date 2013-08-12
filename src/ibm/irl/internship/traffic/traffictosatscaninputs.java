package ibm.irl.internship.traffic;
/*The generates satscan inputs for Normal, weighted (weight is given as 1 as we have one 
 * average for each grid cell as input and no information about how many points the average is taken),
 *  Lat/Long, for single data set. This produces a case file and geo file as specified by SatScan user guide. Refer NormFlake.geo and .cas 
 *  in the SatScan sample inputs.  */

import java.io.*;

public class traffictosatscaninputs {
	
	public static void main(String args[]) throws IOException{
		
		String infilepath=args[0];
		String outfilepath=args[1];
	
		//String casefilename = "NAtemplatlongcasnormseattle.cas";
		String casefilename = "Trafficlatlongcasnorm.cas";
		//String geofilename = "NAtemplatlonggeoseattle.geo";
		String geofilename = "Trafficlatlonggeo.geo";
		
		//String casefilename2= "NAtemplatlongberncasseattle.cas";
		String casefilename2= "Trafficlatlongberncas.cas";
		
		//String ctlfilename = "NAtemplatlongberctlseattle.ctl";
		String ctlfilename = "Trafficlatlongberctl.ctl";

		
		File casefile = new File(outfilepath+"/"+casefilename);
		boolean exist = casefile.createNewFile();

		while(!exist){
			
			boolean success= casefile.delete();
			if(success)
				exist = casefile.createNewFile();
		}

		FileWriter fstream0 = new FileWriter(casefile);
	
		BufferedWriter casewriter = null;
		
		File geofile = new File(outfilepath+"/"+geofilename);
		boolean exist1 = geofile.createNewFile();

		while(!exist1){
			
			boolean success= geofile.delete();
			if(success)
				exist1 = geofile.createNewFile();
		}
		
		FileWriter fstream1 = new FileWriter(geofile);
		
		BufferedWriter geowriter = null;
		
		File casefile2 = new File(outfilepath+"/"+casefilename2);
		boolean exist2 = casefile2.createNewFile();

		while(!exist2){
			
			boolean success= casefile2.delete();
			if(success)
				exist2 = casefile2.createNewFile();
		}

		FileWriter fstream2 = new FileWriter(casefile2);
	
		BufferedWriter casewriter2 = null;
		
		File ctlfile = new File(outfilepath+"/"+ctlfilename);
		boolean exist3 = ctlfile.createNewFile();

		while(!exist3){
			
			boolean success= ctlfile.delete();
			if(success)
				exist3 = ctlfile.createNewFile();
		}

		FileWriter fstream3 = new FileWriter(ctlfile);
	
		BufferedWriter ctlwriter = null;
		
		System.out.println("Output files touched");
		
		String infilename = "4day10hourcompactfilteredstretchremoved.txt";
		
		Integer locid=0 ; // Locid is an integer unlike NormFlake.cas and geo which use alphabets.
		
		String currentline;
		
		Integer casecount=0;
		
		Integer one = 1, casevalue,ctlvalue;
		
		BufferedReader br = null;
		
		try{FileInputStream fstream = new FileInputStream(infilepath+"/"+infilename);
		DataInputStream in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));
		casewriter = new BufferedWriter(fstream0);
		ctlwriter=new BufferedWriter(fstream3);
		casewriter2 = new BufferedWriter(fstream2);
		geowriter = new BufferedWriter(fstream1);
		
		while((currentline = br.readLine())!=null){
			
			System.out.println(currentline);
			
			String current[] = currentline.split(",");
			
			Double longitude = Double.parseDouble(current[3]) * 180 / Math.PI;
			
			Double latitude = Double.parseDouble(current[4]) * 180 / Math.PI;
			
			//if(longitude >= -125.00 && longitude <= -100.0 && latitude >= 40.0 && latitude <= 50.0){
			
			String caseentry = locid.toString() + "	" + one.toString() + "	" + current[2].toString()+"\n";
			
			System.out.println(current[2].toString());
			
			String geoentry = locid.toString() + "	" +latitude.toString() + "	" + longitude.toString() + "\n";
			
			if(Double.parseDouble(current[7]) <= 0.003){
				casevalue=1;
				ctlvalue = 0;
				casecount ++;
			}
			else{
				casevalue =0;
				ctlvalue = 1;
			}
				
			String caseentry2 = locid.toString() + "	" + casevalue.toString() +"\n";
				
			String ctlentry = locid.toString() + "	" + ctlvalue.toString() +"\n";
				
			casewriter2.write(caseentry2);
				
			ctlwriter.write(ctlentry);			
			
			
			geowriter.write(geoentry);
			
			casewriter.write(caseentry);
			
			locid++;
			
			//}
			
		}
		
		}finally{
		
		casewriter.close();
		geowriter.close();
		casewriter2.close();
		ctlwriter.close();
		br.close();
		}
		
		System.out.println("Number of cases: "+ casecount);
		
	}

}
