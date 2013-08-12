#!/bin/sh

#  Trafficginibpair.command
#  
#
#  Created by Ranjana Rajendran on 11/27/12.
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

sourcepath1=$path/src/ibm/irl/internship/traffic
echo sourcepath: $sourcepath1

javac -Xlint -d $CLASSPATH $sourcepath1/*.java

GINITHRESHOLDDIRECTORY=$path/data/trafficginibestpair

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
fi

mkdir $GINITHRESHOLDDIRECTORY

cp -f $path/data/4day10hourcompactfilteredstretchremoved.txt $GINITHRESHOLDDIRECTORY


java -classpath $CLASSPATH ibm.irl.internship.traffic.Ginineighborgridbeijing $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY


#cp -f $path/data/groundtruthseattle.txt $GINITHRESHOLDDIRECTORY

#java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 1

#java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 2

#java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 3

#java -classpath $CLASSPATH ibm.irl.internship.climate.converttoprecinputthreshold $GINITHRESHOLDDIRECTORY $GINITHRESHOLDDIRECTORY 4

GINITHRESHOLDPLOTS=$path/plots/trafficginibestpairplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

rfile=$path/Rscripts/trafficginiareascombined.R

ablines=$GINITHRESHOLDDIRECTORY/trafficbeijingablines.txt

gridaverages=$GINITHRESHOLDDIRECTORY/4day10hourbeijingtrafficgridaverages.txt

ginirectangles=$GINITHRESHOLDDIRECTORY/4day10hourgridginirectanglescombined.txt

plotname=trafficginiareacombinedplot.pdf

plotlocation=$GINITHRESHOLDPLOTS/$plotname

dataset=$GINITHRESHOLDDIRECTORY/4day10hourcompactfilteredstretchremoved.txt


Rscript $rfile $plotlocation $dataset $ablines $gridaverages $ginirectangles




