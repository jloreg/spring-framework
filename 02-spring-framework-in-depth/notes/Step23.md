## Step 23 - Mixing XML Context with Component Scan for Beans defined with Annotations

In the previous step we define a XML context, in this step we'll look at using a combination of XML context and annotations as well. So, let's get started now.

One of the first things we wanted to do was get rid of the @Configuration and the @ComponentScan annotations in the SpringXmlContextApplication bean, so this is not a Java configuration anymore.

Except that I've changed the user Logger in here *private static Logger LOGGER = LoggerFactory.getLogger(SpringXmlContextApplication.class);*, that's the only change that I have made. If you look at the thing which is printed into the console, you can see the JDBC connection, as well as, the DAO which is printed on the left hand side. That's we are printing in here using the Logger.info.

##### /src/main/java/com/imh/spring/basics/springindepth/SpringXmlContextApplication.xml

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.imh.spring.basics.springindepth.xml.XmlPersonDAO;

//@Configuration and @ComponentScan annotations has been removed
public class SpringXmlContextApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringXmlContextApplication.class);
	
	public static void main(String[] args) {
		
		try (ClassPathXmlApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml")) {
			
			// [xmlJdbcConnection, xmlPersonDAO]
			LOGGER.info("Beans Loaded -> {}", applicationContext.getBeanDefinitionNames());
			
			XmlPersonDAO personDao = applicationContext.getBean(XmlPersonDAO.class);
			//To make sure that it's auto-wired properly.
			LOGGER.info("{} {}", personDao, personDao.getXmlJdbcConnection());
		}
	}
}
```

What I want to do in this step, is try and use a combination of XML and a Java context. As of now what are the beans that are being loaded ? Let's find that out first. So, from the *applicationContext.getBeanDefinitionNames()*, I would want to find out what are all the beans that are loaded as of now by that specific application context ?; that's the thing which gives us the application context.

One of the things that you'll see when I run this instruction *LOGGER.info("Beans Loaded -> {}", applicationContext.getBeanDefinitionNames()) *, is that you'll see that the beans loaded, it's only showing the first bean which is loaded in here.

```java
Console output:
11:26:45.528 [main] INFO com.imh.spring.basics.springindepth.SpringXmlContextApplication - Beans Loaded -> xmlJdbcConnection
11:26:45.529 [main] INFO com.imh.spring.basics.springindepth.SpringXmlContextApplication - com.imh.spring.basics.springindepth.xml.XmlPersonDAO@42e99e4a com.imh.spring.basics.springindepth.xml.XmlJdbcConnection@14dd9eb7
```

The reason is because, the *Logger.info* accepts a variable argument and when you pass an array in place of a variable argument ,only the first element of the array is passed and it's replaced in here. I would want the entire array to be passed, so what I'll do, is I'll convert this to an Object. So, I'll type cast this to an Object *LOGGER.info("Beans Loaded -> {}", (Object) applicationContext.getBeanDefinitionNames());*, and now run the application SpringXmlContextApplication. You'll see now that more bean definitions would come up.

```java
Console output:
11:25:25.575 [main] INFO com.imh.spring.basics.springindepth.SpringXmlContextApplication - Beans Loaded -> [xmlJdbcConnection, xmlPersonDAO]
11:25:25.577 [main] INFO com.imh.spring.basics.springindepth.SpringXmlContextApplication - com.imh.spring.basics.springindepth.xml.XmlPersonDAO@14dd9eb7 com.imh.spring.basics.springindepth.xml.XmlJdbcConnection@52e6fdee
```

So, in this context *ClassPathXmlApplicationContext("applicationContext.xml")*, how many beans are being loaded right now? Two beans are being loaded: XmlJdbcConnection, and XmlPersonDAO. These are the two things which we defined in the applicationContext.xml. That's exactly what we expected. 

##### /src/main/resources/applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
	
	<!-- Here, you must define the beans, and the dependencies as well; id:name of the Bean, class:=fetch the Java class for the bean-->
    <bean id="xmlJdbcConnection" class="com.imh.spring.basics.springindepth.xml.XmlJdbcConnection">
        <!-- collaborators and configuration for this bean go here -->
    </bean>
    <bean id="xmlPersonDAO" class="com.imh.spring.basics.springindepth.xml.XmlPersonDAO">
			<!-- With the tag property, here we auto-wire the JdbcConnection bean defined previously, into a PersonDAO bean;-->
    		<property name="xmlJdbcConnection" ref="xmlJdbcConnection"/>
    </bean>
</beans>
```

---

Now I will also want to load up all the things which we have defined in this com.imh.spring.basics.springindepth package. How do I do that ? The answer to that is, we will need to define a Component Scan to scan the package com.imh.spring.basics.springindepth and and the subpackages within it (basics,boot,cdi,scope,xml). So, when Spring does this Component Scan on this subpackages, whether you're using XML definition or a Java definition, it does not really matter.  All that we need to do is get a Component Scan triggered over these subpackages. 

The context which we are using is a XML context ("applicationContext.xml"). So in the XML context you'd want to define a Component Scan. How do we do that ? In the Java context we looked at it earlier, we used @ComponentScan to define the ComponentScan. How do we do it in the XML context ? We need to use something called <context: component-scan>, one of the things is the context is not yet available in here "applicationContext.xml", so let's first define the context schema. Until now, only the beans schema is present here, we need to define the context schema also in here. 

So, what we are defining now is a new schema. What I will do is this is context and for this, I'd want to define a new shortcut name, so that's   *xmlns:context="http://www.springframework.org/schema/context"* , so we have a shortcut called name called context. By default, when you use anything without a name space <bean id="No name space here" >, it belongs to this namespace *beans xmlns="http://www.springframework.org/schema/beans"*. Now for context I'm defining a name space saying the context *xmlns:context="http://www.springframework.org/schema/context"*, and also I've defined the schema location fot the context; make sure that you are replacing "beans" with "context", so three places you'll need to replace with context here in the schema location:
	
```xml
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
```
  
Once you replace that I can go ahead and now use the context name space. You can see that one of the things which is being offered right now is context:component-scan. I'll use that, all that I needed to do was press control+space, context:component-scan. Now I'll need to give it which package to use. Base package is the package we would want to scan, we can either scan the "com.imh.spring.basics.springindepth" package, then we would load everything, let's do that.

```xml
<context:component-scan base-package="com.imh.spring.basics.springindepth.xml"/>
```

All that we are doing is define a Component Scan in the XML context.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
	
	<context:component-scan base-package="com.imh.spring.basics.springindepth"/>

	<!-- Here, you must define the beans, and the dependencies as well; id:name of the Bean, class:=fetch the Java class for the bean-->
    <bean id="xmlJdbcConnection" class="com.imh.spring.basics.springindepth.xml.XmlJdbcConnection">
        <!-- collaborators and configuration for this bean go here -->
    </bean>
    <bean id="xmlPersonDAO" class="com.imh.spring.basics.springindepth.xml.XmlPersonDAO">
			<!-- With the tag property, here we auto-wire the JdbcConnection bean defined previously, into a PersonDAO bean;-->
    		<property name="xmlJdbcConnection" ref="xmlJdbcConnection"/>
    </bean>
</beans>
```

Let's run the SpringXmlContextApplication application:

```java
Console output:
13:04:15.125 [main] INFO com.imh.spring.basics.springindepth.SpringXmlContextApplication - Beans Loaded -> [springApplication, springCdiApplication, springComponentScanApplication, springScopeApplication, binarySearchImpl, bubbleSortAlgorithm, quickSortAlgorithm, someCdiBusiness, someCdiDao, scopedTarget.jdbcConnection, jdbcConnection, personDAO, org.springframework.context.annotation.internalConfigurationAnnotationProcessor, org.springframework.context.annotation.internalAutowiredAnnotationProcessor, org.springframework.context.event.internalEventListenerProcessor, org.springframework.context.event.internalEventListenerFactory, xmlJdbcConnection, xmlPersonDAO, componentDAO, scopedTarget.componentJdbcConnection, componentJdbcConnection]
13:04:15.126 [main] INFO com.imh.spring.basics.springindepth.SpringXmlContextApplication - com.imh.spring.basics.springindepth.xml.XmlPersonDAO@7d9f158f com.imh.spring.basics.springindepth.xml.XmlJdbcConnection@45efd90f
```
You can see  that now a huge list of beans are being loaded. What's present in here is JdbcConection, SpringAlication, etc. You can see all the component scan application, all of them are also loaded, because they are all defined as a configuration. You can see that the XmlJdbcConnection are loaded, the BinarySearchImpl are loaded, etc, and everything else is being loaded up. So if you want to go further, you can actually try and retrieve the other beans and bring the content from them also.
 
This is how we define an application context try *(ClassPathXmlApplicationContext applicationContext =  new ClassPathXmlApplicationContext("applicationContext.xml"))*, with the beans loaded through component scan *<context:component-scan base-package="com.imh.spring.basics.springindepth"/>*.So, here we are actually using a combination. We have for some of the beans, we are defining and autowiring them in the applicationContext.xml.

```xml
<bean id="xmlJdbcConnection" class="com.imh.spring.basics.springindepth.xml.XmlJdbcConnection">
  <!-- collaborators and configuration for this bean go here -->
</bean>
<bean id="xmlPersonDAO" class="com.imh.spring.basics.springindepth.xml.XmlPersonDAO">
  <!-- With the tag property, here we auto-wire the JdbcConnection bean defined previously, into a PersonDAO bean;-->
  <property name="xmlJdbcConnection" ref="xmlJdbcConnection"/>
</bean>
```

For the other beans, we are using a Component Scan in applicationContext.xml to load them up.

```xml
xmlns:context="http://www.springframework.org/schema/context"
xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd">
	
<context:component-scan base-package="com.imh.spring.basics.springindepth"/>
```

One of the exercises I would suggest for you to do is to remove this:

```xml
<!-- Here, you must define the beans, and the dependencies as well; id:name of the Bean, class:=fetch the Java class for the bean-->
<bean id="xmlJdbcConnection" class="com.imh.spring.basics.springindepth.xml.XmlJdbcConnection">
    <!-- collaborators and configuration for this bean go here -->
</bean>
 <bean id="xmlPersonDAO" class="com.imh.spring.basics.springindepth.xml.XmlPersonDAO">
	<!-- With the tag property, here we auto-wire the JdbcConnection bean defined previously, into a PersonDAO bean;-->
    <property name="xmlJdbcConnection" ref="xmlJdbcConnection"/>
</bean>
```

So, try and remove this, and later add the Java annotations on top of the XmlJdbcConnections, XmlPersonDao, then you'll see that everything would work as usual. So try and play around with these application contexts, both the XML and the Java configurations. It's very important that you understand both the XML configurations and the Java configuration, and what you can do through each one of them.

From here on, we will be focusing on using a Java context because with Spring Boot we see that everybody is switching to a Java context. I see very little need for us to define context in XML, but it's always good for you to understand what has happened earlier, because there are always earlier applications that you would want to maintain.
  
---

## Complete Code Example

##### /src/main/resources/applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
	
	<context:component-scan base-package="com.imh.spring.basics.springindepth"/>

	<!-- Here, you must define the beans, and the dependencies as well; id:name of the Bean, class:=fetch the Java class for the bean-->
    <bean id="xmlJdbcConnection" class="com.imh.spring.basics.springindepth.xml.XmlJdbcConnection">
        <!-- collaborators and configuration for this bean go here -->
    </bean>
    <bean id="xmlPersonDAO" class="com.imh.spring.basics.springindepth.xml.XmlPersonDAO">
			<!-- With the tag property, here we auto-wire the JdbcConnection bean defined previously, into a PersonDAO bean;-->
    		<property name="xmlJdbcConnection" ref="xmlJdbcConnection"/>
    </bean>
</bean
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/SpringXmlContextApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.imh.spring.basics.springindepth.xml.XmlPersonDAO;

//@Configuration and @ComponentScan annotations has been removed
public class SpringXmlContextApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(SpringXmlContextApplication.class);
	
	public static void main(String[] args) {
		
		try (ClassPathXmlApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml")) {
			
			// [xmlJdbcConnection, xmlPersonDAO]
			LOGGER.info("Beans Loaded -> {}", (Object) applicationContext.getBeanDefinitionNames());
			
			XmlPersonDAO personDao = applicationContext.getBean(XmlPersonDAO.class);
			//To make sure that it's auto-wired properly.
			LOGGER.info("{} {}", personDao, personDao.getXmlJdbcConnection());
		}
	}
}	
```