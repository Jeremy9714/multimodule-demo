#!/bin/sh

SERVER_JAR="demo-console"  # 此处为包名

echo -n "Stopping server ..."
PID=$(ps -ef | grep $SERVER_JAR | grep -v grep | awk '{print $2}')
if [ -z "$PID" ]; then
  echo Application is already stopped
else
  echo kill $PID
  kill -9 $PID
fi

exit 0
