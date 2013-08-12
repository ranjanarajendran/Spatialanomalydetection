#!/bin/sh

#  Script.sh
#  
#
#  Created by Ranjana Rajendran on 11/26/12.
#  Copyright (c) 2012 __IBM IRL__. All rights reserved.

  

path=`dirname $0`
echo $path

if [ ! -d $path/bin ]; then
mkdir $path/bin
fi

CLASSPATH=$path/bin
echo classpath: $CLASSPATH

rm -rf $CLASSPATH/*


GINITHRESHOLDDIRECTORY=$path/data/ClimateginithresholdalgorithmNAdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY
fi

GINITHRESHOLDDIRECTORY=$path/data/Climateanalysisdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY
fi



GINITHRESHOLDDIRECTORY=$path/data/Climateginibestpairseattlealgorithmdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY
fi


GINITHRESHOLDDIRECTORY=$path/data/ClimateginibestpairalgorithmNAdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi
                  
GINITHRESHOLDDIRECTORY=$path/data/Climatesatscanradiusmultdata
                  
if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY
                  
fi

GINITHRESHOLDDIRECTORY=$path/data/ClimateSatScansoftwareinputdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/SatScansoftwareoutputdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/SatScansoftwareoutputdataBernNA

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/SatScansoftwareoutputdataNormNA

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/SatScansoftwareoutputdataNormSeattle

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/trafficginibestpairplots

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/TrafficSatScanNeighborNormaloutput

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/TrafficSatScanradiusmultdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/TrafficGinicirclesdata

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi
                  
                  GINITHRESHOLDDIRECTORY=$path/data/TrafficSatScansoftwareinputdata
                  
                  if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
                  rm -rf $GINITHRESHOLDDIRECTORY
                  rm -f $GINITHRESHOLDDIRECTORY
                  
                  fi                  

GINITHRESHOLDDIRECTORY=$path/data/TrafficSatScansoftwareoutputdataBern

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi

GINITHRESHOLDDIRECTORY=$path/data/TrafficSatScansoftwareoutputdataNorm

if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
rm -rf $GINITHRESHOLDDIRECTORY
rm -f $GINITHRESHOLDDIRECTORY

fi
                  
                  GINITHRESHOLDDIRECTORY=$path/data/trafficginibestpair
                  
                  if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
                  rm -rf $GINITHRESHOLDDIRECTORY
                  rm -f $GINITHRESHOLDDIRECTORY
                  
                  fi                  

                  GINITHRESHOLDDIRECTORY=$path/data/ClimateGinicirclesdata
                  
                  if [ -d "$GINITHRESHOLDDIRECTORY" ]; then                  
                  rm -rf $GINITHRESHOLDDIRECTORY
                  rm -f $GINITHRESHOLDDIRECTORY
                  
                  fi


GINITHRESHOLDPLOTS=$path/plots/Climatedataanalysisplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS
fi

GINITHRESHOLDPLOTS=$path/plots/Trafficsatscannormalwithradiusmultplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS
fi

GINITHRESHOLDPLOTS=$path/plots/ClimateginithresholdalgorithmplotsNA

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS
fi


GINITHRESHOLDPLOTS=$path/plots/ClimateginibestpairseattlealgorithmNAplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS
fi

GINITHRESHOLDPLOTS=$path/plots/ClimateginibestpairalgorithmNAplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi

GINITHRESHOLDPLOTS=$path/plots/Climatesatscannormalwithradiusmultplots
                  
if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS
                  
fi

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsBernNA

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsNormNA

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsNormSeattle

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi

GINITHRESHOLDPLOTS=$path/plots/satscannormalwithradiusmultplots

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsTrafficBern

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi

GINITHRESHOLDPLOTS=$path/plots/SatScansoftwareplotsTrafficNorm

if [ -d "$GINITHRESHOLDPLOTS" ]; then
rm -rf $GINITHRESHOLDPLOTS
rm -f $GINITHRESHOLDPLOTS

fi
                  
                  
                  GINITHRESHOLDPLOTS=$path/plots/trafficginibestpairplots
                  
                  if [ -d "$GINITHRESHOLDPLOTS" ]; then
                  rm -rf $GINITHRESHOLDPLOTS
                  rm -f $GINITHRESHOLDPLOTS
                  
                  fi
                  
                  
                  GINITHRESHOLDPLOTS=$path/plots/TrafficGinicirclesplots
                  
                  if [ -d "$GINITHRESHOLDPLOTS" ]; then
                  rm -rf $GINITHRESHOLDPLOTS
                  rm -f $GINITHRESHOLDPLOTS
                  
                  fi
                  
                  GINITHRESHOLDPLOTS=$path/plots/ClimateGinicirclesplots
                  
                  if [ -d "$GINITHRESHOLDPLOTS" ]; then
                  rm -rf $GINITHRESHOLDPLOTS
                  rm -f $GINITHRESHOLDPLOTS
                  
                  fi
