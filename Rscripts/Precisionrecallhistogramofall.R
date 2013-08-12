

pdf("/Users/hadoop/Desktop/precrecallginiareathreshold1.pdf")

colours<- rainbow(8, s = 1, v = 1, start = 0.01, end = 0.99, alpha = 1)


#radii<-c(2.0,2.1,2.2,2.3,2.4,2.5,2.6,2.7,2.8,2.9)



radii<-c("Local Sat Scan with radiusmult 2.4","Global SatScan over Seattle", "Global SatScan over NA","Grown Gini area Case1","Case 2","Case 3","Case 4")

attach(precisionrecall2.4)
plot(V3,V2,xlab="Recall",ylab="Precision",type="l",pch="*",lwd=2,col=colours[1],xlim=c(0.0,1.0),ylim=c(0.0,1.0),main="Grown Areas combining all cell pairs with gini index below threshold")

attach(precisionrecallsatscan0)
points(V3,V2,type="l",pch="+",lwd=2,col=colours[2])

attach(precisionrecallsatscanNA)
points(V3,V2,type="l",pch="+",lwd=2,col=colours[3])

#attach(precisionandrecallbpair1)
attach(precisionandrecallthreshold1)
points(V3,V2,type="l",pch="+",lwd=2,col=colours[4])

#attach(precisionandrecallbpair2)
attach(precisionandrecallthreshold2)
points(V3,V2,type="l",pch="+",lwd=2,col=colours[5])

#attach(precisionandrecallbpair3)
attach(precisionandrecallthreshold3)
points(V3,V2,type="l",pch="+",lwd=2,col=colours[6])

#attach(precisionandrecall4)
attach(precisionandrecallthreshold4)
points(V3,V2,type="l",pch="+",lwd=2,col=colours[7])

legend(0.40,1.0,radii,lwd=c(2,2,2,2,2,2,2),
       col=colours,ncol=1)

dev.off()



pdf("/Users/hadoop/Desktop/precrecallhistogramfmeasuresofallthreshold1.pdf")

attach(precisionandrecallthreshold4)
len<-length(V1)
fmeasure4<-vector()
count<-0
for(i in 1:len){
  if(count<7){
    fmeas<-V4[i]
    fmeasure4<-c(fmeasure4,fmeas)
    count<-count+1
  }
}

attach(precisionandrecall4)
len<-length(V1)
fmeasure5<-vector()
count<-0
for(i in 1:len){
  if(count<7){
    fmeas<-V4[i]
    fmeasure5<-c(fmeasure5,fmeas)
    count<-count+1
  }
}

attach(precisionandrecallthreshold3)
len<-length(V1)
fmeasure3<-vector()
count<-0
for(i in 1:len){
  if(count<7){
    fmeas<-V4[i]
    fmeasure3<-c(fmeasure3,fmeas)
    count<-count+1
  }
}

attach(precisionandrecallthreshold2)
len<-length(V1)
fmeasure2<-vector()
count<-0
for(i in 1:len){
  if(count<7){
    fmeas<-V4[i]
    fmeasure2<-c(fmeasure2,fmeas)
    count<-count+1
  }
}

attach(precisionandrecallthreshold1)
len<-length(V1)
fmeasure1<-vector()
count<-0
for(i in 1:len){
  if(count<7){
    fmeas<-V4[i]
    fmeasure1<-c(fmeasure1,fmeas)
    count<-count+1
  }
}

attach(precisionrecallsatscanNA)
len<-length(V1)
fmeasureNA<-vector()
count<-0
for(i in 1:len){
  if(count<7 ){
    fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
    fmeasureNA<-c(fmeasureNA,fmeas)
    count<-count+1
  }
}

numareas<-vector()
for(i in 1:30)
  numareas<-c(numareas,i*10)

attach(precisionrecall2.4)
len<-length(V1)
fmeasureradiusmult<-vector()
count<-0
for(i in 1:len){
  if(count<7 ){
    fmeas<-2*V2[i]*V3[i]/(V2[i]+V3[i])
    fmeasureradiusmult<-c(fmeasureradiusmult,fmeas)
    count<-count+1
  }
}

numareas<-vector()
for(i in 1:7)
  numareas<-c(numareas,i*10)


fmeastable<-rbind(fmeasureradiusmult,fmeasure1,fmeasure2,fmeasure3,fmeasure4,fmeasure5)

mp <- barplot(fmeastable,names.arg=numareas, col=c("darkblue","green","pink","violet","orange","brown"),beside = TRUE,legend.text=c("Local Spatial Scan","Grown areas by threshold Case 1","Grown areas by threshold Case 2", "Grown areas by threshold Case 3","Grown areas by threshold Case 4","Grown areas by best pair Case 4"),args.legend=list(x="topleft"), xlab="Number of Areas/Circles", ylab="F Measure",main="Grown Areas by gini index vs Local Spatial Scan Statistics")


dev.off()
