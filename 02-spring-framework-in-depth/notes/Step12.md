## Step 12 - Autowiring in Depth - byType @Primary, byName and Constructor

* byType
* byName
* constructor - similar to byType, but through constructor

### Approach number one. Autowiring byType; setter injection through reflection

**TIP**. Autowiring byType means that we use the annotation @Primary.

```java
@Component
public class BinarySearchImpl {

	@Autowired	//byType through Setter reflection
	private SortAlgorithm sortAlgorithm;
}
```

```java
@Component
@Primary
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
//Console output
com.imh.spring.basics.springindepth.BubbleSortAlgorithm@1c98290c
```

Without @Primary in any of the two classes:
```
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in com.imh.spring.basics.springindepth.BinarySearchImpl required a single bean, but 2 were found:
	- bubbleSortAlgorithm: defined in file [/home/jon/spring-workspace/joan/spring-framework/02-spring-framework-in-depth/target/classes/com/imh/spring/basics/springindepth/BubbleSortAlgorithm.class]
	- quickSortAlgorithm: defined in file [/home/jon/spring-workspace/joan/spring-framework/02-spring-framework-in-depth/target/classes/com/imh/spring/basics/springindepth/QuickSortAlgorithm.class]


Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```

### Approach number one. Autowiring byType; setter injection

```java
@Component
public class BinarySearchImpl {

	private SortAlgorithm sortAlgorithm;
		
	@Autowired	//byType through Setter injection
	public void setSortAlgorithm (SortAlgorithm sortAlgorithm) {
		this.sortAlgorithm = sortAlgorithm;
	}
}
```

Important. Needs @Primary annotation in one of the two classes, BubbleSortAlgorithm or  QuickSortAlgorithm.

### Approach number two.  Autowiring byName; setter injection through reflection

```java
@Component
public class BinarySearchImpl {

	@Autowired //setter injection through reflection
	private SortAlgorithm quickSortAlgorithm;
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
com.imh.spring.basics.springin10steps.QuickSortAlgorithm@32cb636e
```
Caution. If we add @Primary in the BubbleSortAlgorithm class, @Primary has higher priority over autowiring byName:
```
//Console output:
com.imh.spring.basics.springindepth.BubbleSortAlgorithm@1c98290c
```

### Approach number three. Constructor injection; similar to byType, but through constructor

```java
@Component
public class BinarySearchImpl {
	
	private SortAlgorithm sortAlgorithm;
	
	@Autowired	
	public BinarySearchImpl (SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm;
	}
}
```

Important. Needs @Primary annotation in one of the two classes, BubbleSortAlgorithm or  QuickSortAlgorithm.
```
Console output:
com.imh.spring.basics.springin10steps.QuickSortAlgorithm@32cb636e
```
### Constructor vs Setter Injection

When you talk about dependencies there are basically two types of dependencies. One is call mandatory dependencies and the other one are optional dependencies.

In the code below, SortAlgorithm is a mandatory dependency, because the BinarySearchImpl need this SortAlgorithm to be there. So only when SortAlgorithm is there will be able to take care of its work. 

```java
@Component
public class BinarySearchImpl {
	
	private SortAlgorithm sortAlgorithm;
		
	@Autowired	//byType through constructor injection
	public BinarySearchImpl (SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm;
	}
}
```

There might be things like internationalization and things like that. So if you have an dependencies like that, those are not really mandatory. So  if you don't internationalize something, probably you can use the default and show them to the user. So those are not mandatory. Those are what called optional dependencies.

[Spring team actually advocates](https://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/) using Spring Constructor injection, so they say use constructor injection always. For me, the most important things is use:

* Constructor injection for Mandatory Dependencies.
* Setter injection for Optional Dependencies.

If you have optional dependencies whether  they're there or not it doesn't really matter. So, you can use setter injection for them. You can use constructor injection for mandatory dependencies. 

The most important advantage of a constructor injection is the fact that the entire bean is created at one instance. **So you're creating basically a inmutable bean, you're not changing the bean at multiple times**; is something which you'd want to promote in your application.

However, when you use setter injection you would first create a bean using the default constructor, then called the setter method on this dependency (SortAlgorithm sortAlgorithm). In the code below, you're changing the state of the bean.

```java
@Component
public class BinarySearchImpl {
	
	private SortAlgorithm sortAlgorithm;
	
	@Autowired	//byType through Setter injection
	public void setSortAlgorithm (SortAlgorithm sortAlgorithm) {
		this.sortAlgorithm = sortAlgorithm;
	}
}
```

That's one of the important reasons why constructor injection is prefered, becuase it helps us create inmutable bean and inmutable is something which you'd want to promote in your application. 

The second important think why constructor injection is preferred, is because if you have constructor injection defining all the dependencies, then what would happen? then you would have 15 arguments in your constructor, so you are having too many dependencies in your constructor, so you can directly identify that there is a problem with your code. That means you need to refactor the code to something like that:

```java
@Component
public class BinarySearchImpl {
	
	//Inmutable properties use injection via constructor injection
	private SortAlgorithm sortAlgorithm;
	
	//Not inmutable Properties use injection via setter reflection
	@Autowired
	private Type property2;
	@Autowired
	private Type property3;
//	@Autowired properties from 4..14
//	private Type propertyX;
	@Autowired
	private Type property15
	
	@Autowired	//byType through constructor injection
	public BinarySearchImpl (SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm;
	}
}
```