
args<-commandArgs(TRUE)

pdf(args[1])

temperature<-read.table(args[2], header=TRUE, col.names=c("V1","V2","V3","V4","V5"))

attach(temperature)

Temperature <- V3
#num <- length(precips)

hist(Temperature, freq=TRUE, col = "lightpink")

dev.off()