#!/bin/bash
clear
echo "Uploading to Database"
DATE=Y`date '+%g'`D`date '+%j'`

git init
git add .
git commit -m ${DATE}
git checkout -b ${DATE}
git checkout ${DATE}
git remote add origin "https://github.com/TTOExtreme/EveryApp.git"
git push -u origin master

echo "finished"
sleep 3
