## Step 26 - Read values from external properties file

Typically, applicatios have a lot of configuration. For example, in the data layer, you might be talking to a database. So, the URL to the database or the data source connection to the database might be different in different enviroments. Also, the external services that you talk to might be different in different enviroments. So the service you could be talking to in Dev would be different from the service you would be talking to in Production.

These kind of values are good to be externalized into **property files**. In this step, will look at how to read values from property files in  a Spring application. As usuall, we'll create a new class SomeExternalService, and I would want to locate it in the package com.imh.spring.basics.springindepth.properties.

Now, I would want to actually have a URL. So here I would want to have something called a URL. This URL, I would want it to be different, in different enviroments. So, I would want to get this value from property file.

```java
package com.imh.spring.basics.springindepth.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SomeExternalService {

	private String url;
	
	//Typically, this would be something like a call or something like that. Fow now, let it just return the value which it reads from the property file.
	public String returnServiceURL(){
		return url;
	}
}
```

Typically, the property files, which we would use, we'll put in /src/main/resources/. Create a new file here called app.properties. In here, I would  want to be able to configure the value of this URL. So, I'll say:

```
external.service.url=http://someserver.dev.com/service
```

Let's this be the URL of the external service. Now, I would want to read the value from this property value over here *external.service.url*.So, if you want to read the value from a property file, the way you would do that is use an annotation called @value.

```javas
@Component
public class SomeExternalService {
		
	@Value("${external.service.url}")
	private String url;
	
	//Typically, this would be something like a call or something like that. Fow now, let it just return the value which it reads from the property file.
	public String returnServiceURL(){
		return url;
	}
}
```
Also what I would need to do is  actually have a specific syntax that I need to follow. You have to put it between "${ }".This is something which a lot of people make a mistake with, so if you don't have this, then this will not really work. So, now I'm saying pick up the value from the property file. 

But I've not said the app.properties, I have not really configured it anywhere. We will configure that when we are loading up the context. So let's create a new SpringPropertiesApplication main application and 

3:50
what we would want to do is, you would want to say that I would want to load in app.properties at number one. So I would want to load in the line //TODO. Load in app.properties, we need to define that in here. Other than that, I would want to load the SomeExternalService in. So I would want to say SomeExternalService.class. I would want to get this and put it in here *SomeExternalService service = applicationContext.getBean(SomeExternalService.class);*

```java
package com.imh.spring.basics.springindepth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.imh.spring.basics.springindepth.properties.SomeExternalService;

@Configuration
@ComponentScan 
//TODO. Load in app.properties
public class SpringPropertiesApplication {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringPropertiesApplication.class)) {

			SomeExternalService service = applicationContext.getBean(SomeExternalService.class);
			System.out.println(service.returnServiceURL());
		}
	}
}
```

Now we are able to get the external service, but we have not yet configured  the app.properties. So, how do we tell this configuration to load app.properties? The way you can do that is by defining an annotation @PropertySource that is present in /src/main/resources/, so I can say @PropertySource ("classpath:app.properties").

```
@Configuration
@ComponentScan 
@PropertySource("classpath:app.properties")
public class SpringPropertiesApplication {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringPropertiesApplication.class)) {

			SomeExternalService service = applicationContext.getBean(SomeExternalService.class);
			System.out.println(service.returnServiceURL());
		}
	}
}
```
Let's run it, and now you will see that SomeExternalService and *service.returnServiceURL()*, is being printed in here. You'd see that service.returnServiceURL it's picking up the value from the property file.

```
//Console output:
11:18:37.394 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'someExternalService'
11:18:37.396 [main] DEBUG org.springframework.core.env.PropertySourcesPropertyResolver - Found key 'external.service.url' in PropertySource 'class path resource [app.properties]' with value of type String
http://someserver.dev.com/service
11:18:37.485 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@1c2c22f3, started on Tue Feb 04 11:18:37 CET 2020
```
 
**Important**. What you can do is you can actually remove the property file from src/main/resources and put it outside, and have that folder in the class path of the application. By that in your production, you can have different app.properties. In your Dev, you can have diferent app.properties. By using which you can configure your application differently in Production compared to your configuration in Dev, QA, or Stage.


## Complete Code Example

##### /src/main/java/com/imh/spring/basics/springindepth/SpringPropertiesApplication.java

```java
package com.imh.spring.basics.springindepth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.imh.spring.basics.springindepth.properties.SomeExternalService;

@Configuration
@ComponentScan 
@PropertySource("classpath:app.properties")
public class SpringPropertiesApplication {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringPropertiesApplication.class)) {

			SomeExternalService service = applicationContext.getBean(SomeExternalService.class);
			System.out.println(service.returnServiceURL());
		}
	}
}
```

--- 

##### /src/main/java/com/imh/spring/basics/springindepth/properties/SomeExternalService.java

```java
package com.imh.spring.basics.springindepth.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SomeExternalService {
	
	@Value("${external.service.url}")
	private String url;
	
	public String returnServiceURL(){
		return url;
	}

}
```

---

##### /src/main/resources/app.properties

```text
external.service.url=http://someserver.dev.com/service
```