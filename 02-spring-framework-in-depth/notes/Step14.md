## Step 14 - Scope of a Bean - Prototype and Singleton

Default - singleton

* singleton - One instance per Spring Context
* prototype - New bean whenever requested
* request - One bean per HTTP request. Web-aware Spring ApplicationContext.
* session - One bean per HTTP session. Web-aware Spring ApplicationContext.

The beans are created and the life-cycle is managed by this thing called container.

Singleton it's basically one instance per Spring Context. When you create ApplicationContext there's only one instance of that particular bean, that's Singleton. When you are trying to get different instances of binarySearch from the applicationContext, what we are getting is the same bean. When we are requesting the applicationContext for the same bean twice, we are getting the same instance of it back. These kind of beans are called singleton beans. In Spring, by default, any bean is a singleton. If you are requesting applicationContext ten times for a bean, you get the same bean back. That's the  default, that's singleton.


Prototype is whenever there's a request for that bean, you create a new instance for it. So in a single ApplicationContext you have hundred request to that bean. Then you'd create a hundred different instances. Whenever you're requesting a prototype bean twice, you get two different instances of that same bean.

The other scopes which are present in Spring are request and session scope. These are really useful in case of a web application. In a web application, as you will learn a little later, there is separate thing called a HTTP request. One flow of a request from the browser to the server, processed on the server, and response comes back to the browser. This is called a request. One bean is created per HTTP request. In the case of session, one bean is created for a specific user session.

Let's focus on these two things: singleton and prototype.

### Singleton - One instance per Spring Context

When you create application context there's only one instance of that particular bean, that's Singleton.

```java
@SpringBootApplication
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default !!!
public class Application {
	
	public static void main(String[] args) {
		
		//Spring Application Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
		System.out.println(binarySearch);
		System.out.println(binarySearch1);

		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}
}
```

When you're trying to get different instances of binarySearch from the applicationContext, what we are getting is the same bean. When we are requesting the applicationContext for the same bean twice, we are getting the same instances of it back; the IDs (haschcodes) is the same.

```
Console output:
com.imh.spring.basics.springindepth.BinarySearchImpl@2b58f754
com.imh.spring.basics.springindepth.BinarySearchImpl@2b58f754
```

### Prototype - New bean whenever requested

We use the annotation @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)

```
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BinarySearchImpl {

	@Autowired
	private SortAlgorithm sortAlgorithm;
	
	ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
	BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
	BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
	
	System.out.println(binarySearch);
	System.out.println(binarySearch1);
}
```

Different instances is being used. When we are asking the applicationContext two times for the BinarySearchImpl, it is returning two different instances of the bean; the IDs here (hashcodes) are different.

```
Console output:
com.imh.spring.basics.springindepth.BinarySearchImpl@1255b1d1
com.imh.spring.basics.springindepth.BinarySearchImpl@464649c
```

### Difference between Spring Singleton and Gang of Four design pattern called Singleton

* The singleton scope is the default scope in Spring.
* The Gang of Four defines Singleton as having one and only one per ClassLoader.
* Spring singleton is defined as one instance of bean definition per container; if you have multiple ApplicationContext inside the same JVM then you would have multiples instances of that particular bean. So a spring singleton is actually one instance per ApplicationContext or one instance per Container. That's the most important difference between Spring singleton and GangOfFour.

In the Gang of Four design patterns, whenever they say singleton, it basically means one singleton per JVM. Per Java Virtual Machine you have one instance of that bean. However, when we talk about singleton in Spring, it's one instance per application context.

By Gang of Four definition, even if multiple application contexts are running in the same JVM, you should just have one instance of that specific class. However, when we talk about Spring singleton, there can be one instance of that class per one application context. So if there are five application contexts, that are running in the same JVM, then if I have Spring beans of scope singleton then I'll have five instances. However if you are having a singleton which meets the definition of Gang of Four design pattern singleton, there will only be one instance of it.

You need to keep this difference in mind, and when we talk about application context, bean factory, and IOC.

## Complete Code Example

##### /src/main/java/com/imh/spring/basics/springindepth/Application.java

```java
package com.imh.spring.basics.springindepth;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.imh.spring.basics.springindepth.basics.BinarySearchImpl;

@SpringBootApplication
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Application {
	
	public static void main(String[] args) {
		
		//Spring Application Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
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

##### /src/main/java/com/imh/spring/basics/springindepth/basics/BinarySearchImpl.java

```java
package com.imh.spring.basics.springindepth.basics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BinarySearchImpl {

	@Autowired
	private SortAlgorithm sortAlgorithm;
	
	public int binarySearch (int[] numbers, int numberTo) {
		int[] sortedNumbers = sortAlgorithm.sort(numbers);
		System.out.println(sortAlgorithm);
		//Search the array
		
		return 3;
	}
}
```
---

##### /src/main/java/com/imh/spring/basics/springindepth/basics/SortAlgorithm.java

```java
package com.imh.spring.basics.springindepth.basics;

public interface SortAlgorithm {
	public int[] sort(int [] numbers);
}	
```
---

##### /src/main/java/com/imh/spring/basics/springindepth/basics/BubbleSortAlgorithm.java

```java
package com.imh.spring.basics.springindepth.basics;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int[] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```
---

##### /src/main/java/com/imh/spring/basics/springindepth/basics/QuickSortAlgorithm.java

```java
package com.imh.spring.basics.springindepth.basics;

import org.springframework.stereotype.Component;

@Component
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}	
```