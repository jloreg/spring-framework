## Step 15 - Complex Scope Scenarios of a Spring Bean - Mix Prototype and Singleton

In this step, we would look at one complex scenario which is related to this scope. What we want to find out is what would happen if bean is off scope of singleton. Until now, there is only one instance of the BinarySearchImpl, but what would happen if the dependency of a Singleton bean like BinarySearchImpl is a Prototype bean ?, -the Sort algorithm instances, for example-. What would happen in that kind of situation ? That's what we would want to find out.

## Singleton scope

```java
@SpringBootApplication
public class ScopeApplication {
	
	//Logging with SLF4J Logger; because it's static, I would need to rename 'logger' to LOGGER
	private static Logger LOGGER = LoggerFactory.getLogger(ScopeApplication.class);

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ScopeApplication.class);

		//Create two instances of PersonDAO
		PersonDAO personDao = applicationContext.getBean(PersonDAO.class);
		PersonDAO personDao2 = applicationContext.getBean(PersonDAO.class);

		//"{}": thisi would replace whatever is in within the second argument (personDao, personDao.getJdbcConnection(), etc) 
		LOGGER.info("{}", personDao);					//Prints instance hascode
		LOGGER.info("{}", personDao.getJdbcConnection());	//Prints Jdbc instance hascode
		
		LOGGER.info("{}", personDao2);
		LOGGER.info("{}", personDao2.getJdbcConnection());
	}
}
```
 
```java
@Component
public class PersonDAO {

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

```java
@Component
public class JdbcConnection {
	public JdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```
One of the things which we'll need to remember is the fact as of now we have not really put any annotations related to scope, on either the JdbcConnection or the PersonDao. So the default scope that they would get is singleton scope. 

As we see below, both instances of PersonDAO are the same, you can see that the adress for them are the same, and also the same instance of the JdbcConnection is being used.

```
Console output:
17:17:53.719 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@5d1659ea
17:17:53.720 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@33aeca0b
17:17:53.720 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@5d1659ea
17:17:53.720 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@33aeca0b
```

## Prototype and Singleton scope

Now let's go to the PersonDAO and make the scope for this as PROTOTYPE.

```java
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PersonDAO {

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

```java
@Component
//Scope: Singleton by default
public class JdbcConnection {
	public JdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```

As you can see below the PersonDAO is different; it has a different ID. However the JdbcConnection, if you look at it, is the same. Because the JdbcConnection we have actually left it, there's no scope on it, default is singleton.

```
Console output:
15:55:01.346 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@4bd1f8dd
15:55:01.346 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@784b990c
15:55:01.346 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@3d3ba765
15:55:01.346 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@784b990c
```

---

The example that we really wanted to discuss in here is the fact that I would want to use a new JdbcConnection every time, so I'll remove the @Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE) from PersonDAO -now is using the default scope-, and I add the annotation @Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS) in the dependency JdbcConnection. Now I'm saying that in PersonDAO, I would want to use the same instance every time. However, in the JdbcConnection, I'd want different instances each time. So let's see what would happen now.

```java
@Component			//Scope: Singleton by default
public class PersonDAO {

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

```java
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JdbcConnection {
	public JdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```

You can see below that I'm getting the same instance for PersonDAO, but also the same instance for JdbcConnection.

```
Console output:
17:58:57.288 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@47c4ecdc
17:58:57.288 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@6475472c
17:58:57.288 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@47c4ecdc
17:58:57.289 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@6475472c
```
**IMPORTANT**. So even though I'm trying to get the JdbcConnection and I would want to it to be a prototyp  e, **I'm not really getting different instances of it**. The reason is because **we are actually trying to get the PersonDAO**. 

**So in the ScopeApplication, we are trying to retrieve PersonDAO, and PersonDAO is directly getting the JdbcConnection (@Autowired JdbcConnection jdbcConnection)**. It's having one instance of it and it's happy with it. It does not still realize the fact that JdbcConnection has a prototype scope. If we really want to have a different instance of the JdbcConnection whenever we have a request through PersonDAO, then we would need to configure something called a proxy.

---

So what we need to do is, instead of directly giving it a JdbcConnection, we need to give it a proxy. And the proxy would make sure that it gets a new instance of the JdbcConnection each time. So how do it configure a proxy in here ?, you need to add an additional attribute in @Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS).

What it does ? in all the places where this JdbcConnection is used, it would start using the proxy, and thereby, whenever there is a request, whenever there is a get on this, it would make sure that there's a new JdbcConnection being used.

```java
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, 
		proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JdbcConnection {
	public JdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```

Now you can see the PersonDAO is the same. However, we have two different instances of JdbcConnection.

```
Console output:
13:34:53.029 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@40f1be1b
JDBC Connection
13:34:53.030 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@2b95e48b
13:34:53.031 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.PersonDAO@40f1be1b
JDBC Connection
13:34:53.031 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@4a3329b9
```

In this exemple, we wanted to show you an example of the proxy. So whenever you are trying to get a bean **and one of its dependencies is a protototype, then on the dependency, we should use a proxy**. So we should use a proxy, so that whenever we try to get a bean and and try to get the dependency, we would get a new instance of the dependency.

One of the most important things you need to understand is the fact that whenever we are trying to program, we are trying to create as less numbers of objects as possible. If we create more objects, then they would occupy more memory and also there would be more pressure on garbage collection and things like that. So you would want to keep the number of objects that are created to a minimum. I could have easily made this a prototype and created one instance of PersonDAO for every request (@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE). But that would defeat the purpose of keeping the number of objects, number of instances very low.

```java
@Component
public class PersonDAO {
	@Autowired
	JdbcConnection jdbcConnection;	//jdbcConnection is a dependencie with prototype scope. 
}
```

In this step, what we'll look that is, when a dependencie or one of the dependencies is a prototype, and the bean using that is not a prototype (PersonDAO), how do you do that ? We saw that we would solve that by using a proxy. 

```java
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, 
		proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JdbcConnection {
	public JdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
``` 
## Resume

The important thing here is whenever on the same instance of the PersonDAo, if I'm calling getJdbcConnection again, what would happen ? It would actually get a new JdbcConnection. So on the same instance, I'm calling getJdbcConnection, and now I have two different JdbcConnections.

```java
@SpringBootApplication
public class ScopeApplication {

		LOGGER.info("{}", personDao2.getJdbcConnection());
		LOGGER.info("{}", personDao2.getJdbcConnection());
	}
}
```
 Below you see there are two JdbcConnections which are present.

```
Console output:
19:16:33.863 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@5b40ceb
JDBC Connection
19:16:33.863 [main] INFO com.imh.spring.basics.springindepth.ScopeApplication - com.imh.spring.basics.springindepth.scope.JdbcConnection@13c3c1e1
```
So the scope proxy is very important, especially when even on the same instance personDao2, I'm calling getJdbcConnection twice, I would want two different instances of JdbcConnections being used. That is the reason why a proxy becomes very important. What would happen is, instead of the JdbcConnection getting autowired into the PersonDAO, what would be autowired in, is something called a proxy. So a proxy gets autowired in here, and whenever you call the getJdbcConnection, the proxy would make sure that it would return the new JdbcConnection back.

```java
@Component
public class PersonDAO {
	@Autowired
	JdbcConnection jdbcConnection;	//proxy gets autowired here 
	
	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
}
```

## Complete Code Example

##### /src/main/java/com/imh/spring/basics/springindepth/ScopeApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.imh.spring.basics.springindepth.scope.PersonDAO;

@SpringBootApplication
public class ScopeApplication {
 
	private static Logger LOGGER = 
			LoggerFactory.getLogger(ScopeApplication.class);

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ScopeApplication.class);

		PersonDAO personDao = applicationContext.getBean(PersonDAO.class);
		PersonDAO personDao2 = applicationContext.getBean(PersonDAO.class);
 
		LOGGER.info("{}", personDao);
		LOGGER.info("{}", personDao.getJdbcConnection());
		LOGGER.info("{}", personDao2);
		LOGGER.info("{}", personDao2.getJdbcConnection());
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

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PersonDAO {

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

##### /src/main/java/com/imh/spring/basics/springindepth/scope/JdbcConnection.java

```java
package com.imh.spring.basics.springindepth.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, 
		proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JdbcConnection {

	public JdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```
---