#!/bin/sh
mvn install -Dmaven.test.skip=true
cd target/dist
java -jar parking-system-1.0.jar
