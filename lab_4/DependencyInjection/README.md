Application uses Spring to create beans for dependency injection.

Create fat-jar:
```
gradle fatJar
```

Exemplary triggering (parameter -file is a path to csv file):
```
java -jar di.jar -file movies.txt
```