/* This file is part of CASE.
 *
 * CASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CASE.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * This project is partly funded by The Swedish emergency management agency
 * @author kenneth Hebing, Anette Hulth, Baki Cakici and Maria Grunewald
 */

/* This file has been modified for SatScan9.1.1 by Ranjana Rajendran 
 * Please refer attached file SatScan9.1.1/circlesnores.prm for the allowed parameters
 *  for each attribute and what it stands for.
 * */

package ibm.irl.internship.climate;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

public class SaTScanParameters {
    // Valid for SaTScan v8.0. Assumes uniqueness of parameter keys
    // (i. e. the []-enclosed parameter categories, although required
    // in the SaTScan parameter file, are considered redundant).
    // Usage requires knowledge of valid SaTScan parameters and values,
    // no checks are performed. However unrecognized parameters will be
    // ignored when producing SatScan .prm file content.

    private HashMap<String,String> categoryForParameter = new HashMap<String,String>();
    private static LinkedHashMap<String,String> parameter  = new LinkedHashMap<String,String>(100,.75f,false);
    
    String DetectionWindowInDays = "180";

    private SaTScanParameters(String typeOfAlgo) {
        // [Input]
        categoryForParameter.put("CaseFile","Input");
        categoryForParameter.put("ControlFile","Input");
        categoryForParameter.put("PopulationFile","Input");
        categoryForParameter.put("CoordinatesFile","Input");
        categoryForParameter.put("UseGridFile","Input");
        categoryForParameter.put("GridFile","Input");
        categoryForParameter.put("PrecisionCaseTimes","Input");
        categoryForParameter.put("CoordinatesType","Input");
        categoryForParameter.put("StartDate","Input");
        categoryForParameter.put("EndDate","Input");
        parameter.put("CaseFile","cases.dat");
        parameter.put("ControlFile","controls.dat");
        parameter.put("PopulationFile","population.dat");
        parameter.put("CoordinatesFile","coordinates.dat");
        parameter.put("UseGridFile","n");
        parameter.put("GridFile","");
        parameter.put("PrecisionCaseTimes","0");
        parameter.put("CoordinatesType","0");
        parameter.put("StartDate","2000/1/1");
        parameter.put("EndDate","2000/12/31");

        // [Analysis]
        categoryForParameter.put("AnalysisType","Analysis");
        categoryForParameter.put("ModelType","Analysis");
        categoryForParameter.put("ScanAreas","Analysis");
        categoryForParameter.put("TimeAggregationUnits","Analysis");
        categoryForParameter.put("TimeAggregationLength","Analysis");
        //categoryForParameter.put("MonteCarloReps","Analysis"); This parameter was moved to 'Inference' in 9.1.1
        parameter.put("AnalysisType","1");
        parameter.put("ModelType","5");
        parameter.put("ScanAreas","3");
        parameter.put("TimeAggregationUnits","1");
        parameter.put("TimeAggregationLength","1");
        //parameter.put("MonteCarloReps","999");

        // [Output]
        categoryForParameter.put("ResultsFile","Output");
        categoryForParameter.put("SaveSimLLRsASCII","Output");
        categoryForParameter.put("SaveSimLLRsDBase","Output");
        categoryForParameter.put("IncludeRelativeRisksCensusAreasASCII","Output");
        categoryForParameter.put("IncludeRelativeRisksCensusAreasDBase","Output");
        categoryForParameter.put("CensusAreasReportedClustersASCII","Output");
        categoryForParameter.put("CensusAreasReportedClustersDBase","Output");
        categoryForParameter.put("MostLikelyClusterEachCentroidASCII","Output");
        categoryForParameter.put("MostLikelyClusterEachCentroidDBase","Output");
        categoryForParameter.put("MostLikelyClusterCaseInfoEachCentroidASCII","Output");
        categoryForParameter.put("MostLikelyClusterCaseInfoEachCentroidDBase","Output");
        parameter.put("ResultsFile","results.dat");
        parameter.put("SaveSimLLRsASCII","y");
        parameter.put("SaveSimLLRsDBase","n");
        parameter.put("IncludeRelativeRisksCensusAreasASCII","y");
        parameter.put("IncludeRelativeRisksCensusAreasDBase","n");
        parameter.put("CensusAreasReportedClustersASCII","y");
        parameter.put("CensusAreasReportedClustersDBase","n");
        parameter.put("MostLikelyClusterEachCentroidASCII","y");
        parameter.put("MostLikelyClusterEachCentroidDBase","n");
        parameter.put("MostLikelyClusterCaseInfoEachCentroidASCII","y");
        parameter.put("MostLikelyClusterCaseInfoEachCentroidDBase","n");

        // [Multiple Data Sets]
        categoryForParameter.put("MultipleDataSetsPurposeType","Multiple Data Sets");
        parameter.put("MultipleDataSetsPurposeType","0");

        // [Data Checking]
        categoryForParameter.put("StudyPeriodCheckType","Data Checking");
        categoryForParameter.put("GeographicalCoordinatesCheckType","Data Checking");
        parameter.put("StudyPeriodCheckType","0");
        parameter.put("GeographicalCoordinatesCheckType","0");

        // [Non-Euclideian Neighbors in version 8]
        // [Spatial Neighbors in version 9.1.1]
        categoryForParameter.put("NeighborsFilename","Spatial Neighbors");
        categoryForParameter.put("UseNeighborsFile","Spatial Neighbors");
        categoryForParameter.put("MetaLocationsFilename","Spatial Neighbors");
        categoryForParameter.put("UseMetaLocationsFile","Spatial Neighbors");
        categoryForParameter.put("MultipleCoordinatesType","Spatial Neighbors");
        parameter.put("NeighborsFilename","");
        parameter.put("UseNeighborsFile","n");
        parameter.put("MetaLocationsFilename","");
        parameter.put("UseMetaLocationsFile","n");
        parameter.put("MultipleCoordinatesType","0");

        // [Spatial Window]
        categoryForParameter.put("MaxSpatialSizeInPopulationAtRisk","Spatial Window");
        categoryForParameter.put("MaxSpatialSizeInMaxCirclePopulationFile","Spatial Window");
        categoryForParameter.put("MaxSpatialSizeInDistanceFromCenter","Spatial Window");
        categoryForParameter.put("UseMaxCirclePopulationFileOption","Spatial Window");
        categoryForParameter.put("UseDistanceFromCenterOption","Spatial Window");
        categoryForParameter.put("IncludePurelyTemporal","Spatial Window");
        categoryForParameter.put("MaxCirclePopulationFile","Spatial Window");
        categoryForParameter.put("SpatialWindowShapeType","Spatial Window");
        categoryForParameter.put("NonCompactnessPenalty","Spatial Window");
        categoryForParameter.put("IsotonicScan","Spatial Window");
        parameter.put("MaxSpatialSizeInPopulationAtRisk","50");
        parameter.put("MaxSpatialSizeInMaxCirclePopulationFile","50");
        parameter.put("MaxSpatialSizeInDistanceFromCenter","1");
        parameter.put("UseMaxCirclePopulationFileOption","n");
        parameter.put("UseDistanceFromCenterOption","n");
        parameter.put("IncludePurelyTemporal","n");
        parameter.put("MaxCirclePopulationFile","");
        parameter.put("SpatialWindowShapeType","0");
        parameter.put("NonCompactnessPenalty","1");
        parameter.put("IsotonicScan","0");


        if (typeOfAlgo.equalsIgnoreCase("spaceTimeModel")) {
        	// [Temporal Window]
        	categoryForParameter.put("MaxTemporalSize","Temporal Window");
        	categoryForParameter.put("IncludePurelySpatial","Temporal Window");
        	categoryForParameter.put("MaxTemporalSizeInterpretation","Temporal Window");
        	categoryForParameter.put("IncludeClusters","Temporal Window");
        	categoryForParameter.put("IntervalStartRange","Temporal Window");
        	categoryForParameter.put("IntervalEndRange","Temporal Window");
        	parameter.put("MaxTemporalSize","50");
        	parameter.put("IncludePurelySpatial","n");
        	parameter.put("MaxTemporalSizeInterpretation","0");
        	parameter.put("IncludeClusters","0");
        	parameter.put("IntervalStartRange","2000/1/1,2000/12/31");
        	parameter.put("IntervalEndRange","2000/1/1,2000/12/31");
        }

        // [Space and Time Adjustments]
        categoryForParameter.put("TimeTrendAdjustmentType","Space and Time Adjustments");
        categoryForParameter.put("TimeTrendPercentage","Space and Time Adjustments");
        categoryForParameter.put("AdjustmentsByKnownRelativeRisksFilename","Space and Time Adjustments");
        categoryForParameter.put("UseAdjustmentsByRRFile","Space and Time Adjustments");
        categoryForParameter.put("SpatialAdjustmentType","Space and Time Adjustments");
        categoryForParameter.put("TimeTrendType","Space and Time Adjustments");
        parameter.put("TimeTrendAdjustmentType","0");
        parameter.put("TimeTrendPercentage","0");
        parameter.put("AdjustmentsByKnownRelativeRisksFilename","");
        parameter.put("UseAdjustmentsByRRFile","n");
        parameter.put("SpatialAdjustmentType","0");
        parameter.put("TimeTrendType","0");


        // [Inference]
        categoryForParameter.put("ProspectiveStartDate","Inference");
        categoryForParameter.put("PValueReportType","Inference");
        categoryForParameter.put("ReportGumbel","Inference");
        categoryForParameter.put("EarlyTerminationThreshold","Inference");
        categoryForParameter.put("AdjustForEarlierAnalyses","Inference");
        categoryForParameter.put("CriticalValue","Inference");
        categoryForParameter.put("IterativeScan","Inference");
        categoryForParameter.put("IterativeScanMaxIterations","Inference");
        categoryForParameter.put("IterativeScanMaxPValue","Inference");
        categoryForParameter.put("MonteCarloReps","Inference");
        parameter.put("ProspectiveStartDate","2000/12/31");
        parameter.put("PValueReportType","0");
        parameter.put("ReportGumbel","n");
        parameter.put("EarlyTerminationThreshold","50");
        parameter.put("AdjustForEarlierAnalyses","n");
        parameter.put("CriticalValue","n");
        parameter.put("IterativeScan","n");
        parameter.put("IterativeScanMaxIterations","10");
        parameter.put("IterativeScanMaxPValue","0.05");
        parameter.put("MonteCarloReps","999");


        // [Clusters Reported]
        categoryForParameter.put("CriteriaForReportingSecondaryClusters","Clusters Reported");
        categoryForParameter.put("UseReportOnlySmallerClusters","Clusters Reported");
        categoryForParameter.put("MaxSpatialSizeInPopulationAtRisk_Reported","Clusters Reported");
        categoryForParameter.put("MaxSizeInMaxCirclePopulationFile_Reported","Clusters Reported");
        categoryForParameter.put("MaxSpatialSizeInDistanceFromCenter_Reported","Clusters Reported");
        categoryForParameter.put("UseMaxCirclePopulationFileOption_Reported","Clusters Reported");
        categoryForParameter.put("UseDistanceFromCenterOption_Reported","Clusters Reported");
        parameter.put("CriteriaForReportingSecondaryClusters","5");
        parameter.put("UseReportOnlySmallerClusters","n");
        parameter.put("MaxSpatialSizeInPopulationAtRisk_Reported","50");
        parameter.put("MaxSizeInMaxCirclePopulationFile_Reported","50");
        parameter.put("MaxSpatialSizeInDistanceFromCenter_Reported","1");
        parameter.put("UseMaxCirclePopulationFileOption_Reported","n");
        parameter.put("UseDistanceFromCenterOption_Reported","n");
        
        //[Additional Output]
        categoryForParameter.put("CriticalValue","Additional Output");
	    categoryForParameter.put("ReportClusterRank","Additional Output");
        categoryForParameter.put("PrintAsciiColumnHeaders","Additional Output");
        parameter.put("CriticalValue","y");
        parameter.put("ReportClusterRank","y");
        parameter.put("PrintAsciiColumnHeaders","n");

        // [Elliptic Scan]
        categoryForParameter.put("EllipseShapes","Elliptic Scan");
        categoryForParameter.put("EllipseAngles","Elliptic Scan");
        parameter.put("EllipseShapes","1.5,2,3,4,5");
        parameter.put("EllipseAngles","4,6,9,12,15");

        // [Isotonic Scan] This has been moved to Spatial Window in version 9.1.1
        //categoryForParameter.put("IsotonicScan", "Isotonic Scan");
        //parameter.put("IsotonicScan","0");

        // [Power Simulations]
        categoryForParameter.put("PValues2PrespecifiedLLRs","Power Simulations");
        categoryForParameter.put("LLR1","Power Simulations");
        categoryForParameter.put("LLR2","Power Simulations");
        categoryForParameter.put("SimulatedDataMethodType","Power Simulations");
        categoryForParameter.put("SimulatedDataInputFilename","Power Simulations");
        categoryForParameter.put("PrintSimulatedDataToFile","Power Simulations");
        categoryForParameter.put("SimulatedDataOutputFilename","Power Simulations");
        parameter.put("PValues2PrespecifiedLLRs","n");
        parameter.put("LLR1","0");
        parameter.put("LLR2","0");
        parameter.put("SimulatedDataMethodType","0");
        parameter.put("SimulatedDataInputFilename","");
        parameter.put("PrintSimulatedDataToFile","n");
        parameter.put("SimulatedDataOutputFilename","");

        // [Run Options]
        categoryForParameter.put("ExecutionType","Run Options");
        categoryForParameter.put("NumberParallelProcesses","Run Options");
        categoryForParameter.put("LogRunToHistoryFile","Run Options");
        categoryForParameter.put("SuppressWarnings","Run Options");
        parameter.put("ExecutionType","0");
        parameter.put("NumberParallelProcesses","0");
        parameter.put("LogRunToHistoryFile","y");
        parameter.put("SuppressWarnings","n");

        // [System]
        categoryForParameter.put("Version", "System");
        parameter.put("Version","9.1.1");

    }


    public static SaTScanParameters getDefault(String typeOfAlgo) {
        return new SaTScanParameters(typeOfAlgo);
    }
    
    private static String getDateString(java.util.Calendar c)
    {
        return
            Integer.toString(c.get(java.util.Calendar.YEAR)) + "/" +
            Integer.toString(c.get(java.util.Calendar.MONTH) + 1) + "/" +
            Integer.toString(c.get(java.util.Calendar.DAY_OF_MONTH));
    }
    
    public void setParameter(String key, String value) {
        if (key.equals("DetectionWindowInDays")) {
            java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
            parameter.put("EndDate", getDateString(cal));
            cal.add(java.util.Calendar.DAY_OF_YEAR, - Integer.parseInt(value));
            parameter.put("StartDate", getDateString(cal));
        }
        else
        {
            parameter.put(key,value);
        }
    }

    public static String getParameter(String key) {
        return parameter.get(key);
    }
    
    // Produces a SaTScan parameter file formatted string
    public String print(){
        String newline = System.getProperty("line.separator");
        LinkedHashMap<String,String> sections = new LinkedHashMap<String,String>(10,.75f,false);
        
        sections.put("Input","[Input]"+newline);
        sections.put("Analysis",newline+"[Analysis]"+newline);
        sections.put("Output",newline+"[Output]"+newline);
        sections.put("Multiple Data Sets",newline+"[Multiple Data Sets]"+newline);
        sections.put("Data Checking",newline+"[Data Checking]"+newline);
        sections.put("Spatial Neighbors",newline+"[Spatial Neighbors]"+newline);
        sections.put("Spatial Window",newline+"[Spatial Window]"+newline);
        sections.put("Temporal Window",newline+"[Temporal Window]"+newline);
        sections.put("Space and Time Adjustments",newline+"[Space and Time Adjustments]"+newline);
        sections.put("Inference",newline+"[Inference]"+newline);
        sections.put("Clusters Reported",newline+"[Clusters Reported]"+newline);
        sections.put("Additional Output",newline+"[Additional Output]"+newline);
        sections.put("Elliptic Scan",newline+"[Elliptic Scan]"+newline);
        //sections.put("Isotonic Scan",newline+"[Isotonic Scan]"+newline);
        sections.put("Power Simulations",newline+"[Power Simulations]"+newline);
        sections.put("Run Options",newline+"[Run Options]"+newline);
        sections.put("System",newline+"[System]"+newline);

        for(Map.Entry<String,String> c : parameter.entrySet()) {
           String temp = categoryForParameter.get(c.getKey());
           sections.put(temp,sections.get(temp) + c.getKey() + "=" + c.getValue() + newline);
        }
        String temp = ";Generated by ibm.irl.internship.climate.SaTScanParameters"+newline+newline;
        for(String s : sections.values()) {
            temp += s;
        }
        return temp;
    }

    @Override
    public String toString() {
        return print();
    }
}                              
