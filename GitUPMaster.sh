#!/bin/bash
clear
echo "Uploading to Database"
DATE=Y`date '+%g'`D`date '+%j'`
DATEComit=Y`date '+%g'`D`date '+%j'`T`date '+%H%M'`

git init
git add .
git commit -m ${DATEComit}
git checkout -b master
git remote add origin "https://github.com/TTOExtreme/EveryApp.git"
git push -u origin master
git checkout ${DATE}

echo "finished"
sleep 3
