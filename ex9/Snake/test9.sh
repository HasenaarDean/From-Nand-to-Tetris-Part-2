#!/bin/bash

EX=9
TAR_FILE=project$EX.tar

rm -fr testTar
mkdir testTar
cp $TAR_FILE testTar/
cd testTar/
tar -xf $TAR_FILE

ERR=`ls -l *.jack >/dev/null 2>/dev/null`
if [ $? != 0 ]; then
  echo "No *.jack files found"
fi


if [ -r README ]; then
  dos2unix README &> /dev/null
  logins=`head -1 README`
  echo Your logins are: $logins, is that ok?
else
  echo No README was found
fi

cd ../
rm -Rf testTar
