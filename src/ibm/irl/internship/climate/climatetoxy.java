package ibm.irl.internship.climate;

import java.io.*;

public class climatetoxy {
	
	public static void main(String args[]) throws IOException{
		
		String infilepath = args[0];
		String outfilepath= args[1];
		Integer worldornot = Integer.parseInt(args[2]);
		
		FileInputStream fstream = new FileInputStream(infilepath+"/"+"cai_temp.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//String filename = "cai_temptoxylatlongNA.txt";
		//String filename = "worldflatdegrees.txt";
		
		String filename = "tempNA.txt";
		
		if(worldornot.intValue() == 1)
			filename = "tempWorld.txt";
		
		File newfile = new File(outfilepath+"/"+filename);
		boolean exist = newfile.createNewFile();
			
		if(!exist){
				
			System.out.println("File "+newfile.getName()+" already exist");
			System.exit(0);
		}
			
		FileWriter fstream1 = new FileWriter(newfile);
		BufferedWriter out = new BufferedWriter(fstream1);
		
		String currentline="";
		
		while((currentline=br.readLine())!=null){
			
			currentline= currentline.trim();
			
			String current[] = currentline.split("\\s+");
			
			if(current[0]=="-99.750"){
				
				System.out.println(" ");
			}
			
			System.out.println(currentline+", "+current[0]);
			
			Double longitude = Double.parseDouble(current[0]);
			//Double longitude1 = (longitude + 180) * Math.PI /180;
			Double longitude1 = (longitude + 180);
			Double latitude = Double.parseDouble(current[1]);
			Double latitude1 = (latitude + 180);
			//Double latitude1 = (latitude + 180 )* Math.PI / 180 ;
			
			Double earthR = 6378.00;;
			
			Double objX = Math.abs(earthR * Math.cos(latitude1)*Math.cos(longitude1));
			Double objY = Math.abs(earthR * Math.cos(latitude1)*Math.sin(longitude1));
			
			Double annualprecip = Double.parseDouble(current[14]);
			
			if(worldornot.intValue() == 0){
				if(longitude <= -67.25 && longitude >= -127 && latitude <= 48.75 && latitude >= 25.75)
					out.write(longitude1.toString()+"	"+latitude1.toString()+"	"+annualprecip.toString()+"	"+longitude.toString()+"	"+latitude.toString()+"\n");
			}
			else
				out.write(longitude1.toString()+"	"+latitude1.toString()+"	"+annualprecip.toString()+"	"+longitude.toString()+"	"+latitude.toString()+"\n");

		}
		
		out.close();
		
		br.close();
		
	}

}
