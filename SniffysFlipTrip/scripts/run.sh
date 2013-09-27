#!/usr/bin/env bash

# make sure we have java
java -version &> /dev/null
if [[ $? -ne 0 ]]
then
    echo "Java runtime not found. Please download at www.java.com/getjava/"
    exit 1
fi

java -jar SniffysFlipTrip.jar
