#!/bin/sh
./gradlew jar
java -server -Xmx4096m -Xms4096m -XX:+CMSClassUnloadingEnabled -XX:+HeapDumpOnOutOfMemoryError -jar build/libs/github-0.1.jar
