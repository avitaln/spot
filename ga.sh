#!/bin/sh
LASTVER=$(<latest)
NEWVER=$(($LASTVER + 1))
echo from $LASTVER to $NEWVER
pushd ../avitaln.github.io && ./ga.sh $LASTVER $NEWVER && popd
/usr/local/bin/sbt fullOptJS
cp target/scala-2.12/spot-opt.js dist/spot$NEWVER.js
echo $NEWVER > latest
git add dist/spot$NEWVER.js latest
git commit -m "GA"
git push
