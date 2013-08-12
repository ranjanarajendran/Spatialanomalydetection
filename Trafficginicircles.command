#!/bin/sh

#  Trafficginicircles.command
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
                  
sourcepath1=$path/src/ibm/irl/internship/traffic
echo sourcepath: $sourcepath1
                  
javac -Xlint -d $CLASSPATH $sourcepath1/*.java
                  
GINITHRESHOLDDIRECTORY=$path/data/TrafficGinicirclesdata
                  
if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
    rm -rf $GINITHRESHOLDDIRECTORY
fi
                  
mkdir $GINITHRESHOLDDIRECTORY
                  
java -classpath $CLASSPATH ibm.irl.internship.traffic.Ginicircletraffic $path/data $GINITHRESHOLDDIRECTORY 
                  
cp -rf $path/data/4day10hourcompactfilteredstretchremoved.txt $GINITHRESHOLDDIRECTORY
                  
                  
GINITHRESHOLDPLOTS=$path/plots/TrafficGinicirclesplots
                  
if [ -d "$GINITHRESHOLDPLOTS" ]; then
    rm -rf $GINITHRESHOLDPLOTS
fi
                  
mkdir $GINITHRESHOLDPLOTS
                  
rfile=$path/Rscripts/Trafficginicircle.R
                  
datafile=$GINITHRESHOLDDIRECTORY/4day10hourcompactfilteredstretchremoved.txt
                  
nameofplot="Trafficginicirclescase1.pdf"
                  
echo $nameofplot
                  
locatepdf=$GINITHRESHOLDPLOTS/$nameofplot
                  
circles=$GINITHRESHOLDDIRECTORY/"4day10hourginicircles.txt"               
 
Rscript $rfile $locatepdf $datafile $circles 1

nameofplot="Trafficginicirclescase2.pdf"
                  
echo $nameofplot
                  
locatepdf=$GINITHRESHOLDPLOTS/$nameofplot     
                  
Rscript $rfile $locatepdf $datafile $circles 2 
                  
                  nameofplot="Trafficginicirclescase3.pdf"
                  
                  echo $nameofplot
                  
                  locatepdf=$GINITHRESHOLDPLOTS/$nameofplot     
                  
                  Rscript $rfile $locatepdf $datafile $circles 3
                  
                  nameofplot="Trafficginicirclescase4.pdf"
                  
                  echo $nameofplot
                  
                  locatepdf=$GINITHRESHOLDPLOTS/$nameofplot     
                  
                  Rscript $rfile $locatepdf $datafile $circles 4 
                  
