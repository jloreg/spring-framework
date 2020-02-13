## Step 9 - Spring Boot Actuator

In this step we will investigate much more about *spring-boot-actuator*. What does the actuator bring in ? actually brings in a lot of monitoring  around your application; if you need any such information, there is one place you can go, that's actuator. So in  actuator you'd be able to read a lot of data about the application. Let's say you want to see what are the beans that are configured, how to auto-configuration has worked. You want to see how many times the specific services you want to see how many times a specific service has failed. All that kind of stuff you can check in your actuator, and all that I need to do to enable that is adding in pom.xml something called *spring-boot-actuator*

Spring Boot actuator actually exposes a lot of rest services, and these services are compliant with a standard called hal. And we would use a hal-browser so that we can browse through the data which is provided by this service (actuator); once we add the dependency fot that browser *spring-data-rest-hal-browser*, would be configured among our dependencies.

##### pom.xml

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-rest-hal-browser</artifactId>
		</dependency>
```

One of the important things to remember is that the actuator you're using have been changing quite a lot across spring boot releases. In Spring Boot. X the URL was the *localhost:8080/actuator* and later in the earlier versions of Spring Boot it was *localhost:8080/application*, no again are back to normal *localhost:8080/actuator*. Once you type localhost:8080/actuator this is what you'd be seeing in the web browser.

```	
_links	
self	
href	"http://localhost:8080/actuator"
templated	false
health	
href	"http://localhost:8080/actuator/health"
templated	false
health-path	
href	"http://localhost:8080/actuator/health/{*path}"
templated	true
info	
href	"http://localhost:8080/actuator/info"
templated	false
```

If I click in "http://localhost:8080/actuator/health/" you see they held up the applications up and running. If I click in "http://localhost:8080/actuator/info", there is not  a lot of info here right now, there is a lot of stuff that actuator provides to enable that you'd need to configure property. How do I do that ? the way you can do that, is go to src/main/resources/application.properties and add *management.endpoints.web.exposure.include=**. What we are doing in here is we are enabling the web exposure, so the exposure.

##### src/main/resources/application.properties

```java
management.endpoints.web.exposure.include=*
```

One of the important things to remember is when you enable a lot of tracking, a lot of auditing, what would happen is there would be a performance impact as well. One of the recommendations is to identify which of the things that you would want and only enable those. But for now we are taking a shortcut and enabling everything management endpoints web exposure, that include all physical stuff. One of the things you already notice is the fact that if I type in control+comment space you'd see a drop-down menu coming in; this is because of something called a **Spring Tools Eclipse Plugin**, with this plugin you can see all the valid properties, and also validates the values of these properties and things like that. We configured management on end points web.exposure, and now we can save it an restart your application to make sure that you stop and start your application; make sure that you stop and start your application, and go and refresh the actuator in the web browser "http://localhost:8080/actuator/". You'd see that now, there are many more URL which are present in here.

```
_links	
self	
href	"http://localhost:8080/actuator"
templated	false
beans	
href	"http://localhost:8080/actuator/beans"
templated	false
caches-cache	
href	"http://localhost:8080/actuator/caches/{cache}"
templated	true
caches	
href	"http://localhost:8080/actuator/caches"
templated	false
health	
href	"http://localhost:8080/actuator/health"
templated	false
health-path	
href	"http://localhost:8080/actuator/health/{*path}"
templated	true
info	
href	"http://localhost:8080/actuator/info"
templated	false
conditions	
href	"http://localhost:8080/actuator/conditions"
templated	false
configprops	
href	"http://localhost:8080/actuator/configprops"
templated	false
env	
href	"http://localhost:8080/actuator/env"
templated	false
env-toMatch	
href	"http://localhost:8080/actuator/env/{toMatch}"
templated	true
loggers	
href	"http://localhost:8080/actuator/loggers"
templated	false
loggers-name	
href	"http://localhost:8080/actuator/loggers/{name}"
templated	true
heapdump	
href	"http://localhost:8080/actuator/heapdump"
templated	false
threaddump	
href	"http://localhost:8080/actuator/threaddump"
templated	false
metrics	
href	"http://localhost:8080/actuator/metrics"
templated	false
metrics-requiredMetricName	
href	"http://localhost:8080/actuator/metrics/{requiredMetricName}"
templated	true
scheduledtasks	
href	"http://localhost:8080/actuator/scheduledtasks"
templated	false
mappings	
href	"http://localhost:8080/actuator/mappings"
templated	false
```
So you would see a lot of URL which are coming up in here. The other thing we can type in this localhost is the whole browser that we have already added http://localhost:8080/browser/index.html#/. We can use the help browser through the actuators; If I type "/actuator", I can start looking through all the things related to actuators directly from here. 

Let's look at a few of these things that the actuator is exposing. The "auditevents" it is related to security, is that you want to show the users who where properly validated, how many people failed the authentication and all that kind of stuff. Beans is another interesting thing; picking "beans" the browser show all the spring beans that are configurated and it's dependencies. "Conditions" is another interesting thing, as we have discussed until now is all about auto-configuration; it would draw a list of beans which are configured, which are not trigger, and all that kind of stuff. And also you can get a few metrics as well, so if you go to the "metrics", I can see a list of metrics which are valid; JVM memory used, CPU usage, etc. I can also see a interesting thing called "httptrace" which shows all the requests that were executed. The last one which is present in here is mappings, "mapping" shows all the different things that are mapped to our eyes. Right now where we are creating a web application or a web services, we are mapping a lot of URLs. 

Note. The important thing is to remember that this would have a performance impact. **You cannot enable it in production**. This is useful to trace a few request but obviously you would not want to enable it to decrease in production for all the request.

## Complete Code Example

##### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.imh.springboot.basics</groupId>
	<artifactId>springboot-in-10-steps</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>03-spring-boot-introduction-in-10-steps</name>
	<description>03 - Spring Boot in 10 steps</description>

	<properties>
		<java.version>11</java.version>
	</properties>

	<dependencies>

		<!-- Comment this dependency if you are going to use the web application as main application -WebApplication.java-
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency> -->
		
		<!-- SpringBoot Starter Web; preferred starter for Spring Boot to develop web applications as well as RESTful web services.
		     Comment this dependency if you are going to use the local application as main application -Aplication.java- -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<!--SpringBoot Starter JPA provides interface for hibernate; JPA defines how ORM applications or ORM frameworks should do 
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>-->
		
		<!--SpringBoot Starter Actuator provides monitoring for all the services loaded in the project application -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		<!--SpringBoot Starter data-rest-hal Browser provides a browser to see the data which is provided by the actuator -->
		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-rest-hal-browser</artifactId>
		</dependency>
		
		<!-- SpringBoot Starter Test enables you to write unite test and integration test as well -->  
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```

##### src/main/resources/application.properties

```
management.endpoints.web.exposure.include=*
```