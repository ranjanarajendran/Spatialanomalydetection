#!/bin/sh

#  ClimateGinicircles.command
#  
#
#  Created by Ranjana Rajendran on 11/27/12.
#  Copyright (c) 2012 __IBM IRL__. All rights reserved.

echo `detect-java.command`

echo $JAVA_HOME    

path=`dirname $0`
echo $path

if [ ! -d $path/bin ]; then
mkdir $path/bin
fi

CLASSPATH=$path/bin
echo classpath: $CLASSPATH

rm -rf $CLASSPATH/*
                  
sourcepath1=$path/src/ibm/irl/internship/climate
echo sourcepath: $sourcepath1
                  
javac -Xlint -d $CLASSPATH $sourcepath1/*.java
                  
GINITHRESHOLDDIRECTORY=$path/data/ClimateGinicirclesdata
                  
if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
    rm -rf $GINITHRESHOLDDIRECTORY
fi
                  
mkdir $GINITHRESHOLDDIRECTORY
                  
java -classpath $CLASSPATH ibm.irl.internship.climate.Climateginicircles $path/data $GINITHRESHOLDDIRECTORY  #the last argument denotes the multiplier - Here separate files for multipliers from 1.1 to 3.0 are created with steps of 0.1
                  
cp -f $path/data/tempNA.txt $GINITHRESHOLDDIRECTORY
                  
cp -f $path/data/groundtruthseattle.txt $GINITHRESHOLDDIRECTORY
                  
                  
for c in 1 2 3 4
do
                  
    java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallginicircle $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY $c # The case for which prec and recall calculated                  
                  
done
                  
GINITHRESHOLDPLOTS=$path/plots/ClimateGinicirclesplots
                  
if [ -d "$GINITHRESHOLDPLOTS" ]; then
    rm -rf $GINITHRESHOLDPLOTS
fi
                  
mkdir $GINITHRESHOLDPLOTS
                  
rfile=$path/Rscripts/ClimateGinicircles.R
                  
latlonggeo=$path/data/NAtemplatlonggeo.geo
                  
latlongvalues=$path/data/NAtemplatlongcasnorm.cas
                  
ablineXs=$GINITHRESHOLDDIRECTORY/ablineXs.txt
ablineYs=$GINITHRESHOLDDIRECTORY/ablineYs.txt
                  
                  #giniarearectangles=$GINITHRESHOLDDIRECTORY/climateginiarearectanglesthreshold
                  
groundtruth=$GINITHRESHOLDDIRECTORY/groundtruthseattlerectangles
                  
#anomalies=$GINITHRESHOLDDIRECTORY/"seattleginicirclecase"$case"rectangles400"
                  
koftopk=100

for casenum in 1 2 3 4

do

    anomalies=$GINITHRESHOLDDIRECTORY/"seattleginicirclecase"$casenum"rectangles"$koftopk
  
    nameofplot="ClimateGinitop400case"$casenum".pdf"
                  
    echo $nameofplot
                  
    locatepdf=$GINITHRESHOLDPLOTS/$nameofplot
                  
    circles=$GINITHRESHOLDDIRECTORY/"NAginicirclescase"$casenum".txt"  
                  
    relevantcircles=$GINITHRESHOLDDIRECTORY/"relevantcircles"$casenum".txt"              
                  
    java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallginicircle $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY $casenum $koftopk
                  
                  
    Rscript $rfile $locatepdf $latlonggeo $latlongvalues $ablineXs $ablineYs $groundtruth $anomalies $circles $relevantcircles $koftopk
                  
                  
done
                  
for c in 1 2 3 4
do

java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallginicircle $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY $c # The case for which prec and recall calculated                  

done
