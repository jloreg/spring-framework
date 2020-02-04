## Step 25 - @Component vs @Service vs @Repository vs @Controller

### Component Annotations

* @Component - Generic Component
* @Repository - encapsulating storage, retrieval, and search behavior typically from a relational database
* @Service - Business Service Facade
* @Controller - Controller in MVC pattern

There are a variety of component annotations that we can use. Until now, we have been using @Component to delclare all our components.

However, there are other options present @Controller, @Service, @Repository and other stuff. So what is the difference between them and when do you use what ? Thatś basically what we want to understand in this step. If you look all at all the examples we have used until now (BinarySearchImpl), we are using @Component, if you look at this Stereotype package (org.springframework.stereotype.Repository), there are other stuff, Component, Controller, and Repository and Service. So @Component, @Controller, @Repository, and @Service. These are the different Stereotypes that are present in Spring. So when do you use which one ? That's the question that we will try to answer now.

Whenever we talk about web applications or any applications for that matter. There would be a web layer or the UI layer, which kind of shows the information to the user, and the business layer is responsible for the business logic, and the data layer is responsible for getting the data. So it might be from a database or an external interface. Some of the people have an external interface layer, which talks to the external applications.

@Component is very generic, so in any of these stuff (web, business, anda data layer),** if you are not sure if something belongs to one of the layers, then you can use an @Component**. However, the other three annotations are very, very specific, so when you talk about @Controller, we will look at it much more in the Spring MVC section of this course where we talk about web application. 

@Controller is used to define a controller in the web layer. We'll discuss about the MVC pattern, model view controller pattern in the web application section of this course. We'll understand that the controller plays a crucial role in making sure that the model is populated and the right view is rendered. When it comes to  the Business layer, we are supposed to use the @Service annotation. When it comes to the Data layer, we are supposed to use the @Repository annotation.

![Layers](/home/jon/spring-workspace/spring-framework/02-spring-framework-in-depth/images/layers.png)

So basically, except for @Component the remaining three annotations (Repository, Service, Controller), are specific to the layer (Web, Business, and Data layer, respectively).So @Controller is in the UI layer, @Service is in the Business Services, @Repositoryis the ones that get the data from the database -if you read the text out here it says, "encapsulating storage, retrieval, and search behaviour typically from relational database"- so it's typically from a relational database, but also it can be from a big data store, or anywhere you want to get the data from. So @Respository is related to getting the data, @Service is related to business logic, and @Controller is related to the controller in the MVC pattern.

Let's look an exampled, now let's go into the scope package com.imh.spring.basics.springindepth.scope. The PersonDao class if you look at it at right way is @Component, but the DAO is supposed to be getting the data from the database, so it should be @Repository. 

Now If I run the SpringScopeApplication, you'll see that it is working as usual. Also, if we look at the other stuff which is present in package com.imh.spring.basics.springindepth.xml, due to we are depending on XML configuration, we are not really using annotations, so thatś not really a place where we can use our annotations in.

We have the @ComponentDAO in package componentscan, this can also be @Repository because it is getting data from the database. If we look at the basic example that we were using the BinarySearchImpl, this is business logic, so this kind of determines how to do the search, that's the kind of business logic, so you can actually use @Service in here, and even the algorithms are kinds of services, so you can actually call them @Services as well. So anything in the business logic, anything that provides business logic is @Service, @Service and anything that is kind of talking to the database is @Repository.

You would see that when you run this example, there is no chage in terms of functionallity as such. So if I run the basic application right now, it would run as usual perfectly fine. 

So, now the question that you might be asking is, if there is no change in functionality, why should I use a specific annotation, why should I call this a service, why should I call the other one @Repository ? One of the things which we'll look at in the AOP section is that you can identify the annotation and add functionality over that specific thing. So these annotations, @Service, @Repository, @Controller allows you to classify your components into different categories, and you can apply different logic for each of these categories.

For example, Spring provides a default exception translation facility, if you use @Repository. There are a lot of JDBC exceptions and Spring classifies them and translates them, and that feature is provided only if you are using @Repository annotation. So on your database component if you are putting an @Component you'll not be able to use that feature. Also, you 'd see that a later point in time, let's say you would want to log everything that's coming into your business layer, in that kind of scenario you'd be able to identify everything that has an @Service annotation. You can use AOP to indentify that, and you can log all of the content which is coming in. We'll have a detailed discussion about this when we are talkin about it in the AOP section.

## Complete Code Example

##### /src/main/java/com/imh/spring/basics/springindepth/basics/BinarySearchImpl.java

```java
package com.imh.spring.basics.springindepth.basics;

//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service	//@Component before
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class BinarySearchImpl {

//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("bubble")
	private SortAlgorithm sortAlgorithm;
	
	public int binarySearch (int[] numbers, int numberTo) {
		int[] sortedNumbers = sortAlgorithm.sort(numbers);
		System.out.println(sortAlgorithm);
		//Search the array
		
		return 3;
	}
	
//	@PostConstruct
//	public void postConstruct() {
//		logger.info("postConstruct");
//	}
//
//	@PreDestroy
//	public void preDestroy() {
//		logger.info("preDestroy");
//	}
}	
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/basics/BubbleSortAlgorithm.java

```java
package com.imh.spring.basics.springindepth.basics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service	//@Component before
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service	//@Component before
@Qualifier("quick")
public class QuickSortAlgorithm implements SortAlgorithm {
	
	public int[] sort(int[] numbers) {
		//Logic for Quick Sort
		return numbers;
	}	
}	
``` 

---

##### /src/main/java/com/imh/spring/basics/springindepth/scope/PersonDAO.java


```java
package com.imh.spring.basics.springindepth.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository		//@Component before
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PersonDAO {						//Data Access Object, that's your Data layer.

	@Autowired
	JdbcConnection jdbcConnection;

	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
}
```

---

##### /src/main/java/componentscan/ComponentDAO.java

```java
package componentscan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository		//@Component before
public class ComponentDAO {

	@Autowired
	ComponentJdbcConnection jdbcConnection;

	public ComponentJdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setComponentJdbcConnection(ComponentJdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
}
```