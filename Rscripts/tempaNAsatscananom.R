# Normal distribution
#dev.off()

args<-commandArgs(TRUE)
pdf(args[1])
#attach(NAtemplatlonggeo)

NAtemplatlonggeo<-read.table(args[2], header=TRUE, col.names=c("V1","V2","V3"))

attach(NAtemplatlonggeo)

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

groundtruthseattlelatlongsame<-read.table(args[6], header=TRUE,col.names=c("V1","V2","V3","V4"))

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

#attach(top10giniareasseattlesatscanlow)
#attach(top10giniareasseattleaditya)
#attach(top10giniareasseattle3)

seattlesatscannormhighlowrectangles<-read.table(args[7], header=TRUE,col.names=c("V1","V2","V3","V4"))

attach(seattlesatscannormhighlowrectangles)
leftx <- V1
lefty <- V2
rightx <- V3
righty <- V4
numgini <- length(V4)

for(i in 1:numgini){
  if(lefty[i] < 29.0)
    print("Error")
  rect(leftx[i],lefty[i],rightx[i],righty[i], density=99,angle=135,col = "orange", lwd=0.1, border = "transparent")
}


library(graphics)

relevantcircles<-read.table(args[9], header=TRUE,col.names=c("V1"))

attach(relevantcircles)

rcircleids <- V1
#attach(SeattleSatscan.col)
#attach(SeattleSatscanlow.col)
#attach(NAtempnormhighlow.col)

output.col<-read.table(args[8], header=TRUE,col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10","V11","V12","V13","V14"))


if(args[10] == 5){

    output.col<-read.table(args[8], header=TRUE,col.names=c("V1","V2","V3","V4","V5","V6","V7","V8","V9","V10","V11","V12","V13","V14","V15"))
}


attach(output.col)
centrex <- V4
centrey <- V3
radii <- V5
numcircles <- length(V10)

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
 if(V10[i]<= 0.05 && V1[i] %in% rcircleids)
    #draw.circle(centrex[i],centrey[i],radii[i],nv=100,border="cyan",col=NA,lty=1,lwd=0.1)
    circles(centrex[i],centrey[i], radii[i], col = NA, border = "brown")
    count <- count + 1
}

circles(centrex[1],centrey[1], radii[1], col = NA, border = "black")
circles(centrex[2],centrey[2], radii[2], col = NA, border = "red")
dev.off()