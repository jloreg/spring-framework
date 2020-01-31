## Step 21 - Defining Spring Application Context using XML - Part 1

In this step we'll take a peek back into history. What we'll do, is we will try and create and XML context and wire dependencies there. So instead of using annotations, we'll create an xml context and try and wire things there, and see how it works out. What I'll start with is, I'll start creating a basic example for us.

I'll copy the JdbcConnection and PersonDao in the scope example (package com.imh.spring.basics.springindepth.scope),  I'll remove the annotations, and shift it to in a different package com.imh.spring.basics.springindepth.xml.

```java
public class XmlPersonDAO {						//Data Access Object, that's your Data layer.

	XmlJdbcConnection jdbcConnection;

	public XmlJdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(XmlJdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
}
```

```java
public class XmlJdbcConnection {
	
	public XmlJdbcConnection() {
		System.out.println("JDBC Connection");
	}
}
```

All that we did was created a very simple example. So, we have a XMLJdbcConnection with a constructor, as well as an XMLPersonDAO,  which has a dependency on XMLJdbcConnection, and a getter and setter for that. That's basically this.

So, before Spring 5 all these	kind of things had to be wired in manually through XML. So how does an XML context for that look like ?. Let's do it right now. You would neeed to create a new XML and specify the complete content of the XML. 

One of the things is you need to specify the name pace, and specifying the name space is a little tricky thing. So what I'll do is go to the spring documentation and get it. Search in google by the term "spring documentation application context example", and go to the next [URL](https://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/beans.html). So I'll take this sample example, and put it in own project /src/main/resources/applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

  <bean id="..." class="...">
    <!-- collaborators and configuration for this bean go here -->
  </bean>

  <bean id="..." class="...">
    <!-- collaborators and configuration for this bean go here -->
  </bean>

  <!-- more bean definitions go here -->

</beans>
```
In the applicationContext.xml, you can define the beans and the dependencies as well. Two things which we created are XmlJdbcConnection and XmlPersonDao. So let's create  a couple of beans for each one of them.

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
			<!-- With the tag property, here I auto-wire the JdbcConnection bean defined previously, into a PersonDAO bean;-->
    		<property name="xmlJdbcConnection" ref="xmlJdbcConnection"/>
    </bean>
</beans>
```

We have now defined a simple applicationContext.xml. However we should need to still create a simple main application to be able to load this up. We will do that and  more in the next step.

## Complete Code Example

##### ##### /src/main/resources/applicationContext.xml
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

---

##### /src/main/java/com/imh/spring/basics/springindepth/xml/XmlPersonDAO.java
```java
package com.imh.spring.basics.springindepth.xml;

public class XmlPersonDAO {						//Data Access Object, that's your Data layer.

	XmlJdbcConnection jdbcConnection;

	public XmlJdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(XmlJdbcConnection jdbcConnection) {
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