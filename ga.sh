#!/bin/sh
LASTVER=$(<latest)
NEWVER=$(($LASTVER + 1))
echo $NEWVER
cd ../avitaln.github.io && ./ga.sh $LASTVER $NEWVER
sbt fullOptJS
cp target/scala-2.12/spot-opt.js dist/spot$NEWVER.js
git add dist/spot$NEWVER.js
git commit -m "GA"
git push