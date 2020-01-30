## Step 19 - Removing Spring Boot in Basic Application

Until now we have been using Spring Boot to create all our examples. However, it is very important that you understand how to use Spring without Spring Boot. We believe that great Spring developers understand how to use Spring without Spring Boot. So that's what you will start doing in this specific section.

So in this step, let's start with removing the dependency on spring-boot-starter. The spring-boot-starter is the dependency, which starts bringing in all the Spring Boot functionality.  I don't want to use Spring Boot functionality, so I'll start with step by step gettting rid of it.

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter</artifactId>
</dependency>
```

So I remove that dependency org.springframework to org.springframework, and now I'm actually changing the artifact ID to the base one of Spring. So the base thing for Spring is spring-core; spring-core is where your bean factory is defined, basic management of your beans, is defined in spring-core.

In addition to that we are making use of application context, so If I want to make use of an application context, then I would need make use of something called spring-context. So these are the two dependencies I am bringing in, and I've removed this spring-boot-starter. 

```xml
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-core</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-context</artifactId>
</dependency>
```

So let's get started with one of the main applications and see what would happen -run Application class in spring mode-

Also, we add the next dependencies.

```xml
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-api</artifactId>
</dependency>
		
<dependency>
	<groupId>javax.inject</groupId>
	<artifactId>javax.inject</artifactId>
	<version>1</version>
</dependency>
```

**Important**. The reason you are not really getting a compilation error in the main applications that use SpringBoot (package com.imh.spring.basics.springindepth.boot), is because we have a dependency on spring-boot-starter-test which we'll remove at a later point in time, and this has a scope of test. So only when running the test spring-boot-starter-test is available and one of the transititve dependencies of spring-boot-starter-test is spring-bbot-starter. In a circle of it, the @SpringBootApplication int the package com.imh.spring.basics.springindepth.boot is still available, but it's not available when I run the Java application because it's only available in the scope of test. So when I run the unit test, I would have Spring Boot application available, but when I run the Java application, you'll get a class not found exception.

```
<!--  By default version:
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
<!-- New version to load the spring-boot-starter-test to compile the SprinBoot code without errors -->
	<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-test</artifactId>
	        <scope>test</scope>
	 </dependency>
```

That's exactly what we would want, because we don't want to make use of the SpringBoot functionallity. The first thing I would need to do is to initialice a ApplicationContext. As of now, what we are doing is we are using @SpringBootApplication as the annotation to initialize the application context. This is something which is provided by Spring Boot. We don want to use that. The way you define an application confifuration in Spring is by using @Configuration. This is a Java way of doing this, we'll look at the xml way of doing that latter.
 
Now I can remove the SpringApplication.run and annotation @SpringBootApplication. In Spring, the way you would usually create an application context is by using a class which is called AnnotationConfigApplicationContext to create the applicationContext, we need to create a new instance of it and pass in the application context class (SpringApplication.class).

SpringBoot automatically defines a @ComponentScan on the package where the configuration is present. But by default Spring does not do that, so you have to do that with @ComponentScan; this means component scan on current package, or you can explicitly specify the package as well @ComponentScan (com.imh.spring.basics.springindepth), either of these ways should be fine. After that, the things remains the same as they are.

```java
//Spring version
package com.imh.spring.basics.springindepth;

@Configuration				//In Java configuration, if you want to define a spring configuration, yo use the bean @Configuration. Component
@ComponentScan 				//ComponentScan defines that this specific package needs to be scanned for components
public class SpringApplication {
		
		//Spring ApplicationContext
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringApplication.class);
}

//SpringBoot version
package com.imh.spring.basics.springindepth.boot;

@SpringBootApplication
public class SpringBootApplication {
	
	public static void main(String[] args) {
		
		//SpringBoot ApplicationContext
		ApplicationContext applicationContext = SpringApplication.run(SpringBootApplication.class, args);
}
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
		
		<dependency>
			<groupId>javax.inject</groupId>
			<artifactId>javax.inject</artifactId>
			<version>1</version>
		</dependency>

		<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-test</artifactId>
	        <scope>test</scope>
	    </dependency> 
		
<!-- By default version
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

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringScopeApplication.class);

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
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/boot/SpringBootApplication.java

```java
package com.imh.spring.basics.springindepth.boot;

import org.springframework.context.ApplicationContext;

import com.imh.spring.basics.springindepth.SpringApplication;
import com.imh.spring.basics.springindepth.basics.BinarySearchImpl;

@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringBootApplication {
	
	public static void main(String[] args) {
		
		//Spring Application Context
		ApplicationContext applicationContext = SpringApplication.run(SpringBootApplication.class, args);
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
		System.out.println(binarySearch);
		System.out.println(binarySearch1);

		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}
}
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/boot/SpringBootCdiApplication.java

```java
package com.imh.spring.basics.springindepth.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.imh.spring.basics.springindepth.cdi.SomeCdiBusiness;

@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringBootCdiApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringBootCdiApplication.class);

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringBootCdiApplication.class)) {
			
			SomeCdiBusiness business = applicationContext.getBean(SomeCdiBusiness.class);

			LOGGER.info("{} dao-{}", business, business.getSomeCDIDAO());
		}
	}
}
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/boot/SpringBootComponentScanApplication.java

```java
package com.imh.spring.basics.springindepth.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import componentscan.ComponentDAO;

@SpringBootApplication
@ComponentScan("componentscan")
public class SpringBootComponentScanApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringBootComponentScanApplication.class);

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringBootComponentScanApplication.class)) {
			ComponentDAO componentDAO = applicationContext.getBean(ComponentDAO.class);

			LOGGER.info("{}", componentDAO);
		}
	}
}
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/boot/SpringBootScopeApplication.java

```java
package com.imh.spring.basics.springindepth.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.imh.spring.basics.springindepth.scope.PersonDAO;

@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//By default
public class SpringBootScopeApplication {

	//Logging with SLF4J Logger; because it's static, I would need to rename 'logger' to LOGGER 
	private static Logger LOGGER = 
			LoggerFactory.getLogger(SpringBootScopeApplication.class);

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringBootScopeApplication.class);

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
```