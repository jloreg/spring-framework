##Spring Framework in 10 Steps
- 0000 - 01 - Section Introduction - Spring in 10 Steps
- Step 1 - Setting up a Spring Project using htttp://start.spring.io
- Step 2 - Understanding Tight Coupling using the Binary Search Algorithm Example
- Step 3 - Making the Binary Search Algorithm Example Loosely Coupled
- Step 4 - Using Spring to Manage Dependencies - @Component, @Autowired
- Step 5 - What is happening in the background?
- Step 6 - Dynamic auto wiring and Troubleshooting - @Primary
- Step 7 - Constructor and Setter Injection
- Step 8 - Spring Modules
- Step 9 - Spring Projects
- Step 10 - Why is Spring Popular?

## Notes
```
Application, BinarySearchImpl, SorthAlgorithm, BubbleSortAlgorithm, QuickSortAlgorithm,
org.springframework.boot.autoconfigure.SpringBootApplication,
org.springframework.boot.SpringApplication,
org.springframework.context.ApplicationContext,
org.springframework.stereotype.Component,
org.springframework.beans.factory.annotation.Autowired
org.springframework.context.annotation.Primary;
```

## Complete Code Example

### /pom.xml

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
	<artifactId>spring-in-10-steps</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>01-spring-framework-in-10-steps</name>
	<description>01- Spring Framework in 10 steps</description>

	<properties>
		<java.version>1.8</java.version>
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

### /src/main/resources/application.properties

```
logging.level.org.springframework=debug
```

---

### /src/main/java/com/imh/spring/basics/springin10steps/Application.java

```java
package com.imh.spring.basics.springin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
	
		//Srping Application Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java
package com.imh.spring.basics.springin10steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BinarySearchImpl {
	
	@Autowired
	private SortAlgorithm sortAlgorithm;
	
	public BinarySearchImpl (SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm;
	}
	
	public int binarySearch (int[] numbers, int numberTo) {
		int[] sortedNumbers = sortAlgorithm.sort(numbers);
		System.out.println(sortAlgorithm);
		//Search the array
		
		return 3;
	}
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/SortAlgorithm.java

```java
package com.imh.spring.basics.springin10steps;

public interface SortAlgorithm {
	public int[] sort(int [] numbers);
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.java

```java
package com.imh.spring.basics.springin10steps;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int[] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/QuickSortAlgorithm.java

```java
package com.imh.spring.basics.springin10steps;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}
```