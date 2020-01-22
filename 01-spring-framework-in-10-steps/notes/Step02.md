## Step 2 - Understanding Tight Coupling using the Binary Search Algorithm Example

If you are familiar with programming you know what a search is, right? So you're given a number, you're given an array, and finally you try to find the number in an array. For example, here I would want to search this array for this number.

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java

```java
public static void main(String[] args) {
	BinarySearchImpl binarySearchImpl = new BinarySearchImpl();
	int result = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
	System.out.println(result);
		
	SpringApplication.run(Application.class, args);
}
```

A binary search is a specific search algorithm which expects the array to be sorted. For binary search, it's not OK if I send an array like this. What I would need to do is to sort the array, and then I would be able to search through that. In this step, we will set up a simple example using Spring Boot to be able to do a binary search. The objective is to understand the concepts of tight coupling and dependency injection.

One of the things that you need to remember, is we are not really going to implement the entire logic for binary search. I would just implement high level algorithm if you can call it that way, just to get you understanding about tight coupling and loose coupling.

##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java	
public class BinarySearchImpl {
	
	public int binarySearch (int[] numbers, int numberTo) { 
		//Implementing Sorting Logic
		//Bubble Sort Algorithm
		//Search the array
		return 3;
	}
}
```
Now we have implemented a simple dummy method for doing a binary search. Let’s say this is using, the sorting logic is implemented by using bubble sort algorithm. You don't need to really worry about what the bubble sort algorithm is. It's one of the algorithm. So let's say I have bubble sort algorithm which is used in here.This logic of binary search is tightly coupled to the bubble sort algorithm.

What do I mean by that is that if I want to change the algorithm then I would need to change the code of the binarySearch method. That's what tight coupling means. This binary search Impl is tightly coupled to the bubble sort algorithm which is present in here, and that's not really good.

 Another scenario. Let's say over a period of time things evolve and there's a new sorting algorithm. I'd want to switch this to quicksort, then I'd need to change this piece of code to be able to use quicksort. Sometimes I want to use bubble sort, and sometimes I would want to use quicksort in the binarySearch method. How do I separate them out ? How do I make them loosely coupled ?
  
 One of the things that we can do is we can actually start implementing a algorithm for bubble sort outside. So let's start with a simple thing. Let’s start with taking bubble sort outside this search. So how do I do that ? What I would do is actually start with creating a new class BubbleSortAlgorithm.
 
##### /src/main/java/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.java

```java	
public class BubbleSortAlgorithm {

	public int[] sort(int [] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```
The sorting algorithm of bubble sort is outside the ambit of binary search, so if any time there is a change in logic of bubble sort all they wanted to do is change the logic in the BubbleSortAlgorithm class. Nothing else needs to change.
 
 So now this starting logic is moved out. I can just implement the binary search logic here and return the index back.
 
##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java	
public class BinarySearchImpl {
	
	public int binarySearch (int[] numbers, int numberTo) { 
		BubbleSortAlgorithm bubbleSortAlgorithm = new BubbleSortAlgorithm();
		int[] sortedNumbers = bubbleSortAlgorithm.sort(numbers);
		
		//Logic here and return the index
		return 3;
	}
}
```

I have not solved the problem of being able to change our sort algorithm dynamically. So I would want to be able to run this binary search with different sorting algorithm.  But still I have the problem of not being able to change the algorithm. So let's say I quickly create a quick sort algorithm.

##### /src/main/java/com/imh/spring/basics/springin10steps/QuickSortAlgorithm.java

```java	
public class QuickSortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}
```
I'm not going to change anything in here I'll just leave the logic as it is and just let's say the logic for quick sort is what is being implemented in here. 

So now I would want to be able to switch between quicksort and the bubble sort algorithms. Dynamically. I would want the search algorithm, the binary search algorithm to be more loosely coupled I would want to be able to pass in the sort algorithm in. How I do that? Just think about it and we will find the solution for it in the next step. This is more of a java problem.

---

#### Notes

* Typically input array to Binary Search is sorted ahead of time. In this example, we asume Binary Search is responsible for Sorting. 
 * Why to sort repeatedly for each search ?. Exercise: How do we make this algorithm more efficient ?

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

### /src/main/java/com/imh/spring/basics/springin10steps/Application.java

```java
package com.imh.spring.basics.springin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		BinarySearchImpl binarySearchImpl = new BinarySearchImpl();
		int result = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
		
		SpringApplication.run(Application.class, args);
	}
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java
package com.imh.spring.basics.springin10steps;

public class BinarySearchImpl {
	
	public int binarySearch (int[] numbers, int numberTo) {
		
		BubbleSortAlgorithm bubbleSortAlgorithm = new BubbleSortAlgorithm();
		int[] sortedNumbers = bubbleSortAlgorithm.sort(numbers);
		
		return 3;
	}
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.java

```java
package com.imh.spring.basics.springin10steps;

public class BubbleSortAlgorithm {
	
	public int[] sort(int [] numbers) {
		return numbers;
	}
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/QuickSortAlgorithm.java

```java
package com.imh.spring.basics.springin10steps;

public class QuickSortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}
```
---