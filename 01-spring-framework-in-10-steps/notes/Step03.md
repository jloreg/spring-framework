## Step 3 - Making the Binary Search Algorithm Example Loosely Coupled

In the previous step we created two simple algorithms, Bubble sort algorithm and quicksort algorithm. In the binary search we are currently using the bubble sort algorithm, and we’re now searching for a way where I would be able to make the binary search use the algorithm I would want to configure.

##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java
public class BinarySearchImpl {

	public int binarySearch (int[] numbers, int numberTo) {
		
		BubbleSortAlgorithm bubbleSortAlgorithm = new BubbleSortAlgorithm();
		int[] sortedNumbers = bubbleSortAlgorithm.sort(numbers);
		
		return 3;
	}
}
```

So right now if it's hardcoded to use the bubble sort algorithm. How do I make the binary search Impl use a different algorithm? One way I can do that is just change quick sort here. 

```java
	public int binarySearch (int[] numbers, int numberTo) {
		
		QuickSortAlgorithm quickSortAlgorithm = new QuickSortAlgorithm();	//Wrong way
		int[] sortedNumbers =quickSortAlgorithm.sort(numbers);
				
		return 3;
	}
```

But that would not serve the purpose because I would not be able to dynamically determine which algorithm to use. Let's now go ahead and implement a simple solution for this. The solution obviously is interface.

I've got to bubble sort algorithm and say implements sort algorithm. 

##### /src/main/java/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.java

```java
public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int [] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```

So right now there is no interface called sort algorithm. We’ll create it right now. So I'll say create interface sort algorithm.

I would want to add a interface. I don’t want extend anything. Let's go in and finish. The sort algorithm is implemented. I would want to add one method to it. That's this.

##### /src/main/java/com/imh/spring/basics/springin10steps/SortAlgorithm.java

```java
public interface SortAlgorithm {
	public int[] sort(int [] numbers);
}
```

Now, I have the bubble sort algorithm implementing this sort method. Now, I can also make the quicksort algorithm implement this interface. So I can create quicksort algorithm implements.

##### /src/main/java/com/imh/spring/basics/springin10steps/QuickSortAlgorithm.java

```java
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}
```

Now you have quicksort algorithm and the bubble sort algorithm implementing the sort algorithm interface.

I would want to be able to make the binary search use the algorithm dynamically so I would want to be able to switch between the algorithms. How do I do that? The way I can do that is by making the binary search impl use the interface instead of the implementation.

So I'm creating an instance of the sort algorithm in here. Obviously since it's an instance I have to make it private and also create a constructor. Now I have a binary search constructor taking this sort algorithm as the input.

What I would now need to do is to switch the program to use this sort algorithm instead of the bubble sort algorithm. So now the program uses sort algorithm.

##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java
public class BinarySearchImpl {
	
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
So what I'll do also in here is a sysout the algorithm which is being made use of. So I'm doing a sysout here saying sort algorithm. We have changed the binary search impl to use the interface. 

What we're doing in here is we have now separated the binary search Impl from the sort algorithm. You can dynamically decide which algorithm you'd want the binary search Impl to use. **This is basically how you make your code loosely coupled**. When you hear people talking about interfaces making the applications loosley couple this is what they're talking about.

So now I can dynamically supply what I would want to use. Now I've created a binary search using the bubble sort algorithm, and you would see that when I execute this java application. You'd see that it's printing this in the console. Binary search is using what algorithm? It’s using the bubble sort algorithm.

If I actually want to change, let’s say I want to change quicksort all that I need to do is change it here. 

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java

```java
BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm());
```
So I'm not changing anything in the implementation of binary search Impl. Now if I run this right now what would be the output? Quicksort algorithm.

**What we have done is we have made binary search Impl independent of the sort algorithm being used**. You can create a new algorithm and you can start using binary search Impl with this. 

##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java
public class BinarySearchImpl {
	
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
**Sort algorithm is a dependency of the binary search Impl. So binary search depends on the sort algorithm**. We can make the sort algorithm as a separate dependency, but now we are passing it in to the binary search Impl. I have to still create the instance of binary search Impl and create an instance of the quicksort algorithm and pass it in. Is there a way I can avoid doing that?

##### /src/main/java/com/imh/spring/basics/springin10steps/Application.java

```java
BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new QuickSortAlgorithm());
```
That's what we would discuss in the next step where we would start bringing spring into the equation. I'm sure this might be a little slow introduction to Spring because we would really want you to understand the concepts behind Spring.

The most important concept behind Spring are dependency injection and loose coupling. And if you understand these two concepts very well, you can make the best use of the Spring framework, and that's the reason why we are giving you an introduction starting with a simple example.

Now we have made it more loosely coupled. We understood what a dependency is. We understood what loose coupling means. We are ready to start using Spring to make this example even better.

## Complete Code Example

### /src/main/java/com/imh/spring/basics/springin10steps/Application.java

```java
package com.imh.spring.basics.springin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		
		BinarySearchImpl binarySearchImpl = new BinarySearchImpl(new BubbleSortAlgorithm());
		int result = binarySearchImpl.binarySearch(new int[] {12,4,6}, 3);
		System.out.println(result);
		
//		SpringApplication.run(Application.class, args); 
/*I’ll just comment this line for now. We'll use this when we actuallyrun Spring context. For now we're not running anything related to Spring.*/
	}
}	
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java
package com.imh.spring.basics.springin10steps;

public class BinarySearchImpl {
	
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
package com.imh.spring.basics.springin10steps;

public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int [] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```
---

### /src/main/java/com/imh/spring/basics/springin10steps/QuickSortAlgorithm.java

```java
package com.imh.spring.basics.springin10steps;

public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
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