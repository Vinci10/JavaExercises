Application to get parameters from command line and generate transaction in JSON format. Project is builded by Gradle and tested by JUnit and Mockito. 

Create fat-jar:
```
gradle fatJar
```

Exemplary triggering:
```
java -jar transaction-generator.jar -customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" 
-itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 1000 -outDir ./output
```

Generate a test report and test coverage:
```
gradle test jacocoTestReport
```

Test report will be in build/reports/jacoco/test/html/index.html

Publishing to Maven local:
```
gradle publishToMavenLocal
```