#!/bin/sh

PROFILE="${defaultProfileName}"  # 启用的配置文件名称
JAVA_OPTS="-server -Xms1g -Xmx1g -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=logs"
if [ -n "$1" ]
then
   nohup java $JAVA_OPTS -jar  $1 --spring.profiles.active=$PROFILE>/dev/null 2>&1 &
else
   echo "*******************************************"
   echo "*          未填写启动jar包名称            *"
   echo "*  启动示例如下(jar包名称以实际部署为准)  *"
   echo "*                                         *"
   echo -e "*  \e[1m\e[92m ./start.sh  demo-console-XXX.jar \e[0m  *"
   echo "*                                         *"
   echo "*******************************************"
fi
