## Step 16 - Using Component Scan to scan for beans

We created a package outside the principal package "com.imh.spring.basics.springindepth" called componentscan. We created a simple ComponentDAO which uses another @Autowired thing called ComponentJdbcConnection, and the ComponentJdbcConnection also is in the same package called componentscan. Now, we have also created a simple ComponentScanApplication class, which is actually looking  for the Component DAO class.

ComponentScanApplication class it's picking up the bean *applicationContext.getBean(ComponentDAO.class)* from the context and plating in out in the instruction* LOGGER.info("{}", componentDAO);

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import componentscan.ComponentDAO;

@SpringBootApplication
public class ComponentScanApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(ComponentScanApplication.class);

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				ComponentScanApplication.class)) {
			ComponentDAO componentDAO = applicationContext.getBean(ComponentDAO.class);

			LOGGER.info("{}", componentDAO);
		}
	}
}
```

Let's see what happens when I run this.
```
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'componentscan.ComponentDAO' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:351)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:342)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1126)
	at com.imh.spring.basics.springindepth.ComponentScanApplication.main(ComponentScanApplication.java:19)
```
Says "I can not find the ComponentDAO for you", what is the problem ?. Why is it not able to find the component for us ?. Let's  check the basics that we would usually check fo. So when I check for something where a bean not found is found, first thing I'll check is if there an @Component or an @Service on it.


```java
package com.imh.spring.basics.springindepth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import componentscan.ComponentDAO;

@SpringBootApplication
public class ComponentScanApplication {
```

The reason why this is not being picked up is because this package which of where it is present in is not part of the ComponentScan. So, what happens is here we are saying SpringBootApplication **-whenever we say something is a @SpringBootApplication it automatically defines a ComponentScan on this package com.imh.spring.basics.springindepth;-**, and these (package componentscan), are packages off it. So everything in this package com.imh.spring.basics.springindepth and its sub-packages will be picked up by that ComponentScan. 

So whenever I say that ComponentScanApplication class uses @SpringBootApplication, it's almost like saying that I want to ComponentScan on this package, I mean putting the @ComponentScan in. So this is what is present by default. By default we are looking for everything inside this package. So everything inside this com.imh.spring.basics.springindepth package and its sub-packages is by default picked up. However, the things in other packages are not picked up. So if I want other packages to be picked up, then I would need to add @ComponentScan on them as well.

```java
package com.imh.spring.basics.springindepth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import componentscan.ComponentDAO;

@SpringBootApplication
//@ComponentScan("com.imh.spring.basics.springindepth")	//By default when you uses @SpringBootApplication annotation
@ComponentScan("componentscan")
public class ComponentScanApplication {
```

Let's see what happens when I run this.
```
Console output:
10:25:26.116 [main] INFO com.imh.spring.basics.springindepth.ComponentScanApplication - componentscan.ComponentDAO@1ef3efa8
```
This is one thing you should always remember. So ComponentScan is a very important thing and every time you have a no bean found exception, then the problem could be either one of these two things. One is the @Component or the @Service, or the corresponding annotation; if it's not there then that component will not be picked up at all. The other one is the absence of a ComponentScan; if you're not scanning that package for components, then it will not even pick that specific thing up.

You can look ComponentScan as like a search. So Spring needs to find the components. It needs to be able to create the beans. For creating the beans, it needs to find the package which are elegible to be beans and so it does a search. This search is called a ComponentScan and only the things which Spring finds during the ComponentScan will be created as beans. So it's on you to tell Spring where to search for components and that definition is called a ComponentScan.

## Complete Code Example

##### /src/main/java/com/imh/spring/basics/springindepth/ComponentScanApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import componentscan.ComponentDAO;

@SpringBootApplication
@ComponentScan("componentscan")
public class ComponentScanApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(ComponentScanApplication.class);

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				ComponentScanApplication.class)) {
			ComponentDAO componentDAO = applicationContext.getBean(ComponentDAO.class);

			LOGGER.info("{}", componentDAO);
		}
	}
}
```
---

##### /src/main/java/componentscan/ComponentDAO.java

```java
package componentscan;	//I put the class in a different package out of the scope from the principal package (when our application ComponentScanApplication runs).

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
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
---

##### /src/main/java/componentscan/ComponentJdbcConnection.java

```java
package componentscan;	//I put the class in a different package out of the scope from the principal package (when our application ComponentScanApplication runs).

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, 
		proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ComponentJdbcConnection {
	public ComponentJdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```