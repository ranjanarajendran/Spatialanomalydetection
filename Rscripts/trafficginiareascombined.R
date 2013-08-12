
graphics.off()

args<-commandArgs(TRUE)

pdf(args[1])

dataset<- read.table(args[2], header=TRUE, col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10"), sep=",")

attach(dataset)

numpoints<- length(V1)

lats<-vector()
lons<-vector()

for(i in 1:numpoints){
    lats<-c(lats,V5 * 180 / pi)
}

for(i in 1:numpoints){
    lons<-c(lons,V4 * 180 / pi)
}

p <- plot(lons,lats,type='p',xlab="Longitude",ylab="Latitude",pch=".",col="white")

#p <- ggplot(V6,V7,)


library(plotrix)
library(calibrate)

#grid(32, 32, col = "lightgray", lty = "dotted",lwd = par("lwd"), equilogs = TRUE)

gridaverages<-read.table(args[4], header=TRUE, col.names=c("V1","V2","V3"))

attach(gridaverages)

Xs <- V1
Ys <- V2
Radii <- V3
num <- length(V3)
#ggpoint2(p,aesthetics=list(Xs,Ys,size(p,name="average velocity",to=Radii),shape(p,solid=FALSE),colour="skyblue"),data=`4day10hoursginismallerfilteredstretchremovedgridaverages`)

for (i in 1:num)
  draw.circle(Xs[i],Ys[i],Radii[i]/1000,border="skyblue",lty=1,lwd=0.02)

ablines<-read.table(args[3], header=TRUE, col.names=c("V1","V2"))

attach(ablines)

ablineY <- V1
ablineX <- V2

abline(h=ablineX, col = "gray", lty=1,lwd = 0.01)
abline(v=ablineY, col = "gray", lty=1, lwd = 0.01)

climateginiarearectanglesthreshold<-read.table(args[5], header=TRUE, col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10","V11","V12"))

attach(climateginiarearectanglesthreshold)

rectangles <- climateginiarearectanglesthreshold[order(V11),]

attach(rectangles)

rectlx <- V1
rectly <- V2
rectrx <- V3
rectry <- V4
gridvalues <- V6

diffarray<-V12

num <- length(V11)
#max <- V5[num]

maxdiff <- 0
for(i in 1:num){
    if(V12[i] > maxdiff && V12[i] != 2.0)
        maxdiff <- V12[i]
}

uvalues3 <- unique(V11)

maxu <- length(uvalues3)

library(grDevices)

numcolors <- maxu

colors <- rainbow(numcolors*2, start = 0.1, end = 0.99)

samplecolors <- sample(1:numcolors*2,numcolors,replace=F)

bool = 0;

numcols <-0

diffset<-0

found<-0

for(i in 1:maxu){
  
  if(bool ==0){
    hue <- colors[i]
    bool <- 1
  }
  
  else{
    hue <- colors[ maxu - i ]
    bool <- 0
  }
    
  prev <- 0
  for(j in 1:num){
    if(V11[j]==uvalues3[i] && V6[j] > -5000.0){
        if(diffset==0){
            if(V12[j]==maxdiff){
                found<-1
                rect(rectlx[j],rectly[j],rectrx[j],rectry[j], density=NA, col="orangered", lwd=0.1, border = "transparent")
            }
        } 
        
      if(prev == 0)
        numcols <- numcols + 1
      rect(rectlx[j],rectly[j],rectrx[j],rectry[j], density=0, border= colors[samplecolors[numcols]], lwd=0.5)
      #textxy(rectlx[j] + 0.4, rectly[j] + 0.1, gridvalues[j], cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.001, rectly[j] + 0.005, V11[j], cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.001, rectly[j] + 0.003, V7[j], cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.0035, rectly[j] + 0.003, ",", cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.004, rectly[j] + 0.003, V8[j], cx = 0.1, dcol = "black")
        #textxy(rectlx[j] + 0.001, rectly[j] + 0.0015, V6[j], cx = 0.1, dcol = "black")  

      prev <- 1
    }
      
      #    else if(V11[j]==uvalues3[i] && V10[j] == 1){
      #xinc <- rectrx[j] - rectlx[j]
      #yinc <- rectry[j] - rectly[j]
      #x <- rectlx[j] + xinc/2
      #y <- rectly[j] + yinc/2
      #symbols(x, y , squares = xinc/2 , 
      #      inches = FALSE, add = TRUE,
      #      fg = "black", bg = NA,lwd=0.02)
#      if(gridvalues[j] > -5000.0)
#        textxy(rectlx[j] + 0.4, rectly[j] + 0.1, gridvalues[j], cx = 0.1, dcol = "black")
        #    }
  }
  if(found ==1 )
    diffset<-1
}

uvaluesgini <- unique(V5)

maxnumgini <- length(uvaluesgini)

for(i in 1:maxnumgini){
  
  if(uvaluesgini[i]!=0){
    best <- uvaluesgini[i]
    break
  }
  
}

count <- 0
for (j in 1:num){
  
  if(V5[j]==best){
    rect(rectlx[j],rectly[j],rectrx[j],rectry[j], density=0, border= "black", lty = 3,lwd= 0.5)
    count <- count +1
  }
}

attach(dataset)

greenpoints <- dataset[order(V8),]

uvaluespeeds <- unique(V8)

numspeeds <- length(uvaluespeeds)

numpoints<-length(V1)

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

dev.off()
