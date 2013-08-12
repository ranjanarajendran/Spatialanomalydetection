#!/bin/sh

#  GiniareathresholdNA.command
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

GINITHRESHOLDDIRECTORY=$path/data/Climatesatscanradiusmultdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
fi

mkdir $GINITHRESHOLDDIRECTORY

java -classpath $CLASSPATH ibm.irl.internship.climate.climatesatscannormal $path/data $GINITHRESHOLDDIRECTORY 1.1  #the last argument denotes the multiplier - Here separate files for multipliers from 1.1 to 3.0 are created with steps of 0.1

cp -f $path/data/tempNA.txt $GINITHRESHOLDDIRECTORY

cp -f $path/data/groundtruthseattle.txt $GINITHRESHOLDDIRECTORY


for c in 2.0 2.1 2.2 2.3 2.4 2.5 2.6 2.7 2.8 2.9
do

    java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscannormalrm $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY $c # The multiplier for which the precision and recall are calculated
    
#    shift

done

GINITHRESHOLDPLOTS=$path/plots/Climatesatscannormalwithradiusmultplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

rfile=$path/Rscripts/NAtempsatscanradiusmult.R

latlonggeo=$path/data/NAtemplatlonggeo.geo

latlongvalues=$path/data/NAtemplatlongcasnorm.cas

ablineXs=$GINITHRESHOLDDIRECTORY/ablineXs.txt
ablineYs=$GINITHRESHOLDDIRECTORY/ablineYs.txt

#giniarearectangles=$GINITHRESHOLDDIRECTORY/climateginiarearectanglesthreshold

groundtruth=$GINITHRESHOLDDIRECTORY/groundtruthseattlerectangles

anomalies=$GINITHRESHOLDDIRECTORY/seattlesatscannormhighlowrectangles400

koftopk=400

#for((radiusmult=1.1; radiusmult < 3.0; radiusmult += 0.1)); do

radiusmult=2.4

#koftopk=400

nameofplot="satscannormalwihradiusmulttop"$koftopk"mult"$radiusmult".pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot
                  
circles=$GINITHRESHOLDDIRECTORY/"NAsatscanneighborresult"$radiusmult".txt"  

relevantcircles=$GINITHRESHOLDDIRECTORY/"relevantcircles.txt"              

shift

java -classpath $CLASSPATH ibm.irl.internship.climate.precisionrecallsatscannormalrm $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY $radiusmult 


Rscript $rfile $locatepdf $latlonggeo $latlongvalues $ablineXs $ablineYs $groundtruth $anomalies $circles $relevantcircles $koftopk


#done


