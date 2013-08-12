

pdf("/Users/hadoop/Desktop/precrecallsatscanradiusmultginicirc.pdf")

colors<- rainbow(15, s = 1, v = 1, start = 0, end = 0.99, alpha = 1)

colors<-c(colors,"black","brown")

#radii<-c(2.0,2.1,2.2,2.3,2.4,2.5,2.6,2.7,2.8,2.9)

radmult<-c(2.0,2.1,2.2,2.3,2.4,2.5,2.6,2.7,2.8,2.9)
radii<-vector()
for(i in radmult){
  st<-paste("Local SatScan with radius multiplier ",i,sep="")
  radii<- c(radii, st)
}

radii<-c(radii,"Ginicircles sorted by case 1","Ginicircles sorted by case 2","Ginicircles sorted by case 3","Ginicircles sorted by case 4","Global SatScan over Seattle", "Global SatScan over NA")

attach(precisionrecall2.0)
plot(V3,V2,xlab="Recall",ylab="Precision",type="l",pch="*",lwd=2,col=colors[1],xlim=c(0.0,1.0),ylim=c(0.0,1.0),main="Global vs Local Spatial Scan Statistics")

attach(precisionrecall2.1)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[2])

attach(precisionrecall2.2)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[3])

attach(precisionrecall2.3)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[4])

attach(precisionrecall2.4)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[5])

attach(precisionrecall2.5)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[6])

attach(precisionrecall2.6)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[7])

attach(precisionrecall2.7)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[8])

attach(precisionrecall2.8)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[9])

attach(precisionrecall2.9)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[10])

attach(precisionrecallcase1)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[11])

attach(precisionrecallcase2)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[12])

attach(precisionrecallcase3)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[13])

attach(precisionrecallcase4)
points(V3,V2,type="l",pch="+",lwd=2,col=colors[14])

attach(precisionrecallsatscan0)
points(V3,V2,type="l",pch="+",lwd=2,col="black")

attach(precisionrecallsatscanNA)
points(V3,V2,type="l",pch="+",lwd=2,col="brown")

legend(0.35,1.0,radii,lwd=c(2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2),
       col=colors,ncol=1)

dev.off()

numareas<-vector()
for(i in 1:12)
  numareas<-c(numareas,i*10)

pdf("/Users/hadoop/Desktop/precrecalllocalginiallclimatehistogramfmeasures.pdf")

attach(precisionrecallsatscanNA)
len<-length(V1)
fmeasureNA<-vector()
count<-0
for(i in 1:len){
  if(count<120){
  fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
  fmeasureNA<-c(fmeasureNA,fmeas)
  count<-count+10
  }
}

#attach(precisionrecallsatscan0)
#len<-length(V1)
#fmeasureSeattle<-vector()
#count<-0
#for(i in 1:len){
#  if(count<400){
#  fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
#  fmeasureSeattle<-c(fmeasureSeattle,fmeas)
#  count<-count+10
#  }
#}

attach(precisionrecall2.4)
len<-length(V1)
fmeasureradiusmult<-vector()
count<-0
for(i in 1:len){
  if(count<120){
    fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
    fmeasureradiusmult<-c(fmeasureradiusmult,fmeas)
    count<-count+10
  }
}

attach(precisionrecallcase1)
len<-length(V1)
fmeasuregincase1<-vector()
count<-0
for(i in 1:len){
  if(count<120){
    fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
    fmeasuregincase1<-c(fmeasuregincase1,fmeas)
    count<-count+10
  }
}

attach(precisionrecallcase2)
len<-length(V1)
fmeasuregincase2<-vector()
count<-0
for(i in 1:len){
  if(count<120){
    fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
    fmeasuregincase2<-c(fmeasuregincase2,fmeas)
    count<-count+10
  }
}

attach(precisionrecallcase3)
len<-length(V1)
fmeasuregincase3<-vector()
count<-0
for(i in 1:len){
  if(count<120){
    fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
    fmeasuregincase3<-c(fmeasuregincase3,fmeas)
    count<-count+10
  }
}

attach(precisionrecallcase4)
len<-length(V1)
fmeasuregincase4<-vector()
count<-0
for(i in 1:len){
  if(count<120){
    fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
    fmeasuregincase4<-c(fmeasuregincase4,fmeas)
    count<-count+10
  }
}

#m1<-tapply(numareas,fmeasureNA)
#m2<-tapply(numareas,fmeasureSeattle)
#m3<-tapply(numareas,fmeasureradiusmult)

#fmeastable<-rbind(fmeasureNA,fmeasureradiusmult,fmeasuregincase1,fmeasuregincase2,fmeasuregincase3,fmeasuregincase4)

fmeastable<-rbind(fmeasureNA, fmeasureradiusmult,fmeasuregincase1,fmeasuregincase2, fmeasuregincase3, fmeasuregincase4)


#mp <- barplot(fmeastable, col=c("darkblue","red","green"),beside = TRUE,legend.text=c("Global Spatial Scan over NA","Global Spatial Scan over Seattle","Local Spatial Scan"),args.legend=list(x="topleft"), xlab="Number of Areas/Circles", ylab="F Measure",main="Comparison of Local and Global Spatial Scan")

mp <- barplot(fmeastable, names.arg=numareas,col=c("pink","red","green","yellow","blue","brown"),beside = TRUE,legend.text=c("Global Spatial Scan","Local Spatial Scan","Gini circles case1","Gini circles case2","Gini circles case3","Gini circles case4"),args.legend=list(x="topleft"), xlab="Number of Areas/Circles", ylab="F Measure",main="Local & Global Spatial Scan vs Growing circles by gini index")


dev.off()
