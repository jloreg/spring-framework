## Step 02 - Setting up AOP Example - Part 2

In the previous step we set up a simple project for AOP and created a couple of business and dao things. In this step we'll refactor a little bit as well, as we would want to intercept the calls to the business layer. What will do to start off is move the business stuff to a specific package com.imh.spring.aop.springaop.business, and move the data stuff to a specific package com.imh.spring.aop.springaop.data.

So, we have a business layer, a data layer, and we have a SpringAopApplication class which is the main application.

Unitl now we have been doing is we got the application context from *SpringApplication.run(X.class, args);*. So what we did is we assigned this to a local variable. We tried to get the application context, and we were using the application context to retrieve the bean from there. 

```java
@SpringBootApplication
public class SpringBootApplicationExample {
	
	public static void main(String[] args) {
		//Get Spring Application Context
		ConfigureApplicationContext applicationContext = SpringApplication.run(SpringBootApplication.class, args);
		Business1 business1 = applicationContext.getBean(Business1.class);
		//Do something with the business layer
	}
}
```
In SpringBoot there is another way where we can do stuff like that, what we can do is implement somenthing called a command line runner. So whatever we have defined in command line runner would be invoked as soon as the application is launched. So inside the run method I can do whatever I would want, as soon as the application launches up. So once the application launches up, the runner would get invoked.

The great thing about using a runner is we no longer need to write in SpringAopApplication.class, code in a static thing inside the main. So I can actually autowire things in, so I can @Autowired all the stuff I would need. If I run the application, the business methods are getting called, the business method are calling the Dao. The Dao are autowired in and we are getting this printed out.

```java
package com.imh.spring.aop.springaop;

@SpringBootApplication
public class SpringAopApplication implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Business1 business1;

	@Autowired
	private Business2 business2;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringAopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(business1.calculateSomething());
		logger.info(business2.calculateSomething());
	}
}
```

What we did is we implemented a command line runner and we auto wired our dependencies in, and we were able to call them from the run method. The run method is one of the things that you need to implement; run method is what is defined in the command line runner interface. As soon as we implement the run method, whenever the Spring Boot application launches up, what it does is it also calls the run method. And once the Spring Context is ready it would call launch up the command line runner and would be seeing the information which is implemented in the run method.

In the next step we would write AOP code to intercept these calls. What we want to do is all the calls that are coming into the business layer, we would want to intercept them and log them.

## Complete Code Example

##### /src/main/java/com/imh/spring/aop/springaop/SpringAopApplication.java

```java
package com.imh.spring.aop.springaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.in28minutes.spring.aop.springaop.business.Business1;
import com.in28minutes.spring.aop.springaop.business.Business2;

@SpringBootApplication
public class SpringAopApplication implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Business1 business1;

	@Autowired
	private Business2 business2;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringAopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(business1.calculateSomething());
		logger.info(business2.calculateSomething());
	}
}
```

---

##### /src/main/java/com/imh/spring/aop/springaop/business/Business1.java

```java
package com.imh.spring.aop.springaop.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imh.spring.aop.springaop.data.Dao1;

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

##### /src/main/java/com/imh/spring/aop/springaop/business/Business2.java

```java
package com.imh.spring.aop.springaop.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imh.spring.aop.springaop.data.Dao2;

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

##### /src/main/java/com/imh/spring/aop/springaop/data/Dao1.java

```java
package com.imh.spring.aop.springaop.data;

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

##### /src/main/java/com/imh/spring/aop/springaop/data/Dao2.java

```java
package com.imh.spring.aop.springaop.data;

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