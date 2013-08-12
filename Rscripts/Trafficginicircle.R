#!/bin/sh

#  Trafficsatscanradiusmult.R
#  
#
#  Created by Ranjana Rajendran on 12/6/12.
#  Copyright (c) 2012 __IBM IRL__. All rights reserved.

args<-commandArgs(TRUE)
pdf(args[1])

dataset<- read.table(args[2], header=TRUE, col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10"), sep=",")

attach(dataset)

greenpoints <- dataset[order(V8),]

numpoints<-length(V1)

lats<-vector()
lons<-vector()

for(i in 1:numpoints){
    lats<-c(lats,V5 * 180 / pi)
}

for(i in 1:numpoints){
    lons<-c(lons,V4 * 180 / pi)
}

p <- plot(lons,lats,type='p',xlab="Longitude",ylab="Latitude",pch=".",col="white")

uvaluespeeds <- unique(V8)

numspeeds <- length(uvaluespeeds)

greencolors <- vector()
for (i in 1:numspeeds){
    greencolors[i] <- 255 - i * 255 / numspeeds
}

S <- V8

uvaluespeedssorted <- sort(uvaluespeeds, decreasing = FALSE)

for (i in 1:numspeeds){
    
    for (j in 1:numpoints){
        if(S[j]==uvaluespeedssorted[i]){
            points(lons[j],lats[j],type='p',xlab="Longitude",ylab="Latitude",pch=".",col=rgb(0,greencolors[i],0,maxColorValue=255))
        }
    }
}

library(plotrix)
library(calibrate)
library(graphics)

#if(args[5] == 1){
output.col<-read.table(args[3], header=TRUE,col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10","V11"))
#}else{
#    output.col<-read.table(args[4], header=TRUE,col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10","V11","V12","V13","V14","V15"))
#}

attach(output.col)

mod<-args[4]
if(mod==1){
    output.col<-output.col[order(V6,decreasing=TRUE),]
}else if(mod==2){
    output.col<-output.col[order(V7),]
}else if(mod==3){
    output.col<-output.col[order(V8),]
}else 
    output.col<-output.col[order(V9),]
        
    
   

attach(output.col)
centrex <- V2
centrey <- V3
radii <- V4
numcircles <- length(V1)

circle <- function(x, y, r, color2, border2) {
    xx <- c()
    yy <- c()
    n <- 100
    for(i in 1:n ) {
        ang <- i*6.28/n
        xx[i] <- cos(ang)
        yy[i] <- sin(ang)
    }
    xx <- r * xx + x
    yy <- r * yy + y
    polygon(xx, yy, col = color2, border = border2, lty = 1, lwd = 0.1 )
}

circles <- function(x, y, r, color1 ,border1) {
    circle(x, y, r, color1, border1)
}


count <- 0
for(i in 1:numcircles){
    if(count <10){
    #draw.circle(centrex[i],centrey[i],radii[i],nv=100,border="cyan",col=NA,lty=1,lwd=0.1)
    circles(centrex[numcircles-i-1],centrey[numcircles-i-1], radii[numcircles-i-1], col = NA, border = "brown")
    count <- count + 1
    print(V9[numcircles-i-1])
    }
}

circles(centrex[numcircles],centrey[numcircles], radii[numcircles], col = NA, border = "blue")
circles(centrex[numcircles-1],centrey[numcircles-1], radii[numcircles-1], col = NA, border = "violet")
dev.off()
