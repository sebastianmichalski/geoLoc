# geoLoc

Small project made for an interview. The purpose of it was creating REST service, that will provide a method, that will get
coordinates (latitude and longitude) and store it in efficient way. I decided to use MongoDB as it is one of the most popular NoSQL
data sources on the market.

Technologies used during development:
* Spring Boot
* JUnit
* Mockito
* AssertJ
* Lombok
* Java 8

Prerequisites:
* MongoDB downloaded, installed, process mongod started: https://www.mongodb.com/
* Java 8 JDK installed, evirnoment variable are set up: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

## Usage

To start application using maven plugin call command as presented below:

```
mvn spring-boot:run
```

Application should start on port 8080:

```
2017-08-28 17:36:06.933  INFO 14784 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-08-28 17:36:06.988  INFO 14784 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-08-28 17:36:06.992  INFO 14784 --- [           main] com.geoloc.LocationsApplication          : Started LocationsApplication in 2.79 seconds (JVM running for 5.326)
```

To store location call REST service, ie. by curl command from terminal:

```
curl -X PUT -d lat=52.2297 -d long=21.0122 localhost:8080
```

By default, locations are stored in bulks by 10, but it can by changed in [LocationServiceImpl.java](https://github.com/shekerama/geoLoc/blob/master/src/main/java/com/geoloc/service/LocationServiceImpl.java).
