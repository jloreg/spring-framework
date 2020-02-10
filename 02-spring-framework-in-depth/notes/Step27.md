## Step 27 - Spring Unit Testing with a Java Context

With the @ContextConfiguration annotation, we must define the main application class (classes=SpringApplication.class), that contains the @Configuration and @ComponentScan annotations that says to Spring, what is the class that contains the Java configuration context, and the base package to scan for beans and dependencies.

```java
package com.imh.spring.basics.springindepth.basics;
//Load the Spring context
//Runner launch the configuration app SpringApplication.class; SpringRunner.class is part of the spring-test module.
@RunWith(SpringRunner.class)	
//Here we are using classes to specifying the Java configuration file for the the context; classes that are in package com.imh.spring.basics.springindepth contains Java Context annotations.
@ContextConfiguration(classes=SpringApplication.class)
public class BinarySearchImplTest {
```

## Complete Code Example

##### pom.xml

```java
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
	<groupId>com.imh.spring.basics</groupId>
	<artifactId>spring-in-10-depth</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	
	<name>02-spring-framework-in-depth</name>
	<description>02-Spring Framework in depth</description>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>11</java.version>
	</properties>

	<dependencies>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
		</dependency>
		
		<!-- module that is present in Spring to recognice Spring framework annotations in the unit test -->		
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<scope>test</scope>
		</dependency>
		
		<!-- I load JUnit4 localy, so I don't need this dependency
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<scope>test</scope>
		</dependency>-->
		
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-core</artifactId>
			<scope>test</scope>
		</dependency>

<!-- we added the slf4j-api, and now we add a implementation for it -->
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
		</dependency>
		
		<dependency>
			<groupId>javax.inject</groupId>
			<artifactId>javax.inject</artifactId>
			<version>1</version>
		</dependency>
		
<!-- To be able to run the Spring main applications (package com.imh.spring.basics.springindepth), you must comment these lines:
		<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-test</artifactId>
	        <scope>test</scope>
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
-->
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

---

##### /src/test/java/com/imh/spring/basics/springindepth/basics/BinarySearchImplTest.java

```java
package com.imh.spring.basics.springindepth.basics;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.imh.spring.basics.springindepth.SpringApplication;

//Load the Spring context
//Runner launch the configuration app SpringApplication.class; SpringRunner.class is part of the spring-test module.
@RunWith(SpringRunner.class)	
//Here we are using classes to specifying the Java configuration file for the the context; classes that are in package com.imh.spring.basics.springindepth contains Java Context annotations.
@ContextConfiguration(classes=SpringApplication.class)
public class BinarySearchImplTest {

	//Get this bean from the context (using autowiring).
	@Autowired
	BinarySearchImpl binarySearch;
	
	@Test
	public void testBasicScenario() {
		
		//call method on binarySearch
		int actualResult = binarySearch.binarySearch(new int[]{}, 5);
		//check if the result is the expected (first argument = expected value)
		assertEquals(3, actualResult);
	}
}
```