
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

GINITHRESHOLDDIRECTORY=$path/data/Climateanalysisdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
fi

mkdir $GINITHRESHOLDDIRECTORY

java -classpath $CLASSPATH ibm.irl.internship.climate.climatetoxy $path/data $GINITHRESHOLDDIRECTORY 1  # world data

java -classpath $CLASSPATH ibm.irl.internship.climate.climatetoxy $path/data $GINITHRESHOLDDIRECTORY 0  # NA data

GINITHRESHOLDPLOTS=$path/plots/Climatedataanalysisplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
fi

mkdir $GINITHRESHOLDPLOTS

rfile=$path/Rscripts/tempscatter.R

datafile=$GINITHRESHOLDDIRECTORY/tempWorld.txt

echo "Datafile" $datafile

nameofplot="Worldscatteroftemp.pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

Rscript $rfile $locatepdf $datafile

rfile=$path/Rscripts/histogramsclimate.R

nameofplot="Worldhistogramoftemp.pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

Rscript $rfile $locatepdf $datafile

datafile=$GINITHRESHOLDDIRECTORY/tempNA.txt

echo "Datafile" $datafile

nameofplot="NAhistogramoftemp.pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

Rscript $rfile $locatepdf $datafile

rfile=$path/Rscripts/tempscatter.R

nameofplot="NAscatteroftemp.pdf"

echo $nameofplot

locatepdf=$GINITHRESHOLDPLOTS/$nameofplot

Rscript $rfile $locatepdf $datafile

rfile=$path/Rscripts/tempscatter.R




