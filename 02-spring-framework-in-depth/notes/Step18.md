## Step 18 - Container and Dependency Injection (CDI) - @Named, @Inject

### CDI

* JavaEE Dependency Injection Standard (JSR-330)
* Spring Supports most annotations
	* @Inject (@Autowired)
	* @Named (@Component & @Qualifier)
	* @Singleton (Defines a scope of Singleton)

Let's look at something called CDI, Context and Dependency Injection. If you have hear about this thing called Context and Dependency Injection and were wondering what it is all about.

Spring Framework introduced  this concept called Dependency Injection. And also,  it introduced something called IoC Container, Inversion of Control container. So, CDI tries to  standarize this as part of the JAVA EE. So, CDI is like the interface. It defines the different annotations. So it says @Inject, @Named, @Singleton. These are the different annotations, which needs to be used. And this CDI implementation frameworks would provide the functionallity.

A comparable thing would be JPA and Hibernate. So JPA, is an interface. Java Persistence API defines the API, and Hibernate understands the API, which is defined by JPA and provides the implementation for that.  Similar to tha, TDA is a interface defining how to do Dependency Injection. It says, if I want to auto-wiring something in, then you'd need to use @Inject. If you'd want to do @Named, that means you are defining a component.  So the comparable Spring annotation for CDI @Named, is @Component and the comparable for @Inject is @Autowired.

So, CDI tries to define common annotations to use with Dependency Injection. Spring supports almost all the features of containers and Dependency Injection. So, instead of using this Spring annotations @Autowired, you can use the annotation @Inject. Instead of using @Component, I can use @Named.

Let's add CDI dependencies to the pom.xml and code some examples -see Complete Code Example section-. 

```xml
<dependency>
	<groupId>javax.inject</groupId>
	<artifactId>javax.inject</artifactId>
	<version>1</version>
</dependency>
```
The annotations which are provided by CDI are really simple ones, and Spring provides the implementations for those annotations as well. So in addition to the Spring annotations, you can also use the CDI annotations. Now, the most difficult question to answer is whether you should use CDI or whether you should use Spring. 

I think it's a tricky question to answer. CDI is a JavaEE standard, so you are talking about using something which is really a standard, which is something which a lot people would like to do right, so you always want to stick to a standard, so instead of using Hibernate you would want to use a JPA, and then use Hibernat as the implementation. So similar to that, maybe you'd wanted to use a CDI to provide you with annotations, and you can use Spring as the implementation to it. 

As far as I'm concerned there is not a situation where all the advantage is loaded on one side, and I don't really have a favor with either of those; whether you want to use CDI annotations or Spring annotations, you can go ahead and use them, because you are going to use Spring as the implementation anyway, so it doesn't really matter.


## Complete Code Example

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
		<java.version>11</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
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
		
		<dependency> 	<!-- CDI dependencies -->
			<groupId>javax.inject</groupId>
			<artifactId>javax.inject</artifactId>
			<version>1</version>
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

##### /src/main/java/com/imh/spring/basics/springindepth/CdiApplication.java

```java
@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class CdiApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(CdiApplication.class);

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				CdiApplication.class)) {
			
			SomeCdiBusiness business = applicationContext.getBean(SomeCdiBusiness.class);

			LOGGER.info("{} dao-{}", business, business.getSomeCDIDAO());
		}
	}
}
```
---

##### /src/main/java/com/imh/spring/basics/springindepth/cdi/SomeCdiBusiness.java

```java
package com.imh.spring.basics.springindepth.cdi;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;

@Named		//Instead of @Component it's called @Named
@Singleton	//Instead of @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) it's called @Singleton
public class SomeCdiBusiness {

	@Inject	//Instead of @Autowired it's called @Inject
	SomeCdiDao someCdiDao;

	public SomeCdiDao getSomeCDIDAO() {
		return someCdiDao;
	}

	public void setSomeCDIDAO(SomeCdiDao someCdiDao) {
		this.someCdiDao = someCdiDao;
	}

	public int findGreatest() {
		int greatest = Integer.MIN_VALUE;
		int[] data = someCdiDao.getData();
		for (int value : data) {
			if (value > greatest) {
				greatest = value;
			}
		}
		return greatest;
	}
}
```
---

##### /src/main/java/com/imh/spring/basics/springindepth/cdi/SomeCdiDao.java

```java
package com.imh.spring.basics.springindepth.cdi;

import javax.inject.Named;
import javax.inject.Singleton;

@Named		//Instead of @Component it's called @Named
@Singleton	//Instead of @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) it's called @Singleton
public class SomeCdiDao {
	
	public int[] getData() {
		return new int[] {5, 89,100};
	}

}
```