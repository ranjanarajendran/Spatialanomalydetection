/* 
 * Author Ranjana Rajendran
 * */

package ibm.irl.internship.traffic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class SatScanNormalParser {

	//private String[][] COUNTIES = null;
	private static java.io.PrintWriter logStream = null;
	private static String CASE_FILE_SATSCAN = null;
	private static String CTL_FILE_SATSCAN = null;
	//private String POPULATION_FILE = "SatScan/population_sveriges_lan.pop";
	private static String COORDINATES_FILE = null;
	private static String RESULTS_FILE_SATSCAN = null;
	private static String PARAMETER_FILE = null;
	private static String ALGO_MOD = "";
    private static String SCAN_AREAS = "";

	private static String SATSCAN_PATH;
	
	public static void main(String args[]) throws FileNotFoundException {
		
	 	 try {
             logStream = new java.io.PrintWriter(new BufferedWriter(new java.io.FileWriter("Execute.log", true)));
        } catch(Exception e) {
            e.printStackTrace();
        }
		
		
		SATSCAN_PATH = args[0];
		CASE_FILE_SATSCAN = args[1];
		//CTL_FILE_SATSCAN = args[]
		COORDINATES_FILE = args[3];
		RESULTS_FILE_SATSCAN = args[4];
		PARAMETER_FILE = args[5];
		ALGO_MOD = args[6];
        SCAN_AREAS = args[7];
		
		//if(ALGO_MOD.equals("1"))
			CTL_FILE_SATSCAN=args[2];
		
		System.out.println(SATSCAN_PATH);
		
		SaTScanParameters prms;
		
		if(ALGO_MOD.equals("1"))
			prms = SaTScanParameters.getDefault("bernoulliModel");
		else
			prms = SaTScanParameters.getDefault("normalModel");

			
        prms.setParameter("CaseFile", CASE_FILE_SATSCAN);
		if(ALGO_MOD.equals("1"))
        	prms.setParameter("ControlFile", CTL_FILE_SATSCAN);
        prms.setParameter("CoordinatesFile", COORDINATES_FILE);
        prms.setParameter("ResultsFile", RESULTS_FILE_SATSCAN);
        prms.setParameter("CoordinatesType", "0");
        prms.setParameter("ModelType", ALGO_MOD);
        prms.setParameter("ScanAreas",SCAN_AREAS);
        //prms.setParameter("PrecisionCaseTimes", "3");
        //prms.setParameter("TimeAggregationUnits", "3");
            
        String saTScanParameterFile = PARAMETER_FILE;
        PrintWriter writer = new PrintWriter(saTScanParameterFile);
        writer.print(prms);
        writer.close();
                      
        String[] winCmds = {args[0]+"/SaTScanBatch.exe", saTScanParameterFile};
        String[] linCmds = {args[0]+"/satscan9.1.1.linux", saTScanParameterFile};
        String[] cmdarray;
        String system = System.getProperty("os.name");
        if (system.equals("Windows XP") || system.equals("Windows Vista")) {
             cmdarray = winCmds;
        }
        else {
             cmdarray = linCmds;
        }
        System.out.println(system+","+cmdarray);
        executeExternal(cmdarray);
        waitForFile(RESULTS_FILE_SATSCAN);
            
    }

    /**
     * This method removes spaces in a string. as simple as that...
     * @param s
     * @return t
     * 
     * @author kenneth hebing
     */
    private static String removeSpaces(String s) {
    	  StringTokenizer st = new StringTokenizer(s," ",false);
    	  String t="";
    	  while (st.hasMoreElements()) t += st.nextElement();
    	  return t;
    	}
    
    /**
     * This method is checking for a file in a while loop until it excises.
     * @param filename
     * 
     * @author kenneth hebing
     */
    private static void waitForFile(String filename) {
        File f = new File(filename);
        while (!f.exists()) {}
    }

    
    /**
     * This method executes the external SaTScanBatch 
     * @param cmdarray
     * @return true or false (meaning detected or not detected)
     * @throws InterruptedException 
     * @throws IOException 
     * 
     * @author kenneth hebing
     * @throws IOException 
     */
    private static void executeExternal(String[] cmdarray) {
   	 
   	 String s = null;

		try {

			Process p = Runtime.getRuntime().exec(cmdarray);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			
			// read any errors from the attempted command
			logStream.println("Here is the standard error of the command (if any):\n");
			while ((s = stdInput.readLine()) != null) {
				//System.out.println(s);
			}
			
			// read any errors from the attempted command
			logStream.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				logStream.println(s);
                System.out.println(s);
			}

		} catch (IOException e) {
			logStream.println("exception happened - here's what I know: ");
			e.printStackTrace();
			logStream.println(e);
		}
    }
}
