#!/bin/sh
LASTVER=$(<latest)
NEWVER=$(($LASTVER + 1))
echo $NEWVER
cd ../avitaln.github.io && ./ga.sh $LASTVER $NEWVER