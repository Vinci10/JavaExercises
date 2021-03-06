Application to get parameters from command line and generate transaction in JSON,XML or Yaml format. Project is builded by Gradle, tested by JUnit and Mockito.
Moreover, application uses Spring to create beans for dependency injection and Logback to print logs in JSON format to console and save logs in actual directory to file.

Create fat-jar:
```
gradle fatJar
```

Exemplary triggering (-format=[xml|json|yaml], default: json):
```
java -jar transaction-generator.jar -customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" 
-itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 1000 -outDir ./output -format xml
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

Before build an image from a Dockerfile create fat-jar:
```
gradle fatJar
docker build --tag transaction-generator .
```

There are 3 additional parameters to send transactions to ActiveMQ message broker:

-broker - broker address

-queue - queue name

-topic - topic name

Exemplary triggering:
```
java -jar transaction-generator.jar -itemsFile ~/items.csv -customerIds 1:1 -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" -itemsCount 1:2 -itemsQuantity 1:2 -outDir ./output -eventsCount 1 -format xml -broker tcp://localhost:61616 -queue transactions-queue -topic transaction-topics
```
