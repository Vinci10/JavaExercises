from openjdk:8-jdk-alpine

WORKDIR /

ADD ./build/libs/transaction-generator.jar transaction-generator.jar
RUN mkdir /storage

ENTRYPOINT ["java", "-jar", "transaction-generator.jar", "-configFile", "./storage/generator.properties"]