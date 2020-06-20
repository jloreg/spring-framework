## Step 01 - Setting up AOP Example - Part 1

The package "spring-aop-XXXX.jar" present in the Maven dependencies, is one of the popular implementations of AOP, this is provided by default but it's not as powerful as "aspectj". You can use spring-aop to intercept any calls to beans. So any beans which are managed by this Spring Framework you can intercept the method calls and do something around that. However Aspectj is much more powerful, you can intercept change of values on a field and things like that.

Typically when we talk about applications, we would talk about layers, so you have web layer, business layer, data layer, each layer has its own concern. However there are things called cross-cutting concerns. One of them is logging security, these are not things which are related to a specific layer. Let' I want to track performance across the layers. This is not really a single layer concern, I would like to track performance across al the layers. So this is a crosscutting concern.

Let's focus on getting our first AOP example up and running. AOP stands for Aspect Oriented Programming, and is the best approach for implementing cross cutting concerns. Let's create a simple example for aop. What we'll do is we'll create two business classes and two DAO classes which we use as example for the rest of this stuff on aop.

So we created a couple of business services and a couple of Dao respositories, what we want to do in the next step is intercept calls to this.

## Complete Code Example

##### /src/main/java/com/imh/spring/aop/springaop/Business1.java

```java
package com.imh.spring.aop.springaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service	//@Service	is for Business layer
public class Business1 {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Dao1 dao1;

	public String calculateSomething(){
		//Business Logic
		String value = dao1.retrieveSomething();
		logger.info("In Business - {}", value);
		return value;
	}
}
```

---

##### /src/main/java/com/imh/spring/aop/springaop/Business2.java

```java
package com.imh.spring.aop.springaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service	//@Service	is for Business layer
public class Business2 {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Dao2 dao2;

	public String calculateSomething(){
		//Business Logic
		String value = dao2.retrieveSomething();
		logger.info("In Business - {}", value);
		return value;
	}
}
```

---

##### /src/main/java/com/imh/spring/aop/springaop/Dao1.java

```java
package com.imh.spring.aop.springaop;

import org.springframework.stereotype.Repository;

@Repository	//@Repository is something which talk's with the database; @Repository is for Data layer. 
public class Dao1 {

	/* Ideally, when you are naming your Dao methods, I prefer calling them retrieve instead of get. Retrieve is something which indicates that I'm trying 
	 * to retrieve from our database, or from my external thing. When I use get to represent something, it might be confused with a getter, so get or setter,
	 * that kind of stuff.*/
	public String retrieveSomething(){
		return "Dao1";
	}

}
```

---

##### /src/main/java/com/imh/spring/aop/springaop/Dao2.java

```java
package com.imh.spring.aop.springaop;

import org.springframework.stereotype.Repository;

@Repository	//@Repository is something which talk's with the database; @Repository is for Data layer. 
public class Dao2 {

	/* Ideally, when you are naming your Dao methods, I prefer calling them retrieve instead of get. Retrieve is something which indicates that I'm trying 
	 * to retrieve from our database, or from my external thing. When I use get to represent something, it might be confused with a getter, so get or setter,
	 * that kind of stuff.*/
	public String retrieveSomething(){
		return "Dao2";
	}

}
```