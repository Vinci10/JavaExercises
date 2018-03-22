Project is builded by Gradle. 

Create fat-jar:
```
gradle fatJar
```

#To use resource filtering to set appriopriate db.properties for switching profiles:
Create fat-jar with db.properties for dev:
```
gradle myTask -Pprofile=dev
```

Publishing to Maven local:
```
gradle publishToMavenLocal
```