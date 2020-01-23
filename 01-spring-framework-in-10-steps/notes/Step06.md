## Step 6 - Dynamic auto wiring and Troubleshooting - @Primary

In the last step, we tried to understand what's happening in the background. What's happening in the background when we're running a Spring application. How Spring creates beans, how it manages dependencies and all that kind of fun stuff. We saw that we created an instance of the bubble sort bean.

Now what would happen if I put @Component on the QuicksortAlgorithm as well? So now there are two things that are matching. So now there are two components, right? So QuickSortAlgorithm is a sort algorithm and also BubbleSortAlgorithm is a sort algorithm. In the previous example we saw that autowiring was taking place by type of sort algorithm.

QuickSort is a sort algorithm, and BubbleSort also too. In the previous step, only bubble sort was having @Component. So Spring picks up bubble sort and it say there's only one component of that type.

```java
@Component
public class BubbleSortAlgorithm implements SortAlgorithm{...}
```

What did now is we added @Component on QuickSort also. So there are two components on the classpath. What does Spring do to resolve that ? Let's try that and figure out a lot of variations of this concept. Let's run this application. 

```java
@Component
public class QuickSortAlgorithm implements SortAlgorithm {...}
```

```java
2020-01-23 19:51:31.877 ERROR 5126 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in com.imh.spring.basics.springin10steps.BinarySearchImpl required a single bean, but 2 were found:
	- bubbleSortAlgorithm: defined in file [/home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps/target/classes/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.class]
	- quickSortAlgorithm: defined in file [/home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps/target/classes/com/imh/spring/basics/springin10steps/QuickSortAlgorithm.class]


Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```

Failed to start. BinarySearchImpl requires a single bean but actually two were found. So it’s saying I need one sort algorithm but you are giving me two. I can go ahead and remove the @Component from the bubble sort algorithm. So I can say OK I don't want to use you. I remove that @Component from it. So what would happen now? OK. It's using the quicksort algorithm. So that went through a fine. 

Other way you can have done that is by using something called @Primary. So now you have two components on the class path. So if you have two components on the class path of the same type then I can make one of them @Primary. You can see that I have bubble sort @Component, and I've added @Component and @Primary in QuickSort. Let's see which one is used.

```java
@Component
@Primary
public class QuickSortAlgorithm implements SortAlgorithm {...}
```

```java
//Output console
2020-01-23 20:07:18.614  INFO 5361 --- [           main] c.i.s.b.springin10steps.Application      : Started Application in 0.527 seconds (JVM running for 0.955)
com.imh.spring.basics.springin10steps.QuickSortAlgorithm@29a60c27
3
```

Ok, Spring it's using quicksort because quicksort has @Primary. If you have more than one component matching a specific type, you can use @Primary to give more importance to one of those components.

Whenever you have any problems of the type a bean is not found or multiple beans are found, see if you have used the right combination of @Component, @AutoWired and @Primary. Those are the important things you would need to know to be able to troubleshoot any problem with Spring framework.

## Complete Code Example

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