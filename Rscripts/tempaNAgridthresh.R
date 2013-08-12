# Normal distribution

args<-commandArgs(TRUE)

#dev.off()
pdf(args[1])

NAtemplatlonggeo<-read.table(args[2], header=TRUE, col.names=c("V1","V2","V3"))
#print(args[2])

attach(NAtemplatlonggeo)

#colnames(NAtemplatlonggeo) <- c( 'V1', 'V2', 'V3')

#print(NAtemplatlonggeo)

plot(V3,V2,type='p',xlab="Longitude",ylab="Latitude",pch=".",col="white")
#attach(cai_temptoxylatlong)
X <- V3
Y <- V2
locs <- V1

NAtemplatlongcasnorm<-read.table(args[3], header=TRUE, col.names=c("V1","V2","V3"))

attach(NAtemplatlongcasnorm)

P <- V3

num <- length(V1)

uvalues <- unique(V3)

uvalueprecipsorted <- sort(uvalues, decreasing = FALSE)

maxu <- length(uvalueprecipsorted)

library(grDevices)

colors <- heat.colors(maxu, alpha=1)

#colors <- rainbow(maxu, alpha =1, start = 0.1, end = 0.99 )


for (j in 1:num){
  
  index <- which(uvalueprecipsorted %in% P[j])
  print(index)
  points(X[j],Y[j],type='p',xlab="X",ylab="Y",pch=".",col= colors[maxu-index])
  
  
}

#attach(NAtemplatlongberncas)

#numcases <- length(V1)

#ct <- 0

#for (i in 1:numcases){
  
#  if(V2[i]==1){
   # points(X[i],Y[i],type='p',xlab="X",ylab="Y",pch="o",col= "black")
#    ct <- ct + 1
#  }
  
#}

ablineXs<-read.table(args[4], header=TRUE, col.names=c("V1"))

attach(ablineXs)

ablineX <- V1

ablineYs<-read.table(args[5], header=TRUE, col.names=c("V1"))

attach(ablineYs)

ablineY <- V1

abline(v=ablineX, col = "gray", lty = 1, lwd =0.01)
abline(h = ablineY, col = "gray", lty = 1, lwd =0.01)


library(plotrix)
library(calibrate)


#attach(climateginiarearectangles)
#attach(climateginiarearectangles3)

climateginiarearectanglesthreshold<-read.table(args[6], header=TRUE, col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10","V11","V12"))

attach(climateginiarearectanglesthreshold)

#rectangles <- climateginiarearectangles[order(V11),]
#rectangles <- climateginiarearectangles[order(V11),]
#rectangles <- climateginiarearectangles3[order(V11),]
rectangles <- climateginiarearectanglesthreshold[order(V12),]

attach(rectangles)

rectlx <- V1
rectly <- V2
rectrx <- V3
rectry <- V4
gridvalues <- V6

num <- length(V12)
#max <- V5[num]

uvalues3 <- unique(V12)

maxu <- length(uvalues3)

library(grDevices)

numcolors <- maxu

colors <- rainbow(numcolors * 3, start = 0.1, end = 0.99)

samplecolors <- sample(1:numcolors*3,numcolors,replace=F)

bool = 0;

numcols <-0

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
    if(V12[j]==uvalues3[i] && V11[j] != 1){
      if(prev == 0)
        numcols <- numcols + 1
      rect(rectlx[j],rectly[j],rectrx[j],rectry[j], density=0, border= colors[samplecolors[numcols]], lwd=0.5)
      textxy(rectlx[j] + 0.4, rectly[j] + 0.1, gridvalues[j], cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.45, rectly[j] + 0.3, V12[j], cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.22, rectly[j] + 0.2, V8[j], cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.23, rectly[j] + 0.2, ",", cx = 0.1, dcol = "black")
      textxy(rectlx[j] + 0.48, rectly[j] + 0.2, V9[j], cx = 0.1, dcol = "black")
      prev <- 1
    }
    else if(V12[j]==uvalues3[i] && V11[j] == 1){
      xinc <- rectrx[j] - rectlx[j]
      yinc <- rectry[j] - rectly[j]
      x <- rectlx[j] + xinc/2
      y <- rectly[j] + yinc/2
      symbols(x, y , squares = xinc/2 , 
            inches = FALSE, add = TRUE,
            fg = "black", bg = NA,lwd=0.02)
      if(gridvalues[j] > -5000.0)
        textxy(rectlx[j] + 0.4, rectly[j] + 0.1, gridvalues[j], cx = 0.1, dcol = "black")
    }
  }
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

#print("Hello")

groundtruthseattlelatlongsame<-read.table(args[7], header=TRUE,col.names=c("V1","V2","V3","V4"))

attach(groundtruthseattlelatlongsame)
#attach(groundtruthseattlelatlongaditya)
leftx <- V1
lefty <- V2
rightx <- V3
righty <- V4
numanom <- length(V4)

for(i in 1:numanom){
  rect(leftx[i],lefty[i],rightx[i],righty[i], density=99,col = "midnightblue", lwd=0.1, border = "transparent")
}

library(graphics)

giniareacellsseattlerectangles1<-read.table(args[8], header=TRUE,col.names=c("V1","V2","V3","V4"))

attach(giniareacellsseattlerectangles1)

leftx <- V1
lefty <- V2
rightx <- V3
righty <- V4
numanom <- length(V4)

for(i in 1:numanom){
  rect(leftx[i],lefty[i],rightx[i],righty[i], density=99,col = "orange", angle = 135 , lwd=0.1, border = "transparent")
}


  
dev.off()