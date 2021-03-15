# Upstac API


#####You can create package by running

  >mvn clean -Dmaven.test.skip package install spring-boot:repackage

#####You can create package for MySQl database by running
  >mvn clean package install spring-boot:repackage -Pprod

#####You can create package for cloud by running
  >mvn clean -Dmaven.test.skip  package install spring-boot:repackage -Pcloud



This will create a Jar file under target folder


#####You can run the app from Jar by by running 

>java -jar upstac-api-{version}.jar

This will start the server