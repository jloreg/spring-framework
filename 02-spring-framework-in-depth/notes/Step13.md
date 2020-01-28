## Step 13 - Autowiring in Depth - @Qualifier annotation

Another option we have to resolve multiple candidates available for auto wiring is using something called @Qualifier. You can assign a qualifier, a name to each of these algorithms.

### Auto-wiring by @Qualifier

#### @Qualifier with Constructor injection  

```java
@Component
public class BinarySearchImpl {

	@Qualifier("quick")
	private SortAlgorithm sortAlgorithm;	//We don't rename the variable, only the content of the annotation @Qualifier: "bubble" or "quick".
	
	@Autowired	
	public BinarySearchImpl (SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm;
	}
}
```

```java
@Component
@Qualifier("bubble")
@Primary			//IMPORTANT. We must declare one of them as @Primary !! 
public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int[] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```

```java
@Component
@Qualifier("quick")
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}
```

Although bubblesort has been declared as @Primary, Spring will use the instance that matches with the Qualifier ("quick"):
```
//Console output:
com.imh.spring.basics.springin10steps.QuickSortAlgorithm@32cb636e
```

#### @Qualifier with Setter injection (through reflection)

```java
@Component
public class BinarySearchImpl {
	
	@Autowired
	@Qualifier("bubble")
	private SortAlgorithm sortAlgorithm;	//We don't rename the variable, only the content of the annotation @Qualifier: "bubble" or "quick".
}
```

```java
@Component
@Qualifier("bubble")
public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int[] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```

```java
@Component
@Qualifier("quick")
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}
```

```
//Console output:
com.imh.spring.basics.springindepth.BubbleSortAlgorithm@63c5efee
```

### When to use @Qualifier or auto-wiring byName instead of @Primary
   
There are situations where I would want BubbleSort algorithm in one kind of situation, QuickSort algorithm in other kind of situations, the @Primary will not work for you because in certain situations you have to switch to the other one. So, in those kind of situations, you can use @Autowiring byName, or the @Qualifier. 

It's preferable @Autowiring byName, because it makes it very, very clear. And also, we don't need to put any @Qualifier on the algorithm itself. When I'm using @Autowiring byName, I can just rename the variable here (#), and therefore I don't need to change anything in the dependency class (BubbleSortAlgorithm or QuickSortAlgorithm). 

#### byName with Setter injection (through reflection)
```java
@Component
public class BinarySearchImpl {

	@Autowired
	private SortAlgorithm bubbleSortAlgorithm;	//Rename the variable here (#). Old name: sortAlgorithm.
}
```

```java
@Component
public class BubbleSortAlgorithm implements SortAlgorithm{

	public int[] sort(int[] numbers) {
		//Logic for Bubble sort.
		return numbers;
	}
}
```

```java
@Component 
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}
```

```
//Console output:
com.imh.spring.basics.springindepth.BubbleSortAlgorithm@63c5efee
```

**ATENTION**. @Prymary has priority over injection byName.

### @Primary as a default choice for auto-wiring

Until now, we'll have did three different options of resolving multiple candidates available for auto-wiring. We looked byName, @Primary and @Qualifier. The question is which of these should I make use of. The thing is, if there is one clear favorite among your algorithms, and the favorite thing is favorite in all the situations, then I would really recommend putting in @Primary. So, if one of these algorithms is the most important one, it's the most efficient one, then you can always say that's the @Primary you want. So, @Primary it's the most efficient way of resolving dependencies the best possible way.