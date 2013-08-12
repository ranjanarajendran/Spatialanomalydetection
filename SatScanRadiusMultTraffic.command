#!/bin/sh

#  SatScanRadiusMultTraffic.command
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

GINITHRESHOLDDIRECTORY=$path/data/TrafficSatScanradiusmultdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
fi

mkdir $GINITHRESHOLDDIRECTORY

java -classpath $CLASSPATH ibm.irl.internship.traffic.trafficsatscannormal $path/data $GINITHRESHOLDDIRECTORY 1.1  #the last argument denotes the multiplier - Here separate files for multipliers from 1.1 to 3.0 are created with steps of 0.1


GINITHRESHOLDPLOTS=$path/plots/Trafficsatscannormalwithradiusmultplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

rfile=$path/Rscripts/Trafficsatscanradiusmult.R

latlonggeo=$path/data/Trafficlatlonggeo.geo

latlongvalues=$path/data/Trafficlatlongcasnorm.cas

koftopk=400

#for((radiusmult=1.1; radiusmult < 3.0; radiusmult += 0.1)); do

radiusmult=2.0

#koftopk=400

nameofplot="Trafficradiusmult"$radiusmult".pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot
                  
circles=$GINITHRESHOLDDIRECTORY/"Trafficsatscanneighborresult"$radiusmult".txt"                


Rscript $rfile $locatepdf $latlonggeo $latlongvalues $circles


#done


