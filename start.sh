gradle clean
gradle bootJar
java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 build/libs/weblib.jar