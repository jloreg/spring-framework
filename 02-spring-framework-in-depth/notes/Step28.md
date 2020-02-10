## Step 28 - Spring Unit Testing with an XML Context

In the previous step we wrote a unit test using Java application context, so in this step, what I will do now is write a unit test using XML application configuration.

In the XML configuration test what I would do is define the context configuration @ContextConfiguration, earlyer in the previous step we define the context configuration with classes, but we don't want to use classes, what we want to do is use the locations. Locations are the XML 
locations="/applicationContext.xml".

If you want to run any configuration that is different from the real application context, then you can change the context configuration to use this location *@ContextConfiguration(locations="/testContext.xml")* as the context. One of the important things when we are picking up the context is that this file "/src/main/resources/applicationContext.xml" must be stored in the classpath of the unit test under different name "/src/test/resources/testContext.xml".

**Note**. When we use the character '/' in the annotation, we are specifying that the file testContext.xml is being located inside the classpath of the unit test.

```java
package com.imh.spring.basics.springindepth.basics;

@RunWith(SpringRunner.class)	
@ContextConfiguration(locations="/testContext.xml")
public class BinarySearchImplXMLConfgurationTest {
}
```

## Complete Code Example

##### /src/test/resources/testContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

	<!-- Here, we are importing all that is present in the XML configuration context defined in applicationContext.xml. Spring framework will use XML and 
		 Java configuration application context, not one or the other, if not both. Java configuration context on the application classes that contains 
		 Java annotations, and XML configuration on the application classes that uses XML configuration context. All the main application classes that
		 implements Java and XML configuration contexts are present inside base-package="com.imh.spring.basics.springindepth".
	-->
	<import resource="classpath:applicationContext.xml"/>
</beans>
```

---

##### /src/test/java/com/imh/spring/basics/springindepth/basics/BinarySearchImplXMLConfgurationTest.java

```java
package com.imh.spring.basics.springindepth.basics;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

//Load the Spring context
//Runner launch the configuration app SpringApplication.class; SpringRunner.class is part of the spring-test module.
@RunWith(SpringRunner.class)	
//Here we are using locations to specifying the XML configuration file for the the context.
@ContextConfiguration(locations="/testContext.xml")
public class BinarySearchImplXMLConfgurationTest {

	//Get this bean from the context (using autowiring).
	@Autowired
	BinarySearchImpl binarySearch;
	
	@Test
	public void testBasicScenario() {
		
		//call method on binarySearch
		int actualResult = binarySearch.binarySearch(new int[]{}, 5);
		//check if the result is the expected (first argument = expected value)
		assertEquals(3, actualResult);
	}
}
```