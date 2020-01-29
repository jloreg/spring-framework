## Step 17 - Lifecycle of a Bean - @PostConstruct and @PreDestroy

Let's focus on some of the bean life cycle methods. As we understand until now, when we put a @Component on a bean, the entire life cycle of this bean will be managed by Spring. So the Spring IOC container will create a new instances of this bean, and it will also make sure that this is destroyed once it's no longer needed. So the entire life cyclce of this bean is maintained by Spring Ioc Container, Spring Inversion of Control container.

Now let's say I want to do certaing things at the creation of this particular bean (@Component BinarySearchImpl), or I would want to do certain things before the bean is destroyed. How do I do that ? That's basically what we will be discusing now.

One of the first things that this Spring IoC container does is autowire in the dependencies. So depending on whether you're using setter inejction or constructor injection, it would make sure that the appropiate thing is called and all the dependencies which have been declared are autowired.

```java
@Component
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class BinarySearchImpl {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("bubble")
	private SortAlgorithm sortAlgorithm;
}
```

So let's say you want to do something which needs all the dependencies to be populated. So you want to be sure that all the dependencies are populated, and only then, you would want to do certain actions. Those kinds of things can be performed in something called @PostConstruct.

```java
@PostConstruct
public void postConstruct() {
	logger.info("postConstruct");
}
```
What would happen whenever we define a PostConstruct method in here is, as soon as the bean is created, this PostConstruct would be called. So as soon as the bean is created and initialized with the dependencies, the PostConstruct method would be called. So if you want to do something to initialize the content of the bean, as soon as the dependencies are available, then you can use the PostConstruct method.

A similar method, which is present just before destroying, so this is called PreDestroy.

```java
@PreDestroy
public void preDestroy() {
	logger.info("preDestroy");
}
```
This is called just before the bean is demoted. Just before the bean is demoted our of the context, this specific PreDestroy method is called. So, the PreDestroy annotation is used; so this instance (BinarySearchImpl), the bean instance is bein removed from the container, and in that kind of scenario, this method would be called.

## Note

What I would recommend you to do is also try and play around by putting it in the debug mode. So you can go to the application.properties, put the logging level for all the Spring Framework to debug, and try and look at where the specific methods are being called. 

One of the exercises I would recommend you to do at this point, is put the application.properties into debug mode, and run all the three main application classes: Application, ComponentScanApplication and ScopeApplication. And look thorugh what's happening in the log. Look through the different things that the SpringFramework is doing. 

Try and figure out how these things are different for all these different application classes, and try and understand more about the Spring Framework. One of the things we really believe is in try and play around with it, try and break it. Try and remove the annotations which are presenting there, and see what is happening. Most of the learning happens when you do that kind of stuff, as well as you can look at what is happening in the log, and play around with working examples by trying to break them, will really enhance your knowledge and also help you become better Spring developer.

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
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
	public class BinarySearchImpl {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("bubble")
	private SortAlgorithm sortAlgorithm;
	
	public int binarySearch (int[] numbers, int numberTo) {
		int[] sortedNumbers = sortAlgorithm.sort(numbers);
		System.out.println(sortAlgorithm);
		//Search the array
		
		return 3;
	}
	
	@PostConstruct
	public void postConstruct() {
		logger.info("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("preDestroy");
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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bubble")
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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("quick")
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}	
```