#!/bin/sh

#  SatScansoftwareontraffic.command

#  
#
#  Created by Ranjana Rajendran on 11/27/12.
#  Copyright (c) 2012 __IBM IRL__. All rights reserved.

path=`dirname $0`
echo $path

echo `$path/detect-java.command`

echo `$path/TogenerateSatScaninputsfortraffic.command`

#echo $JAVA_HOME  

CLASSPATH=$path/bin

pathtosatscan=$path/SatScan9.1.1

GINITHRESHOLDINDIRECTORY=$path/data/TrafficSatScansoftwareinputdata

#-----------------------------------------Normal for Traffic

GINITHRESHOLDOUTDIRECTORY=$path/data/TrafficSatScansoftwareoutputdataNorm

if [ -d "$GINITHRESHOLDOUTDIRECTORY" ]; then
rm -rf $GINITHRESHOLDOUTDIRECTORY
fi

mkdir $GINITHRESHOLDOUTDIRECTORY

casefile=$GINITHRESHOLDINDIRECTORY/Trafficlatlongcasnorm.cas
ctlfile=$GINITHRESHOLDINDIRECTORY/Trafficlatlongberctl.ctl # is not taken here
coordfile=$GINITHRESHOLDINDIRECTORY/Trafficlatlonggeo.geo
resultfile=$GINITHRESHOLDOUTDIRECTORY/output.txt
paramfile=$GINITHRESHOLDOUTDIRECTORY/output.prm
algomod=5

scanareas=2

java -classpath $CLASSPATH ibm.irl.internship.traffic.SatScanNormalParser $pathtosatscan $casefile $ctlfile $coordfile $resultfile $paramfile $algomod $scanareas

cp -f $path/data/4day10hourcompactfilteredstretchremoved.txt $GINITHRESHOLDOUTDIRECTORY


GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsTrafficNorm

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

koftopk=100

rfile=$path/Rscripts/Trafficsatscananom.R

latlonggeo=$GINITHRESHOLDINDIRECTORY/Trafficlatlonggeo.geo

latlongvalues=$GINITHRESHOLDINDIRECTORY/Trafficlatlongcasnorm.cas

#ablineXs=$GINITHRESHOLDOUTDIRECTORY/ablineXs.txt
#ablineYs=$GINITHRESHOLDOUTDIRECTORY/ablineYs.txt


nameofplot="SatScansoftwareNormtraffic.pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

circles=$GINITHRESHOLDOUTDIRECTORY/output.col.txt 


Rscript $rfile $locatepdf $latlonggeo $latlongvalues $circles 5

#------------------------------------Bernoulli for Traffic

GINITHRESHOLDOUTDIRECTORY=$path/data/TrafficSatScansoftwareoutputdataBern

if [ -d "$GINITHRESHOLDOUTDIRECTORY" ]; then
rm -rf $GINITHRESHOLDOUTDIRECTORY
fi

mkdir $GINITHRESHOLDOUTDIRECTORY

casefile=$GINITHRESHOLDINDIRECTORY/Trafficlatlongberncas.cas
coordfile=$GINITHRESHOLDINDIRECTORY/Trafficlatlonggeo.geo
ctlfile=$GINITHRESHOLDINDIRECTORY/Trafficlatlongberctl.ctl 
resultfile=$GINITHRESHOLDOUTDIRECTORY/output.txt
paramfile=$GINITHRESHOLDOUTDIRECTORY/output.prm
algomod=1
scanareas=1

java -classpath $CLASSPATH ibm.irl.internship.traffic.SatScanNormalParser $pathtosatscan $casefile $ctlfile $coordfile $resultfile $paramfile $algomod $scanareas

cp -f $path/data/4day10hourcompactfilteredstretchremoved.txt $GINITHRESHOLDOUTDIRECTORY

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsTrafficBern

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

rfile=$path/Rscripts/Trafficsatscananom.R

latlonggeo=$GINITHRESHOLDINDIRECTORY/Trafficlatlonggeo.geo

latlongvalues=$GINITHRESHOLDINDIRECTORY/Trafficlatlongcasnorm.cas

nameofplot="SatScansoftwareBernTraffic.pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

circles=$GINITHRESHOLDOUTDIRECTORY/output.col.txt  


Rscript $rfile $locatepdf $latlonggeo $latlongvalues $circles 1



#-----------------------------------#-----------------------------------#


