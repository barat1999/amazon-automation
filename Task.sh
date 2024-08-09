#!/bin/sh
script_dir=$(dirname "$0")
cd $script_dir
echo "Starting Task.sh"
browserMode=$1
echo $BROWSER
echo $browserMode
mvn clean test -DBrowser=$BROWSER -DBrowserMode=$browserMode -DsuiteXmlFile=src/test/resources/Amazon.xml
