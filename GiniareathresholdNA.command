#!/bin/sh

#  GiniareathresholdNA.command
#  
#
#  Created by Ranjana Rajendran on 11/26/12.
#  Copyright (c) 2012 __IBM IRL__. All rights reserved.


path=`dirname $0`
echo $path

echo `$path/detect-java.command`

echo $JAVA_HOME    


if [ ! -d $path/bin ]; then
    mkdir $path/bin
fi

CLASSPATH=$path/bin
echo classpath: $CLASSPATH

rm -rf $CLASSPATH/*

sourcepath1=$path/src/ibm/irl/internship/climate
echo sourcepath: $sourcepath1

javac -Xlint -d $CLASSPATH $sourcepath1/*.java

GINITHRESHOLDDIRECTORY=$path/data/ClimateginithresholdalgorithmNAdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
    rm -rf $GINITHRESHOLDDIRECTORY
fi

mkdir $GINITHRESHOLDDIRECTORY

java -classpath $CLASSPATH ibm.irl.internship.climate.giniareathresholdcombined $path/data $GINITHRESHOLDDIRECTORY

cp -f $path/data/tempNA.txt $GINITHRESHOLDDIRECTORY

cp -f $path/data/groundtruthseattle.txt $GINITHRESHOLDDIRECTORY

java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 1

java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 2

java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 3

java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 4

GINITHRESHOLDPLOTS=$path/plots/ClimateginithresholdalgorithmplotsNA

if [ -d "$GINITHRESHOLDPLOTS" ]; then
    rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

rfile=$path/Rscripts/tempaNAgridthresh.R

latlonggeo=$path/data/NAtemplatlonggeo.geo

latlongvalues=$path/data/NAtemplatlongcasnorm.cas

ablineXs=$GINITHRESHOLDDIRECTORY/ablineXs.txt
ablineYs=$GINITHRESHOLDDIRECTORY/ablineYs.txt

giniarearectangles=$GINITHRESHOLDDIRECTORY/climateginiarearectanglesthreshold

groundtruth=$GINITHRESHOLDDIRECTORY/groundtruthseattlelatlongrectangles

anomalies=$GINITHRESHOLDDIRECTORY/giniareacellsseattlerectangles1

koftopk=50

for i in {1..4} ; do

    nameofplot="giniareathresholdtop"$koftopk"case"$i".pdf"

    echo $nameofplot

    locatepdf=$GINITHRESHOLDPLOTS/$nameofplot


    java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY $i $koftopk


    Rscript $rfile $locatepdf $latlonggeo $latlongvalues $ablineXs $ablineYs $giniarearectangles $groundtruth $anomalies

    shift

done


