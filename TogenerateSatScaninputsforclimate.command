#!/bin/sh

# TogenerateSatScaninputsforclimate.command
#  
#
#  Created by Ranjana Rajendran on 11/27/12.
#  Copyright (c) 2012 __IBM IRL__. All rights reserved.

path=`dirname $0`
echo $path



echo `$path/detect-java.command`

echo "JAVA_HOME: "$JAVA_HOME    



if [ ! -d $path/bin ]; then
mkdir $path/bin
fi

CLASSPATH=$path/bin
echo classpath: $CLASSPATH

rm -rf $CLASSPATH/*

sourcepath1=$path/src/ibm/irl/internship/climate
echo sourcepath: $sourcepath1

javac -Xlint -d $CLASSPATH $sourcepath1/*.java

GINITHRESHOLDDIRECTORY=$path/data/ClimateSatScansoftwareinputdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
fi

mkdir $GINITHRESHOLDDIRECTORY

echo $GINITHRESHOLDDIRECTORY

java -classpath $CLASSPATH ibm.irl.internship.climate.climtosatscaninputs $path/data $GINITHRESHOLDDIRECTORY  #reads tempNA.txt and creates the .cas file for SatScan based on normal distribution and also the ctl and case files for bernoulli based. However, for this case SatScan based on normal distribution makes sense.Also .geo is created. 

java -classpath $CLASSPATH ibm.irl.internship.climate.climtosatscaninputsseattle $path/data $GINITHRESHOLDDIRECTORY

echo "done"






