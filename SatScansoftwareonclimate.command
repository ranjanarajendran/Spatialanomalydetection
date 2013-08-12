#!/bin/sh

#  GiniareathresholdNA.command
#  
#
#  Created by Ranjana Rajendran on 11/27/12.
#  Copyright (c) 2012 __IBM IRL__. All rights reserved.

path=`dirname $0`
echo $path

echo `$path/detect-java.command`

echo `$path/TogenerateSatScaninputsforclimate.command`

#echo $JAVA_HOME  

CLASSPATH=$path/bin

pathtosatscan=$path/SatScan9.1.1

GINITHRESHOLDINDIRECTORY=$path/data/ClimateSatScansoftwareinputdata

#-----------------------------------------Normal for NA

GINITHRESHOLDOUTDIRECTORY=$path/data/SatScansoftwareoutputdataNormNA

if [ -d "$GINITHRESHOLDOUTDIRECTORY" ]; then
rm -rf $GINITHRESHOLDOUTDIRECTORY
fi

mkdir $GINITHRESHOLDOUTDIRECTORY

casefile=$GINITHRESHOLDINDIRECTORY/NAtemplatlongcasnorm.cas
ctlfile=$GINITHRESHOLDINDIRECTORY/NAtemplatlongberctl.ctl # is not taken here
coordfile=$GINITHRESHOLDINDIRECTORY/NAtemplatlonggeo.geo
resultfile=$GINITHRESHOLDOUTDIRECTORY/output.txt
paramfile=$GINITHRESHOLDOUTDIRECTORY/output.prm
algomod=5

chmod +x $pathtosatscan

java -classpath $CLASSPATH ibm.irl.internship.climate.SatScanNormalParser $pathtosatscan $casefile $ctlfile $coordfile $resultfile $paramfile $algomod

cp -f $path/data/tempNA.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/groundtruthseattle.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/ablineXs.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/ablineYs.txt $GINITHRESHOLDOUTDIRECTORY


java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscanNA $GINITHRESHOLDOUTDIRECTORY $GINITHRESHOLDOUTDIRECTORY 0 # The first k circles for which the precision and recall are calculated. 0 indicates this is done till recall is 1.0

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsNormNA

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

koftopk=100

rfile=$path/Rscripts/tempaNAsatscananom.R

latlonggeo=$path/data/NAtemplatlonggeo.geo

latlongvalues=$path/data/NAtemplatlongcasnorm.cas

ablineXs=$GINITHRESHOLDOUTDIRECTORY/ablineXs.txt
ablineYs=$GINITHRESHOLDOUTDIRECTORY/ablineYs.txt

groundtruth=$GINITHRESHOLDOUTDIRECTORY/groundtruthseattlerectangles

anomalies=$GINITHRESHOLDOUTDIRECTORY/seattlesatscanhighlowrectangles$koftopk


nameofplot="SatScansoftware"$koftopk".pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

circles=$GINITHRESHOLDOUTDIRECTORY/"output.col.txt"  

relevantcircles=$GINITHRESHOLDOUTDIRECTORY/"relevantcircles.txt"

java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscanNA $GINITHRESHOLDOUTDIRECTORY $GINITHRESHOLDOUTDIRECTORY $koftopk


Rscript $rfile $locatepdf $latlonggeo $latlongvalues $ablineXs $ablineYs $groundtruth $anomalies $circles $relevantcircles 5

#------------------------------------Bernoulli for NA

GINITHRESHOLDOUTDIRECTORY=$path/data/SatScansoftwareoutputdataBernNA

if [ -d "$GINITHRESHOLDOUTDIRECTORY" ]; then
rm -rf $GINITHRESHOLDOUTDIRECTORY
fi

mkdir $GINITHRESHOLDOUTDIRECTORY

casefile=$GINITHRESHOLDINDIRECTORY/NAtemplatlongberncas.cas
#coordfile=$GINITHRESHOLDINDIRECTORY/NAtemplatlonggeo.geo
resultfile=$GINITHRESHOLDOUTDIRECTORY/output.txt
paramfile=$GINITHRESHOLDOUTDIRECTORY/output.prm
algomod=1

chmod +x $pathtosatscan

java -classpath $CLASSPATH ibm.irl.internship.climate.SatScanNormalParser $pathtosatscan $casefile $ctlfile $coordfile $resultfile $paramfile $algomod

cp -f $path/data/tempNA.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/groundtruthseattle.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/ablineXs.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/ablineYs.txt $GINITHRESHOLDOUTDIRECTORY


java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscanNA $GINITHRESHOLDOUTDIRECTORY $GINITHRESHOLDOUTDIRECTORY 0 # The first k circles for which the precision and recall are calculated. 0 indicates this is done till recall is 1.0

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsBernNA

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

koftopk=100

rfile=$path/Rscripts/tempaNAsatscananom.R

latlonggeo=$path/data/NAtemplatlonggeo.geo

latlongvalues=$path/data/NAtemplatlongcasnorm.cas

ablineXs=$GINITHRESHOLDOUTDIRECTORY/ablineXs.txt
ablineYs=$GINITHRESHOLDOUTDIRECTORY/ablineYs.txt

groundtruth=$GINITHRESHOLDOUTDIRECTORY/groundtruthseattlerectangles

anomalies=$GINITHRESHOLDOUTDIRECTORY/seattlesatscanhighlowrectangles$koftopk


nameofplot="SatScansoftware"$koftopk".pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

circles=$GINITHRESHOLDOUTDIRECTORY/"output.col.txt"  

relevantcircles=$GINITHRESHOLDOUTDIRECTORY/"relevantcircles.txt"

java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscanNA $GINITHRESHOLDOUTDIRECTORY $GINITHRESHOLDOUTDIRECTORY $koftopk


Rscript $rfile $locatepdf $latlonggeo $latlongvalues $ablineXs $ablineYs $groundtruth $anomalies $circles $relevantcircles 1

#------------------------------------- Normal for Seattle

GINITHRESHOLDOUTDIRECTORY=$path/data/SatScansoftwareoutputdataNormSeattle

if [ -d "$GINITHRESHOLDOUTDIRECTORY" ]; then
rm -rf $GINITHRESHOLDOUTDIRECTORY
fi

mkdir $GINITHRESHOLDOUTDIRECTORY

casefile=$GINITHRESHOLDINDIRECTORY/NAtemplatlongcasnormseattle.cas
coordfile=$GINITHRESHOLDINDIRECTORY/NAtemplatlonggeoseattle.geo
resultfile=$GINITHRESHOLDOUTDIRECTORY/output.txt
paramfile=$GINITHRESHOLDOUTDIRECTORY/output.prm
algomod=5

chmod +x $pathtosatscan

java -classpath $CLASSPATH ibm.irl.internship.climate.SatScanNormalParser $pathtosatscan $casefile $ctlfile $coordfile $resultfile $paramfile $algomod


cp -f $path/data/tempNA.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/groundtruthseattle.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/ablineXs.txt $GINITHRESHOLDOUTDIRECTORY

cp -f $path/data/ablineYs.txt $GINITHRESHOLDOUTDIRECTORY


java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscanNA $GINITHRESHOLDOUTDIRECTORY $GINITHRESHOLDOUTDIRECTORY 0 # The first k circles for which the precision and recall are calculated. 0 indicates this is done till recall is 1.0


GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsNormSeattle

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

koftopk=100

rfile=$path/Rscripts/tempaNAsatscananom.R

latlonggeo=$path/data/NAtemplatlonggeo.geo

latlongvalues=$path/data/NAtemplatlongcasnorm.cas

ablineXs=$GINITHRESHOLDOUTDIRECTORY/ablineXs.txt
ablineYs=$GINITHRESHOLDOUTDIRECTORY/ablineYs.txt

groundtruth=$GINITHRESHOLDOUTDIRECTORY/groundtruthseattlerectangles

anomalies=$GINITHRESHOLDOUTDIRECTORY/seattlesatscanhighlowrectangles$koftopk


nameofplot="SatScansoftware"$koftopk".pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

circles=$GINITHRESHOLDOUTDIRECTORY/"output.col.txt"  

relevantcircles=$GINITHRESHOLDOUTDIRECTORY/"relevantcircles.txt"


java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscanNA $GINITHRESHOLDOUTDIRECTORY $GINITHRESHOLDOUTDIRECTORY $koftopk


Rscript $rfile $locatepdf $latlonggeo $latlongvalues $ablineXs $ablineYs $groundtruth $anomalies $circles $relevantcircles 5




