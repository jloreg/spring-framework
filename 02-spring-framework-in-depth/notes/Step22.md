## Step 22 - Defining Spring Application Context using XML - Part 2

In the last step, we define the beans for loading using XML. We defined XmlPersonDAO, and XmlJdbcConnection. We also defined XML,so we created an applicationContext.xml that we can use to load the beans up. We are manually creating the beans, and also auto-wiring the xmlJdbcConnection into the xmlPersonDAO. 

Now we would want to launch up this applicationContext. So we will need to create an main application file for that. So let's get started with that in this step. I'll make a copy of the SpringApllication and rename it to SpringXmlContextApplication. The problem here, the thing which we would want to do, is we would want to load ApplicationContext using XML.

```java
@Configuration
@ComponentScan("com.imh.spring.basics.springindepth")	//by default it's equivalent to @ComponentScan ("com.imh.spring.basics.springindepth")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class SpringApplication {

	public static void main(String[] args) {
		
		//CHANGE this to load ApplicationContext using XML insted of Spring annotations.
		try (AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(SpringApplication.class)) {
			
			BinarySearchImpl binarySearch = applicationContext.getBean(BinarySearchImpl.class);
			BinarySearchImpl binarySearch1 = applicationContext.getBean(BinarySearchImpl.class);
			System.out.println(binarySearch);
			System.out.println(binarySearch1);

			int result = binarySearch.binarySearch(new int[] {12,4,6}, 3);
			System.out.println(result);
		}
	}
}
```
How do we that ? For loading the Java context up, until now we use AnnotationConfigApplicationContext. So similar to that there is something called ClasspathXMLApplicationContext. So we create a ClassPathXmlApplicationContext and pass in the location of the applicationContext.xml. Since we put it in my directory "/src/main/resources/", it's readily available in the ClassPath. All that we would need to do is to give the name of it "applicationContext.xml"

```java
package com.imh.spring.basics.springindepth;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.imh.spring.basics.springindepth.xml.XmlPersonDAO;

public class SpringXmlContextApplication {

	public static void main(String[] args) {
		
		try (ClassPathXmlApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml")) {
			
			XmlPersonDAO personDao = applicationContext.getBean(XmlPersonDAO.class);
			System.out.println(personDao);
			//To make sure that it's auto-wired properly.
			System.out.println(personDao.getXmlJdbcConnection());
			
		}
	}
}
```

What we are doing now instead of using a Java application context, is use  XML application context.  So we are defining XMLApplicationContext using "applicationContext.xml", and we are trying to load the bean "XmlPersonDAO.class" from it in XmlPersonDAO personDao = applicationContext.getBean(XmlPersonDAO.class). If you look at the XmlPersonDAO, we are not using any annotations in here. So this is how we were doing development when I started with Spring. So you had to actually define the beans, then you had to go the applicationContext.xml, define the dependencies. 

These kind of xml run in thousands and thousands of lines, and they are very, very difficult to maintain, because whenever I add a dependency in here I would need to go into the applicationContext.xml and add the dependency in there as well. And whenever I create a new bean I would need to define in it here as well. So it was very tough. The idea behind this xml context lecture, is to just give you an idea of how things were before the Java context confifuration came into picture. **With annotations, the configuration is much, much more easier, but before annotations it was really, really difficult to wire beans using spring**.

What I'll recommend you to do is to play around with this thing as well, is to play around with this thing  as well. Try and create a couple of new beans, a few dependencies, auto-wires them in, and play around with the xml context to understand this stuff much more.

## Complete Code Example

##### /src/main/java/com/imh/spring/basics/springindepth/SpringXmlContextApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.imh.spring.basics.springindepth.xml.XmlPersonDAO;

public class SpringXmlContextApplication {

	public static void main(String[] args) {
		
		try (ClassPathXmlApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("applicationContext.xml")) {
			
			XmlPersonDAO personDao = applicationContext.getBean(XmlPersonDAO.class);
			System.out.println(personDao);
			//To make sure that it's auto-wired properly.
			System.out.println(personDao.getXmlJdbcConnection());
			
		}
	}
}
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/xml/XmlPersonDAO.java

```java
package com.imh.spring.basics.springindepth.xml;

public class XmlPersonDAO {						//Data Access Object, that's your Data layer.

	XmlJdbcConnection jdbcConnection;

	public XmlJdbcConnection getXmlJdbcConnection() {
		return jdbcConnection;
	}

	public void setXmlJdbcConnection(XmlJdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
}
```

---

##### /src/main/java/com/imh/spring/basics/springindepth/xml/XmlJdbcConnection.java
 
```java
package com.imh.spring.basics.springindepth.xml;

public class XmlJdbcConnection {
	
	public XmlJdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```