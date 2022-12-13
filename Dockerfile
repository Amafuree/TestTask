
FROM maven as builder


RUN mkdir /test
COPY .. /test

WORKDIR /test
RUN mvn install #-Dmaven.test.skip=true

FROM openjdk:17

COPY --from=builder test/target/test-2.7.5.jar /

EXPOSE 8080
CMD java -jar test-2.7.5.jar