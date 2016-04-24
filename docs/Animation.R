noiseevents <- 10

teles <- read.csv('telescope.txt',header=FALSE,sep=" ")
names(teles) <- c('Telescope','X','Y')

#asteroid <- c(-539773,-63317,4821,563,100)
asteroid <- read.csv('asteroid.txt',header=FALSE,sep=" ")

# y -> ax + b
a <- asteroid[4]/asteroid[3]
b <- asteroid[2] - a * asteroid[1]
xint <- -b/a
s <- c(xint,0)

#with(teles,plot(X,Y,pch=3))

# position of asteroid at time t
position <- function(x0,y0,vx,vy,t) {
  yp <- (y0 + t*vy)
  xp <- (x0 + t*vx)
  c(xp,yp)
}

events <- read.csv('events.txt',header=FALSE,sep=" ")
names(events) <- c('Telescope','T1','T2','Type')

start = min(events$T1)
end   = max(events$T2)
frames = 50
range <- (end-start)/frames

f2 <- range + start
f1 <- start

for(i in 1:frames) {
  # creating a name for each plot file with leading zeros
  if (i < 10) {name = paste('000',i,'plot.png',sep='')}
  if (i < 100 && i >= 10) {name = paste('00',i,'plot.png', sep='')}
  if (i >= 100) {name = paste('0', i,'plot.png', sep='')}
  png(name)
  frame <- events[((events$T1 < f2)&(events$T2 > f1)),]
  notframe <- events[!((events$T1 < f2)&(events$T2 > f1)),]
  #print(nrow(frame))
  with(teles,plot(X,Y,pch=20,xlab="",ylab=""))

  posn <- position(asteroid[1],asteroid[2],asteroid[3],asteroid[4],(f1+range/2))
  #segments(s[1],s[2],x1=posn[1],y1=posn[2])

  for (i in 1:nrow(frame)) {
    points(teles[frame[i,1]+1,2:3],type='p',col='green',pch=15)
    #symbols(teles[frame[i,1]+1,2:3],circles,col='green')
  }
  # Add noise 
  noise <- sample(0:noiseevents,1)
  for (i in 1:noise) {
    point <- sample(1:nrow(teles),1)
    points(teles[point,2:3],type='p',col='red',pch=4)
    print(teles[point,2:3])
  }

  f1 <- f2
  f2 <- f2 + range
  dev.off()
}
  
#for (i in 1:50) {
#  for (i in 1:nrow(frame)) {
#    points(teles[frame[i,1]+1,2:3],type='p',col='red',pch=2)
#  }
#  f1 <- f2
#  f2 <- f2 + range
#  print(f2)
#  Sys.sleep(0.5)
#}

