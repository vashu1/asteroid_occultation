# length of each (square) edge of the area covered in metres
areadim <- 800

# number of telescopes in each dimension (total number will be telesnum^2)
telesnum <- 4

# distance between telescopes (metres)
telesdist <- 200

# Max range (metres) - maximum distance from centre line and still observe occultation
# e.g. 50m is max for a 100m object, not accounting for abberations such as refraction
mrange <- 50

sims <- 5 # number of simulated events

#teles <- data.frame(numeric(),numeric(),numeric())
#for (i in 0:(telesnum-1)) {
#  t <- data.frame(c((i*telesnum + 1):(telesnum*(i+1))),i*telesdist,c(0:(telesnum-1)*telesdist))
#  teles <- rbind(teles,t)
#}
#names(teles) <- c('TeleNumber','Xcoor','Ycoor')

# generate the array
gentel <- function(telnum,teldist) {
  ts <- data.frame(numeric(),numeric(),numeric())
  for (i in 0:(telesnum-1)) {
    t <- data.frame(c((i*telnum + 1):(telnum*(i+1))),i*teldist,c(0:(telnum-1)*teldist))
    ts <- rbind(ts,t)
  }
  names(ts) <- c('TeleNumber','Xcoor','Ycoor')
  ts
}
teles <- gentel(telesnum,telesdist)

# plot search area
#with(teles,plot(Xcoor,Ycoor))

# function to determine distance between abline (in form ax+by+c = 0)and point (x,y)
# denom = sqrt(a^2 + b^2) - a constant for each a/b line, so pass in to avoid duplicate calcs
dist <- function(x,y,a,b,c,denom = 0) {
  if (denom <= 0) {
    denom = sqrt(a^2 + b^2)
  }
  abs(a*x + b*y + c)/denom
}

# generate a random glide path for an asteroid through the observation area
randompath <- function() {
  # which axis does the object enter through
  axes <- sample(4, 2,replace=F)
  # left to right
  axes <- c(2,4)
  intersects <- runif(2,0,areadim)
  # a is axis, b = intersect, both scalar. Returns a coordinate
  coords <- function(a,b) {
    if (a == 1) {
      c(b,0)
    } else {
      if (a == 2) {
        c(0,b)
      } else {
        if (a == 3) {
          c(b,areadim)
        } else {
          c(areadim,b)
        }
      }
    }
  }
  s <- coords(axes[1],intersects[1])
  e <- coords(axes[2],intersects[2])
  slope   <- (e[2]-s[2])/(e[1]-s[1])
  intsect <- e[2] - slope*e[1]
  c(intsect,slope)
}


results <- numeric(sims)
with(teles,plot(Xcoor,Ycoor,xlim=c(0,areadim),ylim=c(0,areadim)))
for (i in 1:sims) {
  path <- randompath()
  dists <- dist(teles[2],teles[3],path[2],-1,path[1])
  results[i] <- sum(dists < mrange)
  color <- 'green'
  if (results[i] < 3) {
    color <- 'red'
  }
  abline(path,col=color)
}
