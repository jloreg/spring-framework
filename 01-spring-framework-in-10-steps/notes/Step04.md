## Step 4 - Using Spring to Manage Dependencies - @Component, @Autowired

One of the important things that you would already observe is the fact that we are creating objects. We are creating an object of the algorithm, and an object of the binary search, and we are actually wiring them together and then we are invoking the binary search.

We made the binary search loosely coupled by passing the sort algorithm to use as one of the constructor arguments. It would be great actually if some framework can take control of creation of the beans and the dependencies. What we are looking at on the screen right now is a very simple example. We have one bean and one dependency.

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java
```java
com.imh.spring.basics.springin10steps;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm());
        int result = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
        System.out.println(result);

//      SpringApplication.run(Application.class, args); 
    }
}
```

But if you look at the typical application, there might be thousand beans and 3000 dependencies. You'd want some framework to manage the beans and the dependencies. What we are calling as a bean is an instance of this object, so due to I'm creating a new quicksort algorithm, that’s a bean.

```java
BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm()); //new QuickSortAlgorithm() it's a bean
```
So we would want to be able to create beans and we would want to be able to wire in the dependencies. So we want to be able to create new quicksort algorithm and wire it in into the binary search Impl. **That's called wiring**.

So we would want to be able to create beans. We would want to be able to manage and populate the dependencies.
We would want a framework to be able to do that. The Spring Framework helps us to manage beans via wiring in the dependencies and do a lot of magic.

One of the things we already know is the fact that Spring framework is already in our Maven dependencies. So
we already know that the base framework of Spring is already here. Spring core, Spring beans, Spring context is also here. We are getting these dependencies through something called Spring Boot Starter. Let's not worry about it right now.

The most important thing is the fact that Spring is already available for us to use. To be able to make best use of Spring we would need to tell it three different things. 

* One is, what are the beans? What are the different beans that Spring has to manage. 

* The second thing that you need to tell it is what are the dependencies for a bean? BinarySearchImpl, if you look at it, the dependency for it is the SortAlgorithm. So we need to be able to tell Spring that SortAlgorithm is a dependency of binary BinarySearchImpl. So that's number two.

* The third thing we need to be able to tell Spring is where to search for beans? Where to search for beans. Let's say you tell Spring that there are certain beans present so I've told Spring about the beans and the dependencies. It needs to know where to search for them. So should I search in this package or should I search it in another package? Where should I really search for the beans?

These are the three questions we would need to tell Spring about. We need to answer them for Spring so that Spring would be able to help us to wire beans together.

---

So the first thing we need to tell Spring framework is what are the different beans. To start off what we'll do is we'll use BinarySearchImpl and the BubbleSortAlgorithm. I would want to tell BinarySearchImpl and BubbleSortAlgorithm are a bean. How do I tell that? The way you tell that Spring is by adding an annotation called @Component. With @Component  I'm telling spring what are the different components to manage.

##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java
```java
@Component
public class BinarySearchImpl {...}
```

##### /src/main/java/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.java
```java
@Component
public class BubbleSortAlgorithm implements SortAlgorithm {...}
```

The second thing I have to do is what are the dependencies of the bean? We created two beans, Right? We said these are the two beans by using the @Component annotation. So inside @Component I would need to tell Spring what is a dependency. How do I tell Spring what is a dependency? The way I would do that is by adding something called @Autowired.

So I'm adding an @Autowired annotation on the SortAlgorithm. Now I'm telling that BinarySearchImpl depends on SortAlgorithm. Sort algorithm is a dependency for binary search. Bubble sort algorithm does not really have any dependency, so I don't really need to add @autowired in BubbleSortAlgorithm.

##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java
```java
com.imh.spring.basics.springin10steps;

@Component
public class BinarySearchImpl {
	
	@Autowired
	private SortAlgorithm sortAlgorithm;
	
	public BinarySearchImpl (SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm; 
	}
}
```

So the last thing that I would to tell spring usually is where to search for beans? If you look at all our classes are in the same package.com com.imh.spring.basics.springin10steps. So that's where all our beans are; the two beans that we have defined are basically the BinarySearchImpl and the BubbleSortAlgorithm. 

So we’d want Spring to search in this package, so we call that something called component scan. We would want Spring do a component scan in this particular package. How are we using Spring Boot, Spring Boot would automatically scan the package where the main application class is present. Using an @SpringBootApplication annotation, Spring automatically scans the package and the sub packages of the package where this @SpringBootApplication annotatios is in. So springin10steps, which packages is it in ? It's in this specific package.com.imh.spring.basics.springin10steps. So automatically Spring Boot would scan this package and its sub packages for the beans. So there is no need to do this because we are in the same package.

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java
```java
@SpringBootApplication
public class Application {..}
```

We have completed the first two steps . What are the beans and what are the dependencies of a bean. So now we are saying Spring is going to manage these beans. I don't need to create this:

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java
```java
BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm());
```

We said Spring Boot created for us. So we said instead of doing this creation of code, Spring would do that for us. I would want to be able to get the binary search from Spring. So I would want to get the binary search bean from Spring. How do I do that? The way you can do that is by using the Spring context. This is a line which we already commented. As we said we are going to create a lot of beans. So Spring framwork is going to create a lot of beans for us. Where are all these beans going to be present? We would create a bean for binary search, we would create a bean for quick sort algorithm, but where would all these beans be managed? These would all be managed by something called application context. Spring application context is the one which would maintain all the beans. So what we would need to do is we would need to get the bean from the application context.

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java
```java
@SpringBootApplication
public class Application {
	
	//What are the beans ?
	//What are the dependencies of a bean ?
	//Where to search for beans ? => No need
	
	public static void main(String[] args) {
		
		//BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm());
		
		//Srping Application Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
		int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
	}
}
```

The run method actually returns the application context back. So I would want to take the application context and do things with it. So what I'm doing is I'm taking the SpringApplication.ru - It’s returning me the application context-, and from the application context I would now want to get the bean with ApplicationContext.getBean. What kind of bean do we want? We would want the bean of the binary search Impl dot class. I'd want to take this and assign it to a local variable called binary search.

Take a step back and think about what are all the things that we have done in this step.

* First we told Spring what are the beans. How did we do that? @Component.
* Then we told Spring what are the dependencies of the beans. How did we do? @Autowired.
* These beans are managed by the Spring application context. So we got an application context by running the Spring Boot application class using the Spring application class.

This is by default and if you create a Spring Boot project using start.spring .io, the line SpringApplication.run(X.class, args); is by default present. So what I'm doing is I’m taking that value and assigning it to the application context. So now I have an application context. From the application context I'm getting the binary search beans. So I'm getting a binary search beans from the application context and executing it. 

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java
```java
//Spring Application Context
ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
```

For this specific example, all of these things might look complex but think about an application which has thousands of beans. The fact that you don't need to do this always and you can directly get the beans from the application context. You can let Spring take care of the dependencies and their wiring. That makes it very easy to manage your applications. We will learn about all that kind of stuff in later steps but for now let's focus on getting this thing up and running.

If we remember things fine, actually we are already printing something in binary search Impl. So we are actually printing the sort algorithm. So we are printing the sort algorithm out. And also we are actually printing the results out. So what we have done is instead of creating the instance of binary search and the quicksort algorithm ourselves and wiring them together, we made Spring to wire them for us. And we're getting the BinarySearchImpl bean from Spring and we are printing the result.

Let's see what the result would be. Run as Spring application. OK! Console. So if I see this, this is the output which is being printed. So let's copy this output out and see what's being printed. So we see that the component being used is bubble sort. That's cool because that's the ONLY one which we said Spring to manage. So we said Spring manage this component and Spring is able to find that component and wire it in.

```java
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.4.RELEASE)

2020-01-23 11:40:23.594  INFO 4861 --- [           main] c.i.s.b.springin10steps.Application      : Starting Application on laptop with PID 4861 (/home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps/target/classes started by jon in /home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps)
2020-01-23 11:40:23.595  INFO 4861 --- [           main] c.i.s.b.springin10steps.Application      : No active profile set, falling back to default profiles: default
2020-01-23 11:40:23.906  INFO 4861 --- [           main] c.i.s.b.springin10steps.Application      : Started Application in 0.476 seconds (JVM running for 0.874)
com.imh.spring.basics.springin10steps.BubbleSortAlgorithm@33352f32
3
```

And we see that the result is being printed three. What we have done in this step is we've Spring manage our beans. So instead of creating the bean for binary search and bubble sort, we made Spring to do all the magic and create the beans for us. Spring is managing the beans for us, Spring manages the dependences, injects the dependencies where ever they are needed and it manages the entire lifecycle of these beans. **That's what is called dependency management**. As far as the beans are concerned, Spring does dependency management as far as your java classes, your individual beans are concerned.

In this step, we learned how to get Spring to manage the dependencies for us. There might be a lot of questions in your mind and one of them algorithm being used is bubble sort and how do I change it to use QuickStart. And how can I do that dynamically? And there might be other questions as well. We’ll answer those questions in the subsequent steps.

## Complete Code Example

### /src/main/java/com/imh/spring/basics/springin10steps/Application.java

```java
package com.imh.spring.basics.springin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	
	//What are the beans ?
	//What are the dependencies of a bean ?
	//Where to search for beans ? => No need
	
	public static void main(String[] args) {
		
		//BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm());
		//Application Context
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

### /src/main/java/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.java

```java
import org.springframework.stereotype.Component;

@Component
public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int[] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```