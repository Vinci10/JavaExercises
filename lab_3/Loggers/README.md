Application uses Logback to print logs and adjusts to external libraries which use log4j api and log4j2.

Create fat-jar:
```
gradle fatJar
```

Exemplary triggering:
```
java -jar loggers.jar -customerId 5 -age 27 -ticketPrice 78.5 -companyId 125
```