## Step 7 - Constructor and Setter Injection

In one of the previous steps, we discussed about constructer injection. What we're doing in here is we provided a constructor for the sort algorithm.

##### /src/main/java/com/imh/spring/basics/springin10steps/BinarySearchImpl.java

```java
@Component
public class BinarySearchImpl {
	
	@Autowired
	private SortAlgorithm sortAlgorithm;

	//Constructor injection for the sort algorithm.
	public BinarySearchImpl (SortAlgorithm sortAlgorithm) {
		super();
		this.sortAlgorithm = sortAlgorithm;
	}
}
```

So the other way you could have done this is by using something called setter injection. So I’m removing the constructor and I’m actually generating a setter.

```java
@Component
public class BinarySearchImpl {
	
	private SortAlgorithm sortAlgorithm;

	//Setter injection V1 - Setter
	@Autowired
	public void setSortAlgorithm(SortAlgorithm sortAlgorithm) {
		this.sortAlgorithm = sortAlgorithm;
	}
}
```

```java
@Component
public class BinarySearchImpl {

	//Setter injection V2 (there are any constructor implemented) - No Setter or Constructor
	@Autowired
	private SortAlgorithm sortAlgorithm;
	
}
```
So these are the three options for using autowiring: constructor, setter and, no setter or constructor. One thing you need to remember is the fact that these two are really the same (setter and, no setter or constructor). So whether you're creating a setter or not, the way Spring the autowiring is the same.

So when people say setter injection they would be either referring to any of these (Setter & No Setter or Constructor). You can even call this to be using setter injection. So if I just put out @Autowire here and remove the setter -see Setter injection V2-, this is kind of also termed to be setter injection, even though there is no setter in there.

---

Basically we have two options, right? Constructor injection and setter injection. So how do you choose between constructor and setter injection? Actually the choicee used to be really clear earlier. In the earlier version of Spring, what we used to suggest to do is all mandatory dependencies should be autowired using constructor.

```java
@Component
public class BinarySearchImpl {

	@Autowired
	private SortAlgorithm sortAlgorithm;
}
```

For example, here this is a mandatory dependency. Sort algorithm…without sort algorithm the binary search cannot work at all. If there is a dependency which it can work without **this is a mandatory dependency**, if some dependencies are not available and still the class can run, **those dependencies are optional dependencies**. So if you have mandatory dependencies then the recommendation was to use constructor injection. For all other dependencies the recommendation was to use setter injection.

So if it's optional dependency, go for setter injection, otherwise use constructor injection. That was a recommendation with the earlier versions of Spring with @Autowired, what happens is even though you're using setter injection, if a bean of @AutoWired is not found, so let's say you put @Autowired and you don't have any bean of sort algorithm present in the class path with @Component. Then what would happen? The context will not launch at all.

So now actually there is not a lot of difference in terms of mandatory or optional dependences. Typically, in these days most of the applications I’ve worked on use this kind of construction. 

```java
@Component
public class BinarySearchImpl {

	//Setter injection V2 (there are any constructor implemented) - No Setter or Constructor
	@Autowired
	private SortAlgorithm sortAlgorithm;
}
```

So what this would do is, I’d have no need to create a constructor. However, one drawback of this approach is that you can add a lot of dependencies. So, because it's easy to now add a lot of dependencies, what people tend to do is add 5 10 15 20 dependencies for a specific thing and it would become very complex to manage. The same thing, if there was a kind of constructor injection being used, I would need to keep adding stuff to the constructor and you'd see that the the constructor has 15 arguments which is really tough to maintain. So constructor injection kinds of makes those kind …all kind of problem very very obvious.

The setter injection kinds of hides those dependencies because all that need to do is very simple, right? Create another dependency and just put at autowired on top of it. Other than that right now. I mean with Spring framework as it is. There's not a great deal of difference in using setter and constructor injection. The lines have really really become thin between constructor and setter injections.

```java
@Component
public class BinarySearchImpl {

	//Setter injection V2 (there are any constructor implemented) - No Setter or Constructor
	@Autowired
	private SortAlgorithm sortAlgorithm;
	//Added another dependency
	@Autowired
	private ChaosAlgorithm stupidAlgorithm;
	
}
```
 Whichever option you pick I don't think you would be doing very bad.