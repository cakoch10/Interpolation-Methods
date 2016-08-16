
CubicErrorTraj <- read.csv("C:/test/trajectory2/Error_Analysis/CubicErrorTraj.plt", header=FALSE)
colnames(CubicErrorTraj) <- c("file", "error", "errorSquared", "length")

LinerrorTraj <- read.csv("C:/test/trajectory2/Error_Analysis/LinerrorTraj.plt", header=FALSE)
colnames(LinerrorTraj) <- c("file", "error", "errorSquared", "length")

NNerrorTraj <- read.csv("C:/test/trajectory2/Error_Analysis/NNerrorTraj.plt", header=FALSE)
colnames(NNerrorTraj) <- c("file", "error", "errorSquared", "length")

CubicTSError <- read.csv("C:/test/trajectory2/Error_Analysis/CubicTSError.plt", header=FALSE)
colnames(CubicTSError) <- c("file", "error", "errorSquared", "length")

LinerrorTS <- read.csv("C:/test/trajectory2/Error_Analysis/LinerrorTS.plt", header=FALSE)
colnames(LinerrorTS) <- c("file", "error", "errorSquared", "length")

NNerrorTS <- read.csv("C:/test/trajectory2/Error_Analysis/NNerrorTS.plt", header=FALSE)
colnames(NNerrorTS) <- c("file", "error", "errorSquared", "length")

totalDistance <- read.csv("C:/test/trajectory2/Error_Analysis/totalDistance.plt", header=FALSE)
colnames(totalDistance) <- c("trajectory", "timeSeries", "file")

roughness <- read.csv("C:/test/trajectory2/Error_Analysis/roughness.plt", header=FALSE)
colnames(roughness) <- c("file", "roughness")

totalDistance <- merge(totalDistance, roughness, by.x = "file", by.y = "file")

LoessErrorTraj <- read.csv("C:/test/trajectory2/Error_Analysis/LoessErrorTraj.plt", header = FALSE)
colnames(LoessErrorTraj) <- c("file", "error", "errorSquared", "length")


LinerrorTS <- lapply(LinerrorTS, function(x){
  gsub("_TS", "", x)
})

NNerrorTS <- lapply(NNerrorTS, function(x){
  gsub("_TS", "", x)
})

LinerrorTraj <- lapply(LinerrorTraj, function(x){
  gsub("_LinInt_Traj.plt", "", x)
})

# Format CubicErrorTraj

CubicErrorTraj <- lapply(CubicErrorTraj, function(x){
  sub("_", "@", x)
})

test <- data.frame(do.call('rbind', strsplit(as.character(CubicErrorTraj$file),'_',fixed = TRUE)))

test <- lapply(test, function(x){
  sub("@", "_", x)
})

CubicErrorTraj$file <- test$X1

# Format CubicTSError

CubicTSError <- lapply(CubicTSError, function(x){
  sub("_", "@", x)
})

CubicTSError <- lapply(CubicTSError, function(x){
  sub("_", "@", x)
})

test <- data.frame(do.call('rbind', strsplit(as.character(CubicTSError$file),'_',fixed = TRUE)))

test <- lapply(test, function(x){
  sub("@TS@", "_", x)
})

CubicTSError$file <- test$X1

# Format LoessErrorTraj -----------------------------------------------------------

LoessErrorTraj <- lapply(LoessErrorTraj, function(x){
  sub("_", "@", x)
})

test <- data.frame(do.call('rbind', strsplit(as.character(LoessErrorTraj$file),'_',fixed = TRUE)))

test <- lapply(test, function(x){
  sub("@", "_", x)
})

LoessErrorTraj$file <- test$X1


# Merge Files
NNerrorTS <- merge(totalDistance, NNerrorTS, by.x = "file", by.y = "file")
NNerrorTraj <- merge(totalDistance, NNerrorTraj, by.x = "file", by.y = "file")
CubicTSError <- merge(totalDistance, CubicTSError, by.x = "file", by.y = "file")
CubicErrorTraj <- merge(totalDistance, CubicErrorTraj, by.x = "file", by.y = "file")
LinerrorTraj <- merge(totalDistance, LinerrorTraj, by.x = "file", by.y = "file")
LinerrorTS <- merge(totalDistance, LinerrorTS, by.x = "file", by.y = "file")
LoessErrorTraj <- merge(totalDistance, LoessErrorTraj, by.x = "file", by.y = "file")

# Add Gap Lengths

test <- data.frame(do.call('rbind', strsplit(as.character(NNerrorTS$file),'_',fixed = TRUE)))
NNerrorTS$Gaps <- test$X2

test <- data.frame(do.call('rbind', strsplit(as.character(NNerrorTraj$file),'_',fixed = TRUE)))
NNerrorTraj$Gaps <- test$X2

test <- data.frame(do.call('rbind', strsplit(as.character(CubicTSError$file),'_',fixed = TRUE)))
CubicTSError$Gaps <- test$X2

test <- data.frame(do.call('rbind', strsplit(as.character(CubicErrorTraj$file),'_',fixed = TRUE)))
CubicErrorTraj$Gaps <- test$X2

test <- data.frame(do.call('rbind', strsplit(as.character(LinerrorTraj$file),'_',fixed = TRUE)))
LinerrorTraj$Gaps <- test$X2

test <- data.frame(do.call('rbind', strsplit(as.character(LinerrorTS$file),'_',fixed = TRUE)))
LinerrorTS$Gaps <- test$X2

test <- data.frame(do.call('rbind', strsplit(as.character(LoessErrorTraj$file),'_',fixed = TRUE)))
LoessErrorTraj$Gaps <- test$X2

# Add inaccuracy
NNerrorTS$error <- as.numeric(as.character(NNerrorTS$error))
NNerrorTraj$error <- as.numeric(as.character(NNerrorTraj$error))
CubicTSError$error <- as.numeric(as.character(CubicTSError$error))
CubicErrorTraj$error <- as.numeric(as.character(CubicErrorTraj$error))
LinerrorTraj$error <- as.numeric(as.character(LinerrorTraj$error))
LinerrorTS$error <- as.numeric(as.character(LinerrorTS$error))
LoessErrorTraj$error <- as.numeric(as.character(LoessErrorTraj$error))

NNerrorTS$timeSeries <- as.numeric(as.character(NNerrorTS$timeSeries))
CubicTSError$timeSeries <- as.numeric(as.character(CubicTSError$timeSeries))
LinerrorTS$timeSeries <- as.numeric(as.character(LinerrorTS$timeSeries))

NNerrorTraj$trajectory <- as.numeric(as.character(NNerrorTraj$trajectory))
CubicErrorTraj$trajectory <- as.numeric(as.character(CubicErrorTraj$trajectory))
LinerrorTraj$trajectory <- as.numeric(as.character(LinerrorTraj$trajectory))
LoessErrorTraj$trajectory <- as.numeric(as.character(LoessErrorTraj$trajectory))

NNerrorTS$Inaccuracy <- NNerrorTS$error / NNerrorTS$timeSeries
CubicTSError$Inaccuracy <- CubicTSError$error / CubicTSError$timeSeries
LinerrorTS$Inaccuracy <- LinerrorTS$error / LinerrorTS$timeSeries

NNerrorTraj$Inaccuracy <- NNerrorTraj$error / NNerrorTraj$trajectory
CubicErrorTraj$Inaccuracy <- CubicErrorTraj$error / CubicErrorTraj$trajectory
LinerrorTraj$Inaccuracy <- LinerrorTraj$error / LinerrorTraj$trajectory
LoessErrorTraj$Inaccuracy <- LoessErrorTraj$error / LoessErrorTraj$trajectory

# Convert Gaps to numeric
NNerrorTS$Gaps <- as.numeric(as.character(NNerrorTS$Gaps))
NNerrorTraj$Gaps <- as.numeric(as.character(NNerrorTraj$Gaps))
CubicTSError$Gaps <- as.numeric(as.character(CubicTSError$Gaps))
CubicErrorTraj$Gaps <- as.numeric(as.character(CubicErrorTraj$Gaps))
LinerrorTraj$Gaps <- as.numeric(as.character(LinerrorTraj$Gaps))
LinerrorTS$Gaps <- as.numeric(as.character(LinerrorTS$Gaps))
LoessErrorTraj$Gaps <- as.numeric(as.character(LoessErrorTraj$Gaps))

# Get average of inaccuracies
x <- c(seq(from = 50, to = 2000, by = 50))
y <- x+2000
df <- data.frame(x, y)
for(i in seq(from = 50, to = 2000, by = 50)){
  test2 <- subset(NNerrorTS, NNerrorTS$Gaps==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
NNerrorTS2 <- df

x <- c(seq(from = 50, to = 2000, by = 50))
y <- x+2000
df <- data.frame(x, y)
for(i in seq(from = 50, to = 2000, by = 50)){
  test2 <- subset(NNerrorTraj, NNerrorTraj$Gaps==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
NNerrorTraj2 <- df

x <- c(seq(from = 50, to = 2000, by = 50))
y <- x+2000
df <- data.frame(x, y)
for(i in seq(from = 50, to = 2000, by = 50)){
  test2 <- subset(CubicTSError, CubicTSError$Gaps==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
CubicTSError2 <- df

x <- c(seq(from = 50, to = 2000, by = 50))
y <- x+2000
df <- data.frame(x, y)
for(i in seq(from = 50, to = 2000, by = 50)){
  test2 <- subset(CubicErrorTraj, CubicErrorTraj$Gaps==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
CubicErrorTraj2 <- df

x <- c(seq(from = 50, to = 2000, by = 50))
y <- x+2000
df <- data.frame(x, y)
for(i in seq(from = 50, to = 2000, by = 50)){
  test2 <- subset(LinerrorTraj, LinerrorTraj$Gaps==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
LinerrorTraj2 <- df

x <- c(seq(from = 50, to = 2000, by = 50))
y <- x+2000
df <- data.frame(x, y)
for(i in seq(from = 50, to = 2000, by = 50)){
  test2 <- subset(LinerrorTS, LinerrorTS$Gaps==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
LinerrorTS2 <- df

x <- c(seq(from = 50, to = 2000, by = 50))
y <- x+2000
df <- data.frame(x, y)
for(i in seq(from = 50, to = 2000, by = 50)){
  test2 <- subset(LoessErrorTraj, LoessErrorTraj$Gaps==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
LoessErrorTraj2 <- df

#Visualize Total Error
library(ggplot2)

df <- data.frame(NNerrorTS2$x, NNerrorTS2$y, NNerrorTraj2$y, CubicErrorTraj2$y, CubicTSError2$y, LinerrorTS2$y, LinerrorTraj2$y)


ggplot(df, aes(NNerrorTS2$x, y=value, color=Method)) + labs(x="Gap Length", y="Inaccuracy") + ggtitle("Comparing Inaccuracy")  + geom_point(aes(y=LinerrorTraj2$y, col="Linear (Trajectory)"), size=0.5) +geom_point(aes(y=CubicErrorTraj2$y,col="Cubic (Trajectory)"), size=0.5)+ geom_point(aes(y=NNerrorTraj2$y,col="N. Neighbor (Trajectory)"), size=0.5) + geom_point(aes(y=LinerrorTS2$y, col="Linear (TS)"), size=0.5) + geom_point(aes(y=NNerrorTS2$y,col="N. Neighbor (TS)"), size=0.5) + geom_point(aes(y=CubicTSError2$y,col="Cubic (TS)"), size=0.5) + geom_line(aes(x = LinerrorTraj2$x, y = LinerrorTraj2$y), color="#00BA38", data=LinerrorTraj2, size=0) + geom_line(aes(x = NNerrorTraj2$x, y = NNerrorTraj2$y), color="#619CFF", data=NNerrorTraj2, size=0) + geom_line(aes(x = LinerrorTS2$x, y = LinerrorTS2$y), color="#00BFC4", data=LinerrorTS2, size=0) + geom_line(aes(x = NNerrorTS2$x, y = NNerrorTS2$y), color="#FB61D7", data=NNerrorTS2, size=0) + geom_line(aes(x = CubicTSError2$x, y = CubicTSError2$y), color="#C49A00", data = CubicTSError2, size = 0) + geom_line(aes(x = CubicErrorTraj2$x, y = CubicErrorTraj2$y), color="#F8766D", data = CubicErrorTraj2, size = 0)


#Visualize Total Error (without Cubic Trajectory)

ggplot(df, aes(NNerrorTS2$x, y=value, color=Method)) + labs(x="Gap Length", y="Inaccuracy") + ggtitle("Comparing Inaccuracy (without Cubic Trajectory)")  + geom_point(aes(y=LinerrorTraj2$y, col="Linear (Trajectory)"), size=0.5) + geom_point(aes(y=NNerrorTraj2$y,col="N. Neighbor (Trajectory)"), size=0.5) + geom_point(aes(y=LinerrorTS2$y, col="Linear (TS)"), size=0.5) + geom_point(aes(y=NNerrorTS2$y,col="N. Neighbor (TS)"), size=0.5) + geom_point(aes(y=CubicTSError2$y,col="Cubic (TS)"), size=0.5) + geom_line(aes(x = LinerrorTraj2$x, y = LinerrorTraj2$y), color="#C49A00", data=LinerrorTraj2, size=0) + geom_line(aes(x = NNerrorTraj2$x, y = NNerrorTraj2$y), color="#00B6EB", data=NNerrorTraj2, size=0) + geom_line(aes(x = LinerrorTS2$x, y = LinerrorTS2$y), color="#00C094", data=LinerrorTS2, size=0) + geom_line(aes(x = NNerrorTS2$x, y = NNerrorTS2$y), color="#FB61D7", data=NNerrorTS2, size=0) + geom_line(aes(x = CubicTSError2$x, y = CubicTSError2$y), color="#F8766D", data = CubicTSError2, size = 0)


# Write to CSV

df <- data.frame(NNerrorTS2$x, NNerrorTS2$y, NNerrorTraj2$y, CubicTSError2$y, CubicErrorTraj2$y, LoessErrorTraj2$y, LinerrorTS2$y, LinerrorTraj2$y)

colnames(df) <- c("Gaps", "NNTS", "NNTraj", "CubicTS","CubicTraj","LoessTraj","LinearTS","LinearTraj")

write.table(df, "C:/test/trajectory2/Error_Analysis/final_results.plt", sep=",", row.names = FALSE)

df <- NNerrorTS2
colnames(df) <- c("Gaps", "Inaccuracy", "Roughness")
write.table(df, "C:/test/trajectory2/Error_Analysis/NNerrorTS2.plt", sep=",", row.names = FALSE)
df <- NNerrorTraj2
colnames(df) <- c("Gaps", "Inaccuracy", "Roughness")
write.table(df, "C:/test/trajectory2/Error_Analysis/NNerrorTraj2.plt", sep=",", row.names = FALSE)
df <- CubicTSError2
colnames(df) <- c("Gaps", "Inaccuracy", "Roughness")
write.table(df, "C:/test/trajectory2/Error_Analysis/CubicTSError2.plt", sep=",", row.names = FALSE)
df <- CubicErrorTraj2
colnames(df) <- c("Gaps", "Inaccuracy", "Roughness")
write.table(df, "C:/test/trajectory2/Error_Analysis/CubicErrorTraj2.plt", sep=",", row.names = FALSE)
df <- LoessErrorTraj2
colnames(df) <- c("Gaps", "Inaccuracy", "Roughness")
write.table(df, "C:/test/trajectory2/Error_Analysis/LoessErrorTraj2.plt", sep=",", row.names = FALSE)
df <- LinerrorTS2
colnames(df) <- c("Gaps", "Inaccuracy", "Roughness")
write.table(df, "C:/test/trajectory2/Error_Analysis/LinerrorTS2.plt", sep=",", row.names = FALSE)
df <- LinerrorTraj2
colnames(df) <- c("Gaps", "Inaccuracy", "Roughness")
write.table(df, "C:/test/trajectory2/Error_Analysis/LinerrorTraj2.plt", sep=",", row.names = FALSE)


# Average Roughness Across files (each unique trajectory is averaged across different gap lengths)
NNerrorTS$length <- as.numeric(as.character(NNerrorTS$length))
x <- NNerrorTS$length
x <- unique(x)
y <- x+2000
z <- y+2000
df <- data.frame(x, y, z)
for(i in x){
  test2 <- subset(NNerrorTS, NNerrorTS$length==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$roughness)
  tempVal <- tempVal+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
NNerrorTS3 <- df

NNerrorTraj$length <- as.numeric(as.character(NNerrorTraj$length))
x <- NNerrorTraj$length
x <- unique(x)
y <- x+2000
z <- y+2000
df <- data.frame(x, y, z)
for(i in x){
  test2 <- subset(NNerrorTraj, NNerrorTraj$length==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$roughness)
  tempVal <- tempVal+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
NNerrorTraj3 <- df

CubicErrorTraj$length <- as.numeric(as.character(CubicErrorTraj$length))
x <- CubicErrorTraj$length
x <- unique(x)
y <- x+2000
z <- y+2000
df <- data.frame(x, y, z)
for(i in x){
  test2 <- subset(CubicErrorTraj, CubicErrorTraj$length==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$roughness)
  tempVal <- tempVal+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
CubicErrorTraj3 <- df

CubicTSError$length <- as.numeric(as.character(CubicTSError$length))
x <- CubicTSError$length
x <- unique(x)
y <- x+2000
z <- y+2000
df <- data.frame(x, y, z)
for(i in x){
  test2 <- subset(CubicTSError, CubicTSError$length==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$roughness)
  tempVal <- tempVal+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
CubicTSError3 <- df

LinerrorTS$length <- as.numeric(as.character(LinerrorTS$length))
x <- LinerrorTS$length
x <- unique(x)
y <- x+2000
z <- y+2000
df <- data.frame(x, y, z)
for(i in x){
  test2 <- subset(LinerrorTS, LinerrorTS$length==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$roughness)
  tempVal <- tempVal+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
LinerrorTS3 <- df

LinerrorTraj$length <- as.numeric(as.character(LinerrorTraj$length))
x <- LinerrorTraj$length
x <- unique(x)
y <- x+2000
z <- y+2000
df <- data.frame(x, y, z)
for(i in x){
  test2 <- subset(LinerrorTraj, LinerrorTraj$length==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$roughness)
  tempVal <- tempVal+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
LinerrorTraj3 <- df

LoessErrorTraj$length <- as.numeric(as.character(LoessErrorTraj$length))
x <- LoessErrorTraj$length
x <- unique(x)
y <- x+2000
z <- y+2000
df <- data.frame(x, y, z)
for(i in x){
  test2 <- subset(LoessErrorTraj, LoessErrorTraj$length==i)
  tempVal <- i+2000
  df[df==tempVal] <- mean(test2$roughness)
  tempVal <- tempVal+2000
  df[df==tempVal] <- mean(test2$Inaccuracy)
}
LoessErrorTraj3 <- df

# Plot average roughness, y is roughness, z is inaccuracy
library(ggplot2)

df <- data.frame(NNerrorTS3$y, NNerrorTraj3$y, CubicErrorTraj3$y, CubicTSError3$y, LinerrorTS3$y, LinerrorTraj3$y, LoessErrorTraj3$y, NNerrorTS3$z, NNerrorTraj3$z, CubicErrorTraj3$z, CubicTSError3$z, LinerrorTS3$z, LinerrorTraj3$z, LoessErrorTraj3$z)

ggplot(df, aes(NNerrorTS3$y, y=value, color=Method)) + labs(x="Roughness", y="Inaccuracy") + ggtitle("Comparing Inaccuracy and Roughness")  + geom_point(aes(x=LinerrorTraj3$y, y=LinerrorTraj3$z, col="Linear (Trajectory)"), size=1) + geom_point(aes(x=CubicErrorTraj3$y, y=CubicErrorTraj3$z, col="Cubic (Trajectory)"), size=1)+ geom_point(aes(x=LoessErrorTraj3$y, y=LoessErrorTraj3$z, col="LOESS (Trajectory)"), size=1)+ geom_point(aes(x=NNerrorTraj3$y, y=NNerrorTraj3$z, col="N. Neighbor (Trajectory)"), size=1)
