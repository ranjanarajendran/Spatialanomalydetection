

pdf("/Users/hadoop/Desktop/precrecallradiusmultginicirc15.pdf")

colors<- rainbow(5, s = 1, v = 1, start = 0.1, end = 0.99, alpha = 1)

colors<-c(colors,"black","brown")

#radii<-c(2.0,2.1,2.2,2.3,2.4,2.5,2.6,2.7,2.8,2.9)

radmult<-c(2.4)
radii<-vector()
for(i in radmult){
  st<-paste("Local SatScan with radius multiplier ",i,sep="")
  radii<- c(radii, st)
}

radii<-c(radii,"Ginicircles sorted by case 1","Ginicircles sorted by case 2","Ginicircles sorted by case 3","Ginicircles sorted by case 4","Global SatScan over NA", "Global SatScan over Seattle")

#attach(precisionrecall2.0)
#plot(V3,V2,xlab="Recall",ylab="Precision",type="l",pch="*",lwd=2,col=colors[1],xlim=c(0.0,1.0),ylim=c(0.0,1.0),main="Global vs Local Spatial Scan Statistics")

#attach(precisionrecall2.1)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[2])

#attach(precisionrecall2.2)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[3])

#attach(precisionrecall2.3)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[4])

attach(precisionrecall2.4)
plot(V3,V2,xlab="Recall",ylab="Precision",type="l",pch="*",lwd=2,col=colors[1],xlim=c(0.0,1.0),ylim=c(0.0,1.0),main="Global vs Local Satscan vs Growing circles by gini index")

#attach(precisionrecall2.5)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[6])

#attach(precisionrecall2.6)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[7])

#attach(precisionrecall2.7)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[8])

#attach(precisionrecall2.8)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[9])

#attach(precisionrecall2.9)
#points(V3,V2,type="l",pch="+",lwd=2,col=colors[10])

attach(precisionrecallcase1)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[2])

attach(precisionrecallcase2)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[3])

attach(precisionrecallcase3)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[4])

attach(precisionrecallcase4)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[5])

attach(precisionrecallsatscan0)
points(V3,V2,type="l",pch="+",lwd=2,col="black")

attach(precisionrecallsatscanNA)
points(V3,V2,type="l",pch="+",lwd=2,col="brown")

legend(0.35,1.0,radii,lwd=c(2,2,2,2,2,2,2),
       col=colors,ncol=1)

dev.off()

