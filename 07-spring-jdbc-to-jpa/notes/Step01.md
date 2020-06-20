## Step 01 - Setting up a project with JDBC, JPA, H2 and Web Dependencies

In the first step, we will set up a simple project using [Spring Initializr Web](https://start.spring.io/) and create a Spring project with the next dependencies: JDBC, JPA, H2 (in-memory database), and Web.

As far as the dependencies are concerned, the dependencies we would be using are, first we are going to start with JDBC, later we'll use JPA to develop the same application and in-memory database called H2 to persist the data; H2 doesn't really need set up anything, so H2 it's a very easy database to use if you are interested to migrate to something like MySQL. Other than that, we would want to look at H2 in something called a web console. So H2 offers a client, and we can use a web console to look at the data which is present in H2, so I want to add on the project the Web dependency as well.

So the dependencies which we are adding in our project are JDBC, JPA, H2, and Web. Make sure that you have all these four dependencies added in the pom.xml.

The other important things are to use the latest version of Spring Boot and make sure that you're using Maven project with Java.

What we'll do before we end this step is to run the application SpringDatabaseApplication. This would launch up a simple web application. We did not really write anything yet. but I'm just trying to make sure that everything is working fine. 

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
	<groupId>com.imh.spring.database</groupId>
	<artifactId>spring-jdbc-to-jpa</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>05-spring-jdbc-to-jpa</name>
	<description>05 - Spring Jdbc to Jpa</description>

	<properties>
		<java.version>11</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
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


##### /src/main/java/com/imh/spring/database/databasedemo/SpringDatabaseApplication.java

```java
package com.imh.spring.database.databasedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseApplication.class, args);
	}

}
```