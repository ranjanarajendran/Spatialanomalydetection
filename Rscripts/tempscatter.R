#dev.off()

args<-commandArgs(TRUE)

pdf(args[1])

Temperature<-read.table(args[2], header=TRUE, col.names=c("V1","V2","V3","V4","V5"))

attach(Temperature)
plot(V4,V5,type='p',xlab="Longitude",ylab="Latitude",pch=".",col="white")

X <- V4
Y <- V5
P <- V3

num <- length(V1)

uvalues <- unique(V3)

uvalueprecipsorted <- sort(uvalues, decreasing = FALSE)

maxu <- length(uvalueprecipsorted)

library(grDevices)

#colors <- topo.colors(maxu, alpha=1)

colors <- rainbow(maxu, alpha =1, start = 0.1, end = 0.99)


for (j in 1:num){
  
    index <- which(uvalueprecipsorted %in% P[j])
    print(index)
    points(X[j],Y[j],type='p',xlab="X",ylab="Y",pch=".",col= colors[index])

  
}

dev.off()