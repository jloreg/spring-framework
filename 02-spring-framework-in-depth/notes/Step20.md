## Step 20 - Fixing minor stuff - Add Logback and Close Application Context

In the last step we converted or SprinBoot context into a Spring context. We are now using pure Spring only code. However, there is one error that's coming up. So ApplicationContext, if you see it says, applicationContext is never closed.

```java
@Configuration
@ComponentScan		//by default it's equivalent to @ComponentScan ("com.imh.spring.basics.springindepth")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringApplication {

	public static void main(String[] args) {
		
		//Spring Application Context
		ApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringApplication.class);
		
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
		System.out.println(binarySearch);
		System.out.println(binarySearch1);

		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}
}
```
So one of the things we need to make sure is to close the ApplicationContext. The way you can do that is by doing applicationContext.close();, so that ensures that this error disappears.

```java
public static void main(String[] args) {
		
		//Spring Application Context
		ApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringApplication.class);
		
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
		System.out.println(binarySearch);
		System.out.println(binarySearch1);

		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
		
		applicationContext.close();
	}
```
The other way I could have done this, actually, instead of doing this way, I can actually use the new try-catch. So I can actually put the try catch around the pice of code which wants to be automatically closed. So, what would happen in this case ? The applicationContext would automatically close, even there is any exception in this piece of code. This is one of the coool features which is present in Java 7.

Even if there is anhy exception in this piece of code, connection would get closed because this, if you look at this, it would implement something called the AutoCloseable. Since this implements the AutoCloseable interface, we can use it in a try, and if anything goes wrong, the close method would recall, otherwise, everything it's fine.

```java
@Configuration
@ComponentScan		//by default it's equivalent to @ComponentScan ("com.imh.spring.basics.springindepth")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringApplication {

	public static void main(String[] args) {
		
		try (AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringApplication.class)) {
			
			BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
			BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
			System.out.println(binarySearch);
			System.out.println(binarySearch1);

			int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
			System.out.println(result);
		}
	}
}
``` 
The other thing which I noticed when I was migrating the other stuff, so when I was actually removing this SpringBoot and using SpringCdiApplication and other stuff, is LOGGER.info was not working fine. This is because we added in the pom.xml. In the pom.xml in the previous step, we added in slf4j-api, but we did not add any implementation for it. One need to make sure is to add an implementation. The looger implementation we will use is logback-classic, that's the one which Spring uses by default, so let's add in as well.

```xml
	<dependency>
		<groupId>ch.qos.logback</groupId>
		<artifactId>logback-classic</artifactId>
	</dependency>
```

## Notes

**Important**. To be able to run the Spring main applications (package com.imh.spring.basics.springindepth), you must comment these lines in the pom.xml:
```xml
<!-- 
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
```

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

		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
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

##### /src/main/java/com/imh/spring/basics/springindepth/SpringApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.imh.spring.basics.springindepth.basics.BinarySearchImpl;

@Configuration
@ComponentScan		//by default it's equivalent to @ComponentScan ("com.imh.spring.basics.springindepth")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringApplication {

	public static void main(String[] args) {
		
		try (AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringApplication.class)) {
			
			BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
			BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
			System.out.println(binarySearch);
			System.out.println(binarySearch1);

			int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
			System.out.println(result);
		}
	}
}
```
---

##### /src/main/java/com/imh/spring/basics/springindepth/SpringCdiApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.imh.spring.basics.springindepth.cdi.SomeCdiBusiness;

@Configuration
@ComponentScan										//by default it's equivalent to @ComponentScan ("com.imh.spring.basics.springindepth")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringCdiApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringCdiApplication.class);

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringCdiApplication.class)) {
			
			SomeCdiBusiness business = applicationContext.getBean(SomeCdiBusiness.class);

			LOGGER.info("{} dao-{}", business, business.getSomeCDIDAO());
		}
	}
}
```
---
 
##### /src/main/java/com/imh/spring/basics/springindepth/SpringComponentScanApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import componentscan.ComponentDAO;

@Configuration
@ComponentScan("componentscan")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringComponentScanApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringComponentScanApplication.class);

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringComponentScanApplication.class)) {
			
			ComponentDAO componentDAO = applicationContext.getBean(ComponentDAO.class);

			LOGGER.info("{}", componentDAO);
		}
	}
}
```
---

##### /src/main/java/com/imh/spring/basics/springindepth/SpringScopeApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.imh.spring.basics.springindepth.scope.PersonDAO;

@Configuration
@ComponentScan		//by default it's equivalent to @ComponentScan ("com.imh.spring.basics.springindepth")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringScopeApplication {

	//Logging with SLF4J Logger; because it's static, I would need to rename 'logger' to LOGGER 
	private static Logger LOGGER = 
			LoggerFactory.getLogger(SpringScopeApplication.class);

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringScopeApplication.class)) {
		
			//Create two instances of PersonDAO
			PersonDAO personDao = applicationContext.getBean(PersonDAO.class);
			PersonDAO personDao2 = applicationContext.getBean(PersonDAO.class);

			//"{}": this would replace whatever is in within the second argument (personDao, personDao.getJdbcConnection(), etc) 
			LOGGER.info("{}", personDao);
			LOGGER.info("{}", personDao.getJdbcConnection());	//Print Jdbc connection
			LOGGER.info("{}", personDao2);
			LOGGER.info("{}", personDao2.getJdbcConnection());
		}
	}
}
```